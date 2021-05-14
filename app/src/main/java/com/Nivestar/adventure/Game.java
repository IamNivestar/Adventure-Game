package com.Nivestar.adventure;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Random;

/*
        if(enable_song){
            stop_song();
            play_song_battle(null);
        }
 */

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
    private static Boolean especial = false;

    //Toast.makeText(getApplicationContext(), "Eita!! olhe o else!!", Toast.LENGTH_SHORT).show();

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
            }else {
                name = extras.getString("name");
                id_class = extras.getInt("number", 0);
                enable_song = extras.getBoolean("set");
            }
        } else {
            Toast.makeText(getApplicationContext(), "Eita!! olhe o else!!", Toast.LENGTH_SHORT).show();
            name = (String) savedInstanceState.getSerializable("name");
            id_class = (int) savedInstanceState.getInt("id_class");  // i don't know about this! i created cuz i do not got it what has done on the last
            enable_song = (boolean) savedInstanceState.getBoolean("set");
        }
        if(id_class == 9){ //load
            SharedPreferences prefs = getSharedPreferences("adventure_save", Context.MODE_PRIVATE);
            name = prefs.getString("name", "Irineu");
            id_class = prefs.getInt("id_class", 1);

            player = new Player(name, id_class);

            int at_min = prefs.getInt("at_min", 1);
            int at_max = prefs.getInt("at_max", 1);
            int critic = prefs.getInt("critic", 1);
            int hp = prefs.getInt("hp", 1);
            int hp_max = prefs.getInt("hp_max", 1);
            int xp = prefs.getInt("xp", 1);
            int xp_nes = prefs.getInt("xp_nes", 1);
            int potion = prefs.getInt("potion", 1);
            int lvl = prefs.getInt("lvl", 1);
            armor = prefs.getBoolean("armor",false);
            sword = prefs.getBoolean("sword",false);
            ring = prefs.getBoolean("ring",false);
            book = prefs.getBoolean("book",false);

            /*
            SharedPreferences preferences = getSharedPreferences("AUTHENTICATION_FILE_NAME", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("Authentication_Id",userid.getText().toString());
            editor.putString("Authentication_Password",password.getText().toString());
            editor.putString("Authentication_Status","true");
            editor.apply();
            */

            player.load_player(at_min, at_max, critic, hp, hp_max, xp, xp_nes, potion, lvl);
            out_combat(null);
        }else{
            player = new Player(name, id_class);
            start_game();
        }
    }

    public void start_game() {
        click_sound2();
        setContentView(R.layout.start);
        button_off();
        ImageView img = findViewById(R.id.start_image);

        switch ( player.getPlayer_class() ) {
            case "Warrior":
                img.setImageResource(R.drawable.w1);
                break;
            case "Wizard":
                img.setImageResource(R.drawable.wi1);
                break;
            case "Berserker":
                img.setImageResource(R.drawable.b1);
                break;
        }
        enemy = new Enemy(player.getPlayer_lvl());
        //maybe player name and avatar
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

        } else if (luck > 0 && luck < 4 && player.getPlayer_potion() > 0) {
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

        } else if (luck >= 90 && luck <= 97 && player.getPlayer_potion() == 0) {
            click_sound();
            img.setImageResource(R.drawable.potion_found);
            player.setPlayer_potion(player.getPlayer_potion() + 1);
            text_event = findViewById(R.id.event_texto);
            text = player.getPlayer_name() + " are lucky\n" +
                    player.getPlayer_name() + " found a potion!!!\n" +
                    player.getPlayer_name() + " have " + player.getPlayer_potion() + " potion(s)";
            text_event.setText(text);
        } else if (luck > 97){  //get item
            click_sound();
            while (true){
                int luck2 = rand.nextInt(100);
                if(luck2 < 25){
                    if(armor == false){
                        img.setImageResource(R.drawable.armor);
                        armor = true;
                        player.setPlayer_hp_max(player.getPlayer_hp_max()+30);
                        text_event = findViewById(R.id.event_texto);
                        text = player.getPlayer_name() + " found an artifact !!\n" +
                                player.getPlayer_name() + " found the Sacred Armor\n\n" +
                                player.getPlayer_name() + "'s max health increase from "+ (player.getPlayer_hp_max()-30)
                                + " to " + player.getPlayer_hp_max();
                        text_event.setText(text);
                        break;
                    }
                }else if(luck2<45){
                    if(sword == false){
                        sword = true;
                        img.setImageResource(R.drawable.sword);
                        player.setPlayer_at_max(player.getPlayer_at_max()+10);
                        player.setPlayer_at_min(player.getPlayer_at_min()+5);
                        text_event = findViewById(R.id.event_texto);
                        text = player.getPlayer_name() + " found an artifact !!\n" +
                                player.getPlayer_name() + " found the enchanted Sword ANDÚRIL!!\n\n" +
                                player.getPlayer_name() + "'s attack increase from "+ (player.getPlayer_at_min()-5)
                                + '/' + (player.getPlayer_at_max()-10) + " to " + player.getPlayer_at_min() + "/" + player.getPlayer_at_max();
                        text_event.setText(text);
                        break;
                    }

                }else if(luck2<70){
                    if(book == false){
                        book = true;
                        img.setImageResource(R.drawable.book);
                        text_event = findViewById(R.id.event_texto);
                        if(player.getPlayer_lvl() == 5){
                            player.setPlayer_hp(player.getPlayer_hp_max()); // if at max level will heal all health
                            text = player.getPlayer_name() + " found an artifact !!\n" +
                                    player.getPlayer_name() + " a book with reports of fighting and ancient techniques\n\n" +
                                    player.getPlayer_name() + "'s healed all your health";
                        }else {
                            int evolve = player.add_xp(100);
                            if (evolve == 1) {
                                player.setPlayer_hp(player.getPlayer_hp_max());
                                text = player.getPlayer_name() + " found an artifact !!\n" +
                                        player.getPlayer_name() + " a book with reports of fighting and ancient techniques\n\n" +
                                        player.getPlayer_name() + "'s got 100 experience points\n" +
                                        player.getPlayer_name() + " level up for LEVEL " + player.getPlayer_lvl() +"!\n" + player.getPlayer_name()
                                        + "'s  health has restored and your status improved !!!!";
                                        
                            } else {
                                text = player.getPlayer_name() + " found an artifact !!\n" +
                                    player.getPlayer_name() + " a book with reports of fighting and ancient techniques\n\n" +
                                    player.getPlayer_name() + "'s get 100 experience";
                            }
                        }
                        text_event.setText(text);
                        break;
                    }
                }else if(luck2<90){
                    if(ring == false){
                        ring = true;
                        img.setImageResource(R.drawable.ring);
                        player.setPlayer_critical(player.getPlayer_critical()+30);
                        text_event = findViewById(R.id.event_texto);
                        text = player.getPlayer_name() + " found an artifact !!\n" +
                                player.getPlayer_name() + " found the enchanted Ring of Luck\n\n" +
                                player.getPlayer_name() + "'s critic chance increase from "+ (player.getPlayer_critical()-30)
                                + " to " + player.getPlayer_critical();
                        text_event.setText(text);
                        break;
                    }
                }else{
                    if(especial == false){
                        especial = true;
                        //img.setImageResource(R.drawable.fall);
                        /*
                        player.setPlayer_hp_max(player.getPlayer_at_max()+10);
                        text_event = findViewById(R.id.event_texto);
                        text = player.getPlayer_name() + " found an artifact !!\n" +
                                player.getPlayer_name() + " found the Sacred Armor\n\n" +
                                player.getPlayer_name() + "'s max health increase from "+ (player.getPlayer_hp_max()-30)
                                + " to " + player.getPlayer_hp_max();
                        text_event.setText(text);
                        break;
                        */
                    }
                }
            }
        }else{
            if(enable_song){
                stop_song();
                play_song_battle(null);
            }
            enemy = new Enemy(player.getPlayer_lvl());
            enemy_fight(view);
        }
    }

    public void out_combat(View view) {
        click_sound();
        if (player.getPlayer_hp() < 1) {
            player_game_over();
        }else{
            setContentView(R.layout.out_combat);
            button_off();
            TextView text_out;
            String text;
            if(enable_song){
                stop_song();
                play_song_ambient(null);
            }
            ImageView img = (ImageView) findViewById(R.id.out_image);

            switch ( player.getPlayer_class() ) {
                case "Warrior":
                    img.setImageResource(R.drawable.w1);
                    break;
                case "Wizard":
                    img.setImageResource(R.drawable.wi1);
                    break;
                case "Berserker":
                    img.setImageResource(R.drawable.b1);
                    break;
            }
            if(player.getPlayer_hp() > 0.9*player.getPlayer_hp_max()){
                heal_out = false;
            }
            if (heal_out == true) {
                int heal = (int) Math.round(player.getPlayer_hp_max() * 0.05);
                text_out = findViewById(R.id.player_recovery);
                text = "Being out of combat makes you regenerate a small part of your HP.";
                text_out.setText(text);
                player.setPlayer_hp(player.getPlayer_hp() + heal);
                heal_out = false;
            }

            text_out = findViewById(R.id.player_potion2);
            text = player.getPlayer_name() + " has " + player.getPlayer_potion() + " potion(s).";
            text_out.setText(text);

            text_out = findViewById(R.id.player_st_out);
            text = "    "+ player.getPlayer_name() + "   " + player.getPlayer_class() + "\n level " + player.getPlayer_lvl() +
                    "    XP = " + player.getPlayer_xp() + "/" + player.getNecessary_xp();
            text_out.setText(text);

            text_out = findViewById(R.id.player_at_out);
            text = player.getPlayer_at_min() + " - " +  player.getPlayer_at_max();
            text_out.setText(text);

            text_out = findViewById(R.id.player_hp_out);
            text = player.getPlayer_hp() + "/" + player.getPlayer_hp_max();
            text_out.setText(text);

            text_out = findViewById(R.id.player_crit_out);
            text = player.getPlayer_critical()+ " %";
            text_out.setText(text);

            save_game();
        }
    }

    public void enemy_fight(View view) {

        click_sound();
        setContentView(R.layout.enemy_fight);
        button_off();
        TextView text_fight = null;
        String text;

        Button fgt = findViewById(R.id.exit10);
        Button exit =  findViewById(R.id.exit11);
        Button dri =  findViewById(R.id.exit12);
        fgt.setEnabled(false);
        exit.setEnabled(false);
        dri.setEnabled(false);

        ImageView img = findViewById(R.id.combat_image);

        switch ( player.getPlayer_class() ) {
            case "Warrior":
                img.setImageResource(R.drawable.w2);
                break;
            case "Wizard":
                img.setImageResource(R.drawable.wi2);
                break;
            case "Berserker":
                img.setImageResource(R.drawable.b2);
                break;
        }
        img = (ImageView) findViewById(R.id.enemy_image);
        switch ( enemy.getEnemy_name() ) {
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
        }

        text_fight = findViewById(R.id.player_st);
        text = player.getPlayer_name() + "   " + player.getPlayer_class() + "\nXP = " + player.getPlayer_xp() + "/" + player.getNecessary_xp() + "  level " + player.getPlayer_lvl();
        text_fight.setText(text);

        text_fight = findViewById(R.id.player_at);
        text = player.getPlayer_at_min() + "-" +  player.getPlayer_at_max();
        text_fight.setText(text);

        text_fight = findViewById(R.id.player_hp);
        text = player.getPlayer_hp() + "/" + player.getPlayer_hp_max();
        text_fight.setText(text);

        text_fight = findViewById(R.id.player_crit);
        text = " "+player.getPlayer_critical()+ " %";
        text_fight.setText(text);

        text_fight = findViewById(R.id.enemy_st);
        text = enemy.getEnemy_name() + "\n  level " + enemy.getEnemy_lvl();
        text_fight.setText(text);

        text_fight = findViewById(R.id.enemy_at);
        text = enemy.getEnemy_at_max() + " (MAX)";
        text_fight.setText(text);

        text_fight = findViewById(R.id.enemy_hp);
        text = " " + enemy. getEnemy_hp();
        text_fight.setText(text);

        text_fight = findViewById(R.id.fight_title);
        text = player.getPlayer_name()+" came across a " + enemy.getEnemy_name() + " !";
        text_fight.setText(text);

        text_fight = findViewById(R.id.player_potion);
        text = player.getPlayer_name()+" has " + player.getPlayer_potion() + " potion(s).";
        text_fight.setText(text);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
            if( enemy.getEnemy_hp() <= 0 || player.getPlayer_hp() <= 0){
                end_fight();
                return;
            }
            Button fgt = findViewById(R.id.exit10);
            Button exit =  findViewById(R.id.exit11);
            Button dri =  findViewById(R.id.exit12);
            fgt.setEnabled(true);
            exit.setEnabled(true);
            dri.setEnabled(true);
            }
        }, 200);
    }

    public void attack(View view) {

        Button fgt = findViewById(R.id.exit10);
        Button exit =  findViewById(R.id.exit11);
        Button dri =  findViewById(R.id.exit12);
        fgt.setEnabled(false);
        exit.setEnabled(false);
        dri.setEnabled(false);

        fight_click();
        TextView text_fight;
        String text;

        //player dmg
        text_fight = findViewById(R.id.player_dmg);
        int critic = player.critic();
        int dmg = player.player_attacking(critic);
        if ( dmg == 0){
            text = "miss";
        }else{
            if(critic == 1){
                text = dmg +" CRITIC";
                text_fight.setText(text);
            }else{
                text = dmg + "";
            }
        }
        text_fight.setText(text);
        enemy.enemy_receive_dmg(dmg);

        ImageView img = findViewById(R.id.slice_enemy);
        img.setImageResource(R.drawable.slice);

        //after seconds
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                ImageView img = findViewById(R.id.slice_enemy);
                img.setImageResource(0);
                TextView text_fight = findViewById(R.id.player_dmg);
                String text = " ";
                text_fight.setText(text);
            }
        }, 900);
        //enemy dmg
        text_fight = findViewById(R.id.enemy_dmg);
        dmg = enemy.enemy_attacking();
        if ( dmg == 0){
            text = "miss";
        }else{
            text = ""+ dmg;
        }
        text_fight.setText(text);
        player.player_receive_dmg(dmg);

        img = findViewById(R.id.slice_player);
        img.setImageResource(R.drawable.slice);
        Handler handler2 = new Handler();
        handler2.postDelayed(new Runnable() {
            public void run() {
                ImageView img = findViewById(R.id.slice_player);
                img.setImageResource(0);
                TextView text_fight = findViewById(R.id.enemy_dmg);
                String text = " ";
                text_fight.setText(text);
            }
        }, 900);

        Handler handler3 = new Handler();
        handler3.postDelayed(new Runnable() {
            @Override
            public void run() {
                enemy_fight(null);
            }
        }, 1000);
    }

    public void end_fight(){

        setContentView(R.layout.combat_fight);
        button_off();
        TextView text_fight;
        String text;
        if (enemy.getEnemy_hp() <= 0 && player.getPlayer_hp()>0) {
            stop_song();
            text_fight = findViewById(R.id.fight_xp);
            text = player.getPlayer_name() + " defeat " + enemy.getEnemy_name() +
                    "\n\n" + player.getPlayer_name() + " got " + enemy.getXp_value() + " experience points.";
            text_fight.setText(text);

            if (enemy.getEnemy_name() == "Dungeon's Guardian(BOSS)") {
                win_game();
            }else {
                if (enemy.enemy_drop()) {
                    text_fight = findViewById(R.id.fight_pot);
                    text = enemy.getEnemy_name() + " drop one potion!";
                    text_fight.setText(text);
                    player.setPlayer_potion(player.getPlayer_potion() + 1);
                }
                int evolve = player.add_xp(enemy.getXp_value());
                if (evolve == 1) {
                    level_up_sound();
                    text_fight = findViewById(R.id.fight_lvl);
                    if (player.getPlayer_lvl() == 5) {
                        text = player.getPlayer_name() + " level up for LEVEL 5!\n\n" + player.getPlayer_name() + "'s health has restored and your status improved!\n"
                                + player.getPlayer_name() + " is at max level now!";
                    } else {
                        text = player.getPlayer_name() + " level up for LEVEL " + player.getPlayer_lvl() + "!\n" + player.getPlayer_name()
                                + "'s  health has restored and your status improved !!!!";
                    }
                    text_fight.setText(text);
                }
            }
        }
        else if (player.getPlayer_hp()<=0){
            text_fight =  findViewById(R.id.fight_xp);
            text = enemy.getEnemy_name() + " defeat " + player.getPlayer_name() +" !\n\n"
                    + player.getPlayer_name() + " die.";
            text_fight.setText(text);
        }
    }

    public void drink(View view) {
        click_sound();
        setContentView(R.layout.combat_action);
        button_off();
        TextView text_event = findViewById(R.id.combat_texto);
        String text;

        ImageView img = findViewById(R.id.imagem_combat);

        int luck = rand.nextInt(100);

        if (player.getPlayer_potion() == 0) {
            text = "You can do it!\n\n" + player.getPlayer_name() + " don't have any potions!!";
            text_event.setText(text);
            img.setImageResource(R.drawable.dungeon);
        }
        else if(luck < 10){
            int dmg = (enemy.enemy_attacking()) / 2;
            player.player_receive_dmg(dmg);
            text =  player.getPlayer_name()+ " tried to take the potion but enemy attacked!!\n\n" +
                    player.getPlayer_name() + " managed to dodge part of the attack, but the potion bottle was broken.\n";
            if (dmg != 0){
                text = text.concat(player.getPlayer_name() + " received " + dmg + " damage (half the total damage)\n");
            }else {
                text = text.concat("The enemy miss your attack\n");
            }
            text_event.setText(text);
            player.setPlayer_potion(player.getPlayer_potion() - 1);
            img.setImageResource(R.drawable.fight);
        } else {
            drink_sound();
            int heal = player.drink_potion();
            text =  player.getPlayer_name() + " drank one potion!\n\n"+
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

        if (enemy.getEnemy_name() == "Crazy Adventure") {
            text_event = findViewById(R.id.combat_texto);
            text = "\n"+ player.getPlayer_name() + " tried to escape but the adventurer follows him madly\n\n" + player.getPlayer_name() +  " cannot run away from this enemy";
            text_event.setText(text);
        }else if (luck >= 20){
            setContentView(R.layout.event);
            button_off();
            if(enable_song) {
                stop_song();
                play_song_ambient(null);
            }
            img =  findViewById(R.id.image_event);
            img.setImageResource(R.drawable.run);
            text_event =  findViewById(R.id.event_texto);
            text =  "\n\n"+ player.getPlayer_name() + " ran away successfully";
            text_event.setText(text);
        }else{
            int dmg = enemy.enemy_attacking();
            text_event = findViewById(R.id.combat_texto);
            player.player_receive_dmg(dmg);
            text =  player.getPlayer_name() + " tried to escape but " + enemy.getEnemy_name() + " didn't allow\n\n" +
                    enemy.getEnemy_name() + " attacked " + player.getPlayer_name() + "!\n\n" +
                    player.getPlayer_name() + " received " + dmg + " damage";
            text_event.setText(text);
        }
    }

    public void drink_out(View view) {
        click_sound();
        setContentView(R.layout.event);
        button_off();
        TextView text_event = (TextView) findViewById(R.id.event_texto);
        String text;
        ImageView img = (ImageView) findViewById(R.id.image_event);

        if (player.getPlayer_potion() == 0) {
            text = "You can do it!\n\n" + player.getPlayer_name() + " don't have any potions!!";
            text_event.setText(text);
            img.setImageResource(R.drawable.dungeon);
        } else {
            int luck = rand.nextInt(100);
            if (luck >= 5) {
                drink_sound();
                int heal = player.drink_potion();
                text =  player.getPlayer_name() + " drank one potion!\n\n"+
                        player.getPlayer_name() + " recovered " + heal +" HP";
                text_event.setText(text);
                player.setPlayer_potion(player.getPlayer_potion() - 1);
                img.setImageResource(R.drawable.potion_found);
            } else {
                text =  "Opsss\n\n"+
                        player.getPlayer_name() + " was startled by a noise and the potion fell, breaking the flask!";
                text_event.setText(text);
                img.setImageResource(R.drawable.dungeon);
                player.setPlayer_potion(player.getPlayer_potion() - 1);
            }
        }
    }

    public void reset_game(){
        String class_p = player.getPlayer_class();
        String name = player.getPlayer_name();
        armor = false;
        sword = false;
        ring = false;
        especial = false;
        book = false;
        switch (class_p){
            case "Warrior":
                player = new Player(name, 1);
                break;
            case "Wizard":
                player = new Player(name, 2);
                break;
            case "Berserker":
                player = new Player(name, 3);
                break;
        }
    }

    public void player_game_over() {
        //game over menu
       click_sound();
       setContentView(R.layout.game_over);
       button_off();
       if(enable_song){
           stop_song();
           play_song_dead(null);
       }
       reset_game();
    }

    public void win_game() {
        click_sound();
        setContentView(R.layout.win_game);
        button_off();
        TextView text_event = findViewById(R.id.win_text);
        String text = "Congratulations " + player.getPlayer_name() + "!\n\n   You finished the game!";
        text_event.setText(text);
        if(enable_song){
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

    public  void menu_return (View view){
        click_sound();
        Intent resultIntent = new Intent();
        resultIntent.putExtra("result", enable_song);
        setResult(RESULT_OK,resultIntent);
        finish();
    }

    private void save_game() {

        int id_class = 4;
        switch (player.getPlayer_class()) {
            case "Warrior":
                id_class = 1;
                break;
            case "Wizard":
                id_class = 2;
                break;
            case "Berserker":
                id_class = 3;
                break;
        }
        SharedPreferences.Editor editor = getSharedPreferences("adventure_save", MODE_PRIVATE).edit();
        editor.putString("name", player.getPlayer_name());
        editor.putInt("id_class", id_class);
        editor.putInt("at_min", player.getPlayer_at_min());
        editor.putInt("at_max", player.getPlayer_at_max());
        editor.putInt("critic", player.getPlayer_critical());
        editor.putInt("hp", player.getPlayer_hp());
        editor.putInt("hp_max", player.getPlayer_hp_max());
        editor.putInt("xp", player.getPlayer_xp());
        editor.putInt("xp_nes", player.getNecessary_xp());
        editor.putInt("potion", player.getPlayer_potion());
        editor.putInt("lvl", player.getPlayer_lvl());
        editor.putBoolean("armor", armor);
        editor.putBoolean("book", book);
        editor.putBoolean("ring", ring);
        editor.putBoolean("sword", sword);
        editor.apply();
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
        if(enable_song == true){
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
        if(enable_song == false) {
            Button b = findViewById(R.id.sound);
            b.setBackgroundResource(android.R.drawable.ic_lock_silent_mode);
        }
    }

    @Override
    protected void onResume(){
        super.onResume();
        if(enable_song==true){
            play_song_ambient(null);
        }
    }

    @Override
    protected void onStop(){
        super.onStop();
        stop_song();
    }
}
