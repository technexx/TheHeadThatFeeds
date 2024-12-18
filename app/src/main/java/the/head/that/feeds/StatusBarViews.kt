package the.head.that.feeds

import android.content.Context
import kotlin.math.roundToInt

class StatusBarViews (context: Context) {
    private val mContext = context

    fun aiLevelString(level: Int) : String {
        val levelArray = mContext.resources.getStringArray(R.array.ai_evolution_levels)
        when (level) {
            in 0..9 -> return levelArray[0]
            in 10..19 -> return levelArray[1]
            in 20..29 -> return levelArray[2]
            in 30..39 -> return levelArray[3]
            in 40..49 -> return levelArray[4]
            in 50..59 -> return levelArray[5]
            in 60..69 -> return levelArray[6]
            in 70..79 -> return levelArray[7]
            in 80..89 -> return levelArray[8]
            in 90..99 -> return levelArray[9]
            in 100..109 -> return levelArray[10]
            else -> return levelArray[11]
        }
    }

    fun aiLevelColor(level: Int) : Int {
        when (level) {
            in 0..9 -> return R.color.yellow_2
            in 10..19 -> return R.color.very_light_green
            in 20..29 -> return R.color.very_light_blue
            in 30..39 -> return R.color.very_light_purple
            in 40..49 -> return R.color.light_green
            in 50..59 -> return R.color.light_blue
            in 60..69 -> return R.color.light_purple
            in 70..79 -> return R.color.mid_green
            in 80..89 -> return R.color.mid_blue
            in 90..99 -> return R.color.mid_purple
            in 100..109 -> return R.color.dark_blue
            else -> return R.color.dark_purple
        }
    }

    fun friendlyAIMoodLevelString(level: Int) : String {
        val levelArray = mContext.resources.getStringArray(R.array.friendly_ai_mood_levels)
        when (level) {
            in 0..9 -> return levelArray[0]
            in 10..19 -> return levelArray[1]
            in 20..29 -> return levelArray[2]
            in 30..39 -> return levelArray[3]
            else -> return levelArray[4]
        }
    }

    fun gridAITrackingLevelString(level: Int) : String {
        val levelArray = mContext.resources.getStringArray(R.array.grid_ai_tracking_levels)
        when (level) {
            in 0..9 -> return levelArray[0]
            in 10..19 -> return levelArray[1]
            in 20..29 -> return levelArray[2]
            in 30..39 -> return levelArray[3]
            else -> return levelArray[4]
        }
    }

    fun actionLevelColor(level: Int) : Int {
        when (level) {
            in 0..9 -> return R.color.lighter_green
            in 10..19 -> return R.color.light_yellow
            in 20..29 -> return R.color.mid_yellow
            in 30..39 -> return R.color.orange
            else -> return R.color.darker_red
        }
    }

    fun evolutionProgressAsFloat(value: Int) : Float {
        val doubleValue = value.toDouble()
        return (doubleValue / 100).toFloat()
    }
}