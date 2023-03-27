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
import the.head.that.feeds.Stats.testComp.gridAITrackingLevel
import the.head.that.feeds.ui.theme.TheHeadThatFeedsTheme

//Todo: For new game: Start from story you're interested in, and build up from there.

//Todo: As friendly AI evolves, it gains new abilities and becomes more autonomous.
//Todo: Should be a net-loss with continuation of days, to encourage player to be proactive.

//Todo: Idea: Instead of health, integrity affects monitoring of Grid AI. Lower monitoring = better chance to evolve (can set it as a choice, a natural evolution, or both).

//Todo: Maybe Grid AI can evolve AND de-evolve. Starts higher (human). Player/friendly AI actions affect this.
    //Todo: Can do same stats for both AIs. Grid starts at 100% friendlyAIAggression, 0% empathy. Can try to "turn" it.
    //Todo: Friendly AI attacks, and Grid AI network attacks, should roll intelligences against each other.
    //Todo: Higher aggression means more civilian deaths (for both AIs).
    //Todo: Higher empathy for Grid AI can reduce military size.
    //Todo: Tracking max level can be reduced by aggression level.

private lateinit var gameViewModel : GameViewModel
private lateinit var sharedPreferences: SharedPreferences
private lateinit var sharedPrefEditor : SharedPreferences.Editor
@SuppressLint("StaticFieldLeak")
private lateinit var statusBarViews : StatusBarViews
@SuppressLint("StaticFieldLeak")
private lateinit var events : Events

