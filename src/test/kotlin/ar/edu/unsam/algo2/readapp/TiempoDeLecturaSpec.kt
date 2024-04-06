@file:Suppress("SpellCheckingInspection")

package ar.edu.unsam.algo2.readapp

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import java.time.LocalDate
class TiempoDeLecturaSpec : DescribeSpec({
    describe("Test de tiempos de lectura") {
        //Arrange
        val autorConsagrado = Autor(LocalDate.of(1969, 3, 27), mutableSetOf(),Lenguaje.es_ES,2)
        val autorNoConsagrado = Autor(LocalDate.of(2002, 3, 27), mutableSetOf(),Lenguaje.es_ES,0)
        val usuario = Usuario(
            nombre = "Diego",
            apellido = "Alegre",
            username = "pipo",
            palabrasPorMinuto = 100,
            fechaNac = LocalDate.of(2000, 4, 23),
            direccionEmail = "pipo@gmail.com",
            autorFavorito = autorNoConsagrado,
            lenguaNativa = Lenguaje.es_ES
        )

        val libroDesafiante = Libro(
            "HarryPotter",
            "Salamandra",
            800,
            75000,
            true,
            1,
            setOf(Lenguaje.es_ES, Lenguaje.ja_JP, Lenguaje.fr_FR, Lenguaje.ru_RU, Lenguaje.hi_IN),
            1,
            autorConsagrado
        )

        val libroNoDesafiante = Libro(
            "HarryPotter",
            "Salamandra",
            300,
            500,
            false,
            1,
            setOf(Lenguaje.es_ES, Lenguaje.ja_JP, Lenguaje.fr_FR, Lenguaje.ru_RU, Lenguaje.hi_IN),
            1,
            autorConsagrado
        )

        val libroBestSeller = Libro(
            "Skander",
            "Salamandra",
            300,
            75000,
            false,
            5,
            setOf(Lenguaje.es_ES, Lenguaje.ja_JP, Lenguaje.fr_FR, Lenguaje.ru_RU, Lenguaje.hi_IN),
            1,
            autorNoConsagrado
        )

        val libroNotBetSeller = Libro(
            "jojos",
            "Salamandra",
            300,
            75000,
            false,
            1,
            setOf(Lenguaje.ja_JP),
            1,
            autorNoConsagrado
        )

        it("Lector promedio lee mas lento si es desafiante") {
            //Assert
            usuario.tiempoDeLectura(libroDesafiante, usuario) shouldBe 1500

        }

        it("") {

        }

        it("") {

        }
    }




})