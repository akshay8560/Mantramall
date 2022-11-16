package com.mantramall.fragments

import android.annotation.SuppressLint
import android.app.Dialog
import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.*
import androidx.fragment.app.Fragment
import com.cedarsoftware.util.io.JsonWriter.DATE_FORMAT
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.mantramall.R
import java.text.SimpleDateFormat
import java.util.*

class home : Fragment() {
    var contractAmount =0
    var START_MILLI_SECONDS = 60000L


    var isRunning: Boolean = false;
    var time_in_milli_seconds = 120000L
    private lateinit var countDownTimer:CountDownTimer
    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment


        val view = inflater.inflate(R.layout.fragment_home, container, false)

        val addMoney = view.findViewById<TextView>(R.id.addMoneyBtn)
        val rules = view.findViewById<TextView>(R.id.gameRulesBtn)

        val greenBtn = view.findViewById<ImageView>(R.id.greenBtn)
        val redBtn = view.findViewById<ImageView>(R.id.redBtn)
        val violetBtn = view.findViewById<ImageView>(R.id.violetBtn)
        val minutes = view.findViewById<TextView>(R.id.minutes)
        val seconds = view.findViewById<TextView>(R.id.seconds)

        object : CountDownTimer(30000, 1000) {

            override fun onTick(millisUntilFinished: Long) {

                val Minutess = millisUntilFinished / (60 * 1000) % 60


                    minutes.setText(String.format("%02d", Minutess))




            }

            override fun onFinish() {
                minutes.setText("00")
            }
        }.start()
        object : CountDownTimer(60000, 1000) {

            override fun onTick(millisUntilFinished: Long) {

                val Secondss = millisUntilFinished / 1000 % 60
                seconds.setText(String.format("%02d", Secondss))

            }

            override fun onFinish() {
                minutes.setText("00")
            }
        }.start()
        val num0 = view.findViewById<TextView>(R.id.num0)
        val num1 = view.findViewById<TextView>(R.id.num1)
        val num2 = view.findViewById<TextView>(R.id.num2)
        val num3 = view.findViewById<TextView>(R.id.num3)
        val num4 = view.findViewById<TextView>(R.id.num4)
        val num5 = view.findViewById<TextView>(R.id.num5)
        val num6 = view.findViewById<TextView>(R.id.num6)
        val num7 = view.findViewById<TextView>(R.id.num7)
        val num8 = view.findViewById<TextView>(R.id.num8)
        val num9 = view.findViewById<TextView>(R.id.num9)


        num0.setOnClickListener {
            selectedNumber(0)
        }
        num1.setOnClickListener {
            selectedNumber(1)
        }
        num2.setOnClickListener {
            selectedNumber(2)
        }
        num3.setOnClickListener {
            selectedNumber(3)
        }
        num4.setOnClickListener {
            selectedNumber(4)
        }
        num5.setOnClickListener {
            selectedNumber(5)
        }
        num6.setOnClickListener {
            selectedNumber(6)
        }
        num7.setOnClickListener {
            selectedNumber(7)
        }
        num8.setOnClickListener {
            selectedNumber(8)
        }
        num9.setOnClickListener {
            selectedNumber(9)
        }

