package ar.edu.unsam.algo2.ruleta

class Jugador(var tipoJugador: TipoJugador) {
    var montoGanado = 0

    fun procesarApuesta(apuesta: Apuesta) {
        if (apuesta.ganadora()) {
            montoGanado += apuesta.montoRecompensa()
        }
    }

    fun montoPorApuesta() = tipoJugador.montoPorApuesta(this)
}

interface TipoJugador {
    fun montoPorApuesta(jugador: Jugador): Int
}

class Gastador : TipoJugador {
    override fun montoPorApuesta(jugador: Jugador) = jugador.montoGanado
}

class Conservador : TipoJugador {
    override fun montoPorApuesta(jugador: Jugador) = 10
}

abstract class Apuesta(var montoApostado: Int) {
    var numeroGanador: Int = 0

    abstract fun ganadora(): Boolean
    fun montoRecompensa() = montoApostado * efectoMultiplicador()
    abstract fun efectoMultiplicador(): Int
}

class ApuestaPleno(var numeroApostado: Int, montoApostado: Int) : Apuesta(montoApostado) {
    override fun ganadora() = numeroApostado == numeroGanador
    override fun efectoMultiplicador() = if (montoApostado > 10000) 3 else 2
}

class ApuestaPar(montoApostado: Int) : Apuesta(montoApostado) {
    override fun ganadora() = numeroGanador % 2 == 0
    override fun efectoMultiplicador() = 1
}

class ApuestaMultiple(montoApostado: Int) : Apuesta(montoApostado) {
    val apuestas = mutableSetOf<Apuesta>()

    override fun ganadora() = apuestas.all { it.ganadora() }
    override fun efectoMultiplicador() = apuestas.sumOf { it.efectoMultiplicador() }
}
