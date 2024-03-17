package ar.edu.unsam.algo2.readapp
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe

class LibroSpec : DescribeSpec({
    describe("Test de libro"){
        val harryPotter = Libro(
            "HarryPotter",
            "Salamandra",
            800,
            75000,
            true,
            5,
            listOf(ESP, JAP),
            10001
        )
        it("El libro es desafiante"){
            harryPotter.esDesafiante() shouldBe true
        }
        it("El libro es BestSeller"){
            harryPotter.esBestSeller() shouldBe true
        }
    }
})
