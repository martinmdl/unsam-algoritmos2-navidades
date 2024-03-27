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

class Recomendaciones(
    private var esPrivado: Boolean,
    private val creador: Usuario,
    private var libroRecomendados: MutableSet<Libro>,
    private var descripcion: String
    /*var valoraciones: Set<Valoracion>  <-IMPLEMENTAR VALEN*/
) {
    /***
    * Los usuarios del sistema pueden crear recomendaciones de una serie de libros para publicar en ReadApp.
    * Estas recomendaciones pueden ser públicas (disponibles para cualquier usuario de la app) o privadas (solo para los amigos).
    * Las recomendaciones, sólo pueden ser editadas por el usuario creador, o por un amigo, si es que este último leyó todos los libros recomendados.
    * El creador no puede agregar libros que no haya leído, y en el caso de que un amigo edite una recomendación, no puede agregar un libro que no haya leído él y el creador.
    En todos los casos se debe emitir una reseña o detalle, necesaria/o para cautivar a otros usuarios.
    * Debemos poder determinar el tiempo de lectura que puede llevarle a un usuario leer todos los libros recomendados, el tiempo que se puede ahorrar (si ya leyó alguno/s) y el tiempo neto (si al leer los libros de la recomendación evita la relectura).
    Por otro lado, los usuarios que no sean el creador podrán dejarle una valoración siempre y cuando haya leído todos los libros, o si estos pertenecen a un único autor y sea uno de sus autores preferidos. La valoración cuenta con un valor del 1 al 5 y un comentario; los usuarios que ya hayan emitido una valoración no pueden emitir otra nueva, pero sí editar la que emitieron.
     ***/

    /*SETTERS*/

    fun editarPrivacidad(usuarioQueEdita: Usuario) {
        if(esCreador(usuarioQueEdita)) {
            alternarPrivacidad()
        }
        /*CAPTURAR ERROR*/
    }
    fun editarDescripcion(usuarioQueEdita: Usuario,libro: Libro, descripcionNueva: String) {
        if(validarEdicion(usuarioQueEdita,libro)) {
            cambioDeValoracion(descripcionNueva)
        }
        /*CAPTURAR ERROR*/
    }

    fun agregarALibrosDeRecomendacion(usuarioQueEdita: Usuario, libro: Libro) {
        if(validarEdicion(usuarioQueEdita,libro)) {
            libroRecomendados.add(libro)
        }
        /*CAPTURAR ERROR*/
    }

    fun crearValoracion(valor: Int, comentario: String, lector: Usuario ){

    }

    fun editarValoracion(valor: Int, comentario: String, lector: Usuario) {

    }

    /*GETTERS*/
    fun leerRecomendacion(usuarioQueLee: Usuario): Boolean = !esPrivado || puedeLeerLaRecomendacion(usuarioQueLee)

    fun tiempoDeLecturaTotal(lector: Usuario): Int = libroRecomendados.sumOf { lector.tiempoDeLectura(it)}

   //IMPLEMENTAR/fun tiempoDeLecturaRapida(lector: Usuario): Int = libroRecomendados.subtract(lector.librosLeidos).sumOf { lector.tiempoDeLectura(it) }

    fun tiempoDeLecturaAhorrado(lector: Usuario): Int {
        return 0
    }


    /*AUX*/

    private fun validarEdicion(usuarioQueEdita: Usuario, libro: Libro): Boolean = puedeAgregarElCreador(usuarioQueEdita,libro) ||  puedeAgregarElUsuarioAmigo(usuarioQueEdita,libro)

    private fun puedeAgregarElCreador(usuarioQueEdita: Usuario, libro: Libro): Boolean = esCreador(usuarioQueEdita) && sePuedeAgregarLibro(usuarioQueEdita,libro)

    private fun esCreador(usuarioQueEdita: Usuario): Boolean = creador == usuarioQueEdita

    private fun sePuedeAgregarLibro(usuarioQueEdita: Usuario, libro: Libro): Boolean = !existeLibro(libro) && libroYaFueLeidoPorCreador(libro)

    private fun existeLibro(libro: Libro): Boolean = libroRecomendados.contains(libro)

    private fun libroYaFueLeidoPorCreador(libro: Libro,):Boolean = creador.librosLeidos.contains(libro)
    private fun libroYaFueLeidoPorAmigo(usuarioQueEdita: Usuario,libro: Libro,):Boolean = usuarioQueEdita.librosLeidos.contains(libro)

    private fun amigoleidosTodos(usuarioQueEdita: Usuario): Boolean = usuarioQueEdita.librosLeidos.containsAll(libroRecomendados)

    fun validarValoracion(lector: Usuario){

    }
    private fun alternarPrivacidad() {
        esPrivado = !esPrivado
    }
    private  fun cambioDeValoracion(texto: String){
        descripcion = texto
    }
}

