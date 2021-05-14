package com.Nivestar.adventure;

import java.util.Random;

public class Player {

    private String player_name;
    private String player_class;
    private int player_at_max;
    private int player_at_min;
    private int player_hp_max;
    private int player_hp;
    private int player_potion;
    private int player_xp;
    private int player_lvl;
    private int player_critical;
    private int necessary_xp;

    static Random rand = new Random();

    public Player(String name, int player_class){

        switch (player_class){
            case 1:
                this.player_class = "Warrior";
                this.player_at_max = 15;
                this.player_at_min = 10;
                this.player_hp_max = 80;
                this.player_potion = 2;
                this.player_critical = 20;
                this.necessary_xp = 40;
                break;
            case 2:
                this.player_class = "Wizard";
                this.player_at_max = 17;
                this.player_at_min = 5;
                this.player_hp_max = 60;
                this.player_potion = 3;
                this.necessary_xp = 30;
                this.player_critical = 5;
                break;
            case 3:
                this.player_class = "Berserker";
                this.player_at_max = 20;
                this.player_at_min = 10;
                this.player_hp_max = 50;
                this.player_potion = 2;
                this.necessary_xp = 40;
                this.player_critical = 40;
                break;
            default:
                System.out.println("erro!!");
        }
        this.player_name = name;
        this.player_lvl = 1;
        this.player_xp = 0;
        this.player_hp= this.player_hp_max;
    }

    public void load_player(int at_min, int at_max, int critic, int hp, int hp_max, int xp, int xp_nes, int potion, int lvl){

        this.player_at_min = at_min;
        this.player_at_max = at_max;
        this.player_critical = critic;
        this.player_hp= hp;
        this.player_hp_max = hp_max;
        this.player_xp = xp;
        this.necessary_xp = xp_nes;
        this.player_potion = potion;
        this.player_lvl = lvl;
    }

    public int add_xp(int count){
        if(this.player_lvl == 5){
            return 0;
        }
        else{
            this.player_xp += count;
            if(this.player_xp >= this.necessary_xp) {
                this.player_xp = 0;
                this.player_lvl++;
                this.player_hp_max += 10 + Math.round((this.player_hp_max) * 0.25);
                this.player_hp = this.player_hp_max;
                this.player_at_max += Math.round((this.player_at_max) * 0.3);
                this.player_at_min += Math.round((this.player_at_max) * 0.2);

                if (this.player_lvl == 5) {
                    this.necessary_xp = 0;
                } else {
                    this.necessary_xp = (this.necessary_xp * 2);
                }
                return 1;
            }
            else{
                return 0;
            }
        }
    }

    public int player_attacking(int critic){
        int luck = rand.nextInt(100);
        if (luck<10){
            return 0;
        }else{
            if (critic == 1){
                return ( rand.nextInt(this.player_at_max - this.player_at_min + 1 ) + this.player_at_min ) * 2;
            }
            else{
                return rand.nextInt(this.player_at_max - this.player_at_min + 1 ) + this.player_at_min;
            }
        }
    }

    public int critic( ){
        int luck = rand.nextInt(100);
        if( luck> (100-this.player_critical)) {
            return 1;
        }else{
            return 0;
        }
    }

    public void player_receive_dmg(int dmg){
        this.player_hp -= dmg;
    }

    public int drink_potion(){
        int heal = (int) (Math.round( 25 + (0.35*player_hp_max)));
        this.player_hp += heal;
        if(this.player_hp > this.player_hp_max){ //not healing more than max health
            this.player_hp = this.player_hp_max;
        }
        return heal;
    }

    //g and s

    public void setPlayer_potion(int player_potion) {
        if(player_potion > 4){//You can't carry more than 4 potions!
            this.player_potion = 4;
        }else{
            this.player_potion = player_potion;
        }
    }

    public String getPlayer_class() {
        return this.player_class;
    }

    public int getNecessary_xp() {
        return this.necessary_xp;
    }

    public String getPlayer_name() {
        return this.player_name;
    }

    public int getPlayer_hp() {
        return this.player_hp;
    }

    public void setPlayer_hp(int player_hp) {
        if(player_hp > this.player_hp_max){
            this.player_hp = this.player_hp_max;
        }else{
            this.player_hp = player_hp;
        }
    }

    public int getPlayer_potion() {
        return this.player_potion;
    }

    public int getPlayer_xp() {
        return this.player_xp;
    }

    public int getPlayer_lvl() {
        return this.player_lvl;
    }

    public int getPlayer_hp_max() {
        return this.player_hp_max;
    }

    public int getPlayer_at_max() {
        return this.player_at_max;
    }

    public int getPlayer_at_min() {
        return this.player_at_min;
    }

    public void setPlayer_at_max(int player_at_max) {
        this.player_at_max = player_at_max;
    }

    public void setPlayer_at_min(int player_at_min) {
        this.player_at_min = player_at_min;
    }

    public void setPlayer_hp_max(int player_hp_max) {
        this.player_hp_max = player_hp_max;
    }

    public int getPlayer_critical() {
        return player_critical;
    }

    public void setPlayer_critical(int player_critical) {
        this.player_critical = player_critical;
    }
}