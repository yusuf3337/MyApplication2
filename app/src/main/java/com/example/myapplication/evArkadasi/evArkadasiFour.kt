package com.example.myapplication.evArkadasi

import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.location.GnssNavigationMessage
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.support.annotation.RequiresApi
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.EvArkSingleton
import com.example.myapplication.R
import com.example.myapplication.SallerHomeSingelton
import com.example.myapplication.Singelton
import com.example.myapplication.adapter.evArkadasiRecyclerAdapter
import com.example.myapplication.databinding.ActivityEvArkadasiFourBinding
import com.example.myapplication.yurt.YurtBilgiThree
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import java.io.ByteArrayOutputStream
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale
import java.util.UUID

class evArkadasiFour : AppCompatActivity() {

    companion object {
        private const val GALLERY_REQUEST_CODE = 123
    }

    private lateinit var binding: ActivityEvArkadasiFourBinding
    private lateinit var activityResultLauncher: ActivityResultLauncher<Intent>
    private lateinit var permissionLauncher: ActivityResultLauncher<String>
    private lateinit var evArkadasiRecyclerAdapter: evArkadasiRecyclerAdapter
    private val selectedImages3 = ArrayList<Uri>()
    private var selectedPicture: Uri? = null

    private lateinit var auth: FirebaseAuth
    private lateinit var firebaseDB: FirebaseFirestore
    private lateinit var firebaseStorage: FirebaseStorage

    var formattedDateTime = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEvArkadasiFourBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val date = Date()
        val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        formattedDateTime = formatter.format(date)

        auth = FirebaseAuth.getInstance()
        firebaseDB = FirebaseFirestore.getInstance()
        firebaseStorage = FirebaseStorage.getInstance()

        evArkadasiRecyclerAdapter = evArkadasiRecyclerAdapter(selectedImages3) { position ->
            selectedImages3.removeAt(position)
            evArkadasiRecyclerAdapter.notifyDataSetChanged()
            updateevArkadasiFoto2Visibility()
        }

        binding.evArkadasiImage.layoutManager = LinearLayoutManager(this)
        binding.evArkadasiImage.adapter = evArkadasiRecyclerAdapter

        registerLauncher2()
        updateevArkadasiFoto2Visibility()
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    private fun showAlertDialog(title: String, message: String) {
        androidx.appcompat.app.AlertDialog.Builder(this)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton("OK") { dialog, _ -> dialog.dismiss() }
            .show()
    }

