package regionalsearch

class Region(points: List<Point>) {
    private val points: List<Point> = points.sortedBy { it.x }
    private val leftRegion: Region?
    private val rightRegion: Region?

    init {
        if (points.size > 2) {
            val sortedPointsByX = points.sortedBy { it.x }
            leftRegion = Region(sortedPointsByX.getFirstHalf())
            rightRegion = Region(sortedPointsByX.getSecondHalf())
        } else {
            leftRegion = null
            rightRegion = null
        }
        validateRegion()
    }

    fun regionalSearch(circle: Circle): Set<Point> {
        val points = if (getLeftBound() >= circle.getLeftBound() && getRightBound() <= circle.getRightBound()) {
            points.filter { circle.contains(it) }.toSet()
        } else {
            (leftRegion?.regionalSearch(circle) ?: emptySet()) +
                    (rightRegion?.regionalSearch(circle) ?: emptySet())
        }
        return if (points.isEmpty()) setOf(this.points.first(), this.points.last())
            .filter { circle.contains(it) }.toSet()
        else points
    }

    private fun getLeftBound(): Double = points.first().x

    private fun getRightBound(): Double = points.last().x

    private fun validateRegion() {
        if ((leftRegion == null) xor (rightRegion == null))
            throw java.lang.IllegalStateException("Region must either have two children or none")

    }

}

private fun <T> List<T>.getFirstHalf() = take(size / 2 + 1)

private fun <T> List<T>.getSecondHalf() = drop(size / 2)