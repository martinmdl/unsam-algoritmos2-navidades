@file:Suppress("SpellCheckingInspection")

package ar.edu.unsam.algo2.readapp
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe

class LibroSpec : DescribeSpec({
    describe("Test de libro"){

        it("El libro es desafiante"){
            //Arrange
            val harryPotter = Libro(
                "HarryPotter",
                "Salamandra",
                800,
                75000,
                true,
                5,
                setOf(Lenguaje.es_ES, Lenguaje.ja_JP),
                10001
            )
            //Assert
            harryPotter.esDesafiante() shouldBe true
        }

        it("El libro es BestSeller"){
            //Arrange
            val skander = Libro(
                "Skander",
                "Salamandra",
                300,
                75000,
                false,
                5,
                setOf(Lenguaje.ru_RU, Lenguaje.fr_FR),
                10001
            )
            //Assert
            skander.esBestSeller() shouldBe true
        }
    }
})