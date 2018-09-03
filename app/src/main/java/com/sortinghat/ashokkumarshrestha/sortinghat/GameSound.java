package com.sortinghat.ashokkumarshrestha.sortinghat;

import android.content.Context;
import android.media.MediaPlayer;

/**
 * Created by Uchiha Ashuke on 8/2/2017.
 */

public class GameSound {
    Context _context;
    public GameSound(Context context){
        this._context = context;
    }

    public void playWin(){
        MediaPlayer mp = MediaPlayer.create(_context, R.raw.s_coin);
        mp.start();
        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp1) {
                // TODO Auto-generated method stub
                mp1.stop();
                mp1.release();
            }
        });
    }

    public void playLoose(){
        MediaPlayer mp = MediaPlayer.create(_context, R.raw.s_beep);
        mp.start();
        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp1) {
                // TODO Auto-generated method stub
                mp1.stop();
                mp1.release();
            }
        });
    }
}
