package com.mantramall.fragments

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.mantramall.R
import com.mantramall.R.*
import com.mantramall.login
import com.mantramall.referAndEarn
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_settings.*
import org.jetbrains.anko.support.v4.toast


class settings : Fragment() {

    lateinit var sharedPrefference: SharedPreferences
    private var firebaseUser: FirebaseUser? = null
    private lateinit var myRef: DatabaseReference
    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(layout.fragment_settings, container, false)

        sharedPrefference=requireActivity().getSharedPreferences("SHARED_PREF", Context.MODE_PRIVATE)
        val myRefer = view.findViewById<TextView>(R.id.myRefer)
        val termsAndConditions = view.findViewById<TextView>(R.id.termsAndConditions)
        val logout = view.findViewById<TextView>(R.id.logout)
        val teamBonus = view.findViewById<TextView>(R.id.teamBonus)
        val joinWP = view.findViewById<TextView>(R.id.joinWP)
        val profilePic = view.findViewById<ImageView>(R.id.profilePic)
        val editUserDetail = view.findViewById<ImageView>(R.id.editUserDetail)
        val saveUserDetails = view.findViewById<ImageView>(R.id.saveUserDetail)
        val username = view.findViewById<EditText>(R.id.userName)
        val mobileNo =  view.findViewById<EditText>(R.id.phoneNumber)
        val saveDetailLL=view.findViewById<LinearLayout>(R.id.savedetailLL)



        val database = Firebase.database
        myRef = database.getReference("Users")
        firebaseUser= FirebaseAuth.getInstance().currentUser;
        FirebaseDatabase.getInstance().reference.child("Users")
        myRef!!.child(firebaseUser!!.uid).get().addOnSuccessListener{
            if (it.exists()){
                val guest_names=it.child("name").value
                val guest_number=it.child("mobileno").value
                username.setText(guest_names.toString())
                mobileNo.setText(guest_number.toString())
            }

        }
        username.setEnabled(false)
        mobileNo.setEnabled(false)
        saveDetailLL.setVisibility(View.INVISIBLE);
        editUserDetail.setOnClickListener(View.OnClickListener {
            username.setEnabled(true);
            mobileNo.setEnabled(true);
            saveDetailLL.setVisibility(View.VISIBLE);
        })
        saveUserDetails.setOnClickListener(View.OnClickListener {
            updateProfile()
            username.setEnabled(false)
            mobileNo.setEnabled(false)
            saveDetailLL.setVisibility(View.INVISIBLE);
            toast("Saved Details")

        })

        myRefer.setOnClickListener {
            startActivity(Intent(requireContext(),referAndEarn::class.java))
        }
        termsAndConditions.setOnClickListener {
            dialogView()
        }
        logout.setOnClickListener {
            val editor: SharedPreferences.Editor = sharedPrefference.edit()
            editor.remove("authenticated")
            editor.apply()
            startActivity(Intent(requireContext(),login::class.java))
        }
        teamBonus.setOnClickListener {
            dialogView()
        }
        joinWP.setOnClickListener {
            val url = "https://api.whatsapp.com/send?phone=$+919864933180"
            val i = Intent(Intent.ACTION_VIEW)
            i.data = Uri.parse(url)
            startActivity(i)
        }
        return view
    }

    private fun updateProfile() {
        val map: HashMap<String, Any> = HashMap()
        map["name"] = userName.editableText.toString()
        map["mobileno"] = phoneNumber.editableText.toString()
        FirebaseDatabase.getInstance().reference.child("Users").child(firebaseUser!!.uid).updateChildren(map)
    }

    fun dialogView() {

        val dialog = Dialog(requireContext(), style.BottomSheetDialogTheme)

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)

        dialog.setCancelable(true)

        dialog.setContentView(layout.terms_and_conditions_dialog_layout_design)
        val okay = dialog.findViewById<TextView>(R.id.okayActionBtnDialog)
        okay.setOnClickListener {
            dialog.hide()
        }


        dialog.show()
    }

}