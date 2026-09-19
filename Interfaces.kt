interface GestorCRUD<T> {
    fun crear(item: T)
    fun listar(): List<T>
    fun actualizar(id: String, itemActualizado: T): Boolean
    fun eliminar(id: String): Boolean
}