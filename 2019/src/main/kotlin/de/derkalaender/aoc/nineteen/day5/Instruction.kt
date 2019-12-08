package de.derkalaender.aoc.nineteen.day5

import java.util.*

enum class Mode {
    POSITION, IMMEDIATE;

    companion object {
        fun fromCode(code: Int): Mode {
            return if (code == 0) POSITION else IMMEDIATE
        }

        fun fromInstruction(inst: List<Int>, nParam: Int): Mode {
            return if (inst.size < nParam + 2) POSITION else fromCode(inst[1 + nParam])
        }
    }
}

data class Parameter(val mode: Mode, val value: Int) {
    fun get(opCode: List<Int>): Int {
        return if (mode == Mode.POSITION) {
            opCode[value]
        } else {
            value
        }
    }
}

abstract class Instruction {
    open lateinit var opCode: MutableList<Int>

    abstract fun exec()

    fun parse(opCode: MutableList<Int>, pointer: Int): Instruction {
        val instruction = opCode[pointer].toString().toCharArray().map { it.toString().toInt() }.reversed()
        val code = if (instruction.size > 1) instruction[0] + instruction[1] * 10 else instruction[0]
        return when (code) {
            1 -> Addition(
                pointer + 4,
                Parameter(Mode.fromInstruction(instruction, 1), opCode[pointer + 1]),
                Parameter(Mode.fromInstruction(instruction, 2), opCode[pointer + 2]),
                Parameter(Mode.fromInstruction(instruction, 3), opCode[pointer + 3])
            ).apply { this.opCode = opCode }
            2 -> Multiplication(
                pointer + 4,
                Parameter(Mode.fromInstruction(instruction, 1), opCode[pointer + 1]),
                Parameter(Mode.fromInstruction(instruction, 2), opCode[pointer + 2]),
                Parameter(Mode.fromInstruction(instruction, 3), opCode[pointer + 3])
            ).apply { this.opCode = opCode }
            3 -> Input(
                pointer + 2,
                Parameter(Mode.fromInstruction(instruction, 1), opCode[pointer + 1])
            ).apply { this.opCode = opCode }
            4 -> Output(
                pointer + 2,
                Parameter(Mode.fromInstruction(instruction, 1), opCode[pointer + 1]),
                ::println
            ).apply { this.opCode = opCode }
            5 -> JumpIfTrue(
                pointer + 3,
                Parameter(Mode.fromInstruction(instruction, 1), opCode[pointer + 1]),
                Parameter(Mode.fromInstruction(instruction, 2), opCode[pointer + 2])
            ).apply { this.opCode = opCode }
            6 -> JumpIfFalse(
                pointer + 3,
                Parameter(Mode.fromInstruction(instruction, 1), opCode[pointer + 1]),
                Parameter(Mode.fromInstruction(instruction, 2), opCode[pointer + 2])
            ).apply { this.opCode = opCode }
            7 -> LessThan(
                pointer + 4,
                Parameter(Mode.fromInstruction(instruction, 1), opCode[pointer + 1]),
                Parameter(Mode.fromInstruction(instruction, 2), opCode[pointer + 2]),
                Parameter(Mode.fromInstruction(instruction, 3), opCode[pointer + 3])
            ).apply { this.opCode = opCode }
            8 -> Equals(
                pointer + 4,
                Parameter(Mode.fromInstruction(instruction, 1), opCode[pointer + 1]),
                Parameter(Mode.fromInstruction(instruction, 2), opCode[pointer + 2]),
                Parameter(Mode.fromInstruction(instruction, 3), opCode[pointer + 3])
            ).apply { this.opCode = opCode }
            else -> Halt()
        }
    }
}

class Start(override var opCode: MutableList<Int>) : Instruction() {
    override fun exec() {
        println("Start")
        parse(opCode, 0).exec()
    }
}

class Halt : Instruction() {
    override fun exec() {
        println("DONE!")
    }
}

class Input(private val pointer: Int, private val saveTo: Parameter) : Instruction() {
    override fun exec() {
        val s = Scanner(System.`in`)

        print("Input required: ")
        val int = s.nextInt()
        println("")

        opCode[saveTo.value] = int
        parse(opCode, pointer).exec()
    }
}

class Output(
    private val pointer: Int,
    private val value: Parameter,
    private val outputTo: (Int) -> Unit
) : Instruction() {
    override fun exec() {
        outputTo(value.get(opCode))
        parse(opCode, pointer).exec()
    }
}

class JumpIfTrue(
    private val pointer: Int,
    private val a: Parameter,
    private val b: Parameter
) : Instruction() {
    override fun exec() {
        if (a.get(opCode) != 0) {
            parse(opCode, b.get(opCode)).exec()
        } else {
            parse(opCode, pointer).exec()
        }
    }
}

class JumpIfFalse(
    private val pointer: Int,
    private val a: Parameter,
    private val b: Parameter
) : Instruction() {
    override fun exec() {
        if (a.get(opCode) == 0) {
            parse(opCode, b.get(opCode)).exec()
        } else {
            parse(opCode, pointer).exec()
        }
    }
}

class LessThan(
    private val pointer: Int,
    private val a: Parameter,
    private val b: Parameter,
    private val saveTo: Parameter
) : Instruction() {
    override fun exec() {
        opCode[saveTo.value] = if (a.get(opCode) < b.get(opCode)) 1 else 0
        parse(opCode, pointer).exec()
    }
}

class Equals(
    private val pointer: Int,
    private val a: Parameter,
    private val b: Parameter,
    private val saveTo: Parameter
) : Instruction() {
    override fun exec() {
        opCode[saveTo.value] = if (a.get(opCode) == b.get(opCode)) 1 else 0
        parse(opCode, pointer).exec()
    }
}

class Addition(
    private val pointer: Int,
    private val a: Parameter,
    private val b: Parameter,
    private val saveTo: Parameter
) : Instruction() {
    override fun exec() {
        opCode[saveTo.value] = a.get(opCode) + b.get(opCode)
        parse(opCode, pointer).exec()
    }
}

class Multiplication(
    private val pointer: Int,
    private val a: Parameter,
    private val b: Parameter,
    private val saveTo: Parameter
) : Instruction() {
    override fun exec() {
        opCode[saveTo.value] = a.get(opCode) * b.get(opCode)
        parse(opCode, pointer).exec()
    }
}