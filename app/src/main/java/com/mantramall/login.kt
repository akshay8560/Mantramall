package com.mantramall

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.Window
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.firebase.FirebaseException
import com.google.firebase.auth.*
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.mantramall.dataModel.UserData
import com.mantramall.databinding.ActivityLoginBinding
import com.mantramall.repository.Repository
import com.mantramall.viewModel.MainViewModel
import com.mantramall.viewModelFactory.MainViewModelFactory
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONException
import org.json.JSONObject
import java.util.concurrent.TimeUnit


class login : AppCompatActivity() {

    lateinit var dialog: Dialog

    private lateinit var myRef: DatabaseReference
    private lateinit var currentUsers: FirebaseUser

    lateinit var sharedPrefference: SharedPreferences



    var phnNo = ""
    var vId = ""
    var tkn = ""
    var clickState = 0
    private lateinit var mAuth: FirebaseAuth
    private var mCallbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks? = null

    private lateinit var viewModel: MainViewModel

    private lateinit var binding: ActivityLoginBinding
    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        editTextInputFocusing()

        sharedPrefference = getSharedPreferences("SHARED_PREF", Context.MODE_PRIVATE)
        mAuth = FirebaseAuth.getInstance()





        val database = Firebase.database

         myRef = database.getReference("Users")



        binding.btnLogin.setOnClickListener {


            if (clickState == 0) {
                if (binding.phnNumberET.text.toString().trim().isEmpty()) {
                    Toast.makeText(this, "Invalid Phone Number", Toast.LENGTH_SHORT)
                        .show()
                } else if (binding.phnNumberET.text.toString().trim().length !== 10) {
                    Toast.makeText(this, "Type valid Phone Number of 10 digit", Toast.LENGTH_SHORT)
                        .show()
                } else {
                    otpSend()
                    phnNo = binding.phnNumberET.text.trim().toString()

                    // otpRequest(phnNo)
                }
            }
            else if (clickState == 1) {

                run {
                    binding.loadingLayout.visibility = View.VISIBLE;

                    if (binding.etC1.text.toString().trim().isEmpty() ||
                        binding.etC2.text.toString().trim().isEmpty() ||
                        binding.etC3.text.toString().trim().isEmpty() ||
                        binding.etC4.text.toString().trim().isEmpty() ||
                        binding.etC5.text.toString().trim().isEmpty() ||
                        binding.etC6.text.toString().trim().isEmpty()
                    ) {
                        Toast.makeText(applicationContext, "OTP is not Valid!", Toast.LENGTH_SHORT)
                            .show();
                    } else {
                        var code: String? = null
                        code = binding.etC1.text.toString().trim() +
                                binding.etC2.text.toString().trim() +
                                binding.etC3.text.toString().trim() +
                                binding.etC4.text.toString().trim() +
                                binding.etC5.text.toString().trim() +
                                binding.etC6.text.toString().trim();

                        val credential = PhoneAuthProvider.getCredential(vId, code)

                        mAuth.signInWithCredential(credential)
                            .addOnCompleteListener(this) { task ->

                                var userId= mAuth.getCurrentUser()?.getUid()
                                if (task.isSuccessful) {
                                    regester(phnNo,"kawsar")
                                    phnNo = binding.phnNumberET.text.trim().toString()

                                    val editor: SharedPreferences.Editor = sharedPrefference.edit()
                                    editor.putString("phnNumber", phnNo)
                                    editor.putString("authenticated", "true")
                                    editor.apply()
                                    val nameId = (1000..10000).shuffled().last()
                                    val map: HashMap<String, Any> = HashMap()
                                    map.put("Mobile ","+91 "+phnNo);
                                    map.put("Name","Guest_"+nameId)
                                    map.put("NameId",nameId)
                                    if (userId != null) {
                                        map.put("id",userId)
                                    };
                                    map.put("imageurl","https://firebasestorage.googleapis.com/v0/b/mantrimall-bdd75.appspot.com/o/profile.png?alt=media&token=f1c19692-bf9c-4fce-9e88-a2aba433f271")

                                    val user=UserData("+91 "+phnNo ,"guest","id","")
                                    if (userId != null) {
                                        myRef.child(userId).setValue(map)
                                    }
                                    binding.loadingLayout.visibility = View.GONE
                                    Toast.makeText(
                                        applicationContext, "Welcome...", Toast.LENGTH_SHORT
                                    ).show()

                                    val intent = Intent(applicationContext, MainActivity::class.java)
                                    intent.putExtra("mobile",phnNo );
                                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                    startActivity(intent)



                                } else {
                                    binding.loadingLayout.visibility = View.GONE
                                    Toast.makeText(
                                        applicationContext,
                                        "OTP is not Valid!",
                                        Toast.LENGTH_SHORT
                                    ).show()

                                }

                            }






                    }
                }
            }


        }

    }



    var randomKey = "4364565df46"


    private fun regester(phnNo: String, refrence: String){

        try {
            val ran = System.currentTimeMillis() / 1000
            val requestQueue = Volley.newRequestQueue(this)
            val URL = "https://mantrimall.anjalpufoam.in/public/api/user-rgister"
            val jsonBody = JSONObject()
            jsonBody.put("mobile", phnNo)
            jsonBody.put("refrence", refrence)
            jsonBody.put("random", randomKey.toString())

//            jsonBody.put("refrence", "dfasdfgdg")
            val requestBody = jsonBody.toString()
            val stringRequest: StringRequest = object : StringRequest(
                Method.POST, URL,
                Response.Listener { response ->
                    Log.e("VOLLEY", response.toString())
                    Toast.makeText(applicationContext, response.toString(), Toast.LENGTH_SHORT).show()},
                Response.ErrorListener { error -> Log.e("VOLLEY", error.toString()) }) {
            }
            requestQueue.add(stringRequest)
        } catch (e: JSONException) {
            e.printStackTrace()
        }
    }
