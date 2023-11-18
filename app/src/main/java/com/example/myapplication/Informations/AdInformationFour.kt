package com.example.myapplication.Informations

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.example.myapplication.SallerHomeSingelton
import com.example.myapplication.Singelton
import com.example.myapplication.adapter.ImageRecyclerAdapter
import com.example.myapplication.databinding.ActivityAdInformationFourBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import java.util.*

class AdInformationFour : AppCompatActivity() {

    companion object {
        private const val GALLERY_REQUEST_CODE = 123 // Bu kodu değiştirebilirsiniz
    }

    private lateinit var binding: ActivityAdInformationFourBinding
    private lateinit var activityResultLauncher: ActivityResultLauncher<Intent>
    private lateinit var permissionLauncher: ActivityResultLauncher<String>
    private lateinit var imageRecyclerAdapter: ImageRecyclerAdapter
    private val selectedImages = ArrayList<Uri>()
    private var selectedPicture: Uri? = null

    private lateinit var auth: FirebaseAuth
    private lateinit var firebaseDB: FirebaseFirestore
    private lateinit var firebaseStorage: FirebaseStorage

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdInformationFourBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        auth = FirebaseAuth.getInstance()
        firebaseDB = FirebaseFirestore.getInstance()
        firebaseStorage = FirebaseStorage.getInstance()

        // RecyclerView için adaptör ve layoutManager oluşturuluyor
        imageRecyclerAdapter = ImageRecyclerAdapter(selectedImages) { position ->
            // Handle the deletion of the image at the specified position
            selectedImages.removeAt(position)
            imageRecyclerAdapter.notifyDataSetChanged()
            updateFotoYukleVisibility()
        }

        binding.imageRecyclerView.layoutManager = GridLayoutManager(this, 3) // Adjust the span count as needed
        binding.imageRecyclerView.adapter = imageRecyclerAdapter

        registerLauncher()
        updateFotoYukleVisibility()
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    private fun showAlertDialog(title: String, message: String) {
        AlertDialog.Builder(this)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton("OK") { dialog, _ -> dialog.dismiss() }
            .show()
    }

    fun fotoEkle(view: View) {
        if (selectedImages.size >= 5) {
            showToast("You can select up to 5 photos.")
            return
        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_MEDIA_IMAGES) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_MEDIA_IMAGES)) {
                Snackbar.make(view, "Gallery için izin gereklidir", Snackbar.LENGTH_INDEFINITE)
                    .setAction("İzni Ver") {
                        permissionLauncher.launch(Manifest.permission.READ_MEDIA_IMAGES)
                    }.show()
            } else {
                permissionLauncher.launch(Manifest.permission.READ_MEDIA_IMAGES)
            }
        } else {
            val intentToGallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            activityResultLauncher.launch(intentToGallery)
        }
    }

    private fun updateFotoYukleVisibility() {
        if (selectedImages.isNotEmpty()) {
            binding.fotoYukle.visibility = View.GONE
        } else {
            binding.fotoYukle.visibility = View.VISIBLE
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == GALLERY_REQUEST_CODE && resultCode == RESULT_OK) {
            val intentFromResult = data?.data
            intentFromResult?.let { uri ->
                selectedImages.add(uri)
                imageRecyclerAdapter.notifyDataSetChanged()
                updateFotoYukleVisibility()

                // Seçilen resmi selectedPicture değişkenine atayın
                selectedPicture = uri
            }
        }
    }

    private fun registerLauncher() {
        activityResultLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult(),
            ActivityResultCallback { result ->
                if (result.resultCode == RESULT_OK) {
                    val intentFromResult = result.data
                    intentFromResult?.data?.let { uri ->
                        selectedImages.add(uri)
                        imageRecyclerAdapter.notifyDataSetChanged()
                        updateFotoYukleVisibility()

                        // Seçilen resmi selectedPicture değişkenine atayın
                        selectedPicture = uri
                    }
                }
            })

        permissionLauncher =
            registerForActivityResult(ActivityResultContracts.RequestPermission()) { result ->
                if (result) {
                    val intentToGallery =
                        Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                    activityResultLauncher.launch(intentToGallery)
                } else {
                    showToast("Permission required")
                }
            }
    }

    fun firebaseButton(view: View) {
        binding.yayinButton.isEnabled = false

        if (selectedPicture != null) {
            val uuid = UUID.randomUUID()
            val imageName = "${Singelton.name + Singelton.surname}$uuid.jpg"
            val fotoDatabase = firebaseStorage.reference
            val imageReference = fotoDatabase.child("homePhoto").child(imageName)

            imageReference.putFile(selectedPicture!!)
                .addOnSuccessListener { _ ->
                    showToast("İlan başarıyla oluşturuldu!")

                    imageReference.downloadUrl
                        .addOnSuccessListener { uri ->
                            val downloadURL = uri.toString()
                            val userMap = hashMapOf(
                                "downloadURL" to downloadURL,
                                "İlan Başlığı" to SallerHomeSingelton.ilanBasligi!!,
                                "Fiyat" to SallerHomeSingelton.ilanfiyat!!,
                                "M2(Net)" to SallerHomeSingelton.evM2!!,
                                "Oda Sayısı" to SallerHomeSingelton.odaSayisi!!,
                                "Bina Yaşı" to SallerHomeSingelton.binaYasi!!,
                                "Banyo Sayısı" to SallerHomeSingelton.banyoSyisi!!,
                                "Balkon" to SallerHomeSingelton.balkonVarMi!!,
                                "CreateDateUser" to com.google.firebase.Timestamp.now(),
                                "Eşyalı" to SallerHomeSingelton.esyaliMi!!,
                                "Kat Sayısı" to SallerHomeSingelton.binaKatSayisi!!,
                                "Bulunduğu Kat" to SallerHomeSingelton.bulunduguKatSayisi!!,
                                "Kullanım Durumu" to SallerHomeSingelton.kullanimDurumu!!,
                                "Cephe" to SallerHomeSingelton.cephe!!,
                                "Ulaşım" to SallerHomeSingelton.ulasim!!,
                                "Isıtıma" to SallerHomeSingelton.isitmaVarMiNedir!!,
                                "Öğrenciye Uygun" to SallerHomeSingelton.ogrenciyeUygunmudur!!
                            )

                            val uuid = UUID.randomUUID()
                            val customDocumentName = Singelton.name + Singelton.surname + uuid
                            firebaseDB.collection("ilanlar").document(customDocumentName).set(userMap)
                                .addOnSuccessListener {
                                    // Başarıyla eklendi
                                }
                                .addOnFailureListener { exception ->
                                    showAlertDialog("Hata", "Kullanıcı bilgileri kaydedilemedi! ${exception.localizedMessage}")
                                }
                        }
                }
                .addOnFailureListener { exception ->
                    showAlertDialog("Hata", "Fotoğraf karşıya yüklenemedi. Lütfen daha sonra tekrar deneyiniz! ${exception.localizedMessage}")
                }
                .addOnCompleteListener {
                    binding.yayinButton.isEnabled = true
                }
        } else {
            showToast("Lütfen Fotoğraf Seçiniz")
            binding.yayinButton.isEnabled = true
        }
    }
}
