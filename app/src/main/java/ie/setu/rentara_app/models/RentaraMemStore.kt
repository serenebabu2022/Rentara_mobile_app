package ie.setu.rentara_app.models

var lastId = 0L

internal fun getId(): Long {
    return lastId++
}

class RentaraMemStore : RentaraStore {

    val listings = ArrayList<RentaraModel>()

    override fun findAll(): List<RentaraModel> {
        return listings
    }

    override fun findById(id:Long) : RentaraModel? {
        val foundDonation: RentaraModel? = listings.find { it.id == id }
        return foundDonation
    }

    override fun create(rentals: RentaraModel) {
        rentals.id = getId()
        listings.add(rentals)
        logAll()
    }

    fun logAll() {
        println("** Donations List **")
//        donations.forEach { Timber.v("Donate ${it}") }
    }
}