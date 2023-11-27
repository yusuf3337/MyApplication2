
package com.example.myapplication.loginScreen

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.myapplication.Add
import com.example.myapplication.Home
import com.example.myapplication.Profile
import com.example.myapplication.R
import com.example.myapplication.Search
import com.example.myapplication.Settings
import com.example.myapplication.Singelton
import com.example.myapplication.databinding.ActivityHomeScreenBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class HomeScreen : AppCompatActivity() {

    private lateinit var binding: ActivityHomeScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        replaceFragment(Home())
        getUserData()

        binding.bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.Home -> replaceFragment(Home())
                R.id.Search -> replaceFragment(Search())
                R.id.Add -> replaceFragment(Add())
                R.id.Profile -> replaceFragment(Profile())
                R.id.Settings -> replaceFragment(Settings())
            }
            true
        }

    }

    fun getUserData() {
        val firebaseDB = FirebaseFirestore.getInstance()
        val currentUserEmail = FirebaseAuth.getInstance().currentUser?.email

        if (currentUserEmail != null) {
            firebaseDB.collection("userInformation")
                .whereEqualTo("email", currentUserEmail)
                .get()
                .addOnSuccessListener { snapshot ->
                    if (!snapshot.isEmpty) {
                        for (document in snapshot.documents) {
                            val email = document.getString("email")
                            val name = document.getString("name")
                            val surname = document.getString("surname")
                            val phoneNumber = document.getString("phoneNumber")
                            val username = document.getString("username")

                            if (email != null) {
                                Singelton.email = email
                                println(email)
                            }

                            if (name != null) {
                                Singelton.name = name
                                println(name)
                            }

                            if (surname != null) {
                                Singelton.surname = surname
                                println(surname)
                            }

                            if (phoneNumber != null) {
                                Singelton.phone = phoneNumber
                            }

                            if (username != null) {
                                Singelton.username = username
                            }
                        }
                    }
                }
                .addOnFailureListener { exception ->
                    // hata
                }
        }
    }

    private fun replaceFragment(fragment: Fragment){

        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout,fragment)
        fragmentTransaction.commit()


    }
}
