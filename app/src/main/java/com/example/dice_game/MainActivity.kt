package com.example.dice_game

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    private var playerOverallScore: Int = 0
    private var computerOverallScore: Int = 0
    private var playerTurnScore: Int = 0
    private var computerTurnScore: Int = 0
    private val diceFaceSource = listOf(
        R.drawable.dice1,
        R.drawable.dice2,
        R.drawable.dice3,
        R.drawable.dice4,
        R.drawable.dice5,
        R.drawable.dice6,
    )
    private var playerTurn = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        updateText()
    }

    fun onClickRoll(view: View) {
        if (playerTurn) {
            roll().also {
                when (it) {
                    1 -> {
                        playerOverallScore -= playerTurnScore
                        playerTurnScore = 0
                        computerTurn()
                    }
                    else -> {
                        playerTurnScore += it
                        playerOverallScore += it
                    }
                }
            }
            updateText()
        }
    }

    fun onClickReset(view: View) {
        if (playerTurn) {
            playerOverallScore = 0
            playerTurnScore = 0
            computerOverallScore = 0
            computerTurnScore = 0
            updateText()
        }
    }

    fun onClickHold(view: View) {
        if (playerTurn) {
            playerTurnScore = 0
            updateText()
            computerTurn()
        }
    }

    private fun roll(): Int {
        val i: Int = Random.nextInt(6)
        imageView.setImageResource(diceFaceSource[i])
        return i + 1
    }

    private fun computerTurn() {
        playerTurn = false
        while (computerTurnScore < 20 && !playerTurn) {
            roll().also {
                when (it) {
                    1 -> {
                        computerOverallScore -= computerTurnScore
                        computerTurnScore = 0
                        playerTurn = true
                    }
                    else -> {
                        computerTurnScore += it
                        computerOverallScore += it
                    }
                }
            }
            updateText()
        }
        computerTurnScore = 0
        playerTurn = true
    }

    private fun updateText() {
        val s = if (playerTurnScore == 0) "" else ", Your turn score: $playerTurnScore"
        textView.text = getString(R.string.init_score, playerOverallScore, computerOverallScore, s)
    }
}