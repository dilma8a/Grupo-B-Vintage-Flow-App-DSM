import java.io.File
import java.io.PrintWriter
import java.io.StringWriter
import java.text.SimpleDateFormat
import java.util.Date
import java.util.logging.FileHandler
import java.util.logging.Formatter
import java.util.logging.Level
import java.util.logging.LogRecord
import java.util.logging.Logger

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
        handler.formatter = object : Formatter() {
            override fun format(record: LogRecord): String {
                val fecha = SimpleDateFormat("yyyy/MM/dd").format(Date(record.millis))
                val hora = SimpleDateFormat("hh:mm:ss a").format(Date(record.millis))
                val builder = StringBuilder()
                builder.append("[$fecha $hora] ")
                builder.append("${record.level}: ")
                builder.append(record.message)
                builder.append(System.lineSeparator())

                if (record.thrown != null) {
                    val sw = StringWriter()
                    val pw = PrintWriter(sw)
                    record.thrown.printStackTrace(pw)
                    builder.append(sw.toString())
                }

                return builder.toString()
            }
        }

        logger.addHandler(handler)
        logger.useParentHandlers = false
    }

    fun registrarError(operacion: String, error: Exception) {
        logger.log(Level.SEVERE, "Error durante la operación: $operacion", error)
    }
}
