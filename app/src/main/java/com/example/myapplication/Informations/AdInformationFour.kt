package com.example.myapplication.Informations

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.SallerHomeSingelton
import com.example.myapplication.Singelton
import com.example.myapplication.adapter.ImageRecyclerAdapter
import com.example.myapplication.databinding.ActivityAdInformationFourBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import org.checkerframework.checker.index.qual.SameLen
import java.io.ByteArrayOutputStream
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
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

    var formattedDateTime = ""

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdInformationFourBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)


        val localDateTime = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        formattedDateTime = localDateTime.format(formatter)

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

        binding.imageRecyclerView.layoutManager = LinearLayoutManager(binding.root.context, LinearLayoutManager.HORIZONTAL, false)
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
                Snackbar.make(view, "Gallery için izin gereklidir", Snackbar.LENGTH_INDEFINITE)
                    .setAction("İzni Ver") {
                        permissionLauncher.launch(Manifest.permission.READ_MEDIA_IMAGES)
                    }.show()
            } else {
                permissionLauncher.launch(Manifest.permission.READ_MEDIA_IMAGES)
            }
        } else {
            val intentToGallery =
                Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
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
    /// Dikkat SIM TELEGON ILE WIFI SORUNU YASANIYOR
    fun firebaseButton(view: View) {
        if (selectedImages.isEmpty()) {
            showToast("Please select at least one photo.")
            return
        }

        val compressedImages = mutableListOf<ByteArray>()

        for (uri in selectedImages) {
            val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, uri)
            val compressedImage = compressImage(bitmap, 200)
            compressedImage?.let { compressedImages.add(it) }
        }

        if (compressedImages.isNotEmpty()) {
            uploadFirebase(compressedImages)
        } else {
            showToast("Error compressing images.")
        }
    }

    fun uploadFirebase(images: List<ByteArray>) {
        val uuid = UUID.randomUUID().toString()
        val customDocumentName = Singelton.username + uuid

        val photoURLs: MutableList<String> = mutableListOf()

        for ((index, image) in images.withIndex()) {
            val photoStorage = FirebaseStorage.getInstance()
            val storageReference = photoStorage.reference
            val mediaFolder = storageReference.child("homePhoto")

            val photoFileName = "$customDocumentName-$index.jpg"
            val photoReference = mediaFolder.child(photoFileName)

            val uploadTask = photoReference.putBytes(image)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        // Başarılı
                        photoReference.downloadUrl.addOnCompleteListener { urlTask ->
                            if (urlTask.isSuccessful) {
                                val urlString = urlTask.result.toString()
                                photoURLs.add(urlString)

                                if (photoURLs.size == images.size) {
                                    // Tüm resimler yüklendi, Firestore'u güncelle
                                    updateFirestore(customDocumentName, photoURLs)
                                }
                            } else {
                                // URL alımı başarısız
                                val error = urlTask.exception
                                println("URL Hatası: $error")
                                showToast("Error getting URL: ${error?.message}")
                            }
                        }
                    } else {
                        // Yükleme başarısız
                        val error = task.exception
                        println("Yükleme Hatası: $error")
                        showToast("Error uploading image: ${error?.message}")
                    }
                }
        }
    }

    fun updateFirestore(customDocumentName: String, photoURLs: List<String>) {
        val db = FirebaseFirestore.getInstance()
        val docData = hashMapOf(
            "photoURLs" to photoURLs,
            "isActive" to 3,
            "ilanBasligi" to SallerHomeSingelton.ilanBasligi,
            "ilanFiyat" to SallerHomeSingelton.ilanfiyat,
            "evM2" to SallerHomeSingelton.evM2,
            "evBanyoSayisi" to SallerHomeSingelton.banyoSyisi,
            "evBalkon" to SallerHomeSingelton.balkonVarMi,
            "evEsya" to SallerHomeSingelton.esyaliMi,
            "evBinaYasi" to SallerHomeSingelton.binaYasi,
            "evOdaSayisi" to SallerHomeSingelton.odaSayisi,
            "ilanAciklamsi" to SallerHomeSingelton.ilanAciklamasi,
            "evBinaKatSayisi" to SallerHomeSingelton.binaKatSayisi,
            "evBulunduguKat" to SallerHomeSingelton.bulunduguKatSayisi,
            "evCephe" to SallerHomeSingelton.cephe,
            "evUlasim" to SallerHomeSingelton.ulasim,
            "evOgrenciyeUygun" to SallerHomeSingelton.ogrenciyeUygunmudur,
            "evIsitma" to SallerHomeSingelton.isitmaVarMiNedir,
            "evAcikAdresi" to SallerHomeSingelton.acikAdres,
            "evinSehiri" to SallerHomeSingelton.ilanSehiri,
            "binaAidatTutari" to SallerHomeSingelton.binaAidatTutari,
            "evDepozitoTutari" to SallerHomeSingelton.evDepozitoTutari,
            "documentID" to customDocumentName,
// "ilanSahibi" to userInfo, // Eğer userInfo bir değişkense ekleyebilirsiniz
            "ilanYuklemeTarihi" to FieldValue.serverTimestamp(),
            "ilanKategorisi" to SallerHomeSingelton.ilanKategorisi,
            "ilanNumarasi" to 997755,
            "ilanParaBirimi" to SallerHomeSingelton.ilanParabirimi,
            "ilanKiraMinSuresi" to SallerHomeSingelton.minumumKiraSuresi,
            "aylikAidatParaBirimi" to SallerHomeSingelton.aidatParaBirimi,
            "depozitoParaBirimi" to SallerHomeSingelton.depozitoParaBirimi,
            "doping" to 0,
            "acilAcilDoping" to 0,
            "dopingCerceve" to 0,
            "dopingYazisi" to "",
            "gosterimSayisi" to 0
        )

        db.collection("ilanlar").document(customDocumentName)
            .set(docData)
            .addOnSuccessListener {
                // İşlem başarılı
                SallerHomeSingelton.ilanKategorisi?.let { it1 ->
                    db.collection(it1)
                        .document(customDocumentName)
                        .set(docData)
                        .addOnSuccessListener {
                            showToast("Ad successfully added to Firebase.")
                        }
                        .addOnFailureListener { e ->
                            println("Firestore Güncelleme Hatası: $e")
                            showToast("Error updating Firestore.")
                        }
                }
            }
            .addOnFailureListener { e ->
                // Handle Firestore set failure
                println("Firestore Ayarlama Hatası: $e")
                showToast("Error setting Firestore document.")
            }
    }


    // Fotoğraf Sıkıştırmak
    fun compressImage(image: Bitmap, maxFileSizeKB: Int): ByteArray? {
        var compression: Float = 0.8f // Örnek bir başlangıç değeri
        val maxCompression: Float = 0.1f
        val maxFileSizeBytes = maxFileSizeKB * 1024

        val imageData = ByteArrayOutputStream()

        try {
            image.compress(Bitmap.CompressFormat.JPEG, (compression * 100).toInt(), imageData)

            while (imageData.size() > maxFileSizeBytes && compression > maxCompression) {
                imageData.reset()
                compression -= 0.1f
                image.compress(Bitmap.CompressFormat.JPEG, (compression * 100).toInt(), imageData)
            }

            return imageData.toByteArray()
        } finally {
            imageData.close()
        }
    }


}

