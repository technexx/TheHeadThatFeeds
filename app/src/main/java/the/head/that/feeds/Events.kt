package the.head.that.feeds

import android.content.Context
import android.util.Log
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.roundToInt

class Events(context : Context) {
    private val mContext = context

    val RANDOM_GOOD = 11
    val RANDOM_BAD = 12

    val FRIENDLY_AI_DIRECTED_NETWORK_ATTACK = 21
    val FRIENDLY_AI_DIRECTED_MILITARY_ATTACK = 22

    val FRIENDLY_AI_AUTONOMOUS_NETWORK_ATTACK = 31
    val FRIENDLY_AI_AUTONOMOUS_MILITARY_ATTACK = 32

    val PLAYER_NETWORK_ATTACK = 41
    val PLAYER_MILITARY_ATTACK = 42

    val GRID_AI_LOW_NETWORK_ATTACK = 51
    val GRID_AI_LOW_PHYSICAL_ATTACK = 52
    val GRID_AI_HIGH_NETWORK_ATTACK = 53
    val GRID_AI_HIGH_PHYSICAL_ATTACK = 54

    val FRIENDLY_AI_RECRUITMENT = 61

    val FRIENDLY_AI_STAT_CHANGE = 71

    val FRIENDLY_AI_EVOLUTION_CHOICE = 81
    val FRIENDLY_AI_EVOLUTION_NO_CHOICE = 82

    var randomEventWeight = 0
    var playerAttackWeight = 0
    var gridLowAttackWeight = 0
    var gridHighAttackWeight = 0
    var playerRecruitmentWeight = 0
    var friendlyAIRecruitmentWeight = 0
    var friendlyAIStatChangeWeight = 0
    var friendlyAIEvolutionLevelProgressRepairWeight = 0
    var gridAIIntegrityRepairWeight = 0

    var pastEventsArray = ArrayList<Int>()

    fun eventString(eventType: Int) : String {
        when (eventType) {
            RANDOM_GOOD -> return (randomStringFromArray(R.array.random_good_event_array))
            RANDOM_BAD -> return (randomStringFromArray(R.array.random_bad_event_array))

            FRIENDLY_AI_DIRECTED_NETWORK_ATTACK -> return mContext.getString(R.string.friendly_ai_directed_network_attack)
            FRIENDLY_AI_DIRECTED_MILITARY_ATTACK -> return mContext.getString(R.string.friendly_ai_directed_military_attack)

            FRIENDLY_AI_AUTONOMOUS_NETWORK_ATTACK -> return mContext.getString(R.string.friendly_ai_autonomous_network_attack)
            FRIENDLY_AI_AUTONOMOUS_MILITARY_ATTACK -> return mContext.getString(R.string.friendly_ai_autonomous_military_attack)

            PLAYER_NETWORK_ATTACK -> return mContext.getString(R.string.player_network_attack)
            PLAYER_MILITARY_ATTACK -> return mContext.getString(R.string.player_military_attack)

            GRID_AI_LOW_NETWORK_ATTACK -> return mContext.getString(R.string.grid_ai_network_attack_low_threat,  randomStringFromArray(R.array.enemy_types), randomStringFromArray(R.array.enemy_ai_malware))
            GRID_AI_LOW_PHYSICAL_ATTACK -> return mContext.getString(R.string.grid_ai_physical_attack_low_threat, randomStringFromArray(R.array.enemy_types), randomStringFromArray(R.array.destruction_synonyms))
            GRID_AI_HIGH_NETWORK_ATTACK -> return mContext.getString(R.string.grid_ai_network_attack_high_threat, randomStringFromArray(R.array.enemy_types))
            GRID_AI_HIGH_PHYSICAL_ATTACK -> return mContext.getString(R.string.grid_ai_physical_attack_high_threat, randomStringFromArray(R.array.enemy_types), randomStringFromArray(R.array.destruction_synonyms))

            FRIENDLY_AI_RECRUITMENT -> return mContext.getString(R.string.friendly_ai_recruitment)

            FRIENDLY_AI_STAT_CHANGE -> return mContext.getString(R.string.friendly_ai_stat_altering_event)

            FRIENDLY_AI_EVOLUTION_CHOICE -> return mContext.getString(R.string.friendly_ai_evolution_with_choice)
            FRIENDLY_AI_EVOLUTION_NO_CHOICE -> return mContext.getString(R.string.friendly_ai_evolution_no_choice)

            else -> return ""
        }
    }

    fun randomStringFromArray(array: Int) : String {
        val fetchedArray = mContext.resources.getStringArray(array)
        return fetchedArray[(fetchedArray.indices).random()].toString()
    }

