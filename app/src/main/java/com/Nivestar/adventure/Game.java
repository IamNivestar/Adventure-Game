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
    private static Boolean enable_song;
    MediaPlayer music;

    //control boleans
    private static Boolean heal_out = false;
    private static Boolean armor = false;
    private static Boolean sword = false;
    private static Boolean ring = false;
    private static Boolean book = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String name;
        int id_class;
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
        if (id_class == 9) { //load
            load_game();
        } else { //new game
            player = new Player(name, id_class);
            start_game();
        }
    }

    public void start_game() {
        click_sound2();
        setContentView(R.layout.start);
        button_off();
        ImageView img = findViewById(R.id.start_image);
        img.setImageResource(LoadPlayerImage());

        enemy = new Enemy(player.getPlayer_lvl());
    }

    public void explorer(View view) {
        heal_out = true;
        int luck = rand.nextInt(100);
        setContentView(R.layout.event);
        button_off();
        TextView text_event;
        String text;
        ImageView img = findViewById(R.id.image_event);
        if (luck == 0) {
            click_sound();
            img.setImageResource(R.drawable.fall);
            if (enable_song) {
                stop_song();
                play_song_ambient2(null);
            }
            text_event = findViewById(R.id.event_texto);
            text = "You are very unlucky!\n\n" + player.getPlayer_name() + " fell into a pit!\n\n" + player.getPlayer_name() + " died !";
            text_event.setText(text);
            player.setPlayer_hp(0);

        } else if (luck < 4 && player.getPlayer_potion() > 0) {
            click_sound();
            img.setImageResource(R.drawable.thielf);
            if (enable_song) {
                stop_song();
                play_song_ambient2(null);
            }
            text_event = findViewById(R.id.event_texto);
            text = " Oh shit!\n\nA thief goblin stole one potion! ";
            text_event.setText(text);
            player.setPlayer_potion(player.getPlayer_potion() - 1);

        } else if (luck >= 4 && luck <= 8) {
            click_sound();
            img.setImageResource(R.drawable.trap);
            if (enable_song) {
                stop_song();
                play_song_ambient2(null);
            }
            int dmg = (int) Math.round((player.getPlayer_hp_max()) * 0.2);
            text_event = findViewById(R.id.event_texto);
            text = "You fell into a blade trap!\n\n" + player.getPlayer_name() + " received " + dmg + " damage";
            text_event.setText(text);
            player.player_receive_dmg(dmg);

        } else if (luck >= 80 && luck < 88 && (player.getPlayer_hp() < player.getPlayer_hp_max() * 0.6)) {
            click_sound();
            img.setImageResource(R.drawable.supplies);
            int heal = (int) Math.round((player.getPlayer_hp_max() * 0.15));
            player.setPlayer_hp(heal + player.getPlayer_hp());
            text_event = findViewById(R.id.event_texto);
            text = player.getPlayer_name() + " find supplies\n\n" +
                    player.getPlayer_name() + " recovered " + heal + " HP\n";
            text_event.setText(text);

        } else if (luck >= 89 && luck <= 98 && player.getPlayer_potion() == 0) {
            click_sound();
            img.setImageResource(R.drawable.potion_found);
            player.setPlayer_potion(player.getPlayer_potion() + 1);
            text_event = findViewById(R.id.event_texto);
            text = player.getPlayer_name() + " are lucky\n" +
                    player.getPlayer_name() + " found a potion!!!\n" +
                    player.getPlayer_name() + " have " + player.getPlayer_potion() + " potion(s)";
            text_event.setText(text);
        } else if (luck > 98) {  //get artifact!!
            find_artifact(img);
        } else { //batalha
            if (enable_song) {
                stop_song();
                play_song_battle(null);
            }
            enemy = new Enemy(player.getPlayer_lvl());
            fight(view);
        }
    }

    public void find_artifact(ImageView img){
        TextView text_event;
        String text;
        click_sound();
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
                    boolean evolve = player.add_xp(100);
                    if (evolve) {
                        player.setPlayer_hp(player.getPlayer_hp_max());
                        text = player.getPlayer_name() + " found an artifact !!\n" +
                                player.getPlayer_name() + " a book with reports of fighting and ancient techniques\n\n" +
                                player.getPlayer_name() + "'s got 100 experience points\n" +
                                player.getPlayer_name() + " level up for LEVEL " + player.getPlayer_lvl() + "!\n" + player.getPlayer_name()
                                + "'s  health has restored and your status improved !!!!";
                    } else {
                        text = player.getPlayer_name() + " found an artifact !!\n" +
                                player.getPlayer_name() + " a book with reports of fighting and ancient techniques\n\n" +
                                player.getPlayer_name() + "'s get 100 experience";
                    }
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

    public void out_combat(View view) {
        if (player.getPlayer_hp() < 1) {
            player_game_over();
        } else {
            click_sound();
            setContentView(R.layout.out_combat);
            TextView text_out;
            String text;

            if (player.getPlayer_hp() > 0.9 * player.getPlayer_hp_max()) {
                heal_out = false;
            }
            if (heal_out) {
                int heal = (int) Math.round(player.getPlayer_hp_max() * 0.05); //5% of regen
                text_out = findViewById(R.id.player_recovery);
                text = "Being out of combat makes you regenerate a small part of your HP and recover your energy.";
                text_out.setText(text);
                player.setPlayer_hp(player.getPlayer_hp() + heal);
                heal_out = false;
            }
            player.player_recover_energy();
            out_combat_no_heal(view);
            save_game();
        }
    }

    public void out_combat_no_heal(View view) {
        click_sound();
        setContentView(R.layout.out_combat);
        button_off();
        TextView text_out;
        String text;
        if (enable_song) {
            stop_song();
            play_song_ambient(null);
        }
        ImageView img = findViewById(R.id.out_image);
        img.setImageResource(LoadPlayerImage());

        text_out = findViewById(R.id.player_potion2);
        text = player.getPlayer_name() + " has " + player.getPlayer_potion() + " potion(s).";
        text_out.setText(text);

        text_out = findViewById(R.id.player_st_out);
        text = "    " + player.getPlayer_name() + "   " + player.getPlayer_class() + "\n level " + player.getPlayer_lvl() +
                "    XP = " + player.getPlayer_xp() + "/" + player.getNecessary_xp();
        text_out.setText(text);

        text_out = findViewById(R.id.player_at_out);
        text = player.getPlayer_dmg_phy() + "";
        text_out.setText(text);

        text_out = findViewById(R.id.player_hp_out);
        text = player.getPlayer_hp() + "/" + player.getPlayer_hp_max();
        text_out.setText(text);

        text_out = findViewById(R.id.player_crit_out);
        text = player.getPlayer_critical() + " %";
        text_out.setText(text);
    }

    public void fight(View view) {

        click_sound();
        setContentView(R.layout.enemy_fight);
        button_off();
        TextView text_fight;
        String text;
        disable_buttons_combat();

        ImageView img = findViewById(R.id.combat_image);
        img.setImageResource(LoadPlayerImage());

        img = findViewById(R.id.enemy_image);
        LoadEnemyImage(img);

        text_fight = findViewById(R.id.player_st);
        text = player.getPlayer_name() + "   " + player.getPlayer_class() + "\nXP = " + player.getPlayer_xp() + "/" + player.getNecessary_xp() + "  level " + player.getPlayer_lvl();
        text_fight.setText(text);

        text_fight = findViewById(R.id.player_at);
        text = player.getPlayer_dmg_phy() + "";
        text_fight.setText(text);

        text_fight = findViewById(R.id.player_hp);
        text = player.getPlayer_hp() + "/" + player.getPlayer_hp_max();
        text_fight.setText(text);

        text_fight = findViewById(R.id.player_sta);
        text = player.getPlayer_energy() + "/" + player.getPlayer_energy_max();
        text_fight.setText(text);

        text_fight = findViewById(R.id.enemy_st);
        text = enemy.getEnemy_name() + "\n  level " + enemy.getEnemy_lvl();
        text_fight.setText(text);

        text_fight = findViewById(R.id.enemy_at);
        text = enemy.getEnemy_dmg_phy() + "";
        text_fight.setText(text);

        text_fight = findViewById(R.id.enemy_hp);
        text = " " + enemy.getEnemy_hp();
        text_fight.setText(text);

        text_fight = findViewById(R.id.enemy_sta);
        text = enemy.getEnemy_energy() + "/" + enemy.getEnemy_energy_max();
        text_fight.setText(text);

        text_fight = findViewById(R.id.fight_title);
        text = player.getPlayer_name() + " came across a " + enemy.getEnemy_name() + " !";
        text_fight.setText(text);

        text_fight = findViewById(R.id.player_potion);
        text = player.getPlayer_name() + " has " + player.getPlayer_potion() + " potion(s).";
        text_fight.setText(text);

        //intentional delay to press
        new Handler().postDelayed(new Runnable() {

            public void run() {
                if (enemy.getEnemy_hp() <= 0 || player.getPlayer_hp() <= 0) {
                    end_fight();
                    return;
                }
                Button fgt = findViewById(R.id.exit10);
                Button exit = findViewById(R.id.exit11);
                Button dri = findViewById(R.id.exit12);
                fgt.setEnabled(true);
                exit.setEnabled(true);
                dri.setEnabled(true);
            }
        }, 200);
    }

    //attack functions
    public int[] get_player_dmg(){
        int[] dmg = new int[5];
        dmg[0] = player.player_att_phy(enemy.getEnemy_armor());
        dmg[1] = player.player_att_heat(enemy.getEnemy_resis_heat());
        dmg[2] = player.player_att_cold(enemy.getEnemy_resis_cold());
        dmg[3] = player.player_att_eletric(enemy.getEnemy_resis_eletric());
        dmg[4] = player.player_att_poison(enemy.getEnemy_resis_poison());
        return dmg;
    }

    public int[] get_enemy_dmg(){
        int[] dmg = new int[5];
        dmg[0] = enemy.enemy_att_phy(player.getPlayer_armor());
        dmg[1] = enemy.enemy_att_heat(player.getPlayer_resis_heat());
        dmg[2] = enemy.enemy_att_cold(player.getPlayer_resis_cold());
        dmg[3] = enemy.enemy_att_eletric(player.getPlayer_resis_eletric());
        dmg[4] = enemy.enemy_att_poison(player.getPlayer_resis_poison());
        return dmg;
    }

    public void reset_visuals(ImageView img, TextView text_fight){
        new Handler().postDelayed( new Runnable() {
            public void run() {
                img.setImageResource(0);
                text_fight.setText(" ");
            }
        }, 1000);
    }

    public void disable_buttons_combat(){
        Button fgt = findViewById(R.id.exit10);
        Button exit = findViewById(R.id.exit11);
        Button dri = findViewById(R.id.exit12);
        fgt.setEnabled(false);
        exit.setEnabled(false);
        dri.setEnabled(false);
    }

    public void text_color_att(TextView text_fight, int i){
        switch (i) {
            case 0:
                text_fight.setTextColor(Color.rgb(255, 255, 255));
                break;
            case 1:
                text_fight.setTextColor(Color.rgb(200, 0, 0));
                break;
            case 2:
                text_fight.setTextColor(Color.rgb(0, 200, 255));
                break;
            case 3:
                text_fight.setTextColor(Color.rgb(150, 0, 200));
                break;
            case 4:
                text_fight.setTextColor(Color.rgb(0, 200, 0));
                break;
        }
    }

    public void player_attack(){
        TextView text_fight;
        ImageView img;
        String text;
        text_fight = findViewById(R.id.player_dmg);
        boolean crit = player.critic();
        int[] dmg = get_player_dmg();
        int dmgTotal = 0;

        if (player.player_att_dodge(enemy.getEnemy_dodge())) {
            text = "miss";
            text_fight.setText(text);
            img = findViewById(R.id.slice_enemy);
            img.setImageResource(R.drawable.slice);
            reset_visuals(img, text_fight);
        }else{  //hit
            for (int i = 0; i < 5; i++) {
                if (dmg[i] != 0) {
                    if (crit) {
                        text = dmg[i] + " CRITIC";
                        text_fight.setText(text);
                        dmgTotal = dmgTotal + 2 * (dmg[i]);
                    } else {
                        text = dmg[i] + "";
                        dmgTotal += dmg[i];
                    }
                    text_fight.setText(text);
                    text_color_att(text_fight, i);
                    img = findViewById(R.id.slice_enemy);
                    img.setImageResource(R.drawable.slice);
                    reset_visuals(img, text_fight);
                }
            }
        }
        dmgTotal = player.player_exhaustion(dmgTotal); //if player get exhausted the damage will be half
        enemy.enemy_receive_dmg(dmgTotal);
    }

    public void enemy_attack(){
        TextView text_fight;
        ImageView img;
        String text;

        text_fight = findViewById(R.id.enemy_dmg);
        boolean crit = enemy.critic();
        int[] dmg = get_enemy_dmg();
        int dmgTotal = 0;

        if (enemy.enemy_att_dodge(player.getPlayer_dodge())) {
            text = "miss";
            text_fight.setText(text);
            img = findViewById(R.id.slice_player);
            img.setImageResource(R.drawable.slice);
            reset_visuals(img, text_fight);
        }else{  //hit
            for (int i = 0; i < 5; i++) {
                if (dmg[i] != 0) {
                    if (crit) {
                        text = dmg[i] + " CRITIC";
                        text_fight.setText(text);
                        dmgTotal = dmgTotal + 2 * (dmg[i]);
                    } else {
                        text = dmg[i] + "";
                        dmgTotal += dmg[i];
                    }
                    text_fight.setText(text);
                    text_color_att(text_fight, i);
                    img = findViewById(R.id.slice_player);
                    img.setImageResource(R.drawable.slice);
                    reset_visuals(img, text_fight);
                }
            }
        }
        dmgTotal = enemy.enemy_exhaustion(dmgTotal); //if player get exhausted the damage will be half
        player.player_receive_dmg(dmgTotal);
    }

    public void attack(View view) {

        disable_buttons_combat();
        fight_click();

        player_attack();  //talvez passaro view
        enemy_attack();

        new Handler().postDelayed(new Runnable() {
            public void run() {
                fight(null);
            }
        }, 2000);
    }
    //end

    public void drink(View view) {
        click_sound();
        setContentView(R.layout.combat_action);
        button_off();
        TextView text_event = findViewById(R.id.combat_texto);
        String text;

        ImageView img = findViewById(R.id.imagem_combat);

        int luck = rand.nextInt(100);

        if (player.getPlayer_potion() == 0) {
            text = "You cannot do it!\n\n" + player.getPlayer_name() + " don't have any potions!!";
            text_event.setText(text);
            img.setImageResource(R.drawable.dungeon);
        } else if (luck < 10) {
            int[] dmg = get_enemy_dmg();
            int dmgTotal = 0;
            for (int i = 0; i < 5; i++)
                dmgTotal += dmg[i];

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
            drink_sound();
            int heal = player.drink_potion();
            text = player.getPlayer_name() + " drank one potion!\n\n" +
                    player.getPlayer_name() + " recovered " + heal + " HP";
            text_event.setText(text);
            player.setPlayer_potion(player.getPlayer_potion() - 1);
            img.setImageResource(R.drawable.potion_found);
        }
    }

    public void escape(View view) {
        click_sound();
        setContentView(R.layout.combat_action);
        button_off();
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
            if (enable_song) {
                stop_song();
                play_song_ambient(null);
            }
            img = findViewById(R.id.image_event);
            img.setImageResource(R.drawable.run);
            text_event = findViewById(R.id.event_texto);
            text = "\n\n" + player.getPlayer_name() + " ran away successfully";
            text_event.setText(text);
        } else {
            int[] dmg = get_enemy_dmg();
            int dmgTotal = 0;
            for (int i = 0; i < 5; i++)
                dmgTotal += dmg[i];
            text_event = findViewById(R.id.combat_texto);
            player.player_receive_dmg(dmgTotal);
            text = player.getPlayer_name() + " tried to escape but " + enemy.getEnemy_name() + " didn't allow\n\n" +
                    enemy.getEnemy_name() + " attacked " + player.getPlayer_name() + "!\n\n" +
                    player.getPlayer_name() + " received " + dmgTotal + " damage";
            text_event.setText(text);
        }
    }

    public void end_fight() {
        if (enemy.getEnemy_hp() <= 0 && player.getPlayer_hp() > 0) {
            boolean evolve = player.add_xp(enemy.getXp_value());
            if (evolve) {
                LevelUp();
            }
            setContentView(R.layout.combat_report);
            button_off();
            TextView text_fight;
            String text;
            stop_song();
            if (evolve) {
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
            button_off();
            TextView text_fight;
            String text;
            stop_song();
            text_fight = findViewById(R.id.fight_xp);
            text = enemy.getEnemy_name() + " defeat " + player.getPlayer_name() + " !\n\n"
                    + player.getPlayer_name() + " die.";
            text_fight.setText(text);
        }
    }

    public void drink_out(View view) {
        click_sound();
        setContentView(R.layout.event);
        button_off();
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
                drink_sound();
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

    public void status_player(View view) {
        click_sound();
        setContentView(R.layout.status);
        button_off();
        TextView text_event = findViewById(R.id.status_txt);
        String text;

        int p_ap = player.getPlayer_armor_penetration();
        int p_d = player.getPlayer_dodge();
        int p_p = player.getPlayer_persuasion();
        int p_c = player.getPlayer_critical();
        int p_rh = player.getPlayer_resis_heat();
        int p_rc = player.getPlayer_resis_cold();
        int p_re = player.getPlayer_resis_eletric();
        int p_rp = player.getPlayer_resis_poison();
        int p_dh = player.getPlayer_dmg_heat();
        int p_dc = player.getPlayer_dmg_cold();
        int p_de = player.getPlayer_dmg_electric();
        int p_dp = player.getPlayer_dmg_poison();

        text = "\nArmor Penetration= " + p_ap + "%\ndodge= " + p_d + "%\npersuasion= " + p_p + "\ncritical= " + p_c + "%\nresis_heat= " +
                p_rh + "%\nresist_cold= " + p_rc + "%\nresist_eletric= " + p_re + "%\nresist_poison= " + p_rp + "%\ndano_heat= " +
                p_dh + "\ndano_cold= " + p_dc + "\ndano_eletric= " + p_de + "\ndano_poison= " + p_dp;
        text_event.setText(text);
    }

    public void status_out_combat(View view) {
        click_sound();
        setContentView(R.layout.status_out_combate);
        button_off();
        TextView text_event = findViewById(R.id.status_txt);
        String text;

        int p_ap = player.getPlayer_armor_penetration();
        int p_d = player.getPlayer_dodge();
        int p_p = player.getPlayer_persuasion();
        int p_c = player.getPlayer_critical();
        int p_rh = player.getPlayer_resis_heat();
        int p_rc = player.getPlayer_resis_cold();
        int p_re = player.getPlayer_resis_eletric();
        int p_rp = player.getPlayer_resis_poison();
        int p_dh = player.getPlayer_dmg_heat();
        int p_dc = player.getPlayer_dmg_cold();
        int p_de = player.getPlayer_dmg_electric();
        int p_dp = player.getPlayer_dmg_poison();

        text = "\nArmor Penetration= " + p_ap + "%\ndodge= " + p_d + "%\npersuasion= " + p_p + "\ncritical= " + p_c + "%\nresis_heat= " +
                p_rh + "%\nresist_cold= " + p_rc + "%\nresist_eletric= " + p_re + "%\nresist_poison= " + p_rp + "%\ndano_heat= " +
                p_dh + "\ndano_cold= " + p_dc + "\ndano_eletric= " + p_de + "\ndano_poison= " + p_dp;
        text_event.setText(text);
    }

    public void status_enemy(View view) {
        click_sound();
        setContentView(R.layout.status);
        button_off();
        TextView text_event = findViewById(R.id.status_txt);
        String text;

        int e_ap = enemy.getEnemy_armor_penetration();
        int e_d = enemy.getEnemy_dodge();
        int e_p = enemy.getEnemy_persuasion();
        int e_c = enemy.getEnemy_critical();
        int e_rh = enemy.getEnemy_resis_heat();
        int e_rc = enemy.getEnemy_resis_cold();
        int e_re = enemy.getEnemy_resis_eletric();
        int e_rp = enemy.getEnemy_resis_poison();
        int e_dh = enemy.getEnemy_dmg_heat();
        int e_dc = enemy.getEnemy_dmg_cold();
        int e_de = enemy.getEnemy_dmg_electric();
        int e_dp = enemy.getEnemy_dmg_poison();
        text = "\nArmor Penetration= " + e_ap + "%\ndodge= " + e_d + "%\npersuasion= " + e_p + "\ncritical= " + e_c + "%\nresis_heat= " +
                e_rh + "%\nresist_cold= " + e_rc + "%\nresist_eletric= " + e_re + "%\nresist_poison= " + e_rp + "%\ndano_heat= " +
                e_dh + "\ndano_cold= " + e_dc + "\ndano_eletric= " + e_de + "\ndano_poison= " + e_dp;
        text_event.setText(text);
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

    public void player_game_over() {
        //game over menu
        click_sound();
        setContentView(R.layout.game_over);
        button_off();
        if (enable_song) {
            stop_song();
            play_song_dead(null);
        }
        reset_game();
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
                Toast.makeText(getApplicationContext(), "Erro load image player", Toast.LENGTH_SHORT).show();
                exit(null);
                return -1;
        }
    }

    public void LoadEnemyImage(ImageView img) {
        switch (enemy.getEnemy_name()) {
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
            case "Rabid Bear":
                img.setImageResource(R.drawable.bear);
                break;
            case "Ghul":
                img.setImageResource(R.drawable.ghul);
                break;
            case "Deep Demon":
                img.setImageResource(R.drawable.demon);
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
        level_up_sound();
        setContentView(R.layout.levelup);
        button_off();
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
        text = "Constitution (" + player.getPlayer_vitality() + ")";
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
    }

    public void stren(View view) {
        player.player_levelup(2);
    }

    public void dext(View view) {
        player.player_levelup(3);
    }

    public void vita(View view) {
        player.player_levelup(4);
    }

    public void cunn(View view) {
        player.player_levelup(5);
    }

    public void magi(View view) {
        player.player_levelup(6);
    }

    public void luc(View view) {
        player.player_levelup(7);
    }
    //end

    //e possivel vencer?
    public void win_game() {
        click_sound();
        setContentView(R.layout.win_game);
        button_off();
        TextView text_event = findViewById(R.id.win_text);
        String text = "Congratulations " + player.getPlayer_name() + "!\n\n   You finished the game!";
        text_event.setText(text);
        if (enable_song) {
            stop_song();
            play_song_win(null);
        }
        reset_game();
    }

    public void exit(View view) {
        moveTaskToBack(true);
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(1);
    }

    public void menu_return(View view) {
        click_sound();
        Intent resultIntent = new Intent();
        resultIntent.putExtra("result", enable_song);
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

    public void load_game(){

        SharedPreferences prefs = getSharedPreferences("adventure_save", Context.MODE_PRIVATE);
        String name = prefs.getString("name","Irineu");
        int id_class = prefs.getInt("id_classe",0);
        player = new Player(name, id_class);
        try {
            JSONArray jsonArray = new JSONArray(prefs.getString("attributes", "[]"));
            int[] attributes = new int[jsonArray.length()];
            for (int i = 0; i < jsonArray.length(); i++) {
                attributes[i] = jsonArray.getInt(i);
            }
            player.load_player(attributes);
        }
        catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "Erro load image player", Toast.LENGTH_SHORT).show();
            exit(null);
        }
        armor =prefs.getBoolean("armor",false);
        sword =prefs.getBoolean("sword",false);
        ring =prefs.getBoolean("ring",false);
        book =prefs.getBoolean("book",false);

        out_combat(null);
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
    public  void play_song_battle(View view){
        if(view !=null ){
            set_enable_song();
        }
        if(music == null){
            music = MediaPlayer.create(this, R.raw.battle);
            music.setLooping(true);
            music.setVolume(0.4f, 0.4f);
            play_song_fun();
        }else{
            stop_song();
        }
    }

    public  void play_song_ambient(View view){
        if(view !=null ){
            set_enable_song();
        }
        if(music == null){
            music = MediaPlayer.create(this, R.raw.ambient);
            music.setLooping(true);
            play_song_fun();
        }else{
            stop_song();
        }
    }

    public  void play_song_ambient2(View view){
        if(view !=null ){
            set_enable_song();
        }
        if(music == null){
            music = MediaPlayer.create(this, R.raw.ambient2);
            music.setLooping(true);
            play_song_fun();
        }else{
            stop_song();
        }
    }

    public  void play_song_win(View view){
        if(view !=null ){
            set_enable_song();
        }
        if(music == null){
            music = MediaPlayer.create(this, R.raw.win);
            play_song_fun();
        }else{
            stop_song();
        }
    }

    public  void play_song_dead(View view){
        if(view !=null ){
            set_enable_song();
        }
        if(music == null){
            music = MediaPlayer.create(this, R.raw.dead);
            music.setVolume(0.7f, 0.7f);
            play_song_fun();
        }else{
            stop_song();
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

    public void fight_click(){
        if(enable_song) {
            final MediaPlayer mp = MediaPlayer.create(this, R.raw.fight_click);
            mp.setVolume(0.2f, 0.2f);
            mp.start();
        }
    }

    public void click_sound2(){
        if(enable_song) {
            final MediaPlayer mp = MediaPlayer.create(this, R.raw.click2);
            mp.setVolume(0.3f, 0.3f);
            mp.start();
        }
    }

    public void level_up_sound(){
        if(enable_song) {
            final MediaPlayer mp = MediaPlayer.create(this, R.raw.level);
            mp.setVolume(0.7f, 0.7f);
            mp.start();
        }
    }

    public void drink_sound(){
        if(enable_song) {
            final MediaPlayer mp = MediaPlayer.create(this, R.raw.drink);
            mp.setVolume(0.5f, 0.5f);
            mp.start();
        }
    }


    private void button_off(){
        if(!enable_song) {
            Button b = findViewById(R.id.sound);
            b.setBackgroundResource(android.R.drawable.ic_lock_silent_mode);
        }
    }

    @Override
    protected void onResume(){
        super.onResume();
        if(enable_song){
            play_song_ambient(null);
        }
    }

    @Override
    protected void onStop(){
        super.onStop();
        stop_song();
    }
}
