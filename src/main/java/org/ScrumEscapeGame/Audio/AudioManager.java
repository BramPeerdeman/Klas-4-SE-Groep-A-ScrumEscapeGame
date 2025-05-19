package org.ScrumEscapeGame.Audio;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;

/*
    Call audio with: AudioManager name = new AudioManager;
*/

public class AudioManager {
    AudioPlayer bossmusic = new AudioPlayer("SoundFX/bossmusic.wav");
    AudioPlayer monsterappears = new AudioPlayer("SoundFx/monsterappearssound.wav");
    AudioPlayer monsterdeath = new AudioPlayer("SoundFX/monsterdeathsound.wav");
    AudioPlayer questioncorrect = new AudioPlayer("SoundFX/questioncorrectsound.wav");
    AudioPlayer questionincorrect = new AudioPlayer("SoundFX/questionincorrectsound.wav");
    AudioPlayer victory = new AudioPlayer("SoundFX/victorysound.wav");

    public AudioManager() throws UnsupportedAudioFileException, LineUnavailableException, IOException {

    }

    public void stopAllAudio () throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        bossmusic.stop();
        monsterappears.stop();
        monsterdeath.stop();
        questioncorrect.stop();
        questionincorrect.stop();
        victory.stop();
    }

    public void pauseAllAudio () throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        bossmusic.pause();
        monsterappears.pause();
        monsterdeath.pause();
        questioncorrect.pause();
        questionincorrect.pause();
        victory.pause();
    }

    public void resumeAllAudio () throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        bossmusic.resumeAudio();
        monsterappears.resumeAudio();
        monsterdeath.resumeAudio();
        questioncorrect.resumeAudio();
        questionincorrect.resumeAudio();
        victory.resumeAudio();
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
