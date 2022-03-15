package com.jaeyoung1.bmi_chap01

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.jaeyoung1.bmi_chap01.BusEvent.MemoF1toResultFEventBusItem
import com.jaeyoung1.bmi_chap01.BusEvent.ResultF2toMemoF2BusEventItem
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class ResultF : Fragment() {

    private var heightValue = 0
    private var weightValue = 0


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.activity_result, container, false)

        EventBus.getDefault().register(this)



        val bmi: Double = weightValue / ((heightValue / 100.0) * 2.0)

        val resultText = when {

            bmi >= 30 -> "고도비만"
            bmi >= 25 -> "비만"
            bmi >= 23 -> "과체중"
            bmi >= 18.5 -> "정상체중"
            else -> "저체중"
        }

        val bmiNum = view.findViewById<TextView>(R.id.bmiNum)
        bmiNum.text = String.format("%.2f", bmi) // 소수점 버림
        val bmiNumValue = bmiNum.text.toString()

        val bmiString = view.findViewById<TextView>(R.id.bmiString)
        bmiString.text = resultText
        val bmiStringValue = bmiString.text.toString()

        val addMemoButton = view.findViewById<TextView>(R.id.addMemoButton)

        addMemoButton.setOnClickListener {

            EventBus.getDefault().postSticky(ResultF2toMemoF2BusEventItem(heightValue.toString(), bmiNumValue, bmiStringValue, 1))
            (activity as MainActivity).setFrag(2)

        }

        return view
    }



    override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this)
    }


    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)  //알아서 실행?
    fun addEvent(event: MemoF1toResultFEventBusItem) {

        heightValue = event.heightEvent
        weightValue = event.weightEvent

    }


}