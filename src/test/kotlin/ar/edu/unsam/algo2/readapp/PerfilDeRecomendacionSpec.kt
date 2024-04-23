@file:Suppress("SpellCheckingInspection")

package ar.edu.unsam.algo2.readapp

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import java.time.LocalDate

class PerfilDeRecomendacionSpec : DescribeSpec({
    describe("Test perfil de recomendaciones") {

        // ARRANGE
        val autorConsagrado = Autor(
            "pipo",
            "Alegre",
            "yagoo", LocalDate.of(1969, 3, 27), mutableSetOf(), Lenguaje.ja_JP, 2
        )
        val autorNoConsagrado = Autor(
            "pipo",
            "Alegre",
            "yagoo", LocalDate.of(2002, 3, 27), mutableSetOf(), Lenguaje.es_ES, 0
        )

        val usuario1 = Usuario(
            nombre = "Diego",
            apellido = "Alegre",
            username = "pipo",
            palabrasPorMinuto = 100,
            fechaNac = LocalDate.of(2000, 4, 23),
            direccionEmail = "pipo@gmail.com",
            autorFavorito = autorNoConsagrado,
            lenguaNativa = Lenguaje.es_ES
        )

        val usuario2 = Usuario(
            nombre = "Martin",
            apellido = "Rodriguez",
            username = "marto",
            palabrasPorMinuto = 100,
            fechaNac = LocalDate.of(1968, 9, 24),
            direccionEmail = "marto@gmail.com",
            autorFavorito = autorNoConsagrado,
            lenguaNativa = Lenguaje.ja_JP
        )

        val usuario3 = Usuario(
            nombre = "Valen",
            apellido = "juan",
            username = "tigger",
            palabrasPorMinuto = 200,
            fechaNac = LocalDate.of(1999, 9, 24),
            direccionEmail = "marto@gmail.com",
            autorFavorito = autorNoConsagrado,
            lenguaNativa = Lenguaje.ja_JP
        )

        val libroDesafiante = Libro(
            "HarryPotter",
            "Salamandra",
            800,
            100000,
            true,
            1,
            setOf(Lenguaje.es_ES),
            1,
            autorNoConsagrado
        )

        val libroTestDesafiante = Libro(
            "HarryPotter",
            "Salamandra",
            800,
            200000,
            true,
            1,
            setOf(Lenguaje.es_ES),
            1,
            autorNoConsagrado
        )

        val libroNoDesafiante = Libro(
            "HarryPotter",
            "Salamandra",
            300,
            50000,
            false,
            1,
            setOf(Lenguaje.ja_JP, Lenguaje.fr_FR, Lenguaje.ru_RU, Lenguaje.hi_IN),
            1,
            autorConsagrado
        )

        val libroTestNoDesafiante = Libro(
            "Tiger",
            "Salamandra",
            300,
            40000,
            false,
            1,
            setOf(Lenguaje.ja_JP, Lenguaje.fr_FR, Lenguaje.ru_RU, Lenguaje.hi_IN),
            1,
            autorConsagrado
        )
        val libroTesteado = Libro(
            "HarryPotter",
            "Salamandra",
            300,
            1000000,
            false,
            1,
            setOf(Lenguaje.ja_JP, Lenguaje.fr_FR, Lenguaje.ru_RU, Lenguaje.hi_IN),
            1,
            autorConsagrado
        )

        val recomendacion1 = Recomendacion(
            esPrivado = true,
            creador = usuario1,
            librosRecomendados = mutableSetOf(libroDesafiante, libroNoDesafiante),
            descripcion = "no se leer",
            mutableMapOf()
        )

        val recomendacion2 = Recomendacion(
            esPrivado = true,
            creador = usuario1,
            librosRecomendados = mutableSetOf(libroNoDesafiante, libroTestNoDesafiante),
            descripcion = "no se leer",
            mutableMapOf()
        )

        val recomendacion3 = Recomendacion(
            esPrivado = true,
            creador = usuario1,
            librosRecomendados = mutableSetOf(libroTestDesafiante, libroDesafiante),
            descripcion = "no se leer",
            mutableMapOf()
        )

        val recomendacion4 = Recomendacion(
            esPrivado = true,
            creador = usuario1,
            librosRecomendados = mutableSetOf(libroTestDesafiante, libroDesafiante),
            descripcion = "no se leer",
            mutableMapOf()
        )

        val recomendacion5 = Recomendacion(
            esPrivado = true,
            creador = usuario1,
            librosRecomendados = mutableSetOf(libroTesteado),
            descripcion = "no se leer",
            mutableMapOf()
        )

        describe("Dado un usuario precavido") {

            usuario1.cambiarPerfilDeRecomendacion(Precavido)
            usuario1.agregarLibroPorLeer(libroDesafiante)

            it("Si la recomendación incluye un libro pendiente de lectura, le interesa") {
                usuario1.buscarRecomendaciones(recomendacion1) shouldBe true
            }

            usuario1.leerLibro(libroDesafiante)

            it("Cuando el usuario lee un libro, este se elimina de la lista de libros pendientes de lectura (no le interesa)") {
                usuario1.librosPorLeer shouldBe mutableSetOf()
                usuario1.buscarRecomendaciones(recomendacion1) shouldBe false
            }

            usuario1.agregarAmigo(usuario2)
            usuario2.leerLibro(libroDesafiante)

            it("Si la recomendación incluye un libro que un amigo ya leyó, le interesa") {
                usuario1.buscarRecomendaciones(recomendacion1) shouldBe true
            }
        }

        describe("Dado un usuario leedor") {

            usuario1.cambiarPerfilDeRecomendacion(Leedor)

            it("Cualquier recomendación le interesa") {
                usuario1.buscarRecomendaciones(recomendacion1) shouldBe true
            }
        }

        describe("Dado un usuario poliglota") {

            usuario1.cambiarPerfilDeRecomendacion(Poliglota)

            it("Si los libros de la recomendación suman al menos 5 idiomas, le interesa") {
                usuario1.buscarRecomendaciones(recomendacion1) shouldBe true
            }

            it("Si los libros de la recomendación repiten idiomas y no llegan a 5, no le interesa") {
                usuario1.buscarRecomendaciones(recomendacion2) shouldBe false
            }

        }

        describe("Dado un usuario nativista") {

            usuario1.cambiarPerfilDeRecomendacion(Nativista)

            it("Si la recomendación contiene al menos un libro con el mismo lenguaje original que su lengua nativa, le interesa") {
                usuario1.buscarRecomendaciones(recomendacion1) shouldBe true
            }

            it("Si la recomendación no contiene ni un libro con el mismo lenguaje original que su lengua nativa, no le interesa") {
                usuario1.buscarRecomendaciones(recomendacion2) shouldBe false
            }
        }

        describe("Dado un usuario calculador") {

            usuario1.cambiarPerfilDeRecomendacion(Calculador)
            usuario1.variarTipoLector(LectorNormal)
            // porque es el tipo de lector más básico
            // libro.cantPalabras / usuario.palabrasPorMinuto
            usuario1.rangoMax(2000)
            usuario1.rangoMin(1000)

            it("Si tarda 1500 min. en leer todos los libros de la recomendación, le interesa") {
                usuario1.buscarRecomendaciones(recomendacion1) shouldBe true
            }

            it("Si tarda 900 min. en leer todos los libros de la recomendación, no le porque no llega al mínimo establecido") {
                usuario1.buscarRecomendaciones(recomendacion2) shouldBe false
            }

            it("Si tarda 3000 min. en leer todos los libros de la recomendación, no le interesa porque supera el máximo establecido") {
                usuario1.buscarRecomendaciones(recomendacion3) shouldBe false
            }
        }

        describe("Dado un usuario demandante") {

            usuario1.cambiarPerfilDeRecomendacion(Demandante)
            recomendacion3.crearValoracion(5, ".", usuario2)
            recomendacion3.crearValoracion(3, ".", usuario3)
            recomendacion4.crearValoracion(1, ".", usuario2)
            recomendacion4.crearValoracion(5, ".", usuario3)

            it("El promedio de la recomendacion3 es 4 y el de la recomendacion5 es 3") {
                recomendacion3.promedioValoraciones() shouldBe 4.0
                recomendacion4.promedioValoraciones() shouldBe 3.0
            }

            it("Dada una recomendación con valoración promedio de 4 puntos, le interesa") {
                usuario1.buscarRecomendaciones(recomendacion3) shouldBe true
            }

            it("Dada una recomendación con valoración promedio de 3 punto, no le interesa") {
                usuario1.buscarRecomendaciones(recomendacion4) shouldBe false
            }
        }

        describe("Dado un usuario experimentado") {

            usuario1.cambiarPerfilDeRecomendacion(Experimentado)

            it("si hay un libro en la recomendacion con un autor consagrado, le interesa") {
                usuario1.buscarRecomendaciones(recomendacion1) shouldBe true
            }

            it("No hay libros en las recomendaciones con autores consagrados, no le interesaD") {
                usuario1.buscarRecomendaciones(recomendacion3) shouldBe false
            }
        }

        describe("Dado un usuario cambiante") {

            usuario1.cambiarPerfilDeRecomendacion(Cambiante)

            it("Dado un usuario de 23 años de edad, se comporta como leedor y le interesa") {
                usuario1.buscarRecomendaciones(recomendacion1) shouldBe true
            }

            usuario2.cambiarPerfilDeRecomendacion(Cambiante)

            it("Dado un usuario de 56 años de edad, se comporta como calculador y no le interesa") {
                usuario2.buscarRecomendaciones(recomendacion1) shouldBe false
            }

            it("Dado un usuario de 56 años de edad, se comporta como calculador y le interesa") {
                usuario2.buscarRecomendaciones(recomendacion5) shouldBe true
            }
        }

        describe("Dado un usuario Combinador") {
            val setDePerfiles = mutableSetOf(Nativista, Poliglota)
            usuario1.cambiarAPerfilCombinador(setDePerfiles)

            it("Si un libro le interesa al Nativista, le interesará al Combinador también") {
                usuario1.buscarRecomendaciones(recomendacion1) shouldBe true
            }

            it("Si un libro le interesa al Políglota, le interesará al Combinador también") {
                usuario1.buscarRecomendaciones(recomendacion1) shouldBe true
            }

            it("Si un libro no le interesa ni al Nativista, ni al Políglota, no le interesa al Combinador tampoco") {
                usuario1.buscarRecomendaciones(recomendacion2) shouldBe false
            }
        }
    }
})