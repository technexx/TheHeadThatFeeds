package the.head.that.feeds

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import the.head.that.feeds.ui.theme.TheHeadThatFeedsTheme

//Todo: All humanity is under control of AI. It controls all media access. Progress is made by poking holes/sapping power/exploiting blind spots.
//Todo: Can locate other "off the grid" humans to help.
//Todo: As player AI evolves, it gains new abilities.
//Todo: As enemy AI gets closer to locating player, it gains new abilities.

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
        GameStatusBar(screenHeight / 4)
        GameBody  (screenHeight / 2)
        GameInteraction(screenHeight / 4)
    }
}

@Composable
fun GameStatusBar(height: Int) {
    Column(modifier = Modifier
        .fillMaxWidth()
        .height(height.dp)
        .background(colorResource(id = R.color.blue))
    ) {
    }
}

@Composable
fun GameBody(height: Int) {
    Column(modifier = Modifier
        .fillMaxWidth()
        .height(height.dp)
        .background(colorResource(id = R.color.white))
    ) {
    }
}

@Composable
fun GameInteraction(height: Int) {
    Column(modifier = Modifier
        .fillMaxWidth()
        .height(height.dp)
        .background(colorResource(id = R.color.android_yellow))
    ) {
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    TheHeadThatFeedsTheme {
    }
}