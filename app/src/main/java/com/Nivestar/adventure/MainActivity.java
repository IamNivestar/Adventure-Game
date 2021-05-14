package com.Nivestar.adventure;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {

    String TAG = "ACTIVITY ONE";
    String name;
    int id_class;
    MediaPlayer music;
    private static Boolean enable_song = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);
        button_off();

    }
    public void start_game(){
        Intent intent = new Intent(MainActivity.this, Game.class);
        intent.putExtra("name", name);
        intent.putExtra("set", enable_song);
        intent.putExtra("number", id_class);
        startActivityForResult(intent, 1);
    }

    //menu
    public void play(View view) {
        click_sound();
        setContentView(R.layout.select_name);
        button_off();
    }

    public void load(View view){
        click_sound();
        id_class = 9;
        name = "default";
        start_game();
    }

    public void help(View view) {
        click_sound();
        setContentView(R.layout.help);
        button_off();
        TextView text_about = (TextView) findViewById(R.id.about_game);
        String text = "You are an adventure responsible by explore the dungeon seeking to eliminate a dungeon boss!\n\n" +
                "     How to play:\n" +
                "        You can choose from 3 classes, each with its own advantage.\n" +
                "        The max level is 5 and you start at the 1,\n" +
                "        The game automatically saves when you are out of combate\n"+
                "        You can not carry more than 4 potions.\n" +
                "        When you explore the dungeon, random events can occur, such as getting hurt in traps or encountering a random enemy!\n" +
                "        Enemies appear based on your level\n" +
                "        When you encounter an enemy, you can: fight, drink potions or try to escape.\n" +
                "        All actions have their own chance of failing. Drinking potions during combat, for example, is a little more dangerous than drinking out of combat!\n" +
                "        Storing potions can be a good idea, but you'll only find potions on exploration if don't have any.(you could be stolen too...)" +
                "        Low-level enemies are less dangerous, but give less experience, so think hard before defining your strategy!\n" +
                "        Good luck!";
        text_about.setText(text);
    }

    public void exit(View view) {
        click_sound();
        moveTaskToBack(true);
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(1);
    }
    //

    public void get_name(View view){
        click_sound();
        EditText nameinput = (EditText) findViewById(R.id.name);
        name = nameinput.getText().toString();
        setContentView(R.layout.class_choose);
        button_off();
    }

    //class
    public void warrior(View view) {
        click_sound();
        id_class = 1;
        start_game();
    }

    public void wizard(View view) {
        click_sound();
        id_class = 2;
        start_game();
    }

    public void berserker(View view) {
        click_sound();
        id_class = 3;
        start_game();
    }

    public void back(View view) {
        click_sound();
        setContentView(R.layout.menu);
        button_off();
    }

    public void back_class(View view) {
        click_sound();
        setContentView(R.layout.class_choose);
        button_off();
    }

    public void class_help(View view) {
        click_sound();
        setContentView(R.layout.help_class);
        button_off();
        TextView text_about = (TextView) findViewById(R.id.help_class);
        String text = "Warrior:\nIt's a balanced class, the Warriors are highly resistant, being the class with more hit points and balanced attack\n" +
                "\nAttributes:\nAttack: 10 - 15\nHP: 80\nCritical Chance: 20%\n\n" +
                "Wizard:\nIt's the smartest class, because of this, they need little experience to evolve, also start with more potions and small critical chance\n" +
                "\nAttributes:\nAttack: 8 - 17\nHP: 60\nCritical Chance: 5%\nextra potion in beginning\n-25% XP for evolve\n\n" +
                "Berserker:\nIt's a bold choice, Berserkers can do more damage than all classes, and have the highest chance of being critical. However, they have less HP.\n" +
                "\nAttributes:\nAttack: 10 - 20\nHP: 50\nCritical Chance: 40%";
        text_about.setText(text);
    }

    //music
    public  void play_song(View view){
        if(view != null){
            set_enable_song();
        }
        if(music == null){
            play_song_fun();
        }else{
            stop_song();
        }
    }

    private void play_song_fun() {
        music = MediaPlayer.create(this, R.raw.start);
        music.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                stop_song();
            }
        });
        music.setLooping(true);
        music.start();
    }
    public  void stop_song(){
        if(music != null){
            music.release();
            music = null;
        }
    }

    public void click_sound(){
        if(enable_song) {
            final MediaPlayer mp = MediaPlayer.create(this, R.raw.click);
            mp.setVolume(0.2f, 0.2f);
            mp.start();
        }
    }

    private void set_enable_song(){
        if(enable_song == true){
            enable_song= false;
            button_off();
        }else{
            enable_song = true;
            Button b = findViewById(R.id.sound);
            b.setBackgroundResource(android.R.drawable.ic_lock_silent_mode_off);
        }
    }
    private void button_off(){
        if(enable_song == false){
            Button b = findViewById(R.id.sound);
            b.setBackgroundResource(android.R.drawable.ic_lock_silent_mode);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 1){
            if(resultCode == RESULT_OK){
                enable_song = data.getBooleanExtra("result",false);
                button_off();
            }else{
                enable_song = true;
            }
        }
    }

    @Override
    protected void onStop(){
        super.onStop();
        stop_song();
    }
    @Override
    protected void onResume(){
        super.onResume();
        if(enable_song==true){
            play_song(null);
        }
    }


}