package string

import io.kotlintest.properties.Gen
import io.kotlintest.properties.assertAll
import io.kotlintest.shouldBe
import io.kotlintest.specs.ShouldSpec

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
        TODO("not implemented")
    }

    override fun random(): Sequence<Pair<String, String>> {
        TODO("not implemented")
    }
}

object DistinctNonPermutedStringsGenerator : Gen<Pair<String, String>> {
    override fun constants(): Iterable<Pair<String, String>> {
        TODO("not implemented")
    }

    override fun random(): Sequence<Pair<String, String>> {
        TODO("not implemented")
    }
}