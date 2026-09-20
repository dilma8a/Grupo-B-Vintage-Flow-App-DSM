fun main() {
    val inventarioPrincipal = mutableListOf<Prenda>()
    val gestor = GestorPrendasValidado(ModuloGestionPrendas(inventarioPrincipal))
    val procesamiento = ModuloProcesamiento(inventarioPrincipal)
    val reporteGenerador = ReporteGenerador(inventarioPrincipal)

    val chaqueta = Prenda(id = "VINT-001", nombre = "Chaqueta Retro", talla = "L", categoria = "Chaquetas", precio = 45.00)
    try {
        gestor.crear(chaqueta)
    } catch (e: Exception) {
        LogErrores.registrarError("cargar dato de ejemplo", e)
        println("No se pudo cargar el dato de ejemplo: ${e.message}")
    }
    MenuConsola(gestor, procesamiento, reporteGenerador).iniciar()
}