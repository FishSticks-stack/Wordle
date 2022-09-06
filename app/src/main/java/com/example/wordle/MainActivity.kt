package com.example.wordle

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import layout.FourLetterWordList
import java.lang.StringBuilder

class MainActivity : AppCompatActivity()
{
    private lateinit var wordToGuess: String
    private var tries = 1

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // get random word from fourLetterWord file
        wordToGuess = FourLetterWordList.getRandomFourLetterWord()

        // guess text
        val guessText = findViewById<TextView>(R.id.guess)
        val answerText = findViewById<TextView>(R.id.answer)
        val hiddenWord = findViewById<TextView>(R.id.randomWord)
        val userGuess = findViewById<EditText>(R.id.enterGuess)
        val buttonEnter = findViewById<Button>(R.id.button)

        hiddenWord.text = wordToGuess


        buttonEnter.setOnClickListener{
            val userWord = userGuess.text.toString().uppercase()
            val checkers = checkGuess(userWord)
//            answerText.text = checkers
//            guessText.text = "guess"
            val saveText = guessText.text
            val saveAnswer = answerText.text

            guessText.text = StringBuilder(saveText).append("Guess #$tries\nGuess #$tries Check\n").toString()
            answerText.text = StringBuilder(saveAnswer).append("$userWord\n$checkers\n").toString()


            // if answer is correct
            if(checkers == "0000"){
                // disable button
                    buttonEnter.isEnabled = false
            }
            else if (tries == 3){
                buttonEnter.isEnabled = false
                Toast.makeText(this, "You have exceeded the number of tries :0", Toast.LENGTH_SHORT).show()
                hiddenWord.visibility = View.VISIBLE
            }
            else{
                //increase the tries
                tries++
            }
        }
    }

    /**
     * Parameters / Fields:
     *   wordToGuess : String - the target word the user is trying to guess
     *   guess : String - what the user entered as their guess
     *
     * Returns a String of 'O', '+', and 'X', where:
     *   'O' represents the right letter in the right place
     *   '+' represents the right letter in the wrong place
     *   'X' represents a letter not in the target word
     */
    private fun checkGuess(guess: String) : String
    {

        var result = ""
        for (i in 0..3) {
            if (guess[i] == wordToGuess[i]) {
                result += "O"
            }
            else if (guess[i] in wordToGuess) {
                result += "+"
            }
            else {
                result += "X"
            }
        }
        return result
    }
}