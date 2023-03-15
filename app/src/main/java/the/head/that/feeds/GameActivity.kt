package the.head.that.feeds

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
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

class GameActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        statusBarViews = StatusBarViews(this)
        val gameViewModelInit : GameViewModel by viewModels()
        gameViewModel = gameViewModelInit

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
    Column(modifier = Modifier
        .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Resistance AI", color = Color.White, fontSize = 20.sp)
        Spacer(modifier = Modifier.height(40.dp))
        Text(text = statusBarViews.friendlyAILevelString(0), color = Color.White, fontSize = 20.sp)
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
    Column(modifier = Modifier
        .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Grid AI", color = Color.White, fontSize = 20.sp)
        Spacer(modifier = Modifier.height(40.dp))
        Text(text = statusBarViews.enemyAILevelString(0), color = Color.White, fontSize = 20.sp)
    }
}

//@Composable
//fun StatusBarRightRow(width: Int) {
//    Row (modifier = Modifier
//        .fillMaxHeight()
//        .background(colorResource(id = R.color.black))
//        .width(width.dp),
//    ) {
//        StatusBarRightRowColumn()
//    }
//}
////
//@Composable
//fun StatusBarRightRowColumn() {
//    Column(
//        horizontalAlignment = Alignment.CenterHorizontally
//    ) {
//        Text(text = "Grid AI", color = Color.White, fontSize = 20.sp)
//        Spacer(modifier = Modifier.height(40.dp))
//        Text(text = statusBarViews.enemyAILevelString(0), color = Color.White, fontSize = 20.sp)
//    }
//}

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
        .background(colorResource(id = R.color.darker_grey))
    ) {
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    TheHeadThatFeedsTheme {
        FullGameScreen()
    }
}