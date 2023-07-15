package com.example.concurr

import android.os.Bundle
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.concurr.ui.theme.ConcurrTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.newSingleThreadContext

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ConcurrTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting()
                }
            }
        }
    }
}

@Composable
fun Greeting(modifier: Modifier = Modifier) {


    var player1 by remember {
        mutableStateOf(RaceParticipants("player1", 1))
    }
    var player2 by remember {
        mutableStateOf(RaceParticipants("Player2", 2))
    }
    var progressing by remember {
        mutableStateOf(false)
    }

    if(progressing){
        LaunchedEffect(null) {
            coroutineScope {
                launch {

                    while(player1.progressFactor<1){
                        player1.progressFactor+=0.1F
                        delay(500)
                    }
                }
                launch {

                    while(player2.progressFactor<1){
                        player2.progressFactor+=0.2F
                        delay(500)
                    }
                }
            }
            progressing = !progressing

        }
    }


    Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.SpaceBetween, modifier = modifier) {
        Text(text = stringResource(R.string.run_a_race), fontSize = 24.sp, modifier = Modifier.padding(20.dp))
        Column(verticalArrangement = Arrangement.spacedBy(20.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            Image(painter = painterResource(id = R.drawable.ic_walk), contentDescription = null )
            Row(modifier = Modifier.padding(start = 10.dp, end = 15.dp) , horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                Text(text = "Player 1", fontSize = 20.sp)
                Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(3.dp)) {
                    LinearProgressIndicator(modifier = Modifier
                        .fillMaxWidth()
                        .height(15.dp), progress = player1.progressFactor)
                    Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
                        Text(text = (player1.progressFactor * 100).toInt().toString()+"%", fontSize = 20.sp)
                        Text(text = "100%", fontSize = 20.sp)
                    }
                }

            }
            Row(modifier = Modifier.padding(start = 10.dp, end = 15.dp) , horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                Text(text = "Player 2", fontSize = 20.sp)
                Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(3.dp)) {
                    LinearProgressIndicator(modifier = Modifier
                        .fillMaxWidth()
                        .height(15.dp), progress = player2.progressFactor)
                    Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
                        Text(text = (player2.progressFactor * 100).toInt().toString()+"%", fontSize = 20.sp)
                        Text(text = "100%", fontSize = 20.sp)
                    }
                }

            }
            Row(horizontalArrangement = Arrangement.SpaceAround, modifier = Modifier
                .fillMaxWidth()
                .padding(start = 50.dp, end = 40.dp)) {

                    Button(onClick = {
                        if(player1.progressFactor >= 1.0F)  {
                            player1.progressFactor=0.0F
                            player2.progressFactor=0.0F
                        }
                            progressing = !progressing}) {
                        if (progressing) Text(text = "Pause")
                        else Text(text = "Start")
                    }
                Button(onClick = {
                    player1.progressFactor =0.0F
                    player2.progressFactor = 0.0F
                    progressing = false
                }) {
                    Text(text = "Reset")
                }
            }
            Spacer(modifier = Modifier.padding(40.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun preview() {
    Greeting(Modifier.fillMaxSize())
}