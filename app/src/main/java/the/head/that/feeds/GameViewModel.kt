package the.head.that.feeds

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class GameViewModel : ViewModel() {
    val mutableFriendlyAILevel = MutableLiveData<Int>()
    val friendlyAILevel : LiveData<Int> get() = mutableFriendlyAILevel
    fun setFriendlyAILevel(value: Int) { mutableFriendlyAILevel.value = value }
    fun getFriendlyAILevel() : Int { return friendlyAILevel.value!! }

    val mutableGridAILevel = MutableLiveData<Int>()
    val gridAILevel : LiveData<Int> get() = mutableGridAILevel
    fun setGridAILevel(value: Int) { mutableGridAILevel.value = value }
    fun getGridAILevel() : Int { return gridAILevel.value!! }

    val mutableCurrentDay = MutableLiveData<Int>()
    val currentDay : LiveData<Int> get() = mutableCurrentDay
    fun setCurrentDay(value: Int) { mutableCurrentDay.value = value }
    fun getCurrentDay() : Int { return currentDay.value!! }
}