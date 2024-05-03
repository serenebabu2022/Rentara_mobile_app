package ie.setu.rentara_app.models

import androidx.lifecycle.MutableLiveData
import ie.setu.rentara_app.api.RentaraClient
import ie.setu.rentara_app.api.RentaraWrapper
import ie.setu.rentara_app.main.RentaraXApp
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber

object RentaraManager :  RentaraStore {

    val listings = ArrayList<RentaraModel>()

    override fun findAll(rentaraList: MutableLiveData<List<RentaraModel>>) {

        val call = RentaraClient.getApi().findall()

        call.enqueue(object : Callback<List<RentaraModel>> {
            override fun onResponse(call: Call<List<RentaraModel>>,
                                    response: Response<List<RentaraModel>>
            ) {
                rentaraList.value = response.body() as ArrayList<RentaraModel>
                Timber.i("Retrofit findAll() = ${response.body()}")
            }

            override fun onFailure(call: Call<List<RentaraModel>>, t: Throwable) {
                Timber.i("Retrofit findAll() Error : $t.message")
            }
        })
    }

    override fun findAll(email: String, donationsList: MutableLiveData<List<RentaraModel>>) {

        val call = RentaraClient.getApi().findall(email)

        call.enqueue(object : Callback<List<RentaraModel>> {
            override fun onResponse(call: Call<List<RentaraModel>>,
                                    response: Response<List<RentaraModel>>
            ) {
                donationsList.value = response.body() as ArrayList<RentaraModel>
                Timber.i("Retrofit findAll() = ${response.body()}")
            }

            override fun onFailure(call: Call<List<RentaraModel>>, t: Throwable) {
                Timber.i("Retrofit findAll() Error : $t.message")
            }
        })
    }

    override fun findById(email: String, id: String, listing: MutableLiveData<RentaraModel>)   {

        val call = RentaraClient.getApi().get(email,id)

        call.enqueue(object : Callback<RentaraModel> {
            override fun onResponse(call: Call<RentaraModel>, response: Response<RentaraModel>) {
                listing.value = response.body() as RentaraModel
                Timber.i("Retrofit findById() = ${response.body()}")
            }

            override fun onFailure(call: Call<RentaraModel>, t: Throwable) {
                Timber.i("Retrofit findById() Error : $t.message")
            }
        })
    }

    override fun create( donation: RentaraModel) {

        val call = RentaraClient.getApi().post(donation.email,donation)

        call.enqueue(object : Callback<RentaraWrapper> {
            override fun onResponse(call: Call<RentaraWrapper>,
                                    response: Response<RentaraWrapper>
            ) {
                val donationWrapper = response.body()
                if (donationWrapper != null) {
//                    Timber.i("Retrofit ${donationWrapper.message}")
                    Timber.i("Retrofit ${donationWrapper.data.toString()}")
                }
            }

            override fun onFailure(call: Call<RentaraWrapper>, t: Throwable) {
                Timber.i("Retrofit Error : $t.message")
            }
        })
    }

    override fun delete(email: String,id: String) {

        val call = RentaraClient.getApi().delete(email,id)

        call.enqueue(object : Callback<RentaraWrapper> {
            override fun onResponse(call: Call<RentaraWrapper>,
                                    response: Response<RentaraWrapper>
            ) {
                val donationWrapper = response.body()
                if (donationWrapper != null) {
//                    Timber.i("Retrofit Delete ${donationWrapper.message}")
                    Timber.i("Retrofit Delete ${donationWrapper.data.toString()}")
                }
            }

            override fun onFailure(call: Call<RentaraWrapper>, t: Throwable) {
                Timber.i("Retrofit Delete Error : $t.message")
            }
        })
    }

    override fun update(email: String,id: String, listing: RentaraModel) {

        val call = RentaraClient.getApi().put(email,id,listing)

        call.enqueue(object : Callback<RentaraWrapper> {
            override fun onResponse(call: Call<RentaraWrapper>,
                                    response: Response<RentaraWrapper>
            ) {
                val rentaraWrapper = response.body()
                if (rentaraWrapper != null) {
//                    Timber.i("Retrofit Update ${donationWrapper.message}")
                    Timber.i("Retrofit Update ${rentaraWrapper.data.toString()}")
                }
            }

            override fun onFailure(call: Call<RentaraWrapper>, t: Throwable) {
                Timber.i("Retrofit Update Error : $t.message")
            }
        })
    }
}