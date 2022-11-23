package com.mantramall

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Window
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.mantramall.databinding.ActivitySplashScreenBinding


class splashScreen : AppCompatActivity() {


    private lateinit var binding: ActivitySplashScreenBinding
    lateinit var sharedPrefference: SharedPreferences
    lateinit var database: FirebaseDatabase

    lateinit var mAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)


        sharedPrefference = getSharedPreferences("SHARED_PREF", Context.MODE_PRIVATE)

        //startsecondactivity()

        mAuth=FirebaseAuth.getInstance()
        var currentUser = mAuth.currentUser
        if(currentUser != null) {
            Handler(Looper.getMainLooper()).postDelayed({
                val intent = Intent(applicationContext, MainActivity::class.java)
                startActivity(intent)
                finish()
            }, 1000)

        }
        else{
            Handler(Looper.getMainLooper()).postDelayed({
                val intent = Intent(applicationContext, login::class.java)
                startActivity(intent)
                finish()
            }, 1000)

        }


    }



    fun dialogView() {

       val dialog = Dialog(this@splashScreen, R.style.BottomSheetDialogTheme)

       dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)

       dialog.setCancelable(false)

       dialog.setContentView(R.layout.dialoge_layout_design)
       val titleTV = dialog.findViewById<TextView>(R.id.headerTVDialog)
       val messageTV = dialog.findViewById<TextView>(R.id.messageDialog)
       val actionBtn = dialog.findViewById<TextView>(R.id.actionBtnDialog)

       actionBtn.setOnClickListener {
           dialog.hide()
           val intent = Intent(applicationContext, login::class.java)
           startActivity(intent)
           finish()
       }

           dialog.show()




    }


    fun startsecondactivity() {
        Handler(Looper.getMainLooper()).postDelayed({

           // val authenticated = sharedPrefference.getString("authenticated", "false")
            val authenticated = sharedPrefference.getString("authenticatsdfed", "false")
            if (authenticated == "true") {
                val intent = Intent(applicationContext, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
            else {
                dialogView()

            }
        }, 3000)
    }

}