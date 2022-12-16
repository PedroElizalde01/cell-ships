import edu.austral.ingsis.starships.ui.*
import factory.classicDuo
import factory.classicSolo
import javafx.application.Application
import javafx.application.Application.launch
import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.Cursor
import javafx.scene.Scene
import javafx.scene.control.Label
import javafx.scene.input.KeyCode
import javafx.scene.layout.HBox
import javafx.scene.layout.StackPane
import javafx.scene.layout.VBox
import javafx.scene.paint.Color
import javafx.stage.Stage
import model.Game
import model.States
import model.starship.Starship
import modelToGUI.ModelToGUI
import kotlin.system.exitProcess

private val imageResolver = CachedImageResolver(DefaultImageResolver())
private val facade = ElementsViewFacade(imageResolver)
private val keyTracker = KeyTracker()
private var adapter = ModelToGUI(Game(States.PAUSED, listOf(), mapOf(), mapOf()), SPAWN_PROBS)
private var pressedKeys = listOf<KeyCode>()

fun main() {
    launch(DoodleShip::class.java)
}

class DoodleShip() : Application() {

    companion object {
        var pauseTimeList: List<Long> = listOf()
        var pauseTime: Long = 0L
        var startTime = 0L
    }

    override fun start(primaryStage: Stage) {
        adapter = adapter.addElements(facade.elements)
        val displayedMinutes: Long = 0

        val life = StackPane()
        val life1 = Label(LIFE.toString())
        val life2 = Label(LIFE.toString())
        val time = Label("00:00")

        val div1 = HBox(300.0)
        val div2 = HBox(50.0)

        div1.alignment = Pos.TOP_CENTER
        div2.alignment = Pos.BOTTOM_CENTER

        div1.children.addAll(life1, life2)
        div2.children.addAll(time)

        div1.padding = Insets(10.0, 10.0, 10.0, 10.0)
        div2.padding = Insets(10.0, 10.0, 10.0, 10.0)
        life.children.addAll(div1, div2)

        val pane = StackPane()
        val layout = VBox(10.0)

        val root = facade.view
        pane.children.addAll(root, life)
        root.id = "game"

        val scene = Scene(layout)
        keyTracker.scene = scene
        scene.stylesheets.add(this::class.java.classLoader.getResource("menu.css")?.toString())
        scene.stylesheets.add("https://fonts.googleapis.com/css2?family=Rock+Salt&display=swap")

        primaryStage.scene = scene
        primaryStage.height = HEIGHT
        primaryStage.width = WIDTH

        //MENU
        initMenu(layout, scene, pane)

        startGame(primaryStage)

        facade.collisionsListenable.addEventListener(MyCollisionListener())

        facade.timeListenable.addEventListener(
            MyTimeListener(
                life1,
                life2,
                time,
                div1,
                div2,
                displayedMinutes
            )
        )

        keyTracker.keyPressedListenable.addEventListener(MyKeyPressedListener())

        keyTracker.keyReleasedListenable.addEventListener(MyKeyReleasedListener())
    }

    private fun initMenu(layout: VBox, scene: Scene, pane: StackPane) {
        layout.alignment = Pos.CENTER
        layout.id = "pane"

        val name = Label("Doodle-Ships")
        name.textFill = Color.BLACK
        name.style = "-fx-font-family: 'Rock Salt', monospace; -fx-font-size: 100"

        val buttons = HBox(100.0)
        buttons.alignment = Pos.CENTER

        val onePlayer = Label("Solo")
        addCssToLabelButton(onePlayer, scene, pane, ModelToGUI(classicSolo(), SPAWN_PROBS))

        val twoPlayer = Label("Co-op")
        addCssToLabelButton(twoPlayer, scene, pane, ModelToGUI(classicDuo(), SPAWN_PROBS))

        buttons.children.addAll(onePlayer, twoPlayer)
        layout.children.addAll(name, buttons)
    }

    private fun startGame(primaryState: Stage) {
        facade.start()
        keyTracker.start()
        facade.showCollider.set(false)
        primaryState.show()
    }

