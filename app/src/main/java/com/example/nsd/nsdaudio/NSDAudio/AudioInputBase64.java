package com.example.nsd.nsdaudio.NSDAudio;

import android.media.MediaPlayer;
import android.os.Environment;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by NSD on 9/5/16.
 */
public class AudioInputBase64 {
    private static final String path = "/audioTemp.3gpp";
    private MediaPlayer player = null;
    private AudioInputDelegate delegate = null;
    private boolean isStopped = false;

    public AudioInputBase64(String base64){
        byte[] decodedBytes = NSDBase64Tool.decode(base64);
        File outFile = new File(Environment.getExternalStorageDirectory() + path);
        if(outFile.exists()){
          outFile.delete();
        }
        try {
            outFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(outFile.getAbsolutePath());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            fos.write(decodedBytes);
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }




    }

    public void setDelegate(AudioInputDelegate delegate){
        this.delegate=delegate;
    }

    public void playAudio() {
        try {
            releasePlayer();
            player = new MediaPlayer();
            player.setDataSource(Environment.getExternalStorageDirectory() + path);
            player.prepare();
            player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    if (AudioInputBase64.this.delegate != null&&!AudioInputBase64.this.isStopped) {
                        AudioInputBase64.this.delegate.afterPlay();
                    }
                }
            });
            player.start();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void stopAudio(){
        this.isStopped = true;
        if(delegate!=null)
        this.delegate.afterPlay();
    }




    private void releasePlayer() {
        if (player != null) {
            player.release();
            player = null;
        }
    }

}