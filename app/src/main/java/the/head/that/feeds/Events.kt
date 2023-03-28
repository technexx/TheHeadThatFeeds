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
    val RANDOM_MIXED = 13

    val PLAYER_LOW_ATTACK = 21
    val PLAYER_MEDIUM_ATTACK = 22
    val PLAYER_HIGH_ATTACK = 23
    val FRIENDLY_AI_LOW_ATTACK = 24
    val FRIENDLY_AI_MEDIUM_ATTACK = 25
    val FRIENDLY_AI_HIGH_ATTACK = 26

    val GRID_AI_LOW_NETWORK_ATTACK = 31
    val GRID_AI_LOW_PHYSICAL_ATTACK = 32
    val GRID_AI_HIGH_NETWORK_ATTACK = 33
    val GRID_AI_HIGH_PHYSICAL_ATTACK = 34

    val PLAYER_RECRUITMENT = 41
    val FRIENDLY_AI_RECRUITMENT = 42

    val FRIENDLY_AI_INTEGRITY_REPAIR = 51
    val GRID_AI_INTEGRITY_REPAIR = 52

    val FRIENDLY_AI_STAT_CHANGE = 61

    val FRIENDLY_AI_EVOLUTION_CHOICE = 71
    val FRIENDLY_AI_EVOLUTION_NO_CHOICE = 72

    var randomEventWeight = 0
    var playerAttackWeight = 0
    var gridAttackWeight = 0
    var playerRecruitmentWeight = 0
    var friendlyAIRecruitmentWeight = 0
    var friendlyAIStatChangeWeight = 0
    var friendlyAIEvolutionLevelProgressRepairWeight = 0
    var gridAIIntegrityRepairWeight = 0

    val randomGoodEventArray = mContext.resources.getStringArray(R.array.random_good_event_array)
    val randomBadEventArray = mContext.resources.getStringArray(R.array.random_bad_event_array)
    val randomMixedEventArray = mContext.resources.getStringArray(R.array.random_mixed_event_array)

    val enemyTypesArray = mContext.resources.getStringArray(R.array.enemy_types)
    val destructionVerbsArray = mContext.resources.getStringArray(R.array.destruction_synonyms)
    val gridAIMalwareArray = mContext.resources.getStringArray(R.array.enemy_ai_malware)
    val proAggressionArray = mContext.resources.getStringArray(R.array.pro_friendlyAIAggression_triggers)
    val antiAggressionArray = mContext.resources.getStringArray(R.array.anti_friendlyAIAggression_triggers)
    val proEmpathyArray = mContext.resources.getStringArray(R.array.pro_friendlyAIEmpathy_triggers)
    val antiEmpathyArray = mContext.resources.getStringArray(R.array.anti_friendlyAIEmpathy_triggers)

    var pastEventsArray = ArrayList<Int>()

    fun eventString(eventType: Int) : String {
        when (eventType) {
            RANDOM_GOOD -> return (randomStringFromArray(R.array.random_good_event_array))
            RANDOM_BAD -> return (randomStringFromArray(R.array.random_bad_event_array))
            RANDOM_MIXED -> return (randomStringFromArray(R.array.random_mixed_event_array))

            PLAYER_LOW_ATTACK -> return mContext.getString(R.string.player_attack_low_risk)
            PLAYER_MEDIUM_ATTACK -> return mContext.getString(R.string.player_attack_medium_risk)
            PLAYER_HIGH_ATTACK -> return mContext.getString(R.string.player_attack_high_risk)
            FRIENDLY_AI_LOW_ATTACK -> return mContext.getString(R.string.friendly_ai_attack_low_risk)
            FRIENDLY_AI_MEDIUM_ATTACK -> return mContext.getString(R.string.friendly_ai_attack_medium_risk)
            FRIENDLY_AI_HIGH_ATTACK -> return mContext.getString(R.string.friendly_ai_attack_high_risk)

            GRID_AI_LOW_NETWORK_ATTACK -> return mContext.getString(R.string.grid_ai_network_attack_low_threat,  randomStringFromArray(R.array.enemy_types), randomStringFromArray(R.array.enemy_ai_malware))
            GRID_AI_LOW_PHYSICAL_ATTACK -> return mContext.getString(R.string.grid_ai_physical_attack_low_threat, randomStringFromArray(R.array.enemy_types), randomStringFromArray(R.array.destruction_synonyms))
            GRID_AI_HIGH_NETWORK_ATTACK -> return mContext.getString(R.string.grid_ai_network_attack_high_threat, randomStringFromArray(R.array.enemy_types))
            GRID_AI_HIGH_PHYSICAL_ATTACK -> return mContext.getString(R.string.grid_ai_physical_attack_high_threat, randomStringFromArray(R.array.enemy_types), randomStringFromArray(R.array.destruction_synonyms))

            PLAYER_RECRUITMENT -> return mContext.getString(R.string.physical_recruitment)
            FRIENDLY_AI_RECRUITMENT -> return mContext.getString(R.string.friendly_ai_recruitment)

            FRIENDLY_AI_INTEGRITY_REPAIR -> return mContext.getString(R.string.friendly_ai_integrity_repair)
            GRID_AI_INTEGRITY_REPAIR -> return mContext.getString(R.string.grid_ai_integrity_repair)

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
        setGridAIAttackWeight(trackingLevel)
        setResistanceRecruitmentWeight(fighters, programmers)
        setFriendlyAIRecruitmentWeight()
        setFriendlyAIStatChangeWeight()
        setFriendlyAIEvolutionProgressWeight()
        setGridAIEvolutionProgressWeight()
    }

    private fun totalEventsWeight() : Int {
        return randomEventWeight + playerAttackWeight + gridAttackWeight + playerRecruitmentWeight + friendlyAIRecruitmentWeight + friendlyAIStatChangeWeight + friendlyAIEvolutionLevelProgressRepairWeight + gridAIIntegrityRepairWeight
    }

    private fun eventsAsIntArray() : ArrayList<Int> {
        return ArrayList(listOf(randomEvent(), playerAttack(), gridAIAttack(), playerRecruitment(), friendlyAIRecruitment(), friendlyAIStatChange(), playerIntegrityRepair(), gridAIIntegrityRepair()))
    }

    private fun eventsWeightArray() : ArrayList<Int> {
        return ArrayList(listOf(randomEventWeight, playerAttackWeight, gridAttackWeight, playerRecruitmentWeight, friendlyAIRecruitmentWeight, friendlyAIStatChangeWeight, friendlyAIEvolutionLevelProgressRepairWeight, gridAIIntegrityRepairWeight))
    }

    private fun addEventToPastEventsList(event: Int) { pastEventsArray.add(event) }

    fun setRandomEventWeight() { randomEventWeight = 50 }

    fun setPlayerAttackWeight() { playerAttackWeight = 20 }

    fun setGridAIAttackWeight(trackingLevel: Int) { gridAttackWeight = (trackingLevel * 1.2).roundToInt() }

    fun setResistanceRecruitmentWeight(fighters: Int, programmers: Int) { playerRecruitmentWeight = ((fighters + programmers) / 100) }

    fun setFriendlyAIRecruitmentWeight() { friendlyAIRecruitmentWeight = 5 }

    fun setFriendlyAIStatChangeWeight() { friendlyAIStatChangeWeight = 5 }

    fun setFriendlyAIEvolutionProgressWeight() { friendlyAIEvolutionLevelProgressRepairWeight = 10 }

    fun setGridAIEvolutionProgressWeight() { gridAIIntegrityRepairWeight = 10 }

    fun randomEvent() : Int { return (RANDOM_GOOD..RANDOM_MIXED).random() }

    fun playerAttack() : Int { return (PLAYER_LOW_ATTACK..PLAYER_HIGH_ATTACK).random() }
    fun gridAIAttack() : Int { return (GRID_AI_LOW_NETWORK_ATTACK..GRID_AI_HIGH_PHYSICAL_ATTACK).random() }

    fun playerRecruitment() : Int { return PLAYER_RECRUITMENT }
    fun friendlyAIRecruitment() : Int { return FRIENDLY_AI_RECRUITMENT }

    fun friendlyAIStatChange() : Int { return FRIENDLY_AI_STAT_CHANGE }

    fun playerIntegrityRepair() : Int { return FRIENDLY_AI_INTEGRITY_REPAIR }
    fun gridAIIntegrityRepair() : Int { return GRID_AI_INTEGRITY_REPAIR }

    fun friendlyAIEvolutionChoice() : Int { return FRIENDLY_AI_EVOLUTION_CHOICE }
    fun friendlyAIEvolutionNoChoice() : Int { return FRIENDLY_AI_EVOLUTION_NO_CHOICE }

    fun dailyFriendlyAIProgress(programmers : Int) : Int {
        val doubleValue : Double = (programmers/50).toDouble()
        return doubleValue.roundToInt()
    }

    fun testEventWeights() {
        randomEventWeight = 10; playerAttackWeight = 10; gridAttackWeight = 10; playerRecruitmentWeight = 10; friendlyAIRecruitmentWeight = 10; friendlyAIStatChangeWeight = 10; friendlyAIEvolutionLevelProgressRepairWeight = 10; gridAIIntegrityRepairWeight = 10
    }
}