package the.head.that.feeds

import android.annotation.SuppressLint
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.LifecycleOwner
import the.head.that.feeds.ui.theme.TheHeadThatFeedsTheme

//Todo: As friendly AI evolves, it gains new abilities and becomes more autonomous.
//Todo: Should be a net-loss with continuation of days, to encourage player to be proactive.
//Todo: Attacks are more frequent and severe the higher Grid AI's awareness is.

//Todo: Idea: Instead of health, integrity affects monitoring of Grid AI. Lower monitoring = better chance to evolve (can set it as a choice, a natural evolution, or both).


private lateinit var gameViewModel : GameViewModel
private lateinit var sharedPreferences: SharedPreferences
private lateinit var sharedPrefEditor : SharedPreferences.Editor
@SuppressLint("StaticFieldLeak")
private lateinit var statusBarViews : StatusBarViews
@SuppressLint("StaticFieldLeak")
private lateinit var events : Events

private fun setViewModelObservers(lifeCycleOwner: LifecycleOwner) {
    gameViewModel.friendlyAIEvolutionLevel.observe(lifeCycleOwner) {
        Stats.friendlyAIEvolutionLevel = gameViewModel.getFriendlyAIEvolutionLevel()
        saveIntToSharedPref(sharedPrefEditor,"friendlyAIEvolutionLevel", Stats.friendlyAIEvolutionLevel)

    }
    gameViewModel.friendlyAIIntegrityLevel.observe(lifeCycleOwner) {
        Stats.friendlyAIIntegrity = gameViewModel.getFriendlyAIIntegrity()
        saveIntToSharedPref(sharedPrefEditor,"friendlyAIEvolutionLevel", Stats.friendlyAIIntegrity)

    }
    gameViewModel.gridAIIntegrityLevel.observe(lifeCycleOwner) {
        Stats.gridAIIntegrity = gameViewModel.getGridAIIntegrity()
        saveIntToSharedPref(sharedPrefEditor,"gridAIIntegrity", Stats.gridAIIntegrity)

    }
    gameViewModel.gridAITrackingLevel.observe(lifeCycleOwner) {
        Stats.gridAITrackingLevel = gameViewModel.getGridAITrackingLevel()
        saveIntToSharedPref(sharedPrefEditor,"gridAITrackingLevel", Stats.gridAITrackingLevel)
        Log.i("testShared", "grid AI tracking in Shared is ${sharedPreferences.getInt("gridAITrackingLevel", 0)}")
    }

    gameViewModel.aggression.observe(lifeCycleOwner) {
        Stats.aggression = gameViewModel.getAggression()
        saveIntToSharedPref(sharedPrefEditor,"aggression", Stats.aggression)
    }
    gameViewModel.empathy.observe(lifeCycleOwner) {
        Stats.empathy = gameViewModel.getEmpathy()
        saveIntToSharedPref(sharedPrefEditor,"empathy", Stats.empathy)
    }
    gameViewModel.programmers.observe(lifeCycleOwner) {
        Stats.programmers = gameViewModel.getProgrammers()
        saveIntToSharedPref(sharedPrefEditor,"programmers", Stats.programmers)
    }
    gameViewModel.fighters.observe(lifeCycleOwner) {
        Stats.fighters = gameViewModel.getFighters()
        saveIntToSharedPref(sharedPrefEditor,"fighters", Stats.fighters)
    }
    gameViewModel.civilians.observe(lifeCycleOwner) {
        Stats.civilians = gameViewModel.getCivilians()
        saveStringToSharedPref(sharedPrefEditor,"civilians", Stats.civilians.toString())
    }

    gameViewModel.currentDay.observe(lifeCycleOwner) {
        Stats.currentDay = gameViewModel.getCurrentDay()
        saveIntToSharedPref(sharedPrefEditor,"currentDay", Stats.currentDay)
    }
}

private fun saveIntToSharedPref(editor: SharedPreferences.Editor, key: String, value: Int) {
    editor.putInt(key, value)
    editor.apply()
}

private fun saveStringToSharedPref(editor: SharedPreferences.Editor, key: String, value: String) {
    editor.putString(key, value)
    editor.apply()
}

