@file:Suppress("SpellCheckingInspection")

package ar.edu.unsam.algo2.readapp

abstract class ProcesoAdministracion(private val mailSender: MailSender) {

    protected open lateinit var tipoProceso: String

    fun ejecutar(adm: Administracion) {
        this.enviarMail(mailSender)
        this.accionarProceso(adm)
    }

    private fun enviarMail(mailSender:MailSender) {
        val mail = Mail(
            from = "System@not.com",
            to = "admin@readapp.com.ar",
            subject = "Se ejecuto un proceso",
            content = "Se realizó el proceso: $tipoProceso"
        )
        mailSender.sendMail(mail)
    }

    abstract fun accionarProceso(adm: Administracion)
}

//Borrar Usuarios Inactivos: estos son aquellos que no generaron recomendaciones,
// ni evaluaron alguna y tampoco ha sido considerado amigo por otro usuario.

//Actualización de Libros: Al ejecutarlo se actualizarán los libros del repositorio,
// utilizando un ServiceLibros.

//Borrar Centros de Lecturas expirados: es decir, eliminar de la aplicación los Centros de Lectura que
// hayan expirado su publicación/divulgación.

//Agregar Autores: agregar de forma masiva autores, partiendo de una lista dada.


class BorrarUsuarioInactivo(mailSender: MailSender ) : ProcesoAdministracion(mailSender) {

    override var tipoProceso: String = "BorrarUsuarioActivo"

    override fun accionarProceso(adm: Administracion) {
        for (usuario in adm.usuariosRegistrados) {
            adm.usuariosRegistrados.removeIf { it.esActivo() }
        }
    }
}

class ActualizacionDeLibro(mailSender: MailSender, private val repoLibros: RepositorioLibros) : ProcesoAdministracion(mailSender) {

    override var tipoProceso: String = "ActualizacionDeLibro"

    override fun accionarProceso(adm: Administracion) {
        for(libro in repoLibros.dataMap.values) {
            repoLibros.update(libro)
        }
    }
}

class BorrarCentroDeLecturaExpirados(mailSender: MailSender) : ProcesoAdministracion(mailSender) {

    override var tipoProceso: String = "BorrarCentroDeLecturaExpirados"

    override fun accionarProceso(adm: Administracion) {
        adm.centrosDeLecturaRegistrados.removeIf { it.seVencieronTodasLasFechas() }
    }
}

class AgregarAutores(mailSender: MailSender, private val autoresPorAgregar: MutableSet<Autor>) : ProcesoAdministracion(mailSender) {

    override var tipoProceso: String = "AgregarAutores"

    override fun accionarProceso(adm: Administracion) {
        for(autor in autoresPorAgregar) {
            adm.autoresRegistrados.add(autor)
        }
    }
}

