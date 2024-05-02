package ie.setu.rentara.ui.listings

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ie.setu.rentara_app.models.RentaraManager
import ie.setu.rentara_app.models.RentaraModel

class ListingsViewModel : ViewModel() {

    private val rentalsList = MutableLiveData<List<RentaraModel>>()

    val observableRentalsList: LiveData<List<RentaraModel>>
        get() = rentalsList

    init {
        load()
    }

    fun load() {
        try {
            RentaraManager.findAll(rentalsList)
            println("Retrofit Success : $rentalsList.value")
        }
        catch (e: Exception) {
            println("Retrofit Error : $e.message")
        }
    }
}