package ar.edu.unsam.algo2.readapp
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
class LibroSpec: DescribeSpec({
    describe("Test de libro"){
        val harrypotter = Libro(
            "HarryPotter",
            "Salamandra",
            800,
            75000,
            true,
            1,
            listOf(ESP, JAP),
            10000)
        it("El libro es desafiante"){
            harrypotter.esDesafiante() shouldBe true
        }
    }
})