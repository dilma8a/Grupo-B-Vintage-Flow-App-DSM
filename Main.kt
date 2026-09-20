import java.io.PrintStream
import java.nio.charset.StandardCharsets

fun main() {
    System.setOut(PrintStream(System.out, true, StandardCharsets.UTF_8.name()))
    System.setErr(PrintStream(System.err, true, StandardCharsets.UTF_8.name()))

    val inventarioPrincipal = mutableListOf<Prenda>()
    val gestorBase = ModuloGestionPrendas(inventarioPrincipal)
    val gestorValidado = GestorPrendasValidado(gestorBase)
    val procesamiento = ModuloProcesamiento(inventarioPrincipal)
    val reporteGenerador = ReporteGenerador(inventarioPrincipal)

    MenuConsola(gestorValidado, procesamiento, reporteGenerador).iniciar()
}