package com.example.myapplication

import AddRecyclerAdapter
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.Informations.AdInformationOne
import com.example.myapplication.databinding.FragmentAddBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Add.newInstance] factory method to
 * create an instance of this fragment.
 */
class Add : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var binding: FragmentAddBinding? = null
    private val addList = ArrayList<addClass>() // RecyclerView için bir veri listesi oluşturun
    private lateinit var addRecyclerAdapter: AddRecyclerAdapter


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
        binding = FragmentAddBinding.inflate(inflater, container, false)
        return binding?.root
    }

    //// BUTTONLAR BURAYA EKLENECEK!!!
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val goToAdInformationOneButton = view.findViewById<Button>(R.id.evEkle)
        goToAdInformationOneButton.setOnClickListener {
            val intent = Intent(requireActivity(), AdInformationOne::class.java)
            startActivity(intent)
        }


        val object1 = addClass().apply { addImage = "kiralikpng"; addTitle = "Kiralık Ev İlanı Ver" }
        val object2 = addClass().apply { addImage = "satilikpng"; addTitle = "Satılık Ev İlanı Ver" }
        val object3 = addClass().apply { addImage = "yurtilanpng"; addTitle = "Yurt Devr, İlanı Ver" }
        val object4 = addClass().apply { addImage = "satilikpng"; addTitle = "Ev Arkadaşı Arıyorum" }

        addList.add(object1)
        addList.add(object2)
        addList.add(object3)
        addList.add(object4)

        binding?.addRecylerView?.layoutManager = LinearLayoutManager(requireContext())
        addRecyclerAdapter = AddRecyclerAdapter(requireContext(), addList)
        binding?.addRecylerView?.adapter = addRecyclerAdapter



        // BUTTON =

    }



    override fun onDestroyView() {
        super.onDestroyView()
        binding = null // Bellek sızıntısını önlemek için ViewBinding nesnesini null olarak ayarlayın
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Add.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Add().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
