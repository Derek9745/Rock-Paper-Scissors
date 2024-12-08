package com.example.rock_paper_scissors

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.rock_paper_scissors.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityMainBinding

    var playerScore = 0
    var ComputerScore = 0
    var isPlaying = false
    var rps_Computer = ""
    var playerChoice: String = ""
    val ComputerList = listOf("Rock","Paper","Scissors")



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)


        binding.PlayerView.visibility = View.INVISIBLE
        binding.ComputerView.visibility = View.INVISIBLE



        enableEdgeToEdge()
        setContentView(binding.root)

        binding.rockButton.setOnClickListener(this)
        binding.paperButton.setOnClickListener(this)
        binding.scissorsButton.setOnClickListener(this)
        binding.StartGame.setOnClickListener {
            isPlaying = true
            binding.StartGame.visibility = View.INVISIBLE
            binding.rockButton.visibility = View.VISIBLE
            binding.paperButton.visibility = View.VISIBLE
            binding.scissorsButton.visibility = View.VISIBLE
        }
        updateScores()
    }



    override fun onClick(v: View?) {
        if (!isPlaying) return // Prevent action if game is not started

         playerChoice = when (v?.id){
            binding.rockButton.id -> "Rock"
            binding.paperButton.id -> "Paper"
            binding.scissorsButton.id -> "Scissors"
            else -> ""
        }

        getComputerChoice()
        updateUI()
        chooseWinner()
        updateScores()
        checkGameEnd()


    }

    fun getComputerChoice(){
        rps_Computer = ComputerList.random()

    }

    fun chooseWinner(){
        if (playerChoice == "Rock" && rps_Computer == "Paper") {
            ComputerScore += 1
        } else if (playerChoice == "Rock" && rps_Computer == "Scissors") {
            playerScore += 1
        } else if (playerChoice == "Rock" && rps_Computer == "Rock") {
            showSnackbar()
        } else if (playerChoice == "Paper" && rps_Computer == "Rock"){
            playerScore +=1
        } else if (playerChoice == "Paper" && rps_Computer == "Scissors"){
            ComputerScore += 1
        } else if (playerChoice == "Paper" && rps_Computer == "Paper"){
            showSnackbar()
        }else if (playerChoice == "Scissors" && rps_Computer == "Paper"){
            playerScore +=1
        }else if (playerChoice == "Scissors" && rps_Computer == "Rock"){
            ComputerScore+=1
        }else if (playerChoice == "Scissors" && rps_Computer == "Scissors"){
            showSnackbar()
        }
        getComputerChoice()

    }

    private fun checkGameEnd(){
        when{
            playerScore >=10->{
                val mySnackbar = Snackbar.make(binding.constraintLayout, "YOU WIN!", Snackbar.LENGTH_SHORT)
                mySnackbar.show()
                resetGame()
                binding.StartGame.setText("Play Again?")
            }
            ComputerScore >= 10-> {
                val mySnackbar = Snackbar.make(binding.constraintLayout, "COMPUTER WINS!", Snackbar.LENGTH_SHORT)
                mySnackbar.show()
                resetGame()
                binding.StartGame.setText("Play Again?")
            }
        }
    }

    fun updateScores(){
        binding.PlayerScore.text = "Player_Score: $playerScore"
        binding.ComputerScore.text = "Computer_Score: $ComputerScore"
    }

    fun resetGame() {
        isPlaying = false
        playerScore = 0
        ComputerScore = 0
        binding.StartGame.visibility = View.VISIBLE
        binding.rockButton.visibility = View.INVISIBLE
        binding.paperButton.visibility = View.INVISIBLE
        binding.scissorsButton.visibility = View.INVISIBLE
        updateScores()
    }

    private fun showSnackbar() {
        val mySnackbar = Snackbar.make(binding.constraintLayout, "Tie game, Please choose again", Snackbar.LENGTH_SHORT)
        mySnackbar.show()
    }

    fun updateUI(){
        binding.PlayerView.visibility = View.VISIBLE
        binding.ComputerView.visibility = View.VISIBLE

        binding.PlayerView.setImageResource(
            when (playerChoice) {
                "Rock" -> R.drawable.rock
                "Paper" -> R.drawable.paper
                "Scissors" -> R.drawable.scissors
                else -> R.drawable.rock // Fallback image
            }
        )

        binding.ComputerView.setImageResource(
            when (rps_Computer) {
                "Rock" -> R.drawable.rock
                "Paper" -> R.drawable.paper
                "Scissors" -> R.drawable.scissors
                else -> R.drawable.rock // Fallback image
            }
        )


    }

}