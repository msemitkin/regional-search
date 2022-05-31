package regionalsearch

import kotlin.math.pow
import kotlin.math.sqrt

data class Point(val x: Double, val y: Double) {
    constructor(x: Int, y: Int) : this(x.toDouble(), y.toDouble())

    fun distanceTo(point: Point): Double = sqrt((this.x - point.x).pow(2) + (this.y - point.y).pow(2))
}