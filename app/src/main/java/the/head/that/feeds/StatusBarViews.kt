package the.head.that.feeds

import android.content.Context

class StatusBarViews (context: Context) {
    private val mContext = context

    fun friendlyAILevelString(level: Int) : String {
        val levelArray = mContext.resources.getStringArray(R.array.player_ai_levels)
        return levelArray[level]
    }

    fun friendlyAILevelColor(level: Int) : Int {
        when (level) {
            0 -> return R.color.white
            1 -> return R.color.very_light_green
            2 -> return R.color.very_light_blue
            3 -> return R.color.very_light_purple
            4 -> return R.color.light_green
            5 -> return R.color.light_blue
            6 -> return R.color.light_purple
            7 -> return R.color.mid_green
            8 -> return R.color.mid_blue
            9 -> return R.color.mid_purple
            10 -> return R.color.dark_blue
            11 -> return R.color.dark_purple
        }

        return R.color.white
    }

    fun gridAILevelString(level: Int) : String {
        val levelArray = mContext.resources.getStringArray(R.array.enemy_ai_levels)
        return levelArray[level]
    }

    fun gridAILevelColor(level: Int) : Int {
        when (level) {
            0 -> return R.color.android_green
            1 -> return R.color.light_yellow
            2 -> return R.color.mid_yellow
            3 -> return R.color.orange
            4 -> return R.color.android_red
        }

        return R.color.android_green
    }

}