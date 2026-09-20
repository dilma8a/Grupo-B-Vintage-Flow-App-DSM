class MenuConsola(
    // Tipo GestorCRUD<Prenda> para poder usar GestorPrendasValidado
    private val gestor: GestorCRUD<Prenda>,
    private val procesamiento: ModuloProcesamiento,
    private val reporteGenerador: ReporteGenerador // TODO: clase de José David — confirmar nombre del método si difiere de generarReporte()
) {

    fun iniciar() {
        var opcion: Int? = null
        do {
            mostrarMenu()
            opcion = leerOpcion()
            when (opcion) {
                1 -> registrarPrenda()
                2 -> listarPrendas()
                3 -> actualizarPrenda()
                4 -> eliminarPrenda()
                5 -> reporteGenerador.generarReporte()
                0 -> println("Saliendo del sistema... ¡Hasta pronto!")
                null -> println("Entrada inválida. Ingresa un número de opción.")
                else -> println("Opción inválida. Intenta nuevamente.")
            }
            println()
        } while (opcion != 0)
    }

    private fun mostrarMenu() {
        println("========= VINTAGE FLOW =========")
        println("1. Registrar prenda")
        println("2. Listar catálogo")
        println("3. Actualizar prenda")
        println("4. Eliminar prenda")
        println("5. Generar reporte")
        println("0. Salir")
        print("Seleccione una opción: ")
    }

    // Valida que la entrada sea un número entero antes de continuar.
    // Devuelve null si la entrada no es un número válido, en vez de un
    // valor "mágico" como -1 (evita reportar -1 como opción inválida
    // silenciosamente y deja explícito el caso de error).
    private fun leerOpcion(): Int? {
        val entrada = readLine()
        return entrada?.toIntOrNull()
    }

    private fun registrarPrenda() {
        try {
            print("ID: ")
            val id = leerTextoObligatorio()
            print("Nombre: ")
            val nombre = leerTextoObligatorio()
            print("Talla: ")
            val talla = leerTextoObligatorio()
            print("Categoría: ")
            val categoria = leerTextoObligatorio()
            print("Precio: ")
            val precio = leerPrecioValido()

            gestor.crear(Prenda(id, nombre, talla, categoria, precio))
        } catch (e: Exception) {
            LogErrores.registrarError("Error al registrar prenda", e)
            println("Error al registrar la prenda: ${e.message}")
        }
    }

    // Muestra el catálogo con formato tabular legible.
    private fun listarPrendas() {
        val prendas = gestor.listar()
        if (prendas.isEmpty()) return

        println("%-10s %-20s %-6s %-15s %-10s %-12s".format(
            "ID", "Nombre", "Talla", "Categoría", "Precio", "Estado"
        ))
        println("-".repeat(80))
        for (p in prendas) {
            println("%-10s %-20s %-6s %-15s $%-9.2f %-12s".format(
                p.id, p.nombre, p.talla, p.categoria, p.precio, p.estado
            ))
        }
    }

    private fun actualizarPrenda() {
        try {
            print("ID de la prenda a actualizar: ")
            val id = leerTextoObligatorio()
            print("Nuevo nombre: ")
            val nombre = leerTextoObligatorio()
            print("Nueva talla: ")
            val talla = leerTextoObligatorio()
            print("Nueva categoría: ")
            val categoria = leerTextoObligatorio()
            print("Nuevo precio: ")
            val precio = leerPrecioValido()
            print("Nuevo estado (DISPONIBLE/VENDIDO/RESERVADO): ")
            val estado = leerTextoObligatorio()

            gestor.actualizar(id, Prenda(id, nombre, talla, categoria, precio, estado))
        } catch (e: Exception) {
            LogErrores.registrarError("Error al actualizar prenda", e)
            println("Error al actualizar la prenda: ${e.message}")
        }
    }

    private fun eliminarPrenda() {
        print("ID de la prenda a eliminar: ")
        val id = leerTextoObligatorio()
        gestor.eliminar(id)
    }

    // --- Utilidades de validación de entrada ---

    private fun leerTextoObligatorio(): String {
        val texto = readLine()?.trim()
        if (texto.isNullOrEmpty()) throw IllegalArgumentException("El campo no puede estar vacío.")
        return texto
    }

    private fun leerPrecioValido(): Double {
        val entrada = readLine()?.trim()
        val precio = entrada?.toDoubleOrNull()
            ?: throw IllegalArgumentException("El precio debe ser un número válido.")
        if (precio < 0) throw IllegalArgumentException("El precio no puede ser negativo.")
        return precio
    }
}
