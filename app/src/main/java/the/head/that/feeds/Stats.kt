package the.head.that.feeds

//Todo: Stats need to have separate data class + database/sharedPref access. We're just using LiveData to observe state so we can update UI.

class Stats {
    var friendlyAIIntegrity : Double = 0.0
    var friendlyAIEvolutionLevel : Int = 0
    var aggression : Int = 0
    var empathy : Int = 0
    var programmers : Int = 0
    var fighters : Int = 0
    var civilians : Double = 0.0

    var gridAIIntegrity : Double = 0.0
    var gridAITrackingLevel : Int = 0

    var currentDay : Int = 0

    fun setDefaultStatValues() {
        friendlyAIIntegrity = 100.0
        friendlyAIEvolutionLevel = 0
        aggression = 20
        empathy = 20
        programmers = 100
        fighters = 100
        civilians = 42.0

        gridAIIntegrity = 100.0
        gridAITrackingLevel = 0
    }
}