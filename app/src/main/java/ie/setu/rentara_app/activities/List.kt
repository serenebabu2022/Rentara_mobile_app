package ie.setu.rentara_app.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import ie.setu.rentara_app.R
import ie.setu.rentara_app.databinding.ActivityListBinding
import ie.setu.rentara_app.main.ListXApp
import ie.setu.rentara_app.models.ListModel

class List : AppCompatActivity() {
    private lateinit var listLayout : ActivityListBinding
    lateinit var app: ListXApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        listLayout = ActivityListBinding.inflate(layoutInflater)
        setContentView(listLayout.root)
        app = this.application as ListXApp


        listLayout.amountPicker.minValue = 1
        listLayout.amountPicker.maxValue = 1000

        listLayout.amountPicker.setOnValueChangedListener { _, _, newVal ->
            //Display the newly selected number to paymentAmount
            listLayout.paymentAmount.setText("$newVal")
        }

        listLayout.listButton.setOnClickListener {
            val amount = if (listLayout.paymentAmount.text.isNotEmpty())
                listLayout.paymentAmount.text.toString().toInt() else listLayout.amountPicker.value
            if(amount!==0){
                val rentalType = if(listLayout.rentalPeriodType.checkedRadioButtonId == R.id.Direct)
                    "Daily" else "Weekly"
                app.listStore.create(ListModel(rentalPeriodType = rentalType, price = amount))
                println("amount is $amount")
            }
        }
    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_list, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_my_listings -> {
                startActivity(Intent(this, MyListings::class.java))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

}