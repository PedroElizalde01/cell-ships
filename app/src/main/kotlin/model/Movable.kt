package model

interface Movable {
    fun id(): String
    fun x(): Double
    fun y(): Double
    fun move(time: Double): Movable
    fun rotation(): Double
    fun collision(collider: Movable): Movable
    fun life(): Int
}