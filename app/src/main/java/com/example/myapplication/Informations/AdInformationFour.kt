package com.example.myapplication.Informations

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.myapplication.R
import com.example.myapplication.Singelton
import com.example.myapplication.databinding.ActivityAdInformationFourBinding
import com.example.myapplication.databinding.ActivityRegister3Binding
import com.google.android.material.snackbar.Snackbar
import java.util.UUID
import androidx.activity.result.ActivityResultCallback


class AdInformationFour : AppCompatActivity() {

    private lateinit var binding: ActivityAdInformationFourBinding
    private lateinit var activityResultLauncher: ActivityResultLauncher<Intent>
    private lateinit var permissonLauncher: ActivityResultLauncher<String>
    var selectedPicture2: Uri? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdInformationFourBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        uploadLauncher()
    }
    fun selectImage2(view: View) {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_MEDIA_IMAGES
            ) != PackageManager.PERMISSION_GRANTED
        ) {

            // Andorid mantigi soruyoru!
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    this,
                    Manifest.permission.READ_MEDIA_IMAGES
                )
            ) {
                Snackbar.make(view, "Permission needed for gallery", Snackbar.LENGTH_INDEFINITE)
                    .setAction("Give Permisson", View.OnClickListener {
                        // request Permisson
                        permissonLauncher.launch(Manifest.permission.READ_MEDIA_IMAGES)
                    }).show()
            } else {
                // request Permisson
                permissonLauncher.launch(Manifest.permission.READ_MEDIA_IMAGES)
            }
        } else {
            val intentToGallery =
                Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            activityResultLauncher.launch(intentToGallery)
        }
    }

    private fun uploadLauncher(){
        activityResultLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult(),
            ActivityResultCallback { result ->
                if (result.resultCode == RESULT_OK){
                    val intentFromResult = result.data
                    if (intentFromResult != null){
                        selectedPicture2 = intentFromResult.data
                        selectedPicture2?.let { uri ->
                            binding.imageViewUpload.setImageURI(uri)
                        }
                    }
                }
            })
        permissonLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()){ result ->
            if (result){
                // Permission Granted
                val intentToGallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                activityResultLauncher.launch(intentToGallery)
            }else{
                Toast.makeText(this, "Permission needed", Toast.LENGTH_LONG).show()
            }
        }
    }






}