    private fun addCssToLabelButton(label: Label, scene: Scene, pane: StackPane, newAdapter: ModelToGUI) {
        label.style = "-fx-font-family: 'Rock Salt', monospace; -fx-font-size: 50"
        label.textFill = Color.BLACK
        label.style = "-fx-font-family: 'Rock Salt', monospace; -fx-font-size: 50"
        label.setOnMouseEntered {
            label.textFill = Color.RED
            label.cursor = Cursor.HAND
        }
        label.setOnMouseExited {
            label.textFill = Color.BLACK
        }
        label.setOnMouseClicked {
            startTime = System.currentTimeMillis()
            scene.root = pane
            adapter = newAdapter
        }
    }

    class MyTimeListener(
        private var life1: Label,
        private var life2: Label,
        private var time: Label,
        private var div1: HBox,
        private var div2: HBox,
        private var displayedMinutes: Long
    ) : EventListener<TimePassed> {

        override fun handle(event: TimePassed) {
            pressedKeys.forEach { adapter = adapter.pressedKey(it, event.secondsSinceLastTime) }
            if (adapter.game.state == States.RUNNING) {
                adapter = adapter
                    .keyFramePassed(event.secondsSinceLastTime)
                    .addElements(facade.elements)
                    .adaptElements(facade.elements)

                life1 = adapter.updateLives("player1")
                life2 = adapter.updateLives("player2")

                time = Label(getCurrentTime())
                life1.style = "-fx-font-family: 'Rock Salt', monospace; -fx-font-size: 40"
                life2.style = "-fx-font-family: 'Rock Salt', monospace; -fx-font-size: 40"
                life1.alignment = Pos.TOP_RIGHT
                life2.alignment = Pos.TOP_LEFT
                time.style = "-fx-font-family: 'Rock Salt', monospace; -fx-font-size: 40"
                life1.textFill = Color.GREEN
                life2.textFill = Color.BLUE
                time.textFill = Color.BLACK

                div1.children[0] = life2
                div1.children[1] = life1
                div2.children[0] = time

                if (life1.text == "" && life2.text == "") {
                    manageGameOver()
                }
            }
        }

        private fun getCurrentTime(): String {
            val total = pauseTimeList.sum()
            val timePassed = System.currentTimeMillis() - startTime - total
            var secondsPassed = (timePassed / 1000)
            if (secondsPassed >= 60L) {
                startTime = System.currentTimeMillis()
                secondsPassed = 0
                displayedMinutes += 1
            }

            return String.format("%02d:%02d", displayedMinutes, secondsPassed)
        }

        private fun manageGameOver() {
            val ships = adapter.game.movables.filter { mov -> mov is Starship }
            if (ships.isEmpty()) {
                println("Your time was -> " + time.text)
                facade.stop()
                keyTracker.stop()
                exitProcess(0)
            }
        }
    }

    class MyCollisionListener() : EventListener<Collision> {
        override fun handle(event: Collision) {
            adapter = adapter.collision(event.element1Id, event.element2Id, facade.elements).addElements(facade.elements)
        }
    }

    class MyKeyPressedListener() : EventListener<KeyPressed> {
        override fun handle(event: KeyPressed) {
            if (!pressedKeys.contains(event.key) && !adapter.game.releaseKeyMap.containsKey(event.key)) {
                pressedKeys = pressedKeys.plus(event.key)
            }
        }
    }

    class MyKeyReleasedListener() : EventListener<KeyReleased> {
        override fun handle(event: KeyReleased) {
            if (pressedKeys.contains(event.key)) {
                pressedKeys = pressedKeys.filter { it != event.key }
            }
            adapter = adapter.releasedKey(event).addElements(facade.elements)
            if (event.key === KeyCode.P) {
                if (adapter.game.state === States.PAUSED) {
                    pauseTime = System.currentTimeMillis()
                }
                if (adapter.game.state === States.RUNNING) {
                    pauseTimeList += (System.currentTimeMillis() - pauseTime)
                }
            }
        }
    }
}


