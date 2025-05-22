package org.ScrumEscapeGame.Audio;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/*
    Use AudioManager with: AudioManager name = new AudioManager;
*/

public class AudioManager {
    private List<AudioPlayer> audioPlayers = new ArrayList<AudioPlayer>();

    private AudioPlayer bossmusic;
    private AudioPlayer monsterappears;
    private AudioPlayer monsterdeath;
    private AudioPlayer questioncorrect;
    private AudioPlayer questionincorrect;
    private AudioPlayer swordhit;
    private AudioPlayer victory;

    public AudioManager() throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        bossmusic = new AudioPlayer("SoundFX/bossmusic.wav");
        monsterappears = new AudioPlayer("SoundFX/monsterappearssound.wav");
        monsterdeath = new AudioPlayer("SoundFX/monsterdeathsound.wav");
        questioncorrect = new AudioPlayer("SoundFX/questioncorrectsound.wav");
        questionincorrect = new AudioPlayer("SoundFX/questionincorrectsound.wav");
        victory = new AudioPlayer("SoundFX/victorysound.wav");
        swordhit = new AudioPlayer("SoundFX/swordhitsound.wav");

        audioPlayers.add(bossmusic);
        audioPlayers.add(monsterappears);
        audioPlayers.add(monsterdeath);
        audioPlayers.add(questioncorrect);
        audioPlayers.add(questionincorrect);
        audioPlayers.add(swordhit);
        audioPlayers.add(victory);
    }

    public void stopAllAudio() {
        try {
            for (AudioPlayer audio : audioPlayers) {
                audio.stop();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void pauseAllAudio() {
        try {
            for (AudioPlayer audio : audioPlayers) {
                audio.pause();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void resumeAllAudio () {
        try {
            for (AudioPlayer audio : audioPlayers) {
                audio.resume();
            }
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
    public void playSwordHit () {
        swordhit.play();
    }
    public void playVictory () {
        victory.play();
    }
}
