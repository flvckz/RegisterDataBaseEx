package com.fluck.logintry

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.vishnusivadas.advanced_httpurlconnection.PutData

class LogIn : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log_in)

        var loginLayout : ConstraintLayout = findViewById(R.id.loginLayout)
        var buttonLogIn : Button = findViewById(R.id.buttonLogin)
        var progressBarLogin : ProgressBar = findViewById(R.id.progress)
        var etUsername: TextInputEditText = findViewById(R.id.usernameLogin)
        var etPassword: TextInputEditText = findViewById(R.id.passwordLogin)
        var SignUpText: TextView = findViewById(R.id.signUpText)

        SignUpText.setOnClickListener {

            val intent = Intent (this, SignUp::class.java)
            startActivity(intent)
            finish()
        }

        buttonLogIn.setOnClickListener {

            var stringUsername = etUsername.text.toString()
            var stringPassword = etPassword.text.toString()

            if (stringUsername != "" && stringPassword != "") {

                progressBarLogin.visibility = View.VISIBLE
                val handler = Handler(Looper.getMainLooper())
                handler.post(Runnable {
                    val field = arrayOfNulls<String>(2)
                    field[0] = "username"
                    field[1] = "password"

                    val data = arrayOfNulls<String>(2)
                    data[0] = stringUsername
                    data[1] = stringPassword

                    val putData = PutData ("http://192.168.0.169/LoginRegister/login.php", "POST", field, data)
                    if (putData.startPut()) {
                        if (putData.onComplete()) {
                            progressBarLogin.visibility = View.GONE

                            var resultStringed: String = putData.result

                            if (resultStringed == "Login Success"){

                                Snackbar.make(loginLayout, resultStringed, Snackbar.LENGTH_SHORT).show()
                                val intent = Intent (this, MainActivity::class.java)
                                startActivity(intent)
                                finish()
                            }
                            else {
                                Snackbar.make(loginLayout, resultStringed, Snackbar.LENGTH_SHORT).show()
                            }
                        }
                    }
                })
            }
            else {
                Snackbar.make(loginLayout, "All fields required", Snackbar.LENGTH_SHORT).show()
            }

        }
    }
}