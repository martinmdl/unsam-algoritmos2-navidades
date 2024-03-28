@file:Suppress("SpellCheckingInspection")

package ar.edu.unsam.algo2.readapp

/**
 * Recomendaciones.
 *
 * @property esPrivado Indica si la recomendacion es de acceso publico o privado
 * @property creador Indica el autor de la recomendacion
 * @property libroRecomendados Conjunto de libros incluidos en la recomendacion
 * @property descripcion String que indica una pequeña reseña de la recomendacion
 */

class Recomendacion(
    var esPrivado: Boolean,
    val creador: Usuario,
    var libroRecomendados: MutableSet<Libro>,
    var descripcion: String,
    var valoraciones: MutableMap<Usuario, Valoracion>
) {


    /*SETTERS*/

    fun editarPrivacidad(usuarioQueEdita: Usuario) {
        if (esCreador(usuarioQueEdita)) {
            alternarPrivacidad()
        }
        /*CAPTURAR ERROR*/
    }

    /**
     * [editarDescripcion]:
     *
     * Solo el [creador] y el amigo, pueden editar la [descripcion].
     */
    fun editarDescripcion(usuarioQueEdita: Usuario, descripcionNueva: String) {
        if (esCreador(usuarioQueEdita) || esAmigo(usuarioQueEdita)) {
            cambioDeValoracion(descripcionNueva)
        }
        /*CAPTURAR ERROR*/
    }

    fun agregarALibrosDeRecomendacion(usuarioQueEdita: Usuario, libro: Libro) {
        if (validarEdicion(usuarioQueEdita, libro)) {
            libroRecomendados.add(libro)
        }
        /*CAPTURAR ERROR*/
    }

    fun crearValoracion(valor: Int, comentario: String, usuario: Usuario) {
        if (amigoleidosTodos(usuario) || collecionAutorFavorito(usuario)) {
            valoraciones[usuario] = Valoracion(usuario, valor, comentario)
        }
        //Captura error
    }

    fun collecionAutorFavorito(usuario: Usuario): Boolean =
        libroRecomendados.all { it -> it.autor == usuario.autorFavorito }

    /*GETTERS*/
    fun leerRecomendacion(usuarioQueLee: Usuario): Boolean = !esPrivado || puedeLeerLaRecomendacion(usuarioQueLee)

    fun tiempoDeLecturaTotal(lector: Usuario): Int = libroRecomendados.sumOf { lector.tiempoDeLectura(it) }

    fun tiempoDeLecturaNeto(lector: Usuario): Int =
        libroRecomendados.subtract(lector.librosLeidos).sumOf { lector.tiempoDeLectura(it) }

    fun tiempoDeLecturaAhorrado(lector: Usuario): Int {
        return tiempoDeLecturaTotal(lector) - tiempoDeLecturaNeto(lector)
    }


    /*AUX*/
    private fun esCreador(usuario: Usuario): Boolean = creador == usuario

    private fun esAmigo(usuario: Usuario) = creador.amigos.contains(usuario)

    private fun puedeLeerLaRecomendacion(usuarioQueLee: Usuario): Boolean =
        esAmigo(usuarioQueLee) || esCreador(usuarioQueLee)

    private fun validarEdicion(usuarioQueEdita: Usuario, libro: Libro): Boolean =
        puedeAgregarCreador(usuarioQueEdita, libro) || puedeAgregarAmigo(usuarioQueEdita, libro)

    private fun puedeAgregarCreador(usuarioQueEdita: Usuario, libro: Libro): Boolean =
        esCreador(usuarioQueEdita) && agregarLibroCreador(libro)

    private fun puedeAgregarAmigo(usuarioQueEdita: Usuario, libro: Libro): Boolean =
        esAmigo(usuarioQueEdita) && agregarLibroAmigo(usuarioQueEdita, libro) && amigoleidosTodos(usuarioQueEdita)

    private fun agregarLibroCreador(libro: Libro): Boolean = !existeLibro(libro) && libroYaFueLeidoPorCreador(libro)

    private fun agregarLibroAmigo(usuarioQueEdita: Usuario, libro: Libro): Boolean =
        agregarLibroCreador(libro) && libroYaFueLeidoPorAmigo(usuarioQueEdita, libro)

    private fun existeLibro(libro: Libro): Boolean = libroRecomendados.contains(libro)

    private fun libroYaFueLeidoPorCreador(libro: Libro): Boolean = creador.librosLeidos.contains(libro)

    private fun libroYaFueLeidoPorAmigo(usuarioQueEdita: Usuario, libro: Libro): Boolean =
        usuarioQueEdita.librosLeidos.contains(libro)

    private fun amigoleidosTodos(usuarioQueEdita: Usuario): Boolean =
        usuarioQueEdita.librosLeidos.containsAll(libroRecomendados)

    private fun alternarPrivacidad() {
        esPrivado = !esPrivado
    }

    private fun cambioDeValoracion(texto: String) {
        descripcion = texto
    }
}