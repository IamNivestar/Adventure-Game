package com.Nivestar.adventure;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.Random;


public class Game extends AppCompatActivity {

    static Random rand = new Random();

    private static Enemy enemy;
    private static Player player;
    private static Sound my_sound;
    private static MediaPlayer music;
    //control boleans
    private static Boolean heal_out = false;
    private static Boolean armor = false;
    private static Boolean sword = false;
    private static Boolean ring = false;
    private static Boolean book = false;

    private final Handler mhandler = new Handler();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String name;
        int id_class;
        boolean enable_song;
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras == null) {
                name = "Irineu";
                id_class = 1;
                enable_song = true;
            } else {
                name = extras.getString("name");
                id_class = extras.getInt("number", 0);
                enable_song = extras.getBoolean("set");
            }
        } else {
            Toast.makeText(getApplicationContext(), "Eita!! olhe o else!!", Toast.LENGTH_SHORT).show();
            name = (String) savedInstanceState.getSerializable("name");
            id_class = savedInstanceState.getInt("id_class");  // i don't know about this! i created cuz i do not got it what has done on the last
            enable_song = savedInstanceState.getBoolean("set");
        }
        my_sound = new Sound(enable_song);
        if (id_class == 9) { //load
            load_game();
        } else { //new game
            player = new Player(name, id_class);
            start_game();
        }
    }

    public void start_game() {
        click_sound2(this);
        setContentView(R.layout.start);
        music_choose(2);
        button_off();
        ImageView img = findViewById(R.id.start_image);
        img.setImageResource(LoadPlayerImage());
    }

    public void explorer(View view) {
        heal_out = true;
        int luck = rand.nextInt(1000);
        setContentView(R.layout.event);
        click_and_button_set();
        music_choose(3);
        TextView text_event;
        String text;
        ImageView img = findViewById(R.id.image_event);
        if (luck == 0) {
            img.setImageResource(R.drawable.fall);
            text_event = findViewById(R.id.event_texto);
            text = "You are very unlucky!\n\n" + player.getPlayer_name() + " fell into a pit!\n\n" + player.getPlayer_name() + " died !";
            text_event.setText(text);
            player.setPlayer_hp(0);

        } else if (luck < 20 && player.getPlayer_potion() > 0) {
            img.setImageResource(R.drawable.thielf);
            text_event = findViewById(R.id.event_texto);
            text = " Oh shit!\n\nA thief goblin stole one potion! ";
            text_event.setText(text);
            player.setPlayer_potion(player.getPlayer_potion() - 1);

        } else if (luck <= 50 && luck >= 20) {
            img.setImageResource(R.drawable.trap);
            int dmg = (int) Math.round((player.getPlayer_hp_max()) * 0.2);
            text_event = findViewById(R.id.event_texto);
            text = "You fell into a blade trap!\n\n" + player.getPlayer_name() + " received " + dmg + " damage";
            text_event.setText(text);
            player.player_receive_dmg(dmg);

        } else if (luck <= 110 && luck > 50 && (player.getPlayer_hp() < player.getPlayer_hp_max() * 0.6)) {
            img.setImageResource(R.drawable.supplies);
            int heal = (int) Math.round((player.getPlayer_hp_max() * 0.15));
            player.setPlayer_hp(heal + player.getPlayer_hp());
            text_event = findViewById(R.id.event_texto);
            text = player.getPlayer_name() + " find supplies\n\n" +
                    player.getPlayer_name() + " recovered " + heal + " HP\n";
            text_event.setText(text);

        } else if (luck >= 110 && luck <= 190 && player.getPlayer_potion() == 0) {
            img.setImageResource(R.drawable.potion_found);
            player.setPlayer_potion(player.getPlayer_potion() + 1);
            text_event = findViewById(R.id.event_texto);
            text = player.getPlayer_name() + " are lucky\n" +
                    player.getPlayer_name() + " found a potion!!!\n" +
                    player.getPlayer_name() + " have " + player.getPlayer_potion() + " potion(s)";
            text_event.setText(text);
        } else if (luck == 999) {  //get artifact!!
            find_artifact(img);
        } else { //batalha
            int r;
            do{
                r = rand.nextInt(100);
                enemy = new Enemy(player.getPlayer_lvl());
            }while( r >= enemy.getSpawn_chance());
            fight(view);
        }
    }

    public void find_artifact(ImageView img){
        TextView text_event;
        String text;
        while (true) {
            int luck = rand.nextInt(100);
            if (luck < 25) {
                if (!armor) {
                    img.setImageResource(R.drawable.armor);
                    armor = true;
                    player.setPlayer_hp_max(player.getPlayer_hp_max() + 30);
                    text_event = findViewById(R.id.event_texto);
                    text = player.getPlayer_name() + " found an artifact !!\n" +
                            player.getPlayer_name() + " found the Sacred Armor\n\n" +
                            player.getPlayer_name() + "'s max health increase from " + (player.getPlayer_hp_max() - 30)
                            + " to " + player.getPlayer_hp_max();
                    text_event.setText(text);
                    break;
                }
            } else if (luck < 50) {
                if (!sword) {
                    sword = true;
                    img.setImageResource(R.drawable.sword);
                    player.setPlayer_dmg_phy(player.getPlayer_dmg_phy() + 10);
                    text_event = findViewById(R.id.event_texto);
                    text = player.getPlayer_name() + " found an artifact !!\n" +
                            player.getPlayer_name() + " found the enchanted Sword ANDÚRIL!!\n\n" +
                            player.getPlayer_name() + "'s attack increase from " + (player.getPlayer_dmg_phy() - 10)
                            + " to " + player.getPlayer_dmg_phy();
                    text_event.setText(text);
                    break;
                }
            } else if (luck < 75) {
                if (!book) {
                    book = true;
                    img.setImageResource(R.drawable.book);
                    text_event = findViewById(R.id.event_texto);
                    text = player.getPlayer_name() + " found an artifact !!\n" +
                            player.getPlayer_name() + " a book with reports of fighting and ancient techniques\n\n" +
                            player.getPlayer_name() + "'s get 100 experience";
                    check_level_up();
                    text_event.setText(text);
                    break;
                }
            } else {
                if (!ring) {
                    ring = true;
                    img.setImageResource(R.drawable.ring);
                    player.setPlayer_critical(player.getPlayer_critical() + 30);
                    text_event = findViewById(R.id.event_texto);
                    text = player.getPlayer_name() + " found an artifact !!\n" +
                            player.getPlayer_name() + " found the enchanted Ring of Luck\n\n" +
                            player.getPlayer_name() + "'s critic chance increase from " + (player.getPlayer_critical() - 30)
                            + " to " + player.getPlayer_critical();
                    text_event.setText(text);
                    break;
                }
            }
        }
    }

    public void out_combat_heal(View view) {
        boolean heal_out;
        if (player.getPlayer_hp() < 1) {
            game_over();
        } else {
            setContentView(R.layout.out_combat);

            if (player.getPlayer_hp() > 0.9 * player.getPlayer_hp_max()) {
                heal_out = false;
            }else{
                int heal = (int) Math.round(player.getPlayer_hp_max() * 0.05); //5% of regen
                player.setPlayer_hp(player.getPlayer_hp() + heal);
                heal_out = true;
            }
            player.player_recover_energy();
            out_combat(heal_out);
            save_game();
        }
    }

    public void out_combat(boolean heal_out) {

        setContentView(R.layout.out_combat);
        click_and_button_set();
        music_choose(2);
        TextView text_out;
        String text;

        ImageView img = findViewById(R.id.out_image);
        img.setImageResource(LoadPlayerImage());
        if(heal_out){
            text_out = findViewById(R.id.player_recovery);
            text = "Being out of combat makes you regenerate a small part of your HP and recover your energy.";
            text_out.setText(text);
        }
        text_out = findViewById(R.id.player_potion2);
        text = player.getPlayer_name() + " has " + player.getPlayer_potion() + " potion(s).";
        text_out.setText(text);

        text_out = findViewById(R.id.player_st_out);
        text = "    " + player.getPlayer_name() + "   " + player.getPlayer_class() + "\n level " + player.getPlayer_lvl() +
                "    XP = " + player.getPlayer_xp() + "/" + player.getNecessary_xp();
        text_out.setText(text);

        text_out = findViewById(R.id.player_hp_out);
        text = player.getPlayer_hp() + "/" + player.getPlayer_hp_max();
        text_out.setText(text);
    }

    public void status_out_combat(View view) {
        setContentView(R.layout.status_out_combat);
        click_and_button_set();
        TextView text_out;
        String text;
        ImageView img = findViewById(R.id.out_image);
        img.setImageResource(LoadPlayerImage());

        text_out = findViewById(R.id.attack);
        text = player.getPlayer_dmg_phy() + "";
        text_out.setText(text);

        text_out = findViewById(R.id.dmgelectric);
        text = player.getPlayer_dmg_electric() + "";
        text_out.setText(text);

        text_out = findViewById(R.id.dmgfire);
        text = player.getPlayer_dmg_heat() + "";
        text_out.setText(text);

        text_out = findViewById(R.id.dmgice);
        text = player.getPlayer_dmg_cold() + "";
        text_out.setText(text);

        text_out = findViewById(R.id.dmgpoison);
        text = player.getPlayer_dmg_poison() + "";
        text_out.setText(text);

        text_out = findViewById(R.id.hp);
        text = player.getPlayer_hp() + "/" + player.getPlayer_hp_max();
        text_out.setText(text);

        text_out = findViewById(R.id.critical);
        text = player.getPlayer_critical() + " %";
        text_out.setText(text);

        text_out = findViewById(R.id.mind);
        text = player.getPlayer_mind()+ "";
        text_out.setText(text);

        text_out = findViewById(R.id.armor_penetration);
        text = player.getPlayer_armor_penetration()+ "";
        text_out.setText(text);

        text_out = findViewById(R.id.armor);
        text = player.getPlayer_armor() + "%";
        text_out.setText(text);

        text_out = findViewById(R.id.resis_eletric);
        text = player.getPlayer_resis_eletric() + "%";
        text_out.setText(text);

        text_out = findViewById(R.id.resis_fire);
        text = player.getPlayer_resis_heat() + "%";
        text_out.setText(text);

        text_out = findViewById(R.id.resis_ice);
        text = player.getPlayer_resis_cold() + "%";
        text_out.setText(text);

        text_out = findViewById(R.id.resis_poison);
        text = player.getPlayer_resis_poison() + "%";
        text_out.setText(text);

        text_out = findViewById(R.id.dodge);
        text = player.getPlayer_dodge() + "%";
        text_out.setText(text);
    }

    public void out_combat_return(View view){
        out_combat(false);
    }

    public void fight(View view) {
        setContentView(R.layout.combat);
        button_off();
        music_choose(1);
        TextView text_fight;
        String text;
        energy_disable_buttons_combat();

        if (player.getPlayer_hp() < 1) {
            game_over();
            return;
        }
        if(player.getPlayer_energy() == -1){ //recover 1 point after exhaustion
            player.setPlayer_energy(1);
        }
        if(enemy.getEnemy_energy() == -1){
            enemy.setEnemy_energy(1);
        }
        int discover_lvl = get_playerlvl_mind_discover();

        ImageView img = findViewById(R.id.combat_image);
        img.setImageResource(LoadPlayerImage());

        img = findViewById(R.id.enemy_image);
        LoadEnemyImage(img);

        text_fight = findViewById(R.id.player_st);
        text = player.getPlayer_name() + "   " + player.getPlayer_class() + "\nXP = " + player.getPlayer_xp() +
                "/" + player.getNecessary_xp() + "  level " + player.getPlayer_lvl();
        text_fight.setText(text);

        text_fight = findViewById(R.id.player_at);
        text = player.getPlayer_dmg_phy() + "" ;
        text_fight.setText(text);

        text_fight = findViewById(R.id.player_critical);
        text = player.getPlayer_critical() + "";
        text_fight.setText(text);

        text_fight = findViewById(R.id.player_dmgfire);
        text = player.getPlayer_dmg_heat() + "";
        text_fight.setText(text);

        text_fight = findViewById(R.id.player_dmgice);
        text = player.getPlayer_dmg_cold() + "";
        text_fight.setText(text);

        text_fight = findViewById(R.id.player_dmgpoison);
        text = player.getPlayer_dmg_poison() + "";
        text_fight.setText(text);

        text_fight = findViewById(R.id.player_dmgelectric);
        text = player.getPlayer_dmg_electric() + "";
        text_fight.setText(text);

        text_fight = findViewById(R.id.player_hp);
        text = player.getPlayer_hp() + "/" + player.getPlayer_hp_max();
        if( player.getPlayer_hp() <= (player.getPlayer_hp_max()*4)/10){
            text_fight.setTextColor(Color.rgb(200, 0, 0)); //red
        }
        text_fight.setText(text);

        text_fight = findViewById(R.id.player_sta);
        text = player.getPlayer_energy() + "/" + player.getPlayer_energy_max();
        if( player.getPlayer_energy() <= 1 ){
            text_fight.setTextColor(Color.rgb(200, 0, 0)); //red
        }
        text_fight.setText(text);

        //enemy
        text_fight = findViewById(R.id.enemy_st);
        text = enemy.getEnemy_name() + "\n  level " + enemy.getEnemy_lvl() + " Class " + enemy.getEnemy_class();
        text_fight.setText(text);

        text_fight = findViewById(R.id.enemy_at);
        text = enemy.getEnemy_dmg_phy() + "";
        if(discover_lvl == 0) text = "?";
        text_fight.setText(text);

        text_fight = findViewById(R.id.enemy_critical);
        text = enemy.getEnemy_critical() + "";
        if(discover_lvl < 2) text = "?";
        text_fight.setText(text);

        text_fight = findViewById(R.id.enemy_dmgfire);
        text = enemy.getEnemy_dmg_heat() + "";
        if(discover_lvl < 2) text = "?";
        text_fight.setText(text);

        text_fight = findViewById(R.id.enemy_dmgice);
        text = enemy.getEnemy_dmg_cold() + "";
        if(discover_lvl < 2) text = "?";
        text_fight.setText(text);

        text_fight = findViewById(R.id.enemy_dmgpoison);
        text = enemy.getEnemy_dmg_poison() + "";
        if(discover_lvl < 2) text = "?";
        text_fight.setText(text);

        text_fight = findViewById(R.id.enemy_dmgelectric);
        text = enemy.getEnemy_dmg_electric() + "";
        if(discover_lvl < 2) text = "?";
        text_fight.setText(text);

        text_fight = findViewById(R.id.enemy_hp);
        text = " " + enemy.getEnemy_hp();
        if(discover_lvl < 1) text = "?";
        text_fight.setText(text);

        text_fight = findViewById(R.id.enemy_sta);
        text = enemy.getEnemy_energy() + "/" + enemy.getEnemy_energy_max();
        if(discover_lvl < 1) text = "?";
        text_fight.setText(text);

        text_fight = findViewById(R.id.fight_title);
        text = player.getPlayer_name() + " came across a " + enemy.getEnemy_name() + " !";
        text_fight.setText(text);

        //intentional delay to press
        new Handler().postDelayed(new Runnable() {

            public void run() {
                if (enemy.getEnemy_hp() <= 0 || player.getPlayer_hp() <= 0) {
                    check_level_up();
                    return;
                }
                enable_combat_buttons();
            }
        }, 300);
    }

    public void fight2(View view) {
        setContentView(R.layout.combat2);
        button_off();
        TextView text_fight;
        String text;
        energy_disable_buttons_combat();

        if (player.getPlayer_hp() < 1) {
            game_over();
            return;
        }
        if(player.getPlayer_energy() == -1){ //recover 1 point after exhaustion
            player.setPlayer_energy(1);
        }
        if(enemy.getEnemy_energy() == -1){
            enemy.setEnemy_energy(1);
        }
        int lvl_discover = get_playerlvl_mind_discover();
        ImageView img = findViewById(R.id.combat_image);
        img.setImageResource(LoadPlayerImage());

        img = findViewById(R.id.enemy_image);
        LoadEnemyImage(img);

        text_fight = findViewById(R.id.player_st2);
        text = player.getPlayer_name() + "   " + player.getPlayer_class() + "\nXP = " + player.getPlayer_xp() +
                "/" + player.getNecessary_xp() + "  level " + player.getPlayer_lvl();
        text_fight.setText(text);

        text_fight = findViewById(R.id.player_armorpen);
        text = player.getPlayer_armor_penetration() + "";
        text_fight.setText(text);

        text_fight = findViewById(R.id.player_dodge);
        text = player.getPlayer_dodge() + "";
        text_fight.setText(text);

        text_fight = findViewById(R.id.player_armor);
        text = player.getPlayer_armor() + "";
        text_fight.setText(text);

        text_fight = findViewById(R.id.player_mind);
        text = player.getPlayer_mind() + "";
        text_fight.setText(text);

        text_fight = findViewById(R.id.player_resisice);
        text = player.getPlayer_resis_cold() + "";
        text_fight.setText(text);

        text_fight = findViewById(R.id.player_poisonresis);
        text = player.getPlayer_resis_poison() + "";
        text_fight.setText(text);

        text_fight = findViewById(R.id.player_fireresis);
        text = player.getPlayer_resis_heat() + "";
        text_fight.setText(text);

        text_fight = findViewById(R.id.player_resiseletric);
        text = player.getPlayer_resis_eletric() + "";
        text_fight.setText(text);

        //enemy
        text_fight = findViewById(R.id.enemy_st2);
        text = enemy.getEnemy_name() + "\n  level " + enemy.getEnemy_lvl() + " Class " + enemy.getEnemy_class();
        text_fight.setText(text);

        text_fight = findViewById(R.id.enemy_armorpen);
        text = enemy.getEnemy_armor_penetration() + "";
        if(lvl_discover < 3) text = "?";
        text_fight.setText(text);

        text_fight = findViewById(R.id.enemy_dodge);
        text = enemy.getEnemy_dodge() + "";
        if(lvl_discover < 3) text = "?";
        text_fight.setText(text);

        text_fight = findViewById(R.id.enemy_armor);
        text = enemy.getEnemy_armor() + "";
        if(lvl_discover < 2) text = "?";
        text_fight.setText(text);

        text_fight = findViewById(R.id.enemy_mind);
        text = enemy.getEnemy_mind() + "";
        if(lvl_discover < 1) text = "?";
        text_fight.setText(text);

        text_fight = findViewById(R.id.enemy_resiselectric);
        text = enemy.getEnemy_resis_eletric() + "";
        if(lvl_discover < 3) text = "?";
        text_fight.setText(text);

        text_fight = findViewById(R.id.enemy_fireresis);
        text = enemy.getEnemy_resis_heat() + "";
        if(lvl_discover < 3) text = "?";
        text_fight.setText(text);

        text_fight = findViewById(R.id.enemy_poisonresis);
        text = enemy.getEnemy_resis_poison() + "";
        if(lvl_discover < 3) text = "?";
        text_fight.setText(text);

        text_fight = findViewById(R.id.enemy_iceresis);
        text = enemy.getEnemy_resis_cold() + "";
        if(lvl_discover < 3) text = "?";
        text_fight.setText(text);

        text_fight = findViewById(R.id.fight_title);
        text = player.getPlayer_name() + " came across a " + enemy.getEnemy_name() + " !";
        text_fight.setText(text);

        //intentional delay to press
        new Handler().postDelayed(new Runnable() {

            public void run() {
                if (enemy.getEnemy_hp() <= 0 || player.getPlayer_hp() <= 0) {
                    check_level_up();
                    return;
                }
                enable_combat_buttons();
            }
        }, 300);
    }

    public int get_playerlvl_mind_discover(){
        int pm = player.getPlayer_mind();
        int em = enemy.getEnemy_mind();

        if(pm <= em - 20){
            return 0;
        }else if( pm < em ){
            return 1;
        }else if( pm > em && pm <= em + 10){
            return 2;
        }else{
            return 3;
        }
    }

    public void attack_physical(View view){
        player.setPlayer_energy(player.getPlayer_energy()-1);
        player.set_player_dmg_types(new int[]{0,-1,-1,-1,-1});
        attack();
    }

    public void attack_fire(View view){
        player.setPlayer_energy(player.getPlayer_energy()-2);
        player.set_player_dmg_types(new int[]{1, -1, -1, -1, -1});
        attack();
    }

    public void attack_ice(View view){
        player.setPlayer_energy(player.getPlayer_energy()-2);
        player.set_player_dmg_types(new int[]{2,-1,-1,-1,-1});
        attack();
    }

    public void attack_poison(View view){
        player.setPlayer_energy(player.getPlayer_energy()-2);
        player.set_player_dmg_types(new int[]{3,-1,-1,-1,-1});
        attack();
    }

    public void attack_bold(View view){
        player.setPlayer_energy(player.getPlayer_energy()-2);
        player.set_player_dmg_types(new int[]{4,-1,-1,-1,-1});
        attack();
    }

    public void attack() {
        energy_disable_buttons_combat();
        //this is all just to set the delay time attack
        int n_at = 0, n_at2 = 0, i;
        int [] vetor_at =  player.get_player_dmg_types();
        for (i = 0; i < 5; i++){
            if (vetor_at[i] != -1)
                n_at += 1;
        }
        int [] vetor_at2 = enemy.get_enemy_dmg_types();
        for (i = 0; i < 5; i++){
            if (vetor_at2[i] != -1)
                n_at2 += 1;
        }
        int time = n_at*700 + 100;
        int time2 = n_at2*700 + 100;
        //

        player_attack(0);
        mhandler.postDelayed(new Runnable() {
            public void run() {
                enemy_attack(0);
            }
        }, time);

        enemy.setEnemy_energy(enemy.getEnemy_energy() - 1);

        mhandler.postDelayed(new Runnable() {
            public void run() {
                fight(null);
            }
        }, time+time2);
    }

    public void player_attack( int at_number) {
        String text = "";
        ImageView img;
        TextView text_fight;
        text_fight = findViewById(R.id.player_dmg);
        int[] dmg_types = player.get_player_dmg_types();

        if(dmg_types[at_number] != -1) {
            if (player.player_att_dodge(enemy.getEnemy_dodge()) && at_number == 0) {
                text = "miss";
                my_sound.miss(this);
                text_fight.setText(text);
                img = findViewById(R.id.slice_enemy);
                img.setImageResource(R.drawable.slice);
                reset_visuals(img, text_fight);
            } else {  //hit
                int dmg = get_player_dmg(dmg_types[at_number]);
                boolean crit = player.critic();
                if (crit) {
                    text = dmg + " CRITIC";
                    text_fight.setText(text);
                    dmg = (int) Math.round(dmg*1.5);
                } else {
                    text = dmg + "";
                }
                text_fight.setText(text);
                text_color_sound_att(text_fight, dmg_types[at_number]);
                img = findViewById(R.id.slice_enemy);
                img.setImageResource(R.drawable.slice);
                reset_visuals(img, text_fight);

                int dmgTotal = player.player_exhaustion(dmg);
                enemy.enemy_receive_dmg(dmgTotal);

                mhandler.postDelayed(new Runnable() {
                    public void run() {
                        if (at_number + 1 < 5)
                            player_attack(at_number + 1);
                    }
                }, 600);
            }
        }
    }
    public void enemy_attack( int at_number){
        TextView text_fight;
        ImageView img;
        String text;
        text_fight = findViewById(R.id.enemy_dmg);
        int [] dmg_types = enemy.get_enemy_dmg_types();

        if(dmg_types[at_number] != -1) {
            if (enemy.enemy_att_dodge(player.getPlayer_dodge()) && at_number == 0) {
                text = "miss";
                my_sound.miss(this);
                text_fight.setText(text);
                img = findViewById(R.id.slice_player);
                img.setImageResource(R.drawable.slice);
                reset_visuals(img, text_fight);
            } else {  //hit
                int dmg = get_enemy_dmg(dmg_types[at_number]);
                boolean crit = enemy.critic();
                if (crit) {
                    text = dmg + " CRITIC";
                    text_fight.setText(text);
                    dmg = (int) Math.round(dmg*1.5);
                } else {
                    text = dmg + "";
                }
                text_fight.setText(text);
                text_color_sound_att(text_fight, dmg_types[at_number]);
                img = findViewById(R.id.slice_player);
                img.setImageResource(R.drawable.slice);
                reset_visuals(img, text_fight);

                int dmgTotal = enemy.enemy_exhaustion(dmg);
                player.player_receive_dmg(dmgTotal);

                mhandler.postDelayed(new Runnable() {
                    public void run() {
                        if (at_number + 1 < 5)
                            enemy_attack(at_number + 1);
                    }
                }, 600);
            }
        }
    }

    //attack functions
    public int get_player_dmg(int id_dmg){
        switch (id_dmg) {
            case 0:
                return player.player_att_phy(enemy.getEnemy_armor());
            case 1:
                return player.player_att_heat(enemy.getEnemy_resis_heat());
            case 2:
                return player.player_att_cold(enemy.getEnemy_resis_cold());
            case 3:
                return player.player_att_poison(enemy.getEnemy_resis_poison());
            case 4:
                return player.player_att_eletric(enemy.getEnemy_resis_eletric());
            default:
                return 0;
        }
    }

    public int get_enemy_dmg(int id_dmg){
        switch (id_dmg) {
            case 0:
                return enemy.enemy_att_phy(player.getPlayer_armor());
            case 1:
                return enemy.enemy_att_heat(player.getPlayer_resis_heat());
            case 2:
                return enemy.enemy_att_cold(player.getPlayer_resis_cold());
            case 3:
                return enemy.enemy_att_poison(player.getPlayer_resis_poison());
            case 4:
                return enemy.enemy_att_eletric(player.getPlayer_resis_eletric());
            default:
                return 0;
        }
    }

    public void reset_visuals(ImageView img, TextView text_fight) {
        Runnable visual = new Runnable() {
            public void run() {
                img.setImageResource(0);
                text_fight.setText(" ");
            }
        };
        mhandler.postDelayed(visual, 600);
    }

    public void energy_disable_buttons_combat(){
        Button fgt = findViewById(R.id.fightbutton);
        Button magic1 = findViewById(R.id.magic1);
        Button magic2 = findViewById(R.id.magic2);
        Button magic3 = findViewById(R.id.magic3);
        Button magic4 = findViewById(R.id.magic4);
        Button exit = findViewById(R.id.exit11);
        Button dri = findViewById(R.id.exit12);
        fgt.setEnabled(false);
        exit.setEnabled(false);
        dri.setEnabled(false);
        magic1.setEnabled(false);
        magic2.setEnabled(false);
        magic3.setEnabled(false);
        magic4.setEnabled(false);
    }

    public void enable_combat_buttons() {
        Button fgt = findViewById(R.id.fightbutton);
        Button magic1 = findViewById(R.id.magic1);
        Button magic2 = findViewById(R.id.magic2);
        Button magic3 = findViewById(R.id.magic3);
        Button magic4 = findViewById(R.id.magic4);
        Button exit = findViewById(R.id.exit11);
        Button dri = findViewById(R.id.exit12);
        fgt.setEnabled(true);
        exit.setEnabled(true);
        dri.setEnabled(true);
        if(player.getPlayer_energy() > 1) {
            magic1.setEnabled(true);
            magic2.setEnabled(true);
            magic3.setEnabled(true);
            magic4.setEnabled(true);
        }else{
            magic1.setVisibility(View.INVISIBLE);
            magic2.setVisibility(View.INVISIBLE);
            magic3.setVisibility(View.INVISIBLE);
            magic4.setVisibility(View.INVISIBLE);
        }
    }

    public void text_color_sound_att(TextView text_fight, int i){
        switch (i) {
            case 0:
                text_fight.setTextColor(Color.rgb(255, 255, 255)); //white
                my_sound.phy_attack(this);
                break;
            case 1:
                text_fight.setTextColor(Color.rgb(200, 0, 0)); //red
                my_sound.heat_attack(this);
                break;
            case 2:
                text_fight.setTextColor(Color.rgb(0, 200, 255));// light blue
                my_sound.ice_attack(this);
                break;
            case 3:
                text_fight.setTextColor(Color.rgb(0, 200, 0)); //green
                my_sound.poison_attack(this);
                break;
            case 4:
                text_fight.setTextColor(Color.rgb(150, 0, 200));//purple
                my_sound.electric_attack(this);
                break;
        }
    }
    // end fight

    public void drink(View view) {
        setContentView(R.layout.combat_action);
        click_and_button_set();
        TextView text_event = findViewById(R.id.combat_texto);
        String text;
        ImageView img = findViewById(R.id.imagem_combat);

        int luck = rand.nextInt(100);

        if (player.getPlayer_potion() == 0) {
            text = "You cannot do it!\n\n" + player.getPlayer_name() + " don't have any potions!!";
            text_event.setText(text);
            img.setImageResource(R.drawable.dungeon);
        } else if (luck < 10) {
            int dmgTotal = attack_total_enemy_potion_escape();
            player.player_receive_dmg(dmgTotal);
            text = player.getPlayer_name() + " tried to take the potion but enemy attacked!!\n\n" +
                    player.getPlayer_name() + " managed to dodge part of the attack, but the potion bottle was broken.\n";
            if (dmgTotal != 0) {
                text = text.concat(player.getPlayer_name() + " received " + dmgTotal + " damage (half the total damage)\n");
            } else {
                text = text.concat("The enemy miss your attack\n");
            }
            text_event.setText(text);
            player.setPlayer_potion(player.getPlayer_potion() - 1);
            img.setImageResource(R.drawable.fight);
        } else {
            my_sound.drink_sound(this);
            int heal = player.drink_potion();
            text = player.getPlayer_name() + " drank one potion!\n\n" +
                    player.getPlayer_name() + " recovered " + heal + " HP";
            text_event.setText(text);
            player.setPlayer_potion(player.getPlayer_potion() - 1);
            img.setImageResource(R.drawable.potion_found);
        }
    }

    public void escape(View view) {
        setContentView(R.layout.combat_action);
        click_and_button_set();
        TextView text_event;
        String text;
        int luck = rand.nextInt(100);
        ImageView img = findViewById(R.id.imagem_combat);
        img.setImageResource(R.drawable.fight);

        if (enemy.getEnemy_name().equals("Crazy Adventure")) {
            text_event = findViewById(R.id.combat_texto);
            text = "\n" + player.getPlayer_name() + " tried to escape but the adventurer follows him madly\n\n" + player.getPlayer_name() + " cannot run away from this enemy";
            text_event.setText(text);
        } else if (luck >= 20) {
            setContentView(R.layout.event);
            button_off();
            music_choose(2);
            img = findViewById(R.id.image_event);
            img.setImageResource(R.drawable.run);
            text_event = findViewById(R.id.event_texto);
            text = "\n\n" + player.getPlayer_name() + " ran away successfully";
            text_event.setText(text);
        } else {
            int dmgTotal = attack_total_enemy_potion_escape();
            text_event = findViewById(R.id.combat_texto);
            player.player_receive_dmg(dmgTotal);
            text = player.getPlayer_name() + " tried to escape but " + enemy.getEnemy_name() + " didn't allow\n\n" +
                    enemy.getEnemy_name() + " attacked " + player.getPlayer_name() + "!\n\n" +
                    player.getPlayer_name() + " received " + dmgTotal + " damage";
            text_event.setText(text);
        }
    }

    private int attack_total_enemy_potion_escape() {
        //just to get the total dmg, not getting the damage separately
        int[] enemy_dmg_types = enemy.get_enemy_dmg_types();
        int[] dmg = new int[5];
        for (int i = 0; i<5; i++) {
            dmg[i] = get_enemy_dmg(enemy_dmg_types[i]);
        }
        int dmgTotal = 0;
        for (int i = 0; i < 5; i++)
            dmgTotal += dmg[i];

        return dmgTotal;
    }

    public void check_level_up(){
        if (enemy.getEnemy_hp() <= 0 && player.getPlayer_hp() > 0) {
            boolean evolve = player.add_xp(enemy.getXp_value());
            if (evolve) {
                LevelUp();
            }else{
                end_fight(false);
            }
        }
    }

    public void end_fight(boolean evolve){
        if (enemy.getEnemy_hp() <= 0 && player.getPlayer_hp() > 0) {
            setContentView(R.layout.combat_report);
            click_and_button_set();
            TextView text_fight;
            String text;
            if (evolve) { //i do not like this, but for now I keep that
                text_fight = findViewById(R.id.fight_lvl);
                text = player.getPlayer_name() + "'s  health has restored and your status improved!";
                text_fight.setText(text);
            }
            text_fight = findViewById(R.id.fight_xp);
            text = player.getPlayer_name() + " defeat " + enemy.getEnemy_name() +
                    "\n\n" + player.getPlayer_name() + " got " + enemy.getXp_value() + " experience points.";
            text_fight.setText(text);
            if (enemy.enemy_drop()) {
                text_fight = findViewById(R.id.fight_pot);
                text = enemy.getEnemy_name() + " drop one potion!";
                text_fight.setText(text);
                player.setPlayer_potion(player.getPlayer_potion() + 1);
            }
        }else if (player.getPlayer_hp() <= 0) {
            setContentView(R.layout.combat_report);
            click_and_button_set();
            TextView text_fight;
            String text;
            text_fight = findViewById(R.id.fight_xp);
            text = enemy.getEnemy_name() + " defeat " + player.getPlayer_name() + " !\n\n"
                    + player.getPlayer_name() + " die.";
            text_fight.setText(text);
        }
    }

    public void drink_out(View view) {
        setContentView(R.layout.event);
        click_and_button_set();
        TextView text_event = findViewById(R.id.event_texto);
        String text;
        ImageView img = findViewById(R.id.image_event);

        if (player.getPlayer_potion() == 0) {
            text = "You can do it!\n\n" + player.getPlayer_name() + " don't have any potions!!";
            text_event.setText(text);
            img.setImageResource(R.drawable.dungeon);
        } else {
            int luck = rand.nextInt(100);
            if (luck >= 5) {
                my_sound.drink_sound(this);
                int heal = player.drink_potion();
                text = player.getPlayer_name() + " drank one potion!\n\n" +
                        player.getPlayer_name() + " recovered " + heal + " HP";
                text_event.setText(text);
                player.setPlayer_potion(player.getPlayer_potion() - 1);
                img.setImageResource(R.drawable.potion_found);
            } else {
                text = "Opsss\n\n" +
                        player.getPlayer_name() + " was startled by a noise and the potion fell, breaking the flask!";
                text_event.setText(text);
                img.setImageResource(R.drawable.dungeon);
                player.setPlayer_potion(player.getPlayer_potion() - 1);
            }
        }
    }

    public void reset_game() {
        String class_p = player.getPlayer_class();
        String name = player.getPlayer_name();
        armor = false;
        sword = false;
        ring = false;
        book = false;
        switch (class_p) {
            case "Warrior":
                player = new Player(name, 1);
                break;
            case "Wizard":
                player = new Player(name, 2);
                break;
            case "Rogue":
                player = new Player(name, 3);
                break;
            case "Tank":
                player = new Player(name, 4);
                break;
            case "None":
                player = new Player(name, 5);
                break;
        }
    }

    public int LoadPlayerImage(){
        switch (player.getPlayer_class()) {
            case "Warrior":
                return R.drawable.w2;
            case "Wizard":
                return R.drawable.wi2;
            case "Rogue":
                return R.drawable.r2;
            case "Tank":
                return R.drawable.t2;
            case "None":
                return R.drawable.n2;
            default:
                Toast.makeText(getApplicationContext(), "Error load image player", Toast.LENGTH_SHORT).show();
                exit(null);
                return -1;
        }
    }

    public void LoadEnemyImage(ImageView img) {
        switch (enemy.getEnemy_name()) {
            case "Deep Stalker":
                img.setImageResource(R.drawable.deep_stalker);
                img.setScaleX(1);
                break;
            case "Queen Spider":
                img.setImageResource(R.drawable.queen_spider);
                img.setScaleX(1);
                break;
            case "Tweegon":
                img.setImageResource(R.drawable.tweegon);
                img.setScaleX(1);
                break;
            case "Werewolf":
                img.setImageResource(R.drawable.werewolf);
                break;
            case "Volt Boar":
                img.setImageResource(R.drawable.volt_boar);
                break;
            case "Venomous Spider":
                img.setImageResource(R.drawable.venomous_spider);
                break;
            case "Venomous Spawn":
                img.setImageResource(R.drawable.venomous_spawn);
                break;
            case "Thunderbull":
                img.setImageResource(R.drawable.thunderbull);
                break;
            case "Poisonous Zumbi":
                img.setImageResource(R.drawable.poisonous_zumbi);
                break;
            case "Poisonous Slime":
                img.setImageResource(R.drawable.poisonous_slime);
                img.setScaleX(1);
                break;
            case "Mummy":
                img.setImageResource(R.drawable.mummy);
                img.setScaleX(1);
                break;
            case "Lightning Bug":
                img.setImageResource(R.drawable.lightning_bug);
                img.setScaleX(1);
                break;
            case "Lava Slime":
                img.setImageResource(R.drawable.lava_slime);
                img.setScaleX(1);
                break;
            case "Poisonous Lotus":
                img.setImageResource(R.drawable.poisonous_lotus);
                break;
            case "Lava Golem":
                img.setImageResource(R.drawable.lava_golem);
                img.setScaleX(1);
                break;
            case "Lava Demon":
                img.setImageResource(R.drawable.lava_demon);
                break;
            case "Infected Human":
                img.setImageResource(R.drawable.infected_human);
                img.setScaleX(1);
                break;
            case "Ice Warrior":
                img.setImageResource(R.drawable.ice_warrior);
                img.setScaleX(1);
                break;
            case "Ice Goop":
                img.setImageResource(R.drawable.ice_goop);
                break;
            case "Ice Snake":
                img.setImageResource(R.drawable.ice_snake);
                break;
            case "Hellhound":
                img.setImageResource(R.drawable.hellhound);
                img.setScaleX(1);
                break;
            case "Ice Spectrum":
                img.setImageResource(R.drawable.ice_spectrum);
                break;
            case "Fenix":
                img.setImageResource(R.drawable.fenix);
                break;
            case "Demonic Canine":
                img.setImageResource(R.drawable.demonic_canine);
                break;
            case "Demon Sorcerer":
                img.setImageResource(R.drawable.demon_sorcerer);
                break;
            case "Demon of the Flames":
                img.setImageResource(R.drawable.demon_of_the_flames);
                break;
            case "Swamp Predator":
                img.setImageResource(R.drawable.swamp_predator);
                break;
            case "Crystal Monster":
                img.setImageResource(R.drawable.crystal_monster);
                break;
            case "Cholera Spectrum":
                img.setImageResource(R.drawable.cholera_spectrum);
                img.setScaleX(1);
                break;
            case "Cerberus":
                img.setImageResource(R.drawable.cerberus);
                img.setScaleX(1);
                break;
            case "Cave Worm":
                img.setImageResource(R.drawable.cave_worm);
                break;
            case "Cave Demon":
                img.setImageResource(R.drawable.cave_demon);
                break;
            case "Barroth":
                img.setImageResource(R.drawable.barroth);
                break;
            case "Ice Wolf":
                img.setImageResource(R.drawable.ice_wolf);
                img.setScaleX(1);
                break;
            case "Makara":
                img.setImageResource(R.drawable.makara);
                img.setScaleX(1);
                break;
            case "Fire Demon":
                img.setImageResource(R.drawable.fire_demon);
                break;
            case "Deep Spawn":
                img.setImageResource(R.drawable.deep_spawn);
                break;
            case "Ravishing":
                img.setImageResource(R.drawable.ravishing);
                break;
            case "Swamp Queen":
                img.setImageResource(R.drawable.swamp_queen);
                break;
            case "Walking Mushroom":
                img.setImageResource(R.drawable.walking_mushroom);
                break;
            case "Demoniac Hyena":
                img.setImageResource(R.drawable.demoniac_hyena);
                break;
            case "Goblin Warrior":
                img.setImageResource(R.drawable.goblin_warrior);
                img.setScaleX(1);
                break;
            case "Ice Dragon":
                img.setImageResource(R.drawable.ice_dragon);
                break;
            case "Rabid Bat":
                img.setImageResource(R.drawable.rabid_bat);
                img.setScaleX(1);
                break;
            case "Anjanath":
                img.setImageResource(R.drawable.anjanath);
                img.setScaleX(1);
                break;
            case "Acid Reptile":
                img.setImageResource(R.drawable.acid_reptile);
                break;
            case "Giant Spider":
                img.setImageResource(R.drawable.spider);
                img.setScaleX(1);
                break;
            case "Skeleton":
                img.setImageResource(R.drawable.skeleton);
                break;
            case "Zombie":
                img.setImageResource(R.drawable.zumbi);
                break;
            case "Goblin":
                img.setImageResource(R.drawable.goblin);
                break;
            case "ORC":
                img.setImageResource(R.drawable.orc);
                break;
            case "Baby Dragon":
                img.setImageResource(R.drawable.baby_dragon);
                break;
            case "Witch":
                img.setImageResource(R.drawable.witch);
                img.setScaleX(1);
                break;
            case "Territorial Bear":
                img.setImageResource(R.drawable.bear);
                break;
            case "Ghul":
                img.setImageResource(R.drawable.ghul);
                break;
            case "Mutant":
                img.setImageResource(R.drawable.mutant);
                break;
            case "Crazy Adventure":
                img.setImageResource(R.drawable.adventure);
                img.setScaleX(1);
                break;
            case "Minotaur":
                img.setImageResource(R.drawable.minotaur);
                img.setScaleX(1);
                break;
            case "Medium Dragon":
                img.setImageResource(R.drawable.dragon);
                img.setScaleX(1);
                break;
            case "Ghost Warrior":
                img.setImageResource(R.drawable.ghost_warrior);
                break;
            case "Abomination of Caves":
                img.setImageResource(R.drawable.abomination);
                break;
            case "Ancient Dragon":
                img.setImageResource(R.drawable.ancient_dragon);
                img.setScaleX(1);
                break;
            case "Troll":
                img.setImageResource(R.drawable.troll);
                img.setScaleX(1);
                break;
            case "Dungeon's Guardian(BOSS)":
                img.setImageResource(R.drawable.guardian);
                break;
            default:
                Toast.makeText(getApplicationContext(), "Erro carregar imagem inimgo", Toast.LENGTH_SHORT).show();
                exit(null);
        }
    }

    //lvl functions begin
    public void LevelUp() {
        my_sound.level_up_sound(this);
        setContentView(R.layout.levelup);
        click_and_button_set();
        music_choose(4);
        TextView text_event = findViewById(R.id.button);
        String text = "Constitution (" + player.getPlayer_constitution() + ")";
        text_event.setText(text);
        text_event = findViewById(R.id.button2);
        text = "Strenght (" + player.getPlayer_strength() + ")";
        text_event.setText(text);
        text_event = findViewById(R.id.button3);
        text = "Dexterity(" + player.getPlayer_dexterity() + ")";
        text_event.setText(text);
        text_event = findViewById(R.id.button4);
        text = "Vitality (" + player.getPlayer_vitality() + ")";
        text_event.setText(text);
        text_event = findViewById(R.id.button5);
        text = "Cunning (" + player.getPlayer_cunning() + ")";
        text_event.setText(text);
        text_event = findViewById(R.id.button7);
        text = "Magic (" + player.getPlayer_magic() + ")";
        text_event.setText(text);
        text_event = findViewById(R.id.button6);
        text = "Lucky (" + player.getPlayer_lucky() + ")";
        text_event.setText(text);
    }

    public void consti(View view) {
        player.player_levelup(1);
        end_fight(true);
    }

    public void stren(View view) {
        player.player_levelup(2);
        end_fight(true);
    }

    public void dext(View view) {
        player.player_levelup(3);
        end_fight(true);
    }

    public void vita(View view) {
        player.player_levelup(4);
        end_fight(true);
    }

    public void cunn(View view) {
        player.player_levelup(5);
        end_fight(true);
    }

    public void magi(View view) {
        player.player_levelup(6);
        end_fight(true);
    }

    public void luc(View view) {
        player.player_levelup(7);
        end_fight(true);
    }
    //end

    public void game_over() {
        setContentView(R.layout.game_over);
        click_and_button_set();
        music_choose(5);
        reset_game();
    }

    //e possivel vencer?
    public void win_game() {
        music_choose(4);
        setContentView(R.layout.win_game);
        click_and_button_set();
        TextView text_event = findViewById(R.id.win_text);
        String text = "Congratulations " + player.getPlayer_name() + "!\n\n   You finished the game!";
        text_event.setText(text);

        reset_game();
    }

    public void exit(View view) {
        moveTaskToBack(true);
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(1);
    }

    public void menu_return(View view) {
        click_and_button_set();
        Intent resultIntent = new Intent();
        resultIntent.putExtra("result", my_sound.enable_song);
        setResult(RESULT_OK, resultIntent);
        finish();
    }

    private void save_game() {
        SharedPreferences.Editor editor = getSharedPreferences("adventure_save", MODE_PRIVATE).edit();
        JSONArray attributes = player.get_load_attributes();
        editor.putString("name", player.getPlayer_name());
        editor.putInt("id_classe", player.get_id_by_class());
        editor.putString("attributes", attributes.toString());
        editor.putBoolean("armor", armor);
        editor.putBoolean("book", book);
        editor.putBoolean("ring", ring);
        editor.putBoolean("sword", sword);
        editor.apply();
    }

    public void load_game() {

        SharedPreferences prefs = getSharedPreferences("adventure_save", Context.MODE_PRIVATE);
        String name = prefs.getString("name", "Irineu");
        int id_class = prefs.getInt("id_classe", 0);
        player = new Player(name, id_class);
        try {
            JSONArray jsonArray = new JSONArray(prefs.getString("attributes", "[]"));
            int[] attributes = new int[jsonArray.length()];
            for (int i = 0; i < jsonArray.length(); i++) {
                attributes[i] = jsonArray.getInt(i);
            }
            player.load_player(attributes);
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "Erro load image player", Toast.LENGTH_SHORT).show();
            exit(null);
        }
        armor = prefs.getBoolean("armor", false);
        sword = prefs.getBoolean("sword", false);
        ring = prefs.getBoolean("ring", false);
        book = prefs.getBoolean("book", false);

        out_combat(false);
        /*
        SharedPreferences preferences = getSharedPreferences("AUTHENTICATION_FILE_NAME", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("Authentication_Id",userid.getText().toString());
        editor.putString("Authentication_Password",password.getText().toString());
        editor.putString("Authentication_Status","true");
        editor.apply();
        */
    }

    //music
    public void music_choose(int id_music){
        if(!my_sound.enable_song){
            return;
        }
        switch (id_music) {
            case 1:
                if(!my_sound.combat_song){//não reiniciar musica ja existente
                    stop_song();
                    call_play_song_battle(null);
                }
                break;
            case 2:
                if(!my_sound.out_combat_song){
                    stop_song();
                    call_play_song_ambient(null);
                }
                break;
            case 3:
                stop_song();
                call_play_song_ambient2(null);
                break;
            case 4:
                stop_song();
                call_play_song_win(null);
                break;
            case 5:
                stop_song();
                call_play_song_dead(null);
                break;
        }
    }

    public void click_and_button_set(){
        click_sound(this);
        button_off();
    }

    public void call_play_song_battle(View view){
        if( music_set(view, 1) == 1) {
            song_battle(this);
            play_song_fun();
        }
    }

    public void call_play_song_ambient(View view){
        if( music_set(view, 2) == 1) {
            song_ambient(this);
            play_song_fun();
        }
    }

    public void call_play_song_ambient2(View view){
        if( music_set(view, 3) == 1) {
            song_ambient2(this);
            play_song_fun();
        }
    }

    public void call_play_song_win(View view){
        if( music_set(view, 3) == 1) {
            song_win(this);
            play_song_fun();
        }
    }

    public void call_play_song_dead(View view){
        if( music_set(view, 3) == 1) {
            song_dead(this);
            play_song_fun();
        }
    }

    public void song_battle(Context con){
        music = MediaPlayer.create(con, R.raw.battle);
        music.setLooping(true);
        music.setVolume(0.3f, 0.3f);
    }

    public void song_ambient(Context con){
        music = MediaPlayer.create(con, R.raw.ambient);
        music.setLooping(true);
    }

    public  void song_ambient2(Context con){
        music = MediaPlayer.create(con, R.raw.ambient2);
        music.setLooping(true);
    }

    public  void song_win(Context con){
        music = MediaPlayer.create(con, R.raw.win);
    }

    public  void song_dead(Context con){
        music = MediaPlayer.create(con, R.raw.dead);
        music.setVolume(0.7f, 0.7f);
    }

    public void click_sound(Context con){
        if(my_sound.enable_song) {
            final MediaPlayer mp = MediaPlayer.create(con, R.raw.click);
            mp.setVolume(0.2f, 0.2f);
            mp.start();
        }
    }

    public void miss(Context con){
        if(my_sound.enable_song) {
            final MediaPlayer mp = MediaPlayer.create(con, R.raw.miss);
            mp.setVolume(0.2f, 0.2f);
            mp.start();
        }
    }

    public void phy_attack(Context con){
        if(my_sound.enable_song) {
            final MediaPlayer mp = MediaPlayer.create(con, R.raw.metal_attack);
            mp.setVolume(0.2f, 0.2f);
            mp.start();
        }
    }
    public void poison_attack(Context con){
        if(my_sound.enable_song) {
            final MediaPlayer mp = MediaPlayer.create(con, R.raw.poison_attack);
            mp.setVolume(0.2f, 0.2f);
            mp.start();
        }
    }
    public void ice_attack(Context con){
        if(my_sound.enable_song) {
            final MediaPlayer mp = MediaPlayer.create(con, R.raw.ice_magic);
            mp.setVolume(0.2f, 0.2f);
            mp.start();
        }
    }

    public void heat_attack(Context con){
        if(my_sound.enable_song) {
            final MediaPlayer mp = MediaPlayer.create(con, R.raw.fire_magic);
            mp.setVolume(0.2f, 0.2f);
            mp.start();
        }
    }

    public void electric_attack(Context con){
        if(my_sound.enable_song) {
            final MediaPlayer mp = MediaPlayer.create(con, R.raw.electric_magic);
            mp.setVolume(0.2f, 0.2f);
            mp.start();
        }
    }

    public void click_sound2(Context con){
        if(my_sound.enable_song) {
            final MediaPlayer mp = MediaPlayer.create(con, R.raw.click2);
            mp.setVolume(0.3f, 0.3f);
            mp.start();
        }
    }

    public void level_up_sound(Context con){
        if(my_sound.enable_song) {
            final MediaPlayer mp = MediaPlayer.create(con, R.raw.level);
            mp.setVolume(0.9f, 0.9f);
            mp.start();
        }
    }

    public void drink_sound(Context con){
        if(my_sound.enable_song) {
            final MediaPlayer mp = MediaPlayer.create(con, R.raw.drink);
            mp.setVolume(0.5f, 0.5f);
            mp.start();
        }
    }

    public void set_enable_song() {
        if (my_sound.enable_song) {
            my_sound.enable_song = false;
            my_sound.combat_song = false;
            my_sound.out_combat_song = false;
            button_off();
        } else {
            my_sound.enable_song = true;
            Button b = findViewById(R.id.sound);
            b.setBackgroundResource(android.R.drawable.ic_lock_silent_mode_off);
        }
    }

    public void button_off(){
        if(!my_sound.enable_song) {
            Button b = findViewById(R.id.sound);
            b.setBackgroundResource(android.R.drawable.ic_lock_silent_mode);
        }
    }

    public int music_set(View view, int music_type) {
        if (view != null) {
            set_enable_song();
        }

        if (music == null) {
            if(my_sound.enable_song) {
                switch (music_type) {
                    case 1:
                        my_sound.out_combat_song = false;
                        my_sound.combat_song = true;
                        break;
                    case 2:
                        my_sound.out_combat_song = true;
                        my_sound.combat_song = false;
                        break;
                    case 3:
                        my_sound.out_combat_song = false;
                        my_sound.combat_song = false;
                }
            }
            return 1;
        } else{
            stop_song();
            return 0;
        }
    }

    private void play_song_fun() {
        music.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                stop_song();
            }
        });
        music.start();
    }

    public void stop_song(){
        if(music != null){
            my_sound.out_combat_song = false;
            my_sound.combat_song = false;
            music.release();
            music = null;
        }
    }

    //

    @Override
    protected void onResume(){
        super.onResume();
        if(my_sound.enable_song){
            if(!my_sound.combat_song){
                call_play_song_battle(null);
            }
            if(!my_sound.out_combat_song){
                call_play_song_ambient(null);
            }
        }
    }

    @Override
    protected void onStop(){
        super.onStop();
        my_sound.stop_song();
    }
}