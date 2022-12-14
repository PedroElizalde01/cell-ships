package model.maths

class Vector (val speed:Double, val rotation:Double){
    fun accelerate(): Vector {
        return if (speed < 6) {
            Vector(speed + 0.5, rotation)
        } else {
            this
        }
    }

    fun stop(): Vector{
        return if (speed > -4) {
            Vector(speed - 0.5, rotation)
        } else {
            this
        }
    }

    fun rotateLeft(time:Double): Vector = Vector(speed, rotation - 270*time)

    fun rotateRight(time: Double): Vector = Vector(speed, rotation + 270*time)
}