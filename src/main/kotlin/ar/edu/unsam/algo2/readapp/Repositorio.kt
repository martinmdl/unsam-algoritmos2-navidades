@file:Suppress("SpellCheckingInspection")

package ar.edu.unsam.algo2.readapp

import java.util.SortedSet

abstract class Repository<T> {
    private var currentId: Int = 0
    private val setRemoveId: SortedSet<Int> = sortedSetOf()
    val dataMap: MutableMap<Int, T> = mutableMapOf()

    fun create(obj: T) {
        if (setRemoveId.isEmpty()) {
            dataMap[currentId] = obj
            currentId++
        } else {
            dataMap[setRemoveId.first()] = obj
            setRemoveId.removeFirst()
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

    /*AUX*/
    /** [checkExistance]
     * Verifica la existencia de un [T] en la coleccion [dataMap]
     *  @param  obj que es un [T]
     *  @return 'true' si existe o 'false' si no.
     */
    private fun checkExistance(obj: T): Boolean = dataMap.filterValues { it == obj }.isNotEmpty()

    /** [getIndex]
     * Devuelve el índice de [T]
     * @param  obj de tipo [T]
     * @return [Int]
     */
    private fun getIndex(obj: T): Int = dataMap.entries.find { it.value == obj }!!.key

}

class RepositorioLibros : Repository<Libro>() {
    override fun search(regex: String): List<Libro> {
        val nombrePartialMatch: List<Libro> =
            dataMap.values.filter { it.getNombre().contains(regex, ignoreCase = true) }
        val apellidoPartialMatch: List<Libro> =
            dataMap.values.filter { it.autor.getApellido().contains(regex, ignoreCase = true) }
        return nombrePartialMatch + apellidoPartialMatch
    }
}

class RepositorioUsuario : Repository<Usuario>() {
    override fun search(regex: String): List<Usuario> {
        val nombrePartialMatch: List<Usuario> = dataMap.values.filter { it.nombre.contains(regex, ignoreCase = true) }
        val apellidoPartialMatch: List<Usuario> =
            dataMap.values.filter { it.apellido.contains(regex, ignoreCase = true) }
        val userNameExactMatch: List<Usuario> = dataMap.values.filter { it.username == regex }
        return (nombrePartialMatch + apellidoPartialMatch + userNameExactMatch).distinct()
    }
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
    override fun search(regex: String): List<CentroDeLectura> {
        val libroExactMatch: List<CentroDeLectura> =
            dataMap.values.filter { it.getLibroAsignadoALeer().getNombre() == regex }
        return libroExactMatch.distinct()
    }
}