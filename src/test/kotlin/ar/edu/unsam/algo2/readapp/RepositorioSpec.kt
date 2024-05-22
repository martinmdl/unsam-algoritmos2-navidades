@file:Suppress("SpellCheckingInspection")
package ar.edu.unsam.algo2.readapp

import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.mockk.mockk
import org.junit.jupiter.api.assertThrows
import java.time.LocalDate

class RepositorioSpec : DescribeSpec({
    isolationMode = IsolationMode.InstancePerLeaf

    val serviceParaLibros = mockk<ServiceLibros>()
    val serviceLibro = UpdateLibros(serviceParaLibros)
    val repository = RepositorioLibros(serviceLibro)

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

    describe("Repositorio general.") {

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

    describe("Repositorio Libro") {
        val repositorySearch = RepositorioLibros(serviceLibro)

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

        it("Search de libros por nombre de libro, devuelve el libro que coincide con el nombre") {
            repositorySearch.create(libroMatchNombre)
            repositorySearch.create(libroNoMatch)
            repositorySearch.search("Es un libro de ") shouldBe listOf(libroMatchNombre)
        }

        it("Search de libros por el apellido del autor, devuelve el libro que coincide con el apellido del autor") {
            repositorySearch.create(libroMatchApellido)
            repositorySearch.create(libroNoMatch)
            repositorySearch.search("Ale") shouldBe listOf(libroMatchApellido)
        }

        it("Search de libros por nombre de libro, no devuelve ninguno") {
            repositorySearch.create(libroMatchApellido)
            repositorySearch.create(libroNoMatch)
            repositorySearch.search("Es un lib") shouldBe listOf()
        }

        it("Search de libros por apellido de autor, no devuelve ninguno") {
            repositorySearch.create(libroMatchNombre)
            repositorySearch.create(libroNoMatch)
            repositorySearch.search("Ale") shouldBe listOf()
        }
    }

    describe("Repositorio Usuario") {

        val serviceParaUsuarios = mockk<ServiceUsuario>()

        val serviceUsuario =  UpdateUsuario(serviceParaUsuarios)

        val repositorySearch = RepositorioUsuario(serviceUsuario)

        val autor =
            Autor(null, "Pipo", "Alegre", "yagoo", LocalDate.of(1969, 3, 27), mutableSetOf(), Lenguaje.es_ES, 2)

        val usuarioMatchNombre = Usuario(
            nombre = "Pipo",
            apellido = "Lojo",
            username = "juan",
            palabrasPorMinuto = 100,
            fechaNac = LocalDate.of(2000, 4, 23),
            direccionEmail = "pipo@gmail.com",
            autorFavorito = autor,
            lenguaNativa = Lenguaje.es_ES
        )

        val usuarioMatchApellido = Usuario(
            nombre = "Diego",
            apellido = "Alegre",
            username = "juan",
            palabrasPorMinuto = 100,
            fechaNac = LocalDate.of(2000, 4, 23),
            direccionEmail = "pipo@gmail.com",
            autorFavorito = autor,
            lenguaNativa = Lenguaje.es_ES
        )

        val usuarioMatchUsername = Usuario(
            nombre = "Diego",
            apellido = "lojo",
            username = "tiger",
            palabrasPorMinuto = 100,
            fechaNac = LocalDate.of(2000, 4, 23),
            direccionEmail = "pipo@gmail.com",
            autorFavorito = autor,
            lenguaNativa = Lenguaje.es_ES
        )

        it("Search parcial de usuario por nombre, devuelve un usuario") {
            repositorySearch.create(usuarioMatchNombre)
            repositorySearch.create(usuarioMatchApellido)
            repositorySearch.create(usuarioMatchUsername)
            repositorySearch.search("Pi") shouldBe listOf(usuarioMatchNombre)
        }

        it("Search parcial de usuario por apellido, devuelve un usuario") {
            repositorySearch.create(usuarioMatchNombre)
            repositorySearch.create(usuarioMatchApellido)
            repositorySearch.create(usuarioMatchUsername)
            repositorySearch.search("Ale") shouldBe listOf(usuarioMatchApellido)
        }

        it("Search exacta de usuario por username, devuelve un usuario") {
            repositorySearch.create(usuarioMatchNombre)
            repositorySearch.create(usuarioMatchApellido)
            repositorySearch.create(usuarioMatchUsername)
            repositorySearch.search("tiger") shouldBe listOf(usuarioMatchUsername)
        }
    }

    describe("Repositorio Autor") {
        val serviceParaAutor = mockk<ServiceAutor>()
        val serviceAutor =  UpdateAutor(serviceParaAutor)
        val repositorySearch = RepositorioAutores(serviceAutor)

        val autorMatchNombre =
            Autor(null, "frank", "fabra", "buro", LocalDate.of(1969, 3, 27), mutableSetOf(), Lenguaje.es_ES, 0)
        val autorMatchApellido =
            Autor(null, "Opip", "robe", "burro", LocalDate.of(1969, 3, 27), mutableSetOf(), Lenguaje.es_ES, 2)
        val autorMatchSeudonimo =
            Autor(null, "fran", "rober", "resid", LocalDate.of(1969, 3, 27), mutableSetOf(), Lenguaje.es_ES, 2)

        it("Search de Autor por nombre") {
            repositorySearch.create(autorMatchNombre)
            repositorySearch.create(autorMatchApellido)
            repositorySearch.create(autorMatchSeudonimo)
            repositorySearch.search("fran") shouldBe listOf(autorMatchNombre, autorMatchSeudonimo)
        }

        it("Search de Autor por Apellido") {
            repositorySearch.create(autorMatchNombre)
            repositorySearch.create(autorMatchApellido)
            repositorySearch.create(autorMatchSeudonimo)
            repositorySearch.search("rob") shouldBe listOf(autorMatchApellido, autorMatchSeudonimo)
        }

        it("Search de Autor por Seudonimo") {
            repositorySearch.create(autorMatchNombre)
            repositorySearch.create(autorMatchApellido)
            repositorySearch.create(autorMatchSeudonimo)
            repositorySearch.search("burro") shouldBe listOf(autorMatchApellido)
        }
    }

    describe("Repositorio Recomendaciones.") {

        val serviceParaRecomendaciones = mockk<ServiceRecomendaciones>()
        val serviceUpdateRecomendacion =  UpdateRecomendaciones(serviceParaRecomendaciones)
        val repoitorioRecomendaciones = RepositorioRecomendaciones(serviceUpdateRecomendacion)

        val librNoMatch = Libro(
            id = null,
            "No Debe",
            "Salamandra",
            800,
            100000,
            true,
            1,
            setOf(Lenguaje.es_ES),
            1,
            autorPipoConsagrado
        )
        val usuarioMatch = Usuario(
            id = null,
            nombre = "Diego",
            apellido = "Alegre",
            username = "pipo",
            palabrasPorMinuto = 100,
            fechaNac = LocalDate.of(2000, 4, 23),
            direccionEmail = "pipo@gmail.com",
            amigos = mutableSetOf(/* other Usuario instances */),
            librosLeidos = mutableMapOf(libroDesafiante to 1),
            autorFavorito = autorPipoConsagrado,
            recomendacionesPorValorar = mutableSetOf(),
            librosPorLeer = mutableSetOf(),
            lenguaNativa = Lenguaje.es_ES
        )
        val valracionMatc = Valoracion(
            usuarioMatch,
            2,
            "Esta es una resenia match"
        )
        val recomendacionMatch = Recomendacion(
            id = null,
            esPrivado = true,
            creador = usuarioMatch,
            librosRecomendados = mutableSetOf(libroDesafiante),
            descripcion = "Esto una recomendacion.",
            valoraciones = mutableMapOf(usuarioMatch to valracionMatc)
        )

        val usuarioNoMatch = Usuario(
            id = null,
            nombre = "Dogo",
            apellido = "Alga",
            username = "pipo",
            palabrasPorMinuto = 100,
            fechaNac = LocalDate.of(2000, 4, 23),
            direccionEmail = "pipo@gmail.com",
            amigos = mutableSetOf(/* other Usuario instances */),
            librosLeidos = mutableMapOf(librNoMatch to 1),
            autorFavorito = autorPipoConsagrado,
            recomendacionesPorValorar = mutableSetOf(),
            librosPorLeer = mutableSetOf(),
            lenguaNativa = Lenguaje.es_ES
        )

        val valracionNoMatc = Valoracion(
            usuarioNoMatch,
            5,
            "No tiene que haber Match."
        )
        val recomendacionNoMatch = Recomendacion(
            id = null,
            esPrivado = true,
            creador = usuarioNoMatch,
            librosRecomendados = mutableSetOf(librNoMatch),
            descripcion = "Esto una recomendacion.",
            valoraciones = mutableMapOf(usuarioNoMatch to valracionNoMatc)
        )


        it("Search de recommendation por apellido de creador, devuelve el libro que coincide con el nombre") {
            repoitorioRecomendaciones.create(recomendacionMatch)
            repoitorioRecomendaciones.create(recomendacionNoMatch)
            repoitorioRecomendaciones.search("Alegre") shouldBe listOf(recomendacionMatch)
        }

        it("Search de recommendation por match parcial del nombre del libro") {
            repoitorioRecomendaciones.create(recomendacionMatch)
            repoitorioRecomendaciones.create(recomendacionNoMatch)
            repoitorioRecomendaciones.search("HarryPotter") shouldBe listOf(recomendacionMatch)
        }

        it("Search de recommendation por resenia parcial.") {
            repoitorioRecomendaciones.create(recomendacionMatch)
            repoitorioRecomendaciones.create(recomendacionNoMatch)
            repoitorioRecomendaciones.search("Esta es una resenia match") shouldBe listOf(recomendacionMatch)
        }
    }

    describe("Repositorio Centro de lectura") {


        val serviceParaCentroDeLectura = mockk<ServiceCentroDeLectura>()
        val serviceUpdateCentroDeLectura=  UpdateCentroDeLectura(serviceParaCentroDeLectura)
        val repositorySearch = RepositorioCentroDeLectura(serviceUpdateCentroDeLectura)
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
            autorPipoConsagrado
        )

        val libroNoMatch = Libro(
            id = null,
            "No es",
            "Salamandra",
            800,
            100000,
            true,
            1,
            setOf(Lenguaje.es_ES),
            1,
            autorPipoConsagrado
        )

        val particular = Particular(
            nombreDeCentroDeLectura = "Centro de Lectura Particular",
            direccion = "Casa de Pipo",
            libroAsignadoALeer = libroMatchNombre,
            costoDeReserva = 100.0,
            conjuntoDeEncuentros = mutableSetOf(),
            capacidadMaximaFijada = 40,
            porcentajeMinimo = 5.0
        )

        val editorial = Editorial(
            nombreDeCentroDeLectura = "Centro de Lectura Editorial",
            direccion = "Casa de Pipo",
            libroAsignadoALeer = libroNoMatch,
            costoDeReserva = 2000.0,
            conjuntoDeEncuentros = mutableSetOf(),
            montoEspecificoAlcanzar = 20000,
            autorPresente = false
        )

        val biblioteca = Biblioteca(
            nombreDeCentroDeLectura = "Centro de Lectura Biblioteca",
            direccion = "Casa de Pipo",
            libroAsignadoALeer = libroMatchNombre,
            costoDeReserva = 2000.0,
            conjuntoDeEncuentros = mutableSetOf(),
            metrosCuadradosSala = 100,
            gastoFijo = mutableListOf(10000.0, 5000.0)
        )

        it("Search de centro de lectura por nombre de libro, devuelve un centro") {
            repositorySearch.create(particular)
            repositorySearch.search("Es un libro de Prueba") shouldBe listOf(particular)
        }

        it("Search de centro de lectura por nombre de libro, no devuelve ningun centro") {
            repositorySearch.create(editorial)
            repositorySearch.search("Es un libro de Prueba") shouldBe listOf()
        }

        it("Search de centro de lectura por nombre de libro, devuelve solo los que cumplen") {
            repositorySearch.create(particular)
            repositorySearch.create(editorial)
            repositorySearch.create(biblioteca)
            repositorySearch.search("Es un libro de Prueba") shouldBe listOf(particular, biblioteca)
        }
    }
})