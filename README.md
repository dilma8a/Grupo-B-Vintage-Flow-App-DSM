# Vintage Flow App

Aplicacion de consola desarrollada en Kotlin para apoyar la administracion de ropa de segunda mano. El proyecto digitaliza el catalogo de prendas y prepara la base funcional para gestionar inventario, consultar informacion y generar resumenes utiles para la toma de decisiones.

## Contexto del proyecto

Vintage Flow nace como una propuesta para optimizar la gestion de inventario y la atencion de un negocio de moda sostenible. La aplicacion permite trabajar con prendas identificadas por codigo, nombre, talla, categoria, precio y estado de disponibilidad.

El alcance de esta etapa es construir el nucleo funcional en modo consola y dejar una base preparada para la evolucion de la aplicacion en la siguiente etapa.

## Funcionalidades implementadas

| Requisito de la etapa | Implementacion |
| --- | --- |
| Gestion principal de registros | `ModuloGestionPrendas`: crear, listar, actualizar y eliminar prendas |
| Procesamiento y logica de negocio | `ModuloProcesamiento`: valores del inventario, promedio, prenda mas cara, categorias y resumen por estado |
| Visualizacion en consola | `MenuConsola`: menu de operaciones y listado tabular del catalogo |
| Reporte o resumen del sistema | `ReporteGenerador`: reporte de inventario, estados, tallas y valores calculados |
| Actualizacion dinamica durante la ejecucion | El inventario se mantiene en una `MutableList<Prenda>` y los cambios CRUD se reflejan en las consultas y reportes |

Tambien se incluyen:

- Validacion de campos obligatorios, precio, talla, categoria, estado e identificadores duplicados.
- Excepciones de dominio derivadas de `VintageFlowException`.
- Registro de errores en archivos dentro de `logs/` mediante `LogErrores`.
- Pruebas unitarias para validaciones, CRUD validado y generacion de reportes.
- Uso de interfaces, clases, herencia, objetos, composicion y colecciones de Kotlin.

## Estructura principal

| Archivo | Responsabilidad |
| --- | --- |
| `Modelos.kt` | Modelos `Usuario`, `Administrador`, `Cliente` y `Prenda`; gestion CRUD del inventario |
| `Interfaces.kt` | Contrato generico `GestorCRUD<T>` |
| `ValidacionYExcepciones.kt` | Validador, excepciones y decorador `GestorPrendasValidado` |
| `Moduloprocesamiento.kt` | Calculos y reglas de negocio del inventario |
| `ReporteGenerador.kt` | Construccion de reportes reutilizables en texto |
| `MenuConsola.kt` | Interaccion por consola y operaciones del usuario |
| `LogErrores.kt` | Escritura de errores en archivos de log |
| `Main.kt` | Punto de entrada de la aplicacion; inicia `MenuConsola` |
| `PruebasUnitarias.kt` | Pruebas automatizadas con JUnit 4 |

## Reglas de validacion

- **Tallas:** `XS`, `S`, `M`, `L`, `XL`, `XXL`, `ÚNICA`.
- **Categorias:** `Camisas`, `Blusas`, `Pantalones`, `Vestidos`, `Chaquetas`, `Faldas`, `Zapatos` y `Accesorios`.
- **Estados:** `DISPONIBLE`, `RESERVADO` y `VENDIDO`.
- El identificador, nombre y demas campos de texto requeridos no pueden estar vacios.
- El precio debe ser mayor que cero.
- No se permite registrar dos prendas con el mismo identificador.


## Flujo de validacion

`GestorPrendasValidado` aplica las reglas antes de delegar la operacion al gestor real. De esta forma, una prenda invalida no se agrega ni actualiza en el inventario y las excepciones pueden ser capturadas por `MenuConsola`, que ademas registra el detalle en `logs/vintage-flow.*.log`.

## Desarrolladores

El trabajo se desarrolla en GitHub con una rama por integrante y los cambios integrados en el repositorio del grupo. Los colaboradores del proyecto son:

- Jefrey Gerardo Moreno Gomez
- Vladmir Alexander Ayala Cabrera
- Hilda Maria Martinez de Reyes
- Jose David Carabantes Hernandez
- Dilma Esmeralda Ochoa Ayala
