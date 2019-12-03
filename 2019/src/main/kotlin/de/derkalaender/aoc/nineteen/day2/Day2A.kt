package de.derkalaender.aoc.nineteen.day2

fun main() {
    val fileContent = {}.javaClass.getResource("/inputs/day2/input.txt").readText()

    val opCode = fileContent.splitToSequence(',').map { it.toInt() }.toMutableList().apply {
        set(1, 12)
        set(2, 2)
    }

    for (i in 0 until opCode.size / 4 + 1) {
        val code = opCode[i * 4]
        when (code) {
            in 1..2 -> {
                val n1 = opCode[opCode[i * 4 + 1]]
                val n2 = opCode[opCode[i * 4 + 2]]
                val target = opCode[i * 4 + 3]

                opCode[target] = if (code == 1) n1 + n2 else n1 * n2
            }
            99 -> {
                println("New Opcode: ${opCode.joinToString(",")}")
                println("First Number: ${opCode[0]}")
                return
            }
        }
    }
}