package ie.setu.rentara_app.models

var lastId = 0L

internal fun getId(): Long {
    return lastId++
}

class ListMemStore : ListStore {

    val listings = ArrayList<ListModel>()

    override fun findAll(): List<ListModel> {
        return listings
    }

    override fun findById(id:Long) : ListModel? {
        val foundDonation: ListModel? = listings.find { it.id == id }
        return foundDonation
    }

    override fun create(listing: ListModel) {
        listing.id = getId()
        listings.add(listing)
        logAll()
    }

    fun logAll() {
        println("** Donations List **")
//        donations.forEach { Timber.v("Donate ${it}") }
    }
}