package com.example.loginexample

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.loginexample.Model.LoginData
import com.example.loginexample.Model.RegisterData
import com.example.loginexample.Model.ResponseData
import com.example.loginexample.services.MainService
import com.example.loginexample.services.ServiceBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val etRegName = findViewById<EditText>(R.id.etRegName)
        val etRegUsername = findViewById<EditText>(R.id.etRegUsername)
        val etRegPassword = findViewById<EditText>(R.id.etRegPassword)
        val btnRegRegister = findViewById<Button>(R.id.btnRegRegister)


        btnRegRegister.setOnClickListener{

            register()

        }

    }

    private fun register(){

        val progressBar = findViewById<View>(R.id.progressBar)
        progressBar.visibility = View.VISIBLE

        val etRegName = findViewById<EditText>(R.id.etRegName)
        val etRegUsername = findViewById<EditText>(R.id.etRegUsername)
        val etRegPassword = findViewById<EditText>(R.id.etRegPassword)

        if(etRegName.text.toString().trim()!="" && etRegUsername.text.toString().trim()!="" && etRegPassword.text.toString().trim()!=""){

            val newRegData = RegisterData(etRegName.text.toString(),etRegUsername.text.toString(),etRegPassword.text.toString())
            val mainService = ServiceBuilder.buildService(MainService::class.java)
            val requestCall = mainService.Register(newRegData)

            requestCall.enqueue((object : Callback<ResponseData> {
                override fun onResponse(call: Call<ResponseData>, response: Response<ResponseData>) {
                    progressBar.visibility = View.GONE
                    if(response.isSuccessful){
                        if(response.body()?.message.toString() == "true"){
                            Toast.makeText(this@RegisterActivity,"Registration Completed Successfully", Toast.LENGTH_SHORT).show()
                        }else{
                            Toast.makeText(this@RegisterActivity,"Something Went Wrong!!!", Toast.LENGTH_SHORT).show()
                        }
                    }else{
                        Toast.makeText(this@RegisterActivity,"ERROR : " + response.code(), Toast.LENGTH_SHORT).show()
                    }
                }
                override fun onFailure(call: Call<ResponseData>, t: Throwable) {
                    progressBar.visibility = View.GONE
                    Toast.makeText(this@RegisterActivity, "ERROR : $t", Toast.LENGTH_SHORT).show()
                }

            }))

        }else{
            Toast.makeText(this@RegisterActivity, "Please Enter Valid Data", Toast.LENGTH_SHORT).show()
        }
    }
}