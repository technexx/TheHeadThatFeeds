package the.head.that.feeds

//Todo: Stats need to have separate data class + database/sharedPref access. We're just using LiveData to observe state so we can update UI.

//Todo: Set default levels within GameActivity and have viewModel get its mutables from there.
class Stats {
    var friendlyAIIntegrity : Double = 0.0
    var friendlyAIIntelligenceLevel : Int = 0
    var friendlyAIIntelligenceLevelProgress = 0
    var aggression : Int = 0
    var empathy : Int = 0
    var programmers : Int = 0
    var fighters : Int = 0
    var civilians : Double = 0.0

    var enemyAIIntegrity : Double = 0.0
    var enemyAITrackingLevel : Int = 0
    var enemyAITrackingLevelProgress : Int = 0

    var currentDay : Int = 0

    fun setDefaultStatValues() {
        friendlyAIIntegrity = 100.0
        friendlyAIIntelligenceLevel = 0
        friendlyAIIntelligenceLevelProgress = 0
        aggression = 20
        empathy = 20
        programmers = 100
        fighters = 100
        civilians = 42.0

        enemyAIIntegrity = 100.0
        enemyAITrackingLevel = 0
        enemyAITrackingLevelProgress = 0
    }
}