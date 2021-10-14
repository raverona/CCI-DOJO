package string

class CheckPermutation {
    fun checkPermutation(first: String, second: String): Boolean {
        val charactersFrequency = hashMapOf<Char, Int>()

        first.forEach { character ->
            charactersFrequency[character] = charactersFrequency.getOrDefault(character, 0) + 1
        }

        second.forEach { character ->
            val oldFrequency = charactersFrequency[character] ?: return false
            val newFrequency = oldFrequency - 1

            if (newFrequency == 0)
                charactersFrequency.remove(character)
            else
                charactersFrequency[character] = newFrequency
        }

        if (charactersFrequency.isEmpty())
            return true
        return false
    }
}

/*
* Time Complexity:  O(n + k)
* Space Complexity: O(n)
*
* n = size of 'first' parameter
* k = size of 'second' parameter*/