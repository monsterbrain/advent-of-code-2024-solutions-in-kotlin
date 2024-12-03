import kotlin.math.abs

fun main() {
    fun isSafe(values: List<Int>, diff: MutableList<Int>): Boolean {
        println("values = ${values}")
        // all increasing or all decreasing
        val increasing = diff.filter { it > 0 }.count() == diff.size
        if (increasing){
            println("increasing")
        }

        val decreasing = diff.filter { it < 0 }.count() == diff.size

        if (increasing || decreasing) {} else {
            println("Breaks Order")
        }

        // Any two adjacent levels differ by at least one and at most three.
        val isAdjacentWithinLimits = diff.count { abs(it) in 1..3 } == diff.size

        /*if (increasing || decreasing || isAdjacentWithinLimits){
            println("all increasing or all decreasing")
        } else {
            println("false")
        }

        if (isAdjacentWithinLimits){
            println("in between 1 and 3")
        } else {
            println("false, above 3 or below 1 !!!")
        }*/

        return ((increasing || decreasing) && isAdjacentWithinLimits)
    }

    fun part1(input: List<String>): Int {
        var safeCount = 0
        input.forEach { report ->
            val values = report.split(" ").map { it.toInt() }
            val diffs = mutableListOf<Int>()
            values.forEachIndexed { i, value ->
                if (i<values.size-1) {
                    diffs.add(values[i+1] - value)
                }
            }
            if (isSafe(values, diffs)) {
                safeCount += 1
            }
        }

        safeCount.println()

        return safeCount
    }

    fun isSafeAfterRemovingAny(values: List<Int>, diffs: MutableList<Int>): Boolean {
        println("isSafeAfterRemoving")
        values.forEachIndexed {i, it ->
            val  removedList = values.toMutableList()
            removedList.removeAt(i)

            println("removedList = ${removedList}")

            val newDiffs = mutableListOf<Int>()
            removedList.forEachIndexed { index, value ->
                if (index<removedList.size-1) {
                    newDiffs.add(removedList[index+1] - value)
                }
            }
            // note: much of the mistakes is from either quickly passing and rewriting the functions
            // such that I forgot to change the parameters accordingly.
            if (isSafe(removedList, newDiffs)) {
                return true
            }
        }
        return false
    }

    fun part2(input: List<String>): Int {
        var safeCount = 0
        input.forEach { report ->
            val values = report.split(" ").map { it.toInt() }
            val diffs = mutableListOf<Int>()
            values.forEachIndexed { i, value ->
                if (i<values.size-1) {
                    diffs.add(values[i+1] - value)
                }
            }
            if (isSafe(values, diffs)) {
                safeCount += 1
            } else if (isSafeAfterRemovingAny(values, diffs)) {
                println("safe after removing ... some")
                safeCount += 1
            }
        }

        safeCount.println()

        return safeCount
    }

    // Or read a large test input from the `src/Day01_test.txt` file:
    val testInput = readInput("Day02_test")
//    check(part1(testInput) == 2)

    // Read the input from the `src/Day02.txt` file.
    val input = readInput("Day02")
//    part1(input).println()

    check(part2(testInput) == 4)

    part2(input).println()
}
