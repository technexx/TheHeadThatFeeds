package the.head.that.feeds

import android.app.AppComponentFactory
import android.content.Context
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.roundToInt

class Events(context : Context) : ComponentActivity() {
    val gameViewModel : GameViewModel by viewModels()

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

    val GRID_AI_PHYSICAL_ATTACK = 51
    val GRID_AI_NETWORK_ATTACK = 52

    val FRIENDLY_AI_RECRUITMENT = 61
    
    val FRIENDLY_AI_STAT_CHANGE = 71

    val FRIENDLY_AI_EVOLUTION_PROGRESS = 81
    val FRIENDLY_AI_EVOLUTION_CHOICE = 82
    val FRIENDLY_AI_EVOLUTION_NO_CHOICE = 83

    var eventWithChoiceWeight = 0
    var goodEventWeight = 0
    var badEventWeight = 0
    var mixedEventWeight = 0
    var playerMilitaryAttackWeight = 0
    var playerNetworkAttackWeight = 0
    var friendlyAIDirectedNetworkAttackWeight = 0
    var friendlyAIDirectedMilitaryAttackWeight = 0
    var friendlyAIAutonomousNetworkAttackWeight = 0
    var friendlyAIAutonomousMilitaryAttackWeight = 0
    var gridAIPhysicalAttackWeight = 0
    var gridAINetworkAttackWeight = 0

    var playerRecruitmentWeight = 0
    var friendlyAIRecruitmentWeight = 0
    var friendlyAIStatChangeWeight = 0
    var friendlyAIEvolutionLevelProgressWeight = 0

    var pastEventsArray = ArrayList<Int>()

