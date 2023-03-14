package the.head.that.feeds

import android.content.Context

class ValuesToStrings (context: Context) {
    private val mContext = context

    fun friendlyAILevelString(level: Int) : String {
        val levelArray = mContext.resources.getStringArray(R.array.player_ai_levels)
        return levelArray[level]
    }

    fun enemyAILevelString(level: Int) : String {
        val levelArray = mContext.resources.getStringArray(R.array.enemy_ai_levels)
        return levelArray[level]
    }
}