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
import com.example.myapplication.R
import com.example.myapplication.SallerHomeSingelton
import com.example.myapplication.Singelton
import com.example.myapplication.databinding.ActivityAdInformationFourBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.util.UUID

class AdInformationFour : AppCompatActivity() {

    private lateinit var binding: ActivityAdInformationFourBinding
    private lateinit var activityResultLauncher: ActivityResultLauncher<Intent>
    private lateinit var permissionLauncher: ActivityResultLauncher<String>

    private lateinit var auth: FirebaseAuth
    private lateinit var firebaseDB: FirebaseFirestore
    private lateinit var firebaseStorage: FirebaseStorage
    var selectedPicture2: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdInformationFourBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        auth = FirebaseAuth.getInstance()
        firebaseDB = FirebaseFirestore.getInstance()
        firebaseStorage = FirebaseStorage.getInstance()

        uploadLauncher()
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

    fun selectImage2(view: View) {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_MEDIA_IMAGES
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    this,
                    Manifest.permission.READ_MEDIA_IMAGES
                )
            ) {
                AlertDialog.Builder(this)
                    .setTitle("Permission Needed")
                    .setMessage("Permission needed for gallery")
                    .setPositiveButton("Give Permission") { _, _ ->
                        permissionLauncher.launch(Manifest.permission.READ_MEDIA_IMAGES)
                    }
                    .show()
            } else {
                permissionLauncher.launch(Manifest.permission.READ_MEDIA_IMAGES)
            }
        } else {
            val intentToGallery =
                Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            activityResultLauncher.launch(intentToGallery)
        }
    }

    private fun uploadLauncher() {
        activityResultLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult(),
            ActivityResultCallback { result ->
                if (result.resultCode == RESULT_OK) {
                    val intentFromResult = result.data
                    if (intentFromResult != null) {
                        selectedPicture2 = intentFromResult.data
                        selectedPicture2?.let { uri ->
                            binding.imageViewUpload.setImageURI(uri)
                        }
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
                    showToast("Permission needed")
                }
            }
    }

    fun firebaseButton(view: View) {
        binding.yayinButton.isEnabled = false

        if (selectedPicture2 != null) {
            val uuid = UUID.randomUUID()
            val imageName = "${Singelton.name + Singelton.surname}$uuid.jpg"
            val fotoDatabase = firebaseStorage.reference
            val imageReference = fotoDatabase.child("homePhoto").child(imageName)

            imageReference.putFile(selectedPicture2!!)
                .addOnSuccessListener { _ ->
                    showToast("İlan başarıyla oluşturuldu!")

                    imageReference.downloadUrl
                        .addOnSuccessListener { uri ->
                            val downloadURL = uri.toString()
                            val userMap = hashMapOf(
                                "downloadURL" to downloadURL,
                                "İlan Başlığı" to SallerHomeSingelton.ilanBasligi!!,
                                "Fiyat" to SallerHomeSingelton.fiyat!!,
                                "M2(Net)" to SallerHomeSingelton.m2!!,
                                "Oda Sayısı" to SallerHomeSingelton.odaSayisi!!,
                                "Bina Yaşı" to SallerHomeSingelton.binaYasi!!,
                                "Banyo Sayısı" to SallerHomeSingelton.banyoSayisi!!,
                                "Balkon" to SallerHomeSingelton.balkon!!,
                                "CreateDateUser" to com.google.firebase.Timestamp.now(),
                                "Eşyalı" to SallerHomeSingelton.esyali!!,
                                "Kat Sayısı" to SallerHomeSingelton.katSayisi!!,
                                "Bulunduğu Kat" to SallerHomeSingelton.bulunduguKat!!,
                                "Kullanım Durumu" to SallerHomeSingelton.kullanimDurumu!!,
                                "Cephe" to SallerHomeSingelton.cephe!!,
                                "Ulaşım" to SallerHomeSingelton.ulasim!!,
                                "Isıtıma" to SallerHomeSingelton.isitma!!,
                                "Öğrenciye Uygun" to SallerHomeSingelton.ogrenciyeUygun!!
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
