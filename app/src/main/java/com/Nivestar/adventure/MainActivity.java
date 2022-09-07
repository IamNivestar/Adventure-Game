package com.Nivestar.adventure;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

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

    public void how_to_play(View view) {
        click_sound();
        setContentView(R.layout.how_to_play);
        button_off();
        TextView text_about = (TextView) findViewById(R.id.about_game);
        String text = "You are an adventure responsible by explore the dungeon seeking to eliminate a dungeon boss!\n\n" +
                "     How to play:\n" +
                "        You can choose from 5 classes, each with its own point distribution\n" +
                "        The level is unlimited, when you level up you'll receive an attribute point to distribute and more xp is required to level up again\n" +
                "        The game automatically saves when you are out of combate\n"+
                "        You can not carry more than 4 potions.\n" +
                "        When you explore the dungeon, random events can occur, such as getting hurt in traps or encountering a random enemy!\n" +
                "        When you encounter an enemy, you can: fight, drink potions or try to escape.\n" +
                "        All actions have their own chance of failing. Drinking potions during combat, for example, is a little more dangerous than drinking out of combat!\n" +
                "        Storing potions can be a good idea, but you'll only find potions on exploration if don't have any.(you could be stolen too...)" +
                "        Low-level enemies are less dangerous, but give less experience, so think hard before defining your strategy!\n" +
                "        Enemy class depends on player level\n" +
                "        Enemies receive a small random mutation in one of their attributes and are adapted in relation to the level generated.\n" +
                "        Good luck!";
        text_about.setText(text);
    }

    public void attributes_help(View view) {
        click_sound();
        setContentView(R.layout.how_to_play);
        button_off();
        TextView text_about = (TextView) findViewById(R.id.about_game);
        String text = "@Attributes:\n Constitution: Determines the character's overall toughness\n" +
            "moderately increases resistance to all types of damage and greatly increases max health\n" +
            "Strength: Determines the character's ability and effectiveness to use certain equipment\n" +
            "increases the damage of strength-based weapons and the unarmed character and slightly armor penetration, requirement for weapons and armor.\n" +
            "Dexterity: Determines how skilled a character is with their weapons and attacks.\n    " +
            "Moderately increases the damage of all attacks, including the effectiveness of armor provided by shields (required on some weapons).\n" +
            "Cunning: Determines the character's cleverness to perform actions and find weaknesses in enemies.\n   " +
            "increase dodge chance, critics and points in mind.\n" +
            "Magic: Determines the character's ability to manipulate and enjoy magic in general.\n    " +
            "increases spell damage, improves effectiveness of item enchantments and potions.\n" +
            "Vitality: Determines the character's total energy in combat before suffering exhaustion.\n     " +
            "increases the amount of possible attacks by reducing fatigue speed and slightly increases max health.\n" +
            "Luck: Determines overall luck for finding objects, running away, and even doing well in combat." +
            "increases the probability of finding rarer items, slightly increases the mind, the chance of crits and dodge\n\n"+
            "@Status:\n" +
            "HP: Character hit points\n" +
            "Energy: Number of attacks possible (magic consumes 2 points) before going into exhaustion state, is restored out of combat." +
            "(Exhaustion: does not allow magic attacks and dodges, it is possible to recover only one energy point after a turn of exhaustion)\n" +
            "Damage: Total damage dealt per attack\n" +
            "Armor Penetration: Percentage that ignores enemy armor\n" +
            "Armor: Percentage of physical damage reduction\n" +
            "Dodge: Probability of dodging attacks\n" +
            "Critical Chance: Probability to deal critical hit (150% of damage)\n" +
            "Mind: Value compared to that of the enemy to indicate the ability to read the enemy and successfully execute actions during combat\n" +
            "Cold Resistance: Percentage of cold damage reduction\n" +
            "Heat Resistance: Heat damage reduction percentage\n" +
            "Poison Resistance: Poison damage reduction percentage\n" +
            "Electrical Resistance: Percentage of electrical damage reduction\n" +
            "Cold Damage: Maximum cold damage dealt on attack\n" +
            "Heat Damage: Maximum heat damage dealt on attack\n" +
            "Poison Damage: Maximum poison damage dealt on attack\n" +
            "Electric damage: Maximum electric damage dealt on attack\n";
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

    public void rogue(View view) {
        click_sound();
        id_class = 3;
        start_game();
    }

    public void tank(View view) {
        click_sound();
        id_class = 4;
        start_game();
    }

    public void none(View view) {
        click_sound();
        id_class = 5;
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
        String text = "Warrior:\n" +
                "constitution 13, strength = 16, dexterity = 13, cunning = 10, vitality = 12, magic = 10, lucky = 10\n" +
                "Wizard\n" +
                "constitution = 10, strength = 10, dexterity = 10, cunning = 11, vitality = 15, magic = 17, lucky = 11;\n" +
                "Rogue\n" +
                "constitution = 10, strength = 10, dexterity = 15, cunning = 14, vitality = 12, magic = 10, lucky = 13;\n" +
                "Tank\n" +
                "constitution = 17, strength = 14, dexterity = 12, cunning = 10, vitality = 10, magic = 10, lucky = 11;\n" +
                "None\n" +
                "constitution = 12, strength = 12, dexterity = 12, cunning = 12, vitality = 12, magic = 12, lucky = 12;\n";
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
        if(enable_song){
            enable_song= false;
            button_off();
        }else{
            enable_song = true;
            Button b = findViewById(R.id.sound);
            b.setBackgroundResource(android.R.drawable.ic_lock_silent_mode_off);
        }
    }
    private void button_off(){
        if(!enable_song){
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
        if(enable_song){
            play_song(null);
        }
    }


}