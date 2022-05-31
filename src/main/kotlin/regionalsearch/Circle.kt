package regionalsearch

class Circle(val center: Point, val radius: Double) {
    constructor(center: Point, radius: Int) : this(center, radius.toDouble())
}

fun Circle.contains(point: Point): Boolean = center.distanceTo(point) <= radius
fun Circle.getLeftBound() = center.x - radius
fun Circle.getRightBound() = center.x + radius