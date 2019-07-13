package com.example.miwokapp;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class activity_family extends AppCompatActivity {

    private MediaPlayer mp;
    private MediaPlayer.OnCompletionListener onCompletionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mp) {
            mp.release();
        }
    };

    private AudioManager audioManager;

    AudioManager.OnAudioFocusChangeListener afChangeListener = new AudioManager.OnAudioFocusChangeListener() {
        public void onAudioFocusChange(int focusChange) {
            if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT || focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK){
                mp.pause();
                mp.seekTo(0);
            } else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
                mp.start();
            } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
                /* abandon Audio Focus too. Taken care in the release method itself*/
                release();
            }
        }
    };


    private int[] resId = {R.raw.family_father, R.raw.family_mother, R.raw.family_son, R.raw.family_daughter, R.raw.family_older_brother
            , R.raw.family_younger_brother, R.raw.family_older_sister, R.raw.family_younger_sister, R.raw.family_grandmother, R.raw.family_grandfather};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.words_list);
        ArrayList<word> numbers = new ArrayList<word>();

        //Get the service of the AudioManager
        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);


        numbers.add(new word("Father", "әpә", R.drawable.family_father));
        numbers.add(new word("Mother", "әṭa", R.drawable.family_mother));
        numbers.add(new word("Son", "angsi", R.drawable.family_son));
        numbers.add(new word("Daughter", "tune", R.drawable.family_daughter));
        numbers.add(new word("Older Brother", "taachi", R.drawable.family_older_brother));
        numbers.add(new word("Younger Brother", "chalitti", R.drawable.family_younger_brother));
        numbers.add(new word("Older Sister", "teṭe", R.drawable.family_older_sister));
        numbers.add(new word("Younger Sister", "kolliti", R.drawable.family_younger_sister));
        numbers.add(new word("Grandmother", "ama", R.drawable.family_grandmother));
        numbers.add(new word("Grandfather", "paapa", R.drawable.family_grandfather));


        /* LinearLayout rootview = (LinearLayout) findViewById(R.id.num);
        for(int i=0;i<10;i++) {
            TextView childview = new TextView(this);
            String s = numbers.get(i);
            childview.setText(s);
            rootview.addView(childview);
        } */
        wordAdapter itemsAdapter;
        itemsAdapter = new wordAdapter(this, numbers, R.color.category_family);
        ListView listView = (ListView) findViewById(R.id.list);
        listView.setAdapter(itemsAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                release();


                /* before playing, get the permission to play via audio focus . If granted then only the sudio id played
                 * Also, override the OnAudioFocusChange method of OnAudioFocusChane Listener (above) to tell what to do when the audio
                 * focus comes to this app.*/
                int result = audioManager.requestAudioFocus(afChangeListener,
                        AudioManager.STREAM_MUSIC,
                        AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);

                if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                    mp = MediaPlayer.create(getApplicationContext(), resId[position]);
                    mp.start();
                    mp.setOnCompletionListener(onCompletionListener);
                }
            }
        });

    }

    private void release() {
        if (mp != null) {
            mp.release();
            mp = null;
            /* release audio focus off this app once its resources have been released */
            audioManager.abandonAudioFocus(afChangeListener);
        }
    }
    @Override
    protected void onStop() {
        super.onStop();
        release();
    }
}