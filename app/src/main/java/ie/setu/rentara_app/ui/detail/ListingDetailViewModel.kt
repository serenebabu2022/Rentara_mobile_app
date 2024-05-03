package ie.setu.rentara_app.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ie.setu.rentara_app.models.RentaraManager
import ie.setu.rentara_app.models.RentaraModel
import timber.log.Timber

class ListingDetailViewModel : ViewModel() {
    private val listing = MutableLiveData<RentaraModel>()

    var observableListing: LiveData<RentaraModel>
        get() = listing
        set(value) {listing.value = value.value}

    fun getListing(email:String, id: String) {
        try {
            RentaraManager.findById(email, id, listing)
            Timber.i("Detail getListing() Success : ${listing.value.toString()}")
        }
        catch (e: Exception) {
            Timber.i("Detail getListing() Error : $e.message")
        }
    }

    fun updateListing(email:String, id: String,listing: RentaraModel) {
        try {
            RentaraManager.update(email, id, listing)
            Timber.i("Detail update() Success : $listing")
        }
        catch (e: Exception) {
            Timber.i("Detail update() Error : $e.message")
        }
    }
}