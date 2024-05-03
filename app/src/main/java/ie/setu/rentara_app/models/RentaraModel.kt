package ie.setu.rentara_app.models
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
@Parcelize
data class RentaraModel (val _id: String = "N/A",
                         var title: String="",
                         var description: String="",
                         var rentalPeriodType: String = "N/A",
                         var price: Int = 0,
                         val email: String = "joe@bloggs.com") : Parcelable