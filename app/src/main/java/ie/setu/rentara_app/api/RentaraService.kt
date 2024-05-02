package ie.setu.rentara_app.api


import ie.setu.rentara_app.models.RentaraModel
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface RentaraService {
    @GET("/donations")
    fun getall(): Call<List<RentaraModel>>

    @GET("/donations/{id}")
    fun get(@Path("id") id: String): Call<RentaraModel>

    @DELETE("/donations/{id}")
    fun delete(@Path("id") id: String): Call<RentaraWrapper>

    @POST("/donations")
    fun post(@Body rental: RentaraModel): Call<RentaraWrapper>

    @PUT("/donations/{id}")
    fun put(@Path("id") id: String,
            @Body rental: RentaraModel
    ): Call<RentaraWrapper>
}