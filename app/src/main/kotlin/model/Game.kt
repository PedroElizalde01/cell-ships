package model

import javafx.scene.input.KeyCode
import model.mover.Movable

data class Game (
    val state: States,
    val movables :List<Movable>,
    val movementKeyMap : Map<KeyCode,PlayerAction>,
    val releaseKeyMap : Map<KeyCode,ReleaseAction>)

enum class States{
    RUNNING,
    PAUSED,
}

data class PlayerAction(val id: String, val movement: ShipMovement)

enum class ShipMovement {
    ACCELERATE,
    ROTATE_LEFT,
    ROTATE_RIGHT,
    STOP,
}

data class ReleaseAction(val id: String, val action: Action)

enum class Action{
    SHOOT,
    TOGGLE_PAUSE
}