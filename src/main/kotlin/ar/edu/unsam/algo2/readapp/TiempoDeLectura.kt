@file:Suppress("SpellCheckingInspection")
package ar.edu.unsam.algo2.readapp
import java.time.LocalDate

// CONSIGNA
// De los usuarios, entre varias cosas, sabemos que se puede calcular su tiempo de lectura promedio.
// Pero esto es muy general, por lo que se nos pide determinar el tiempo de lectura
// de un usuario de forma más precisa; y dicho tiempo puede variar según el libro y la forma en que leen.

interface TipoLector {
    fun tiempoDeLectura(libro: Libro): Double = (libro.cantPalabras / velocidadDeLectura(libro)).toDouble() // minutos
    fun velocidadDeLectura(libro: Libro): Double // palabras / minuto
}

// CONSIGNA
// Conocemos que los usuarios suelen ir variando sus formas de leer:
// Hay algunos cuyo tiempo de lectura no varía del tiempo de un lector promedio, sin importar el libro

open class LectorNormal(
    nombre: String,
    apellido: String,
    username: String,
    palabrasPorMinuto: Int,
    fechaNac: LocalDate,
    direccionDeMail: String,
    amigos: MutableSet<Usuario> = mutableSetOf(),
    librosLeidos: MutableMap<Libro, Int>,
    recomendaciones: MutableSet<Recomendacion> = mutableSetOf(),
    autorFavorito: String,
    recomendacionesPorValorar: MutableSet<Recomendacion> = mutableSetOf(),
    librosPorLeer: MutableSet<Libro> = mutableSetOf(),
    tipoLector: TipoLector,
    perfilLector: Perfil
) : TipoLector, Usuario(
    nombre,
    apellido,
    username,
    palabrasPorMinuto,
    fechaNac,
    direccionDeMail,
    amigos,
    librosLeidos,
    recomendaciones,
    autorFavorito,
    recomendacionesPorValorar,
    librosPorLeer,
    tipoLector,
    perfilLector
) {

    override fun velocidadDeLectura(libro: Libro): Double = palabrasPorMinuto.toDouble()
}

// CONSIGNA
// Hay otros que son ansiosos y reducen el tiempo de lectura promedio en un 20%,
// salvo que el libro se trate de un best seller - en ese caso lo reducen a la mitad.

open class LectorAnsioso(
    nombre: String,
    apellido: String,
    username: String,
    palabrasPorMinuto: Int,
    fechaNac: LocalDate,
    direccionDeMail: String,
    amigos: MutableSet<Usuario> = mutableSetOf(),
    librosLeidos: MutableMap<Libro, Int>,
    recomendaciones: MutableSet<Recomendacion> = mutableSetOf(),
    autorFavorito: String,
    recomendacionesPorValorar: MutableSet<Recomendacion> = mutableSetOf(),
    librosPorLeer: MutableSet<Libro> = mutableSetOf(),
    tipoLector: TipoLector
) : TipoLector, Usuario(
    nombre,
    apellido,
    username,
    palabrasPorMinuto,
    fechaNac,
    direccionDeMail,
    amigos,
    librosLeidos,
    recomendaciones,
    autorFavorito,
    recomendacionesPorValorar,
    librosPorLeer,
    tipoLector
) {
    companion object {
        const val COEFICIENTE_POR_NON_BEST_SELLER = 0.8
        const val COEFICIENTE_POR_BEST_SELLER = 0.5
    }

    override fun tiempoDeLectura(libro: Libro): Double {
        return when {
            libro.esBestSeller() -> super<TipoLector>.tiempoDeLectura(libro) * COEFICIENTE_POR_BEST_SELLER
            else -> super<TipoLector>.tiempoDeLectura(libro) * COEFICIENTE_POR_NON_BEST_SELLER
        }
    }
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
    amigos: MutableSet<Usuario> = mutableSetOf(),
    librosLeidos: MutableMap<Libro, Int>,
    recomendaciones: MutableSet<Recomendacion> = mutableSetOf(),
    autorFavorito: String,
    recomendacionesPorValorar: MutableSet<Recomendacion> = mutableSetOf(),
    librosPorLeer: MutableSet<Libro> = mutableSetOf(),
    tipoLector: TipoLector
) : TipoLector, Usuario(
    nombre,
    apellido,
    username,
    palabrasPorMinuto,
    fechaNac,
    direccionDeMail,
    amigos,
    librosLeidos,
    recomendaciones,
    autorFavorito,
    recomendacionesPorValorar,
    librosPorLeer,
    tipoLector
) {

    companion object {
        const val COEFICIENTE_LIBRO_IMPORTANTE = 2
        const val MAX_PAGINAS_LIBRO_CORTO = 600
            // Libro largo > 600 paginas
            // Libro corto <= 600 paginas
    }

    // velocidadDeLectura() inherits from Usuario()
    override fun tiempoDeLectura(libro: Libro): Double {
        return when {
            esLibroImportante(libro) -> tiempoLecturalLibroImportante(libro)
            else -> super<TipoLector>.tiempoDeLectura(libro)
        }
    }

    // AUX
    private fun esLibroImportante(libro: Libro): Boolean = libro.autor == autorFavorito && !(librosLeidos.contains(libro))

    private fun esLibroLargo(libro: Libro): Boolean = libro.paginas > MAX_PAGINAS_LIBRO_CORTO

    private fun tiempoAdicionalCorto(libro: Libro): Int = libro.paginas * COEFICIENTE_LIBRO_IMPORTANTE

    private fun tiempoAdicionalLargo(libro: Libro): Int = tiempoAdicionalCorto(libro) + libro.paginas - MAX_PAGINAS_LIBRO_CORTO

    private fun tiempoLecturalCorto(libro: Libro): Double = super<TipoLector>.tiempoDeLectura(libro) + tiempoAdicionalCorto(libro)

    private fun tiempoLecturalLargo(libro: Libro): Double = super<TipoLector>.tiempoDeLectura(libro) + tiempoAdicionalLargo(libro)

    private fun tiempoLecturalLibroImportante(libro: Libro): Double {
        return when {
            esLibroLargo(libro) -> tiempoLecturalLargo(libro)
            else -> tiempoLecturalCorto(libro)
        }
    }
}

