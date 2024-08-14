package com.example.morningmobileappmvvm.data

import android.content.Context
import android.widget.Toast
import androidx.navigation.NavController
import com.example.morningmobileappmvvm.models.User
import com.example.morningmobileappmvvm.navigation.ROUTE_HOME
import com.example.morningmobileappmvvm.navigation.ROUTE_REGISTER
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
class AuthViewModel (var navController: NavController,
                     var context: Context) {
    var mAuth: FirebaseAuth

    init {
        mAuth=FirebaseAuth.getInstance()
    }

    fun signup(firstName: String, secondName: String, email: String, password: String) {
        if (firstName.isBlank() || secondName.isBlank()
            || email.isBlank() || password.isBlank()
        ) {
            Toast.makeText(
                context, "Please fill all the field",
                Toast.LENGTH_LONG
            ).show()
            return
        } else {
            mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener{
                if (it.isSuccessful) {
                    val userData = User(
                        firstName, secondName,
                        email, password, mAuth.currentUser!!.uid
                    )
                    val regRef = FirebaseDatabase.getInstance().getReference()
                        .child("User/" + mAuth.currentUser!!.uid)
                    regRef.setValue(userData).addOnCompleteListener {}
                    if (it.isSuccessful) {
                        Toast.makeText(context, "successfully Registered", Toast.LENGTH_LONG).show()
                    } else {
                        Toast.makeText(
                            context, "${it.exception!!.message}",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }else{
                    navController.navigate(ROUTE_REGISTER)
                }

                }
            }
        }

    fun login (firstName: String,secondName: String,
               email: String,password: String){
        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener{
            if (it.isSuccessful){
                Toast.makeText(context,"successfully logged in",
                    Toast.LENGTH_LONG).show()
            }else{
                Toast.makeText(context,"${it.exception!!.message}",
                    Toast.LENGTH_LONG).show()
            }
        }
    }

    fun login(firstName: String, secondName: String) {

    }

}