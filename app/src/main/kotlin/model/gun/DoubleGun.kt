package model.gun

import BULLET_DAMAGE
import BULLET_SPEED
import LASER
import model.bullet.Bullet
import model.maths.Position
import model.maths.Vector
import model.starship.Starship
import kotlin.math.cos
import kotlin.math.sin

class DoubleGun : Gun {

    override fun shoot(starship: Starship): List<Bullet> {
        val xPos1 = starship.pos.x + (70 * sin(Math.toRadians(starship.vector.rotation - 10)))
        val yPos1 = starship.pos.y + (70 * -cos(Math.toRadians(starship.vector.rotation - 10)))
        val xPos2 = starship.pos.x + (70 * sin(Math.toRadians(starship.vector.rotation + 10)))
        val yPos2 = starship.pos.y + (70 * -cos(Math.toRadians(starship.vector.rotation + 10)))
        return listOf(
            Bullet(
                "b"+ (0..1000).random(),
                BULLET_DAMAGE,
                Position(xPos1, yPos1),
                Vector(BULLET_SPEED, starship.vector.rotation),
                LASER,
                starship.id()
            ),
            Bullet(
                "b"+ (0..1000).random(),
                BULLET_DAMAGE,
                Position(xPos2, yPos2),
                Vector(BULLET_SPEED, starship.vector.rotation),
                LASER,
                starship.id()
            )
        )
    }
}