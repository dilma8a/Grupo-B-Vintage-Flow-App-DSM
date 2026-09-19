import org.junit.Assert.assertEquals
import org.junit.Assert.assertThrows
import org.junit.Test

class ValidadorTest {

    private fun prendaValida(id: String = "P001") =
        Prenda(id = id, nombre = "Camisa", talla = "M", categoria = "Camisas", precio = 10.0)

    @Test
    fun probarPrendaValidaNoLanzaExcepcion() {
        Validador.validarPrenda(prendaValida())
    }

    @Test
    fun probarPrecioNegativoLanzaExcepcion() {
        assertThrows(PrecioInvalidoException::class.java) {
            Validador.validarPrenda(prendaValida().copy(precio = -5.0))
        }
    }

    @Test
    fun probarNombreVacioLanzaExcepcion() {
        assertThrows(CampoVacioException::class.java) {
            Validador.validarPrenda(prendaValida().copy(nombre = "  "))
        }
    }

    @Test
    fun probarTallaInvalidaLanzaExcepcion() {
        assertThrows(TallaInvalidaException::class.java) {
            Validador.validarPrenda(prendaValida().copy(talla = "XXXL"))
        }
    }

    @Test
    fun probarEstadoInvalidoLanzaExcepcion() {
        assertThrows(EstadoInvalidoException::class.java) {
            Validador.validarPrenda(prendaValida().copy(estado = "PERDIDA"))
        }
    }

    @Test
    fun probarIdDuplicadoLanzaExcepcion() {
        val existente = listOf(prendaValida("P001"))
        assertThrows(ProductoDuplicadoException::class.java) {
            Validador.validarPrenda(prendaValida("P001"), existente, esNueva = true)
        }
    }
}

class GestorPrendasValidadoTest {

    @Test
    fun probarCrearPrendaValidaSeAgregaAlInventario() {
        val inventario = mutableListOf<Prenda>()
        val gestor = GestorPrendasValidado(ModuloGestionPrendas(inventario))

        gestor.crear(Prenda(id = "P001", nombre = "Chaqueta", talla = "L", categoria = "Chaquetas", precio = 45.0))

        assertEquals(1, gestor.listar().size)
    }

    @Test
    fun probarCrearPrendaInvalidaNoSeAgrega() {
        val inventario = mutableListOf<Prenda>()
        val gestor = GestorPrendasValidado(ModuloGestionPrendas(inventario))

        assertThrows(PrecioInvalidoException::class.java) {
            gestor.crear(Prenda(id = "P002", nombre = "Vestido", talla = "S", categoria = "Vestidos", precio = -1.0))
        }
        assertEquals(0, gestor.listar().size)
    }

    @Test
    fun probarTallaInvalidaBloqueaCreacion() {
        val inventario = mutableListOf<Prenda>()
        val gestor = GestorPrendasValidado(ModuloGestionPrendas(inventario))

        assertThrows(TallaInvalidaException::class.java) {
            gestor.crear(Prenda(id = "P003", nombre = "Suéter", talla = "XXXL", categoria = "Chaquetas", precio = 20.0))
        }
        assertEquals(0, gestor.listar().size)
    }
}

class ReporteGeneradorTest {

    private fun inventarioDePrueba(): List<Prenda> = listOf(
        Prenda(id = "P001", nombre = "Camisa a cuadros", talla = "M", categoria = "Camisas", precio = 10.0),
        Prenda(id = "P002", nombre = "Vestido floral", talla = "S", categoria = "Vestidos", precio = 20.0)
    )

    @Test
    fun probarResumenPorTallaAgrupaCorrectamente() {
        val generador = ReporteGenerador(inventarioDePrueba())
        val resumen = generador.resumenPorTalla()

        assertEquals(1, resumen["M"])
        assertEquals(1, resumen["S"])
    }

    @Test
    fun probarInventarioVacioLanzaExcepcion() {
        val generador = ReporteGenerador(emptyList())
        assertThrows(InventarioVacioException::class.java) {
            generador.reporteCompletoTexto()
        }
    }
}
