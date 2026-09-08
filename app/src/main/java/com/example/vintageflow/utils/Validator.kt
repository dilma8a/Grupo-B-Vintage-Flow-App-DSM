package com.example.vintageflow.utils

//  Excepción personalizada para las reglas del negocio
class VintageFlowException(message: String) : Exception(message)

// Módulo de validaciones
object Validator {

    fun validarCliente(nombre: String, telefono: String) {
        if (nombre.trim().isEmpty()) {
            throw VintageFlowException("El nombre del cliente es obligatorio.")
        }

        val telefonoLimpio = telefono.trim()
        if (telefonoLimpio.length < 8) {
            throw VintageFlowException("El teléfono debe tener al menos 8 caracteres.")
        }

        val regexTelefono = "^\\+?[0-9]+$".toRegex()
        if (!telefonoLimpio.matches(regexTelefono)) {
            throw VintageFlowException("El teléfono contiene caracteres no válidos.")
        }
    }

    fun validarPrenda(precio: Double) {
        if (precio <= 0.0) {
            throw VintageFlowException("El precio de la prenda debe ser mayor a $0.0")
        }
    }

    fun validarVentaStock(cantidadSolicitada: Int, stockDisponible: Int) {
        if (cantidadSolicitada <= 0) {
            throw VintageFlowException("La cantidad a vender debe ser al menos 1.")
        }

        if (cantidadSolicitada > stockDisponible) {
            throw VintageFlowException("Stock insuficiente. Hay $stockDisponible unidades disponibles.")
        }
    }
}
