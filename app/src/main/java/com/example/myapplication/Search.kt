package com.example.myapplication

import SearchRecyclerAdapter
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.Informations.AdInformationOne
import com.example.myapplication.adapter.addClass
import com.example.myapplication.adapter.searchClass
import com.example.myapplication.databinding.FragmentAddBinding
import com.example.myapplication.databinding.FragmentSearchBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Search.newInstance] factory method to
 * create an instance of this fragment.
 */

class Search : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var binding: FragmentSearchBinding? = null
    private val searchList = ArrayList<searchClass>() // RecyclerView için bir veri listesi oluşturun
    private lateinit var searchRecyclerAdapter: SearchRecyclerAdapter

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
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    //// BUTTONLAR BURAYA EKLENECEK!!!
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val object1 = searchClass().apply { searchImage = "kiralikevpng"; searchTitle = "Kiralık Ev" ;searchCategory = "Kiralık"}
        val object2 = searchClass().apply { searchImage = "satilikevpng"; searchTitle = "Satılık Ev" ;searchCategory = "Satılık"}
        val object3 = searchClass().apply { searchImage = "aktifprojepng"; searchTitle = "Aktif Projeler" ;searchCategory = "Aktif Projeler"}
        val object4 = searchClass().apply { searchImage = "satilikevpng"; searchTitle = "Ev Arkadaşı" ;searchCategory = "Ev Arkadaşı"}
        val object5 = searchClass().apply { searchImage = "yurtsearchpng"; searchTitle = "Yurt" ;searchCategory = "Yurt"}


        searchList.add(object1)
        searchList.add(object2)
        searchList.add(object3)
        searchList.add(object4)
        searchList.add(object5)

        binding?.searchRecylerView?.layoutManager = LinearLayoutManager(requireContext())
        searchRecyclerAdapter = SearchRecyclerAdapter(requireContext(), searchList)
        binding?.searchRecylerView?.adapter = searchRecyclerAdapter



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
         * @return A new instance of fragment Search.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Search().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}