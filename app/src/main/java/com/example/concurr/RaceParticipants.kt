package com.example.concurr

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import kotlinx.coroutines.delay

public class RaceParticipants(
    name: String,
    val progressIncrement: Int,
) {
    var currentProgress by mutableStateOf(0)
    var progressFactor by mutableStateOf( currentProgress / 10.0F)

    fun reset() {
        currentProgress = 0
    }

}