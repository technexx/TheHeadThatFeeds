package the.head.that.feeds

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Divider
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import the.head.that.feeds.ui.theme.TheHeadThatFeedsTheme

private lateinit var gameViewModel : GameViewModel
@SuppressLint("StaticFieldLeak")
private lateinit var statusBarViews : StatusBarViews

//private var friendlyAILevel = 0

class GameActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        statusBarViews = StatusBarViews(this)
        val gameViewModelInit : GameViewModel by viewModels()
        gameViewModel = gameViewModelInit

        gameViewModel.setFriendlyAILevel(0)
        gameViewModel.setGridAILevel(0)

        setContent {//private var gridAILevel = 0

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
        GameBody  (screenHeight / 2)
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
    Log.i("testClick", "Status Bar Left Row Column recomposed!")

    Column(modifier = Modifier
        .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Resistance AI", color = Color.White, fontSize = 20.sp)
        Spacer(modifier = Modifier.height(40.dp))
        Text(text = statusBarViews.friendlyAILevelString(friendlyAILevel), color = colorResource(id = statusBarViews.friendlyAILevelColor(friendlyAILevel)), fontSize = 20.sp)
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
    Column(modifier = Modifier
        .fillMaxWidth()
    ) {

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
        Text(text = "Grid AI", color = Color.White, fontSize = 20.sp)
        Spacer(modifier = Modifier.height(40.dp))
        Text(text = statusBarViews.gridAILevelString(gridAILevel), color = colorResource(id = statusBarViews.gridAILevelColor(gridAILevel)), fontSize = 20.sp)
    }
}

@Composable
fun GameBody(height: Int) {
    Column(modifier = Modifier
        .fillMaxWidth()
        .height(height.dp)
        .background(colorResource(id = R.color.onyx))
    ) {
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
            var newLevel = gameViewModel.getFriendlyAILevel() + 1
            if (newLevel == 12) newLevel = 0
            gameViewModel.setFriendlyAILevel(newLevel)
        }) {
            Text(text = "Cycle Friendly AI")
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(onClick = {
            var newLevel = gameViewModel.getGridAILevel() + 1
            if (newLevel == 5) newLevel = 0
            gameViewModel.setGridAILevel(newLevel)
        }) {
            Text(text = "Cycle Grid AI")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    TheHeadThatFeedsTheme {
        FullGameScreen()
    }
}