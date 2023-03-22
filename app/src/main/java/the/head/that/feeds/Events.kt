package the.head.that.feeds

import android.content.Context
import kotlin.math.roundToInt

//Choice Events: Player attacks, grid AI attack, recruitment, friendly AI action, friendly AI evolution
class Events(context : Context) {
    private val mContext = context
    val RANDOM_GOOD = 10
    val RANDOM_BAD = 11
    val RANDOM_MIXED = 12

    val PLAYER_LOW_ATTACK = 21
    val PLAYER_MEDIUM_ATTACK = 22
    val PLAYER_HIGH_ATTACK = 23
    val PLAYER_RECRUITMENT = 29

    val FRIENDLY_AI_ATTACK = 30
    val FRIENDLY_AI_STAT_CHANGE = 31
    val FRIENDLY_AI_RECRUITMENT = 32
    val FRIENDLY_AI_EVOLUTION_CHOICE = 33
    val FRIENDLY_AI_EVOLUTION_NO_CHOICE = 34

    val GRID_AI_LOW_NETWORK_ATTACK = 40
    val GRID_AI_HIGH_NETWORK_ATTACK = 41
    val GRID_AI_LOW_PHYSICAL_ATTACK = 42
    val GRID_AI_HIGH_PHYSICAL_ATTACK = 43
    val GRID_AI_INTEGRITY_REPAIR = 44
    val GRID_AI_TRACKING_CHANGE = 45


    fun rollTypeOfEvent() {

    }

    fun dailyFriendlyAIProgress(programmers : Int) : Int {
        val doubleValue : Double = (programmers/50).toDouble()
        return doubleValue.roundToInt()
    }

}