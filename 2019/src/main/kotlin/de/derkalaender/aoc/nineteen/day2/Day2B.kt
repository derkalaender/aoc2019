package de.derkalaender.aoc.nineteen.day2

fun main() {
    val desiredOutput = 19690720

    val fileContent = {}.javaClass.getResource("/inputs/day2/input.txt").readText()

    val freshOpCode = fileContent.splitToSequence(',').map { it.toInt() }

    // Warning! Brute force!

    for (noun in 0..99) {
        for (verb in 0..99) {
            val opCode = freshOpCode.toMutableList().apply {
                set(1, noun)
                set(2, verb)
            }

            loop@ for (i in 0 until opCode.size / 4 + 1) {
                val code = opCode[i * 4]
                when (code) {
                    in 1..2 -> {
                        val n1 = opCode[opCode[i * 4 + 1]]
                        val n2 = opCode[opCode[i * 4 + 2]]
                        val target = opCode[i * 4 + 3]

                        opCode[target] = if (code == 1) n1 + n2 else n1 * n2
                    }
                    99 -> {
                        if (opCode[0] == desiredOutput) {
                            println("New Opcode: ${opCode.joinToString(",")}")
                            println("First Number: $desiredOutput")
                            println("Noun: $noun | Verb: $verb")
                            println("Calculated answer: ${100 * noun + verb}")
                            return
                        }
                        break@loop
                    }
                }
            }
        }
    }


}