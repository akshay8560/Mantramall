package com.mantramall

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.mantramall.databinding.ActivityReferAndEarnBinding

class referAndEarn : AppCompatActivity() {
    private lateinit var binding:ActivityReferAndEarnBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityReferAndEarnBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }
}