package com.example.myapplication.Informations

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
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
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import java.io.ByteArrayOutputStream
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

        val uuid = UUID.randomUUID().toString()
        var customDocumentName = Singelton.username + uuid
        val userInfo = mutableListOf<String>().apply {
            add("email : " + Singelton.email)
            add("name : " + Singelton.name)
            add("Surname : " + Singelton.surname)
            add("telefonNo : " + Singelton.phone)
        }

        val photoURLs = mutableListOf<String>()

        for ((index, image) in images.withIndex()) {
            val compressedImage = compressImage(image, maxFileSizeKB = 200)

            val photoStorage = FirebaseStorage.getInstance()
            val storageReference = photoStorage.reference
            val mediaFolder = storageReference.child("homePhoto")

            val photoFileName = "${customDocumentName}-$index.jpg"
            val photoReference = mediaFolder.child(photoFileName)

            val uploadTask = photoReference.putBytes(compressedImage)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        // Fotoğraf başarıyla yüklendi
                        photoReference.downloadUrl
                            .addOnSuccessListener { url ->
                                val urlString = url.toString()
                                photoURLs.add(urlString)

                                val db = FirebaseFirestore.getInstance()

                                val docData = hashMapOf(
                                    "photoURLs" to photoURLs,
                                    "isActive" to 0,
                                    "ilanBasligi" to SallerHomeSingelton.ilanBasligi,
                                    "ilanFiyat" to SallerHomeSingelton.ilanfiyat,
                                    // ... (diğer alanları ekleyin)

                                    "ilanYuklemeTarihi" to FieldValue.serverTimestamp(),
                                    // ... (diğer alanları ekleyin)
                                )

                                db.collection("ilanlar").document(customDocumentName)
                                    .set(docData)
                                    .addOnCompleteListener { innerTask ->
                                        if (innerTask.isSuccessful) {
                                            // Ortak Ilanlara kaydedildi!
                                            db.collection(SallerHomeSingelton.ilanKategorisi)
                                                .document(customDocumentName)
                                                .set(docData)
                                                .addOnCompleteListener { finalTask ->
                                                    if (finalTask.isSuccessful) {
                                                        // Ortak Ilanlara kaydedildi!
                                                        successUploadAlert(
                                                            "İlan Bildirimi",
                                                            "İlanınız Başarılı şekilde bizlere ulaştı. İnceleme süresi ortalama 30-60 dakika arasıdır. Beklediğiniz için teşekkür ederiz."
                                                        )
                                                    } else {
                                                        setupShowAlert(
                                                            "Hata",
                                                            "Yükleme işleminde bir sorun oluştu ${finalTask.exception?.localizedMessage}",
                                                            "Yeniden Dene"
                                                        )
                                                    }
                                                }
                                        } else {
                                            setupShowAlert(
                                                "Hata",
                                                "Yükleme işleminde bir sorun oluştu ${innerTask.exception?.localizedMessage}",
                                                "Yeniden Dene"
                                            )
                                        }
                                    }
                            }
                            .addOnFailureListener { error ->
                                setupShowAlert("Hata", "Fotoğraf yüklenirken bir hata oluştu. $error", "Tamam")
                            }
                    } else {
                        setupShowAlert(
                            "Hata",
                            "Fotoğraf yüklenirken bir sorun oluştu ${task.exception?.localizedMessage}",
                            "Tamam"
                        )
                    }
                }
        }
    }

    // Resmi sıkıştırmak için özel bir fonksiyon
    private fun compressImage(bitmap: Bitmap, maxFileSizeKB: Int): ByteArray {
        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
        var quality = 100
        while (stream.toByteArray().size / 1024 > maxFileSizeKB && quality > 0) {
            stream.reset()
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, stream)
            quality -= 10
        }
        return stream.toByteArray()
    }
    }
}
