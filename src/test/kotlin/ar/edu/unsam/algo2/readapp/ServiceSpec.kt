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

    //Arrange
    val autorPipoConsagrado =
        Autor("pipo", "Alegre", "yagoo", LocalDate.of(1969, 3, 27), mutableSetOf(), Lenguaje.es_ES, 2)

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
            //Assert
            val servicioNuevo = stubRepository()
            //Act
            repo.create(libroDesafiante) // [0]
            repo.create(libroDesafiante2) // [0,1]
            repo.create(libroDesafiante3) // [0,1,2]
            repo.delete(libroDesafiante2) // [0,2]
            repo.create(libroDesafiante2) // [0,1,2]
            val jsonAImprimir = servicioNuevo.getLibros(repo)
            jsonAImprimir shouldBe "esto es un json de prueba"
        }
        it("retorana un objeto librosActualizados"){
            //Arrange
            val servicio = stubRepositoryLibroActualizado()
            //Act
            val jsonAImprimir = servicio.getObjectLibros(repo)
            //Assert
            jsonAImprimir shouldBe LibrosActualizados(
                listOf(
                    LibroActualizado(0, 0, 0),
                    LibroActualizado(1, 0, 0),
                    LibroActualizado(2, 0, 0)
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
                LibroActualizado(0, 0, 0),
                LibroActualizado(1, 0, 0),
                LibroActualizado(2, 0, 0)
            )
        )
    }
    return service
}