//    private fun otpRequest(phnNo: String) {
//        try {
//
//            val ran = System.currentTimeMillis() / 1000
//            val requestQueue = Volley.newRequestQueue(this)
//            val URL = "https://mantrimall.anjalpufoam.in/public/api/user-request"
//            val jsonBody = JSONObject()
//            jsonBody.put("mobile", phnNo)
//            jsonBody.put("random", randomKey.toString())
////            jsonBody.put("refrence", "dfasdfgdg")
//            val requestBody = jsonBody.toString()
//            val stringRequest: StringRequest = object : StringRequest(
//                Method.POST, URL,
//                Response.Listener { response ->
//                    Log.e("VOLLEY", response.toString())
//                  //  otpSend()
//                    Toast.makeText(applicationContext, response.toString(), Toast.LENGTH_SHORT).show()},
//                Response.ErrorListener { error -> Log.e("VOLLEY", error.toString()) }) {
//            }
//            requestQueue.add(stringRequest)
//        } catch (e: JSONException) {
//            e.printStackTrace()
//        }
//    }

    private fun userImage(message: String,status: Boolean){

        val repository = Repository()
        val viewModelFactory = MainViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)
        viewModel.pushImagePost(message,status)
        viewModel.myPushImageResponse.observe(this) { response ->

            Log.e("Res", response.toString())
            Toast.makeText(applicationContext, response.toString(), Toast.LENGTH_SHORT).show()

        }
    }
    private fun usereditprofie(message: String,status: Boolean){

        val repository = Repository()
        val viewModelFactory = MainViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)
        viewModel.pushMyPost(message,status)
        viewModel.myPushEditUserResponse.observe(this) { response ->

            Log.e("Res", response.toString())
            Toast.makeText(applicationContext, response.toString(), Toast.LENGTH_SHORT).show()

        }
    }

    private fun user(mobile: String, reference: String, random: String) {

        val repository = Repository()
        val viewModelFactory = MainViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)

       //  viewModel.getPosts()


        viewModel.pushPosts(mobile, reference, random)

