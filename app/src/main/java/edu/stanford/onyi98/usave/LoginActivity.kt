package edu.stanford.onyi98.usave

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login2.*

private const val TAG = "LoginActivity"
class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login2)
        val auth = FirebaseAuth.getInstance()
        if (auth.currentUser != null) {
            goBucketsActivity()
        }
        buttonLogin.setOnClickListener {
            buttonLogin.isEnabled = false
            val email = etEmail.text.toString()
            val password = etPassword.text.toString()
            if (email.isBlank() || password.isBlank()) {
                Toast.makeText(this, "Email/password cannot be empty", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Firebase authentication check

            auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                buttonLogin.isEnabled = true
                if (task.isSuccessful) {
                    Toast.makeText(this, "Login successful!", Toast.LENGTH_SHORT).show()
                    goBucketsActivity()
                } else {
                    Log.i(TAG, "signInWithEmail failed", task.exception)
                    Toast.makeText(this, "Login failed", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun goBucketsActivity() {
        Log.i(TAG, "goBucketsActivity")
        val intent = Intent(this, BucketsActivity::class.java)
        startActivity(intent)
        finish()
    }
}