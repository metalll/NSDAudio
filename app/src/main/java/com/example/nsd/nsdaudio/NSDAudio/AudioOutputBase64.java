package com.example.nsd.nsdaudio.NSDAudio;

import android.media.MediaRecorder;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;


/**
 * Created by NSD on 9/5/16.
 */
public class AudioOutputBase64 {
    private static final String path = "/audioTemp.3gpp";
    private MediaRecorder recorder = null;
    private AudioOutputDelegate delegate = null;

    //call for u need start record

    public void startRecording(){
        releaseRecorder();
        File outFile = new File(Environment.getExternalStorageDirectory() + path);

        if(outFile.exists()){
            outFile.delete();
        }

        try {
            outFile.createNewFile();
        } catch (IOException e) {
            Log.e("NSD AudioOutputBase64 ","system down with exc "+e.getLocalizedMessage());

        }
        try {
            recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
            recorder.setOutputFile(outFile.getPath());
            recorder.prepare();
            recorder.start();   // Recording is now started
        }
        catch (IOException exc){
            Log.e("NSD AudioOutputBase64 ","system down with exc "+exc.getLocalizedMessage());
        }
    }


    // called if tap stop button or etc event

    public void stopRecording(){
        if(recorder!=null){
            recorder.stop();
        }
        File outFile = new File(Environment.getExternalStorageDirectory() + path);
        if(!outFile.exists()) { return; }

        byte[] data = new byte[(int)outFile.length()];

        try {
            FileInputStream fileInputStream = new FileInputStream(outFile);
            fileInputStream.read(data);
            fileInputStream.close();
        }
        catch (IOException exc){

            Log.e("NSD",exc.getLocalizedMessage());
            return;
        }

        outFile.delete();

        if(delegate!=null) delegate.finish(NSDBase64Tool.encode(data));
        //finally u has base64 string
    }

    public void setDelegate(AudioOutputDelegate delegate){
        this.delegate = delegate;
    }

    //private methods Zone

    private void releaseRecorder(){
        if(recorder!=null){
            recorder.release();
            recorder = null;
        }

    }

}
