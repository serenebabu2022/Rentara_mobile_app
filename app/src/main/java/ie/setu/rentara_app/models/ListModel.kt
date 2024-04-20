package ie.setu.rentara_app.models
import android.os.Parcelable
import kotlinx.parcelize.Parcelize
@Parcelize
data class ListModel (var id: Long = 0,
                      val title: String="",
                      val description: String="",
                      val rentalPeriodType: String = "N/A",
                 val price: Int = 0) : Parcelable