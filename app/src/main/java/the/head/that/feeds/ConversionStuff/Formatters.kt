package the.head.that.feeds.ConversionStuff

class Formatters {

    fun commaInNumbers(number: Long) : String {
        val stringNumber = number.toString()
        val digits = stringNumber.count()

        var firstSet = ""
        var otherSets = ""

        if (digits >= 4) {
            if (digits == 4) firstSet = stringNumber[0].toString()
            if (digits == 5) firstSet = (stringNumber[0].toString() + stringNumber[1])
            if (digits == 6) firstSet = (stringNumber[0].toString() + stringNumber[1] + stringNumber[2])

        }

        return ""
    }
}