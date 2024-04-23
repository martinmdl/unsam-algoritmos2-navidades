@file:Suppress("SpellCheckingInspection")

package ar.edu.unsam.algo2.readapp

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import java.time.LocalDate

class LibroSpec : DescribeSpec({
    describe("Test de libro") {
        //Arrange
        val autor1 = Autor("Alegre",LocalDate.of(1990, 3, 27), mutableSetOf(), Lenguaje.es_ES, 2)

        it("El libro es desafiante") {
            //Arrange
            val harryPotter = Libro(
                "HarryPotter",
                "Salamandra",
                800,
                75000,
                true,
                1,
                setOf(Lenguaje.es_ES, Lenguaje.ja_JP, Lenguaje.fr_FR, Lenguaje.ru_RU, Lenguaje.hi_IN),
                1,
                autor1
            )
            //Assert
            harryPotter.esDesafiante() shouldBe true
        }

        it("El libro es BestSeller") {
            //Arrange
            val skander = Libro(
                "Skander",
                "Salamandra",
                300,
                75000,
                false,
                5,
                setOf(Lenguaje.es_ES, Lenguaje.ja_JP, Lenguaje.fr_FR, Lenguaje.ru_RU, Lenguaje.hi_IN),
                1,
                autor1
            )
            //Assert
            skander.esBestSeller() shouldBe true
        }

        it("El libro no es BestSeller") {
            //Arrange
            val jojos = Libro(
                "jojos",
                "Salamandra",
                300,
                75000,
                false,
                1,
                setOf(Lenguaje.ja_JP),
                1,
                autor1
            )
            //Assert
            jojos.esBestSeller() shouldBe false
        }
    }
})