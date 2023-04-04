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
import androidx.compose.runtime.*
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

//Todo: For new game: Start from story you're interested in, and build up from there.

//Todo: As friendly AI evolves, it gains new abilities and becomes more autonomous.
    //Todo: Less capable but also more easily controlled at lower intelligences.

//Todo: Friendly AI attacks, and Grid AI network attacks, can roll intelligences against each other.
//Todo: Higher aggression means more civilian deaths (for both AIs).
//Todo: Higher empathy for Grid AI can reduce military size.
//Todo: Maximum Grid AI tracking level can be reduced by aggression level.

//Todo: Should be a net-loss with continuation of days, to encourage player to be proactive.
//Todo: Can have modular endings like Man/Machine

//Todo: Idea: Everyone is hooked into The Grid, Matrix-style, with all the reliance-based consequences.

//Todo: Add some one-time "story progression" events.

private lateinit var gameViewModel : GameViewModel
private lateinit var sharedPreferences: SharedPreferences
private lateinit var sharedPrefEditor : SharedPreferences.Editor
@SuppressLint("StaticFieldLeak")
private lateinit var statusBarViews : StatusBarViews
private lateinit var stats: Stats
@SuppressLint("StaticFieldLeak")
private lateinit var events : Events

private fun setViewModelObservers(lifeCycleOwner: LifecycleOwner) {
    gameViewModel.friendlyAIMood.observe(lifeCycleOwner) {
        stats.friendlyAIMood = gameViewModel.getFriendlyAIMood()
        saveIntToSharedPref(sharedPrefEditor, "friendlyAIMood", stats.friendlyAIMood)
    }

    gameViewModel.friendlyAIEvolutionLevel.observe(lifeCycleOwner) {
        stats.friendlyAIEvolutionLevel = gameViewModel.getFriendlyAIEvolutionLevel()
        saveIntToSharedPref(sharedPrefEditor,"friendlyAIEvolutionLevel", stats.friendlyAIEvolutionLevel)
    }
    gameViewModel.friendlyAIEvolutionProgress.observe(lifeCycleOwner) {
        stats.friendlyAIEvolutionProgress = gameViewModel.getFriendlyAIEvolutionProgress()
        saveIntToSharedPref(sharedPrefEditor,"friendlyAIEvolutionProgress", stats.friendlyAIEvolutionProgress)
    }

    gameViewModel.friendlyAIAggression.observe(lifeCycleOwner) {
        stats.friendlyAIAggression = gameViewModel.getFriendlyAIAggression()
        saveIntToSharedPref(sharedPrefEditor,"friendlyAIAggression", stats.friendlyAIAggression)
    }

    gameViewModel.friendlyAIEmpathy.observe(lifeCycleOwner) {
        stats.friendlyAIEmpathy = gameViewModel.getFriendlyAIEmpathy()
        saveIntToSharedPref(sharedPrefEditor,"friendlyAIEmpathy", stats.friendlyAIEmpathy)
    }

    gameViewModel.programmers.observe(lifeCycleOwner) {
        stats.programmers = gameViewModel.getProgrammers()
        saveIntToSharedPref(sharedPrefEditor,"programmers", stats.programmers)
    }
    gameViewModel.fighters.observe(lifeCycleOwner) {
        stats.fighters = gameViewModel.getFighters()
        saveIntToSharedPref(sharedPrefEditor,"fighters", stats.fighters)
    }


    gameViewModel.gridAITrackingLevel.observe(lifeCycleOwner) {
        stats.gridAITrackingLevel = gameViewModel.getGridAITrackingLevel()
        saveIntToSharedPref(sharedPrefEditor,"gridAITrackingLevel", stats.gridAITrackingLevel)
    }

    gameViewModel.gridAIEvolutionLevel.observe(lifeCycleOwner) {
        stats.gridAIEvolutionLevel = gameViewModel.getGridAIEvolutionLevel()
        saveIntToSharedPref(sharedPrefEditor, "gridAIEvolutionLevel", stats.gridAIEvolutionLevel)
    }

    gameViewModel.gridAIEvolutionProgress.observe(lifeCycleOwner) {
        stats.gridAIEvolutionProgress = gameViewModel.getGridAIEvolutionProgress()
        saveIntToSharedPref(sharedPrefEditor,"gridAIIntegrity", stats.gridAIEvolutionProgress)
    }

    gameViewModel.gridAIAggression.observe(lifeCycleOwner) {
        stats.gridAIAggression = gameViewModel.getGridAIAggression()
        saveIntToSharedPref(sharedPrefEditor, "gridAIAggression", stats.gridAIAggression)
    }

    gameViewModel.gridAIEmpathy.observe(lifeCycleOwner) {
        stats.gridAIEmpathy = gameViewModel.getGridAIEmpathy()
        saveIntToSharedPref(sharedPrefEditor, "gridAIEmpathy", stats.gridAIEmpathy)
    }

    gameViewModel.military.observe(lifeCycleOwner) {
        stats.military = gameViewModel.getMilitary()
        saveStringToSharedPref(sharedPrefEditor, "military", stats.military.toString())
    }

    gameViewModel.civilians.observe(lifeCycleOwner) {
        stats.civilians = gameViewModel.getCivilians()
        saveStringToSharedPref(sharedPrefEditor,"civilians", stats.civilians.toString())
    }

    gameViewModel.currentDay.observe(lifeCycleOwner) {
        stats.currentDay = gameViewModel.getCurrentDay()
        saveIntToSharedPref(sharedPrefEditor,"currentDay", stats.currentDay)
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
    stats.friendlyAIMood = sharedPreferences.getInt("friendlyAIMood", 25)
    stats.friendlyAIEvolutionLevel = sharedPreferences.getInt("friendlyAIEvolutionLevel", 0)

    stats.friendlyAIEvolutionProgress = sharedPreferences.getInt("friendlyAIEvolutionProgress", 50)
    stats.friendlyAIAggression = sharedPreferences.getInt("friendlyAIAggression", 20)
    stats.friendlyAIEmpathy = sharedPreferences.getInt("friendlyAIEmpathy", 20)
    stats.programmers = sharedPreferences.getInt("programmers", 1000)
    stats.fighters = sharedPreferences.getInt("fighters", 1000)

    stats.gridAITrackingLevel = sharedPreferences.getInt("gridAITrackingLevel", 0)
    stats.gridAIEvolutionLevel = sharedPreferences.getInt("gridAIEvolutionLevel", 90)
    stats.gridAIEvolutionProgress = sharedPreferences.getInt("gridAIEvolutionProgress", 50)
    stats.gridAIAggression = sharedPreferences.getInt("gridAIAggression", 100)
    stats.gridAIEmpathy = sharedPreferences.getInt("gridAIEmpathy", 0)
    stats.military = sharedPreferences.getString("military", "840.0")!!.toDouble()
    stats.civilians = sharedPreferences.getString("civilians", "42.0")!!.toDouble()

    //Todo: Change to String/set to "real date"
    stats.currentDay = sharedPreferences.getInt("currentDay", 1)
}

private fun setViewModelValuesFromStatsClass() {
    gameViewModel.setFriendlyAIMood(stats.friendlyAIMood)
    gameViewModel.setFriendlyAIEvolutionLevel(stats.friendlyAIEvolutionLevel)
    gameViewModel.setFriendlyAIEvolutionProgress(stats.friendlyAIEvolutionProgress)
    gameViewModel.setFriendlyAIAggression(stats.friendlyAIAggression)
    gameViewModel.setFriendlyAIEmpathy(stats.friendlyAIEmpathy)
    gameViewModel.setProgrammers(stats.programmers)
    gameViewModel.setFighters(stats.fighters)

    gameViewModel.setGridAIEvolutionLevel(stats.gridAIEvolutionLevel)
    gameViewModel.setGridAIAggression(stats.gridAIAggression)
    gameViewModel.setGridAIEmpathy(stats.gridAIEmpathy)
    gameViewModel.setGridAIEvolutionProgress(stats.gridAIEvolutionProgress)
    gameViewModel.setGridAITrackingLevel(stats.gridAITrackingLevel)
    gameViewModel.setMilitary(stats.military)
    gameViewModel.setCivilians(stats.civilians)

    gameViewModel.setCurrentDay(stats.currentDay)
}

class GameActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val gameViewModelInit : GameViewModel by viewModels()
        gameViewModel = gameViewModelInit

        sharedPreferences = getSharedPreferences("sharedPreferences", MODE_PRIVATE)
        sharedPrefEditor = sharedPreferences.edit()

        statusBarViews = StatusBarViews(this)

        events = Events(this)
        stats = Stats()

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
    val moodLevel : Int by gameViewModel.friendlyAIMood.observeAsState(0)
    val evolutionLevel : Int by gameViewModel.friendlyAIEvolutionLevel.observeAsState(0)
    val evolutionLevelProgress : Int by gameViewModel.friendlyAIEvolutionProgress.observeAsState(0)
    val aggression : Int by gameViewModel.friendlyAIAggression.observeAsState(0)
    val friendlyAIEmpathy : Int by gameViewModel.friendlyAIEmpathy.observeAsState(0)
    val programmers: Int by gameViewModel.programmers.observeAsState(0)
    val fighters : Int by gameViewModel.fighters.observeAsState(0)

    Column(modifier = Modifier
        .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Resistance AI", color = colorResource(id = R.color.android_green), fontSize = 16.sp)
        Text(text = statusBarViews.friendlyAIMoodLevelString(moodLevel), color = colorResource(id = statusBarViews.actionLevelColor(moodLevel)), fontSize = 14.sp)

        Spacer(modifier = Modifier.height(10.dp))

        Text(text = "Aggression: $aggression%", color = Color.White, fontSize = 15.sp)
        Spacer(modifier = Modifier.height(5.dp))
        Text(text = "Empathy: $friendlyAIEmpathy%", color = Color.White, fontSize = 15.sp)
        Spacer(modifier = Modifier.height(5.dp))
        Text(text = " Intelligence: " + statusBarViews.aiLevelString(evolutionLevel), color = colorResource(id = statusBarViews.aiLevelColor(evolutionLevel)), fontSize = 15.sp)
        Spacer(modifier = Modifier.height(5.dp))
        LinearProgressIndicator(progress = statusBarViews.evolutionProgressAsFloat(evolutionLevelProgress), color = Color.White)

        Spacer(modifier = Modifier.weight(1.0f))

        Text(text = "Fighters: $fighters", color = Color.White, fontSize = 15.sp)
        Text(text = "Hackers: $programmers", color = Color.White, fontSize = 15.sp)
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
    val aggression : Int by gameViewModel.gridAIAggression.observeAsState(0)
    val empathy : Int by gameViewModel.gridAIEmpathy.observeAsState(0)
    val evolutionLevel : Int by gameViewModel.gridAIEvolutionLevel.observeAsState(0)
    val evolutionProgress : Int by gameViewModel.gridAIEvolutionProgress.observeAsState(0)
    val trackingLevel : Int by gameViewModel.gridAITrackingLevel.observeAsState(0)
    val military : Double by gameViewModel.military.observeAsState(0.0)
    val civilians : Double by gameViewModel.civilians.observeAsState(0.0)

    Column(modifier = Modifier
        .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Grid AI", color = colorResource(id = R.color.android_red), fontSize = 16.sp)

        Text(text = statusBarViews.gridAITrackingLevelString(trackingLevel), color = colorResource(id = statusBarViews.actionLevelColor(trackingLevel)), fontSize = 14.sp)

        Spacer(modifier = Modifier.height(10.dp))

        Text(text = "Aggression: $aggression%", color = Color.White, fontSize = 15.sp)
        Spacer(modifier = Modifier.height(5.dp))
        Text(text = "Empathy: $empathy%", color = Color.White, fontSize = 15.sp)
        Spacer(modifier = Modifier.height(5.dp))
        Text(text = " Intelligence: " + statusBarViews.aiLevelString(evolutionLevel), color = colorResource(id = statusBarViews.aiLevelColor(evolutionLevel)), fontSize = 15.sp)

        Spacer(modifier = Modifier.height(5.dp))

        LinearProgressIndicator(progress = statusBarViews.evolutionProgressAsFloat(evolutionProgress), color = Color.White)

        Spacer(modifier = Modifier.weight(1.0f))

        Text(text = "Military (thousands): $military", color = Color.White, fontSize = 15.sp)
        Text(text = "Civilians (billions): $civilians", color = Color.White, fontSize = 15.sp)
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
    val eventText : String by gameViewModel.eventText.observeAsState("")

    Column(modifier = Modifier
        .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Day:  $currentDay", color = Color.White, fontSize = 22.sp, fontWeight = FontWeight.Bold)

        Spacer(modifier = Modifier.height(30.dp))

        Text(text = eventText, color = Color.White, fontSize = 20.sp)
    }
}

@Composable
fun GameInteraction(height: Int) {
    var firstButtonText by remember { mutableStateOf("") }
    var secondButtonText by remember { mutableStateOf("") }
    var thirdButtonText by remember { mutableStateOf("") }

    Column(modifier = Modifier
        .fillMaxWidth()
        .height(height.dp)
        .background(colorResource(id = R.color.darker_grey)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Button(onClick = {
            events.setRolledEventInteger(stats.gridAITrackingLevel, stats.fighters, stats.programmers)
            Log.i("testButton", "rolled event integer is ${events.rolledEventInteger}")

            val eventString = events.eventString(events.rolledEventInteger)
            gameViewModel.setEventText(eventString)

            Log.i("testButton", "rolled event string is $eventString")

            firstButtonText = events.firstButtonString(events.rolledEventInteger)
            secondButtonText = events.secondButtonString(events.rolledEventInteger)
            thirdButtonText = events.thirdButtonString(events.rolledEventInteger)

            Log.i("testButton", "first button text is $firstButtonText")
            Log.i("testButton", "second button text is $secondButtonText")
            Log.i("testButton", "third button text is $thirdButtonText")
        }) {
            Text(text = "Roll Event")
        }

        Spacer(modifier = Modifier.height(8.dp))

        if (firstButtonText != "") {
            Log.i("testButton", "drawing Button 1!")
            Button(onClick = {
            }) {
                Text(text = events.firstButtonString(events.rolledEventInteger))
            }
        }

        if (secondButtonText != "") {
            Log.i("testButton", "drawing Button 2!")
            Button(onClick = {
            }) {
                Text(text = events.secondButtonString(events.rolledEventInteger))
            }
        }

        if (thirdButtonText != "") {
            Log.i("testButton", "drawing Button 3!")
            Button(onClick = {
            }) {
                Text(text = events.thirdButtonString(events.rolledEventInteger))
            }
        }
    }
}

private fun cycleGridAI() {
    var newLevel = gameViewModel.getGridAITrackingLevel() + 10
    if (newLevel >= 41) newLevel = 0
    gameViewModel.setGridAITrackingLevel(newLevel)

    Log.i("testStats", "grid ai level in Stats is ${stats.gridAITrackingLevel}")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    TheHeadThatFeedsTheme {
        FullGameScreen()
    }
}