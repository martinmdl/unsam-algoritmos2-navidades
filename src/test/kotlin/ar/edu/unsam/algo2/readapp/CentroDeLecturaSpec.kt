@file:Suppress("SpellCheckingInspection")

package ar.edu.unsam.algo2.readapp

import io.kotest.core.spec.style.DescribeSpec
import java.time.LocalDate

/*STUB MANUAL*/
class StubCentroDeLectura : CentroDeLectura(
    conjuntoDeEncuentros = mutableSetOf(),
    costoDeReserva = 133.336,
    nombreDeCentroDeLectura = "STUB CENTRO",
    direccion = "SUB DIR",
    libroAsignadoALeer = Libro(
        nombre = "Nombre del Libro",
        editorial = "Nombre de la Editorial",
        paginas = 200,
        cantPalabras = 50000,
        lecturaCompleja = true,
        ediciones = 3,
        idioma = mutableSetOf(Lenguaje.es_ES),
        ventasSemanales = 1000,
        autor = Autor(
            fechaNac = LocalDate.of(1980, 1, 1),
            librosEscritos = mutableSetOf(),
            lenguaNativa = Lenguaje.es_ES,
            premios = 5
        )

    ),


    ) {
    init {

    }


    override fun costo(encuentro: Encuentro): Double {
        TODO("Not yet implemented")
    }

    override fun maximaCapacidadPorEncuentro(): Int {
        TODO("Not yet implemented")
    }

}

class CentroDeLecturaSpec: DescribeSpec(










) {



}