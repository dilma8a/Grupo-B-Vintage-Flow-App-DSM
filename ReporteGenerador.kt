
// Módulo de reportes — José David (CH252330)

// Complementa a ModuloProcesamiento por composición:
// reutiliza sus cálculos en vez de repetirlos, y solo agrega
// (agrupación por talla) más una versión en texto reutilizable
// para el menú de consola.

class ReporteGenerador(private val inventario: List<Prenda>) {

    private val procesador = ModuloProcesamiento(inventario)

    // Cantidad de prendas por talla (nuevo).
    fun resumenPorTalla(): Map<String, Int> {
        val resumen = mutableMapOf<String, Int>()
        for (prenda in inventario) {
            resumen[prenda.talla] = (resumen[prenda.talla] ?: 0) + 1
        }
        return resumen
    }

    /**
     * Junta los cálculos de ModuloProcesamiento + resumenPorTalla en un
     * String listo para el menú de consola
     * en vez de un println() fijo.
     */
    fun reporteCompletoTexto(): String {
        if (inventario.isEmpty()) throw InventarioVacioException()
        return buildString {
            appendLine("===== REPORTE DE INVENTARIO =====")
            appendLine("Prendas registradas : ${inventario.size}")
            appendLine("Valor total          : $${"%.2f".format(procesador.valorTotalInventario())}")
            appendLine("Valor disponible     : $${"%.2f".format(procesador.valorInventarioDisponible())}")
            appendLine("Precio promedio      : $${"%.2f".format(procesador.precioPromedio())}")
            appendLine()
            appendLine("Por estado:")
            procesador.resumenPorEstado().forEach { (estado, cantidad) -> appendLine("  - $estado: $cantidad") }
            appendLine()
            appendLine("Por talla:")
            resumenPorTalla().forEach { (talla, cantidad) -> appendLine("  - $talla: $cantidad") }
            procesador.prendaMasCara()?.let {
                appendLine()
                appendLine("Prenda más cara: ${it.nombre} ($${"%.2f".format(it.precio)})")
            }
        }
    }
}
