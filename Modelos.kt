open class Usuario(val id: String, var nombre: String, var correo: String)

class Administrador(id: String, nombre: String, correo: String, var nivelAcceso: Int = 1) : Usuario(id, nombre, correo)

class Cliente(id: String, nombre: String, correo: String, var metodoPagoPredeterminado: String = "Efectivo") : Usuario(id, nombre, correo)

data class Prenda(
    val id: String,
    var nombre: String,
    var talla: String,
    var categoria: String,
    var precio: Double,
    var estado: String = "DISPONIBLE" 
)

class ModuloGestionPrendas(private val inventario: MutableList<Prenda>) : GestorCRUD<Prenda> {
    override fun crear(item: Prenda) {
        inventario.add(item)
        println("Prenda registrada con éxito: ${item.nombre}")
    }

    override fun listar(): List<Prenda> {
        if (inventario.isEmpty()) println("El catálogo está vacío.")
        return inventario
    }

    override fun actualizar(id: String, itemActualizado: Prenda): Boolean {
        val index = inventario.indexOfFirst { it.id == id }
        if (index != -1) {
            inventario[index] = itemActualizado
            println("Prenda actualizada correctamente.")
            return true
        }
        println("Error: Prenda con ID $id no encontrada.")
        return false
    }

    override fun eliminar(id: String): Boolean {
        val eliminada = inventario.removeIf { it.id == id }
        if (eliminada) {
            println("Prenda eliminada del catálogo.")
        } else {
            println("Error: Prenda con ID $id no encontrada.")
        }
        return eliminada
    }
}