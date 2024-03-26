@file:Suppress("SpellCheckingInspection")
package ar.edu.unsam.algo2.readapp

import java.time.LocalDate

open class LectorRecurrente(
    nombre: String,
    apellido: String,
    username: String,
    palabrasPorMinuto: Int,
    fechaNac: LocalDate
) : Usuario(
    nombre,
    apellido,
    username,
    palabrasPorMinuto,
    fechaNac
) {

    companion object {
        const val PORCENTAJE_VELOCIDAD_LECTURA = 0.01
        const val LECTURAS_SIN_VARIACION = 5
    }
    override fun velocidadDeLectura(libroAleer: Libro): Int {
        return if (libroAleer.esDesafiante()){

        }

    }

    //override fun leer(libroAleer: Libro)
}

open class LectorPromedio(
    nombre: String,
    apellido: String,
    username: String,
    palabrasPorMinuto: Int,
    fechaNac: LocalDate
) : Usuario(nombre, apellido, username, palabrasPorMinuto, fechaNac) {

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
    fechaNac: LocalDate
) : Usuario(nombre, apellido, username, palabrasPorMinuto, fechaNac) {

    override fun tiempoDeLectura(libroAleer: Libro): {
        super() * 2
    }
    fun velocidadDeLectura(libroAleer: Libro): Int {
        return if(libroAleer.esDesafiante()) {
            palabrasPorMinuto / DISMINUCION_VELOCIDAD_LECTURA
        } else {
            palabrasPorMinuto
        }
    }
}

