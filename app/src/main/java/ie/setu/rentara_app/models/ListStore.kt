package ie.setu.rentara_app.models

interface ListStore {
    fun findAll() : List<ListModel>
    fun findById(id: Long) : ListModel?
    fun create(list: ListModel)
}