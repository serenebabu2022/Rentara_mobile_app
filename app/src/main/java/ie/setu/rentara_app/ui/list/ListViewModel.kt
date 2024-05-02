package ie.setu.rentara.ui.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ie.setu.rentara_app.models.RentaraManager
import ie.setu.rentara_app.models.RentaraModel

class ListViewModel : ViewModel() {

    private val status = MutableLiveData<Boolean>()

    val observableStatus: LiveData<Boolean>
        get() = status

    fun addListing(rentals: RentaraModel) {
        status.value = try {
            RentaraManager.create(rentals)
            true
        } catch (e: IllegalArgumentException) {
            false
        }
    }
}



