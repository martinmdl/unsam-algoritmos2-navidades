@file:Suppress("SpellCheckingInspection")

package ar.edu.unsam.algo2.readapp

abstract class Repository<T> {
    private var currentId: Int = 0
    var dataMap: MutableMap<Int, T> = mutableMapOf()


    fun create(obj: T) {
        dataMap[currentId] = obj
        currentId++
    }

    fun delete(obj: T) {
        dataMap.remove(currentId)
    }

    fun sort() {
        dataMap = dataMap.toSortedMap()
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
        //Libros: El valor de búsqueda debe coincidir parcialmente con el titulo o apellido del Autor.

        val nombreMatch: List<Libro> = dataMap.values.filter { it.getNombre().contains(regex, ignoreCase = false) }

        val apellidoMatch: List<Libro> = dataMap.values.filter { it.autor.getApellido().contains(regex, ignoreCase = false) }

        return nombreMatch + apellidoMatch
    }
}

class RepositorioUsuario : Repository<Usuario>() {
    // Usuario: El valor de búsqueda debe coincidir parcialmente con su nombre o apellido, o exáctamente con su username.
    override fun search(regex: String) {
        TODO("Not yet implemented")
    }
}

class RepositorioAutores : Repository<Autor>() {
    //Autores: El valor de búsqueda debe coincidir parcialmente con el nombre o apellido, y exactamente con el seudónimo.
    override fun search(regex: String) {
        TODO("Not yet implemented")
    }
}

//Recomendaciones: El valor de búsqueda exactamente con el apellido del creador o parcialmente con alguno de los títulos de los libros o reseña
class RepositorioRecomendaciones : Repository<Recomendacion>() {
    override fun search(regex: String) {
        TODO("Not yet implemented")
    }
}

//Centros de Lectura: El valor de búsqueda debe coincidir exactamente con el nombre del T
class RepositorioCentroDeLectura : Repository<CentroDeLectura>() {
    override fun search(regex: String) {
        TODO("Not yet implemented")
    }
}