// CONSIGNA
// Sabemos que hay lectores recurrentes que suelen volver a leer los mismos libros:
// estos van disminuyendo la velocidad de lectura promedio en 1% cada vez que lo vuelven a leer.
// A partir de la 5ta lectura la velocidad no varía.

open class LectorRecurrente(
    nombre: String,
    apellido: String,
    username: String,
    palabrasPorMinuto: Int,
    fechaNac: LocalDate,
    direccionDeMail: String,
    amigos: MutableSet<Usuario> = mutableSetOf(),
    librosLeidos: MutableMap<Libro, Int>,
    recomendaciones: MutableSet<Recomendacion> = mutableSetOf(),
    autorFavorito: String,
    recomendacionesPorValorar: MutableSet<Recomendacion> = mutableSetOf(),
    librosPorLeer: MutableSet<Libro> = mutableSetOf(),
    tipoLector: TipoLector
) : TipoLector, Usuario(
    nombre,
    apellido,
    username,
    palabrasPorMinuto,
    fechaNac,
    direccionDeMail,
    amigos,
    librosLeidos,
    recomendaciones,
    autorFavorito,
    recomendacionesPorValorar,
    librosPorLeer,
    tipoLector
) {

// CONSIGNA
// Sabemos que hay lectores recurrentes que suelen volver a leer los mismos libros:
// estos van disminuyendo la velocidad de lectura promedio en 1% cada vez que lo vuelven a leer.
// A partir de la 5ta lectura la velocidad no varía.

    companion object {
        const val DISMINUCION_VELOCIDAD_LECTURA_POR_REPETICION = 0.01
        const val REPETICIONES_QUE_DISMINUYEN_VELOCIDAD = 4
    }

    override fun velocidadDeLectura(libro: Libro): Double =
        (super<Usuario>.velocidadDeLectura(libro) * (1 - porcentajeDeDisminucion(libro))).toDouble()

    // AUX
    private fun porcentajeDeDisminucion(libro: Libro): Double = (cantidadLecturas(libro)!! * DISMINUCION_VELOCIDAD_LECTURA_POR_REPETICION)

    private fun cantidadLecturas(libro: Libro): Int? {
        return when {
            librosLeidos[libro] == null -> 0
            librosLeidos[libro]!! <= REPETICIONES_QUE_DISMINUYEN_VELOCIDAD -> librosLeidos[libro]
            else -> REPETICIONES_QUE_DISMINUYEN_VELOCIDAD
        }
    }
}






