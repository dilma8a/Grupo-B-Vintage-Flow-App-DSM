package com.example.vintageflow.utils

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

//
class Producto(
    val nombre: String,
    val precio: Double,
    var cantidadDisponible: Int,
    var estado: String = "DISPONIBLE"
)

// Módulo reportes corrutinas
object ReportesCatalogo {

    suspend fun obtenerPrendasStockBajo(catalogo: List<Producto>): List<Producto> = coroutineScope {
        println("Generando reporte de stock bajo...")
        delay(1500) // Simulación de carga
        catalogo.filter { it.cantidadDisponible in 1..2 }
    }

    suspend fun calcularValorReservado(catalogo: List<Producto>): Double = coroutineScope {
        println("Calculando dinero en reservas...")
        delay(1000) // Simulación de carga

        var total = 0.0
        val prendasReservadas = catalogo.filter { it.estado == "RESERVADO" }

        for (prenda in prendasReservadas) {
            total += (prenda.precio * prenda.cantidadDisponible)
        }
        return@coroutineScope total
    }
}

