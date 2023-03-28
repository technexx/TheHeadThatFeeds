package the.head.that.feeds

import android.util.Log

class Stats {
    companion object SharedStats {
        var friendlyAIMood : Int = 0
        var friendlyAIEvolutionLevel : Int = 0
        var friendlyAIEvolutionProgress : Int = 0
        var friendlyAIAggression : Int = 0
        var friendlyAIEmpathy : Int = 0
        var programmers : Int = 0
        var fighters : Int = 0

        var gridAITrackingLevel : Int = 0
        var gridAIEvolutionLevel : Int = 0
        var gridAIEvolutionProgress : Int = 0
        var gridAIAggression : Int = 0
        var gridAIEmpathy : Int = 0
        var military : Double = 0.0
        var civilians : Double = 0.0

        var currentDay : Int = 0
    }
}