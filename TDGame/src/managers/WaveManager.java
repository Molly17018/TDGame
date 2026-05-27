/*
 * Decompiled with CFR 0.151.
 */
package managers;

import java.util.ArrayList;
import java.util.Arrays;

import events.Wave;
import scenes.Playing;

public class WaveManager {
    private Playing playing;
    private ArrayList<Wave> waves = new ArrayList();
    private int enemySpawnTickLimit;
    private int enemySpawnTick = this.enemySpawnTickLimit = 120;
    private int enemyIndex;
    private int waveIndex;
    private int waveTickLimit = 600;
    private int waveTick = 0;
    private boolean waveStartTimer;
    private boolean waveTickTimerOver;

    public WaveManager(Playing playing) {
        this.playing = playing;
        this.createWaves();
    }

    public void update() {
        if (this.enemySpawnTick < this.enemySpawnTickLimit) {
            ++this.enemySpawnTick;
        }
        if (this.waveStartTimer) {
            ++this.waveTick;
            if (this.waveTick >= this.waveTickLimit) {
                this.waveTickTimerOver = true;
            }
        }
    }

    public void increaseWaveIndex() {
        ++this.waveIndex;
        this.waveTick = 0;
        this.waveTickTimerOver = false;
        this.waveStartTimer = false;
    }

    public void startWaveTimer() {
        this.waveStartTimer = true;
    }

    public boolean isWaveTimerOver() {
        return this.waveTickTimerOver;
    }

    private void createWaves() {
        this.waves.add(new Wave(new ArrayList<>(Arrays.asList(0, 0, 0, 0, 0, 0, 0, 0, 0, 0))));
        this.waves.add(new Wave(new ArrayList<>(Arrays.asList(0, 0, 0, 0, 0, 0, 0, 0, 0, 1))));
        this.waves.add(new Wave(new ArrayList<>(Arrays.asList(0, 0, 0, 0, 0, 1, 1, 1, 1, 3))));
        this.waves.add(new Wave(new ArrayList<>(Arrays.asList(3, 3, 3, 3, 3, 3, 3, 3, 3, 3))));
        this.waves.add(new Wave(new ArrayList<>(Arrays.asList(0, 0, 0, 0, 0, 0, 0, 1, 1, 2))));
        this.waves.add(new Wave(new ArrayList<>(Arrays.asList(1, 1, 1, 0, 0, 0, 0, 0, 0, 0))));
        this.waves.add(new Wave(new ArrayList<>(Arrays.asList(1, 1, 1, 3, 3, 0, 0, 0, 0, 0))));
        this.waves.add(new Wave(new ArrayList<>(Arrays.asList(1, 1, 1, 3, 3, 3, 3, 0, 0, 0))));
        this.waves.add(new Wave(new ArrayList<>(Arrays.asList(3, 3, 3, 1, 1, 3, 3, 3, 0, 0))));
        this.waves.add(new Wave(new ArrayList<>(Arrays.asList(3, 3, 3, 3, 3, 3, 1, 1, 2, 2))));
    }

    public void resetEnemyIndex() {
        this.enemyIndex = 0;
    }

    public boolean isTimeForNewEnemy() {
        return this.enemySpawnTick >= this.enemySpawnTickLimit;
    }

    public boolean isThereMoreEnemiesInWave() {
        return this.enemyIndex < this.waves.get(this.waveIndex).getEnemyList().size();
    }

    public boolean isThereMoreWaves() {
        return this.waveIndex + 1 < this.waves.size();
    }

    public boolean isWaveTimerStarted() {
        return this.waveStartTimer;
    }

    public int getNextEnemy() {
        this.enemySpawnTick = 0;
        return this.waves.get(this.waveIndex).getEnemyList().get(this.enemyIndex++);
    }

    public ArrayList<Wave> getWaves() {
        return this.waves;
    }

    public int getWaveIndex() {
        return this.waveIndex;
    }

    public float getTimeLeft() {
        float ticksLeft = this.waveTickLimit - this.waveTick;
        return ticksLeft / 60.0f;
    }

    public int getEnemyIndex() {
        return this.enemyIndex;
    }

    public void reset() {
        this.waves.clear();
        this.createWaves();
        this.waveIndex = 0;
        this.enemyIndex = 0;
        this.waveStartTimer = false;
        this.waveTickTimerOver = false;
        this.waveTick = 0;
        this.enemySpawnTick = this.enemySpawnTickLimit;
    }
}

