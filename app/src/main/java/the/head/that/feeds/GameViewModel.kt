package the.head.that.feeds

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class GameViewModel : ViewModel() {
    var friendlyAILevelProgress = 0
    var enemyAILevelProgress = 0
    var enemyActivityMultiplier = 0

    val mutableFriendlyAIEvolutionLevel = MutableLiveData<Int>()
    val friendlyAIEvolutionLevel : LiveData<Int> get() = mutableFriendlyAIEvolutionLevel
    fun setFriendlyAIEvolutionLevel(value: Int) { mutableFriendlyAIEvolutionLevel.value = value }
    fun getFriendlyAIEvolutionLevel() : Int { return friendlyAIEvolutionLevel.value!! }

    val mutableFriendlyAIIntegrityLevel = MutableLiveData<Double>()
    val friendlyAIIntegrityLevel : LiveData<Double> get() = mutableFriendlyAIIntegrityLevel
    fun setFriendlyAIIntegrity(value: Double) { mutableFriendlyAIIntegrityLevel.value = value }
    fun getFriendlyAIIntegrity() : Double { return friendlyAIIntegrityLevel.value!! }

    val mutableGridAIActionLevel = MutableLiveData<Int>()
    val gridAIActionLevel : LiveData<Int> get() = mutableGridAIActionLevel
    fun setGridAILevel(value: Int) { mutableGridAIActionLevel.value = value }
    fun getGridAILevel() : Int { return gridAIActionLevel.value!! }

    val mutableGridAIIntegrityLevel = MutableLiveData<Double>()
    val gridAIIntegrityLevel : LiveData<Double> get() = mutableGridAIIntegrityLevel
    fun setGridAIIntegrity(value: Double) { mutableGridAIIntegrityLevel.value = value }
    fun getGridAIIntegrity() : Double { return gridAIIntegrityLevel.value!! }

    val mutableGridAITrackingProgress= MutableLiveData<Int>()
    val gridAITrackingProgress : LiveData<Int> get() = mutableGridAITrackingProgress
    fun setGridAITrackingProgress(value: Int) { mutableGridAITrackingProgress.value = value}
    fun getGridAITrackingProgress() : Int { return gridAITrackingProgress.value!!}

    val mutableAggression = MutableLiveData<Int>()
    val aggression : LiveData<Int> get() = mutableAggression
    fun setAggression(value: Int) { mutableAggression.value = value }
    fun getAggression() : Int { return aggression.value!! }

    val mutableEmpathy = MutableLiveData<Int>()
    val empathy : LiveData<Int> get() = mutableEmpathy
    fun setEmpathy(value: Int) { mutableEmpathy.value = value }
    fun getEmpathy() : Int { return empathy.value!! }

    val mutableFighters = MutableLiveData<Int>()
    val fighters : LiveData<Int> get() = mutableFighters
    fun setFighters(value: Int) { mutableFighters.value = value }
    fun getFighters() : Int { return fighters.value!! }

    val mutableProgrammers = MutableLiveData<Int>()
    val programmers : LiveData<Int> get() = mutableProgrammers
    fun setProgrammers(value: Int) { mutableProgrammers.value = value }
    fun getProgrammers() : Int { return programmers.value!! }

    val mutableCivilians = MutableLiveData<Double>()
    val civilians : LiveData<Double> get() = mutableCivilians
    fun setCivilians (value: Double) { mutableCivilians.value = value }
    fun getCivilians() : Double { return civilians.value!! }

    val mutableCurrentDay = MutableLiveData<Int>()
    val currentDay : LiveData<Int> get() = mutableCurrentDay
    fun setCurrentDay(value: Int) { mutableCurrentDay.value = value }
    fun getCurrentDay() : Int { return currentDay.value!! }
}