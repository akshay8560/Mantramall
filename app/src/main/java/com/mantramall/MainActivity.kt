package com.mantramall

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.widget.LinearLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import com.mantramall.dataModel.dataUserProfile
import com.mantramall.databinding.ActivityMainBinding
import com.mantramall.fragments.home
import com.mantramall.fragments.settings
import com.mantramall.fragments.transaction
import org.json.JSONException
import org.json.JSONObject
import java.net.URL

class MainActivity : AppCompatActivity() {
   private lateinit var database:DatabaseReference
    private lateinit var binding: ActivityMainBinding
    lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth= FirebaseAuth.getInstance()

        var currentUser=auth.currentUser
        if(currentUser==null){
            startActivity(Intent(this,login::class.java))
            finish()
        }
        getUserProfile()
        getEditUserProfile()
        getUserImage()
        getBankEdit()

        replacefragment(home())
        initBottomBar(binding.home)

        binding.settings.setOnClickListener {
            initBottomBar(binding.settings)
            replacefragment(settings())
        }
        binding.home.setOnClickListener {
            initBottomBar(binding.home)
            replacefragment(home())
        }
        binding.transaction.setOnClickListener {
            initBottomBar(binding.transaction)
            replacefragment(transaction())
        }
    }

    private fun getBankEdit() {
        try {
            val requestQueue = Volley.newRequestQueue(this)
            val URL = "https://mantrimall.anjalpufoam.in/public/api/user-bank"

            val stringRequest: StringRequest = object : StringRequest(
                Method.POST, URL,
                Response.Listener { response ->
                    Log.e("VOLLEYBankRespone", response.toString())
                    Toast.makeText(applicationContext, response.toString(), Toast.LENGTH_SHORT).show()},
                Response.ErrorListener { error -> Log.e("VOLLEYBankEroor", error.toString()) }) {
            }
            requestQueue.add(stringRequest)
        } catch (e: JSONException) {
            e.printStackTrace()
        }
    }

    private fun getUserImage() {
        try {
            val requestQueue = Volley.newRequestQueue(this)
            val URL = "https://mantrimall.anjalpufoam.in/public/api/upload-file"

            val stringRequest: StringRequest = object : StringRequest(
                Method.POST, URL,
                Response.Listener { response ->
                    Log.e("VOLLEY5", response.toString())
                    Toast.makeText(applicationContext, response.toString(), Toast.LENGTH_SHORT).show()},
                Response.ErrorListener { error -> Log.e("VOLLEY6", error.toString()) }) {
            }
            requestQueue.add(stringRequest)
        } catch (e: JSONException) {
            e.printStackTrace()
        }
    }

    private fun getEditUserProfile() {
        try {


            val requestQueue = Volley.newRequestQueue(this)
            val URL = "https://mantrimall.anjalpufoam.in/public/api/user-edit"

            val stringRequest: StringRequest = object : StringRequest(
                Method.POST, URL,
                Response.Listener { response ->
                    Log.e("VOLLEY3", response.toString())
                    Toast.makeText(applicationContext, response.toString(), Toast.LENGTH_SHORT).show()},
                Response.ErrorListener { error -> Log.e("VOLLEY4", error.toString()) }) {
            }
            requestQueue.add(stringRequest)
        } catch (e: JSONException) {
            e.printStackTrace()
        }

    }

    private fun getUserProfile() {
        try {


            val requestQueue = Volley.newRequestQueue(this)
            val URL = "https://mantrimall.anjalpufoam.in/public/api/profile-data"

            val stringRequest: StringRequest = object : StringRequest(
                Method.POST, URL,
                Response.Listener { response ->
                    Log.e("VOLLEY1", response.toString())
                    Toast.makeText(applicationContext, response.toString(), Toast.LENGTH_SHORT).show()},
                Response.ErrorListener { error -> Log.e("VOLLEY2", error.toString()) }) {
            }
            requestQueue.add(stringRequest)
        } catch (e: JSONException) {
            e.printStackTrace()
        }






//        var mRequestQueue = Volley.newRequestQueue(this)
//
//        val url=URL("https://mantrimall.anjalpufoam.in/public/api/profile-data")
//
//      val jsonObjectRequest = JsonObjectRequest(Request.Method.POST,url,null,
//          Response.Listener {
//          var gson = Gson()
//           var userProfileDetail : dataUserProfile = gson.fromJson(it.toString(),dataUserProfile::class.java)
//              Log.d("MainActivity","Response: ${userProfileDetail.data.name}")
//
//          },Response.ErrorListener {
//              Log.d("MainActivity","Error: ${it.message}")
//          })
//
//        mRequestQueue.add(jsonObjectRequest)

    }
    private fun initBottomBar(click:LinearLayout) {
        binding.settings.setBackgroundResource(R.drawable.empty)
        binding.settings.alpha=0.2f
        binding.transaction.setBackgroundResource(R.drawable.empty)
        binding.transaction.alpha=0.2f
        binding.home.setBackgroundResource(R.drawable.empty)
        binding.home.alpha=0.2f
        click.setBackgroundResource(R.drawable.background_grey_stroke)
        click.alpha=1f
    }

    private fun replacefragment(fragment: Fragment) {

        if (fragment!=null){
            if(!fragment.isVisible){
                val fragmentManager = supportFragmentManager
                val fragmentTransaction = fragmentManager.beginTransaction()
                fragmentTransaction.replace(R.id.fragmentContainer,fragment)
                fragmentTransaction.commit()
            }
        }
    }
}




