import jetbrains.datalore.base.math.toRadians
import jetbrains.letsPlot.coordFixed
import jetbrains.letsPlot.export.ggsave
import jetbrains.letsPlot.geom.geomPath
import jetbrains.letsPlot.geom.geomPoint
import jetbrains.letsPlot.ggplot
import jetbrains.letsPlot.intern.Plot
import regionalsearch.Circle
import regionalsearch.Point
import regionalsearch.Region
import kotlin.math.cos
import kotlin.math.sin
import kotlin.random.Random

fun main(args: Array<String>) {
    args.ifEmpty { throw java.lang.IllegalArgumentException("Choose either predefined (p) or automatically generated values (a)") }

    val points: Set<Point> = if (args[0] == "p") getPredefinedPoints()
    else Random(System.currentTimeMillis()).generatePoints(10000)

    val circle = if (args[0] == "p") getPredefinedCircle()
    else Random(System.currentTimeMillis()).generateCircle()

    val region = Region(points.sortedBy { it.x })
    val pointsInCircle = region.regionalSearch(circle)

    val plot = drawPlot(points, circle, pointsInCircle)
    ggsave(plot, "plot.png")
}

fun Random.generatePoints(count: Long): Set<Point> {
    val points: MutableSet<Point> = HashSet()
    repeat((0..count).count()) { points.add(Point(generate(), generate())) }
    return points
}

fun Random.generateCircle(): Circle {
    return Circle(Point(generate(), generate()), generate(200.0, 500.0))
}


fun Random.generate(lowerBound: Double = -1000.0, upperBound: Double = 1000.0): Double {
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

private fun getPointsOnTheCircle(circle: Circle) = Array(360) { it.toDouble() }
    .map {
        Point(
            circle.center.x + (circle.radius * sin(toRadians(it)).toFloat()),
            circle.center.y + (circle.radius * cos(toRadians(it)).toFloat())
        )
    }

private fun drawPlot(points: Collection<Point>, circle: Circle, pointsInCircle: Collection<Point>): Plot {
    val allData = mapOf("x" to points.map { it.x }, "y" to points.map { it.y })
    val pointsInCircleData = mapOf("x" to pointsInCircle.map { it.x }, "y" to pointsInCircle.map { it.y })
    val pointsOnTheCircle = getPointsOnTheCircle(circle)
    val circleData = mapOf("x" to pointsOnTheCircle.map { it.x }, "y" to pointsOnTheCircle.map { it.y })

    return ggplot() + geomPoint(
        data = allData,
        color = "red",
        size = 1.0
    ) { x = "x"; y = "y" } + geomPath(
        data = circleData,
        color = "blue"
    ) { x = "x"; y = "y" } + geomPoint(
        data = pointsInCircleData,
        color = "dark-green",
        size = 1.0
    ) { x = "x"; y = "y" } + coordFixed()
}


