package the.head.that.feeds

import android.content.Context

//Choice Events: Player attacks, player defense, recruitment, friendly AI action, friendly AI evolution
class Events(context : Context) {
    private val mContext = context
    val PLAYER_ATTACK = 0
    val PLAYER_DEFENSE = 1
    val PLAYER_NETWORK_DEFENSE = 2

    fun rollTypeOfEvent() {

    }

    fun dailyFriendlyAIProgress() : Int{

    }

}