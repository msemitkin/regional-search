import regionalsearch.Circle
import regionalsearch.Point
import regionalsearch.Region

fun main() {
    val points: Set<Point> = setOf(
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

    val circle = Circle(Point(7, 6), 3)

    val region = Region(points.sortedBy { it.x })
    val pointsInCircle = region.regionalSearch(circle)
    pointsInCircle.sortedWith(compareBy(Point::x, Point::y)).forEach(::println)
}