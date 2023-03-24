package the.head.that.feeds

import android.content.Context
import android.util.Log
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.roundToInt

class Events(context : Context) {
    private val mContext = context

    val enemyTypesArray = mContext.resources.getStringArray(R.array.enemy_types)
    val destructionVerbsArray = mContext.resources.getStringArray(R.array.destruction_synonyms)
    val gridAIMalwareArray = mContext.resources.getStringArray(R.array.enemy_ai_malware)
    val proAggressionArray = mContext.resources.getStringArray(R.array.pro_aggression_triggers)
    val antiAggressionArray = mContext.resources.getStringArray(R.array.anti_aggression_triggers)
    val proEmpathyArray = mContext.resources.getStringArray(R.array.pro_empathy_triggers)
    val antiEmpathyArray = mContext.resources.getStringArray(R.array.anti_empathy_triggers)

    var TYPE_OF_EVENT = 0
    var pastEventsArray = ArrayList<Int>()

    val RANDOM_GOOD = 11
    val RANDOM_BAD = 12
    val RANDOM_MIXED = 13

    val PLAYER_LOW_ATTACK = 21
    val PLAYER_MEDIUM_ATTACK = 22
    val PLAYER_HIGH_ATTACK = 23
    val FRIENDLY_AI_LOW_ATTACK = 24
    val FRIENDLY_AI_MEDIUM_ATTACK = 25
    val FRIENDLY_AI_HIGH_ATTACK = 26

    val GRID_AI_LOW_NETWORK_ATTACK = 31
    val GRID_AI_HIGH_NETWORK_ATTACK = 32
    val GRID_AI_LOW_PHYSICAL_ATTACK = 33
    val GRID_AI_HIGH_PHYSICAL_ATTACK = 34

    val PLAYER_RECRUITMENT = 41
    val FRIENDLY_AI_RECRUITMENT = 42

    val FRIENDLY_AI_INTEGRITY_REPAIR = 51
    val GRID_AI_INTEGRITY_REPAIR = 52

    val FRIENDLY_AI_STAT_CHANGE = 61
    val FRIENDLY_AI_EVOLUTION_CHOICE = 62
    val FRIENDLY_AI_EVOLUTION_NO_CHOICE = 63

    var randomEventWeight = 0
    var playerAttackWeight = 0
    var gridAttackWeight = 0
    var playerRecruitmentWeight = 0
    var friendlyAIRecruitmentWeight = 0
    var friendlyAIStatChangeWeight = 0
    var friendlyAIIntegrityRepairWeight = 0
    var gridAIIntegrityRepairWeight = 0

    fun eventString(eventType: Int) : String {
        when (eventType) {
            11 -> return mContext.getString(R.string.random_good_event)
            12 -> return mContext.getString(R.string.random_bad_event)
            13 -> return mContext.getString(R.string.random_mixed_event)

            21 -> return mContext.getString(R.string.player_attack_low_risk)
            22 -> return mContext.getString(R.string.player_attack_medium_risk)
            23 -> return mContext.getString(R.string.player_attack_high_risk)
            24 -> return mContext.getString(R.string.)
            25 -> return mContext.getString(R.string.random_bad_event)
            26 -> return mContext.getString(R.string.random_mixed_event)

            11 -> return mContext.getString(R.string.random_good_event)
            12 -> return mContext.getString(R.string.random_bad_event)
            13 -> return mContext.getString(R.string.random_mixed_event)

            11 -> return mContext.getString(R.string.random_good_event)
            12 -> return mContext.getString(R.string.random_bad_event)
            13 -> return mContext.getString(R.string.random_mixed_event)
        }
    }

    fun rolledEvent() : Int {
        setAllEventWeights()

        val totalEventsWeight = totalEventsWeight()
        val roll = (0..totalEventsWeight).random()

        val eventsWeightArray = eventsWeightArray()
        val eventsAsIntArray = eventsAsIntArray()

        var valueToRollAgainst = 0

        for (i in eventsWeightArray.indices) {
            valueToRollAgainst += eventsWeightArray[i]

            if (valueToRollAgainst >= roll) {
                Log.i("testRoll", "event return is ${eventsAsIntArray[i]}")
                addEventToPastEventsList(eventsAsIntArray[i])
                return eventsAsIntArray[i]
            }
        }
        return 0
    }

