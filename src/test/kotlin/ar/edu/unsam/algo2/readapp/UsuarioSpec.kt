@file:Suppress("SpellCheckingInspection")

package ar.edu.unsam.algo2.readapp
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import java.time.LocalDate

class UsuarioSpec : DescribeSpec({
    describe("Tests de usuario") {

        // Arrange
        val usuario1 = Usuario(
//val nombre: String,
//val apellido: String,
//val username: String,
//val palabrasPorMinuto: Int,
//private val fechaNac: LocalDate,
//val direccionDeMail: String,
//val amigos: List<Usuario>,
//val librosLeidos: MutableMap<Libro, Int> = mutableMapOf(), // revisar
//val recomendaciones: MutableList<Recomendacion>,
//val autorFavorito: String,
//// que tipo de lector es este usuario?
//val tipoLector: TipoLector
            "pipo",
            "alegre",
            "pipojr10",
            250,
            LocalDate.of(2002, 6, 24),
            "pipojr10@gmail.com",
            listOf(),
            mutableMapOf(),
            mutableListOf(),
            "JKRowling"
//            LectorNormal(),
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
            10001,
            "JKRowling"
        )

        // Arrange
        val libroNoDesafiante = Libro(
            "OnePiece",
            "ivrea",
            150,
            10000,
            false,
            1,
          setOf(Lenguaje.es_ES, Lenguaje.ja_JP),
            10001,
            "EichiroOda"
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
