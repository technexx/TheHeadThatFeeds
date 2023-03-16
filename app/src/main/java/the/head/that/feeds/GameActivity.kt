package the.head.that.feeds

import android.annotation.SuppressLint
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
import the.head.that.feeds.ui.theme.TheHeadThatFeedsTheme

//Todo: Progress is made by poking holes/sapping power/exploiting blind spots.
//Todo: Can locate other "off the grid" humans to help.

//Todo: As player AI evolves, it gains new abilities.
//Todo: Requires more power as it grows.
//Todo: As enemy AI gets closer to locating player, it gains new abilities. Maybe relate to its animal.
//Todo: Can be in "Hunting" mode, where it gets closer to finding you. Events can disrupt this.

//Todo: Scientist numbers determine Resistance AI progress.
//Todo: Hacker numbers defend against Grid AI attacks.
//Todo: Hunter numbers defend against patrols + elements.

//Todo: Space below AIs can have status effects, etc.

private lateinit var gameViewModel : GameViewModel
@SuppressLint("StaticFieldLeak")
private lateinit var statusBarViews : StatusBarViews

class GameActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        statusBarViews = StatusBarViews(this)
        val gameViewModelInit : GameViewModel by viewModels()
        gameViewModel = gameViewModelInit

        gameViewModel.setFriendlyAILevel(0)
        gameViewModel.setGridAILevel(0)
        gameViewModel.setScientists(2)
        gameViewModel.setHackers(2)
        gameViewModel.setHunters(2)
        gameViewModel.setCurrentDay(0)
        gameViewModel.setEnemyActivity(0)

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

    StatusBarLeftRow(width = screenWidth/3)
    Divider(color = Color.White, modifier = Modifier
        .fillMaxHeight()
        .width(1.dp))
    StatusBarMiddleRow(width = screenWidth/3)
    Divider(color = Color.White, modifier = Modifier
        .fillMaxHeight()
        .width(1.dp))
    StatusBarRightRow(width = screenWidth/3)
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
    val friendlyAILevel : Int by gameViewModel.friendlyAILevel.observeAsState(0)

    Column(modifier = Modifier
        .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Resistance AI", color = Color.White, fontSize = 18.sp)
        Spacer(modifier = Modifier.height(40.dp))
        Text(text = statusBarViews.friendlyAILevelString(friendlyAILevel), color = colorResource(id = statusBarViews.friendlyAILevelColor(friendlyAILevel)), fontSize = 18.sp)
        Spacer(modifier = Modifier.height(10.dp))
        LinearProgressIndicator(progress = statusBarViews.friendlyAILevelProgress(0.5f, 1), color = Color.White)
    }
}

@Composable
fun StatusBarMiddleRow(width: Int) {
    Row (modifier = Modifier
        .fillMaxHeight()
        .background(colorResource(id = R.color.black))
        .width(width.dp),
    ) {
        StatusBarMiddleRowColumn()
    }
}

@Composable
fun StatusBarMiddleRowColumn() {
    val scientists : Int by gameViewModel.scientists.observeAsState(0)
    val hackers : Int by gameViewModel.hackers.observeAsState(0)
    val hunters : Int by gameViewModel.hunters.observeAsState(0)
    val enemyActivity : Int by gameViewModel.enemyActivity.observeAsState(0)

    Column(modifier = Modifier
        .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(text = "Scientists: $scientists", color = Color.White, fontSize = 18.sp)
        Text(text = "Hackers: $hackers", color = Color.White, fontSize = 18.sp)
        Text(text = "Hunters: $hunters", color = Color.White, fontSize = 18.sp)

        Spacer(modifier = Modifier.height(20.dp))

        Text(text = "Enemy Activity:", color = Color.White, fontSize = 18.sp)
        Spacer(modifier = Modifier.height(12.dp))
        Text(text = statusBarViews.enemyLevelString(enemyActivity), color = colorResource(
        id = statusBarViews.enemyActivityLevelColor(enemyActivity)), fontSize = 18.sp)
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
    val gridAILevel : Int by gameViewModel.gridAILevel.observeAsState(0)

    Column(modifier = Modifier
        .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Grid AI", color = Color.White, fontSize = 18.sp)
        Spacer(modifier = Modifier.height(40.dp))
        Text(text = statusBarViews.gridAILevelString(gridAILevel), color = colorResource(id = statusBarViews.gridAILevelColor(gridAILevel)), fontSize = 18.sp)
        Spacer(modifier = Modifier.height(10.dp))
        LinearProgressIndicator(progress = statusBarViews.enemyAILevelProgress(0.5f, 1), color = Color.White)
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
        Text(text = "Day:   $currentDay", color = Color.White, fontSize = 22.sp, fontWeight = FontWeight.Bold)
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

        }) {
            Text(text = "Cycle Grid AI")
        }
    }
}

private fun cycleFriendlyAI() {
    var newLevel = gameViewModel.getFriendlyAILevel() + 1
    if (newLevel == 12) newLevel = 0
    gameViewModel.setFriendlyAILevel(newLevel)
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