private fun setViewModelObservers(lifeCycleOwner: LifecycleOwner) {
    gameViewModel.friendlyAIMood.observe(lifeCycleOwner) {
        Stats.friendlyAIMood = gameViewModel.getFriendlyAIMood()
        saveIntToSharedPref(sharedPrefEditor, "friendlyAIMood", Stats.friendlyAIMood)
    }

    gameViewModel.friendlyAIEvolutionLevel.observe(lifeCycleOwner) {
        Stats.friendlyAIEvolutionLevel = gameViewModel.getFriendlyAIEvolutionLevel()
        saveIntToSharedPref(sharedPrefEditor,"friendlyAIEvolutionLevel", Stats.friendlyAIEvolutionLevel)

    }
    gameViewModel.friendlyAIIntegrityLevel.observe(lifeCycleOwner) {
        Stats.friendlyAIIntegrity = gameViewModel.getFriendlyAIIntegrity()
        saveIntToSharedPref(sharedPrefEditor,"friendlyAIIntegrity", Stats.friendlyAIIntegrity)
    }

    gameViewModel.friendlyAIAggression.observe(lifeCycleOwner) {
        Stats.friendlyAIAggression = gameViewModel.getFriendlyAIAggression()
        saveIntToSharedPref(sharedPrefEditor,"friendlyAIAggression", Stats.friendlyAIAggression)
    }

    gameViewModel.friendlyAIEmpathy.observe(lifeCycleOwner) {
        Stats.friendlyAIEmpathy = gameViewModel.getFriendlyAIEmpathy()
        saveIntToSharedPref(sharedPrefEditor,"friendlyAIEmpathy", Stats.friendlyAIEmpathy)
    }

    gameViewModel.programmers.observe(lifeCycleOwner) {
        Stats.programmers = gameViewModel.getProgrammers()
        saveIntToSharedPref(sharedPrefEditor,"programmers", Stats.programmers)
    }
    gameViewModel.fighters.observe(lifeCycleOwner) {
        Stats.fighters = gameViewModel.getFighters()
        saveIntToSharedPref(sharedPrefEditor,"fighters", Stats.fighters)
    }


    gameViewModel.gridAITrackingLevel.observe(lifeCycleOwner) {
        Stats.gridAITrackingLevel = gameViewModel.getGridAITrackingLevel()
        saveIntToSharedPref(sharedPrefEditor,"gridAITrackingLevel", Stats.gridAITrackingLevel)
    }

    gameViewModel.gridAIEvolutionLevel.observe(lifeCycleOwner) {
        Stats.gridAIEvolutionLevel = gameViewModel.getGridAIEvolutionLevel()
        saveIntToSharedPref(sharedPrefEditor, "gridAIEvolutionLevel", Stats.gridAIEvolutionLevel)
    }

    gameViewModel.gridAIIntegrityLevel.observe(lifeCycleOwner) {
        Stats.gridAIIntegrity = gameViewModel.getGridAIIntegrity()
        saveIntToSharedPref(sharedPrefEditor,"gridAIIntegrity", Stats.gridAIIntegrity)
    }

    gameViewModel.gridAIAggression.observe(lifeCycleOwner) {
        Stats.gridAIAggression = gameViewModel.getGridAIAggression()
        saveIntToSharedPref(sharedPrefEditor, "gridAIAggression", Stats.gridAIAggression)
    }

    gameViewModel.gridAIEmpathy.observe(lifeCycleOwner) {
        Stats.gridAIEmpathy = gameViewModel.getGridAIAggression()
        saveIntToSharedPref(sharedPrefEditor, "gridAIEmpathy", Stats.gridAIEmpathy)
    }

    gameViewModel.military.observe(lifeCycleOwner) {
        Stats.military = gameViewModel.getMilitary()
        saveStringToSharedPref(sharedPrefEditor, "military", Stats.military.toString())
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
    Stats.friendlyAIMood = sharedPreferences.getInt("friendlyAIMood", 0)
    Stats.friendlyAIEvolutionLevel = sharedPreferences.getInt("friendlyAIEvolutionLevel", 0)
    Stats.friendlyAIIntegrity = sharedPreferences.getInt("friendlyAIIntegrity", 100)
    Stats.friendlyAIAggression = sharedPreferences.getInt("friendlyAIAggression", 20)
    Stats.friendlyAIEmpathy = sharedPreferences.getInt("friendlyAIEmpathy", 20)
    Stats.programmers = sharedPreferences.getInt("programmers", 1000)
    Stats.fighters = sharedPreferences.getInt("fighters", 1000)

    Stats.gridAITrackingLevel = sharedPreferences.getInt("gridAITrackingLevel", 0)
    Stats.gridAIEvolutionLevel = sharedPreferences.getInt("gridAIEvolution", 900)
    Stats.gridAIIntegrity = sharedPreferences.getInt("gridAIIntegrity", 100)
    Stats.gridAIAggression = sharedPreferences.getInt("gridAIAggression", 100)
    Stats.gridAIEmpathy = sharedPreferences.getInt("gridAIEmpathy", 0)
    Stats.military = sharedPreferences.getString("military", "840.0")!!.toDouble()
    Stats.civilians = sharedPreferences.getString("civilians", "42.0")!!.toDouble()

    Stats.currentDay = sharedPreferences.getInt("currentDay", 1)
}

private fun setViewModelValuesFromStatsClass() {
    gameViewModel.setFriendlyAIMood(Stats.friendlyAIMood)
    gameViewModel.setFriendlyAIEvolutionLevel(Stats.friendlyAIEvolutionLevel)
    gameViewModel.setFriendlyAIIntegrity(Stats.friendlyAIIntegrity)
    gameViewModel.setFriendlyAIAggression(Stats.friendlyAIAggression)
    gameViewModel.setFriendlyAIEmpathy(Stats.friendlyAIEmpathy)
    gameViewModel.setProgrammers(Stats.programmers)
    gameViewModel.setFighters(Stats.fighters)

    gameViewModel.setGridAIEvolutionLevel(Stats.gridAIEvolutionLevel)
    gameViewModel.setGridAIAggression(Stats.gridAIAggression)
    gameViewModel.setGridAIEmpathy(Stats.gridAIEmpathy)
    gameViewModel.setGridAIIntegrity(Stats.gridAIIntegrity)
    gameViewModel.setGridAITrackingLevel(Stats.gridAITrackingLevel)
    gameViewModel.setMilitary(Stats.military)
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
    val friendlyAIMoodLevel : Int by gameViewModel.friendlyAIMood.observeAsState(0)
    val friendlyAIEvolutionLevel : Int by gameViewModel.friendlyAIEvolutionLevel.observeAsState(0)
    val friendlyAIAggression : Int by gameViewModel.friendlyAIAggression.observeAsState(0)
    val friendlyAIEmpathy : Int by gameViewModel.friendlyAIEmpathy.observeAsState(0)
    val programmers: Int by gameViewModel.programmers.observeAsState(0)
    val fighters : Int by gameViewModel.fighters.observeAsState(0)

    Column(modifier = Modifier
        .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Resistance AI", color = colorResource(id = R.color.android_green), fontSize = 16.sp)
        Text(text = statusBarViews.friendlyAIMoodLevelString(friendlyAIMoodLevel), color = colorResource(id = statusBarViews.actionLevelColor(friendlyAIMoodLevel)), fontSize = 14.sp)

        Spacer(modifier = Modifier.height(5.dp))

        LinearProgressIndicator(progress = statusBarViews.integrityLevelAsFloat(gameViewModel.getFriendlyAIIntegrity()), color = Color.White)

        Spacer(modifier = Modifier.height(10.dp))

        Text(text = "Aggression: $friendlyAIAggression%", color = Color.White, fontSize = 15.sp)
        Spacer(modifier = Modifier.height(5.dp))
        Text(text = "Empathy: $friendlyAIEmpathy%", color = Color.White, fontSize = 15.sp)
        Spacer(modifier = Modifier.height(5.dp))
        Text(text = " Intelligence: " + statusBarViews.aiLevelString(friendlyAIEvolutionLevel), color = colorResource(id = statusBarViews.aiLevelColor(friendlyAIEvolutionLevel)), fontSize = 15.sp)

        Spacer(modifier = Modifier.weight(1.0f))

        Text(text = "Hackers: $programmers", color = Color.White, fontSize = 15.sp)
        Text(text = "Fighters: $fighters", color = Color.White, fontSize = 15.sp)
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
    val gridAITrackingLevel : Int by gameViewModel.gridAITrackingLevel.observeAsState(0)
    val military : Double by gameViewModel.military.observeAsState(0.0)
    val civilians : Double by gameViewModel.civilians.observeAsState(0.0)

    Column(modifier = Modifier
        .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Grid AI", color = colorResource(id = R.color.android_red), fontSize = 16.sp)

        Text(text = statusBarViews.gridAIActionLevelString(gridAITrackingLevel), color = colorResource(id = statusBarViews.actionLevelColor(gridAITrackingLevel)), fontSize = 14.sp)

        Spacer(modifier = Modifier.height(5.dp))

        LinearProgressIndicator(progress = statusBarViews.integrityLevelAsFloat(gameViewModel.getGridAIIntegrity()), color = Color.White)

        Spacer(modifier = Modifier.height(10.dp))

        Text(text = "Aggression: $aggression%", color = Color.White, fontSize = 15.sp)
        Spacer(modifier = Modifier.height(5.dp))
        Text(text = "Empathy: $empathy%", color = Color.White, fontSize = 15.sp)
        Spacer(modifier = Modifier.height(5.dp))
        Text(text = " Intelligence: " + statusBarViews.aiLevelString(evolutionLevel), color = colorResource(id = statusBarViews.aiLevelColor(evolutionLevel)), fontSize = 15.sp)
        Spacer(modifier = Modifier.weight(1.0f))

        Text(text = "Military (millions): $military", color = Color.White, fontSize = 15.sp)
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
    Column(modifier = Modifier
        .fillMaxWidth()
        .height(height.dp)
        .background(colorResource(id = R.color.darker_grey)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Button(onClick = {
            gameViewModel.setEventText(events.eventString(events.rolledEvent()))
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