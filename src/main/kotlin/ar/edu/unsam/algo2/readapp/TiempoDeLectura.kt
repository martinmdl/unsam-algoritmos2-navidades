@file:Suppress("SpellCheckingInspection")

package ar.edu.unsam.algo2.readapp

interface TipoLector {

    companion object{
        const val COEFICIENTE_POR_LIBRO_DESAFIANTE = 2
    }

    fun tiempoDeLectura(libro: Libro, usuario: Usuario): Double =
        (libro.cantPalabras / velocidadDeLectura(libro, usuario))

    fun velocidadDeLectura(libro: Libro, usuario: Usuario): Double {
        return when {
            libro.esDesafiante() -> (usuario.palabrasPorMinuto / COEFICIENTE_POR_LIBRO_DESAFIANTE).toDouble()
            else -> usuario.palabrasPorMinuto.toDouble()
        }
    }
}

object LectorPromedio : TipoLector {}

object LectorNormal : TipoLector {
    override fun velocidadDeLectura(libro: Libro, usuario: Usuario): Double = usuario.palabrasPorMinuto.toDouble()
}

object LectorAnsioso : TipoLector {

    private const val COEFICIENTE_POR_NON_BEST_SELLER = 0.8
    private const val COEFICIENTE_POR_BEST_SELLER = 0.5

    override fun tiempoDeLectura(libro: Libro, usuario: Usuario): Double {
        return when {
            libro.esBestSeller() -> super.tiempoDeLectura(libro, usuario) * COEFICIENTE_POR_BEST_SELLER
            else -> super.tiempoDeLectura(libro, usuario) * COEFICIENTE_POR_NON_BEST_SELLER
        }
    }
}

object LectorFanatico : TipoLector {

        private const val COEFICIENTE_LIBRO_IMPORTANTE = 2
        private const val MAX_PAGINAS_LIBRO_CORTO = 600

    override fun tiempoDeLectura(libro: Libro, usuario: Usuario): Double {
        return when {
            esLibroImportante(libro, usuario) -> tiempoLecturalLibroImportante(libro, usuario)
            else -> super.tiempoDeLectura(libro, usuario)
        }
    }

    private fun esLibroImportante(libro: Libro, usuario: Usuario): Boolean =
        libro.autor == usuario.autorFavorito && !(usuario.librosLeidos.keys.contains(libro))

    private fun esLibroLargo(libro: Libro): Boolean =
        libro.paginas > MAX_PAGINAS_LIBRO_CORTO

    private fun tiempoAdicionalCorto(libro: Libro): Int =
        libro.paginas * COEFICIENTE_LIBRO_IMPORTANTE

    private fun tiempoAdicionalLargo(libro: Libro): Int =
        tiempoAdicionalCorto(libro) + libro.paginas - MAX_PAGINAS_LIBRO_CORTO

    private fun tiempoLecturalCorto(libro: Libro, usuario: Usuario): Double =
        super.tiempoDeLectura(libro, usuario) + tiempoAdicionalCorto(libro)

    private fun tiempoLecturalLargo(libro: Libro, usuario: Usuario): Double =
        super.tiempoDeLectura(libro, usuario) + tiempoAdicionalLargo(libro)

    private fun tiempoLecturalLibroImportante(libro: Libro, usuario: Usuario): Double {
        return when {
            esLibroLargo(libro) -> tiempoLecturalLargo(libro, usuario)
            else -> tiempoLecturalCorto(libro, usuario)
        }
    }
}

object LectorRecurrente : TipoLector {

    private const val DISMINUCION_VELOCIDAD_LECTURA_POR_REPETICION = 0.01
    private const val REPETICIONES_QUE_DISMINUYEN_VELOCIDAD = 4

    override fun velocidadDeLectura(libro: Libro, usuario: Usuario): Double =
        super.velocidadDeLectura(libro, usuario) * (1 - porcentajeDeDisminucion(libro, usuario))

    private fun porcentajeDeDisminucion(libro: Libro, usuario: Usuario): Double =
        (cantidadLecturas(libro, usuario)!! * DISMINUCION_VELOCIDAD_LECTURA_POR_REPETICION)

    private fun cantidadLecturas(libro: Libro, usuario: Usuario): Int? {
        return when {
            usuario.librosLeidos[libro] == null -> 0
            usuario.librosLeidos[libro]!! <= REPETICIONES_QUE_DISMINUYEN_VELOCIDAD -> usuario.librosLeidos[libro]
            else -> REPETICIONES_QUE_DISMINUYEN_VELOCIDAD
        }
    }
}






