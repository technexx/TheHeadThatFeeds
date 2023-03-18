package the.head.that.feeds

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class GameViewModel : ViewModel() {
    var friendlyAILevelProgress = 0
    var enemyAILevelProgress = 0
    var enemyActivityMultiplier = 0

    val mutableFriendlyAILevel = MutableLiveData<Int>()
    val friendlyAILevel : LiveData<Int> get() = mutableFriendlyAILevel
    fun setFriendlyAILevel(value: Int) { mutableFriendlyAILevel.value = value }
    fun getFriendlyAILevel() : Int { return friendlyAILevel.value!! }

    val mutableGridAILevel = MutableLiveData<Int>()
    val gridAILevel : LiveData<Int> get() = mutableGridAILevel
    fun setGridAILevel(value: Int) { mutableGridAILevel.value = value }
    fun getGridAILevel() : Int { return gridAILevel.value!! }

    val mutableAggression = MutableLiveData<Int>()
    val aggression : LiveData<Int> get() = mutableAggression
    fun setAggression(value: Int) { mutableAggression.value = value }
    fun getAggression() : Int { return aggression.value!! }

    val mutableEmpathy = MutableLiveData<Int>()
    val empathy : LiveData<Int> get() = mutableEmpathy
    fun setEmpathy(value: Int) { mutableEmpathy.value = value }
    fun getEmpathy() : Int { return empathy.value!! }

    val mutableResistanceMembers = MutableLiveData<Int>()
    val resistanceMembers : LiveData<Int> get() = mutableResistanceMembers
    fun setResistanceMembers(value: Int) { mutableResistanceMembers.value = value }
    fun getResistanceMembers() : Int { return resistanceMembers.value!! }

    val mutableCivilians = MutableLiveData<Double>()
    val civilians : LiveData<Double> get() = mutableCivilians
    fun setCivilians (value: Double) { mutableCivilians.value = value }
    fun getCivilians () : Double { return civilians.value!! }

    val mutableCurrentDay = MutableLiveData<Int>()
    val currentDay : LiveData<Int> get() = mutableCurrentDay
    fun setCurrentDay(value: Int) { mutableCurrentDay.value = value }
    fun getCurrentDay() : Int { return currentDay.value!! }

    val mutableEnemyActivity = MutableLiveData<Int>()
    val enemyActivity : LiveData<Int> get() = mutableEnemyActivity
    fun setEnemyActivity(value: Int) { mutableEnemyActivity.value = value }
    fun getEnemyActivity() : Int { return enemyActivity.value!! }
}