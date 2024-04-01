@file:Suppress("SpellCheckingInspection")
package ar.edu.unsam.algo2.readapp
import java.time.LocalDate

interface TipoLector {
    fun tiempoDeLectura(libro: Libro): Double = (libro.cantPalabras / velocidadDeLectura(libro)).toDouble() // minutos
    fun velocidadDeLectura(libro: Libro): Double // palabras / minuto
}

open class LectorPromedio(
    nombre: String,
    apellido: String,
    username: String,
    palabrasPorMinuto: Int,
    fechaNac: LocalDate,
    direccionDeMail: String,
    amigos: List<Usuario>,
    librosLeidos: MutableList<Libro>,
    recomendaciones: MutableList<Recomendacion>
) : TipoLector, Usuario(
    nombre,
    apellido,
    username,
    palabrasPorMinuto,
    fechaNac,
    direccionDeMail,
    amigos,
    librosLeidos,
    recomendaciones
) {

    companion object {
        const val COEFICIENTE_POR_LIBRO_DESAFIANTE = 2 // si el libro es desafiante
    }

    override fun velocidadDeLectura(libro: Libro): Double {
        return if(libro.esDesafiante()) {
            (palabrasPorMinuto / COEFICIENTE_POR_LIBRO_DESAFIANTE).toDouble()
        } else {
            palabrasPorMinuto.toDouble()
        }
    }
}

open class LectorNormal(
    nombre: String,
    apellido: String,
    username: String,
    palabrasPorMinuto: Int,
    fechaNac: LocalDate,
    direccionDeMail: String,
    amigos: List<Usuario>,
    librosLeidos: MutableList<Libro>,
    recomendaciones: MutableList<Recomendacion>
) : TipoLector, Usuario(
    nombre,
    apellido,
    username,
    palabrasPorMinuto,
    fechaNac,
    direccionDeMail,
    amigos,
    librosLeidos,
    recomendaciones
) {

    override fun velocidadDeLectura(libro: Libro): Double = palabrasPorMinuto.toDouble()
}

open class LectorAnsioso(
    nombre: String,
    apellido: String,
    username: String,
    palabrasPorMinuto: Int,
    fechaNac: LocalDate,
    direccionDeMail: String,
    amigos: List<Usuario>,
    librosLeidos: MutableList<Libro>,
    recomendaciones: MutableList<Recomendacion>
) : TipoLector, Usuario(
    nombre,
    apellido,
    username,
    palabrasPorMinuto,
    fechaNac,
    direccionDeMail,
    amigos,
    librosLeidos,
    recomendaciones
) {
    companion object {
        const val COEFICIENTE_POR_NON_BEST_SELLER = 1.2
        const val COEFICIENTE_POR_BEST_SELLER = 1.5
    }

    override fun tiempoDeLectura(libro: Libro): Double {
        return if(libro.esBestSeller()) {
            super.tiempoDeLectura(libro) * COEFICIENTE_POR_BEST_SELLER
        } else {
            super.tiempoDeLectura(libro) * COEFICIENTE_POR_NON_BEST_SELLER
        }
    }

    override fun velocidadDeLectura(libro: Libro): Double = palabrasPorMinuto.toDouble()
}

// CONSIGNA
// Está el fanático: cuando se trata de un autor de sus preferidos y no leyó el libro,
// se toma su tiempo para disfrutar el mismo - por lo que su tiempo de lectura aumenta
// a razón de 2 minutos por página si el libro no es largo;
// caso contrario, a partir de +600 páginas, empieza a leer más rápido;
// y solo le agrega 1 minuto por página.
// (Ejemplo: un libro de 650 páginas va a aumentar 1.250 min = 1.200 min + 50 min).

open class LectorFanatico(
    nombre: String,
    apellido: String,
    username: String,
    palabrasPorMinuto: Int,
    fechaNac: LocalDate,
    direccionDeMail: String,
    amigos: List<Usuario>,
    librosLeidos: MutableList<Libro>,
    recomendaciones: MutableList<Recomendacion>
) : TipoLector, Usuario(
    nombre,
    apellido,
    username,
    palabrasPorMinuto,
    fechaNac,
    direccionDeMail,
    amigos,
    librosLeidos,
    recomendaciones
) {

    companion object {
        const val PORCENTAJE_VELOCIDAD_LECTURA = 0.2
    }

    override fun velocidadDeLectura(libro: Libro): Double = palabrasPorMinuto.toDouble()

    // AUX
    private fun esLibroLargo(libro: Libro): Boolean = libro.paginas > 600

//    private fun porcentajeVelocidadReducida() : Double = palabrasPorMinuto * PORCENTAJE_VELOCIDAD_LECTURA
//    override fun velocidadDeLectura(libroAleer: Libro): Number {
//        return if(libroAleer.esBestSeller()) {
//            palabrasPorMinuto / DISMINUCION_VELOCIDAD_LECTURA
//        } else {
//            palabrasPorMinuto - this.porcentajeVelocidadReducida()
//        }
//    }
}

//open class LectorRecurrente(
//    nombre: String,
//    apellido: String,
//    username: String,
//    palabrasPorMinuto: Int,
//    fechaNac: LocalDate,
//    direccionDeMail: String,
//    amigos: List<Usuario>,
//    librosLeidos: MutableList<Libro>,
//    recomendaciones: MutableList<Recomendacion>
//) : TipoLector, Usuario(
//    nombre,
//    apellido,
//    username,
//    palabrasPorMinuto,
//    fechaNac,
//    direccionDeMail,
//    amigos,
//    librosLeidos,
//    recomendaciones
//) {
//    companion object {
//        const val PORCENTAJE_VELOCIDAD_LECTURA = 0.01
//        const val LECTURAS_SIN_VARIACION = 5
//    }
//    override fun velocidadDeLectura(libro: Libro): Number {
//        return if (libro){
//            0
//        } else {
//            0
//        }
//
//    }
//}






