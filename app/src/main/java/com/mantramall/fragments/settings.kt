package com.mantramall.fragments

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.mantramall.R
import com.mantramall.R.*
import com.mantramall.login
import com.mantramall.referAndEarn
import org.w3c.dom.Text

class settings : Fragment() {

    lateinit var sharedPrefference: SharedPreferences

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
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
        val username = view.findViewById<TextView>(R.id.userName)
        val mobileNo =  view.findViewById<TextView>(R.id.phoneNumber)


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