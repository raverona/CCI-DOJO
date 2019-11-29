package string

import io.kotlintest.properties.Gen
import io.kotlintest.properties.assertAll
import io.kotlintest.shouldBe
import io.kotlintest.specs.ShouldSpec
import kotlin.random.Random.Default.nextInt

class URLifyTest : ShouldSpec() {
    init {
        should("replace spaces with '%20'") {
            assertAll(RandomStringWithSpacesGenerator) { randomStringWithSpaces: String ->
                URLify().URLify(
                    randomStringWithSpaces,
                    randomStringWithSpaces.length
                ) shouldBe randomStringWithSpaces.replaceSpacesWithPercentTwenty()
            }
        }
    }

    private fun String.replaceSpacesWithPercentTwenty(): String {
        return this.trimEnd().replace(" ".toRegex(), "%20")
    }
}

object RandomStringWithSpacesGenerator : Gen<String> {
    override fun constants(): Iterable<String> {
        return listOf("")
    }

    override fun random(): Sequence<String> {
        return generateSequence { generateRandomWordsWithSpaces(nextInt(1, 10)) }
    }

    private fun generateRandomWordsWithSpaces(numberOfWords: Int): String {
        return (1..numberOfWords).flatMap { listOf(nextLatinAlphabetString(nextInt(1, 10)), " ") }
            .joinToString("").trimEnd().addTrailingSpaces(numberOfWords)
    }

    private fun String.addTrailingSpaces(numberOfWords: Int): String {
        if (numberOfWords <= 0) return this
        return this + ((numberOfWords - 1) * " ")
    }

    operator fun Int.times(string: String): String {
        return (1..this).joinToString("") { string }
    }

    private fun nextLatinAlphabetString(length: Int): String {
        return (0 until length).map { getNextLatinAlphabetChar() }.joinToString("")
    }

    private fun getNextLatinAlphabetChar(): Char = nextInt(from = 97, until = 123).toChar()
}