package com.sofia.soundpool

import android.media.AudioAttributes
import android.media.SoundPool
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var soundPool: SoundPool

    private var soundShoot = 0
    private var soundBoom = 0
    private var soundRifle = 0
    private var isLoaded = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val audioAttr = AudioAttributes.Builder()
            .setUsage(AudioAttributes.USAGE_GAME)
            .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
            .build()

        soundPool = SoundPool.Builder()
            .setMaxStreams(6)
            .setAudioAttributes(audioAttr)
            .build()

        soundShoot = soundPool.load(this, R.raw.shoot, 1)
        soundBoom = soundPool.load(this, R.raw.boom, 1)
        soundRifle = soundPool.load(this, R.raw.rifle, 1)

        soundPool.setOnLoadCompleteListener { _, _, status ->
            if (status == 0) {
                isLoaded = true
            }
        }

        findViewById<Button>(R.id.btnShoot).setOnClickListener {
            if (isLoaded) playSound(soundShoot)
        }

        findViewById<Button>(R.id.btnBoom).setOnClickListener {
            if (isLoaded) playSound(soundBoom)
        }

        // BUTTON COIN
        findViewById<Button>(R.id.btnRifle).setOnClickListener {
            if (isLoaded) playSound(soundRifle)
        }

        // SEMUA SEKALIGUS
        findViewById<Button>(R.id.btnSimultan).setOnClickListener {
            if (isLoaded) {
                playSound(soundShoot)
                playSound(soundBoom)
                playSound(soundRifle)
            }
        }
    }

    private fun playSound(soundId: Int) {
        soundPool.play(soundId, 1f, 1f, 1, 0, 1f)
    }

    override fun onDestroy() {
        super.onDestroy()
        soundPool.release()
    }
}