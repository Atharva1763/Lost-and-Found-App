package com.example.firstapp

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.firstapp.databinding.ActivityRegisteredlostitemBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class Registeredlostitem : AppCompatActivity() {
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityRegisteredlostitemBinding
    private lateinit var dbref : DatabaseReference
    private lateinit var itemRecyclerView: RecyclerView
    private lateinit var itemArrayList: ArrayList<RetrivingLostData>
    private lateinit var mitem : Adaptar1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRegisteredlostitemBinding.inflate(layoutInflater)
        setContentView(binding.root)

        itemRecyclerView = findViewById(R.id.recycyclerview1)
        itemArrayList = arrayListOf<RetrivingLostData>()
        itemRecyclerView.layoutManager = LinearLayoutManager(this)
        itemRecyclerView.setHasFixedSize(true)


//      setSupportActionBar(binding.toolbar)
        //setup()
        getUserdata()


    }

//        val navController = findNavController(R.id.nav_host_fragment_content_registeredlostitem)
//        appBarConfiguration = AppBarConfiguration(navController.graph)
//        setupActionBarWithNavController(navController, appBarConfiguration)
//
//        binding.fab.setOnClickListener { view ->
//            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                .setAction("Action", null).show()
//        }

//    override fun onSupportNavigateUp(): Boolean {
//        val navController = findNavController(R.id.nav_host_fragment_content_registeredlostitem)
//        return navController.navigateUp(appBarConfiguration)
//                ||super.onSupportNavigateUp()
//    }




    private fun getUserdata(){

        dbref = FirebaseDatabase.getInstance().getReference("LostItem")

        dbref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if( snapshot.exists() ){
                    for( itemSnapshot in snapshot.children ){
                        val storeitem = itemSnapshot.getValue(RetrivingLostData::class.java )
                        itemArrayList.add(storeitem!!)
                    }
                    itemRecyclerView.adapter =  Adaptar1(itemArrayList)
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
    }
}