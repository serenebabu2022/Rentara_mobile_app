package ie.setu.rentara_app.models

import android.os.Parcelable
import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
@IgnoreExtraProperties
@Parcelize
data class RentaraModel(
    var uid: String? = "",
    var title: String = "N/A",
    var description: String = "",
    var rentalPeriodType: String = "",
    var price: Int = 0,
    var email: String? = "joe@bloggs.com")
    : Parcelable
{
    @Exclude
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "uid" to uid,
            "title" to title,
            "description" to description,
            "rentalPeriodType" to rentalPeriodType,
            "price" to price,
            "email" to email
        )
    }
}