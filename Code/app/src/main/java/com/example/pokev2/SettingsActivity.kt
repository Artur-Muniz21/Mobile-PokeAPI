package com.example.pokev2

import MainActivity
import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)


        // Back button to navigate to MainActivity
        findViewById<ImageButton>(R.id.backButton).setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            finish() // Finish SettingsActivity to prevent going back to it
        }


        findViewById<TextView>(R.id.changePasswordText).setOnClickListener {
            // Start Change Password Activity
            val intent = Intent(this, ChangePasswordActivity::class.java)
            startActivity(intent)
        }


        findViewById<ImageButton>(R.id.editPasswordButton).setOnClickListener {
            // Same action as change password text
        }

        // Logout option
        findViewById<TextView>(R.id.logoutText).setOnClickListener {
            // Handle logout logic here
        }

        findViewById<ImageButton>(R.id.logoutButton).setOnClickListener {
            // Same action as logout text
        }
    }
}
