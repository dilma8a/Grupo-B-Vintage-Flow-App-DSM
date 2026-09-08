package com.example.vintageflow

import com.example.vintageflow.utils.Validator
import com.example.vintageflow.utils.VintageFlowException
import org.junit.Test
import org.junit.Assert.assertThrows
import org.junit.Assert.assertEquals

class ValidatorTest {

    @Test
    fun probarClienteNombreVacio() {
        val excepcion = assertThrows(VintageFlowException::class.java) {
            Validator.validarCliente(nombre = "", telefono = "77771234")
        }
        assertEquals("El nombre del cliente es obligatorio.", excepcion.message)
    }

    @Test
    fun probarClienteTelefonoInvalido() {
        val excepcion = assertThrows(VintageFlowException::class.java) {
            Validator.validarCliente(nombre = "Alex", telefono = "123")
        }
        assertEquals("El teléfono debe tener al menos 8 caracteres.", excepcion.message)
    }

    @Test
    fun probarPrendaPrecioInvalido() {
        val excepcion = assertThrows(VintageFlowException::class.java) {
            Validator.validarPrenda(precio = -5.0)
        }
        assertEquals("El precio de la prenda debe ser mayor a $0.0", excepcion.message)
    }

    @Test
    fun probarSobreventaDeStock() {
        val excepcion = assertThrows(VintageFlowException::class.java) {
            Validator.validarVentaStock(cantidadSolicitada = 3, stockDisponible = 1)
        }
        assertEquals("Stock insuficiente. Hay 1 unidades disponibles.", excepcion.message)
    }
}