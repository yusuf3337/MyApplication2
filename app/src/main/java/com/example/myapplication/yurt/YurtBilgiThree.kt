package com.example.myapplication.yurt

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.AttributeSet
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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.Singelton.SallerHomeSingelton
import com.example.myapplication.Singelton.Singelton
import com.example.myapplication.Singelton.YurtDevirSingleton
import com.example.myapplication.adapter.ImageRecyclerAdapter2
import com.example.myapplication.databinding.ActivityYurtBilgiThreeBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import java.io.ByteArrayOutputStream
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class HorizontalRecyclerView : RecyclerView {

    constructor(context: Context) : super(context) {
        initialize()
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        initialize()
    }

    constructor(context: Context, attrs: AttributeSet?, defStyle: Int) : super(context, attrs, defStyle) {
        initialize()
    }

    private fun initialize() {
        layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
    }
}

class YurtBilgiThree : AppCompatActivity() {

    companion object {
        private const val GALLERY_REQUEST_CODE = 123 // Bu kodu değiştirebilirsiniz
    }


    private lateinit var binding: ActivityYurtBilgiThreeBinding
    private lateinit var activityResultLauncher: ActivityResultLauncher<Intent>
    private lateinit var permissionLauncher: ActivityResultLauncher<String>
    private lateinit var imageRecyclerAdapter2: ImageRecyclerAdapter2
    private val selectedImages2 = ArrayList<Uri>()
    private var selectedPicture: Uri? = null

    private lateinit var auth: FirebaseAuth
    private lateinit var firebaseDB: FirebaseFirestore
    private lateinit var firebaseStorage: FirebaseStorage

    var formattedDateTime = ""
    var uploadedPhotoCount = 0


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityYurtBilgiThreeBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val localDateTime = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        formattedDateTime = localDateTime.format(formatter)

        auth = FirebaseAuth.getInstance()
        firebaseDB = FirebaseFirestore.getInstance()
        firebaseStorage = FirebaseStorage.getInstance()

        imageRecyclerAdapter2 = ImageRecyclerAdapter2(selectedImages2) { position ->
            selectedImages2.removeAt(position)
            imageRecyclerAdapter2.notifyDataSetChanged()
            updateFotoYukle2Visibility()
        }
        binding.imageRecyclerView2.layoutManager = LinearLayoutManager(binding.root.context, LinearLayoutManager.HORIZONTAL, false)
        binding.imageRecyclerView2.adapter = imageRecyclerAdapter2

        val snapHelper = LinearSnapHelper()
        snapHelper.attachToRecyclerView(binding.imageRecyclerView2)

        registerLauncher()
        updateFotoYukle2Visibility()
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

    private fun updateFotoYukle2Visibility() {
        if (selectedImages2.isNotEmpty()) {
            binding.fotoYukle2.visibility = View.GONE
        } else {
            binding.fotoYukle2.visibility = View.VISIBLE
        }
    }

    fun fotoEkle2(view: View) {
        if (selectedImages2.size >= 10) {
            showToast("You can select up to 10 photos.")
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
        if (requestCode == GALLERY_REQUEST_CODE && resultCode == RESULT_OK) {
            val intentFromResult = data?.data
            intentFromResult?.let { uri ->
                selectedImages2.add(uri)
                imageRecyclerAdapter2.notifyDataSetChanged()
                updateFotoYukle2Visibility()
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
                        selectedImages2.add(uri)
                        imageRecyclerAdapter2.notifyDataSetChanged()
                        updateFotoYukle2Visibility()
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

    fun firebaseButton2(view: View) {
        if (selectedImages2.isEmpty()) {
            showToast("Please select at least one photo.")
            return
        }

        val compressedImages = mutableListOf<ByteArray>()

        for (uri in selectedImages2) {
            val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, uri)
            val compressedImage = compressImage(bitmap, 200)
            compressedImage?.let { compressedImages.add(it) }
        }

        if (compressedImages.isNotEmpty()) {
            uploadFirebase2(compressedImages)
        } else {
            showToast("Error compressing images.")
        }
    }

    fun uploadFirebase2(images: List<ByteArray>) {
        val uuid = UUID.randomUUID().toString()
        val customDocumentName = Singelton.username + uuid

        val photoURLs: MutableList<String> = mutableListOf()

        for ((index, image) in images.withIndex()) {
            val photoStorage = FirebaseStorage.getInstance()
            val storageReference = photoStorage.reference
            val mediaFolder = storageReference.child("yurtPhoto")

            val photoFileName = "$customDocumentName-$index.jpg"
            val photoReference = mediaFolder.child(photoFileName)

            val uploadTask = photoReference.putBytes(image)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        photoReference.downloadUrl.addOnCompleteListener { urlTask ->
                            if (urlTask.isSuccessful) {
                                val urlString = urlTask.result.toString()
                                photoURLs.add(urlString)

                                uploadedPhotoCount++

                                if (uploadedPhotoCount == images.size) {
                                    updateFirestore2(customDocumentName, photoURLs)
                                }
                            } else {
                                val error = urlTask.exception
                                println("URL Hatası: $error")
                            }
                        }
                    } else {
                        val error = task.exception
                        println("Yükleme Hatası: $error")
                    }
                }
        }
    }

    fun updateFirestore2(customDocumentName: String, photoURLs: List<String>) {
        val db = FirebaseFirestore.getInstance()


        val docData2 = hashMapOf(
            "photoURLs" to photoURLs,
            "isActive" to 0,
            "yurtilanBasligi" to YurtDevirSingleton.yurtilanBasligi,
            "okulSecimi" to YurtDevirSingleton.okulSecimi,
            "yurtSecimi" to YurtDevirSingleton.yurtSecimi,
            "odaTipiSecimi" to YurtDevirSingleton.odaTipiSecimi,
            "odaDevirSecenekleri" to YurtDevirSingleton.odaDevirSecenekleri,
            "tercihEdilenCinsiyet" to YurtDevirSingleton.tercihEdilenCinsiyet,
            "elektirikUcret" to YurtDevirSingleton.elektirikUcret,
            "internetDurumu" to YurtDevirSingleton.internetDurumu,
            "yurtGirisCikisSaatlari" to YurtDevirSingleton.yurtGirisCikisSaatlari,
            "yurtBlokVarmi" to YurtDevirSingleton.yurtBlokVarmi,
            "kizErkekKarisikMi" to YurtDevirSingleton.kizErkekKarisikMi,
            "yurtilanAciklamasi" to YurtDevirSingleton.yurtilanAciklamasi,
            "ilanYuklemeTarihi" to FieldValue.serverTimestamp(),
            "ilanNumarasi" to 997756,
            "doping" to 0,
            "acilAcilDoping" to 0,
            "dopingCerceve" to 0,
            "dopingYazisi" to "",
            "gosterimSayisi" to 0
        )

        db.collection("ilanlar").document(customDocumentName)
            .set(docData2)
            .addOnSuccessListener {
                SallerHomeSingelton.ilanKategorisi?.let { it1 ->
                    db.collection(it1)
                        .document(customDocumentName)
                        .set(docData2)
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
                println("Firestore Ayarlama Hatası: $e")
                showToast("Error setting Firestore document.")
            }
    }

    fun compressImage(image: Bitmap, maxFileSizeKB: Int): ByteArray? {
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
