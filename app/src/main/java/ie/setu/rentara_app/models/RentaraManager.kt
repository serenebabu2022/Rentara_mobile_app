package ie.setu.rentara_app.models

import androidx.lifecycle.MutableLiveData
import ie.setu.rentara_app.api.RentaraClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

var lastId = 0L

internal fun getId(): Long {
    return lastId++
}

object RentaraManager :  RentaraStore {

    val listings = ArrayList<RentaraModel>()

    override fun findAll(rentalsList: MutableLiveData<List<RentaraModel>>) {

        val call = RentaraClient.getApi().getall()

        call.enqueue(object : Callback<List<RentaraModel>> {
            override fun onResponse(call: Call<List<RentaraModel>>,
                                    response: Response<List<RentaraModel>>
            ) {
                println("response is $response")
                rentalsList.value = response.body() as ArrayList<RentaraModel>
                println("Retrofit JSON = ${response.body()}")
            }

            override fun onFailure(call: Call<List<RentaraModel>>, t: Throwable) {
                println("Retrofit Error : $t.message")
            }
        })
    }

    override fun findById(id:Long) : RentaraModel? {
        val foundRental: RentaraModel? = listings.find { it.id == id }
        return foundRental
    }

    override fun create(rentals: RentaraModel) {
        rentals.id = getId()
        listings.add(rentals)
        logAll()
    }

    fun logAll() {
        println("** Rentals List **")
//        donations.forEach { Timber.v("Donate ${it}") }
    }
}