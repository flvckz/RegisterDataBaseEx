package com.fluck.logintry

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.vishnusivadas.advanced_httpurlconnection.PutData


class SignUp : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        var signupLayout : ConstraintLayout = findViewById(R.id.signupLayout)
        var buttonSignUp : Button = findViewById(R.id.buttonSignUp)
        var progressBarSignUp : ProgressBar = findViewById(R.id.progress)
        var etFullname: TextInputEditText = findViewById(R.id.fullname)
        var etUsername: TextInputEditText = findViewById(R.id.username)
        var etPassword: TextInputEditText = findViewById(R.id.password)
        var etEmail: TextInputEditText = findViewById(R.id.email)
        var LogInText: TextView = findViewById(R.id.loginText)

        LogInText.setOnClickListener {

            val intent = Intent (this, LogIn::class.java)
            startActivity(intent)
            finish()
        }

        buttonSignUp.setOnClickListener {

            var stringFullName: String = etFullname.text.toString()
            var stringUsername: String = etUsername.text.toString()
            var stringPassword: String = etPassword.text.toString()
            var stringEmail: String = etEmail.text.toString()

            if (stringFullName != "" && stringUsername != "" && stringPassword != "" && stringEmail != "") {

                progressBarSignUp.visibility = View.VISIBLE

                val handler = Handler(Looper.getMainLooper())
                handler.post(Runnable {

                val field = arrayOfNulls<String>(4)
                field[0] = "fullname"
                field[1] = "username"
                field[2] = "password"
                field[3] = "email"

                val data = arrayOfNulls<String>(4)
                data[0] = stringFullName
                data[1] = stringUsername
                data[2] = stringPassword
                data[3] = stringEmail

                val putData = PutData ("http://192.168.0.169/LoginRegister/signup.php", "POST", field, data)
                if (putData.startPut()) {
                    if (putData.onComplete()) {
                        progressBarSignUp.visibility = View.GONE

                        var resultStringed: String = putData.result

                        if (resultStringed == "Sign Up Success"){

                            Snackbar.make(signupLayout, resultStringed, Snackbar.LENGTH_SHORT).show()
                            val intent = Intent (this, LogIn::class.java)
                            startActivity(intent)
                            finish()
                        }
                        else {
                            Snackbar.make(signupLayout, resultStringed, Snackbar.LENGTH_SHORT).show()
                        }
                    }
                }
            })
        }
        else {
            Snackbar.make(signupLayout, "All fields required", Snackbar.LENGTH_SHORT).show()
            }

        }
    }
}