package the.head.that.feeds

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class GameViewModel : ViewModel() {
    val mutableFriendlyAIMood = MutableLiveData<Int>()
    val friendlyAIMood : LiveData<Int> get() = mutableFriendlyAIMood
    fun setFriendlyAIMood(value: Int) { mutableFriendlyAIMood.value = value }
    fun getFriendlyAIMood() : Int { return friendlyAIMood.value!! }

    val mutableFriendlyAIEvolutionLevel = MutableLiveData<Int>()
    val friendlyAIEvolutionLevel : LiveData<Int> get() = mutableFriendlyAIEvolutionLevel
    fun setFriendlyAIEvolutionLevel(value: Int) { mutableFriendlyAIEvolutionLevel.value = value }
    fun getFriendlyAIEvolutionLevel() : Int { return friendlyAIEvolutionLevel.value!! }

    val mutableFriendlyAIIntegrityLevel = MutableLiveData<Int>()
    val friendlyAIIntegrityLevel : LiveData<Int> get() = mutableFriendlyAIIntegrityLevel
    fun setFriendlyAIIntegrity(value: Int) { mutableFriendlyAIIntegrityLevel.value = value }
    fun getFriendlyAIIntegrity() : Int { return friendlyAIIntegrityLevel.value!! }

    val mutableFriendlyAIAggression = MutableLiveData<Int>()
    val friendlyAIAggression : LiveData<Int> get() = mutableFriendlyAIAggression
    fun setFriendlyAIAggression(value: Int) { mutableFriendlyAIAggression.value = value }
    fun getFriendlyAIAggression() : Int { return friendlyAIAggression.value!! }

    val friendlyAIMutableEmpathy = MutableLiveData<Int>()
    val friendlyAIEmpathy : LiveData<Int> get() = friendlyAIMutableEmpathy
    fun setFriendlyAIEmpathy(value: Int) { friendlyAIMutableEmpathy.value = value }
    fun getFriendlyAIEmpathy() : Int { return friendlyAIEmpathy.value!! }

    val mutableFighters = MutableLiveData<Int>()
    val fighters : LiveData<Int> get() = mutableFighters
    fun setFighters(value: Int) { mutableFighters.value = value }
    fun getFighters() : Int { return fighters.value!! }

    val mutableProgrammers = MutableLiveData<Int>()
    val programmers : LiveData<Int> get() = mutableProgrammers
    fun setProgrammers(value: Int) { mutableProgrammers.value = value }
    fun getProgrammers() : Int { return programmers.value!! }


    val mutableGridAITrackingLevel = MutableLiveData<Int>()
    val gridAITrackingLevel : LiveData<Int> get() = mutableGridAITrackingLevel
    fun setGridAITrackingLevel(value: Int) { mutableGridAITrackingLevel.value = value }
    fun getGridAITrackingLevel() : Int { return gridAITrackingLevel.value!! }

    val mutableGridAIEvolutionLevel = MutableLiveData<Int>()
    val gridAIEvolutionLevel : LiveData<Int> get() = mutableGridAIEvolutionLevel
    fun setGridAIEvolutionLevel(value: Int) { mutableGridAIEvolutionLevel.value = value }
    fun getGridAIEvolutionLevel() : Int { return gridAIEvolutionLevel.value!! }

    val mutableGridAIIntegrityLevel = MutableLiveData<Int>()
    val gridAIIntegrityLevel : LiveData<Int> get() = mutableGridAIIntegrityLevel
    fun setGridAIIntegrity(value: Int) { mutableGridAIIntegrityLevel.value = value }
    fun getGridAIIntegrity() : Int { return gridAIIntegrityLevel.value!! }

    val mutableGridAIAggression = MutableLiveData<Int>()
    val gridAIAggression : LiveData<Int> get() = mutableGridAIAggression
    fun setGridAIAggression(value: Int) { mutableGridAIAggression.value = value }
    fun getGridAIAggression() : Int { return gridAIAggression.value!! }

    val gridAIMutableEmpathy = MutableLiveData<Int>()
    val gridAIEmpathy : LiveData<Int> get() = gridAIMutableEmpathy
    fun setGridAIEmpathy(value: Int) { gridAIMutableEmpathy.value = value }
    fun getGridAIEmpathy() : Int { return gridAIEmpathy.value!! }

    val mutableMilitary = MutableLiveData<Double>()
    val military : LiveData<Double> get() = mutableMilitary
    fun setMilitary(value: Double) { mutableMilitary.value = value }
    fun getMilitary() : Double { return military.value!! }

    val mutableCivilians = MutableLiveData<Double>()
    val civilians : LiveData<Double> get() = mutableCivilians
    fun setCivilians(value: Double) { mutableCivilians.value = value }
    fun getCivilians() : Double { return civilians.value!! }


    val mutableCurrentDay = MutableLiveData<Int>()
    val currentDay : LiveData<Int> get() = mutableCurrentDay
    fun setCurrentDay(value: Int) { mutableCurrentDay.value = value }
    fun getCurrentDay() : Int { return currentDay.value!! }

    val mutableEventText = MutableLiveData<String>()
    val eventText : LiveData<String> get() = mutableEventText
    fun setEventText(value: String) { mutableEventText.value = value }
    fun getEventText() : String { return eventText.value!! }
}