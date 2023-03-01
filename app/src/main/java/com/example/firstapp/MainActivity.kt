package com.example.firstapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button;
import android.content.Intent;
import android.view.View;
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import com.example.firstapp.databinding.ActivityMainBinding
//import com.example.firstapp.databinding.ActivitySignInPageBinding
import com.google.firebase.auth.FirebaseAuth
import java.lang.reflect.Modifier

class MainActivity : AppCompatActivity(){

    private lateinit var binding: ActivityMainBinding
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        //setContentView(R.layout.activity_main)
        setContentView(binding.root)
        /*setContent {
            Box(
                modifier = androidx.compose.ui.Modifier
                    .fillMaxSize()
                    .background()
            )
        }*/
        firebaseAuth = FirebaseAuth.getInstance()
        binding.signup.setOnClickListener {
            val intent = Intent(this, Signup_page::class.java)
            startActivity(intent)
        }

        binding.forgotpassword.setOnClickListener{
            val intent = Intent(this, Forgotpassword_page::class.java)
            startActivity(intent)
        }

        binding.signin.setOnClickListener{
            val email = binding.emailid.text.toString()
            val pass = binding.editTextTextPassword.text.toString()

            if( email.isNotEmpty() && pass.isNotEmpty() ){
                    firebaseAuth.signInWithEmailAndPassword(email,pass).addOnCompleteListener{
                        if(it.isSuccessful){
                            if(firebaseAuth.currentUser!!.isEmailVerified == true){
                                val intent = Intent(this,SignIn_page::class.java)
                                startActivity(intent)
                            }
                            else{
                                Toast.makeText(this,"Email is not Verified", Toast.LENGTH_SHORT).show()
                            }
                        }else{
                            Toast.makeText(this,it.exception.toString(), Toast.LENGTH_SHORT).show()
                        }
                    }
                }

            else if(email.isEmpty() && pass.isNotEmpty()){
                Toast.makeText(this,"Please enter an Email", Toast.LENGTH_SHORT).show()
            }

            else if(pass.isEmpty() && email.isNotEmpty()){
                Toast.makeText(this,"Please enter a Password", Toast.LENGTH_SHORT).show()
            }

            else{
                Toast.makeText(this,"Please enter Email and Password", Toast.LENGTH_SHORT).show()
            }
        }
        }
    }

//    fun OpenSignUpPage(view: View){
//        val intent = Intent(this,Signup_page::class.java )
//        startActivity(intent)
//    }

//    fun OpenSignInPage(view: View) {
//        val intent = Intent(this,SignIn_page::class.java )
//        startActivity(intent)
//    }

//    fun OpenForgetPasswordPage(view: View) {
//        val intent = Intent(,Forgotpassword_page::class.java )
//        startActivity(intent)
//    }
