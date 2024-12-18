fun main() {
    fun part1(input: List<String>): Int {
        println("input = ${input}")

        // input = xmul(2,4)%&mul[3,7]!@^do_not_mul(5,5)+mul(32,64]then(mul(11,8)mul(8,5))
        var sum = 0

        input.forEach { instr ->
            val regex = """mul\(\d{1,3},\d{1,3}\)""".toRegex()
            val matches = regex.findAll(instr)

            matches.forEach {
                it.value.println()
                val matches2 = """(\d+)""".toRegex().findAll(it.value)
                matches2.toList().apply {
                    sum += get(0).value.toInt()*get(1).value.toInt()
                }
                //val (x,y) = toRegex.matchEntire(it.value)?.destructured ?: throw IllegalArgumentException("Bad input")

            }
        }

        sum.println()
        return sum
    }

    fun findSafeRanges(input: String): MutableList<IntRange> {
        var safeRangeStart = 0
        val safeRanges = mutableListOf<IntRange>()
        var isRangeAdded = false
        var previousFind = ""
        // 0 until a don't is a safe range
        // don't until do() is unsafe range
        input.forEachIndexed { index, c ->
            if (c == 'd') {
                // check if it is do() or don't()
                if (input.substring(index, index + "don't()".length) == "don't()") {
                    println("don't() found at $index")
                    if (previousFind == "dont") {
                        // ignore
                    } else {
                        safeRanges.add(safeRangeStart..index)
                        isRangeAdded = true
                        previousFind = "dont"
                    }
                }
                else if (input.substring(index, index + "do()".length) == "do()") {
                    println("do() found at $index")
                    if (previousFind == "do") {
                        // ignore
                    } else {
                        safeRangeStart = index
                        isRangeAdded = false
                        previousFind = "do"
                    }
                }
            }
        }
        if (!isRangeAdded) {
            safeRanges.add(safeRangeStart..input.length)
        }
        println("safeRanges = ${safeRanges}")
        return safeRanges
    }

    fun isInsideSafeRange(safeRanges: List<IntRange>, first: Int): Boolean {
        safeRanges.forEach {
            if(it.contains(first)) {
                return true
            }
        }
        return false
    }

    fun part2(input: List<String>): Long {
        var sum = 0L

        input.forEach { instr ->
            val safeRanges = findSafeRanges(instr)
            val regex = """mul\(\d{1,3},\d{1,3}\)""".toRegex()
            val mulMatches = regex.findAll(instr)

            mulMatches.forEach {
                if (isInsideSafeRange(safeRanges, it.range.first)) {
                    println("${it.value} is inside safe range ")
                    val matches2 = """(\d+)""".toRegex().findAll(it.value)
                    matches2.toList().apply {
                        sum += get(0).value.toLong()*get(1).value.toLong()
                    }
                }

            }
        }

        sum.println()
        return sum
    }

    fun part2Try1(input: List<String>): Long {
        var sum = 0L

        var enabled = true
        input.forEach { instr ->
            val regex = """(mul\(\d{1,3},\d{1,3}\)|do\(\)|don\'t)""".toRegex()
            val mulMatches = regex.findAll(instr)
            mulMatches.forEach {
                if (it.value.equals("don\'t")) {
                    enabled = false
                }else if (it.value.equals("do()")) {
                    enabled = true
                } else {
                    if (enabled) {
                        println("it = ${it.value}")
                        val matches2 = """(\d+)""".toRegex().findAll(it.value)
                        matches2.toList().apply {
                            sum += get(0).value.toLong()*get(1).value.toLong()
                        }
                    }

                }

            }
            enabled = true // set as true for next lines
        }

        sum.println()
        return sum
    }

    // Or read a large test input from the `src/Day03_test.txt` file:
//    val testInput = readInput("Day03_test")
//    check(part1(testInput) == 161)

    // Read the input from the `src/Day03.txt` file.
    val input = readInput("Day03")
//    part1(input).println()

    check(part2Try1(listOf("xmul(2,4)&mul[3,7]!^don't()_mul(5,5)+mul(32,64](mul(11,8)undo()?mul(8,5))")) == 48L)

    part2Try1(input).println()
}