    var gridAINetworkAttackLevel = 0
    var gridAIMilitaryAttackLevel = 0
    var gridAINetworkAIDefenseLevel = 0
    var gridAIMilitaryDefenseLevel = 0

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
        assignEventWithChoiceWeight()
        assignGoodEventWeight()
        assignBadEventWeight()
        assignMixedEventWeight()
        assignPlayerMilitaryAttackWeight()
        assignPlayerNetworkAttackWeight()
        assignGridAIPhysicalAttackWeight(trackingLevel)
        assignGridAINetworkAttackWeight(trackingLevel)
        assignResistanceRecruitmentWeight(fighters, programmers)
        assignFriendlyAIRecruitmentWeight()
        assignFriendlyAIStatChangeWeight()
        assignFriendlyAIEvolutionProgressWeight()
    }

    private fun eventsAsIntArray() : ArrayList<Int> {
        return ArrayList(listOf(EVENT_WTH_CHOICE, RANDOM_GOOD, RANDOM_BAD, RANDOM_MIXED, PLAYER_NETWORK_ATTACK, PLAYER_MILITARY_ATTACK, FRIENDLY_AI_DIRECTED_NETWORK_ATTACK, FRIENDLY_AI_DIRECTED_MILITARY_ATTACK, FRIENDLY_AI_AUTONOMOUS_NETWORK_ATTACK, FRIENDLY_AI_AUTONOMOUS_MILITARY_ATTACK,  GRID_AI_PHYSICAL_ATTACK, GRID_AI_NETWORK_ATTACK, FRIENDLY_AI_RECRUITMENT, FRIENDLY_AI_STAT_CHANGE, FRIENDLY_AI_EVOLUTION_PROGRESS))
    }

    private fun totalEventsWeight() : Int {
        return eventWithChoiceWeight + goodEventWeight + badEventWeight + mixedEventWeight + playerNetworkAttackWeight + playerMilitaryAttackWeight + friendlyAIDirectedNetworkAttackWeight + friendlyAIDirectedMilitaryAttackWeight + friendlyAIAutonomousNetworkAttackWeight + friendlyAIAutonomousMilitaryAttackWeight + gridAINetworkAttackWeight + gridAIPhysicalAttackWeight + friendlyAIRecruitmentWeight + friendlyAIStatChangeWeight + friendlyAIEvolutionLevelProgressWeight
    }

    private fun eventsWeightArray() : ArrayList<Int> {
        return ArrayList(listOf(eventWithChoiceWeight, goodEventWeight, badEventWeight, mixedEventWeight, playerMilitaryAttackWeight, playerNetworkAttackWeight, friendlyAIDirectedNetworkAttackWeight, friendlyAIDirectedMilitaryAttackWeight, friendlyAIAutonomousNetworkAttackWeight, friendlyAIAutonomousMilitaryAttackWeight, gridAIPhysicalAttackWeight, gridAINetworkAttackWeight, friendlyAIRecruitmentWeight, friendlyAIStatChangeWeight, friendlyAIEvolutionLevelProgressWeight))
    }

    private fun addEventToPastEventsList(event: Int) { pastEventsArray.add(event) }

    fun assignEventWithChoiceWeight() { eventWithChoiceWeight = 50 }

    fun assignGoodEventWeight() { goodEventWeight = 20 }
    
    fun assignBadEventWeight() { badEventWeight = 20 }
    
    fun assignMixedEventWeight() { mixedEventWeight = 20 }

    fun assignPlayerMilitaryAttackWeight() { playerMilitaryAttackWeight = 20 }

    fun assignPlayerNetworkAttackWeight() { playerNetworkAttackWeight = 20 }

    fun assignFriendlyAIDirectedNetworkAttackWeight() { friendlyAIDirectedNetworkAttackWeight = 20}

    fun assignFriendlyAIDirectedMilitaryAttackWeight() { friendlyAIDirectedMilitaryAttackWeight = 20 }

    fun assignFriendlyAIAutonomousNetworkAttackWeight(aggression: Int) { friendlyAIAutonomousNetworkAttackWeight = (aggression * .7).roundToInt()}

    fun assignFriendlyAIAutonomousMilitaryAttackWeight(aggression: Int) { friendlyAIAutonomousMilitaryAttackWeight = (aggression * .7).roundToInt()}

    fun assignGridAIPhysicalAttackWeight(trackingLevel: Int) { gridAIPhysicalAttackWeight = (trackingLevel * 1.2).roundToInt() }

    fun assignGridAINetworkAttackWeight(trackingLevel: Int) { gridAINetworkAttackWeight = (trackingLevel * 1.2).roundToInt()}

    fun assignResistanceRecruitmentWeight(fighters: Int, programmers: Int) { playerRecruitmentWeight = ((fighters + programmers) / 100) }

    fun assignFriendlyAIRecruitmentWeight() { friendlyAIRecruitmentWeight = 5 }

    fun assignFriendlyAIStatChangeWeight() { friendlyAIStatChangeWeight = 5 }

    fun assignFriendlyAIEvolutionProgressWeight() { friendlyAIEvolutionLevelProgressWeight = 10 }

    fun eventString(eventType: Int) : String {
        if (eventType == EVENT_WTH_CHOICE) {
            val array = mContext.resources.getStringArray(R.array.events_with_choice)
            return array[array.indices.random()]
        }

        if (eventType == RANDOM_GOOD || eventType == RANDOM_BAD) {
            var array = emptyArray<String>()
            if (eventType == RANDOM_GOOD) array = mContext.resources.getStringArray(R.array.good_event_array)
            if (eventType == RANDOM_BAD) array = mContext.resources.getStringArray(R.array.bad_event_array)

            val index = array.indices.random()
            val valueChangeRoll = (20..60).random()

            if (index == 0 || index == 1) return String.format((array[index]), valueChangeRoll.toString())
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
            return String.format(array[index], specifierRollString)
        }

        if (eventType == PLAYER_NETWORK_ATTACK || eventType == FRIENDLY_AI_DIRECTED_NETWORK_ATTACK || eventType == FRIENDLY_AI_AUTONOMOUS_NETWORK_ATTACK) {
            assignLevelOfGridAINetworkDefense(gameViewModel.getGridAITrackingLevel())

            val defenseLevelString = gridAINetworkDefenseLevelString(gridAINetworkAIDefenseLevel)

            when (eventType) {
                PLAYER_NETWORK_ATTACK -> return mContext.getString(R.string.player_network_attack, defenseLevelString)
                FRIENDLY_AI_DIRECTED_NETWORK_ATTACK -> return mContext.getString(R.string.friendly_ai_directed_network_attack, defenseLevelString)
                FRIENDLY_AI_AUTONOMOUS_NETWORK_ATTACK -> return mContext.getString(R.string.friendly_ai_autonomous_network_attack, defenseLevelString)
            }
        }

        if (eventType == PLAYER_MILITARY_ATTACK || eventType == FRIENDLY_AI_DIRECTED_MILITARY_ATTACK || eventType == FRIENDLY_AI_AUTONOMOUS_MILITARY_ATTACK) {
            assignLevelOfGridAINetworkDefense(gameViewModel.getGridAITrackingLevel())

            val attackLevelString = gridAIMilitaryDefenseLevelString(gridAIMilitaryDefenseLevel)

            when (eventType) {
                PLAYER_MILITARY_ATTACK ->  return mContext.getString(R.string.player_military_attack, attackLevelString)
                FRIENDLY_AI_DIRECTED_MILITARY_ATTACK -> return mContext.getString(R.string.friendly_ai_directed_military_attack, attackLevelString)
                FRIENDLY_AI_AUTONOMOUS_MILITARY_ATTACK -> return mContext.getString(R.string.friendly_ai_autonomous_military_attack, attackLevelString)
            }
        }

        if (eventType == GRID_AI_NETWORK_ATTACK) {
            assignLevelOfGridAINetworkAttack(gameViewModel.getGridAITrackingLevel())

            return mContext.getString(R.string.grid_ai_network_attack,  randomStringFromArray(R.array.enemy_types), randomStringFromArray(R.array.enemy_ai_malware), gridAINetworkAttackLevelString(gridAINetworkAttackLevel))
        }

        if (eventType == GRID_AI_PHYSICAL_ATTACK) {
            return mContext.getString(R.string.grid_ai_physical_attack, randomStringFromArray(R.array.enemy_types), randomStringFromArray(R.array.destruction_synonyms), gridAIMilitaryAttackLevelString(gridAIMilitaryAttackLevel))
        }

        when (eventType) {
            FRIENDLY_AI_RECRUITMENT -> return mContext.getString(R.string.friendly_ai_recruitment)

            //Todo: Needs its placeholders set.
            FRIENDLY_AI_STAT_CHANGE -> return mContext.getString(R.string.friendly_ai_stat_altering_event)

            FRIENDLY_AI_EVOLUTION_PROGRESS -> return mContext.getString(R.string.friendly_ai_evolution_progress)
            FRIENDLY_AI_EVOLUTION_CHOICE -> return mContext.getString(R.string.friendly_ai_evolution_with_choice)
            FRIENDLY_AI_EVOLUTION_NO_CHOICE -> return mContext.getString(R.string.friendly_ai_evolution_no_choice)

            else -> return ""
        }
    }

    fun gridAINetworkDefenseLevelString(attackLevel: Int) : String {
        val array = mContext.resources.getStringArray(R.array.grid_ai_network_defense_level)
        return array[attackLevel]
    }

    fun gridAIMilitaryDefenseLevelString(attackLevel: Int) : String {
        val array = mContext.resources.getStringArray(R.array.grid_ai_military_defense_level)
        return array[attackLevel]
    }

    fun gridAINetworkAttackLevelString(attackLevel: Int) : String {
        val array = mContext.resources.getStringArray(R.array.grid_ai_network_attack_level)
        return array[attackLevel]
    }

    fun gridAIMilitaryAttackLevelString(attackLevel: Int) : String {
        val array = mContext.resources.getStringArray(R.array.grid_ai_physical_attack_level)
        return array[attackLevel]
    }

    fun assignLevelOfGridAINetworkDefense(trackingLevel: Int) {
        gridAINetworkAIDefenseLevel =  (trackingLevel + (-15..15).random()) / 10
    }

    fun assignLevelOfGridAIMilitaryDefense(trackingLevel: Int) {
        gridAIMilitaryDefenseLevel = (trackingLevel + (-15..15).random())  / 10
    }

    fun assignLevelOfGridAINetworkAttack(trackingLevel: Int) {
        gridAINetworkAttackLevel = (trackingLevel + (-15..15).random())  / 10
    }

    fun assignLevelOfGridAIPhysicalAttack(trackingLevel: Int) {
        gridAIMilitaryDefenseLevel = (trackingLevel + (-15..15).random())  / 10
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
}