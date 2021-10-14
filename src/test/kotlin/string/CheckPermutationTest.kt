package string

import io.kotlintest.properties.Gen
import io.kotlintest.properties.assertAll
import io.kotlintest.shouldBe
import io.kotlintest.specs.ShouldSpec
import kotlin.random.Random.Default.nextInt

class CheckPermutationTest : ShouldSpec() {
    init {
        should("return true if the second string is a permutation of the first one") {
            assertAll(PermutedStringsGenerator) { permutedStrings: Pair<String, String> ->
                CheckPermutation().checkPermutation(permutedStrings.first, permutedStrings.second) shouldBe true
            }
        }

        should("return false if the second string is not a permutation of the first one") {
            assertAll(DistinctNonPermutedStringsGenerator) { distinctNonPermutedStrings: Pair<String, String> ->
                CheckPermutation().checkPermutation(
                    distinctNonPermutedStrings.first,
                    distinctNonPermutedStrings.second
                ) shouldBe false
            }
        }
    }
}

object PermutedStringsGenerator : Gen<Pair<String, String>> {
    override fun constants(): Iterable<Pair<String, String>> {
        return listOf("" to "")
    }

    override fun random(): Sequence<Pair<String, String>> {
        return generateSequence { generatePairOfPermutedStrings(::getNextRandomLatinAlphabetString, nextInt(1, 20)) }
    }

    private fun generatePairOfPermutedStrings(
        generateStringFunction: (length: Int) -> String,
        length: Int
    ): Pair<String, String> {
        val randomString = generateStringFunction(length)
        return randomString.toPairWithPermutedString()
    }

    private fun String.toPairWithPermutedString(): Pair<String, String> {
        return this to this.permuted()
    }

    private fun String.permuted(): String {
        val indexesPermuted = mutableSetOf<Int>()

        fun String.permute(randomIndex: (from: Int, to: Int) -> Int): Char {
            val index = randomIndex(0, this.length)
            if (indexesPermuted.contains(index)) return this.permute(randomIndex)
            indexesPermuted.add(index)
            return this[index]
        }

        return (this.indices).map {
            this.permute { from: Int, until: Int -> nextInt(from, until) }
        }.joinToString("")
    }
}

object DistinctNonPermutedStringsGenerator : Gen<Pair<String, String>> {
    override fun constants(): Iterable<Pair<String, String>> {
        return listOf()
    }

    override fun random(): Sequence<Pair<String, String>> {
        return generateSequence {
            generateDistinctNonPermutedPairOfStrings(
                ::getNextRandomLatinAlphabetString,
                ::randomIntFromOneUntilTwenty
            )
        }
    }

    private fun randomIntFromOneUntilTwenty(): Int {
        return nextInt(1, 20)
    }

    private fun generateDistinctNonPermutedPairOfStrings(
        generateStringFunction: (length: Int) -> String,
        length: () -> Int
    ): Pair<String, String> {
        val randomString = generateStringFunction(length())
        return randomString.toPairWithDistinctNonPermutedString(generateStringFunction, length)
    }

    private fun String.toPairWithDistinctNonPermutedString(
        generateStringFunction: (length: Int) -> String,
        length: () -> Int
    ): Pair<String, String> {
        return this to this.distinct(generateStringFunction, length)
    }

    private fun String.distinct(generateStringFunction: (length: Int) -> String, length: () -> Int): String {
        fun generateDistinctStringFrom(string: String): String {
            val randomString = generateStringFunction(length())
            if (string == randomString) return generateDistinctStringFrom(string)
            return randomString
        }

        return generateDistinctStringFrom(this)
    }
}

private fun getNextLatinAlphabetChar(): Char = nextInt(from = 97, until = 123).toChar()

private fun getNextRandomLatinAlphabetString(length: Int): String {
    return (0..length).map { getNextLatinAlphabetChar() }.joinToString("")
}