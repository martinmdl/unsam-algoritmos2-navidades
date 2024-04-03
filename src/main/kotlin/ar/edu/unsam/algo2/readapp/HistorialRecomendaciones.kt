@file:Suppress("SpellCheckingInspection")
package ar.edu.unsam.algo2.readapp

object HistorialRecomendaciones {

    var historialRecomendaciones: MutableSet<Recomendacion> = mutableSetOf()

    // testear elementos repetidos
    fun agregarAlHistorial(recomendacion: Recomendacion) {
        historialRecomendaciones.add(recomendacion)
    }

    fun eliminarDelHistorial(recomendacion: Recomendacion){
        historialRecomendaciones.remove(recomendacion)
    }

    // GETTER
    fun historialRecomendaciones (): MutableSet<Recomendacion> = historialRecomendaciones
}