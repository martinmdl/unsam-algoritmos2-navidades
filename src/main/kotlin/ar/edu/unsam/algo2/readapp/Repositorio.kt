@file:Suppress("SpellCheckingInspection")

package ar.edu.unsam.algo2.readapp

import java.util.SortedMap
import java.util.SortedSet

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
        setRemoveId.add(getIndex(obj))
        dataMap.remove(getIndex(obj))
    }

    fun update(obj: T) {
        if (checkExistance(obj)) {
            dataMap[getIndex(obj)] = obj
        }
    }

    fun getById(id: Int) = dataMap[id]

    abstract fun search(regex: String): List<T>
}

class RepositorioLibros : Repository<Libro>() {

    override fun search(regex: String): List<Libro> =
        matchNombrePartial(regex) + matchApellidoPartial(regex)

    /*AUX*/
    private fun matchNombrePartial(regex: String): List<Libro> =
        dataMap.values.filter { it.getNombre().contains(regex, ignoreCase = true) }

    private fun matchApellidoPartial(regex: String): List<Libro> =
        dataMap.values.filter { it.autor.getApellido().contains(regex, ignoreCase = true) }
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
}

class RepositorioAutores : Repository<Autor>() {
    override fun search(regex: String): List<Autor> {
        val nombrePartialMatch: List<Autor> =
            dataMap.values.filter { it.getNombre().contains(regex, ignoreCase = true) }
        val apellidoPartialMatch: List<Autor> =
            dataMap.values.filter { it.getApellido().contains(regex, ignoreCase = true) }
        val userNameExactMatch: List<Autor> = dataMap.values.filter { it.getSeudonimo() == regex }
        return (nombrePartialMatch + apellidoPartialMatch + userNameExactMatch).distinct()
    }
}

class RepositorioRecomendaciones : Repository<Recomendacion>() {
    override fun search(regex: String): List<Recomendacion> {
        val apellidoExactMatch: List<Recomendacion> = dataMap.values.filter { it.creador.apellido == regex }

        val nombrePartialMatch: List<Recomendacion> = dataMap.values.filter { recomendacion ->
            recomendacion.librosRecomendados.any {
                it.getNombre().contains(regex, ignoreCase = true)
            }
        }

        val reseniaPartialMatch: List<Recomendacion> = dataMap.values.filter { recomendacion ->
            recomendacion.valoraciones.values.any {
                it.comentario.contains(regex, ignoreCase = true)
            }
        }

        return (apellidoExactMatch + nombrePartialMatch + reseniaPartialMatch).distinct()
    }
}

class RepositorioCentroDeLectura : Repository<CentroDeLectura>() {

    override fun search(regex: String): List<CentroDeLectura> = libroExactMatch(regex).distinct()

    /*AUX*/
    private fun libroExactMatch(regex: String): List<CentroDeLectura> =
        dataMap.values.filter { it.getLibroAsignadoALeer().getNombre() == regex }
}

class Businessexception(message: String) : Exception(message)