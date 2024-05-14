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

    describe("Funcionamiento service"){
        it("lista de libros"){
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
    }
})

class RepositoryTest : DescribeSpec({
    describe("Repository, en particular RepositorioLibros pero funciona para cualquier Repository") {
        val repository = RepositorioLibros()
        val libro = mockk<Libro>()

        describe("create") {
            it("Agrega un libro al repositorio.") {
                repository.create(libro)
                repository.getById(0) shouldBe libro
            }
        }

        describe("delete") {
            it("Remueve un libro del repositorio.") {
                repository.create(libro)
                repository.delete(libro)
                repository.getById(0) shouldBe null
            }
        }

        describe("update") {
            it("Actualiza el estado de un libro en el repositorio.") {
                val libroOriginal = mockk<Libro>()
                val libroActualizado = mockk<Libro>()

                every { libroOriginal.getVentasSemanales() } returns 50000
                every { libroActualizado.getVentasSemanales() } returns 100000
                every { libroOriginal == libroActualizado } returns true
                every { libroActualizado == libroOriginal } returns true

                repository.create(libroOriginal)
                repository.update(libroActualizado)

                val libroEnRepositorio = repository.getById(0)
                libroEnRepositorio shouldBe libroActualizado
                libroEnRepositorio?.getVentasSemanales() shouldBe 100000
            }
        }

        describe("getById") {
            it("retrieves correct object from repository") {
                repository.create(libro)
                repository.getById(0) shouldBe libro
            }
        }

        describe("search, la logica es identica es el resto de los repositorios.") {
            it("Retorna true si hay un match parcial o exacto en el nombre o apellido del autor.") {
                val libroMatch = mockk<Libro>()
                val libroNoMatch = mockk<Libro>()
                val autorMatch = mockk<Autor>()
                val autorNoMatch = mockk<Autor>()
                val repositorySearch = RepositorioLibros()

                every { autorMatch.getApellido() } returns "Pipo"
                every { autorNoMatch.getApellido() } returns "Opip"
                every { libroMatch.getNombre() } returns "Es un libro de Prueba"
                every { libroMatch.autor } returns autorMatch
                every { libroNoMatch.getNombre() } returns "No Debe Haber Match"
                every { libroNoMatch.autor } returns autorNoMatch


                repositorySearch.create(libroMatch)
                repositorySearch.create(libroNoMatch)

                println(repositorySearch.dataMap)

                val results = repositorySearch.search("Es un libro de Prueba")

                println("Results: $results")
                results.contains(libroMatch) shouldBe true
                results.contains(libroNoMatch) shouldBe false
            }
        }
    }
})

fun stubRepository(): Service {
    val service = mockk<Service>(relaxUnitFun = true)
    every { service.getLibros(any()) } returns "esto es un json de prueba"
    return service
}