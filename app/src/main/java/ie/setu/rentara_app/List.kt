package ie.setu.rentara_app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ie.setu.rentara_app.databinding.ActivityListBinding

class List : AppCompatActivity() {
    private lateinit var listLayout : ActivityListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        listLayout = ActivityListBinding.inflate(layoutInflater)
        setContentView(listLayout.root)

        listLayout.amountPicker.minValue = 1
        listLayout.amountPicker.maxValue = 1000

        listLayout.amountPicker.setOnValueChangedListener { _, _, newVal ->
            //Display the newly selected number to paymentAmount
            listLayout.paymentAmount.setText("$newVal")
        }
    }
}