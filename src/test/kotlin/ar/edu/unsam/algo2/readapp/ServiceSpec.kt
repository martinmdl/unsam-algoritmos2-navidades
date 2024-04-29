@file:Suppress("SpellCheckingInspection")

package ar.edu.unsam.algo2.readapp

import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.collections.shouldNotContain
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

    describe("Funcionamiento del create y remove fun") {

        it("lista de libros") {
                //Act
                repo.create(libroDesafiante) // [0]
                repo.create(libroDesafiante2) // [0,1]
                repo.create(libroDesafiante3) // [0,1,2]
                repo.delete(libroDesafiante2) // [0,2]
                repo.create(libroDesafiante2) // [0,1,2]
                val listaDeID = repo.dataMap.keys.toList()
            listaDeID shouldBe listOf(0,1,2)
        }

    }

    describe("Funcionamiento service") {
        it("lista de libros") {
            //Assert
            val servicioNuevo = stubRepository()
            //Act
            repo.create(libroDesafiante) // [0]
            repo.create(libroDesafiante2) // [0,1]
            repo.create(libroDesafiante3) // [0,1,2]
            repo.delete(libroDesafiante2) // [0,2]
            repo.create(libroDesafiante2) // [0,1,2]
            val jsonAImprimir = servicioNuevo.getLibros(repo)
            println(jsonAImprimir)
        }
    }
})

fun stubRepository(): Service {
    val service = mockk<Service>(relaxUnitFun = true)
    every { service.getLibros(any()) } returns mutableListOf(
        mapOf("id" to 0,"ediciones" to 1, "ventasSemanales" to 1),
        mapOf("id" to 1,"ediciones" to 2, "ventasSemanales" to 2),
        mapOf("id" to 2,"ediciones" to 3, "ventasSemanales" to 3))
    return service
}