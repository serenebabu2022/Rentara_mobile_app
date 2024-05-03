package ie.setu.rentara_app.models

import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseUser

interface RentaraStore {
        fun findAll(rentalsList:
                    MutableLiveData<List<RentaraModel>>)
        fun findAll(userid:String,
                    rentalsList:
                    MutableLiveData<List<RentaraModel>>)
        fun findById(userid:String, listingid: String,
                     listing: MutableLiveData<RentaraModel>)
        fun create(firebaseUser: MutableLiveData<FirebaseUser>, donation: RentaraModel)
        fun delete(userid:String, listingid: String)
        fun update(userid:String, listingid: String, donation: RentaraModel)
}
