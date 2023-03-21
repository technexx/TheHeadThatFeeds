package the.head.that.feeds

import android.content.Context

class StatusBarViews (context: Context) {
    private val mContext = context

    fun friendlyAILevelString(level: Int) : String {
        val levelArray = mContext.resources.getStringArray(R.array.player_ai_levels)
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

    fun friendlyAILevelProgress(points: Float, multiplier: Int) : Float { return points * multiplier }

    fun friendlyAILevelColor(level: Int) : Int {
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
            in 0..9 -> return R.color.yellow_2
            in 10..19 -> return R.color.light_yellow
            in 20..29 -> return R.color.mid_yellow
            in 30..39 -> return R.color.orange
            else -> return R.color.darker_red
        }
    }

    fun healthLevelAsFloat(health: Double) : Float { return ((health / 100).toFloat())}

}