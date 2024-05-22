@file:Suppress("SpellCheckingInspection")

package ar.edu.unsam.algo2.readapp

/**
 * Libros.
 *
 * @property nombre Indica el nombre del libro
 * @property editorial Indica el nombre de la editorial del libro
 * @property paginas Índica la cantidad de páginas del libro
 * @property cantPalabras Indica la cantidad de palabras del libro
 * @property lecturaCompleja Indica si el libro es complejo
 * @property ediciones Indica la cantidad de ediciones que tiene el libro
 * @property idioma Conjunto de idiomas en los que se encuentra el libro, el primero corresponde al lenguaje nativo del autor, el resto traducciones.
 * @property ventasSemanales Indica la cantiad de ventas semanales
 */

class Libro(
    override var id: Int? = null,
    private val nombre: String,
    private val editorial: String,
    val paginas: Int, //necesario en: 'TipoLector.kt/LectorFanatico'
    val cantPalabras: Int,
    private var lecturaCompleja: Boolean,
    private var ediciones: Int,
    var idioma: Set<Lenguaje>,
    private var ventasSemanales: Int,
    val autor: Autor
): Identidad {

    companion object {
        private const val MINIMO_DE_VENTAS_SEMANALES: Int = 10000
        private const val MINIMO_DE_EDICIONES: Int = 2
        private const val MINIMO_DE_TRADUCCIONES: Int = 5
        const val MINIMO_DE_PAGINAS: Int = 600
    }

    /**
     * De los libros conocemos:La cantidad de palabras, páginas, ediciones y ventas semanales. También si es de lectura compleja.
     * Sabemos que un libro es desafiante si es de lectura compleja o es largo (tiene más de 600 páginas).
     * Un libro llega a ser best seller cuando se cumplen todas las siguientes condiciones:
     * Sus ventas semanales son de por lo menos 10.000 unidades.
     * Tiene varias ediciones (el criterio actual es más de 2), o muchas traducciones en distintos lenguajes (en este caso no menos de 5).
     */
    /*GETTER*/
    private fun numeroDeLenguajes(): Int = this.idioma.size
    fun getNombre() = nombre
    fun getEdiciones() = ediciones
    fun getVentasSemanales(): Int = this.ventasSemanales
    fun getMinimoPaginas() = MINIMO_DE_PAGINAS


    fun esLargo() = paginas > MINIMO_DE_PAGINAS

    fun esDesafiante(): Boolean = this.lecturaCompleja || this.paginas > MINIMO_DE_PAGINAS

    fun esBestSeller(): Boolean =
        (this.ventasSemanales >= MINIMO_DE_VENTAS_SEMANALES && (this.ediciones > MINIMO_DE_EDICIONES) || this.numeroDeLenguajes() >= MINIMO_DE_TRADUCCIONES)

    fun setVentaSemanales(ventasSemanales: Int) {this.ventasSemanales = ventasSemanales}

    fun setEditarEdiciones(ediciones: Int) { this.ediciones = ediciones }
}