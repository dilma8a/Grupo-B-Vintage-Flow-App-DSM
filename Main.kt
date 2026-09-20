
import java.io.PrintStream
import java.nio.charset.StandardCharsets

fun main() {
    System.setOut(PrintStream(System.out, true, StandardCharsets.UTF_8.name()))
    System.setErr(PrintStream(System.err, true, StandardCharsets.UTF_8.name()))

    val inventarioPrincipal = mutableListOf<Prenda>()
    val gestor = ModuloGestionPrendas(inventarioPrincipal)
    val gestorBase = ModuloGestionPrendas(inventarioPrincipal)
    val gestorValidado = GestorPrendasValidado(gestorBase)
    val procesamiento = ModuloProcesamiento(inventarioPrincipal)
    val reporteGenerador = ReporteGenerador(inventarioPrincipal)

    val chaqueta = Prenda(id = "VINT-001", nombre = "Chaqueta Retro", talla = "L", categoria = "Chaquetas", precio = 45.00)
    gestor.crear(chaqueta)

    MenuConsola(gestorValidado, procesamiento, reporteGenerador).iniciar()
}