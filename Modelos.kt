// 1. Interfaces compartidas (Tu responsabilidad transversal)
interface GestorCRUD<T> {
    fun crear(item: T)
    fun listar(): List<T>
    fun actualizar(id: String, itemActualizado: T): Boolean
    fun eliminar(id: String): Boolean
}

// 2. Herencia de Usuarios (Tu responsabilidad transversal)
open class Usuario(
    val id: String,
    var nombre: String,
    var correo: String
)

class Administrador(
    id: String,
    nombre: String,
    correo: String,
    var nivelAcceso: Int = 1
) : Usuario(id, nombre, correo)

class Cliente(
    id: String,
    nombre: String,
    correo: String,
    var metodoPagoPredeterminado: String = "Efectivo"
) : Usuario(id, nombre, correo)

// 3. Modelo de Datos y Módulo de Gestión (Tu requerimiento funcional)
data class Prenda(
    val id: String,
    var nombre: String,
    var talla: String,
    var categoria: String,
    var precio: Double,
    var estado: String = "DISPONIBLE" // DISPONIBLE, RESERVADA o VENDIDA
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

// 4. Función principal de prueba
fun main() {
    val inventarioPrincipal = mutableListOf<Prenda>()
    val gestor = ModuloGestionPrendas(inventarioPrincipal)

    // Crear
    val chaqueta = Prenda(id = "VINT-001", nombre = "Chaqueta de Cuero Retro", talla = "L", categoria = "Chaquetas", precio = 45.00)
    gestor.crear(chaqueta)

    // Listar
    println("\n--- Catálogo actual ---")
    gestor.listar().forEach { println(it) }

    // Actualizar
    println("\n--- Actualizando precio y estado ---")
    val chaquetaActualizada = chaqueta.copy(precio = 39.99, estado = "RESERVADA")
    gestor.actualizar("VINT-001", chaquetaActualizada)
    gestor.listar().forEach { println(it) }

    // Eliminar
    println("\n--- Eliminando prenda ---")
    gestor.eliminar("VINT-001")
    gestor.listar()
}