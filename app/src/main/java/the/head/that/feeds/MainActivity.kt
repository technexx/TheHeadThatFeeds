package the.head.that.feeds

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
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

//Todo: All humanity is under control of AI. It controls all media access. Progress is made by poking holes/sapping power/exploiting blind spots.
//Todo: Can locate other "off the grid" humans to help.

//Todo: As player AI evolves, it gains new abilities.
    //Todo: Requires more power as it grows.
//Todo: As enemy AI gets closer to locating player, it gains new abilities. Maybe relate to its animal.

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TheHeadThatFeedsTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(),
                    color = colorResource(id = R.color.onyx)) {
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
        GameStatsBarSubRows(Arrangement.Center, Alignment.CenterVertically, backgroundColor = R.color.android_yellow)
        GameStatsBarSubRows(Arrangement.Center, Alignment.CenterVertically, backgroundColor = R.color.android_magenta)
        GameStatsBarSubRows(Arrangement.Center, Alignment.CenterVertically, backgroundColor = R.color.android_green)
    }
}

@Composable
fun GameStatsBarSubRows(arrangement: Arrangement.Horizontal, alignment: Alignment.Vertical, backgroundColor: Int) {
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp

    Row (modifier = Modifier
        .fillMaxHeight()
        .background(colorResource(id = backgroundColor))
        .width((screenWidth / 3).dp),
        horizontalArrangement = arrangement,
        verticalAlignment = alignment
        ) {
        StatusBarTexts(textColor = colorResource(id = R.color.white))
    }
}

@Composable
fun StatusBarTexts(textColor: Color) {
    Text(text = "Resistance AI", color = textColor)
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
    }
}