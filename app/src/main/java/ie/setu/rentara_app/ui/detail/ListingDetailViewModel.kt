package ie.setu.rentara_app.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ie.setu.rentara_app.models.RentaraManager
import ie.setu.rentara_app.models.RentaraModel

class ListingDetailViewModel : ViewModel() {
    private val listing = MutableLiveData<RentaraModel>()

    val observableListing: LiveData<RentaraModel>
        get() = listing

    fun getListing(id: Long) {
        listing.value = RentaraManager.findById(id)
    }
}