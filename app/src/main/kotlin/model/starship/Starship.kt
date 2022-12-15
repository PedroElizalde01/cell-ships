package model.starship

import COLLIDE_DAMAGE
import edu.austral.ingsis.starships.ui.ImageRef
import model.asteroid.Asteroid
import model.bullet.Bullet
import model.gun.ClassicGun
import model.gun.DoubleGun
import model.gun.Gun
import model.maths.Position
import model.maths.Vector
import model.mover.Movable
import model.powerUp.PowerUp
import model.powerUp.PowerUpType

data class Starship(
    val id: String,
    val life: Int,
    val score:Int,
    val pos: Position,
    val vector: Vector,
    val skin: ImageRef,
    val gun: Gun
) : Movable {

    fun accelerate() : Starship = copy(vector = vector.accelerate())

    fun stop() : Starship = copy(vector = vector.stop())

    fun rotateLeft(time: Double) : Starship = copy(vector = vector.rotateLeft(time))

    fun rotateRight(time: Double) : Starship = copy(vector = vector.rotateRight(time))

    fun shoot() : List<Bullet> = gun.shoot(this)

    override fun id() : String = id

    override fun life() : Int  = life

    fun score() : Int = score

    override fun x() : Double = pos.x

    override fun y() : Double = pos.y

    override fun move(time: Double) : Movable = copy(pos = pos.move(vector, time))

    override fun rotation() : Double = vector.rotation

    override fun collision(collider: Movable) : Movable {
        return when (collider){
            is Asteroid -> copy(life = life - COLLIDE_DAMAGE, vector = Vector(if(vector.speed > 0) { -1.0 } else { 1.0 }, vector.rotation))
            is Starship -> copy(life = life - COLLIDE_DAMAGE)
            is Bullet   -> if(collider.shipId != id) copy(life = life - collider.damage) else copy(life = life - 5)
            is PowerUp  -> managePowerUp(collider)
            else -> this
        }
    }

    private fun managePowerUp(powerUp: PowerUp): Movable {
        return when(powerUp.type){
            PowerUpType.LIFE -> copy(life = 100)
            PowerUpType.SINGLE_SHOOT -> copy(gun = ClassicGun())
            PowerUpType.DOUBLE_SHOOT -> copy(gun = DoubleGun())
        }
    }
}