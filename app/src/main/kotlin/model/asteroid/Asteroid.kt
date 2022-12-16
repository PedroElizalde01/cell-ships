package model.asteroid

import edu.austral.ingsis.starships.ui.ImageRef
import model.bullet.Bullet
import model.maths.Position
import model.maths.Vector
import model.Movable
import model.starship.Starship

data class Asteroid (
    val id:String,
    val life:Int,
    val size:Double,
    val pos: Position,
    val vector: Vector,
    val skin: ImageRef) : Movable {

    override fun id(): String =  id

    override fun life(): Int =  life

    override fun x(): Double = pos.x

    override fun y(): Double = pos.y

    override fun move(time: Double): Asteroid = copy (pos = pos.move(vector,time))

    override fun rotation(): Double = vector.rotation

    override fun collision(collider: Movable): Asteroid {
        val newSize = (if (size > 100.0){ size - 10 } else { size })
        return when(collider){
            is Starship -> this;
            is Bullet   -> copy(life = life - collider.damage, size = newSize, skin = skin);
            is Asteroid -> copy(vector = Vector(vector.speed * -1, vector.rotation))
            else -> this
        }
    }
}