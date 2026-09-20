import java.io.File
import java.util.logging.FileHandler
import java.util.logging.Level
import java.util.logging.Logger
import java.util.logging.SimpleFormatter

object LogErrores {
    private val logger: Logger = Logger.getLogger("VintageFlow")

    init {
        File("logs").mkdirs()

        val handler = FileHandler(
            "logs/vintage-flow.%g.log",
            20 * 1024 * 1024,
            5,
            true
        )
        handler.formatter = SimpleFormatter()

        logger.addHandler(handler)
        logger.useParentHandlers = false
    }

    fun registrarError(operacion: String, error: Exception) {
        logger.log(Level.SEVERE, "Error durante la operación: $operacion", error)
    }
}
