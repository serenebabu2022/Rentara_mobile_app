package ie.setu.rentara.ui.listings

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseUser
import ie.setu.rentara_app.firebase.FirebaseDBManager
import ie.setu.rentara_app.models.RentaraModel
import timber.log.Timber

class ListingsViewModel : ViewModel() {

        private val rentalsList =
            MutableLiveData<List<RentaraModel>>()

        val observableRentalsList: LiveData<List<RentaraModel>>
            get() = rentalsList

        var liveFirebaseUser = MutableLiveData<FirebaseUser>()
    var readOnly = MutableLiveData(false)
        init { load() }

        fun load() {
            try {
                readOnly.value = false
                FirebaseDBManager.findAll(liveFirebaseUser.value?.uid!!,rentalsList)
                Timber.i("Report Load Success : ${rentalsList.value.toString()}")
            }
            catch (e: Exception) {
                Timber.i("Report Load Error : $e.message")
            }
        }
    fun loadAll() {
        try {
            readOnly.value = true
            FirebaseDBManager.findAll(rentalsList)
            Timber.i("Report LoadAll Success : ${rentalsList.value.toString()}")
        }
        catch (e: Exception) {
            Timber.i("Report LoadAll Error : $e.message")
        }
    }
    fun delete(userid: String, id: String) {
        try {
            //DonationManager.delete(userid,id)
            FirebaseDBManager.delete(userid,id)
            Timber.i("Listing Delete Success")
        }
        catch (e: Exception) {
            Timber.i("Listing Delete Error : $e.message")
        }
    }

}