package com.example.firstapp

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.ui.AppBarConfiguration
import com.example.firstapp.databinding.ActivityInfoLostItemBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_info_lost_item.*
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.internal.cache.DiskLruCache.Snapshot
import java.util.*
import kotlin.random.Random

class InfoLostItem : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityInfoLostItemBinding
    private lateinit var storage: FirebaseStorage
    private lateinit var dbRef : DatabaseReference
    private lateinit var database : FirebaseDatabase
    private lateinit var selImg : Uri
    private lateinit var dialog: AlertDialog.Builder
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var user: FirebaseUser

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)

        binding = ActivityInfoLostItemBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val Randomid = kotlin.random.Random.nextInt(1000 ,9999)

        user = FirebaseAuth.getInstance().currentUser!!
        val uid = user.uid
        dialog = AlertDialog.Builder(this)
            .setMessage("Uploading Data...")
            .setCancelable(false)
        storage = FirebaseStorage.getInstance()
        database = FirebaseDatabase.getInstance()
        dbRef = FirebaseDatabase.getInstance().getReference("LostItem")
        binding.lostitemimage.setOnClickListener{
            val intent = Intent()
            intent.action = Intent.ACTION_GET_CONTENT
            intent.type = "image/*"
            startActivityForResult(intent, 1)
        }

        binding.upload1.setOnClickListener{
            if(binding.Item.text!!.isEmpty()){
                Toast.makeText(this,"Please enter the item that you lost",Toast.LENGTH_SHORT).show()
            }
            else if(binding.PersonName.text!!.isEmpty()){
                Toast.makeText(this,"Please enter your Name",Toast.LENGTH_SHORT).show()
            }else if(binding.Phone.text!!.isEmpty()){
                Toast.makeText(this,"Please enter your Phone Number",Toast.LENGTH_SHORT).show()
            }else if(binding.wherelost.text!!.isEmpty()){
                Toast.makeText(this,"Please enter the location where you found the item",Toast.LENGTH_SHORT).show()
            }else{
                val reference = storage.reference.child("LostItem").child(Randomid.toString())
                val uploaddata = reference.putFile(selImg)
                uploaddata.addOnCompleteListener {
                    reference.downloadUrl.addOnSuccessListener { task ->
                        val user = LostData(uid,binding.Item.text.toString() ,binding.PersonName.text.toString(), binding.Phone.text.toString(), binding.wherelost.text.toString(), binding.editTextTextPersonName7.text.toString(), task.toString())
                        dbRef.child(Randomid.toString()).setValue(user).addOnSuccessListener {
                            Toast.makeText(this, "Data Inserted", Toast.LENGTH_SHORT).show()
                            startActivity(Intent(this, SignIn_page::class.java))
                            finish()
                        }
                    }
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(data != null){
            if(data.data != null){
                selImg = data.data!!

                //Glide.with(InfoLostItem.this).load(selImg).into(lostitemimage);
                binding.lostitemimage.setImageURI(selImg)
            }
        }
    }
}