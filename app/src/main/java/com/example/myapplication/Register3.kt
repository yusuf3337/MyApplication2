package com.example.myapplication

import android.Manifest
import android.app.AlertDialog
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
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.myapplication.Singelton.Singelton
import com.example.myapplication.databinding.ActivityRegister3Binding
import com.example.myapplication.loginScreen.LoginPage
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import java.util.UUID

class Register3 : AppCompatActivity() {

    private lateinit var binding: ActivityRegister3Binding
    private lateinit var activityResultLauncher: ActivityResultLauncher<Intent>
    private lateinit var permissionLauncher: ActivityResultLauncher<String>
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
        val singletonPhone = Singelton.phone
        val singletonEmail = Singelton.email

        binding.surnameSingelton.isEnabled = false
        binding.phoneSingelton.isEnabled = false
        binding.nameSingelton.isEnabled = false
        binding.emailSingelton.isEnabled = false

        binding.surnameSingelton.setText(singletonSurname)
        binding.phoneSingelton.setText(singletonPhone)
        binding.emailSingelton.setText(singletonEmail)
        binding.nameSingelton.setText(singletonName)

        registerLauncher()
    }

    fun selectImage(view: View) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_MEDIA_IMAGES) != PackageManager.PERMISSION_GRANTED) {

            // Android izin mantığı
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_MEDIA_IMAGES)) {
                Snackbar.make(view, "Gallery için izin gereklidir", Snackbar.LENGTH_INDEFINITE)
                    .setAction("İzni Ver") {
                        // İzin talep et
                        permissionLauncher.launch(Manifest.permission.READ_MEDIA_IMAGES)
                    }.show()
            } else {
                // İzin talep et
                permissionLauncher.launch(Manifest.permission.READ_MEDIA_IMAGES)
            }
        } else {
            val intentToGallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            activityResultLauncher.launch(intentToGallery)
        }
    }

    // 3 adımlı izin
    private fun registerLauncher() {
        activityResultLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult(),
            ActivityResultCallback { result ->
                if (result.resultCode == RESULT_OK) {
                    val intentFromResult = result.data
                    if (intentFromResult != null) {
                        selectedPicture = intentFromResult.data
                        selectedPicture?.let { uri ->
                            binding.imageViewProfile.setImageURI(uri)
                        }
                    }
                }
            })

        permissionLauncher =
            registerForActivityResult(ActivityResultContracts.RequestPermission()) { result ->
                if (result) {
                    // İzin verildi
                    val intentToGallery =
                        Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                    activityResultLauncher.launch(intentToGallery)
                } else {
                    Toast.makeText(this, "İzin gerekli", Toast.LENGTH_LONG).show()
                }
            }
    }

    fun goSuccessPage(view: View) {
        upload()
    }

    fun upload() {
        binding.registerButton.isEnabled = false

        if (selectedPicture != null) {
            createUserAndUploadImage()
        } else {
            showAlertDialog("Hata", "Lütfen Fotoğraf Seçiniz")
        }
    }

    private fun createUserAndUploadImage() {
        val uuid = UUID.randomUUID()
        val imageName = "${Singelton.name + Singelton.surname}$uuid.jpg"
        val photoDatabase = firebaseStorage.reference
        val imageReference = photoDatabase.child("profile_photos").child(imageName)

        val email = Singelton.email
        val password = Singelton.password

        auth.createUserWithEmailAndPassword(email!!, password!!)
            .addOnSuccessListener {
                uploadImage(imageReference)
            }
            .addOnFailureListener { exception ->
                showAlertDialog(
                    "Hata",
                    "Kayıt olma işleminde bir aksaklık oldu! ${exception.localizedMessage}"
                )
                binding.registerButton.isEnabled = true
            }
    }

    private fun uploadImage(imageReference: StorageReference) {
        imageReference.putFile(selectedPicture!!)
            .addOnSuccessListener {
                saveUserDataToFirestore(imageReference)
            }
            .addOnFailureListener { exception ->
                showAlertDialog(
                    "Hata",
                    "Fotoğraf karşıya yüklenemedi, Daha Sonra Tekrar Deneyiniz! ${exception.localizedMessage}"
                )
                binding.registerButton.isEnabled = true
            }
    }

    private fun saveUserDataToFirestore(imageReference: StorageReference) {
        imageReference.downloadUrl
            .addOnSuccessListener { uri ->
                val downloadURL = uri.toString()
                val userMap = hashMapOf<String, Any>()
                userMap["downloadURL"] = downloadURL
                userMap["email"] = Singelton.email!!
                userMap["name"] = Singelton.name!!
                userMap["surname"] = Singelton.surname!!
                userMap["age"] = Singelton.age!!
                userMap["username"] = Singelton.username!!
                userMap["password"] = Singelton.password!!
                userMap["phone"] = Singelton.phone!!
                userMap["CreateDateUser"] = com.google.firebase.Timestamp.now()
                userMap["universityDepartment"] = Singelton.universitydepartment!!
                userMap["universityYears"] = Singelton.universityyear!!
                userMap["gender"] = Singelton.gender!!

                val uuid = UUID.randomUUID()
                val customDocumentName = Singelton.name + Singelton.surname + uuid
                firebaseDB.collection("userInformation").document(customDocumentName)
                    .set(userMap)
                    .addOnSuccessListener {
                        sendEmailVerification()
                    }
                    .addOnFailureListener { exception ->
                        showAlertDialog(
                            "Hata",
                            "Kullanıcı bilgileri Kaydedilmedi! ${exception.localizedMessage}"
                        )
                        binding.registerButton.isEnabled = true
                    }
            }
    }

    private fun sendEmailVerification() {
        val user = auth.currentUser
        user?.sendEmailVerification()
            ?.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    showAlertDialogSuccess(
                        "Başarılı",
                        "Kayıt işleminiz Başarılı bir şekilde Gerçekleşmiştir! E-postanıza Gelen onay linki ile devam edebilirsiniz"
                    )
                } else {
                    showAlertDialog("Hata", "Kayıt işleminiz Gerçekleşmemiştir!")
                }
            }
    }

    private fun showAlertDialog(title: String, message: String) {
        if (!isFinishing) {
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

    private fun showAlertDialogSuccess(title: String, message: String) {
        AlertDialog.Builder(this)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton("Tamam") { dialog, _ ->
                val intent = Intent(this@Register3, LoginPage::class.java)
                startActivity(intent)
            }
            .create()
            .show()
    }
}
