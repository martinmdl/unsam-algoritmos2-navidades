@file:Suppress("SpellCheckingInspection")

package ar.edu.unsam.algo2.readapp

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.reflection.beLateInit
import org.jetbrains.annotations.Nullable
import java.time.LocalDate

/*STUB MANUAL*/
data object Store {
    val Jorge = Autor(
        "pipo",
        "Alegre",
        "yagoo",
        LocalDate.of(1990, 3, 27),
        mutableSetOf(),
        Lenguaje.es_ES,
        2
    )
    val LibroAutorJorge = Libro(
        "HarryPotter",
        "Salamandra",
        800,
        75000,
        true,
        1,
        setOf(Lenguaje.es_ES, Lenguaje.ja_JP, Lenguaje.fr_FR, Lenguaje.ru_RU, Lenguaje.hi_IN),
        1,
        Jorge
    )
}

class StubCentroDeLectura : CentroDeLectura(
    nombreDeCentroDeLectura = "STUB CENTRO",
    direccion = "STUB DIR",
    libroAsignadoALeer = Store.LibroAutorJorge,
    costoDeReserva = 2500.0,
    conjuntoDeEncuentros = mutableSetOf()
) {
    init {
        val encuentroQueNoVenceNunca = Encuentro(
            fecha = LocalDate.now().plusDays(1),
            duracion = 60,
            disponible = 10,
            centroDeLectura = this
        )
        val encuentroQueNoVenceNunca2DiaDespues = Encuentro(
            fecha = LocalDate.now().plusDays(2),
            duracion = 60,
            disponible = 10,
            centroDeLectura = this
        )
        val encuentroQueNoVenceNunca3DiaDespues = Encuentro(
            fecha = LocalDate.now().plusDays(3),
            duracion = 60,
            disponible = 10,
            centroDeLectura = this
        )
    }

    override fun maximaCapacidadPorEncuentro(): Int {
        // Implementación de prueba
        return 10
    }

    override fun costo(encuentro: Encuentro): Double {
        // Implementación de prueba
        return 50.0
    }
}

/*Test Unitario*/
class CentroDeLecturaSpec : DescribeSpec(


) {


}