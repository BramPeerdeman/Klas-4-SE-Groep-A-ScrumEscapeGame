package org.ScrumEscapeGame.Audio;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;

/*
    Call audio with: AudioManager name = new AudioManager;
*/

public class AudioManager {
    private AudioPlayer bossmusic;
    private AudioPlayer monsterappears;
    private AudioPlayer monsterdeath;
    private AudioPlayer questioncorrect;
    private AudioPlayer questionincorrect;
    private AudioPlayer victory;

    public AudioManager() throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        bossmusic = new AudioPlayer("SoundFX/bossmusic.wav");
        monsterappears = new AudioPlayer("SoundFX/monsterappearssound.wav");
        monsterdeath = new AudioPlayer("SoundFX/monsterdeathsound.wav");
        questioncorrect = new AudioPlayer("SoundFX/questioncorrectsound.wav");
        questionincorrect = new AudioPlayer("SoundFX/questionincorrectsound.wav");
        victory = new AudioPlayer("SoundFX/victorysound.wav");
    }

    public void stopAllAudio() {
        try {
            bossmusic.stop();
            monsterappears.stop();
            monsterdeath.stop();
            questioncorrect.stop();
            questionincorrect.stop();
            victory.stop();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void pauseAllAudio() {
        try {
            bossmusic.pause();
            monsterappears.pause();
            monsterdeath.pause();
            questioncorrect.pause();
            questionincorrect.pause();
            victory.pause();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void resumeAllAudio () throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        try {
            bossmusic.resumeAudio();
            monsterappears.resumeAudio();
            monsterdeath.resumeAudio();
            questioncorrect.resumeAudio();
            questionincorrect.resumeAudio();
            victory.resumeAudio();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void playBossmusic () {
        bossmusic.play();
    }
    public void playMonsterAppears () {
        monsterappears.play();
    }
    public void playMonsterDeath () {
        monsterdeath.play();
    }
    public void playQuestionCorrect () {
        questioncorrect.play();
    }
    public void playQuestionIncorrect () {
        questionincorrect.play();
    }
    public void playVictory () {
        victory.play();
    }
}
