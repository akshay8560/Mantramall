package com.mantramall

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.Window
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.mantramall.databinding.ActivitySplashScreenBinding
import com.mantramall.viewModel.MainViewModel
import com.mantramall.repository.Repository
import com.mantramall.viewModelFactory.MainViewModelFactory


class splashScreen : AppCompatActivity() {


    private lateinit var binding: ActivitySplashScreenBinding
    lateinit var sharedPrefference: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPrefference = getSharedPreferences("SHARED_PREF", Context.MODE_PRIVATE)

//        startsecondactivity()

        val intent = Intent(applicationContext, login::class.java)
        startActivity(intent)
        finish()

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
//            val authenticated = sharedPrefference.getString("authenticated", "false")
            val authenticated = sharedPrefference.getString("authenticatsdfed", "false")
            if (authenticated == "true") {
                val intent = Intent(applicationContext, MainActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                dialogView()
            }
        }, 3000)
    }
}