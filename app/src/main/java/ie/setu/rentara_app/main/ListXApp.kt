package ie.setu.rentara_app.main

import android.app.Application
//import timber.log.Timber

import ie.setu.rentara_app.models.ListMemStore
import ie.setu.rentara_app.models.ListStore

class ListXApp: Application() {
    lateinit var listStore: ListStore

    override fun onCreate() {
        super.onCreate()
//        Timber.plant(Timber.DebugTree())
        listStore = ListMemStore()
//        Timber.i("Starting DonationX Application")

    }
}