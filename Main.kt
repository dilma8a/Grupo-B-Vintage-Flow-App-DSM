fun main() {
    val inventarioPrincipal = mutableListOf<Prenda>()
    val gestor = ModuloGestionPrendas(inventarioPrincipal)

    val chaqueta = Prenda(id = "VINT-001", nombre = "Chaqueta Retro", talla = "L", categoria = "Chaquetas", precio = 45.00)
    gestor.crear(chaqueta)
    gestor.listar().forEach { println(it) }
}