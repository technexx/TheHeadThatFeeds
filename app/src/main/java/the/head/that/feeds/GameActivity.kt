package the.head.that.feeds

import android.annotation.SuppressLint
import android.os.Bundle
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
import the.head.that.feeds.ui.theme.TheHeadThatFeedsTheme

//Todo: As player AI evolves, it gains new abilities.
//Todo: As enemy AI gets closer to locating player, it gains new abilities. Maybe relate to its animal.

//Todo: Friendly AI will become more autonomous as it evolves.
//Todo: Levels of Aggression, Empathy (and its evolved intelligence) effect behavior.

//Todo: Grid AI has Integrity value.
//Todo: Civilian and/or survivors can be casualties that affect end score.
    //Todo: Civilians are general score. Survivors also used for missions.
//Todo: Both Friendly and Grid AI can cause civilian deaths.

//Todo: Should be a net-loss with continuation of days, to encourage player to be proactive.

//Todo: Idea: Instead of health, integrity affects monitoring of Grid AI. Lower monitoring = better chance to evolve (can set it as a choice, a natural evolution, or both).

///Todo: Replace player Integrity with Power (or just "Network Integrity"). Friendly AI needs a certain amount, increasing with its intelligence.

private lateinit var gameViewModel : GameViewModel
@SuppressLint("StaticFieldLeak")
private lateinit var statusBarViews : StatusBarViews

class GameActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        statusBarViews = StatusBarViews(this)
        val gameViewModelInit : GameViewModel by viewModels()
        gameViewModel = gameViewModelInit

        gameViewModel.setFriendlyAIEvolutionLevel(0)
        gameViewModel.setFriendlyAIHealth(100.0)
        gameViewModel.setGridAILevel(0)
        gameViewModel.setGridAITrackingProgress(0)
        gameViewModel.setAggression(0)
        gameViewModel.setEmpathy(0)
        gameViewModel.setResistanceMembers(1000)
        gameViewModel.setCivilians(42.0)
        gameViewModel.setCurrentDay(1)
        gameViewModel.setGridAIHealth(100.0)

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
    Row (modifier = Modifier
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
    val resistanceMembers : Int by gameViewModel.resistanceMembers.observeAsState(0)

    Column(modifier = Modifier
        .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Resistance AI", color = colorResource(id = R.color.android_green), fontSize = 17.sp)

        Spacer(modifier = Modifier.height(10.dp))

        Text(text = "Integrity", color = Color.White, fontSize = 16.sp)

        Spacer(modifier = Modifier.height(10.dp))

        LinearProgressIndicator(progress = statusBarViews.healthLevelAsFloat(gameViewModel.getFriendlyAIHealth()), color = Color.White)

        Spacer(modifier = Modifier.height(10.dp))

        Text(text = "Aggression: $aggression%", color = Color.White, fontSize = 16.sp)
        Spacer(modifier = Modifier.height(5.dp))
        Text(text = "Empathy: $empathy%", color = Color.White, fontSize = 16.sp)
        Spacer(modifier = Modifier.height(5.dp))
        Text(text = " Intelligence: " + statusBarViews.friendlyAILevelString(friendlyAILevel), color = colorResource(id = statusBarViews.friendlyAILevelColor(friendlyAILevel)), fontSize = 16.sp)

        Spacer(modifier = Modifier.weight(1.0f))

        Text(text = "Guerrillas: $resistanceMembers", color = Color.White, fontSize = 16.sp)
    }
}

@Composable
fun StatusBarRightRow(width: Int) {
    Row (modifier = Modifier
        .fillMaxHeight()
        .background(colorResource(id = R.color.black))
        .width(width.dp),
    ) {
        StatusBarRightRowColumn()
    }
}

@Composable
fun StatusBarRightRowColumn() {
    val gridAILevel : Int by gameViewModel.gridAIActionLevel.observeAsState(0)
    val civilians : Double by gameViewModel.civilians.observeAsState(42.0)

    Column(modifier = Modifier
        .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Grid AI", color = colorResource(id = R.color.android_red), fontSize = 17.sp)

        Spacer(modifier = Modifier.height(10.dp))

        Text(text = "Integrity", color = Color.White, fontSize = 16.sp)

        Spacer(modifier = Modifier.height(10.dp))

        LinearProgressIndicator(progress = statusBarViews.healthLevelAsFloat(gameViewModel.getGridAIHealth()), color = Color.White)

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
    var newLevel = gameViewModel.getFriendlyAIEvolutionLevel() + 1
    if (newLevel == 12) newLevel = 0
    gameViewModel.setFriendlyAIEvolutionLevel(newLevel)
}

private fun cycleGridAI() {
    var newLevel = gameViewModel.getGridAILevel() + 1
    if (newLevel == 5) newLevel = 0
    gameViewModel.setGridAILevel(newLevel)
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    TheHeadThatFeedsTheme {
        FullGameScreen()
    }
}