    fun rolledEvent(trackingLevel: Int, fighters: Int, programmers: Int) : Int {
        setAllEventWeights(trackingLevel, fighters, programmers)

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

    private fun setAllEventWeights(trackingLevel: Int, fighters: Int, programmers: Int) {
        setRandomEventWeight()
        setPlayerAttackWeight()
        setGridAILowAttackWeight(trackingLevel)
        setGridAIHighAttackWeight(trackingLevel)
        setResistanceRecruitmentWeight(fighters, programmers)
        setFriendlyAIRecruitmentWeight()
        setFriendlyAIStatChangeWeight()
        setFriendlyAIEvolutionProgressWeight()
        setGridAIEvolutionProgressWeight()
    }

    private fun totalEventsWeight() : Int {
        return randomEventWeight + playerAttackWeight + gridLowAttackWeight + gridHighAttackWeight + playerRecruitmentWeight + friendlyAIRecruitmentWeight + friendlyAIStatChangeWeight + friendlyAIEvolutionLevelProgressRepairWeight + gridAIIntegrityRepairWeight
    }

    private fun eventsAsIntArray() : ArrayList<Int> {
        return ArrayList(listOf(randomEvent(), playerAttack(), gridAIAttackLowDamage(), gridAIAttackHighDamage(), friendlyAIRecruitment(), friendlyAIStatChange()))
    }

    private fun eventsWeightArray() : ArrayList<Int> {
        return ArrayList(listOf(randomEventWeight, playerAttackWeight, gridLowAttackWeight, gridHighAttackWeight, playerRecruitmentWeight, friendlyAIRecruitmentWeight, friendlyAIStatChangeWeight, friendlyAIEvolutionLevelProgressRepairWeight, gridAIIntegrityRepairWeight))
    }

    private fun addEventToPastEventsList(event: Int) { pastEventsArray.add(event) }

    fun setRandomEventWeight() { randomEventWeight = 50 }

    fun setPlayerAttackWeight() { playerAttackWeight = 20 }

    fun setGridAILowAttackWeight(trackingLevel: Int) {
        if (trackingLevel <= 20) gridLowAttackWeight = (trackingLevel * 1.2).roundToInt()
        else gridLowAttackWeight = (trackingLevel * 0.5).roundToInt() }

    fun setGridAIHighAttackWeight(trackingLevel: Int) {
        if (trackingLevel > 20) gridHighAttackWeight = (trackingLevel * 1.2).roundToInt()
        else gridHighAttackWeight = (trackingLevel * 0.3).roundToInt()
    }

    fun setResistanceRecruitmentWeight(fighters: Int, programmers: Int) { playerRecruitmentWeight = ((fighters + programmers) / 100) }

    fun setFriendlyAIRecruitmentWeight() { friendlyAIRecruitmentWeight = 5 }

    fun setFriendlyAIStatChangeWeight() { friendlyAIStatChangeWeight = 5 }

    fun setFriendlyAIEvolutionProgressWeight() { friendlyAIEvolutionLevelProgressRepairWeight = 10 }

    fun setGridAIEvolutionProgressWeight() { gridAIIntegrityRepairWeight = 10 }

    fun randomEvent() : Int { return (RANDOM_GOOD..RANDOM_BAD).random() }

    fun playerAttack() : Int { return (PLAYER_NETWORK_ATTACK..PLAYER_MILITARY_ATTACK).random() }

    fun gridAIAttackLowDamage() : Int { return (GRID_AI_LOW_NETWORK_ATTACK..GRID_AI_LOW_PHYSICAL_ATTACK).random() }

    fun gridAIAttackHighDamage() : Int { return (GRID_AI_LOW_PHYSICAL_ATTACK..GRID_AI_HIGH_PHYSICAL_ATTACK).random() }

    fun friendlyAIRecruitment() : Int { return FRIENDLY_AI_RECRUITMENT }

    fun friendlyAIStatChange() : Int { return FRIENDLY_AI_STAT_CHANGE }

    fun friendlyAIEvolutionChoice() : Int { return FRIENDLY_AI_EVOLUTION_CHOICE }
    fun friendlyAIEvolutionNoChoice() : Int { return FRIENDLY_AI_EVOLUTION_NO_CHOICE }

    fun dailyFriendlyAIProgress(programmers : Int) : Int {
        val doubleValue : Double = (programmers/50).toDouble()
        return doubleValue.roundToInt()
    }

    fun testEventWeights() {
        randomEventWeight = 10; playerAttackWeight = 10; gridLowAttackWeight = 10; gridHighAttackWeight = 10; playerRecruitmentWeight = 10; friendlyAIRecruitmentWeight = 10; friendlyAIStatChangeWeight = 10; friendlyAIEvolutionLevelProgressRepairWeight = 10; gridAIIntegrityRepairWeight = 10
    }
}