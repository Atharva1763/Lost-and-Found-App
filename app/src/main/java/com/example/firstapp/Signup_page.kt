package com.example.firstapp

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import android.widget.Toast
import androidx.appcompat.app.AlertDialog

import com.example.firstapp.databinding.SignupPageBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage

import java.util.Date

class Signup_page : AppCompatActivity(){
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var binding: SignupPageBinding
    private lateinit var storage: FirebaseStorage
    private lateinit var dbRef : DatabaseReference
    private lateinit var selectedImg : Uri
    private lateinit var dialog: AlertDialog.Builder

    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)

        binding = SignupPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()
        dbRef = FirebaseDatabase.getInstance().getReference("NewUser")
        dialog = AlertDialog.Builder(this)
            .setMessage("Updating Profile...")
            .setCancelable(false)
        storage = FirebaseStorage.getInstance()

        binding.profilepic.setOnClickListener{
            val intent = Intent()
            intent.action = Intent.ACTION_GET_CONTENT
            intent.type = "image/*"
            startActivityForResult(intent, 1)
        }

        binding.button.setOnClickListener{
            val uid = dbRef.push().key!!
            //val uid = FirebaseAuth.getInstance().currentUser?.uid.toString()
            val name = binding.editTextTextPersonName3.text.toString()
            val rollnumber = binding.editTextTextPersonName4.text.toString()
            val phone = binding.editTextPhone.text.toString()
            val waphone = binding.editTextPhone2.text.toString()
            val email = binding.editTextTextEmailAddress.text.toString()
            val pass = binding.editTextTextPassword2.text.toString()
            val confirmPass = binding.editTextTextPassword3.text.toString()

            if(!emptycheck("name",name)) return@setOnClickListener
            if(!emptycheck("Roll Number",rollnumber)) return@setOnClickListener
            if(!emptycheck("phone",phone)) return@setOnClickListener
            if(!emptycheck("Whatsapp Phone",waphone))return@setOnClickListener
            if(!phonecheck(phone)) return@setOnClickListener
            if(!phonecheck(waphone)) return@setOnClickListener
            if(selectedImg == null){
                Toast.makeText(this, "Please select an Image for Profile Picture",Toast.LENGTH_SHORT).show()
            }

            if( email.isNotEmpty() && pass.isNotEmpty() && confirmPass.isNotEmpty()){
                if(pass == confirmPass){
                    firebaseAuth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener{ authResultTask ->
                        if (authResultTask.isSuccessful){
                            val reference = storage.reference.child("Profile").child(Date().time.toString())
                            reference.putFile(selectedImg).addOnCompleteListener {
                                if (it.isSuccessful){
//                                    firebaseAuth.currentUser.sendEmailVerification().addOnCompleteListener {
//
//                                    }
                                    reference.downloadUrl.addOnSuccessListener{
                                        if (uid != null){
                                            val uidmain= firebaseAuth.currentUser?.uid.toString()
                                            val newuser = NewUser(uidmain,name,rollnumber,phone,waphone,email,pass, it.toString())
                                            firebaseAuth.currentUser?.sendEmailVerification()?.addOnCompleteListener {task->
                                                if(task.isSuccessful){
                                                    dbRef.child(uid).setValue(newuser).addOnCompleteListener{
                                                        if(it.isSuccessful){
                                                            Toast.makeText(this,"User registered , Please Verify Your Email",Toast.LENGTH_SHORT).show()
                                                            val intent = Intent(this,MainActivity::class.java)
                                                            startActivity(intent)
                                                        }
                                                        else{
                                                            Toast.makeText(this,it.exception.toString(),Toast.LENGTH_SHORT).show()
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                        else{
                                            Toast.makeText(this,authResultTask.exception.toString(),Toast.LENGTH_SHORT).show()
                                        }
                                    }
                                }
                            }
                        }
                        else{
                            Toast.makeText(this,authResultTask.exception.toString(),Toast.LENGTH_SHORT).show()
                        }
                    }

                    //firebaseAuth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener{
                    //}
                }
                else{
                    Toast.makeText(this,"Password is not matching", Toast.LENGTH_SHORT).show()
                }
            }
        }
        //auth = Firebase.auth
    }
//    private fun uploadData(){
//        val reference = storage.reference.child("Profile").child(Date().time.toString())
//        reference.putFile(selectedImg).addOnCompleteListener{
//            if(it.isSuccessful){
//                reference.downloadUrl.addOnSuccessListener {  }
//            }
//        }
//    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(data != null){
            if(data.data != null)
            {
                selectedImg = data.data!!

                binding.profilepic.setImageURI(selectedImg)
            }
        }
    }

    private fun emptycheck(str1 : String, str : String): Boolean{
        if(str.isEmpty()){
            Toast.makeText(
                this,
                "Please enter your " + str1,
                Toast.LENGTH_SHORT
            ).show()
            return false
        }
        else{
            return true
        }

    }

    private fun phonecheck(str: String ): Boolean{
        if(str.length != 10){
            Toast.makeText(
                this,
                "Please enter a 10 digit phone number",
                Toast.LENGTH_SHORT
            ).show()
            return false
        }

        else{
            return true
        }
    }

//    private lateinit var name: EditText
//    private lateinit var rollnum: EditText
//    private lateinit var email: EditText
//    private lateinit var pass: EditText
//    private lateinit var conpass: EditText
//    private lateinit var num: EditText
//    private lateinit var wanum: EditText
//
//    private lateinit var savebtn: Button
//
//    private lateinit var dbRef: DatabaseReference
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//
//        name = findViewById(R.id.editTextTextPersonName3)
//        rollnum = findViewById(R.id.editTextTextPersonName4)
//        email = findViewById(R.id.editTextTextEmailAddress)
//        pass = findViewById(R.id.editTextTextPassword2)
//        conpass = findViewById(R.id.editTextTextPassword3)
//        num = findViewById(R.id.editTextPhone)
//        wanum = findViewById(R.id.editTextPhone2)
//        savebtn = findViewById(R.id.button)
//
//        dbRef = FirebaseDatabase.getInstance().getReference("NewUser")
//
//        savebtn.setOnClickListener{
//            saveuserdata()
//        }
//    }
//
//    private fun saveuserdata(){
//        val Uname = name.text.toString()
//        val Urollnum = rollnum.text.toString()
//        val Uemail = email.text.toString()
//        val Upass = pass.text.toString()
//        val Uconfpass= conpass.text.toString()
//        val Unum = num.text.toString()
//        val Uwanum = num.text.toString()
//
//
//    }
}

