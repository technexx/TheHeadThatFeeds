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

    val mutableScientists = MutableLiveData<Int>()
    val scientists : LiveData<Int> get() = mutableScientists
    fun setScientists(value: Int) { mutableScientists.value = value }
    fun getScientists() : Int { return scientists.value!! }

    val mutableHackers = MutableLiveData<Int>()
    val hackers : LiveData<Int> get() = mutableHackers
    fun setHackers(value: Int) { mutableHackers.value = value }
    fun getHackers() : Int { return hackers.value!! }

    val mutableHunters = MutableLiveData<Int>()
    val hunters : LiveData<Int> get() = mutableHunters
    fun setHunters(value: Int) { mutableHunters.value = value }
    fun getHunters() : Int { return hunters.value!! }

    val mutableCurrentDay = MutableLiveData<Int>()
    val currentDay : LiveData<Int> get() = mutableCurrentDay
    fun setCurrentDay(value: Int) { mutableCurrentDay.value = value }
    fun getCurrentDay() : Int { return currentDay.value!! }

    val mutableWeather = MutableLiveData<Int>()
    val weather : LiveData<Int> get() = mutableWeather
    fun setWeather(value: Int) { mutableWeather.value = value }
    fun getWeather() : Int { return weather.value!! }
}