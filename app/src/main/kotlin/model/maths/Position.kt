package model.maths

import HEIGHT
import WIDTH
import kotlin.math.cos
import kotlin.math.sin

data class Position(val x: Double,val y: Double) {

    fun move(vector: Vector, time: Double): Position {
        return when {
            y < -80     -> { copy(y = HEIGHT) }
            y > HEIGHT  -> { copy( y = 0.0 ) }
            x < -80     -> { copy( x = WIDTH ) }
            x > WIDTH   -> { copy( x = 0.0 ) }
            else -> copy(
                x = x + 80 * time * (vector.speed * sin(Math.toRadians(vector.rotation))),
                y = y + 80 * time * (vector.speed * -cos(Math.toRadians(vector.rotation)))
            )
        }
    }
}