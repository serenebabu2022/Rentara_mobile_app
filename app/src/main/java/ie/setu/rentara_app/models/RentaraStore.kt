package ie.setu.rentara_app.models

import androidx.lifecycle.MutableLiveData

interface RentaraStore {
    fun findAll(rentalsList:
                MutableLiveData<List<RentaraModel>>)
    fun findAll(email: String, rentalsList:
    MutableLiveData<List<RentaraModel>>)
    fun findById(email:String, id: String, listing: MutableLiveData<RentaraModel>)
    fun create(rental: RentaraModel)
    fun delete(email: String,id: String)
    fun update(email: String,id: String, listing: RentaraModel)
}
