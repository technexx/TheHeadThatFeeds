package the.head.that.feeds

import android.content.Context
import android.util.Log
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.roundToInt

class Events(context : Context) {
    private val mContext = context
    var rolledEventInteger = 0

    val EVENT_WTH_CHOICE = 11
    val RANDOM_GOOD = 12
    val RANDOM_MIXED = 13
    val RANDOM_BAD = 14

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

    val FRIENDLY_AI_EVOLUTION_PROGRESS = 81
    val FRIENDLY_AI_EVOLUTION_CHOICE = 82
    val FRIENDLY_AI_EVOLUTION_NO_CHOICE = 83

    var eventWithChoiceWeight = 0
    var goodOrBadEventWeight = 0
    var playerAttackWeight = 0
    var gridLowAttackWeight = 0
    var gridHighAttackWeight = 0
    var playerRecruitmentWeight = 0
    var friendlyAIRecruitmentWeight = 0
    var friendlyAIStatChangeWeight = 0
    var friendlyAIEvolutionLevelProgressWeight = 0

    var pastEventsArray = ArrayList<Int>()

    fun randomStringFromArray(array: Int) : String {
        val fetchedArray = mContext.resources.getStringArray(array)
        return fetchedArray[(fetchedArray.indices).random()].toString()
    }

    fun setRolledEventInteger(trackingLevel: Int, fighters: Int, programmers: Int) {
        setAllEventWeights(trackingLevel, fighters, programmers)

        val totalEventsWeight = totalEventsWeight()
        val roll = (0..totalEventsWeight).random()

        Log.i("testRoll", "roll is $roll")
        Log.i("testRoll", "event weight is $totalEventsWeight")

        val eventsWeightArray = eventsWeightArray()
        val eventsAsIntArray = eventsAsIntArray()

        var valueToRollAgainst = 0

        for (i in eventsWeightArray.indices) {
            valueToRollAgainst += eventsWeightArray[i]
            Log.i("testRoll", "value to roll against is $valueToRollAgainst")

            if (valueToRollAgainst >= roll) {
                addEventToPastEventsList(eventsAsIntArray[i])
                 rolledEventInteger = eventsAsIntArray[i]
                return
            }
        }
    }

    private fun setAllEventWeights(trackingLevel: Int, fighters: Int, programmers: Int) {
        setEventWithChoiceWeight()
        setGoodOrBadEventWeight()
        setPlayerAttackWeight()
        setGridAILowAttackWeight(trackingLevel)
        setGridAIHighAttackWeight(trackingLevel)
        setResistanceRecruitmentWeight(fighters, programmers)
        setFriendlyAIRecruitmentWeight()
        setFriendlyAIStatChangeWeight()
        setFriendlyAIEvolutionProgressWeight()
    }

    private fun totalEventsWeight() : Int {
        return eventWithChoiceWeight + goodOrBadEventWeight + playerAttackWeight + gridLowAttackWeight + gridHighAttackWeight + friendlyAIRecruitmentWeight + friendlyAIStatChangeWeight + friendlyAIEvolutionLevelProgressWeight
    }

    private fun eventsAsIntArray() : ArrayList<Int> {
        return ArrayList(listOf(eventWithChoice(), randomEvent(), playerAttack(), gridAIAttackLowDamage(), gridAIAttackHighDamage(), friendlyAIRecruitment(), friendlyAIStatChange(), friendlyAIEvolutionProgress()))
    }

    private fun eventsWeightArray() : ArrayList<Int> {
        return ArrayList(listOf(eventWithChoiceWeight, goodOrBadEventWeight, playerAttackWeight, gridLowAttackWeight, gridHighAttackWeight, friendlyAIRecruitmentWeight, friendlyAIStatChangeWeight, friendlyAIEvolutionLevelProgressWeight))
    }

    private fun addEventToPastEventsList(event: Int) { pastEventsArray.add(event) }

    fun setEventWithChoiceWeight() { eventWithChoiceWeight = 50 }

    fun setGoodOrBadEventWeight() { goodOrBadEventWeight = 20 }

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

    fun setFriendlyAIEvolutionProgressWeight() { friendlyAIEvolutionLevelProgressWeight = 10 }

    //////////////////////////////////////////////////////////////////////////////////////

    fun eventWithChoice() : Int { return EVENT_WTH_CHOICE }

    fun randomEvent() : Int { return (RANDOM_GOOD..RANDOM_BAD).random() }

    fun playerAttack() : Int { return (PLAYER_NETWORK_ATTACK..PLAYER_MILITARY_ATTACK).random() }

    fun gridAIAttackLowDamage() : Int { return (GRID_AI_LOW_NETWORK_ATTACK..GRID_AI_LOW_PHYSICAL_ATTACK).random() }

    fun gridAIAttackHighDamage() : Int { return (GRID_AI_LOW_PHYSICAL_ATTACK..GRID_AI_HIGH_PHYSICAL_ATTACK).random() }

    fun friendlyAIRecruitment() : Int { return FRIENDLY_AI_RECRUITMENT }

    fun friendlyAIStatChange() : Int { return FRIENDLY_AI_STAT_CHANGE }

    fun friendlyAIEvolutionProgress() : Int { return FRIENDLY_AI_EVOLUTION_PROGRESS }

