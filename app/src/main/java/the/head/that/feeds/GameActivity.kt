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
import the.head.that.feeds.ui.theme.TheHeadThatFeedsTheme

private lateinit var gameViewModel : GameViewModel
@SuppressLint("StaticFieldLeak")
private lateinit var valuesToStrings : ValuesToStrings

class GameActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        valuesToStrings = ValuesToStrings(this)
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

@Composable
fun FullGameScreen() {
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp

    Column() {
        GameStatusBarColumn(screenHeight / 4)
        Divider(thickness = 2.dp, color = Color.White)
        GameBody  (screenHeight / 2)
        Divider(thickness = 2.dp, color = Color.White)
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
    StatusBarMiddleRow(width = screenWidth/3)
    StatusBarRightRow(width = screenWidth/3)
}

@Composable
fun StatusBarLeftRow(width: Int) {
    Row (modifier = Modifier
        .fillMaxHeight()
        .background(colorResource(id = R.color.android_yellow))
        .width(width.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {

    }
}

@Composable
fun StatusBarMiddleRow(width: Int) {
    Row (modifier = Modifier
        .fillMaxHeight()
        .background(colorResource(id = R.color.android_magenta))
        .width(width.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        StatusBarMiddleRowColumn()
    }
}

@Composable
fun StatusBarMiddleRowColumn() {
    Column(
        verticalArrangement = Arrangement.SpaceAround
    ) {
        Text(text = "Resistance AI", color = Color.White)
        Spacer(modifier = Modifier.height(40.dp))
        Text(text = valuesToStrings.friendlyAILevelString(0), color = Color.White)
    }
}

@Composable
fun StatusBarRightRow(width: Int) {
    Row (modifier = Modifier
        .fillMaxHeight()
        .background(colorResource(id = R.color.android_green))
        .width(width.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {

    }
}

@Composable
fun StatusBarTexts(text: String, textColor: Color) {
    Text(text = text, color = textColor)
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