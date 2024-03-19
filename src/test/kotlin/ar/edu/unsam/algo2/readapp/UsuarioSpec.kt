@file:Suppress("SpellCheckingInspection")

package ar.edu.unsam.algo2.readapp
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import java.time.LocalDate

class UsuarioSpec : DescribeSpec({
    describe("Tests Calculo Edad") {
        it("si nacio en 01-10-2000 debe tener 23 anios") {
            // Arrange
            val usuario1 = Usuario(
                "pipo",
                "alegre",
                "pipojr10",
                2,
                (LocalDate.of(2000, 10, 1))
            )
            // Assert
            usuario1.edad() shouldBe 23
        }
    }
})
