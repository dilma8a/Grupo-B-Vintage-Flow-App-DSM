fun main() {
    val inventarioPrincipal = mutableListOf<Prenda>()
    val gestorBase = ModuloGestionPrendas(inventarioPrincipal)
    val gestorValidado = GestorPrendasValidado(gestorBase)
    val procesamiento = ModuloProcesamiento(inventarioPrincipal)
    val reporteGenerador = ReporteGenerador(inventarioPrincipal)

    MenuConsola(gestorValidado, procesamiento, reporteGenerador).iniciar()
}