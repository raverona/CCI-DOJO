package string

class IsUnique {
    fun isUnique(word: String): Boolean {
        val characters = IntArray(26)

        word.forEach { character ->
            val charIndex = character - 'a'
            when (characters[charIndex]) {
                1 -> return false
                else -> characters[charIndex] = 1
            }
        }

        return true
    }
}

/*
* Time Complexity:  O(n)
* Space Complexity: O(n)
*
* n = size of 'word' parameter*/