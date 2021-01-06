package string

class IsUnique {
    fun isUnique(word: String): Boolean {
        val characters = hashMapOf<Char, Int>()

        word.forEach { character ->
            if(characters.containsKey(character))
                return false
            characters[character] = 0
        }

        return true
    }
}

/*
* Time Complexity:  O(n)
* Space Complexity: O(n)
*
* n = size of 'word' parameter*/