private fun setStatsClassValuesFromSharedPreferences() {
    Stats.friendlyAIEvolutionLevel = sharedPreferences.getInt("friendlyAIEvolutionLevel", 0)
    Stats.friendlyAIIntegrity = sharedPreferences.getInt("friendlyAIIntegrity", 100)
    Stats.gridAIIntegrity = sharedPreferences.getInt("gridAIIntegrity", 100)
    Stats.gridAITrackingLevel = sharedPreferences.getInt("gridAITrackingLevel", 0)
    Stats.aggression = sharedPreferences.getInt("aggression", 20)
    Stats.empathy = sharedPreferences.getInt("empathy", 20)
    Stats.programmers = sharedPreferences.getInt("programmers", 1000)
    Stats.fighters = sharedPreferences.getInt("fighters", 1000)
    Stats.civilians = sharedPreferences.getString("civilians", "42.0")!!.toDouble()
    Stats.currentDay = sharedPreferences.getInt("currentDay", 1)
}

private fun setViewModelValuesFromStatsClass() {
    gameViewModel.setFriendlyAIEvolutionLevel(Stats.friendlyAIEvolutionLevel)
    gameViewModel.setFriendlyAIIntegrity(Stats.friendlyAIIntegrity)
    gameViewModel.setGridAIIntegrity(Stats.gridAIIntegrity)
    gameViewModel.setGridAITrackingLevel(Stats.gridAITrackingLevel)

    gameViewModel.setAggression(Stats.aggression)
    gameViewModel.setEmpathy(Stats.empathy)
    gameViewModel.setProgrammers(Stats.programmers)
    gameViewModel.setFighters(Stats.fighters)
    gameViewModel.setCivilians(Stats.civilians)

    gameViewModel.setCurrentDay(Stats.currentDay)
}

class GameActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val gameViewModelInit : GameViewModel by viewModels()
        gameViewModel = gameViewModelInit

        sharedPreferences = getSharedPreferences("sharedPreferences", MODE_PRIVATE)
        sharedPrefEditor = sharedPreferences.edit()
        statusBarViews = StatusBarViews(this)
        Stats.setDefaultStatValues()
        events = Events(this)

        setStatsClassValuesFromSharedPreferences()
        setViewModelValuesFromStatsClass()
        setViewModelObservers(this)

        setContent {
            TheHeadThatFeedsTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(),
                    color = colorResource(id = R.color.onyx)
                ) {
                    FullGameScreen()
                }
            }
        }
    }
}

//Default height/width within a column/row is determined by largest element, unless specified.
@Composable
fun FullGameScreen() {
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp

    Column() {
        GameStatusBarColumn(screenHeight / 4)
        Divider(thickness = 2.dp, color = Color.Black)
        GameBodyColumn  (screenHeight / 2)
        Divider(thickness = 2.dp, color = Color.Black)
        GameInteraction(screenHeight / 4)
    }
}

//Columns lay down their children vertically, so of course our rows will be stacked.
@Composable
fun GameStatusBarColumn(height: Int) {
    Column(modifier = Modifier
        .height(height.dp)
        .background(colorResource(id = R.color.black_denim))
    ) {
        GameStatusBarMainRow(backgroundColor = R.color.android_blue)
    }
}

@Composable
fun GameStatusBarMainRow(backgroundColor: Int) {
    Row(modifier = Modifier
        .fillMaxSize()
        .background(colorResource(id = backgroundColor))
    ) {
        GameStatusBarSubRows()
    }
}

@Composable
fun GameStatusBarSubRows() {
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp

    StatusBarLeftRow(width = screenWidth/2)
    Divider(color = Color.White, modifier = Modifier
        .fillMaxHeight()
        .width(1.dp))
    StatusBarRightRow(width = screenWidth/2)
}

@Composable
fun StatusBarLeftRow(width: Int) {
    Row(
        modifier = Modifier
            .fillMaxHeight()
            .background(colorResource(id = R.color.black))
            .width(width.dp),
    ) {
        StatusBarLeftRowColumn()
    }
}

