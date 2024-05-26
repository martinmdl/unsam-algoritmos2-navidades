@file:Suppress("SpellCheckingInspection")

package ar.edu.unsam.algo2.readapp

interface RecomendacionObserver {
    fun libroAgregado(libro: Libro, usuario: Usuario, recomendacion: Recomendacion)
}

open class NotificarCreador(private val mailSender: MailSender) : RecomendacionObserver {

    override fun libroAgregado(libro: Libro, usuario: Usuario, recomendacion: Recomendacion) {

        if (usuario != recomendacion.creador) {
            val listadoDeTitulosSinElAgregado = recomendacion.librosRecomendados.joinToString(", ") { it.getNombre() }
            val msj =
                "El usuario: ${usuario.nombre} agrego el Libro ${libro.getNombre()} a la recomendación que tenía estos Títulos: $listadoDeTitulosSinElAgregado"
            val mail = Mail(
                from = "notificaciones@readapp.com.ar",
                to = recomendacion.creador.direccionEmail,
                subject = "Se agregó un Libro",
                content = msj
            )
            mailSender.sendMail(mail)
        }
    }
}

open class Registro : RecomendacionObserver {

    private val registroAportes: MutableMap<Usuario, MutableList<Libro>> = mutableMapOf()

    override fun libroAgregado(libro: Libro, usuario: Usuario, recomendacion: Recomendacion) {
        val librosAgregados = registroAportes.getOrPut(usuario) { mutableListOf() }
        librosAgregados.add(libro)
    }

    fun getRegistro(): MutableMap<Usuario, MutableList<Libro>> = this.registroAportes
}

open class BannearSpammer(private val limiteDeLibro: Int) : RecomendacionObserver {

    private val registroContador: MutableMap<Usuario, Int> = mutableMapOf()

    override fun libroAgregado(libro: Libro, usuario: Usuario, recomendacion: Recomendacion) {
        val valorActual = registroContador.getOrDefault(usuario, 0)
        registroContador[usuario] = valorActual + 1
        if (llegoLimite(usuario) && noEsCreador(usuario, recomendacion)) {
            recomendacion.creador.eliminarAmigo(usuario)
        }
    }

    private fun llegoLimite(usuario: Usuario) = registroContador[usuario] == limiteDeLibro
    private fun noEsCreador(usuario: Usuario, recomendacion: Recomendacion) = usuario != recomendacion.creador

    fun getRegistro(): MutableMap<Usuario, Int> = this.registroContador
}

open class ValoracionAutomatica : RecomendacionObserver {

    override fun libroAgregado(libro: Libro, usuario: Usuario, recomendacion: Recomendacion) {
        if(noEsCreador(usuario, recomendacion) && noValoro(recomendacion, usuario)) {
            recomendacion.crearValoracion(5, "Excelente 100% recomendable", usuario)
        }
    }
    
    // AUX
    private fun noEsCreador(usuario: Usuario, recomendacion: Recomendacion): Boolean = usuario != recomendacion.creador
    private fun noValoro(recomendacion: Recomendacion, usuario: Usuario): Boolean = !recomendacion.valoraciones.contains(usuario)
}