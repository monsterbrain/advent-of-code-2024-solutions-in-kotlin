import kotlin.math.abs

fun main() {
    fun part1(input: List<String>): Int {
        val inRegex = """(\d+)\s*(\d+)""".toRegex()
        val lefts = mutableListOf<Int>()
        val rights = mutableListOf<Int>()
        input.forEach {
            val (left, right) = inRegex.matchEntire(it)?.destructured ?: throw IllegalArgumentException("Bad input")
            println("left,right = [$left, $right]")
            lefts.add(left.toInt())
            rights.add(right.toInt())
        }
        lefts.sort()
        rights.sort()

        var sum = 0
        // note: swapped the params it, i instead of i, it, and hence the sum wasn't getting right ...
        lefts.forEachIndexed {i ,it ->
            sum += abs(it-rights[i])
        }

        return sum
    }

    fun part2(input: List<String>): Int {
        val inRegex = """(\d+)\s*(\d+)""".toRegex()
        val lefts = mutableListOf<Int>()
        val rights = mutableListOf<Int>()
        input.forEach {
            val (left, right) = inRegex.matchEntire(it)?.destructured ?: throw IllegalArgumentException("Bad input")
            lefts.add(left.toInt())
            rights.add(right.toInt())
        }

        var sum = 0
        lefts.forEach { left ->
            val countInRightList = rights.count { it == left }
            sum += countInRightList * left
        }


        return sum
    }

    // Or read a large test input from the `src/Day01_test.txt` file:
    val testInput = readInput("Day01_test")
    check(part1(testInput) == 11)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day01")
//    part1(input).println()

    check(part2(testInput) == 31)

    part2(input).println()
}
