package com.example.firstapp

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.firstapp.databinding.FragmentFoundBinding
import com.google.firebase.database.*

import com.example.firstapp.databinding.FragmentLostBinding
import com.google.firebase.database.DatabaseReference
import kotlinx.android.synthetic.main.fragment_found.view.*

class FoundFragment : Fragment() {

    private lateinit var binding: FragmentLostBinding
    private lateinit var dbref : DatabaseReference
    private lateinit var itemRecyclerView: RecyclerView
    private lateinit var itemArrayList: ArrayList<RetrivingLostData>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        //val bind = FragmentFoundBinding.inflate(layoutInflater)
        val view = inflater.inflate(R.layout.fragment_found, container, false)

        // Inflate the layout for this fragment
        view.button4.setOnClickListener{
            val intent = Intent (this@FoundFragment.requireContext(),InfoFoundItem::class.java)
            startActivity(intent)
        }
        //return inflater.inflate(R.layout.fragment_lost, container, false)

        val layoutManager = LinearLayoutManager(context)
        itemRecyclerView = view.findViewById(R.id.recyclerview2)
        itemRecyclerView.layoutManager = layoutManager
        itemRecyclerView.setHasFixedSize(true)

        itemArrayList = arrayListOf<RetrivingLostData>()
        getUserdata()



        return view
    }

    private fun getUserdata(){
        dbref = FirebaseDatabase.getInstance().getReference("FoundItem")

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