    private fun setAllEventWeights() {
        setRandomEventWeight()
        setPlayerAttackWeight()
        setGridAIAttackWeight(Stats.gridAITrackingLevel)
        setResistanceRecruitmentWeight(Stats.fighters, Stats.programmers)
        setFriendlyAIRecruitmentWeight()
        setFriendlyAIStatChangeWeight()
        setFriendlyAIIntegrityRepair(Stats.friendlyAIIntegrity)
        setGridAIIntegrityRepair(Stats.gridAIIntegrity)
    }

    private fun totalEventsWeight() : Int {
        return randomEventWeight + playerAttackWeight + gridAttackWeight + playerRecruitmentWeight + friendlyAIRecruitmentWeight + friendlyAIStatChangeWeight + friendlyAIIntegrityRepairWeight + gridAIIntegrityRepairWeight
    }

    private fun eventsAsIntArray() : ArrayList<Int> {
        return ArrayList(listOf(randomEvent(), playerAttack(), gridAIAttack(), playerRecruitment(), friendlyAIRecruitment(), friendlyAIStatChange(), playerIntegrityRepair(), gridAIIntegrityRepair()))
    }

    private fun eventsWeightArray() : ArrayList<Int> {
        return ArrayList(listOf(randomEventWeight, playerAttackWeight, gridAttackWeight, playerRecruitmentWeight, friendlyAIRecruitmentWeight, friendlyAIStatChangeWeight, friendlyAIIntegrityRepairWeight, gridAIIntegrityRepairWeight))
    }

    private fun addEventToPastEventsList(event: Int) { pastEventsArray.add(event) }

    fun setRandomEventWeight() { randomEventWeight = 50 }

    fun setPlayerAttackWeight() { playerAttackWeight = 20 }

    fun setGridAIAttackWeight(trackingLevel: Int) { gridAttackWeight = (trackingLevel * 1.2).roundToInt() }

    fun setResistanceRecruitmentWeight(fighters: Int, programmers: Int) { playerRecruitmentWeight = ((fighters + programmers) / 100) }

    fun setFriendlyAIRecruitmentWeight() { friendlyAIRecruitmentWeight = 5 }

    fun setFriendlyAIStatChangeWeight() { friendlyAIStatChangeWeight = 5 }

    fun setFriendlyAIIntegrityRepair(currentIntegrity: Int) { friendlyAIIntegrityRepairWeight = ((100 - currentIntegrity) * 0.25).roundToInt() }

    fun setGridAIIntegrityRepair(currentIntegrity: Int) { gridAIIntegrityRepairWeight= ((100 - currentIntegrity) * 0.5).roundToInt() }

    fun randomEvent() : Int { return (RANDOM_GOOD..RANDOM_MIXED).random() }

    fun playerAttack() : Int { return (PLAYER_LOW_ATTACK..PLAYER_HIGH_ATTACK).random() }
    fun gridAIAttack() : Int { return (GRID_AI_LOW_NETWORK_ATTACK..GRID_AI_HIGH_PHYSICAL_ATTACK).random() }

    fun playerRecruitment() : Int { return PLAYER_RECRUITMENT }
    fun friendlyAIRecruitment() : Int { return FRIENDLY_AI_RECRUITMENT }

    fun friendlyAIStatChange() : Int { return FRIENDLY_AI_STAT_CHANGE }

    fun playerIntegrityRepair() : Int { return FRIENDLY_AI_INTEGRITY_REPAIR }
    fun gridAIIntegrityRepair() : Int { return GRID_AI_INTEGRITY_REPAIR }

    //Todo: Will trigger independent of roll if conditions are met.
    fun friendlyAIEvolutionChoice() : Int { return FRIENDLY_AI_EVOLUTION_CHOICE }
    fun friendlyAIEvolutionNoChoice() : Int { return FRIENDLY_AI_EVOLUTION_NO_CHOICE }

    fun dailyFriendlyAIProgress(programmers : Int) : Int {
        val doubleValue : Double = (programmers/50).toDouble()
        return doubleValue.roundToInt()
    }

    fun testEventWeights() {
        randomEventWeight = 10; playerAttackWeight = 10; gridAttackWeight = 10; playerRecruitmentWeight = 10; friendlyAIRecruitmentWeight = 10; friendlyAIStatChangeWeight = 10; friendlyAIIntegrityRepairWeight = 10; gridAIIntegrityRepairWeight = 10
    }
}