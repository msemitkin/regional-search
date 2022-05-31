import regionalsearch.Circle
import regionalsearch.Point
import regionalsearch.Region
import kotlin.random.Random

fun main(args: Array<String>) {
    args.ifEmpty { throw java.lang.IllegalArgumentException("Choose either predefined (p) or automatically generated values (a)") }

    val points: Set<Point> = if (args[0] == "p") getPredefinedPoints()
    else Random(System.currentTimeMillis()).generatePoints(10000)

    val circle = if (args[0] == "p") getPredefinedCircle()
    else Random(System.currentTimeMillis()).generateCircle()

    val region = Region(points.sortedBy { it.x })
    val pointsInCircle = region.regionalSearch(circle)
    pointsInCircle.sortedWith(compareBy(Point::x, Point::y)).forEach(::println)
}

fun Random.generatePoints(count: Long): Set<Point> {
    val points: MutableSet<Point> = HashSet()
    (0..count).forEach { points.add(Point(generateInBounds(), generateInBounds())) }
    return points
}

fun Random.generateCircle(): Circle {
    return Circle(Point(generateInBounds(), generateInBounds()), generateInBounds())
}


fun Random.generateInBounds(): Double {
    val lowerBound = -1000.0
    val upperBound = 1000.0
    return nextDouble() * (upperBound - lowerBound) + lowerBound
}

private fun getPredefinedPoints() = setOf(
    Point(2, 1),
    Point(3, 8),
    Point(4, 6),
    Point(5, 2),
    Point(5, 8),
    Point(7, 3),
    Point(7, 6),
    Point(7, 9),
    Point(8, 2),
    Point(10, 6),
    Point(10, 9)
)

private fun getPredefinedCircle() = Circle(Point(7, 6), 3)

