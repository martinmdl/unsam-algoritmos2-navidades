@file:Suppress("SpellCheckingInspection")

package ar.edu.unsam.algo2.readapp

import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import java.time.LocalDate

class ServiceSpec: DescribeSpec({

    isolationMode = IsolationMode.InstancePerLeaf

    val autorPipoConsagrado =
        Autor(null, "pipo", "Alegre", "yagoo", LocalDate.of(1969, 3, 27), mutableSetOf(), Lenguaje.es_ES, 2)

    val libroDesafiante = Libro(
        id = null,
        "HarryPotter",
        "Salamandra",
        800,
        100000,
        true,
        1,
        setOf(Lenguaje.es_ES),
        1,
        autorPipoConsagrado
    )

    val libroDesafiante2 = Libro(
        id = null,
        "HarryPotter2",
        "Salamandra",
        800,
        100000,
        true,
        2,
        setOf(Lenguaje.es_ES),
        2,
        autorPipoConsagrado
    )

    val libroDesafiante3 = Libro(
        id = null,
        "HarryPotter3",
        "Salamandra",
        800,
        100000,
        true,
        3,
        setOf(Lenguaje.es_ES),
        3,
        autorPipoConsagrado
    )

    val repo = RepositorioLibros()

    describe("Funcionamiento service"){
        it("lista de libros en formato json String"){

            val servicioNuevo = stubRepository()

            repo.create(libroDesafiante)  // [0]
            val jsonAImprimir = servicioNuevo.getLibros(repo)
            jsonAImprimir shouldBe "esto es un json de prueba"
        }
        it("retornara un objeto librosActualizados"){
            val servicio = stubRepositoryLibroActualizado()
            val jsonAImprimir = servicio.getObjectLibros(repo)
            jsonAImprimir shouldBe LibrosActualizados(
                listOf(
                    LibroActualizado(5, 6, 15000),
                    LibroActualizado(12, 1, 1000),
                    LibroActualizado(15, 3, 11000),
                    LibroActualizado(2, 2, 2000)
                )
            )
        }
    }
})

fun stubRepository(): Service {
    val service = mockk<Service>(relaxUnitFun = true)
    every { service.getLibros(any()) } returns "esto es un json de prueba"
    return service
}

fun stubRepositoryLibroActualizado(): ServiceLibro {
    val service = mockk<ServiceLibro>(relaxUnitFun = true)
    every { service.getObjectLibros(any()) } answers {
        LibrosActualizados(
            listOf(
                LibroActualizado(5, 6, 15000),
                LibroActualizado(12, 1, 1000),
                LibroActualizado(15, 3, 11000),
                LibroActualizado(2, 2, 2000)
            )
        )
    }
    return service
}