//        viewModel.myResponse.observe(this) {
//
//                response ->
//
//            Log.e("Res",response.toString())
//            Toast.makeText(applicationContext, response.toString(), Toast.LENGTH_SHORT).show()
//
//        }

        viewModel.myPushResponse.observe(this) { response ->

                Log.e("Res", response.toString())
                Toast.makeText(applicationContext, response.toString(), Toast.LENGTH_SHORT).show()

        }
    }



    private fun otpSend() {
        binding.loadingLayout.visibility = View.VISIBLE
        val phnNumber = binding.phnNumberET.text.toString().trim()

        mCallbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            override fun onVerificationCompleted(credential: PhoneAuthCredential) {}
            override fun onVerificationFailed(e: FirebaseException) {
                binding.loadingLayout.visibility = View.GONE
                Toast.makeText(applicationContext, e.localizedMessage, Toast.LENGTH_LONG).show()
                clickState = 0
            }

            override fun onCodeSent(
                verificationId: String,
                token: PhoneAuthProvider.ForceResendingToken,
            ) {
                clickState = 1
                binding.loadingLayout.visibility = View.GONE
                Toast.makeText(applicationContext, "OTP is successfully send.", Toast.LENGTH_SHORT)
                    .show()

                phnNo = binding.phnNumberET.text.toString().trim()
                vId = verificationId
                tkn = token.toString()
                binding.otpLayout.visibility = View.VISIBLE
                binding.divider.visibility = View.VISIBLE
                binding.etC1.requestFocus()
                binding.etC1.showSoftKeyboard()
            }
        }
        val options = PhoneAuthOptions.newBuilder(mAuth!!)
            .setPhoneNumber("+91" + binding.phnNumberET.text.toString().trim())
            .setTimeout(60L, TimeUnit.SECONDS)
            .setActivity(this)
            .setCallbacks(mCallbacks as PhoneAuthProvider.OnVerificationStateChangedCallbacks)
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    fun EditText.showSoftKeyboard() {
        (this.context.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager)
            .showSoftInput(this, InputMethodManager.SHOW_IMPLICIT)
    }

    private fun editTextInputFocusing() {
        binding.etC1.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (binding.etC1.length() > 0) {
                    binding.etC2.requestFocus()
                } else {
                    binding.etC1.requestFocus()
                }
            }

            override fun afterTextChanged(s: Editable) {}
        })
        binding.etC2.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {

                if (binding.etC2.length() > 0) {
                    binding.etC3.requestFocus()
                } else {
                    binding.etC1.requestFocus()
                }
            }

            override fun afterTextChanged(s: Editable) {}
        })
        binding.etC3.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {

                if (binding.etC3.length() > 0) {
                    binding.etC4.requestFocus()
                } else {
                    binding.etC2.requestFocus()
                }
            }

            override fun afterTextChanged(s: Editable) {}
        })
        binding.etC4.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {

                if (binding.etC4.length() > 0) {
                    binding.etC5.requestFocus()
                } else {
                    binding.etC3.requestFocus()
                }
            }

            override fun afterTextChanged(s: Editable) {}
        })
        binding.etC5.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {

                if (binding.etC5.length() > 0) {
                    binding.etC6.requestFocus()
                } else {
                    binding.etC4.requestFocus()
                }
            }

            override fun afterTextChanged(s: Editable) {}
        })
        binding.etC6.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {

                if (binding.etC6.length() > 0) {
//                    binding.etC6.clearFocus()
                    binding.btnLogin.performClick()
                } else {
                    binding.etC5.requestFocus()
                }
//                hideSoftKeyboard(binding.etC6)
            }

            override fun afterTextChanged(s: Editable) {}
        })
    }

    protected fun hideSoftKeyboard(input: EditText) {
        val imm: InputMethodManager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(input.windowToken, 0)
    }

    private fun dialogView(title: String, message: String, btn: String) {

        dialog = Dialog(this, R.style.BottomSheetDialogTheme)
        //We have added a title in the custom layout. So let's disable the default title.
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        //The user will be able to cancel the dialog bu clicking anywhere outside the dialog.
        dialog.setCancelable(false)
        //Mention the name of the layout of your custom dialog.
        dialog.setContentView(R.layout.dialoge_layout_design)
        val titleTV = dialog.findViewById<TextView>(R.id.headerTVDialog)
        val messageTV = dialog.findViewById<TextView>(R.id.messageDialog)
        val actionBtn = dialog.findViewById<TextView>(R.id.actionBtnDialog)
        titleTV.text = title.toString()
        messageTV.text = message.toString()
        actionBtn.text = btn.toString()

        actionBtn.setOnClickListener {
            dialog.hide()
        }
        dialog.show()

    }



}


