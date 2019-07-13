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

public class activity_phrases extends AppCompatActivity {

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


    private int[] resId = {R.raw.phrase_where_are_you_going, R.raw.phrase_what_is_your_name, R.raw.phrase_my_name_is, R.raw.phrase_how_are_you_feeling,
            R.raw.phrase_im_feeling_good, R.raw.phrase_are_you_coming, R.raw.phrase_yes_im_coming, R.raw.phrase_lets_go, R.raw.phrase_come_here};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.words_list);

        //      get the service of the audioManager
        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);


        ArrayList<word> numbers = new ArrayList<word>();
        numbers.add(new word("Where are you going", "minto wuksus"));
        numbers.add(new word("What is your name", "tinnә oyaase'nә"));
        numbers.add(new word("My name is...", "oyaaset..."));
        numbers.add(new word("How are you feeling", "michәksәs?"));
        numbers.add(new word("I'm feeling good", "kuchi achit"));
        numbers.add(new word("Are you coming?", "әәnәs'aa?"));
        numbers.add(new word("Yes, i'm coming", "hәә’ әәnәm"));
        numbers.add(new word("let's go", "yoowutis"));
        numbers.add(new word("Come here", "әnni'nem"));


        /* LinearLayout rootview = (LinearLayout) findViewById(R.id.num);
        for(int i=0;i<10;i++) {
            TextView childview = new TextView(this);
            String s = numbers.get(i);
            childview.setText(s);
            rootview.addView(childview);
        } */
        wordAdapter itemsAdapter;
        itemsAdapter = new wordAdapter(this, numbers, R.color.category_phrases);
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