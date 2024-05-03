package ie.setu.rentara.ui.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseUser
import ie.setu.rentara_app.firebase.FirebaseDBManager
import ie.setu.rentara_app.models.RentaraModel

class ListViewModel : ViewModel() {

    private val status = MutableLiveData<Boolean>()

    val observableStatus: LiveData<Boolean>
        get() = status

    fun addListing(firebaseUser: MutableLiveData<FirebaseUser>,
                   listing: RentaraModel) {
        status.value = try {
            //DonationManager.create(donation)
            FirebaseDBManager.create(firebaseUser,listing)
            true
        } catch (e: IllegalArgumentException) {
            false
        }
    }
}



