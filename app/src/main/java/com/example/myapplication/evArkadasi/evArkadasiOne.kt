package com.example.myapplication.evArkadasi

import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.annotation.ContentView
import androidx.annotation.RequiresApi
import com.example.myapplication.Informations.AdInformationTwo
import com.example.myapplication.R
import com.example.myapplication.adapter.ImageRecyclerAdapter
import com.example.myapplication.databinding.ActivityEvArkadasiOneBinding
import com.example.myapplication.databinding.ActivityYurtBilgiThreeBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.ArrayList

class evArkadasiOne : AppCompatActivity() {

    companion object {
        private const val GALLERY_REQUEST_CODE = 123 // Bu kodu değiştirebilirsiniz
    }
    private lateinit var binding: ActivityEvArkadasiOneBinding
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
        binding = ActivityEvArkadasiOneBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val localDateTime = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        formattedDateTime = localDateTime.format(formatter)

        auth = FirebaseAuth.getInstance()
        firebaseDB = FirebaseFirestore.getInstance()
        firebaseStorage = FirebaseStorage.getInstance()

        val intent = Intent(this, evArkadasiTwo::class.java)
        startActivity(intent)
    }

}