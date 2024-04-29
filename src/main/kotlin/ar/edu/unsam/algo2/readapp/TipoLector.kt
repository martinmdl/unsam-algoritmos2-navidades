@file:Suppress("SpellCheckingInspection")

package ar.edu.unsam.algo2.readapp

abstract class TipoLector(val usuario: Usuario) {
    companion object { const val COEFICIENTE_POR_LIBRO_DESAFIANTE = 2 }

    open fun tiempoDeLectura(libro: Libro): Double =
        (libro.cantPalabras / velocidadDeLectura(libro))

    open fun velocidadDeLectura(libro: Libro): Double {
        return when {
            libro.esDesafiante() -> (usuario.palabrasPorMinuto / COEFICIENTE_POR_LIBRO_DESAFIANTE).toDouble()
            else -> usuario.palabrasPorMinuto.toDouble()
        }
    }
}

class LectorPromedio(usuario: Usuario) : TipoLector(usuario) {}

class LectorNormal(usuario: Usuario) : TipoLector(usuario) {
    override fun velocidadDeLectura(libro: Libro): Double =
        usuario.palabrasPorMinuto.toDouble()
}

class LectorAnsioso(usuario: Usuario) : TipoLector(usuario) {

    companion object {
        private const val COEFICIENTE_POR_BEST_SELLER = 0.5
        private const val COEFICIENTE_POR_NON_BEST_SELLER = 0.8
    }

    override fun tiempoDeLectura(libro: Libro): Double {
        return when {
            libro.esBestSeller() -> super.tiempoDeLectura(libro) * COEFICIENTE_POR_BEST_SELLER
            else -> super.tiempoDeLectura(libro) * COEFICIENTE_POR_NON_BEST_SELLER
        }
    }
}

class LectorFanatico(usuario: Usuario) : TipoLector(usuario) {

    companion object { private const val COEFICIENTE_LIBRO_IMPORTANTE = 2 }

    override fun tiempoDeLectura(libro: Libro): Double {
        return when {
            esLibroImportante(libro) -> tiempoLecturaImportante(libro)
            else -> super.tiempoDeLectura(libro)
        }
    }

    private fun tiempoAdicionalImportante(libro: Libro): Double {
        return when {
            libro.esLargo() -> tiempoAdicionalLargo(libro)
            else -> tiempoAdicionalCorto(libro)
        }
    }

    private fun esLibroImportante(libro: Libro): Boolean = libro.autor == usuario.autorFavorito && !(usuario.librosLeidos.keys.contains(libro))
    private fun tiempoLecturaImportante(libro: Libro): Double = super.tiempoDeLectura(libro) + tiempoAdicionalImportante(libro)
    private fun tiempoAdicionalLargo(libro: Libro): Double = (libro.getMinimoPaginas() * COEFICIENTE_LIBRO_IMPORTANTE + (libro.paginas - libro.getMinimoPaginas())).toDouble()
    private fun tiempoAdicionalCorto(libro: Libro): Double = (libro.paginas * COEFICIENTE_LIBRO_IMPORTANTE).toDouble()
}

class LectorRecurrente(usuario: Usuario) : TipoLector(usuario) {

    companion object {
        private const val DISMINUCION_VELOCIDAD_LECTURA_POR_REPETICION = 0.01
        private const val ENTERO = 1
        private const val LECTURAS_MAX = 4
    }

    override fun velocidadDeLectura(libro: Libro): Double = super.velocidadDeLectura(libro) * (ENTERO - porcentajeDeDisminucion(libro))
    private fun porcentajeDeDisminucion(libro: Libro): Double = (checkLecturas(libro) * DISMINUCION_VELOCIDAD_LECTURA_POR_REPETICION)
    private fun checkLecturas(libro: Libro): Int = if (usuario.cantidadLecturas(libro) > LECTURAS_MAX) LECTURAS_MAX else usuario.cantidadLecturas(libro)
}





