package com.example.firstapp

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.firstapp.databinding.ActivityChangePasswordBinding
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_change_password.*
import kotlinx.android.synthetic.main.fragment_profile.*

class ChangePassword : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityChangePasswordBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)

        binding = ActivityChangePasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        savechangedpassword.setOnClickListener{
            changePassword()
        }
    }

    private fun changePassword(){
        if( prevpass.text.isEmpty() ){
            Toast.makeText(this,"Please Enter Your Previous Password",Toast.LENGTH_SHORT).show()
        } else if(newpass.text.isEmpty()){
            Toast.makeText(this,"Please Enter New Password",Toast.LENGTH_SHORT).show()
        } else if(confnewpass.text.isEmpty()){
            Toast.makeText(this,"Please Enter Confirmed New Password",Toast.LENGTH_SHORT).show()
        } else if(newpass.text.toString() != confnewpass.text.toString()){
            Toast.makeText(this,"New Password and Confirmed New Password don't match",Toast.LENGTH_SHORT).show()
        }else{
            val user  = auth.currentUser
            if(user!=null){
                val credential = EmailAuthProvider
                    .getCredential(user.email!!, prevpass.text.toString())

// Prompt the user to re-provide their sign-in credentials
                user.reauthenticate(credential)
                    .addOnCompleteListener {
                        if(it.isSuccessful){
                            Toast.makeText(this,"Re-Authentication Successful",Toast.LENGTH_SHORT).show()
                            user!!.updatePassword(newpass.text.toString())
                                .addOnCompleteListener { task ->
                                    if (task.isSuccessful) {
                                        Toast.makeText(this,"Password Changed Successfully",Toast.LENGTH_SHORT).show()
                                        auth.signOut()
                                        startActivity(Intent(this,MainActivity::class.java))
                                        finish()
                                    }
                                }
                        }
                        else{
                            Toast.makeText(this,"Re-Authentication Failed",Toast.LENGTH_SHORT).show()
                        }
                    }

            }
            else{
                startActivity(Intent(this,MainActivity::class.java))
                finish()
            }
        }

    }
}