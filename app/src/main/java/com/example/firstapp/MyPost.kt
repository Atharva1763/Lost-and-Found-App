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
import com.example.firstapp.databinding.ActivityMyPostBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.getValue

class MyPost : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMyPostBinding
    private lateinit var dbref : DatabaseReference
    private lateinit var itemlostRecyclerView: RecyclerView
    private lateinit var auth: FirebaseAuth
    private lateinit var itemfoundRecyclerView: RecyclerView
    private lateinit var itemlostArrayList: ArrayList<RetrivingLostData>
    private lateinit var itemfoundArrayList: ArrayList<RetrivingLostData>
    private lateinit var mitem : Adaptar1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMyPostBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = FirebaseAuth.getInstance()

        itemlostRecyclerView = findViewById(R.id.recyclerView3)
        itemlostArrayList = arrayListOf<RetrivingLostData>()
        itemlostRecyclerView.layoutManager = LinearLayoutManager(this)
        itemlostRecyclerView.setHasFixedSize(true)
        //setSupportActionBar(binding.toolbar)
        getlostdata()
//        val navController = findNavController(R.id.nav_host_fragment_content_my_post)
//        appBarConfiguration = AppBarConfiguration(navController.graph)
//        setupActionBarWithNavController(navController, appBarConfiguration)
//
//        binding.fab.setOnClickListener { view ->
//            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                .setAction("Action", null).show()
//        }
        itemfoundRecyclerView = findViewById(R.id.recyclerView4)
        itemfoundArrayList = arrayListOf<RetrivingLostData>()
        itemfoundRecyclerView.layoutManager = LinearLayoutManager(this)
        itemfoundRecyclerView.setHasFixedSize(true)

        getfounddata()
    }

    private fun getlostdata(){
        dbref = FirebaseDatabase.getInstance().getReference("LostItem")

        dbref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if( snapshot.exists() ){
                    for( itemSnapshot in snapshot.children ) {
                        if(itemSnapshot.child("id").getValue().toString() == auth.currentUser?.uid.toString()){
                            val storeitem = itemSnapshot.getValue(RetrivingLostData::class.java )
                            itemlostArrayList.add(storeitem!!)
                        }
                    }
                    itemlostRecyclerView.adapter =  Adaptar1(itemlostArrayList)
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
    }

    private fun getfounddata(){

        dbref = FirebaseDatabase.getInstance().getReference("FoundItem")

        dbref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if( snapshot.exists() ){
                    for( itemSnapshot in snapshot.children ) {
                        if(itemSnapshot.child("id").getValue().toString() == auth.currentUser?.uid.toString()){
                            val storeitem = itemSnapshot.getValue(RetrivingLostData::class.java )
                            itemfoundArrayList.add(storeitem!!)
                        }
                    }
                    itemfoundRecyclerView.adapter =  Adaptar1(itemfoundArrayList)
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
    }
//    override fun onSupportNavigateUp(): Boolean {
//        val navController = findNavController(R.id.nav_host_fragment_content_my_post)
//        return navController.navigateUp(appBarConfiguration)
//                || super.onSupportNavigateUp()
//    }
}