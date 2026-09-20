
// Módulo de validaciones y excepciones — José David (CH252330)

// ---- Excepciones ----
// Todas heredan de VintageFlowException para poder capturarlas

sealed class VintageFlowException(mensaje: String) : Exception(mensaje)

class CampoVacioException(campo: String) :
    VintageFlowException("El campo '$campo' no puede estar vacío.")

class PrecioInvalidoException(precio: Double) :
    VintageFlowException("El precio (%.2f) no es válido. Debe ser mayor a 0.".format(precio))

class TallaInvalidaException(talla: String, tallasValidas: Set<String>) :
    VintageFlowException(
        "La talla '$talla' no es válida. Tallas permitidas: ${tallasValidas.joinToString(", ")}"
    )

class CategoriaInvalidaException(categoria: String, categoriasValidas: Set<String>) :
    VintageFlowException(
        "La categoría '$categoria' no es válida. Categorías permitidas: ${categoriasValidas.joinToString(", ")}"
    )

class EstadoInvalidoException(estado: String, estadosValidos: Set<String>) :
    VintageFlowException(
        "El estado '$estado' no es válido. Estados permitidos: ${estadosValidos.joinToString(", ")}"
    )

class ProductoDuplicadoException(id: String) :
    VintageFlowException("Ya existe una prenda registrada con el id '$id'.")

class InventarioVacioException(mensaje: String = "No hay prendas registradas en el inventario.") :
    VintageFlowException(mensaje)

//  Validador:
// Valida los campos de una Prenda real (Modelos.kt).

object Validador {

    val TALLAS_VALIDAS = setOf("XS", "S", "M", "L", "XL", "XXL", "UNICA")

    val CATEGORIAS_VALIDAS = setOf(
        "Camisas", "Blusas", "Pantalones", "Vestidos",
        "Chaquetas", "Faldas", "Zapatos", "Accesorios"
    )

    // Confirmado  el MenuConsola.kt: "DISPONIBLE,VENDIDO,RESERVADO"
    val ESTADOS_VALIDOS = setOf("DISPONIBLE", "RESERVADO", "VENDIDO")

    fun validarTexto(valor: String, nombreCampo: String) {
        if (valor.isBlank()) throw CampoVacioException(nombreCampo)
    }

    fun validarPrecio(precio: Double) {
        if (precio <= 0.0) throw PrecioInvalidoException(precio)
    }

    fun validarTalla(talla: String) {
        if (talla.trim().uppercase() !in TALLAS_VALIDAS) {
            throw TallaInvalidaException(talla, TALLAS_VALIDAS)
        }
    }

    fun validarCategoria(categoria: String) {
        val existe = CATEGORIAS_VALIDAS.any { it.equals(categoria.trim(), ignoreCase = true) }
        if (!existe) throw CategoriaInvalidaException(categoria, CATEGORIAS_VALIDAS)
    }

    fun validarEstado(estado: String) {
        if (estado.trim().uppercase() !in ESTADOS_VALIDOS) {
            throw EstadoInvalidoException(estado, ESTADOS_VALIDOS)
        }
    }

   // Normaliza mayúsculas/minúsculas ANTES de validar y guardar,
    // para que "m" y "M" no se cuenten como tallas distintas.
    fun normalizar(prenda: Prenda) {
        prenda.talla = prenda.talla.trim().uppercase()
        prenda.estado = prenda.estado.trim().uppercase()
        prenda.categoria = CATEGORIAS_VALIDAS
            .firstOrNull { it.equals(prenda.categoria.trim(), ignoreCase = true) }
            ?: prenda.categoria.trim()
    }

    fun validarIdNoDuplicado(id: String, inventarioActual: List<Prenda>) {
        if (inventarioActual.any { it.id == id }) {
            throw ProductoDuplicadoException(id)
        }
    }

    fun validarPrenda(prenda: Prenda, inventarioActual: List<Prenda> = emptyList(), esNueva: Boolean = true) {
        validarTexto(prenda.id, "id")
        validarTexto(prenda.nombre, "nombre")
        validarCategoria(prenda.categoria)
        validarTalla(prenda.talla)
        validarPrecio(prenda.precio)
        validarEstado(prenda.estado)
        if (esNueva) validarIdNoDuplicado(prenda.id, inventarioActual)
    }
}

//Gestor de Prendeas validado
// Envuelve cualquier GestorCRUD<Prenda> agregando validación antes de
// crear/actualizar.
// Único cambio necesario en el resto del código para activarlo:
// en MenuConsola.kt, el parámetro del constructor debe recibir el
// tipo de interfaz GestorCRUD<Prenda> en vez del tipo concreto
// ModuloGestionPrendas (ya aplicado en la copia de este entregable).


class GestorPrendasValidado(
    private val gestorReal: GestorCRUD<Prenda>
) : GestorCRUD<Prenda> {

   override fun crear(item: Prenda) {
        Validador.normalizar(item)
        Validador.validarPrenda(item, gestorReal.listar(), esNueva = true)
        gestorReal.crear(item)
    }

    override fun listar(): List<Prenda> = gestorReal.listar()

    override fun actualizar(id: String, itemActualizado: Prenda): Boolean {
        Validador.normalizar(itemActualizado)
        Validador.validarPrenda(itemActualizado, gestorReal.listar(), esNueva = false)
        return gestorReal.actualizar(id, itemActualizado)
    }
    override fun eliminar(id: String): Boolean = gestorReal.eliminar(id)
}
