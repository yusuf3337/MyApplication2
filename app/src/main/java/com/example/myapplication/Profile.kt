package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import com.example.myapplication.databinding.FragmentProfileBinding
import com.example.myapplication.databinding.FragmentSearchBinding
import com.example.myapplication.loginScreen.MainActivity
import com.google.firebase.auth.FirebaseAuth

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Profile.newInstance] factory method to
 * create an instance of this fragment.
 */
class Profile : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var binding: FragmentProfileBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding?.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.exitButton?.setOnClickListener {
            // Burada butona tıklandığında yapılacak işlemleri ekleyebilirsiniz.
            showAlertDialog("Çıkış", "Çıkış yapmak istediğinize emin misiniz?")
        }

    }

    private fun showAlertDialog(title: String, message: String) {
        AlertDialog.Builder(requireContext())
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton("OK") { dialog, _ ->
                FirebaseAuth.getInstance().signOut()
                val intent = Intent(requireContext(), MainActivity::class.java)
                startActivity(intent)
                activity?.finish()
            }
            .setNegativeButton("NO") {dialog, _ ->

            }
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        // binding nesnesini kaldırmayı unutmayın
        binding = null
    }
    companion object {

        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Profile().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}