package the.head.that.feeds

//Todo: Stats need to have separate data class + database/sharedPref access. We're just using LiveData to observe state so we can update UI.

class Stats {
    companion object testComp {
        var friendlyAIIntegrity : Int = 0
        var friendlyAIEvolutionLevel : Int = 0
        var friendlyAIAggression : Int = 0
        var friendlyAIEmpathy : Int = 0
        var gridAIIntegrity : Int = 0
        var gridAIEvolutionLevel : Int = 0
        var gridAITrackingLevel : Int = 0
        var gridAIAggression : Int = 0
        var gridAIEmpathy : Int = 0
        var programmers : Int = 0
        var fighters : Int = 0
        var civilians : Double = 0.0

        var currentDay : Int = 0

        fun setDefaultStatValues() {
            friendlyAIIntegrity = 100
            friendlyAIEvolutionLevel = 0
            friendlyAIAggression = 20
            friendlyAIEmpathy = 20

            gridAIIntegrity = 100
            gridAITrackingLevel = 0
            gridAIAggression = 100
            gridAIEmpathy = 0
            gridAIEvolutionLevel = 900

            programmers = 100
            fighters = 100
            civilians = 42.0
        }
    }

}