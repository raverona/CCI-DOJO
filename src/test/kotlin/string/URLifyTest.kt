package string

import io.kotlintest.properties.Gen
import io.kotlintest.properties.assertAll
import io.kotlintest.shouldBe
import io.kotlintest.specs.ShouldSpec

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
        return listOf("", " ", "  ", "   ")
    }

    override fun random(): Sequence<String> {
        TODO()
    }

}