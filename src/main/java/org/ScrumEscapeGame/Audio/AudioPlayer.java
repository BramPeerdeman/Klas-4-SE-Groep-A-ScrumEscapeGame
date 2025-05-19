package org.ScrumEscapeGame.Audio;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/*

BE CAREFUL: Audio player can only use .wav files

How to initialize an audio:
    AudioPlayer name = new Audioplayer("SoundFx/filename.wav");
    Then call it using name.play();
It will also ask to be able to throw exceptions, just let intellij add them for you via the context menu

 */

public class AudioPlayer {
    private Long currentFrame;
    private Clip clip;
    private AudioInputStream audioInputStream;
    private final String filePath;
    private String status = "stopped";

    public AudioPlayer(String filePath)
            throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        this.filePath = filePath;
        audioInputStream = AudioSystem.getAudioInputStream(new File(filePath).getAbsoluteFile());
        clip = AudioSystem.getClip();
        clip.open(audioInputStream);
    }

    public void play() {
        if (clip != null) {
            clip.setMicrosecondPosition(0);
            clip.start();
            status = "play";
        }
    }

    public void pause() {
        if ("play".equals(status) && clip != null && clip.isRunning()) {
            currentFrame = clip.getMicrosecondPosition();
            clip.stop();
            status = "paused";
        }
    }

    public void resumeAudio() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        if ("paused".equals(status)) {
            clip.close();
            resetAudioStream();
            clip.setMicrosecondPosition(currentFrame);
            clip.start();
            status = "play";
        }
    }

    public void stop() {
        if (clip != null) {
            currentFrame = 0L;
            clip.stop();
            clip.setMicrosecondPosition(0);
            status = "stopped";
        }
    }

    public void restart() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        stop();
        resetAudioStream();
        clip.setMicrosecondPosition(0);
        play();
    }

    public void jump(long c) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        if (c > 0 && c < clip.getMicrosecondLength()) {
            clip.stop();
            clip.close();
            resetAudioStream();
            currentFrame = c;
            clip.setMicrosecondPosition(c);
            play();
        }
    }

    private void resetAudioStream() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        audioInputStream = AudioSystem.getAudioInputStream(new File(filePath).getAbsoluteFile());
        clip = AudioSystem.getClip();
        clip.open(audioInputStream);
    }
}