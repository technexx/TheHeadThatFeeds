package the.head.that.feeds

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.SnackbarDefaults.backgroundColor
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

        val gameIntent = Intent(this, GameActivity::class.java)
        startActivity(intent)
    }
}