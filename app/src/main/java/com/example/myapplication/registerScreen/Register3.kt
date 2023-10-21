package com.example.myapplication.registerScreen

import android.app.AlertDialog
import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.myapplication.R
import com.example.myapplication.Singelton
import com.example.myapplication.databinding.ActivityRegister3Binding
import com.example.myapplication.loginScreen.LoginPage
import com.google.android.material.snackbar.Snackbar

class Register3 : AppCompatActivity() {

    private lateinit var binding: ActivityRegister3Binding
    private lateinit var activityResultLauncher: ActivityResultLauncher<Intent>
    private lateinit var permissonLauncher: ActivityResultLauncher<String>
    var selectedPicture: Uri? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegister3Binding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)


        val textInputLayout = binding.nameSingelton
        val textInputLayout2 = binding.surnameSingelton
        val textInputLayout3 = binding.phoneSingelton
        val textInputLayout4 = binding.emailSingelton

        val editText = textInputLayout.editableText
        val editText2 = textInputLayout2.editableText
        val editText3 = textInputLayout3.editableText
        val editText4 = textInputLayout4.editableText

//TEXT INPUT TIKLAMA DALGASI BAŞLANGIÇ
        textInputLayout.isEnabled = false
        textInputLayout.isFocusable = false
        textInputLayout.isClickable = false

        textInputLayout2.isEnabled = false
        textInputLayout2.isFocusable = false
        textInputLayout2.isClickable = false

        textInputLayout3.isEnabled = false
        textInputLayout3.isFocusable = false
        textInputLayout3.isClickable = false

        textInputLayout4.isEnabled = false
        textInputLayout4.isFocusable = false
        textInputLayout4.isClickable = false
 //TEXT INPUT TIKLAMA DALGASI BİTİŞ

        val singletonName = Singelton.name
        val singletonSurname = Singelton.surname
        val singletenPhone = Singelton.phone
        val singletonEmail = Singelton.email


        if (singletonName != null && singletonSurname != null && singletenPhone != null && singletonEmail != null ) {
            binding.nameSingelton.setText(singletonName)
            binding.surnameSingelton.setText(singletonSurname)
            binding.phoneSingelton.setText(singletenPhone)
            binding.emailSingelton.setText(singletonEmail)
        } else {
            showAlertDialog("HATA!","HATA!")
        }

        // EditText öğesini düzenlenebilir hale getirin
        binding.nameSingelton.isEnabled = true

        registerLauncher()
    }


    fun selectImage(view: View){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_MEDIA_IMAGES) != PackageManager.PERMISSION_GRANTED){

            // Andorid mantigi soruyoru!
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_MEDIA_IMAGES)){
                Snackbar.make(view, "Permission needed for gallery", Snackbar.LENGTH_INDEFINITE).setAction("Give Permisson", View.OnClickListener {
                    // request Permisson
                    permissonLauncher.launch(Manifest.permission.READ_MEDIA_IMAGES)
                }).show()
            }else{
                // request Permisson
                permissonLauncher.launch(Manifest.permission.READ_MEDIA_IMAGES)
            }
        }else{
            val intentToGallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            activityResultLauncher.launch(intentToGallery)
        }
    }

    // 3step Permisson
    private fun registerLauncher(){
        activityResultLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult(),
            ActivityResultCallback { result ->
                if (result.resultCode == RESULT_OK){
                    val intentFromResult = result.data
                    if (intentFromResult != null){
                        selectedPicture = intentFromResult.data
                        selectedPicture?.let{ uri ->
                            binding.imageViewProfile.setImageURI(uri)
                        }
                    }
                }
            })
        permissonLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()){ result ->
            if (result){
                // Permisson Granted
                val intentToGallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)

                activityResultLauncher.launch(intentToGallery)
            }else{
                Toast.makeText(this,"permisson needed", Toast.LENGTH_LONG).show()
            }
        }
    }


    fun goSuccessPage(view: View) {
        val intent = Intent(this, LoginPage::class.java)
        startActivity(intent)
    }

      private fun showAlertDialog(title: String, message: String) {
        AlertDialog.Builder(this)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton("Tamam") { dialog, _ ->
                dialog.dismiss()
            }
            .create()
            .show()
    }


}