        violetBtn.setOnClickListener {
            contractBegin("Violet")
        }
        redBtn.setOnClickListener {
            contractBegin("Red")
        }
        greenBtn.setOnClickListener {
            contractBegin("Green")
           // test()
        }
        addMoney.setOnClickListener {
            addMoney()
        }
        rules.setOnClickListener {
            rules()
        }
        return view
    }


    fun contractBegin(color:String){

        val view: View = layoutInflater.inflate(R.layout.you_selected_layout_design,null)
        val dialog = BottomSheetDialog(requireContext(),R.style.BottomSheetDialogTheme)

        val cancel = view.findViewById<TextView>(R.id.cancelBtnSelection)
        val confirm = view.findViewById<TextView>(R.id.confirmBtbSelection)
        val selectedColorImg = view.findViewById<ImageView>(R.id.selectedColorImg)

        val checkbox = view.findViewById<CheckBox>(R.id.checkbox)

        val amountContractDialogTV = view.findViewById<TextView>(R.id.amountContractDialogTV)
        val amount10 = view.findViewById<TextView>(R.id.amount10)
        val amount100 = view.findViewById<TextView>(R.id.amount100)
        val amount1000 = view.findViewById<TextView>(R.id.amount1000)
        val amount10000 = view.findViewById<TextView>(R.id.amount10000)


        val plus = view.findViewById<TextView>(R.id.plus)
        val minues = view.findViewById<TextView>(R.id.minues)
        val count = view.findViewById<TextView>(R.id.count)

        confirm.setOnClickListener {
            if (checkbox.isChecked){
                Toast.makeText(requireContext(), "Everything done and the amount is $contractAmount", Toast.LENGTH_SHORT).show()
                dialog.hide()
            }else{
                Toast.makeText(requireContext(), "Select checkbox first", Toast.LENGTH_SHORT).show()
            }
        }
        var c =1
        var clickCount =1
        plus.setOnClickListener {
            clickCount++
            contractAmount += c
            amountContractDialogTV.text="₹"+contractAmount.toString()
            count.text="x"+clickCount
        }
        minues.setOnClickListener {
            if (clickCount>1) {
                clickCount--
                contractAmount -= c
                amountContractDialogTV.text = "₹" + contractAmount.toString()
                count.text="x"+clickCount
            }
        }

        fun selectedAmount(tv:TextView){
            c =1
            clickCount =1
            contractAmount=0
            count.text="x"+clickCount
            amount10.setBackgroundResource(R.drawable.textview_background_grey_stroke)
            amount100.setBackgroundResource(R.drawable.textview_background_grey_stroke)
            amount1000.setBackgroundResource(R.drawable.textview_background_grey_stroke)
            amount10000.setBackgroundResource(R.drawable.textview_background_grey_stroke)
            tv.setBackgroundResource(R.drawable.textview_background_grey_stroke_grey_fill)
            amountContractDialogTV.text="₹"+tv.text
            c=tv.text.toString().toInt()
            contractAmount = tv.text.toString().toInt()
        }

        if (color=="Green"){
            selectedColorImg.setImageResource(R.drawable.green_round)
            confirm.setBackgroundResource(R.color.green)
        }else if (color=="Red"){
            selectedColorImg.setImageResource(R.drawable.red_round)
            confirm.setBackgroundResource(R.color.red)
        }else if (color=="Violet"){
            selectedColorImg.setImageResource(R.drawable.violet_round)
            confirm.setBackgroundResource(R.color.violet)
        }
        cancel.setOnClickListener {
            dialog.hide()
        }

        amount10.setOnClickListener {
            selectedAmount(amount10)
        }
        amount100.setOnClickListener {
            selectedAmount(amount100)
        }
        amount1000.setOnClickListener {
            selectedAmount(amount1000)
        }
        amount10000.setOnClickListener {
            selectedAmount(amount10000)
        }

        dialog.setContentView(view)
        dialog.setCancelable(false)
        dialog.show()

        amount10.performClick()

    }

    fun selectedNumber(number:Int){

        val view: View = layoutInflater.inflate(R.layout.number_selected_layout_design,null)
        val dialog = BottomSheetDialog(requireContext(),R.style.BottomSheetDialogTheme)

        val cancel = view.findViewById<TextView>(R.id.cancelBtnSelection)
        val confirm = view.findViewById<TextView>(R.id.confirmBtbSelection)
        val selectedNumberTV = view.findViewById<TextView>(R.id.selectedNumber)
        selectedNumberTV.setText(number.toString())
        val checkbox = view.findViewById<CheckBox>(R.id.checkbox)

        val amountContractDialogTV = view.findViewById<TextView>(R.id.amountContractDialogTV)
        val amount10 = view.findViewById<TextView>(R.id.amount10)
        val amount100 = view.findViewById<TextView>(R.id.amount100)
        val amount1000 = view.findViewById<TextView>(R.id.amount1000)
        val amount10000 = view.findViewById<TextView>(R.id.amount10000)


        val plus = view.findViewById<TextView>(R.id.plus)
        val minues = view.findViewById<TextView>(R.id.minues)
        val count = view.findViewById<TextView>(R.id.count)

        confirm.setOnClickListener {
            if (checkbox.isChecked){
                Toast.makeText(requireContext(), "Everything done and the amount is $contractAmount", Toast.LENGTH_SHORT).show()
                dialog.hide()
            }else{
                Toast.makeText(requireContext(), "Select checkbox first", Toast.LENGTH_SHORT).show()
            }
        }
        var c =1
        var clickCount =1
        plus.setOnClickListener {
            clickCount++
            contractAmount += c
            amountContractDialogTV.text="₹"+contractAmount.toString()
            count.text="x"+clickCount
        }
        minues.setOnClickListener {
            if (clickCount>1) {
                clickCount--
                contractAmount -= c
                amountContractDialogTV.text = "₹" + contractAmount.toString()
                count.text="x"+clickCount
            }
        }

        fun selectedAmount(tv:TextView){
            c =1
            clickCount =1
            contractAmount=0
            count.text="x"+clickCount
            amount10.setBackgroundResource(R.drawable.textview_background_grey_stroke)
            amount100.setBackgroundResource(R.drawable.textview_background_grey_stroke)
            amount1000.setBackgroundResource(R.drawable.textview_background_grey_stroke)
            amount10000.setBackgroundResource(R.drawable.textview_background_grey_stroke)
            tv.setBackgroundResource(R.drawable.textview_background_grey_stroke_grey_fill)
            amountContractDialogTV.text="₹"+tv.text
            c=tv.text.toString().toInt()
            contractAmount = tv.text.toString().toInt()
        }

        cancel.setOnClickListener {
            dialog.hide()
        }

        amount10.setOnClickListener {
            selectedAmount(amount10)
        }
        amount100.setOnClickListener {
            selectedAmount(amount100)
        }
        amount1000.setOnClickListener {
            selectedAmount(amount1000)
        }
        amount10000.setOnClickListener {
            selectedAmount(amount10000)
        }

        dialog.setContentView(view)
        dialog.setCancelable(false)
        dialog.show()

        amount10.performClick()

    }


    fun rules() {

        val dialog = Dialog(requireContext(), R.style.BottomSheetDialogTheme)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)
        dialog.setContentView(R.layout.game_rules_dialoge_layout_design)
        val actionBtnDialogRules = dialog.findViewById<TextView>(R.id.actionBtnDialogRules)

        actionBtnDialogRules.setOnClickListener {
            dialog.hide()
        }

        dialog.show()
    }
    fun addMoney(){
        val view: View = layoutInflater.inflate(R.layout.add_money_layout_design,null)
        val dialog = BottomSheetDialog(requireContext(),R.style.BottomSheetDialogTheme)

        val amountET = view.findViewById<EditText>(R.id.amountETAM)
        val cancelActionBtn = view.findViewById<TextView>(R.id.cancelActionBtnAM)
        val confirmActionBtn = view.findViewById<TextView>(R.id.confirmActionBtnAM)
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




}