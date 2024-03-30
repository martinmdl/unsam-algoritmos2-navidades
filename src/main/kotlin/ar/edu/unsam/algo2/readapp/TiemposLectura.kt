@file:Suppress("SpellCheckingInspection")
package ar.edu.unsam.algo2.readapp

import java.time.LocalDate
import kotlin.time.times

open class LectorRecurrente(
    nombre: String,
    apellido: String,
    username: String,
    palabrasPorMinuto: Int,
    fechaNac: LocalDate, direccionDeMail: String, amigos: List<Usuario>, librosLeidos: MutableList<Libro>,
    recomendaciones: MutableList<Any>
) : Usuario(nombre, apellido, username, palabrasPorMinuto, fechaNac, direccionDeMail, amigos, librosLeidos,
    recomendaciones
){
    companion object {
        const val PORCENTAJE_VELOCIDAD_LECTURA = 0.01
        const val LECTURAS_SIN_VARIACION = 5
    }
    override fun velocidadDeLectura(libroAleer: Libro): Number {
        return if (leerLibro()){
            0
        } else {
            0
        }

    }
}

open class LectorPromedio(
    nombre: String,
    apellido: String,
    username: String,
    palabrasPorMinuto: Int,
    fechaNac: LocalDate, direccionDeMail: String, amigos: List<Usuario>, librosLeidos: MutableList<Libro>,
    recomendaciones: MutableList<Any>
) : Usuario(nombre, apellido, username, palabrasPorMinuto, fechaNac, direccionDeMail, amigos, librosLeidos,
    recomendaciones
) {

    override fun velocidadDeLectura(libroAleer: Libro): Int {
        return if(libroAleer.esDesafiante()) {
            palabrasPorMinuto / DISMINUCION_VELOCIDAD_LECTURA
        } else {
            palabrasPorMinuto
        }
    }
}

open class LectorAnsioso(
    nombre: String,
    apellido: String,
    username: String,
    palabrasPorMinuto: Int,
    fechaNac: LocalDate, direccionDeMail: String, amigos: List<Usuario>, librosLeidos: MutableList<Libro>,
    recomendaciones: MutableList<Any>
) : Usuario(nombre, apellido, username, palabrasPorMinuto, fechaNac, direccionDeMail, amigos, librosLeidos,
    recomendaciones
) {
    companion object {
        const val PORCENTAJE_VELOCIDAD_LECTURA = 0.2
    }
    private fun porcentajeVelocidadReducida() : Double = palabrasPorMinuto * PORCENTAJE_VELOCIDAD_LECTURA
    override fun velocidadDeLectura(libroAleer: Libro): Number {
        return if(libroAleer.esBestSeller()) {
            palabrasPorMinuto / DISMINUCION_VELOCIDAD_LECTURA
        } else {
            palabrasPorMinuto - this.porcentajeVelocidadReducida()
        }
    }
}

