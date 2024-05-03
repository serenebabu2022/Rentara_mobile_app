package ie.setu.rentara.ui.listings

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseUser
import ie.setu.rentara_app.models.RentaraManager
import ie.setu.rentara_app.models.RentaraModel
import timber.log.Timber

class ListingsViewModel : ViewModel() {

        private val rentalsList =
            MutableLiveData<List<RentaraModel>>()

        val observableRentalsList: LiveData<List<RentaraModel>>
            get() = rentalsList

        var liveFirebaseUser = MutableLiveData<FirebaseUser>()

        init { load() }

        fun load() {
            try {
                RentaraManager.findAll(liveFirebaseUser.value?.email!!, rentalsList)
                Timber.i("Report Load Success : ${rentalsList.value.toString()}")
            }
            catch (e: Exception) {
                Timber.i("Report Load Error : $e.message")
            }
        }

        fun delete(email: String, id: String) {
            try {
                RentaraManager.delete(email,id)
                Timber.i("Report Delete Success")
            }
            catch (e: Exception) {
                Timber.i("Report Delete Error : $e.message")
            }
        }

}