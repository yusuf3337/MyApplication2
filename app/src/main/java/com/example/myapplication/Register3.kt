package com.example.myapplication

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
import com.example.myapplication.databinding.ActivityRegister3Binding
import com.example.myapplication.loginScreen.LoginPage
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import java.util.UUID

class Register3 : AppCompatActivity() {

    private lateinit var binding: ActivityRegister3Binding
    private lateinit var activityResultLauncher: ActivityResultLauncher<Intent>
    private lateinit var permissonLauncher: ActivityResultLauncher<String>
    var selectedPicture: Uri? = null

    // Firebase Progress
    private lateinit var auth: FirebaseAuth
    private lateinit var firebaseDB: FirebaseFirestore
    private lateinit var firebaseStorage: FirebaseStorage

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegister3Binding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        auth = Firebase.auth
        firebaseDB = Firebase.firestore
        firebaseStorage = Firebase.storage

        val singletonName = Singelton.name
        val singletonSurname = Singelton.surname
        val singletenPhone = Singelton.phone
        val singletonEmail = Singelton.email

        //showAlertDialog("veri", singletenPhone + singletonEmail)

            binding.surnameSingelton.setText(singletonSurname)
            binding.phoneSingelton.setText(singletenPhone)
            binding.emailSingelton.setText(singletonEmail)
            binding.nameSingelton.setText(singletonName)






        // EditText öğesini düzenlenebilir hale getirin
        //binding.nameSingelton.isEnabled = true

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
        //upload()
    }






    fun upload(){
        binding.registerButton.isEnabled = false
        val uuid = UUID.randomUUID()
        val imageName = "${Singelton.name + Singelton.surname}$uuid.jpg"

        val fotoDatabase = firebaseStorage.reference
        val imageReferance = fotoDatabase.child("profile_photos").child(imageName)

        // Firebase Auth !!!
        val email = Singelton.email
        val password = Singelton.password

        auth.createUserWithEmailAndPassword(email!!, password!!).addOnSuccessListener(this) {
            // Kayit olma Basarili

            if (selectedPicture != null){
                // foto yukleme islmei
                imageReferance.putFile(selectedPicture!!).addOnSuccessListener {
                    val uploadPictureReference =
                        firebaseStorage.reference.child("profile_photos").child(imageName)
                    uploadPictureReference.downloadUrl.addOnSuccessListener {
                        val downloadURL = it.toString()




                    }.addOnFailureListener {
                        binding.registerButton.isEnabled = true

                    }

                }

            }

        }.addOnFailureListener {
            // Kayit olma Basarisiz
            binding.registerButton.isEnabled = true

        }


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
