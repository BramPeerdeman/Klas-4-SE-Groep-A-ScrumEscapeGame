package org.ScrumEscapeGame.Audio;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/*
    Use AudioManager with: AudioManager.playSound().bossmusic();
*/

public class AudioManager {
    private static AudioManager instance;

    private List<AudioPlayer> audioPlayers = new ArrayList<AudioPlayer>();

    private AudioPlayer bossmusic;
    private AudioPlayer monsterappears;
    private AudioPlayer monsterdeath;
    private AudioPlayer doorunlocks;
    private AudioPlayer questionincorrect;
    private AudioPlayer swordhit;
    private AudioPlayer victory;

    public AudioManager() throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        bossmusic = new AudioPlayer("SoundFX/bossmusic.wav");
        monsterappears = new AudioPlayer("SoundFX/monsterappearssound.wav");
        monsterdeath = new AudioPlayer("SoundFX/monsterdeathsound.wav");
        doorunlocks = new AudioPlayer("SoundFX/doorunlockssound.wav");
        questionincorrect = new AudioPlayer("SoundFX/questionincorrectsound.wav");
        victory = new AudioPlayer("SoundFX/victorysound.wav");
        swordhit = new AudioPlayer("SoundFX/swordhitsound.wav");

        audioPlayers.add(bossmusic);
        audioPlayers.add(monsterappears);
        audioPlayers.add(monsterdeath);
        audioPlayers.add(doorunlocks);
        audioPlayers.add(questionincorrect);
        audioPlayers.add(swordhit);
        audioPlayers.add(victory);
    }

    public static AudioManager playSound() {
        if (instance == null) {
            try {
                instance = new AudioManager();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return instance;
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

    public void bossmusic () {
        bossmusic.play();
    }
    public void monsterAppears () {
        monsterappears.play();
    }
    public void monsterDeath () {
        monsterdeath.play();
    }
    public void doorUnlocks() {
        doorunlocks.play();
    }
    public void questionIncorrect () {
        questionincorrect.play();
    }
    public void swordHit () {
        swordhit.play();
    }
    public void victory () {
        victory.play();
    }
}
