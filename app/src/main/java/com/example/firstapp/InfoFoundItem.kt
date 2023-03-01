package com.example.firstapp

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.firstapp.databinding.ActivityInfoFoundItemBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import java.util.*

class InfoFoundItem : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityInfoFoundItemBinding
    private lateinit var storage: FirebaseStorage
    private lateinit var dbRef : DatabaseReference
    private lateinit var selImg : Uri
    private lateinit var dialog: AlertDialog.Builder
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var user: FirebaseUser

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityInfoFoundItemBinding.inflate(layoutInflater)
        setContentView(binding.root)

        user = FirebaseAuth.getInstance().currentUser!!
        val uid = user.uid
        val Randomid = kotlin.random.Random.nextInt(1000 ,9999)

        dialog = AlertDialog.Builder(this)
            .setMessage("Uploading Data...")
            .setCancelable(false)
        storage = FirebaseStorage.getInstance()
        dbRef = FirebaseDatabase.getInstance().getReference("FoundItem")
        binding.founditemimage.setOnClickListener{
            val intent = Intent()
            intent.action = Intent.ACTION_GET_CONTENT
            intent.type = "image/*"
            startActivityForResult(intent, 1)
        }

        binding.upload1.setOnClickListener{
            if(binding.Item.text!!.isEmpty()){
                Toast.makeText(this,"Please enter the item that you lost",Toast.LENGTH_SHORT).show()
            }
            else if(binding.PersonfoundName.text!!.isEmpty()){
                Toast.makeText(this,"Please enter your Name", Toast.LENGTH_SHORT).show()
            }else if(binding.Phonefound.text!!.isEmpty()){
                Toast.makeText(this,"Please enter your Phone Number", Toast.LENGTH_SHORT).show()
            }else if(binding.wherefound.text!!.isEmpty()){
                Toast.makeText(this,"Please enter the location where you found the item", Toast.LENGTH_SHORT).show()
            }else{
                val reference = storage.reference.child("FoundItem").child(Randomid.toString())
                reference.putFile(selImg).addOnCompleteListener{
                    if(it.isSuccessful){
                        reference.downloadUrl.addOnSuccessListener {task->
                            val user = LostData(uid, binding.Item.text.toString(),binding.PersonfoundName.text.toString(),binding.Phonefound.text.toString(),binding.wherefound.text.toString(), binding.editTextTextPersonName7.text.toString(),task.toString())
                            dbRef.child(Randomid.toString()).setValue(user).addOnSuccessListener {
                                Toast.makeText(this,"Data Inserted", Toast.LENGTH_SHORT).show()
                                startActivity(Intent(this,SignIn_page::class.java))
                                finish()
                            }
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

                binding.founditemimage.setImageURI(selImg)
            }
        }
    }

}