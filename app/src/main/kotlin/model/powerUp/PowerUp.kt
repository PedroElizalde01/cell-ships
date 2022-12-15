package model.powerUp

import model.maths.Position
import model.maths.Vector
import model.mover.Movable
import model.starship.Starship

data class PowerUp (
    val id : String,
    val pos : Position,
    val vector : Vector,
    val type : PowerUpType,
        ) : Movable {
    override fun id(): String = id

    override fun x(): Double = pos.x

    override fun y(): Double = pos.y

    override fun move(time: Double): Movable = copy(pos = pos.move(vector, time))

    override fun rotation(): Double = vector.rotation

    override fun collision(collider: Movable): Movable {
        return when(collider) {
            is Starship -> this
            else -> this
        }
    }

    override fun life(): Int = 0

}

enum class PowerUpType {
    LIFE,
    DOUBLE_SHOOT,
    TRIPLE_SHOOT,
}