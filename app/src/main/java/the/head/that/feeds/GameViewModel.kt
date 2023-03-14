package the.head.that.feeds

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class GameViewModel : ViewModel() {
    val mutableFriendlyAILevel = MutableLiveData<Int>()
    val friendlyAILevel : LiveData<Int> get() = mutableFriendlyAILevel

    fun setFriendlyAILevel(value: Int) { mutableFriendlyAILevel.value = value }

    val mutableGridAILevel = MutableLiveData<Int>()
    val gridAILevel : LiveData<Int> get() = mutableGridAILevel

    fun setGridAILevel(value: Int) { mutableGridAILevel.value = value }

}