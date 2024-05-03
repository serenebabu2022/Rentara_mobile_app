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
    fun findall(): Call<List<RentaraModel>>

    @GET("/donations/{email}")
    fun findall(@Path("email") email: String?)
            : Call<List<RentaraModel>>

    @GET("/donations/{email}/{id}")
    fun get(@Path("email") email: String?,
            @Path("id") id: String): Call<RentaraModel>

    @DELETE("/donations/{email}/{id}")
    fun delete(@Path("email") email: String?,
               @Path("id") id: String): Call<RentaraWrapper>

    @POST("/donations/{email}")
    fun post(@Path("email") email: String?,
             @Body donation: RentaraModel)
            : Call<RentaraWrapper>

    @PUT("/donations/{email}/{id}")
    fun put(@Path("email") email: String?,
            @Path("id") id: String,
            @Body donation: RentaraModel
    ): Call<RentaraWrapper>
}