package ie.setu.rentara_app.models

import androidx.lifecycle.MutableLiveData

interface RentaraStore {
    fun findAll(rentalsList: MutableLiveData<List<RentaraModel>>)
    fun findById(id: Long) : RentaraModel?
    fun create(list: RentaraModel)
}