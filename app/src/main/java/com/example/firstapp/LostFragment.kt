package com.example.firstapp

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.ContentView
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_lost.view.*
import androidx.recyclerview.widget.RecyclerView
import com.example.firstapp.databinding.ActivityMainBinding
import com.example.firstapp.databinding.FragmentLostBinding
import com.example.firstapp.databinding.SignupPageBinding
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_lost.*

class LostFragment : Fragment(){
    private lateinit var binding: FragmentLostBinding
    private lateinit var dbref : DatabaseReference
    private lateinit var itemRecyclerView: RecyclerView
    private lateinit var itemArrayList: ArrayList<RetrivingLostData>

//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        binding = FragmentLostBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//        val bind = FragmentLostBinding.inflate(layoutInflater)
//        bind.button3.setOnClickListener{
//            val intent = Intent (this@LostFragment.requireContext(),InfoLostItem::class.java)
//            startActivity(intent)
//        }
//
//        val layoutManager = LinearLayoutManager(context)
//        itemRecyclerView = view.findViewById(androidx.appcompat.R.id.list_item)
//        itemRecyclerView.layoutManager = layoutManager
//        itemRecyclerView.setHasFixedSize(true)
//
//
//        itemArrayList = arrayListOf<LostData>()
//        getUserdata()
//
//    }

    //@SuppressLint("UseRequireInsteadOfGet")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View?{
        //val bind = FragmentLostBinding.inflate(layoutInflater)
        val view = inflater.inflate(R.layout.fragment_lost, container, false)
        // Inflate the layout for this fragment

        view.button3.setOnClickListener{
            val intent = Intent (this@LostFragment.requireContext(),InfoLostItem::class.java)
            startActivity(intent)
        }
//
//        view.button5.setOnClickListener{
//            val intent = Intent(this@LostFragment.requireContext(),Registeredlostitem::class.java)
//            startActivity(intent)
//        }

        val layoutManager = LinearLayoutManager(context)
        itemRecyclerView = view.findViewById(R.id.recycyclerview1)
        itemRecyclerView.layoutManager = layoutManager
        itemRecyclerView.setHasFixedSize(true)

        itemArrayList = arrayListOf<RetrivingLostData>()
        getUserdata()

//        val layoutManager = LinearLayoutManager(context)
//        itemRecyclerView = view.findViewById(R.id.recycyclerview1)
//        itemRecyclerView.layoutManager = layoutManager
//        itemRecyclerView.setHasFixedSize(true)
//
//        itemArrayList = arrayListOf<RetrivingLostData>()
//        getUserdata()

//        itemRecyclerView = view!!.findViewById(androidx.transition.R.id.list_item)
//        itemRecyclerView.layoutManager = LinearLayoutManager(this)
//        itemRecyclerView.setHasFixedSize(true)

        //return inflater.inflate(R.layout.fragment_lost, container, false)
        return view
    }

    private fun getUserdata(){
        dbref = FirebaseDatabase.getInstance().getReference("LostItem")

        dbref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if( snapshot.exists() ){
                    for( itemSnapshot in snapshot.children ){
                        val item = itemSnapshot.getValue(RetrivingLostData::class.java )
                        itemArrayList.add(item!!)
                    }
                    val adapter = Adaptar1(itemArrayList)
                    itemRecyclerView.adapter =  adapter
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
    }

}