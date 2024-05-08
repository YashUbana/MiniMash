package com.example.minimash.Games.BrickBreaker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.minimash.R
import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.opengl.Visibility
import android.view.MotionEvent
import android.view.View
import android.view.animation.LinearInterpolator
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import com.example.minimash.MainActivity


class BrickBreaker : AppCompatActivity(), SensorEventListener {

    private lateinit var sensorManager: SensorManager
    private var gyroscopeSensor: Sensor? = null
    private lateinit var scoreText: TextView
    private lateinit var paddle: View
    private lateinit var ball: View
    private lateinit var brickContainer: LinearLayout



    private var ballX = 0f
    private var ballY = 0f
    private var ballSpeedX = 0f

    private var ballSpeedY = 0f

    private var paddleX = 0f

    private var score = 0


    private val paddleSpeed = 50


    private val brickRows = 9

    private val brickColumns = 10
    private val brickWidth = 100
    private val brickHeight = 40
    private val brickMargin = 4

    private var isBallLaunched = false

    private var lives = 3

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_brick_breaker)

        scoreText = findViewById(R.id.scoreText)
        paddle = findViewById(R.id.paddle)
        ball = findViewById(R.id.ball)
        brickContainer = findViewById(R.id.brickContainer)


        val newgame = findViewById<Button>(R.id.newgame)
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager

        // Get gyroscope sensor
        gyroscopeSensor = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE)


        newgame.setOnClickListener {
            initializeBricks()
            start()
            //  movepaddle()
            newgame.visibility = View.INVISIBLE


        }
    }

    override fun onResume() {
        super.onResume()
        // Register gyroscope sensor listener
        gyroscopeSensor?.let {
            sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_NORMAL)
        }
    }
    override fun onPause() {
        super.onPause()
        // Unregister gyroscope sensor listener to save battery
        sensorManager.unregisterListener(this)
    }


    private fun initializeBricks() {
        val brickWidthWithMargin = (brickWidth + brickMargin).toInt()
        brickContainer.removeAllViews()

        for (row in 0 until brickRows) {
            val rowLayout = LinearLayout(this)
            val params = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            rowLayout.layoutParams = params

            for (col in 0 until brickColumns) {
                val brick = View(this)
                val brickParams = LinearLayout.LayoutParams(brickWidth, brickHeight)
                brickParams.setMargins(brickMargin, brickMargin, brickMargin, brickMargin)
                brick.layoutParams = brickParams
                brick.setBackgroundResource(R.drawable.circle_background)
                rowLayout.addView(brick)

            }

            brickContainer.addView(rowLayout)
        }
    }

    private fun moveBall() {
        ballX += ballSpeedX
        ballY += ballSpeedY

        ball.x = ballX
        ball.y = ballY
    }

    private fun movePaddle(x: Float) {
        paddleX = x - paddle.width / 2
        paddle.x = paddleX
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun checkCollision() {

        val screenWidth = resources.displayMetrics.widthPixels.toFloat()
        val screenHeight = resources.displayMetrics.heightPixels.toFloat()

        if (ballX <= 0 || ballX + ball.width >= screenWidth) {
            ballSpeedX *= -1
        }

        if (ballY <= 0) {
            ballSpeedY *= -1
        }

        // Check collision with paddle
        if (ballY + ball.height >= paddle.y && ballY + ball.height <= paddle.y + paddle.height
            && ballX + ball.width >= paddle.x && ballX <= paddle.x + paddle.width
        ) {
            ballSpeedY *= -1
            score++
            scoreText.text = "Score: $score"
        }

        // Check collision with bottom wall (paddle misses the ball)
        if (ballY + ball.height >= screenHeight) {
            // Game logic for when the ball goes past the paddle
            // You can implement actions such as reducing lives, resetting the ball, or displaying a message
            resetBallPosition() // Example: Reset the ball to its initial position
        }

        // Check collision with bricks
        for (row in 0 until brickRows) {
            val rowLayout = brickContainer.getChildAt(row) as LinearLayout

            val rowTop = rowLayout.y + brickContainer.y
            val rowBottom = rowTop + rowLayout.height

            for (col in 0 until brickColumns) {
                val brick = rowLayout.getChildAt(col) as View

                if (brick.visibility == View.VISIBLE) {
                    val brickLeft = brick.x + rowLayout.x
                    val brickRight = brickLeft + brick.width
                    val brickTop = brick.y + rowTop
                    val brickBottom = brickTop + brick.height

                    if (ballX + ball.width >= brickLeft && ballX <= brickRight
                        && ballY + ball.height >= brickTop && ballY <= brickBottom
                    ) {
                        brick.visibility = View.INVISIBLE
                        ballSpeedY *= -1
                        score++
                        scoreText.text = "Score: $score"
                        return  // Exit the function after finding a collision with a brick
                    }
                }
            }
        }

        // Check collision with bottom wall (paddle misses the ball)
        if (ballY + ball.height >= screenHeight - 100) {
            // Reduce the number of lives
            lives--

            if (lives > 0 ) {
                Toast.makeText(this, "$lives balls left ", Toast.LENGTH_SHORT).show()
            }


            paddle.setOnTouchListener { _, event ->
                when (event.action) {
                    MotionEvent.ACTION_MOVE -> {
                        movePaddle(event.rawX)







                    }

                }
                true



            }
            if (ballY + ball.height >= paddle.y && ballY + ball.height <= paddle.y + paddle.height
                && ballX + ball.width >= paddle.x && ballX <= paddle.x + paddle.width
            ) {
                ballSpeedY *= -1
                score++
                scoreText.text = "Score: $score"
            }

            if (lives <= 0) {
                // Game over condition: No more lives left
                gameOver()
                return
            } else {
                // Reset the ball to its initial position
                resetBallPosition()
                start()

            }
        }

    }

    private fun resetBallPosition() {
        // Reset the ball to its initial position
        val displayMetrics = resources.displayMetrics
        val screenDensity = displayMetrics.density

        val screenWidth = displayMetrics.widthPixels.toFloat()
        val screenHeight = displayMetrics.heightPixels.toFloat()

        ballX = screenWidth / 2 - ball.width / 2
        ballY = screenHeight / 2 - ball.height / 2 +525

        ball.x = ballX
        ball.y = ballY

        // Reset the ball's speed
        ballSpeedX = 0 * screenDensity
        ballSpeedY = 0 * screenDensity
        paddleX = screenWidth / 2 - paddle.width / 2
        paddle.x = paddleX
        // Implement any additional logic you need, such as reducing lives or showing a message
        // when the ball goes past the paddle.

    }

    private fun resetBricks() {
        for (row in 0 until brickRows) {
            val rowLayout = brickContainer.getChildAt(row) as LinearLayout
            for (col in 0 until brickColumns) {
                val brick = rowLayout.getChildAt(col) as View
                brick.visibility = View.VISIBLE
            }
        }
    }

    private fun gameOver() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }




    @SuppressLint("ClickableViewAccessibility")