    fun friendlyAIEvolutionChoice() : Int { return FRIENDLY_AI_EVOLUTION_CHOICE }
    fun friendlyAIEvolutionNoChoice() : Int { return FRIENDLY_AI_EVOLUTION_NO_CHOICE }

    fun eventString(eventType: Int) : String {
        if (eventType == EVENT_WTH_CHOICE) {
            val array = mContext.resources.getStringArray(R.array.events_with_choice)
            return array[array.indices.random()]
        }

        if (eventType == RANDOM_GOOD || eventType == RANDOM_BAD) {
            var array = emptyArray<String>()
            val
            if (eventType == RANDOM_GOOD) array = mContext.resources.getStringArray(R.array.good_event_array)
            if (eventType == RANDOM_BAD) array = mContext.resources.getStringArray(R.array.bad_event_array)

            val index = array.indices.random()
            val valueChangeRoll = (20..60).random()

            if (index == 0 || index == 1) return mContext.getString(array[index].toInt(), valueChangeRoll.toString())
            else return array[index]
        }

        if (eventType == RANDOM_MIXED) {
            val array = mContext.resources.getStringArray(R.array.mixed_event_array)
            val index = array.indices.random()

            val specifierRoll = (0..9).random()
            var specifierRollString = ""

            if (index == 0) { specifierRollString = mContext.resources.getStringArray(R.array.pro_friendly_AI_aggression_triggers)[specifierRoll]
            }
            if (index == 1) { specifierRollString = mContext.resources.getStringArray(R.array.anti_friendly_AI_aggression_triggers)[specifierRoll]
            }
            if (index == 2) { specifierRollString = mContext.resources.getStringArray(R.array.pro_friendly_AI_empathy_triggers)[specifierRoll]
            }
            if (index == 3) { specifierRollString = mContext.resources.getStringArray(R.array.anti_friendly_AI_empathy_triggers)[specifierRoll]
            }
            return mContext.getString(array[index].toInt(), specifierRollString)
        }

        when (eventType) {
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

            FRIENDLY_AI_EVOLUTION_PROGRESS -> return mContext.getString(R.string.friendly_ai_evolution_progress)
            FRIENDLY_AI_EVOLUTION_CHOICE -> return mContext.getString(R.string.friendly_ai_evolution_with_choice)
            FRIENDLY_AI_EVOLUTION_NO_CHOICE -> return mContext.getString(R.string.friendly_ai_evolution_no_choice)

            else -> return ""
        }
    }

    fun firstButtonString(eventType: Int) : String {
        when (eventType) {
            EVENT_WTH_CHOICE -> return mContext.getString(R.string.yes)
            FRIENDLY_AI_DIRECTED_NETWORK_ATTACK -> return mContext.getString(R.string.friendly_ai_directed_network_attack_choice_one)
            FRIENDLY_AI_DIRECTED_MILITARY_ATTACK -> return mContext.getString(R.string.friendly_ai_directed_military_attack_choice_one)
            FRIENDLY_AI_AUTONOMOUS_NETWORK_ATTACK, FRIENDLY_AI_AUTONOMOUS_MILITARY_ATTACK -> return mContext.getString(R.string.yes)
            PLAYER_NETWORK_ATTACK, PLAYER_MILITARY_ATTACK -> return mContext.getString(R.string.yes)
            else -> return ""
        }
    }

    fun secondButtonString(eventType: Int) : String {
        return when (eventType) {
            EVENT_WTH_CHOICE -> return mContext.getString(R.string.no)
            FRIENDLY_AI_DIRECTED_NETWORK_ATTACK -> mContext.getString(R.string.friendly_ai_directed_network_attack_choice_two)
            FRIENDLY_AI_DIRECTED_MILITARY_ATTACK -> mContext.getString(R.string.friendly_ai_directed_military_attack_choice_two)
            FRIENDLY_AI_AUTONOMOUS_NETWORK_ATTACK, FRIENDLY_AI_AUTONOMOUS_MILITARY_ATTACK -> mContext.getString(R.string.no)
            PLAYER_NETWORK_ATTACK, PLAYER_MILITARY_ATTACK -> mContext.getString(R.string.no)
            else -> ""
        }
    }

    fun thirdButtonString(eventType: Int) : String {
        return when (eventType) {
            FRIENDLY_AI_DIRECTED_NETWORK_ATTACK -> mContext.getString(R.string.friendly_ai_directed_network_attack_choice_three)
            FRIENDLY_AI_DIRECTED_MILITARY_ATTACK -> mContext.getString(R.string.friendly_ai_directed_military_attack_choice_three)
            else -> ""
        }
    }

    fun dailyFriendlyAIProgress(programmers : Int) : Int {
        val doubleValue : Double = (programmers/50).toDouble()
        return doubleValue.roundToInt()
    }

    fun testEventWeights() {
        eventWithChoiceWeight = 10; goodOrBadEventWeight = 10; playerAttackWeight = 10; gridLowAttackWeight = 10; gridHighAttackWeight = 10; playerRecruitmentWeight = 10; friendlyAIRecruitmentWeight = 10; friendlyAIStatChangeWeight = 10; friendlyAIEvolutionLevelProgressWeight = 10;
    }
}