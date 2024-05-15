package ar.edu.unsam.algo2.readapp

import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.assertThrows
import java.time.LocalDate

class RepositorioSpec : DescribeSpec({
    isolationMode = IsolationMode.InstancePerLeaf
    val repository = RepositorioLibros()

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

    val autorMatch =
        Autor(null, "Pipo", "Alegre", "yagoo", LocalDate.of(1969, 3, 27), mutableSetOf(), Lenguaje.es_ES, 2)
    val autorNoMatch =
        Autor(null, "Opip", "Ergela", "yagoo", LocalDate.of(1969, 3, 27), mutableSetOf(), Lenguaje.es_ES, 2)

    val libroMatchNombre = Libro(
        id = null,
        "Es un libro de Prueba",
        "Salamandra",
        800,
        100000,
        true,
        1,
        setOf(Lenguaje.es_ES),
        1,
        autorNoMatch
    )

    val libroMatchApellido = Libro(
        id = null,
        "No Debe",
        "Salamandra",
        800,
        100000,
        true,
        1,
        setOf(Lenguaje.es_ES),
        1,
        autorMatch
    )

    val libroNoMatch = Libro(
        id = null,
        "No Debe",
        "Salamandra",
        800,
        100000,
        true,
        1,
        setOf(Lenguaje.es_ES),
        1,
        autorNoMatch
    )

    val repositorySearch = RepositorioLibros()

    describe("Repositorio general."){

        it("Se crea un libro") {
            repository.create(libroDesafiante) // [0]
            repository.dataMap.keys.toList() shouldBe listOf(0)
        }

        it("Se intenta crear un libro que ya existe") {
            repository.create(libroDesafiante)
            val e = assertThrows<Businessexception> { repository.create(libroDesafiante) }
            "$libroDesafiante ya creado" shouldBe e.message
        }

        it("Se crea un libro y se elimina") {
            repository.create(libroDesafiante) // [0]
            repository.delete(libroDesafiante) // []
            repository.dataMap.keys.toList() shouldBe listOf()
        }

        it("Se intenta eliminar un libro que no existe") {
            val e = assertThrows<Businessexception> { repository.delete(libroDesafiante) }
            "$libroDesafiante no existe" shouldBe e.message
        }

        it("Se busca un libro por id") {
            repository.create(libroDesafiante)
            repository.getById(0) shouldBe libroDesafiante
        }

        it("Se busca un libro por id que no existe") {
            val e = assertThrows<Businessexception> { repository.getById(3) }
            "No existe un objeto con id 3" shouldBe e.message
        }
    }

    describe("Repositorio Libro"){
        it("Search de libros por nombre de libro, devuelve el libro que coincide con el nombre") {
            repositorySearch.create(libroMatchNombre)
            repositorySearch.create(libroNoMatch)
            repositorySearch.search("Es un libro de Prueba") shouldBe listOf(libroMatchNombre)
        }

        it("Search de libros por el apellido del autor, devuelve el libro que coincide con el apellido del autor") {
            repositorySearch.create(libroMatchApellido)
            repositorySearch.create(libroNoMatch)
            repositorySearch.search("Alegre") shouldBe listOf(libroMatchApellido)
        }

        it("Search de libros por nombre de libro, no devuelve ninguno") {
            repositorySearch.create(libroMatchApellido)
            repositorySearch.create(libroNoMatch)
            repositorySearch.search("Es un libro de Prueba") shouldBe listOf()
        }

        it("Search de libros por apellido de autor, no devuelve ninguno") {
            repositorySearch.create(libroMatchNombre)
            repositorySearch.create(libroNoMatch)
            repositorySearch.search("Alegre") shouldBe listOf()
        }
    }

    describe("Repositorio Usuario"){

    }

    describe("Repositorio Autor"){

    }
    describe("Repositorio Recomendaciones."){

    }
    describe("Repositorio Centro de lectura"){

    }

})
