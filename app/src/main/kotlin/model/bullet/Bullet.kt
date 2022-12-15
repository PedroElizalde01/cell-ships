package model.bullet

import edu.austral.ingsis.starships.ui.ImageRef
import model.asteroid.Asteroid
import model.maths.Position
import model.maths.Vector
import model.mover.Movable

data class Bullet(
    val id: String,
    val damage: Int,
    val pos: Position,
    val vector: Vector,
    val skin: ImageRef,
    val shipId: String) : Movable {

    override fun id(): String = id

    override fun x(): Double = pos.x

    override fun y(): Double = pos.y

    override fun move(time: Double): Movable = copy(pos = pos.move(vector, time))

    override fun rotation(): Double = vector.rotation

    override fun collision(collider: Movable): Movable {
        return when (collider) {
            is Asteroid -> copy()
            else -> this
        }
    }

    override fun life(): Int = 0

}