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
import com.google.firebase.storage.StorageReference
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
        upload()
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
        if (selectedPicture != null){
        auth.createUserWithEmailAndPassword(email!!, password!!).addOnSuccessListener(this) {
            // Kayit olma Basarili

                // foto yukleme islmei
                imageReferance.putFile(selectedPicture!!).addOnSuccessListener {
                    val uploadPictureReference =
                        firebaseStorage.reference.child("profile_photos").child(imageName)
                    uploadPictureReference.downloadUrl.addOnSuccessListener {
                        val downloadURL = it.toString()

                        val userMap = hashMapOf<String, Any>()
                        userMap.put("downloadURL", downloadURL)
                        userMap.put("email", Singelton.email!!)
                        userMap.put("name", Singelton.name!!)
                        userMap.put("surname", Singelton.surname!!)
                        userMap.put("age", Singelton.age!!)
                        userMap.put("username", Singelton.username!!)
                        userMap.put("password", Singelton.password!!)
                        userMap.put("phone", Singelton.phone!!)
                        userMap.put("CreateDateUser", com.google.firebase.Timestamp.now())
                        userMap.put("universityDepartment",Singelton.universitydepartment!!)
                        userMap.put("universityYears", Singelton.universityyear!!)
                        //userMap.put("gender", Singelton.gender!!)

                        val customDocumentName = Singelton.name + Singelton.surname

                        firebaseDB.collection("userInformation").document(customDocumentName).set(userMap).addOnSuccessListener {
                            // Kullaniciya Mail Gondermek!
                            val user = auth.currentUser
                            user?.sendEmailVerification()?.addOnCompleteListener { task ->
                                if (task.isSuccessful){
                                    showAlertDialog("Basarili", "Kayit isleminiz Basarilir bir sekilde Gerceklesmistir! E-postaniza Gelen onay linki ile devam edebilirsiniz")
                                }else{
                                    showAlertDialog("Hata", "Kayit isleminiz Gerceklesmemistir!")
                                }
                            }


                        }.addOnFailureListener {
                            showAlertDialog("Hata", "Kullanici bilgileri Kaydedilmedi!")
                        }




                    }.addOnFailureListener {
                        // Foto Link Indirme Basarisiz!
                        binding.registerButton.isEnabled = true

                    }

                }.addOnFailureListener {
                    // Fotograf yukleme Basarisiz!
                    showAlertDialog("Hata", "Fotograf Karsiya yuklenemedi, Daha Sonra Tekrar Deneyiniz! ")
                }


            // Mail Gondermek!


            }.addOnFailureListener {
                // auth Basarisiz    !
                showAlertDialog("Hata", "Kayit olma isleminde bir aksaklik oldu!")
            }

        }else{
            showAlertDialog("Hata", "Lutfen Fotograf Seciniz")
        }


    }

    fun upload2() {
        binding.registerButton.isEnabled = false

        if (selectedPicture != null) {
            createUserAndUploadImage()
        } else {
            showAlertDialog("Hata", "Lütfen Fotograf Seciniz")
        }
    }

    private fun createUserAndUploadImage() {
        val uuid = UUID.randomUUID()
        val imageName = "${Singelton.name + Singelton.surname}$uuid.jpg"
        val fotoDatabase = firebaseStorage.reference
        val imageReferance = fotoDatabase.child("profile_photos").child(imageName)

        val email = Singelton.email
        val password = Singelton.password

        auth.createUserWithEmailAndPassword(email!!, password!!)
            .addOnSuccessListener {
                // Kullanıcı oluşturuldu, şimdi fotoğrafı yükle
                uploadImage(imageReferance)
            }
            .addOnFailureListener {
                showAlertDialog("Hata", "Kayit olma isleminde bir aksaklik oldu!")
                binding.registerButton.isEnabled = true
            }
    }

    private fun uploadImage(imageReference: StorageReference) {
        imageReference.putFile(selectedPicture!!)
            .addOnSuccessListener {
                // Fotoğraf yüklendi, şimdi Firestore'a kullanıcı bilgilerini kaydet
                saveUserDataToFirestore(imageReference)
            }
            .addOnFailureListener {
                showAlertDialog("Hata", "Fotograf Karsiya yuklenemedi, Daha Sonra Tekrar Deneyiniz!")
                binding.registerButton.isEnabled = true
            }
    }

    private fun saveUserDataToFirestore(imageReference: StorageReference) {
        imageReference.downloadUrl
            .addOnSuccessListener { uri ->
                val downloadURL = uri.toString()
                val userMap = hashMapOf<String, Any>()

                // Null kontrolü yaparak Singleton özelliklerini ekleyin
                Singelton.email?.let { userMap.put("email", it) }
                Singelton.name?.let { userMap.put("name", it) }
                Singelton.surname?.let { userMap.put("surname", it) }
                Singelton.age?.let { userMap.put("age", it) }
                Singelton.username?.let { userMap.put("username", it) }
                Singelton.password?.let { userMap.put("password", it) }
                Singelton.phone?.let { userMap.put("phone", it) }
                Singelton.universitydepartment?.let { userMap.put("universityDepartment", it) }
                Singelton.universityyear?.let { userMap.put("universityYears", it) }
                Singelton.gender?.let { userMap.put("gender", it) }
                userMap.put("downloadURL", downloadURL)

                val customDocumentName = Singelton.name + Singelton.surname
                firebaseDB.collection("userInformation").document(customDocumentName)
                    .set(userMap)
                    .addOnSuccessListener {
                        sendEmailVerification()
                    }
                    .addOnFailureListener {
                        showAlertDialog("Hata", "Kullanici bilgileri Kaydedilmedi!")
                        binding.registerButton.isEnabled = true
                    }
            }
    }

    private fun sendEmailVerification() {
        val user = auth.currentUser
        user?.sendEmailVerification()
            ?.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    showAlertDialog("Basarili", "Kayit isleminiz Basarilir bir sekilde Gerceklesmistir! E-postaniza Gelen onay linki ile devam edebilirsiniz")
                } else {
                    showAlertDialog("Hata", "Kayit isleminiz Gerceklesmemistir!")
                }
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
