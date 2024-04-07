@file:Suppress("SpellCheckingInspection")

package ar.edu.unsam.algo2.readapp

/**
 * Valoracion.
 *
 * @property autor Indica el nombre: Usuario del autor.
 * @property valor Indica el valor numerico: [Int] de la valoracion 1 - 5.
 * @property comentario Indica el comentario asociado a la valoracion.
 */

class Valoracion(
    val autor: Usuario,
    var valor: Int,
    var comentario: String
) {

    /**
     * [editarValor]:
     *
     * Recibe un valor numerico [num] y un usuario [usuarioQueEdita].
     * Si el usuario es el autor, entonces modifica la valoracion
     */
    fun editarValor(num: Int, usuarioQueEdita: Usuario) {
        if (esAutor(usuarioQueEdita)) {
            valor = num
        }
    }

    /**
     * [editarComentario].
     *
     * Recibe una cadena de String [texto] y un Usuario [usuarioQueEdita].
     * Si el usuario es el autor, entonces modifica el texto dentro de comentario.
     */
    fun editarComentario(texto: String, usuarioQueEdita: Usuario) {
        if (esAutor(usuarioQueEdita)) {
            comentario = texto
        }
    }

    private fun esAutor(usuarioQueEdita: Usuario): Boolean = usuarioQueEdita == autor

}