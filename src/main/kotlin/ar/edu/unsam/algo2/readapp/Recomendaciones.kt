@file:Suppress("SpellCheckingInspection")

package ar.edu.unsam.algo2.readapp

class Recomendaciones(
    var privacidad: Boolean, //privacidad en true es publico, en false es privado
    val creador: Usuario,
    var libroRecomendados: MutableSet<Libro>,
    var descripcion: String
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

    fun editarPrivacidad(editor: Usuario) {
        if(validarUsuario(editor)) {
            alternarPrivacidad()
        }
        /*CAPTURAR ERROR*/
    }
    fun editarDescripcion(editor: Usuario, descripcionNueva: String) {
        if(validarUsuario(editor)) {
            cambioDeValoracion(descripcionNueva)
        }
        /*CAPTURAR ERROR*/
    }

    fun agregarALibrosDeRecomendacion(editor: Usuario, libro: Libro) {
        if(validarUsuario(editor) && existeLibro(libro) && validarAgregar(editor, libro)) {
            libroRecomendados.add(libro)
        }
        /*CAPTURAR ERROR*/
    }

    fun quitarDeLibrosDeRecomendacion(editor: Usuario, libro: Libro) {
        if(validarUsuario(editor) && existeLibro(libro) && validarAgregar(editor, libro)) {
            libroRecomendados.remove(libro)
        }
        /*CAPTURAR ERROR*/
    }

    fun crearValoracion(valor: Int, comentario: String, lector: Usuario ){

    }

    fun editarValoracion(valor: Int, comentario: String, lector: Usuario) {

    }

    /*GETTERS*/
    fun leerRecomendacion(lector: Usuario): Boolean = privacidad || validarUsuario(lector)

    fun tiempoDeLecturaTotal(lector: Usuario): Int {
        return 0
    }

    fun tiempoDeLecturaAhorrado(lector: Usuario): Int {
        return 0
    }


    /*AUX*/

    private fun validarUsuario(lector: Usuario): Boolean = this.creador == lector ||  esAmigoPermitido(lector)  //[LOS DE USUARIO AGREGAR SET DE AMIGOS]

    private fun esAmigoPermitido(lector: Usuario): Boolean = esAmigo(lector) && condicionDeLectura(lector)

    private fun condicionDeLectura(lector: Usuario): Boolean = amigoleidosTodos(lector)
    private fun esAmigo(lector: Usuario) = creador.amigos.contains(lector)

    private fun usuarioYaLeido(usuario: Usuario,libro: Libro,):Boolean = usuario.librosLeidos.contains(libro)

    private fun amigoleidosTodos(lector: Usuario): Boolean = lector.librosLeidos.containsAll(libroRecomendados)

    private fun validarAgregar(editor: Usuario, libro: Libro) = usuarioYaLeido(editor,libro) && creadorYaLeido(libro)

    private fun creadorYaLeido(libro: Libro): Boolean = creador.librosLeidos.contains(libro)

    fun validarValoracion(lector: Usuario){

    }
    private fun alternarPrivacidad() {
        privacidad = !privacidad
    }
    private  fun cambioDeValoracion(texto: String){
        descripcion = texto
    }

    private fun existeLibro(libro: Libro): Boolean = libroRecomendados.contains(libro)

}

