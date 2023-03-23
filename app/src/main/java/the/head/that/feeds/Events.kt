package the.head.that.feeds

import android.content.Context
import android.util.Log
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.roundToInt

//Choice Events: Player attacks, grid AI attack, recruitment, friendly AI action, friendly AI evolution
class Events(context : Context) {
    private val mContext = context

    var TYPE_OF_EVENT = 0
    var pastEventsArray = ArrayList<Int>()

    val RANDOM_GOOD = 11
    val RANDOM_BAD = 12
    val RANDOM_MIXED = 13

    val RESISTANCE_LOW_ATTACK = 21
    val RESISTANCE_MEDIUM_ATTACK = 22
    val RESISTANCE_HIGH_ATTACK = 23
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

    var totalEventsWeight = 0

    var randomEventWeight = 0
    var playerAttackWeight = 0
    var gridAttackWeight = 0
    var resistanceRecruitmentWeight = 0
    var friendlyAIRecruitmentWeight = 0
    var friendlyAIStatChangeWeight = 0
    var friendlyAIIntegrityRepairWeight = 0
    var gridAIIntegrityRepairWeight = 0

    fun rollTypeOfEvent() : Int {
        setAllEventWeights()
        setTotalEventWeightVariable()

        val roll = (0..totalEventsWeight).random()
        var valueToReturn = 0

        //Todo: totalEventsWeight needs to decrease.
        when (roll) {
            in (0..(totalEventsWeight - (totalEventsWeight - randomEventWeight))) -> valueToReturn =  randomEvent()
            in (0..(totalEventsWeight - (totalEventsWeight - playerAttackWeight))) -> valueToReturn =  playerAttack()
            in (0..(totalEventsWeight - (totalEventsWeight - gridAttackWeight))) -> valueToReturn =  gridAIAttack()
            in (0..(totalEventsWeight - (totalEventsWeight - resistanceRecruitmentWeight))) -> valueToReturn =  playerRecruitment()
            in (0..(totalEventsWeight - (totalEventsWeight - friendlyAIRecruitmentWeight))) -> valueToReturn = friendlyAIRecruitment()
            in (0..(totalEventsWeight - (totalEventsWeight - friendlyAIStatChangeWeight))) -> valueToReturn =  friendlyAIStatChange()
            in (0..(totalEventsWeight - (totalEventsWeight - friendlyAIIntegrityRepairWeight))) -> valueToReturn =  playerIntegrityRepair()
            in (0..(totalEventsWeight - (totalEventsWeight - gridAIIntegrityRepairWeight))) -> valueToReturn =  gridAIIntegrityRepair()
        }

        Log.i("testRoll", "roll var is $roll")
        Log.i("testRoll", "event return is $valueToReturn")
        return valueToReturn
    }

    fun rolledEvent() : Int {
        setAllEventWeights()
        setTotalEventWeightVariable()

        val eventsWeightArray = eventsWeightArray()
        val eventsAsIntArray = eventsAsIntArray()
        var eventsWeight = totalEventsWeight
        val roll = (0..eventsWeight).random()
        Log.i("testRoll", "roll var is $roll")

        var valueToReturn = 0

        for (i in eventsWeightArray.indices) {
            val singleEventWeight = (eventsWeight - (eventsWeight - eventsWeightArray[i]))

            if (roll >= (singleEventWeight)) valueToReturn = eventsAsIntArray[i]
            Log.i("testRoll", "eventsWeight is $eventsWeight")
            Log.i("testRoll", "single event weight is $singleEventWeight")
            eventsWeight -= singleEventWeight
        }

        Log.i("testRoll", "event return is $valueToReturn")

        return valueToReturn
    }

    private fun setTotalEventWeightVariable() {
        totalEventsWeight = randomEventWeight + playerAttackWeight + gridAttackWeight + resistanceRecruitmentWeight + friendlyAIRecruitmentWeight + friendlyAIStatChangeWeight + friendlyAIIntegrityRepairWeight + gridAIIntegrityRepairWeight

//        Log.i("testRoll", "randomEvent is $randomEventWeight, playerAttack is $playerAttackWeight, gridAttack is $gridAttackWeight, resistanceRecruitment is $resistanceRecruitmentWeight, friendlyAiRecruitment is $friendlyAIRecruitmentWeight, friendlyStatChange is $friendlyAIStatChangeWeight, friendlyIntegrityRepair is $friendlyAIIntegrityRepairWeight, gridAiIntegrityRepair is $gridAIIntegrityRepairWeight")

//        Log.i("testRoll", "event roll is $totalEventsWeight")
    }

    private fun eventsAsIntArray() : ArrayList<Int> {
        return ArrayList(listOf(randomEvent(), playerAttack(), gridAIAttack(), playerRecruitment(), friendlyAIRecruitment(), friendlyAIStatChange(), playerIntegrityRepair(), gridAIIntegrityRepair()))
    }

    private fun eventsWeightArray() : ArrayList<Int> {
        return ArrayList(listOf(randomEventWeight, playerAttackWeight, gridAttackWeight, resistanceRecruitmentWeight, friendlyAIRecruitmentWeight, friendlyAIStatChangeWeight, friendlyAIIntegrityRepairWeight, gridAIIntegrityRepairWeight))
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

    fun setRandomEventWeight() { randomEventWeight = 50 }

    fun setPlayerAttackWeight() { playerAttackWeight = 20 }

    fun setGridAIAttackWeight(trackingLevel: Int) { gridAttackWeight = (trackingLevel * 1.2).roundToInt() }

    fun setResistanceRecruitmentWeight(fighters: Int, programmers: Int) {
        resistanceRecruitmentWeight = ((fighters + programmers) / 100) }

    fun setFriendlyAIRecruitmentWeight() { friendlyAIRecruitmentWeight = 5 }

    fun setFriendlyAIStatChangeWeight() { friendlyAIStatChangeWeight = 5 }

    fun setFriendlyAIIntegrityRepair(currentIntegrity: Int) {
        friendlyAIIntegrityRepairWeight = ((100 - currentIntegrity) * 0.25).roundToInt() }

    fun setGridAIIntegrityRepair(currentIntegrity: Int) {
        gridAIIntegrityRepairWeight= ((100 - currentIntegrity) * 0.5).roundToInt() }

    private fun addEventToPastEventsList(event: Int) { pastEventsArray.add(event) }

    fun randomEvent() : Int { return (RANDOM_GOOD..RANDOM_MIXED).random() }

    fun playerAttack() : Int { return (RESISTANCE_LOW_ATTACK..FRIENDLY_AI_HIGH_ATTACK).random() }
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
}