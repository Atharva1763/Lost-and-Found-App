package com.example.firstapp

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.firstapp.databinding.FragmentLostBinding
import com.example.firstapp.databinding.FragmentProfileBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.fragment_profile.view.*

class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding
    private lateinit var dbref : DatabaseReference
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        view.mypost.setOnClickListener{
            val intent = Intent(this@ProfileFragment.requireContext(),MyPost::class.java)
            startActivity(intent)
        }

        view.changepassword.setOnClickListener{
            val intent = Intent(this@ProfileFragment.requireContext(),ChangePassword::class.java)
            startActivity(intent)
        }

        auth = FirebaseAuth.getInstance()
        val curuser = auth.currentUser

        showprofilepic(curuser)
        return view
    }

    private fun showprofilepic(curuser: FirebaseUser?) {
        val userid = curuser?.uid

        dbref = FirebaseDatabase.getInstance().getReference("NewUser")



    }
}