package ie.setu.rentara_app.models

interface RentaraStore {
    fun findAll() : List<RentaraModel>
    fun findById(id: Long) : RentaraModel?
    fun create(list: RentaraModel)
}