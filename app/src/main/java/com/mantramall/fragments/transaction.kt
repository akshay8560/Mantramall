package com.mantramall.fragments

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.mantramall.R
import com.mantramall.login

class transaction : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_transaction, container, false)

        val addBank = view.findViewById<LinearLayout>(R.id.addBank)
        val withdrawBtn = view.findViewById<LinearLayout>(R.id.withdraw)
        addBank.setOnClickListener {
            dialogView()
        }
        withdrawBtn.setOnClickListener {
            withdraw()
        }
        return view
    }

    fun withdraw(){
        val view: View = layoutInflater.inflate(R.layout.withdrow_money_layout_design,null)
        val dialog = BottomSheetDialog(requireContext(),R.style.BottomSheetDialogTheme)

        val amountET = view.findViewById<EditText>(R.id.amountET)
        val cancelActionBtn = view.findViewById<TextView>(R.id.cancelActionBtnWM)
        val confirmActionBtn = view.findViewById<TextView>(R.id.confirmActionBtnWM)
        cancelActionBtn.setOnClickListener {
            dialog.hide()
        }
        confirmActionBtn.setOnClickListener {
            val amount = amountET.text
            if (amount.isNotEmpty()){
                Toast.makeText(requireContext(), "$amount", Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(requireContext(), "Enter amount", Toast.LENGTH_SHORT).show()
            }
        }

        dialog.setContentView(view)
        dialog.setCancelable(false)
        dialog.show()
    }

    fun dialogView() {

        val dialog = Dialog(requireContext(), R.style.BottomSheetDialogTheme)

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)

        dialog.setCancelable(true)

        dialog.setContentView(R.layout.add_bank_layout_design)
        val accountNumberET = dialog.findViewById<EditText>(R.id.accountNumberET)
        val bankHolder = dialog.findViewById<EditText>(R.id.bankHolderNameET)
        val bankName = dialog.findViewById<EditText>(R.id.bankNameET)
        val ifscCode = dialog.findViewById<EditText>(R.id.ifscCodeET)
        val cancel = dialog.findViewById<TextView>(R.id.cancelActionBtnDialog)
        val submit = dialog.findViewById<TextView>(R.id.submitActionBtnDialog)

        cancel.setOnClickListener {
            dialog.hide()
        }
        submit.setOnClickListener {
            if (accountNumberET.text.isNotEmpty()&&bankHolder.text.isNotEmpty()&&bankName.text.isNotEmpty()&&ifscCode.text.isNotEmpty()){
                val data : HashMap<String,Any> = hashMapOf()
                data["accountNumber"]= accountNumberET.text.toString()
                data["bankHolder"]= bankHolder.text.toString()
                data["bankName"]= bankName.text.toString()
                data["ifscCode"]= ifscCode.text.toString()

                dialog.hide()
            }else{
                Toast.makeText(requireContext(), "All data required", Toast.LENGTH_SHORT).show()
            }
        }

        dialog.show()
    }
}