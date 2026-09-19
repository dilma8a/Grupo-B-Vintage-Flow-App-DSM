// Módulo de procesamiento y calculo — logica de negocio central.

class ModuloProcesamiento(private val inventario: List<Prenda>) {

    // Suma el precio de todas las prendas, sin importar su estado.
    fun valorTotalInventario(): Double {
        var total = 0.0
        for (prenda in inventario) {
            total += prenda.precio
        }
        return total
    }

    // Suma el precio unicamente de las prendas que siguen disponibles.
    fun valorInventarioDisponible(): Double {
        var total = 0.0
        for (prenda in inventario) {
            if (prenda.estado == "DISPONIBLE") {
                total += prenda.precio
            }
        }
        return total
    }

    // Precio promedio del catalogo general.
    fun precioPromedio(): Double {
        if (inventario.isEmpty()) return 0.0
        return valorTotalInventario() / inventario.size
    }

    // Busca la prenda con el precio mas alto.
    fun prendaMasCara(): Prenda? {
        var masCara: Prenda? = null
        for (prenda in inventario) {
            val actual = masCara
            if (actual == null || prenda.precio > actual.precio) {
                masCara = prenda
            }
        }
        return masCara
    }

    // Cuenta cuantas prendas hay por cada estado.
    fun resumenPorEstado(): Map<String, Int> {
        val resumen = mutableMapOf<String, Int>()
        for (prenda in inventario) {
            resumen[prenda.estado] = (resumen[prenda.estado] ?: 0) + 1
        }
        return resumen
    }

    // Categorías únicas registradas en el catálogo .
    fun categoriasRegistradas(): Set<String> {
        val categorias = mutableSetOf<String>()
        for (prenda in inventario) {
            categorias.add(prenda.categoria)
        }
        return categorias
    }

    // Imprime en consola el reporte/resumen general del sistema.
    fun generarReporte() {
        println("===== REPORTE VINTAGE FLOW =====")
        println("Prendas registradas: ${inventario.size}")
        println("Valor total del inventario: $${valorTotalInventario()}")
        println("Valor de inventario disponible: $${valorInventarioDisponible()}")
        println("Precio promedio: $${precioPromedio()}")
        println("Categorías registradas: ${categoriasRegistradas().joinToString(", ")}")
        println("Distribución por estado:")
        for ((estado, cantidad) in resumenPorEstado()) {
            println("  - $estado: $cantidad")
        }
        val top = prendaMasCara()
        if (top != null) {
            println("Prenda más cara: ${top.nombre} ($${top.precio})")
        }
    }
}