@Composable
fun StatusBarLeftRowColumn() {
    val friendlyAILevel : Int by gameViewModel.friendlyAIEvolutionLevel.observeAsState(0)
    val aggression : Int by gameViewModel.aggression.observeAsState(0)
    val empathy : Int by gameViewModel.empathy.observeAsState(0)
    val programmers: Int by gameViewModel.programmers.observeAsState(0)
    val fighters : Int by gameViewModel.fighters.observeAsState(0)

    Column(modifier = Modifier
        .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Resistance AI", color = colorResource(id = R.color.android_green), fontSize = 17.sp)

        Spacer(modifier = Modifier.height(10.dp))

        LinearProgressIndicator(progress = statusBarViews.integrityLevelAsFloat(gameViewModel.getFriendlyAIIntegrity()), color = Color.White)

        Spacer(modifier = Modifier.height(10.dp))

        Text(text = "Aggression: $aggression%", color = Color.White, fontSize = 16.sp)
        Spacer(modifier = Modifier.height(5.dp))
        Text(text = "Empathy: $empathy%", color = Color.White, fontSize = 16.sp)
        Spacer(modifier = Modifier.height(5.dp))
        Text(text = " Intelligence: " + statusBarViews.friendlyAILevelString(friendlyAILevel), color = colorResource(id = statusBarViews.friendlyAILevelColor(friendlyAILevel)), fontSize = 16.sp)

        Spacer(modifier = Modifier.weight(1.0f))

        Text(text = "Programmers: $programmers", color = Color.White, fontSize = 16.sp)
        Text(text = "Fighters: $fighters", color = Color.White, fontSize = 16.sp)
    }
}

@Composable
fun StatusBarRightRow(width: Int) {
    Row(
        modifier = Modifier
            .fillMaxHeight()
            .background(colorResource(id = R.color.black))
            .width(width.dp),
    ) {
        StatusBarRightRowColumn()
    }
}

@Composable
fun StatusBarRightRowColumn() {
    val gridAILevel : Int by gameViewModel.gridAITrackingLevel.observeAsState(0)
    val civilians : Double by gameViewModel.civilians.observeAsState(42.0)

    Column(modifier = Modifier
        .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Grid AI", color = colorResource(id = R.color.android_red), fontSize = 17.sp)

        Spacer(modifier = Modifier.height(10.dp))

        LinearProgressIndicator(progress = statusBarViews.integrityLevelAsFloat(gameViewModel.getGridAIIntegrity()), color = Color.White)

        Spacer(modifier = Modifier.height(30.dp))

        Text(text = statusBarViews.gridAIActionLevelString(gridAILevel), color = colorResource(id = statusBarViews.gridAIActionLevelColor(gridAILevel)), fontSize = 20.sp)

        Spacer(modifier = Modifier.weight(1.0f))

        Text(text = "Civilians (billions): $civilians", color = Color.White, fontSize = 16.sp)
    }
}

@Composable
fun GameBodyColumn(height: Int) {
    Column(modifier = Modifier
        .fillMaxWidth()
        .height(height.dp)
        .background(colorResource(id = R.color.onyx))
    ) {
        GameBodyTopColumn()
    }
}

@Composable
fun GameBodyTopColumn() {
    val currentDay : Int by gameViewModel.currentDay.observeAsState(0)

    Column(modifier = Modifier
        .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Day:  $currentDay", color = Color.White, fontSize = 22.sp, fontWeight = FontWeight.Bold)
    }
}

@Composable
fun GameInteraction(height: Int) {
    Column(modifier = Modifier
        .fillMaxWidth()
        .height(height.dp)
        .background(colorResource(id = R.color.darker_grey)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Button(onClick = {
            events.rolledEvent()
        }) {
            Text(text = "Advance evolution")
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(onClick = {
            cycleGridAI()
        }) {
            Text(text = "Cycle Grid AI")
        }
    }
}

private fun cycleGridAI() {
    var newLevel = gameViewModel.getGridAITrackingLevel() + 10
    if (newLevel >= 41) newLevel = 0
    gameViewModel.setGridAITrackingLevel(newLevel)

    Log.i("testStats", "grid ai level in Stats is ${Stats.gridAITrackingLevel}")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    TheHeadThatFeedsTheme {
        FullGameScreen()
    }
}