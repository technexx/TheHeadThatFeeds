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
private lateinit var stats : Stats

private fun setViewModelValuesFromStatsClass() {
    gameViewModel.setFriendlyAIEvolutionLevel(stats.friendlyAIEvolutionLevel)
    gameViewModel.setFriendlyAIIntegrity(stats.friendlyAIIntegrity)
    gameViewModel.setGridAIIntegrity(stats.gridAIIntegrity)
    gameViewModel.setGridAITrackingLevel(stats.gridAITrackingLevel)

    gameViewModel.setAggression(stats.aggression)
    gameViewModel.setEmpathy(stats.empathy)
    gameViewModel.setProgrammers(stats.programmers)
    gameViewModel.setFighters(stats.fighters)
    gameViewModel.setCivilians(stats.civilians)

    gameViewModel.setCurrentDay(stats.currentDay)
}

private fun setViewModelObservers(lifeCycleOwner: LifecycleOwner) {
    gameViewModel.friendlyAIEvolutionLevel.observe(lifeCycleOwner) {
        stats.friendlyAIEvolutionLevel = gameViewModel.getFriendlyAIEvolutionLevel()
    }
    gameViewModel.friendlyAIIntegrityLevel.observe(lifeCycleOwner) {
        stats.friendlyAIIntegrity = gameViewModel.getFriendlyAIIntegrity()
    }
    gameViewModel.gridAIIntegrityLevel.observe(lifeCycleOwner) {
        stats.gridAIIntegrity = gameViewModel.getGridAIIntegrity()
    }
    gameViewModel.gridAITrackingLevel.observe(lifeCycleOwner) {
        stats.gridAITrackingLevel = gameViewModel.getGridAITrackingLevel()
    }

    gameViewModel.aggression.observe(lifeCycleOwner) {
        stats.aggression = gameViewModel.getAggression()
    }
    gameViewModel.empathy.observe(lifeCycleOwner) {
        stats.empathy = gameViewModel.getEmpathy()
    }
    gameViewModel.programmers.observe(lifeCycleOwner) {
        stats.programmers = gameViewModel.getProgrammers()
    }
    gameViewModel.fighters.observe(lifeCycleOwner) {
        stats.fighters = gameViewModel.getFighters()
    }
    gameViewModel.civilians.observe(lifeCycleOwner) {
        stats.civilians = gameViewModel.getCivilians()
    }

    gameViewModel.currentDay.observe(lifeCycleOwner) {
        stats.currentDay = gameViewModel.getCurrentDay()
    }
}

private fun saveStatsToSharedPreferences(statsClass: Stats) {
    sharedPrefEditor.putInt("friendlyAIEvolutionLevel", statsClass.friendlyAIEvolutionLevel)
    sharedPrefEditor.putInt("friendlyAIIntegrity", statsClass.friendlyAIIntegrity)
    sharedPrefEditor.putInt("gridAIIntegrity", statsClass.gridAIIntegrity)
    sharedPrefEditor.putInt("aggression", statsClass.aggression)
    sharedPrefEditor.putInt("empathy", statsClass.empathy)
    sharedPrefEditor.putInt("programmers", statsClass.programmers)
    sharedPrefEditor.putInt("fighters", statsClass.fighters)
    sharedPrefEditor.putString("civilians", statsClass.civilians.toString())
    sharedPrefEditor.putInt("currentDay", statsClass.currentDay)
}

private fun setStatsFromSharedPreferences(statsClass: Stats) {
    statsClass.friendlyAIEvolutionLevel = sharedPreferences.getInt("friendlyAIEvolutionLevel", 0)
    statsClass.friendlyAIIntegrity = sharedPreferences.getInt("friendlyAIIntegrity", 0)
    statsClass.gridAIIntegrity = sharedPreferences.getInt("gridAIIntegrity", 0)
    statsClass.aggression = sharedPreferences.getInt("aggression", 0)
    statsClass.empathy = sharedPreferences.getInt("empathy", 0)
    statsClass.programmers = sharedPreferences.getInt("programmers", 0)
    statsClass.fighters = sharedPreferences.getInt("fighters", 0)
    statsClass.civilians = sharedPreferences.getString("civilians", "")!!.toDouble()
    statsClass.currentDay = sharedPreferences.getInt("currentDay", 0)
}

class GameActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val gameViewModelInit : GameViewModel by viewModels()
        gameViewModel = gameViewModelInit

        sharedPreferences = getSharedPreferences("sharedPreferences", MODE_PRIVATE)
        sharedPrefEditor = sharedPreferences.edit()
        statusBarViews = StatusBarViews(this)
        stats = Stats()
        stats.setDefaultStatValues()

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

        LinearProgressIndicator(progress = statusBarViews.healthLevelAsFloat(gameViewModel.getFriendlyAIIntegrity()), color = Color.White)

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

        LinearProgressIndicator(progress = statusBarViews.healthLevelAsFloat(gameViewModel.getGridAIIntegrity()), color = Color.White)

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

        }) {
            Text(text = "Cycle Friendly AI")
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(onClick = {
            cycleGridAI()
        }) {
            Text(text = "Cycle Grid AI")
        }
    }
}

private fun cycleFriendlyAI() {
    var newLevel = gameViewModel.getFriendlyAIEvolutionLevel() + 10
    if (newLevel == 12) newLevel = 0
    gameViewModel.setFriendlyAIEvolutionLevel(newLevel)
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