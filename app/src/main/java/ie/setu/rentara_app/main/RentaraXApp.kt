package ie.setu.rentara_app.main

import android.app.Application
import timber.log.Timber

//import ie.setu.rentara_app.models.RentaraMemStore
import ie.setu.rentara_app.models.RentaraStore

class RentaraXApp: Application() {
//    lateinit var rentaraStore: RentaraStore

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
//        rentaraStore = RentaraMemStore()
//        Timber.i("Starting DonationX Application")

    }
}