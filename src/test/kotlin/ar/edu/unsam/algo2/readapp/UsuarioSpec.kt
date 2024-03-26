@file:Suppress("SpellCheckingInspection")

package ar.edu.unsam.algo2.readapp
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import java.time.LocalDate

class UsuarioSpec : DescribeSpec({
    describe("Tests de usuario") {

        // Arrange
        val usuario1 = Usuario(
            "pipo",
            "alegre",
            "pipojr10",
            250,
            (LocalDate.of(2000, 10, 1))
        )

        // Arrange
        val libroDesafiante = Libro(
            "HarryPotter",
            "Salamandra",
            800,
            75000,
            true,
            5,
            setOf(Lenguaje.es_ES, Lenguaje.ja_JP),
            10001
        )

        // Arrange
        val libroNoDesafiante = Libro(
            "OnePiece",
            "ivrea",
            150,
            10000,
            false,
            1,
            listOf(Lenguaje.ESP, Lenguaje.JAP),
            10001
        )

        it("cuánto tarda en leer un libro desafiante") {
            //Assert
            usuario1.tiempoDeLectura(libroDesafiante)  shouldBe 600
        }

        it("cuánto tarda en leer un libro no desafiante") {
            //Assert
            usuario1.tiempoDeLectura(libroNoDesafiante)  shouldBe 40
        }
    }
})