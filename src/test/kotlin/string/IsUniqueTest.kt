package string

import io.kotlintest.properties.Gen
import io.kotlintest.properties.assertAll
import io.kotlintest.shouldBe
import io.kotlintest.specs.ShouldSpec
import kotlin.random.Random.Default.nextInt

class IsUniqueTest : ShouldSpec() {
    init {
        should("return true for every string with unique characters") {
            assertAll(UniqueStringGenerator) { uniqueString: String ->
                IsUnique().isUnique(uniqueString) shouldBe true
            }
        }

        should("return false for every string with non unique characters") {
            assertAll(NonUniqueStringGenerator) { nonUniqueString: String ->
                IsUnique().isUnique(nonUniqueString) shouldBe false
            }
        }
    }
}

object UniqueStringGenerator : Gen<String> {
    private fun getNextLatinAlphabetChar(): Char = nextInt(from = 97, until = 123).toChar()

    private fun nextLatinAlphabetUniqueString(length: Int): String {
        if (length > 26) throw RuntimeException("It is impossible to build a string of size $length with only unique characters")

        return (0 until length).fold(emptySet<Char>()) { usedChars, _ ->
            setOf(*usedChars.toTypedArray(), getNextLatinAlphabetCharNotContainedIn(usedChars))
        }.joinToString("")
    }

    private fun getNextLatinAlphabetCharNotContainedIn(setOfChars: Set<Char>): Char {
        if (isNotPossibleToHaveAnotherLatinAlphabetCharacterInside(setOfChars))
            throw RuntimeException("every latin alphabet character is already in $setOfChars")

        fun getNextLatinAlphabetCharNotContainedIn(setOfChars: Set<Char>): Char {
            val latinAlphabetChar = getNextLatinAlphabetChar()
            if (setOfChars.contains(latinAlphabetChar))
                return getNextLatinAlphabetCharNotContainedIn(setOfChars)
            return latinAlphabetChar
        }

        return getNextLatinAlphabetCharNotContainedIn(setOfChars)
    }

    private fun isNotPossibleToHaveAnotherLatinAlphabetCharacterInside(setOfChars: Set<Char>): Boolean {
        return !isPossibleToHaveAnotherLatinAlphabetCharacterInside(setOfChars)
    }

    private fun isPossibleToHaveAnotherLatinAlphabetCharacterInside(setOfChars: Set<Char>): Boolean {
        return setOfChars.size < 26
    }

    override fun constants(): Iterable<String> {
        return listOf("")
    }

    override fun random(): Sequence<String> {
        return generateSequence { nextLatinAlphabetUniqueString(nextInt(1, 27)) }
    }
}

object NonUniqueStringGenerator: Gen<String> {
    override fun constants(): Iterable<String> {
        return emptyList()
    }

    override fun random(): Sequence<String> {
        return Gen.string(100).random()
    }
}