package com.Nivestar.adventure;

import android.content.Context;
import android.media.MediaPlayer;
import android.view.View;
import android.widget.Button;

public class Sound extends Game{
    public boolean enable_song; //n vou encapsular essa classe
    public boolean combat_song;
    public boolean out_combat_song;

    public Sound(boolean enable_song){
        this.enable_song = enable_song;
        this.combat_song = false;
        this.out_combat_song = false;
    }

}
