package com.example.loginexample


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.loginexample.Model.LoginData
import com.example.loginexample.Model.ResponseData
import com.example.loginexample.services.MainService
import com.example.loginexample.services.ServiceBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnLogLogin = findViewById<Button>(R.id.btnLogLogin)
        val btnRegister = findViewById<Button>(R.id.btnLogRegister)

        btnLogLogin.setOnClickListener {
            login()
        }
        btnRegister.setOnClickListener {

            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }

    private fun login() {

        val progressBar = findViewById<View>(R.id.progressBar)
        progressBar.visibility = View.VISIBLE

        val etLogUsername = findViewById<EditText>(R.id.etLogUsername)
        val etLogPassword = findViewById<EditText>(R.id.etLogPassword)

        val newLoginData = LoginData(etLogUsername.text.toString(), etLogPassword.text.toString())
        val mainService = ServiceBuilder.buildService(MainService::class.java)
        val requestCall = mainService.Login(newLoginData)
        requestCall.enqueue((object : Callback<ResponseData> {
            override fun onResponse(call: Call<ResponseData>, response: Response<ResponseData>) {
                progressBar.visibility = View.GONE
                if(response.isSuccessful){

                    if(response.body()?.message.toString() == "true"){
                        val intent = Intent(this@MainActivity,HomeActivity::class.java)
                        startActivity(intent)
                    }else{
                        Toast.makeText(this@MainActivity,"Incorrect Username or Password",Toast.LENGTH_SHORT).show()
                    }
                }else{
                    Toast.makeText(this@MainActivity,response.code(),Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ResponseData>, t: Throwable) {
                progressBar.visibility = View.GONE
                Toast.makeText(this@MainActivity,t.toString(),Toast.LENGTH_SHORT).show()
            }

        }))


    }

}