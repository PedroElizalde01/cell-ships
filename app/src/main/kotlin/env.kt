import edu.austral.ingsis.starships.ui.ImageRef
import javafx.scene.input.KeyCode
import model.maths.Position

val HEIGHT = 700.0
val WIDTH = 1100.0

val COLLIDE_DAMAGE = 10
val BULLET_SPEED = 12.0
val BULLET_DAMAGE = 20
val LIFE = 100
val SPAWN_PROBS = 100000

val CENTER = Position(WIDTH / 2, HEIGHT / 2)

val LASER = ImageRef("laser", 30.0, 10.0)
val STARSHIP1 = ImageRef("starship1", 65.0, 65.0)
val STARSHIP2 = ImageRef("starship2", 65.0, 65.0)
val STARSHIP3 = ImageRef("starship3", 65.0, 65.0)
val STARSHIP4 = ImageRef("starship4", 65.0, 65.0)
val STARSHIP5 = ImageRef("starship5", 65.0, 130.0)
val STARSHIP6 = ImageRef("starship6", 65.0, 65.0)

val PLAYER_PAUSE = KeyCode.P

val PLAYER1_ACCELERATE = KeyCode.UP
val PLAYER1_STOP = KeyCode.DOWN
val PLAYER1_RIGHT = KeyCode.RIGHT
val PLAYER1_LEFT = KeyCode.LEFT
val PLAYER1_SHOOT = KeyCode.M

val PLAYER2_ACCELERATE = KeyCode.W
val PLAYER2_STOP = KeyCode.S
val PLAYER2_RIGHT = KeyCode.D
val PLAYER2_LEFT = KeyCode.A
val PLAYER2_SHOOT = KeyCode.Q