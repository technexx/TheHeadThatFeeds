package the.head.that.feeds

import android.content.Context
import android.util.Log
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

    private fun percentageChanceBasedOnWeight(weight: Int) : Int {
        val weightAsDouble = weight.toDouble()
        val totalWeightAsDouble = totalEventsWeight.toDouble()
        return (weightAsDouble/totalWeightAsDouble).roundToInt()
    }

    fun rollTypeOfEvent() : Int {
        setAllEventWeights()
        setTotalEventWeightVariable()

        val roll = (0..totalEventsWeight).random()

        when (roll) {
            in (0..(totalEventsWeight - (totalEventsWeight - randomEventWeight))) -> return randomEvent()
            in (0..(totalEventsWeight - (totalEventsWeight - playerAttackWeight))) -> return playerAttack()
            in (0..(totalEventsWeight - (totalEventsWeight - gridAttackWeight))) -> return gridAIAttack()
            in (0..(totalEventsWeight - (totalEventsWeight - resistanceRecruitmentWeight))) -> return playerRecruitment()
            in (0..(totalEventsWeight - (totalEventsWeight - friendlyAIRecruitmentWeight))) -> return friendlyAIRecruitment()
            in (0..(totalEventsWeight - (totalEventsWeight - friendlyAIStatChangeWeight))) -> return friendlyAIStatChange()
            in (0..(totalEventsWeight - (totalEventsWeight - friendlyAIIntegrityRepairWeight))) -> return playerIntegrityRepair()
            in (0..(totalEventsWeight - (totalEventsWeight - gridAIIntegrityRepairWeight))) -> return gridAIIntegrityRepair()
            else -> return 0
        }
    }

    private fun setTotalEventWeightVariable() {
        totalEventsWeight = randomEventWeight + playerAttackWeight + gridAttackWeight + resistanceRecruitmentWeight + friendlyAIRecruitmentWeight + friendlyAIStatChangeWeight + friendlyAIIntegrityRepairWeight + gridAIIntegrityRepairWeight


        Log.i("testRoll", "randomEvent is $randomEventWeight, playerAttack is $playerAttackWeight, gridAttack is $gridAttackWeight, resistanceRecruitment is $resistanceRecruitmentWeight, friendlyAiRecruitment is $friendlyAIRecruitmentWeight, friendlyStatChange is $friendlyAIStatChangeWeight, friendlyIntegrityRepair is $friendlyAIIntegrityRepairWeight, gridAiIntegrityRepair is $gridAIIntegrityRepairWeight")

        Log.i("testRoll", "event roll is $totalEventsWeight")
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

    fun dailyFriendlyAIProgress(programmers : Int) : Int {
        val doubleValue : Double = (programmers/50).toDouble()
        return doubleValue.roundToInt()
    }
}