package factory

import CENTER
import HEIGHT
import LIFE
import PLAYER1_ACCELERATE
import PLAYER1_LEFT
import PLAYER1_RIGHT
import PLAYER1_SHOOT
import PLAYER1_STOP
import PLAYER2_ACCELERATE
import PLAYER2_LEFT
import PLAYER2_RIGHT
import PLAYER2_SHOOT
import PLAYER2_STOP
import PLAYER_PAUSE
import STARSHIP
import WIDTH
import edu.austral.ingsis.starships.ui.ImageRef
import model.*
import model.asteroid.Asteroid
import model.gun.ClassicGun
import model.maths.Position
import model.maths.Vector
import model.mover.Movable
import model.starship.Starship

fun classicSolo(): Game {
    val gun = ClassicGun()
    val starship = Starship(
        "player1",
        LIFE,
        CENTER,
        Vector(0.0, 0.0),
        STARSHIP,
        gun
    )
    val movables = listOf<Movable>(starship)
    return Game(
        States.RUNNING,
        movables,
        mapOf(
            Pair(PLAYER1_ACCELERATE, PlayerAction(starship.id, ShipMovement.ACCELERATE)),
            Pair(PLAYER1_STOP, PlayerAction(starship.id, ShipMovement.STOP)),
            Pair(PLAYER1_RIGHT, PlayerAction(starship.id, ShipMovement.ROTATE_RIGHT)),
            Pair(PLAYER1_LEFT, PlayerAction(starship.id, ShipMovement.ROTATE_LEFT)),
        ),
        mapOf(
            Pair(PLAYER1_SHOOT, ReleaseAction(starship.id, Action.SHOOT)),
            Pair(PLAYER_PAUSE, ReleaseAction(starship.id, Action.TOGGLE_PAUSE))
        )
    )
}

fun classicDuo() : Game {
    val gun1 = ClassicGun()
    val gun2 = ClassicGun()
    val starship1 = Starship(
        "player1",
        LIFE,
        Position(WIDTH / 4, HEIGHT / 2),
        Vector(0.0, 0.0),
        STARSHIP,
        gun1
    )
    val starship2 = Starship(
        "player2",
        LIFE,
        Position(WIDTH * 3 / 4, HEIGHT / 2),
        Vector(0.0, 0.0),
        STARSHIP,
        gun2
    )
    val movables = listOf<Movable>(starship1, starship2)
    return Game(
        States.RUNNING,
        movables,
        mapOf(
            Pair(PLAYER1_ACCELERATE, PlayerAction(starship1.id, ShipMovement.ACCELERATE)),
            Pair(PLAYER1_STOP, PlayerAction(starship1.id, ShipMovement.STOP)),
            Pair(PLAYER1_RIGHT, PlayerAction(starship1.id, ShipMovement.ROTATE_RIGHT)),
            Pair(PLAYER1_LEFT, PlayerAction(starship1.id, ShipMovement.ROTATE_LEFT)),
            Pair(PLAYER2_ACCELERATE, PlayerAction(starship2.id, ShipMovement.ACCELERATE)),
            Pair(PLAYER2_STOP, PlayerAction(starship2.id, ShipMovement.STOP)),
            Pair(PLAYER2_RIGHT, PlayerAction(starship2.id, ShipMovement.ROTATE_RIGHT)),
            Pair(PLAYER2_LEFT, PlayerAction(starship2.id, ShipMovement.ROTATE_LEFT)),
        ),
        mapOf(
            Pair(PLAYER1_SHOOT, ReleaseAction(starship1.id, Action.SHOOT)),
            Pair(PLAYER2_SHOOT, ReleaseAction(starship2.id, Action.SHOOT)),
            Pair(PLAYER_PAUSE, ReleaseAction(starship1.id, Action.TOGGLE_PAUSE))
        )
    )
}

fun createAsteroid(movables: List<Movable>, id: String) : List<Movable> {
    val life = (40..200).random()
    val asteroid = Asteroid(
        id,
        life,
        life.toDouble(),
        spawnInBorder(),
        Vector((1..3).random().toDouble(), (0..360).random().toDouble()),
        ImageRef(pickAsteroidSkin(),life.toDouble(),life.toDouble())
    )
    return movables.plus(asteroid)
}

private fun pickAsteroidSkin() : String{
    return when((0..2).random()){
        0 -> "bacteria1"
        else -> "bacteria2"
    }
}

fun spawnInBorder() : Position {
    return when((0..3).random()){
        0-> Position(0.0,(0..HEIGHT.toInt()).random().toDouble())
        1-> Position((0..WIDTH.toInt()).random().toDouble(),0.0)
        2-> Position(WIDTH,(0..HEIGHT.toInt()).random().toDouble())
        3-> Position((0..WIDTH.toInt()).random().toDouble(), HEIGHT)
        else -> {
            Position(0.0,0.0)
        }
    }
}