    private fun updateevArkadasiFoto2Visibility() {
        if (selectedImages3.isNotEmpty()) {
            binding.evArkadasiFoto.visibility = View.GONE
        }else{
            binding.evArkadasiFoto.visibility = View.VISIBLE
        }
    }
    fun evArkadasiFoto2(view: View) {
        if (selectedImages3.size >= 5) {
            showToast("You can select up to 5 photos.")
            return
        }

        if (ContextCompat.checkSelfPermission(
                this,
                android.Manifest.permission.READ_MEDIA_IMAGES
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    this,
                    android.Manifest.permission.READ_MEDIA_IMAGES
                )
            ) {
                Snackbar.make(view, "Gallery için izin gereklidir", Snackbar.LENGTH_INDEFINITE)
                    .setAction("İzni Ver") {
                        permissionLauncher.launch(android.Manifest.permission.READ_MEDIA_IMAGES)
                    }.show()
            } else {
                permissionLauncher.launch(android.Manifest.permission.READ_MEDIA_IMAGES)
            }
        } else {
            val intentToGallery =
                Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            activityResultLauncher.launch(intentToGallery)
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == evArkadasiFour.GALLERY_REQUEST_CODE && resultCode == RESULT_OK) {
            val intentFromResult = data?.data
            intentFromResult?.let { uri ->
                selectedImages3.add(uri)
                evArkadasiRecyclerAdapter.notifyDataSetChanged()
                updateevArkadasiFoto2Visibility()
            }
        }
    }
    private fun registerLauncher2() {
        activityResultLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult(),
            ActivityResultCallback { result ->
                if (result.resultCode == RESULT_OK){
                    val intentFromResult = result.data
                    intentFromResult?.data?.let { uri ->
                        selectedImages3.add(uri)
                        evArkadasiRecyclerAdapter.notifyDataSetChanged()
                        updateevArkadasiFoto2Visibility()
                    }
                }
            })

        permissionLauncher =
            registerForActivityResult(ActivityResultContracts.RequestPermission()) {result ->
                if (result) {
                    val intentToGallery =
                        Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                    activityResultLauncher.launch(intentToGallery)
                }else {
                    showToast("Permission required")
                }
            }
    }
    fun evArkadasiFirebase(view: View) {
        if (selectedImages3.isEmpty()) {
            showToast("Please select at least one photo.")
            return
        }
        val compressedImages = mutableListOf<ByteArray>()

        for (uri in selectedImages3) {
            val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, uri)
            val compressedImage = compressedImage(bitmap, 200)
            compressedImage?.let { compressedImages.add(it) }
        }

        if (compressedImages.isNotEmpty()) {
            uploadFirebase3(compressedImages)
        }else {
            showToast("Error compressing images")
        }
    }

    fun uploadFirebase3(images: List<ByteArray>) {
        val uuid = UUID.randomUUID().toString()
        val customDocumentName = Singelton.username + uuid

        val photosURLs: MutableList<String> = mutableListOf()

        for ((index, image) in images. withIndex()) {
            val photoStorage = FirebaseStorage.getInstance()
            val storageReference = photoStorage.reference
            val mediaFolder = storageReference.child("evArkFoto")

            val photoFileName = "$customDocumentName-$index.jpg"
            val photoReference = mediaFolder.child(photoFileName)

            val uploadTask = photoReference.putBytes(image)
                .addOnCompleteListener {task ->
                    if (task.isSuccessful) {
                        photoReference.downloadUrl.addOnCompleteListener { urlTask ->
                            if (urlTask.isSuccessful){
                                val urlString = urlTask.result.toString()
                                photosURLs.add(urlString)

                                if (photosURLs.size == images.size) {
                                    updateFirestore3(customDocumentName, photosURLs)
                                } else {
                                    val error = urlTask.exception
                                    println("URL Hatası: $error")
                                }
                            }
                        }
                    } else {
                        val error = task.exception
                        println("Yükleme Hatası: $error")
                    }
                }
        }
    }

    fun updateFirestore3(customDocumentName: String, photoURLs: List<String>){
        val db = FirebaseFirestore.getInstance()

        val docData3 = hashMapOf(
            "photoURLs" to photoURLs,
            "isActive" to 0,
            "kategori" to EvArkSingleton.kategori,
            "onayKontrl" to EvArkSingleton.onayKontrol,
            "tercihEdilenCinsiyet" to EvArkSingleton.tercihEdilenCinsiyet,
            "tercihEdilenYasAraligi" to EvArkSingleton.tercihEdilenYasAraligi,
            "alkolKullanimi" to EvArkSingleton.alkolKullanimi,
            "sigaraKullanimi" to EvArkSingleton.sigaraKullanimi,
            "universiteBolumu" to EvArkSingleton.universiteBolumu,
            "tercihEdilenUyruk" to EvArkSingleton.tercihEdilenUyruk,
            "ilanBasligi" to EvArkSingleton.ilanBasligi,
            "ilanAciklamasi" to EvArkSingleton.ilanAciklamasi,
            "ilanAdresi" to EvArkSingleton.ilanAdresi,
            "evSehir" to EvArkSingleton.evSehir,
            "ilanFiyat" to EvArkSingleton.ilanFiyat,
            "ilanFiyatBirim" to EvArkSingleton.ilanFiyatBirim,
            "minKiralamaSuresi" to EvArkSingleton.minKiralamaSuresi,
            "odaSayisi" to EvArkSingleton.odaSayisi,
            "ilanNumarasi" to 997756,
            "doping" to 0,
            "acilAcilDoping" to 0,
            "dopingCerceve" to 0,
            "dopingYazisi" to "",
            "gosterimSayisi" to 0
        )
        db.collection("ilanlar").document(customDocumentName)
            .set(docData3)
            .addOnSuccessListener {
                SallerHomeSingelton.ilanKategorisi?.let { it1 ->
                    db.collection(it1)
                        .document(customDocumentName)
                        .set(docData3)
                        .addOnSuccessListener {
                            showToast("Ad successfully added to Firebase")
                        }
                        .addOnFailureListener { e ->
                            println("Firestore Güncelleme Hatası: $e")
                            showToast("Error updating Firestore.")
                        }
                }
            }
            .addOnFailureListener { e ->
                println("Firestore Ayarlama Hatası: $e")
                showToast("Error setting Firestore document.")
            }
    }

    fun compressedImage(image: Bitmap, maxFileSizeKB: Int): ByteArray? {
        var compression: Float = 1.0f
        val maxCompression: Float = 0.1f
        val maxFileSizeBytes = maxFileSizeKB * 1024

        val outputStream = ByteArrayOutputStream()

        while (true) {
            image.compress(Bitmap.CompressFormat.JPEG, (compression * 100).toInt(), outputStream)
            val imageData = outputStream.toByteArray()

            if (imageData.size <= maxFileSizeBytes || compression <= maxCompression) {
                break
            }

            outputStream.reset()
            compression -= 0.1f
        }

        return outputStream.toByteArray()
    }

    }
