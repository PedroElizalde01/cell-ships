package model.powerUp

import edu.austral.ingsis.starships.ui.ImageRef
import model.maths.Position
import model.maths.Vector
import model.mover.Movable
import model.starship.Starship

data class PowerUp (
    val id : String,
    val pos : Position,
    val vector : Vector,
    val skin : ImageRef,
    val type : PowerUpType
        ) : Movable {
    override fun id(): String = id

    override fun x(): Double = pos.x

    override fun y(): Double = pos.y

    override fun move(time: Double): Movable = copy(pos = pos.move(vector, time))

    override fun rotation(): Double = vector.rotation

    override fun collision(collider: Movable): Movable = this

    override fun life(): Int = 0

}

enum class PowerUpType {
    LIFE,
    SINGLE_SHOOT,
    DOUBLE_SHOOT,
}