package model.gun

import model.bullet.Bullet
import model.starship.Starship

interface Gun {
    fun shoot(starship: Starship): List<Bullet>
}