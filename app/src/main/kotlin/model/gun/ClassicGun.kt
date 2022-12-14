package model.gun

import BULLET_DAMAGE
import BULLET_SPEED
import LASER_BEAM
import model.bullet.Bullet
import model.maths.Position
import model.maths.Vector
import model.starship.Starship
import kotlin.math.sin
import kotlin.math.cos

class ClassicGun : Gun {
    override fun shoot(starship: Starship): List<Bullet> {
        val xPos = starship.pos.x + (40 * sin(Math.toRadians(starship.vector.rotation)))
        val yPos = starship.pos.y + (40 * -cos(Math.toRadians(starship.vector.rotation)))
        return listOf(Bullet(
            "b"+ (0..1000).random(),
            BULLET_DAMAGE,
            Position(xPos, yPos),
            Vector(BULLET_SPEED, starship.vector.rotation),
            LASER_BEAM,
            starship.id()
        ))
    }

}