import java.time.DayOfWeek
import java.time.LocalDate

// PUNTO 3; Observer Pattern: ConcreteSubject
class AdminRegaleria {

    private val stock = mutableListOf<Regalo>()
    private val observersEntrega = mutableSetOf<ObserverEntrega>()

    fun encontrarRegaloAdecuado(persona: Persona): Regalo {

        val regalo = stock.find { regalo ->
            persona.preferencias.any { preferencia ->
                preferencia.esAdecuado(regalo)
            }
        } ?: Voucher()

        return regalo
    }

    fun entregarRegalo(persona: Persona, regalo: Regalo) {
        persona.recibirRegalo(regalo)
        observersEntrega.forEach { it.regaloEntregado(persona, regalo) }
    }
}

// PUNTO 1; Strategy Pattern: Context
class Persona(val mail: String, val dni: String, val direccion: String) {

    private val regalos = mutableListOf<Regalo>()
    var preferencias = mutableSetOf<Preferencia>()

    fun agregarPreferencia(prefe: Preferencia) {
        preferencias.add(prefe)
    }

    fun eliminarPreferencia(prefe: Preferencia) {
        preferencias.remove(prefe)
    }

    fun recibirRegalo(regalo: Regalo) {
        regalos.add(regalo)
    }

    fun resetearPreferencias() {
        preferencias = mutableSetOf()
    }
}

// PUNTO 1; Composite Pattern: Component; Strategy Pattern: Strategy
interface Preferencia {
    fun esAdecuado(regalo: Regalo): Boolean
}

// PUNTO 1.1; Composite Pattern: Leaf; Strategy Pattern: ConcreteStrategyA
class Conformista : Preferencia {
    override fun esAdecuado(regalo: Regalo): Boolean = true
}

// PUNTO 1.2; Composite Pattern: Leaf; Strategy Pattern: ConcreteStrategyB
class Interesada(private val precioMin: Double) : Preferencia {
    override fun esAdecuado(regalo: Regalo): Boolean = regalo.precio > precioMin
}

// PUNTO 1.3; Composite Pattern: Leaf; Strategy Pattern: ConcreteStrategyC
class Exigente : Preferencia {
    override fun esAdecuado(regalo: Regalo): Boolean = regalo.esValioso()
}

// PUNTO 1.4; Composite Pattern: Leaf; Strategy Pattern: ConcreteStrategyD
class Marquera(private val marcasAceptadas: MutableSet<String>) : Preferencia {
    override fun esAdecuado(regalo: Regalo): Boolean = regalo.marca in marcasAceptadas
}

// PUNTO 1.5; Composite Pattern: Composite; Strategy Pattern: ConcreteStrategyE
class Combineta(private val preferencias: MutableSet<Preferencia>) : Preferencia {
    override fun esAdecuado(regalo: Regalo): Boolean = preferencias.any { it.esAdecuado(regalo) }
}

// PUNTO 2
abstract class Regalo(val marca: String, val precio: Double) {

    open lateinit var codigo: String

    companion object { private const val PRECIO_MIN: Double = 5000.0 }

    // TemplateMethod
    fun esValioso(): Boolean = precio > PRECIO_MIN || condicionEspecifica()

    // PrimitiveOperation1
    abstract fun condicionEspecifica(): Boolean
}

// PUNTO 2.1
class Ropa(marca: String, precio: Double) : Regalo(marca, precio) {
    override var codigo = "1"
    companion object { private val MARCAS_VALIOSAS = mutableSetOf("Jordache", "Lee", "Charro", "Motor Oil") }
    override fun condicionEspecifica(): Boolean = marca in MARCAS_VALIOSAS
}

// PUNTO 2.2
class Juguete(private val fechaLanzamiento: LocalDate, marca: String, precio: Double) : Regalo(marca, precio) {
    override var codigo = "2"
    companion object { private val FECHA_ANTIGUA = LocalDate.of(2000,1, 1) }
    override fun condicionEspecifica(): Boolean = fechaLanzamiento < FECHA_ANTIGUA
}

// PUNTO 2.3
class Perfume(private val esExtranjero: Boolean, marca: String, precio: Double) : Regalo(marca, precio) {
    override var codigo = "3"
    override fun condicionEspecifica(): Boolean = esExtranjero
}

// PUNTO 2.4
class Experiencia(private val dia: DayOfWeek, marca: String, precio: Double) : Regalo(marca, precio) {
    override var codigo = "4"
    override fun condicionEspecifica(): Boolean = dia == DayOfWeek.FRIDAY
}

// PUNTO 3.2
class Voucher(marca: String = "Papapp", precio: Double = 2000.0) : Regalo(marca, precio) {
    override var codigo = "5"
    override fun condicionEspecifica(): Boolean = false
}

// PUNTO 3.3 (Observer)
interface ObserverEntrega {
    fun regaloEntregado(persona: Persona, regalo: Regalo)
}

// PUNTO 3.3.1 (Dependency)
interface MailSender { fun sendMail(mail: Mail) }
data class Mail(private val from: String, private val to: String, private val subject: String, private val message: String)

// PUNTO 3.3.1 (Concrete Observer)
class MailDestinatario(private val mailSender: MailSender) : ObserverEntrega {

    override fun regaloEntregado(persona: Persona, regalo: Regalo) {
        mailSender.sendMail(Mail(
            from = "papapp@polonorte.com",
            to = persona.mail,
            subject = "Feliz Navidad!",
            message = "Acaba de recibir un regalo."
        ))
    }
}

// PUNTO 3.3.2 (Dependency)
interface ElRenoLoco { fun informar(informe: Informe) }
data class Informe(private val direccion: String, private val destinatario: String, private val dni: String, private val codigo: String)

// PUNTO 3.3.2 (Concrete Observer)
class Flete(private val flete: ElRenoLoco) : ObserverEntrega {

    override fun regaloEntregado(persona: Persona, regalo: Regalo) {
        flete.informar(Informe(
            direccion = persona.direccion,
            destinatario = persona.mail,
            dni = persona.dni,
            codigo = regalo.codigo
        ))
    }
}

// PUNTO 3.3.3 (Concrete Observer)
class EsInteresada() : ObserverEntrega {

    companion object { private const val PRECIO_LIMITE: Double = 10000.0 }

    override fun regaloEntregado(persona: Persona, regalo: Regalo) {
        if(regalo.precio > PRECIO_LIMITE) {
           // persona.resetearPreferencias()
            persona.agregarPreferencia(Interesada(5000.0))
        }
    }
}



