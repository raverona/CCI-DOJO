package string

class URLify {
    fun URLify(string: String, length: Int): String {
        if(length == 0 || string.length == length)
            return string

        val word = string.toCharArray()
        var newIndex = string.length - 1

        for (i in (length - 1) downTo 0) {
            if (word[i] != ' ')
                word[newIndex--] = word[i]
            else {
                word[newIndex - 2] = '%'
                word[newIndex - 1] = '2'
                word[newIndex] = '0'
                newIndex -= 3
            }
        }

        return word.joinToString("")
    }
}

/*
* Time Complexity:  O(n)
* Space Complexity: 0
*
* n = size of 'string' parameter*/