package the.head.that.feeds

import android.content.Context
import kotlin.math.roundToInt

class StatusBarViews (context: Context) {
    private val mContext = context

    fun aiLevelString(level: Int) : String {
        val levelArray = mContext.resources.getStringArray(R.array.player_ai_levels)
        when (level) {
            in 0..99 -> return levelArray[0]
            in 100..199 -> return levelArray[1]
            in 200..299 -> return levelArray[2]
            in 300..399 -> return levelArray[3]
            in 400..499 -> return levelArray[4]
            in 500..599 -> return levelArray[5]
            in 600..699 -> return levelArray[6]
            in 700..799 -> return levelArray[7]
            in 800..899 -> return levelArray[8]
            in 900..999 -> return levelArray[9]
            in 1000..1099 -> return levelArray[10]
            else -> return levelArray[11]
        }
    }

    fun aiLevelColor(level: Int) : Int {
        when (level) {
            in 0..90 -> return R.color.yellow_2
            in 100..199 -> return R.color.very_light_green
            in 200..299 -> return R.color.very_light_blue
            in 300..399 -> return R.color.very_light_purple
            in 400..499 -> return R.color.light_green
            in 500..599 -> return R.color.light_blue
            in 600..699 -> return R.color.light_purple
            in 700..799 -> return R.color.mid_green
            in 800..899 -> return R.color.mid_blue
            in 900..990 -> return R.color.mid_purple
            in 1000..1099 -> return R.color.dark_blue
            else -> return R.color.dark_purple
        }
    }

    fun gridAIActionLevelString(level: Int) : String {
        val levelArray = mContext.resources.getStringArray(R.array.enemy_ai_levels)
        when (level) {
            in 0..9 -> return levelArray[0]
            in 10..19 -> return levelArray[1]
            in 20..29 -> return levelArray[2]
            in 30..39 -> return levelArray[3]
            else -> return levelArray[4]
        }
    }

    fun gridAIActionLevelColor(level: Int) : Int {
        when (level) {
            in 0..9 -> return R.color.lighter_green
            in 10..19 -> return R.color.light_yellow
            in 20..29 -> return R.color.mid_yellow
            in 30..39 -> return R.color.orange
            else -> return R.color.darker_red
        }
    }

    fun integrityLevelAsFloat(value: Int) : Float {
        val doubleValue = value.toDouble()
        return (doubleValue / 100).toFloat()
    }
}