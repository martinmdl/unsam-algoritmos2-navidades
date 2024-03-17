package ar.edu.unsam.algo2.readapp
/*De los libros conocemos:
La cantidad de palabras, páginas, ediciones y ventas semanales. También si es de lectura compleja.
Sabemos que un libro es desafiante si es de lectura compleja o es largo (tiene más de 600 páginas).
Un libro llega a ser best seller cuando se cumplen todas las siguientes condiciones:
Sus ventas semanales son de por lo menos 10.000 unidades.
Tiene varias ediciones (el criterio actual es más de 2), o muchas traducciones en distintos lenguajes (en este caso no menos de 5).
 */
class Libro(
    val nombre: String,
    val editora: String,
    val paginas: Int,
    val cantPalabras: Int,
    var lecturaCompleja: Boolean,
    var ediciones: Int,
    var lenguajes: List<Lenguaje>,
    var ventasSemanales: Double
) {
    fun esDesafiante(): Boolean = this.lecturaCompleja || this.paginas > 600
    
    fun esBestSeller(): Boolean = this.ventasSemanales >=10000 && (this.ediciones> 2 || this.numeroDeLenguajes() > 2)
    
 /*GETTER*/
    private fun numeroDeLenguajes(): Int = this.lenguajes.size
    
    }
