@file:Suppress("SpellCheckingInspection")

package ar.edu.unsam.algo2.readapp

import java.util.SortedMap

abstract class Repository<T : Identidad> {
    private var currentId: Int = 0
    val dataMap: SortedMap<Int, T> = sortedMapOf()

    fun create(obj: T) {
        if (obj.id == null) {
            dataMap[currentId] = obj
            obj.id = currentId
            currentId++
        } else {
            throw Businessexception("$obj ya creado")
        }
    }

    fun delete(obj: T) {
        try {
            val objeto = getById(obj.id!!)
            dataMap.remove(objeto.id)
            obj.id = null
        } catch (e: NullPointerException) {
            throw Businessexception("No se puede eliminar")
        }

    }

    fun getById(id: Int): T =
        dataMap[id] ?: throw Businessexception("No existe un objeto con id $id")

    abstract fun update(obj: T)

    abstract fun search(regex: String): List<T>
}

class RepositorioLibros(private val updateLibros: UpdateLibros) : Repository<Libro>() {

    override fun search(regex: String): List<Libro> =
        matchNombrePartial(regex) + matchApellidoPartial(regex)

    /*AUX*/
    private fun matchNombrePartial(regex: String): List<Libro> =
        dataMap.values.filter { it.getNombre().contains(regex, ignoreCase = true) }

    private fun matchApellidoPartial(regex: String): List<Libro> =
        dataMap.values.filter { it.autor.getApellido().contains(regex, ignoreCase = true) }

    override fun update(obj: Libro) {
        try {
            getById(obj.id!!)
            val cambios = updateLibros.update(obj)
            dataMap[cambios.id] = cambios
        } catch (e: Businessexception) {
            throw Businessexception("No se puede actualizar")
        }
    }
}

class RepositorioUsuario : Repository<Usuario>() {

    override fun search(regex: String): List<Usuario> =
        (matchNombrePartial(regex) + matchApellidoParcial(regex) + matchUserNameCompleto(regex)).distinct()

    /*AUX*/
    private fun matchNombrePartial(regex: String): List<Usuario> =
        dataMap.values.filter { it.nombre.contains(regex, ignoreCase = true) }

    private fun matchApellidoParcial(regex: String): List<Usuario> =
        dataMap.values.filter { it.apellido.contains(regex, ignoreCase = true) }

    private fun matchUserNameCompleto(regex: String): List<Usuario> =
        dataMap.values.filter { it.username == regex }

    override fun update(obj: Usuario) { }
}

class RepositorioAutores : Repository<Autor>() {

    override fun search(regex: String): List<Autor> =
        (nombrePartialMatch(regex) + apellidoPartialMatch(regex) + userNameExactMatch(regex)).distinct()

    /*AUX*/
    private fun nombrePartialMatch(regex: String): List<Autor> =
        dataMap.values.filter { it.getNombre().contains(regex, ignoreCase = true) }

    private fun apellidoPartialMatch(regex: String): List<Autor> =
        dataMap.values.filter { it.getApellido().contains(regex, ignoreCase = true) }

    private fun userNameExactMatch(regex: String): List<Autor> =
        dataMap.values.filter { it.getSeudonimo() == regex }

    override fun update(obj: Autor) {}
}

class RepositorioRecomendaciones : Repository<Recomendacion>() {

    override fun search(regex: String): List<Recomendacion> =
        (apellidoExactMatch(regex) + nombrePartialMatch(regex) + reseniaPartialMatch(regex)).distinct()

    /*AUX*/
    private fun apellidoExactMatch(regex: String): List<Recomendacion> =
        dataMap.values.filter { it.creador.apellido == regex }

    private fun nombrePartialMatch(regex: String): List<Recomendacion> =
        dataMap.values.filter { recomendacion ->
            recomendacion.librosRecomendados.any { it.getNombre().contains(regex, ignoreCase = true) } }

    private fun reseniaPartialMatch(regex: String): List<Recomendacion> =
        dataMap.values.filter { recomendacion ->
            recomendacion.valoraciones.values.any { it.comentario.contains(regex, ignoreCase = true) } }

    override fun update(obj: Recomendacion) {}
}

class RepositorioCentroDeLectura : Repository<CentroDeLectura>() {

    override fun search(regex: String): List<CentroDeLectura> = libroExactMatch(regex).distinct()

    /*AUX*/
    private fun libroExactMatch(regex: String): List<CentroDeLectura> =
        dataMap.values.filter { it.getLibroAsignadoALeer().getNombre() == regex }

    override fun update(obj: CentroDeLectura) {}
}


class Businessexception(message: String) : Exception(message)