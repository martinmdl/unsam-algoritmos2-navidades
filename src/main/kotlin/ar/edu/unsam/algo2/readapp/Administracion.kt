@file:Suppress("SpellCheckingInspection")

package ar.edu.unsam.algo2.readapp

class Administracion {

    val usuariosRegistrados: MutableSet<Usuario> = mutableSetOf()
    val autoresRegistrados: MutableSet<Autor> = mutableSetOf()
    val centrosDeLecturaRegistrados: MutableSet<CentroDeLectura> = mutableSetOf()

    fun run(program: List<ProcesoAdministracion>) {
        program.forEach { it.ejecutar(this) }
    }
}