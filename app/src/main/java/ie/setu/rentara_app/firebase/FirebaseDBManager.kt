package ie.setu.rentara_app.firebase

import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import ie.setu.rentara_app.models.RentaraModel
import ie.setu.rentara_app.models.RentaraStore
import timber.log.Timber

object FirebaseDBManager: RentaraStore {
    var database: DatabaseReference = FirebaseDatabase.getInstance().reference
    override fun findAll(rentalsList: MutableLiveData<List<RentaraModel>>) {
        TODO("Not yet implemented")
    }

    override fun findAll(userid: String, listingsList: MutableLiveData<List<RentaraModel>>) {

        database.child("user-listing").child(userid)
            .addValueEventListener(object : ValueEventListener {
                override fun onCancelled(error: DatabaseError) {
                    Timber.i("Firebase Listing error : ${error.message}")
                }

                override fun onDataChange(snapshot: DataSnapshot) {
                    val localList = ArrayList<RentaraModel>()
                    val children = snapshot.children
                    children.forEach {
                        val listing = it.getValue(RentaraModel::class.java)
                        localList.add(listing!!)
                    }
                    database.child("user-listing").child(userid)
                        .removeEventListener(this)

                    listingsList.value = localList
                }
            })
    }

    override fun findById(userid: String, listingid: String, listing: MutableLiveData<RentaraModel>) {

        database.child("user-listing").child(userid)
            .child(listingid).get().addOnSuccessListener {
                listing.value = it.getValue(RentaraModel::class.java)
                Timber.i("firebase Got value ${it.value}")
            }.addOnFailureListener{
                Timber.e("firebase Error getting data $it")
            }
    }

    override fun create(firebaseUser: MutableLiveData<FirebaseUser>, listing: RentaraModel) {
        Timber.i("Firebase DB Reference : $database")

        val uid = firebaseUser.value!!.uid
        val key = database.child("listing").push().key

        if (key == null) {
            Timber.i("Firebase Error : Key Empty")
            return
        }

        listing.uid = key
        val listingValues = listing.toMap()

        val childAdd = HashMap<String, Any>()
        childAdd["/listing/$key"] = listingValues
        childAdd["/user-listing/$uid/$key"] = listingValues
        database.updateChildren(childAdd)
    }

    override fun delete(userid: String, listingid: String) {

        val childDelete : MutableMap<String, Any?> = HashMap()
        childDelete["/listing/$listingid"] = null
        childDelete["/user-listing/$userid/$listingid"] = null

        database.updateChildren(childDelete)
    }

    override fun update(userid: String, listingid: String, listing: RentaraModel) {

        val listingValues = listing.toMap()

        val childUpdate : MutableMap<String, Any?> = HashMap()
        childUpdate["listing/$listingid"] = listingValues
        childUpdate["user-listing/$userid/$listingid"] = listingValues

        database.updateChildren(childUpdate)
    }
}