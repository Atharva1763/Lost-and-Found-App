package com.example.firstapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.*
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.firstapp.databinding.ActivityMainBinding
import com.google.android.gms.common.data.DataBufferRef
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.signup__page.*
import java.lang.reflect.Modifier

class Forgotpassword_page : AppCompatActivity(){

    private lateinit var binding: ActivityMainBinding
    private lateinit var firebaseAuth: FirebaseAuth

    private lateinit var email: EditText
    private lateinit var dBRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        dBRef = FirebaseDatabase.getInstance().getReference("Email")

        //setContentView(R.layout.activity_forgotpassword_page)
        setContent {
            Image(
                painter = painterResource(id = R.drawable.background4),
                contentDescription = null,
                modifier = androidx.compose.ui.Modifier.fillMaxSize(),
                contentScale = ContentScale.FillBounds,
            )
            Column(
                modifier = androidx.compose.ui.Modifier
                    .padding(20.dp)
                    .fillMaxSize()
                    .height(40.dp),

                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                //Spacer(modifier = androidx.compose.ui.Modifier.height(60.dp))
                Image(
                    painter = painterResource(id = R.drawable.forgotpassword_removebg_preview_1_),
                    contentDescription = null,
                    androidx.compose.ui.Modifier.size(150.dp)
                )
                Spacer(modifier = androidx.compose.ui.Modifier.height(30.dp))
//                Text(
//                    buildAnnotatedString {
//                        withStyle(
//                            style = SpanStyle(
//                                fontWeight = FontWeight.Bold,
//                                color = Color.White,
//                                background = Color.Green,
//                            )
//                        ) {
//                            append("Forgot Your Password?")
//                        }
//                    }
//                )
                Spacer(modifier = androidx.compose.ui.Modifier.height(20.dp))
                Text(
                    text = "Forgot your Password?",
                    fontWeight = FontWeight.Bold,
                    modifier = androidx.compose.ui.Modifier
                        //.width(100.dp)
                        .padding(
                            start = 86.dp
                        )
                        .fillMaxWidth()
                        .height(100.dp),

                    fontSize = 20.sp,
                )

                Spacer(modifier = androidx.compose.ui.Modifier.height(10.dp))
                val femail = inputTextField(label = "Email")

                Spacer(modifier = androidx.compose.ui.Modifier.height(20.dp))

                GradientButton(
                    text = "Submit",
                    textColor = Color.White ,
                    gradient = Brush.horizontalGradient(
                        colors = listOf(
                            Color(0xFF642B73),
                            Color(0xFFC6426E),
                        ))
                ){
                    //val email: String = femail.text.toString().trim{ it <= ' '}
                    //val femail = email.toString()
                    if(femail.isEmpty()){
                        Toast.makeText(
                            this@Forgotpassword_page,
                            "Please enter an Email",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    else{
                        FirebaseAuth.getInstance().sendPasswordResetEmail(femail)
                            .addOnCompleteListener{task ->
                                if(task.isSuccessful){
                                    Toast.makeText(
                                        this@Forgotpassword_page,
                                        "Email sent successfully to reset your password",
                                        Toast.LENGTH_LONG
                                    ).show()
                                }else{
                                    Toast.makeText(
                                        this@Forgotpassword_page,
                                        task.exception!!.message.toString(),
                                        Toast.LENGTH_LONG
                                    ).show()
                                }

                            }

                    }

                }

            //SimpleImage()
//            Column(
//                modifier = androidx.compose.ui.Modifier
//                    .padding(16.dp)
//                    .height(300.dp),
//
//
//            ) {
//
//            }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun inputTextField(label : String): String {
    var text by remember {
        mutableStateOf(" ")
    }
    //var text by remember {mutableStateOf(" ") }
    OutlinedTextField(
        value = text,
        onValueChange = { text = it },
        label = { Text(label)  },
//        Color  TextFieldDefaults.outlinedTextFieldColors(
//            containerColor = Color.Red
//        ),
        leadingIcon = {
            IconButton(onClick = {null}) {
                Icon(
                    imageVector = Icons.Filled.Email,
                    contentDescription = "Email Icon"
                )
            }
        },
        textStyle = TextStyle(fontWeight = FontWeight.Normal, fontSize = 16.sp),
        modifier = androidx.compose.ui.Modifier
            .fillMaxWidth()
            .height(80.dp)
            .padding(10.dp)
    )
    return text
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview(){
        //SimpleImage()
}
//
//@Composable
//fun SimpleImage(){
//    Image(
//        painter = painterResource(id = R.drawable.background4),
//        contentDescription = "Background3 ",
//        modifier = androidx.compose.ui.Modifier
//            .fillMaxWidth()
//            .fillMaxHeight()
//    )
//}