//    private fun movepaddle() {
//
//        paddle.setOnTouchListener { _, event ->
//            when (event.action) {
//                MotionEvent.ACTION_MOVE -> {
//                    movePaddle(event.rawX)
//                }
//
//            }
//            true
//        }
//    }


    private fun start() {
        val displayMetrics = resources.displayMetrics
        val screenDensity = displayMetrics.density
        ballSpeedX = 3 * screenDensity
        ballSpeedY = -3 * screenDensity

//        movepaddle()

        val screenWidth = displayMetrics.widthPixels.toFloat()
        val screenHeight = displayMetrics.heightPixels.toFloat()

        paddleX = screenWidth / 2 - paddle.width / 2
        paddle.x = paddleX

        ballX = screenWidth / 2 - ball.width / 2
        ballY = screenHeight / 2 - ball.height / 2

        val brickHeightWithMargin = (brickHeight
                + brickMargin * screenDensity).toInt()



        val animator = ValueAnimator.ofFloat(0f, 1f)
        animator.duration = Long.MAX_VALUE
        animator.interpolator = LinearInterpolator()
        animator.addUpdateListener { animation ->
            moveBall()
            checkCollision()
        }
        animator.start()
    }

    override fun onSensorChanged(event: SensorEvent?) {
        event?.let {
            // Process gyroscope data
            if (it.sensor.type == Sensor.TYPE_GYROSCOPE) {
                val deltaX = -it.values[0] * paddleSpeed // Adjust paddle movement sensitivity as needed
                // Update paddle position
                updatePaddlePosition(deltaX)
            }
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        // Do nothing
    }

    private fun updatePaddlePosition(deltaX: Float) {
        val displayMetrics = resources.displayMetrics
        val screenDensity = displayMetrics.density
        val screenWidth = displayMetrics.widthPixels.toFloat()
        val screenHeight = displayMetrics.heightPixels.toFloat()
        paddleX += deltaX
        // Limit paddle movement within screen bounds
        if (paddleX < 0) {
            paddleX = 0f
        } else if (paddleX > screenWidth - paddle.width) {
            paddleX = (screenWidth - paddle.width).toFloat()
        }
        paddle.x = paddleX
    }

}