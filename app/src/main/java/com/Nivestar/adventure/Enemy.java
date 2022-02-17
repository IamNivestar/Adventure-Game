package com.Nivestar.adventure;

import android.widget.Toast;
import android.content.Context;

import java.util.Random;

public class Enemy {

    private int enemy_hp;
    private int enemy_dmg_phy;
    private String enemy_name;
    private int drop_chance;
    private int xp_value;
    private int enemy_lvl;
    private int enemy_armor_penetration;
    private int enemy_energy_max;
    private int enemy_energy;
    private int enemy_armor;
    private int enemy_dodge;
    private int enemy_persuasion;
    private int enemy_critical;
    private int enemy_resis_heat;
    private int enemy_resis_cold;
    private int enemy_resis_poison;
    private int enemy_resis_eletric;
    private int enemy_dmg_heat;
    private int enemy_dmg_cold;
    private int enemy_dmg_poison;
    private int enemy_dmg_electric;

    static Random rand = new Random();

    public Enemy(int player_rank){
        int r = rand.nextInt(100);
        int enemy_id = 0;

        if(r<1) {
            enemy_id = player_rank+3;
        }else if(r<5){
            enemy_id = player_rank+2;
        }else if(r<25){
            enemy_id = player_rank+1;
        }else if(r<70){
            enemy_id = player_rank;
        }else if(r<90){
            enemy_id = player_rank-1;
        }else{
            enemy_id = player_rank-2;
        }
        if(enemy_id < 1){
            enemy_id = 1;
        }
        enemies(enemy_id);
        enemy_energy = enemy_energy_max ;
    }

    public void enemies(int id){
        int r = rand.nextInt(100);
        switch (id) {
            case 1:
                this.enemy_lvl = 1;
                level1(r);
                break;
            case 2:
                this.enemy_lvl = 2;
                level2(r);
                break;
            case 3:
                this.enemy_lvl = 3;
                level3(r);
                break;
            case 4:
                this.enemy_lvl = 4;
                level4(r);
                break;
            case 5:
                set_drop_xp(50, 5);
                set_ap_e_a_d_p_c(1,2,2,2,4,4);
                set_resis_he_co_po_el(0,0,0,0);
                set_dmg_phy_he_co_po_el(10,0,0,0,0);
                this.enemy_hp = rand.nextInt(20 - 10 + 1) + 10;
                this.enemy_name = "Dungeon's Guardian(BOSS)";
            default:
                set_drop_xp(50, 5);
                set_ap_e_a_d_p_c(1,2,2,2,4,4);
                set_resis_he_co_po_el(0,0,0,0);
                set_dmg_phy_he_co_po_el(10,0,0,0,0);
                this.enemy_hp = rand.nextInt(20 - 10 + 1) + 10;
                this.enemy_name = "Defaultt";
        }
    }

    public void level1(int r){
        if (r < 25) {
            set_drop_xp(50, 5);
            set_ap_e_a_d_p_c(1,2,2,2,4,4);
            set_resis_he_co_po_el(0,0,0,0);
            set_dmg_phy_he_co_po_el(10,0,0,0,0);
            this.enemy_hp = rand.nextInt(20 - 10 + 1) + 10;
            this.enemy_name = "Goblin";
        } else if (r < 45) {
            set_drop_xp(50, 5);
            set_ap_e_a_d_p_c(1,2,2,2,4,4);
            set_resis_he_co_po_el(0,0,0,0);
            set_dmg_phy_he_co_po_el(10,0,0,0,0);
            this.enemy_hp = rand.nextInt(20 - 10 + 1) + 10;
            this.enemy_name = "Zombie";
        } else if (r < 65) {
            set_drop_xp(50, 5);
            set_ap_e_a_d_p_c(1,2,2,2,4,4);
            set_resis_he_co_po_el(0,0,0,0);
            set_dmg_phy_he_co_po_el(10,0,0,0,0);
            this.enemy_hp = rand.nextInt(20 - 10 + 1) + 10;
            this.enemy_name = "Giant Spider";
        } else if (r < 85) {
            set_drop_xp(50, 5);
            set_ap_e_a_d_p_c(1,2,2,2,4,4);
            set_resis_he_co_po_el(0,0,0,0);
            set_dmg_phy_he_co_po_el(10,0,0,0,0);
            this.enemy_hp = rand.nextInt(20 - 10 + 1) + 10;
            this.enemy_name = "Skeleton";
        } else {
            set_drop_xp(50, 5);
            set_ap_e_a_d_p_c(1,2,2,2,4,4);
            set_resis_he_co_po_el(0,0,0,0);
            set_dmg_phy_he_co_po_el(10,0,0,0,0);
            this.enemy_hp = rand.nextInt(20 - 10 + 1) + 10;
            this.enemy_name = "Baby Dragon";
        }
    }

    public void level2(int r){
        if (r < 30) {
            set_drop_xp(50, 5);
            set_ap_e_a_d_p_c(1,2,2,2,4,4);
            set_resis_he_co_po_el(0,0,0,0);
            set_dmg_phy_he_co_po_el(10,0,0,0,0);
            this.enemy_hp = rand.nextInt(20 - 10 + 1) + 10;
            this.enemy_name = "Ghul";
        } else if (r < 50) {
            set_drop_xp(50, 5);
            set_ap_e_a_d_p_c(1,2,2,2,4,4);
            set_resis_he_co_po_el(0,0,0,0);
            set_dmg_phy_he_co_po_el(10,0,0,0,0);
            this.enemy_hp = rand.nextInt(20 - 10 + 1) + 10;
            this.enemy_name = "Witch";
        } else if (r < 75) {
            set_drop_xp(50, 5);
            set_ap_e_a_d_p_c(1,2,2,2,4,4);
            set_resis_he_co_po_el(0,0,0,0);
            set_dmg_phy_he_co_po_el(10,0,0,0,0);
            this.enemy_hp = rand.nextInt(20 - 10 + 1) + 10;
            this.enemy_name = "Rabid Bear";
        } else {
            set_drop_xp(50, 5);
            set_ap_e_a_d_p_c(1,2,2,2,4,4);
            set_resis_he_co_po_el(0,0,0,0);
            set_dmg_phy_he_co_po_el(10,0,0,0,0);
            this.enemy_hp = rand.nextInt(20 - 10 + 1) + 10;
            this.enemy_name = "ORC";
        }
    }

    public void level3(int r){
        if (r < 25) {
            set_drop_xp(50, 5);
            set_ap_e_a_d_p_c(1,2,2,2,4,4);
            set_resis_he_co_po_el(0,0,0,0);
            set_dmg_phy_he_co_po_el(10,0,0,0,0);
            this.enemy_hp = rand.nextInt(20 - 10 + 1) + 10;
            this.enemy_name = "Minotaur";
        } else if (r < 45) {
            set_drop_xp(50, 5);
            set_ap_e_a_d_p_c(1,2,2,2,4,4);
            set_resis_he_co_po_el(0,0,0,0);
            set_dmg_phy_he_co_po_el(10,0,0,0,0);
            this.enemy_hp = rand.nextInt(20 - 10 + 1) + 10;
            this.enemy_name = "Deep Demon";
        } else if (r < 60) {
            set_drop_xp(50, 5);
            set_ap_e_a_d_p_c(1,2,2,2,4,4);
            set_resis_he_co_po_el(0,0,0,0);
            set_dmg_phy_he_co_po_el(10,0,0,0,0);
            this.enemy_hp = rand.nextInt(20 - 10 + 1) + 10;
            this.enemy_name = "Crazy Adventure";
        } else if (r < 80) {
            set_drop_xp(50, 5);
            set_ap_e_a_d_p_c(1,2,2,2,4,4);
            set_resis_he_co_po_el(0,0,0,0);
            set_dmg_phy_he_co_po_el(10,0,0,0,0);
            this.enemy_hp = rand.nextInt(20 - 10 + 1) + 10;
            this.enemy_name = "Medium Dragon";
        } else {
            set_drop_xp(50, 5);
            set_ap_e_a_d_p_c(1,2,2,2,4,4);
            set_resis_he_co_po_el(0,0,0,0);
            set_dmg_phy_he_co_po_el(10,0,0,0,0);
            this.enemy_hp = rand.nextInt(20 - 10 + 1) + 10;
            this.enemy_name = "Troll";
        }
    }

    public void level4(int r){
        if (r < 28) {
            set_drop_xp(50, 5);
            set_ap_e_a_d_p_c(1,2,2,2,4,4);
            set_resis_he_co_po_el(0,0,0,0);
            set_dmg_phy_he_co_po_el(10,0,0,0,0);
            this.enemy_hp = rand.nextInt(20 - 10 + 1) + 10;
            this.enemy_name = "Ghost Warrior";
        } else if (r < 65) {
            set_drop_xp(50, 5);
            set_ap_e_a_d_p_c(1,2,2,2,4,4);
            set_resis_he_co_po_el(0,0,0,0);
            set_dmg_phy_he_co_po_el(10,0,0,0,0);
            this.enemy_hp = rand.nextInt(20 - 10 + 1) + 10;
            this.enemy_name = "Abomination of Caves";
        } else {
            set_drop_xp(50, 5);
            set_ap_e_a_d_p_c(1,2,2,2,4,4);
            set_resis_he_co_po_el(0,0,0,0);
            set_dmg_phy_he_co_po_el(10,0,0,0,0);
            this.enemy_hp = rand.nextInt(20 - 10 + 1) + 10;
            this.enemy_name = "Ancient Dragon";
        }
    }

    public void set_dmg_phy_he_co_po_el(int phy, int he, int co, int po, int el){
        this.enemy_dmg_phy = phy;
        this.enemy_dmg_heat = he;
        this.enemy_dmg_cold = co;
        this.enemy_dmg_poison = po;
        this.enemy_dmg_electric = el;
    }

    public void set_resis_he_co_po_el(int he, int co, int po, int el) {
        this.enemy_resis_heat = he;
        this.enemy_resis_cold = co;
        this.enemy_resis_poison = po;
        this.enemy_resis_eletric = el;
    }

    public void set_drop_xp(int dc, int xp){
        this.drop_chance = dc;
        this.xp_value = xp;
    }

    public void set_ap_e_a_d_p_c(int ap, int e, int a, int d, int p, int c){
        this.enemy_armor_penetration = ap;
        this.enemy_energy_max = e;
        this.enemy_armor = a;
        this.enemy_dodge = d;
        this.enemy_persuasion = p;
        this.enemy_critical = c;
    }

    public void enemy_receive_dmg(int dmg){
        this.enemy_hp -= dmg;
    }

    public boolean enemy_drop(){
        int luck = rand.nextInt(100);
        if(luck > (100 - this.drop_chance) ){
            return true;
        }else{
            return false;
        }
    }

    public int enemy_att_phy(int pla_armor){
        int min, dmg;
        min = (int) (Math.round(this.enemy_dmg_phy * 0.8));
        dmg = rand.nextInt(this.enemy_dmg_phy - min + 1) + min;
        pla_armor = pla_armor - this.enemy_armor_penetration;
        if(pla_armor < 0)
            pla_armor = 0;
        dmg = Math.round(dmg*(100-pla_armor)/100);
        return dmg;
    }

    public int enemy_att_heat(int pla_heat){
        int min, dmg;
        min = (int) (Math.round(this.enemy_dmg_heat * 0.8));
        dmg = rand.nextInt(this.enemy_dmg_heat - min + 1) + min;
        dmg = Math.round(dmg*(100-pla_heat)/100);
        return dmg;
    }
    public int enemy_att_cold(int play_resist){
        int min, dmg;
        min = (int) (Math.round(this.enemy_dmg_cold * 0.8));
        dmg = rand.nextInt(this.enemy_dmg_cold - min + 1) + min;
        dmg = Math.round(dmg*(100-play_resist)/100);
        return dmg;
    }
    public int enemy_att_eletric(int play_resist){
        int min, dmg;
        min = (int) (Math.round(this.enemy_dmg_electric * 0.8));
        dmg = rand.nextInt(this.enemy_dmg_electric - min + 1) + min;
        dmg = Math.round(dmg*(100-play_resist)/100);
        return dmg;
    }
    public int enemy_att_poison(int play_resist){
        int min, dmg;
        min = (int) (Math.round(this.enemy_dmg_poison * 0.8));
        dmg = rand.nextInt(this.enemy_dmg_poison - min + 1) + min;
        dmg = Math.round(dmg*(100-play_resist)/100);
        return dmg;
    }

    public boolean enemy_att_dodge(int play_dodge){
        int lucky = rand.nextInt(100);
        if(lucky < play_dodge)
            return true;
        else{
            return false;
        }
    }

    public boolean critic(){
        int luck = rand.nextInt(100);
        if( luck> (100-this.enemy_critical)) {
            return true;
        }else{
            return false;
        }
    }

    public int enemy_exhaustion(int dmg){
        if( (this.enemy_energy - 1) < 0){
            dmg = (int)(Math.round(dmg/2));
            return dmg;
        }else{
            this.enemy_energy -=1;
            return dmg;
        }
    }

    //g and s

    public int getEnemy_dmg_phy() {
        return enemy_dmg_phy;
    }

    public int getEnemy_armor() {
        return enemy_armor;
    }

    public int getEnemy_resis_heat() {
        return enemy_resis_heat;
    }

    public int getEnemy_resis_cold() {
        return enemy_resis_cold;
    }

    public int getEnemy_resis_poison() {
        return enemy_resis_poison;
    }

    public int getEnemy_resis_eletric() {
        return enemy_resis_eletric;
    }

    public String getEnemy_name() {
        return this.enemy_name;
    }

    public int getEnemy_hp() {
        return this.enemy_hp;
    }

    public int getXp_value() {
        return this.xp_value;
    }

    public int getEnemy_lvl() {
        return this.enemy_lvl;
    }

    public int getEnemy_dodge() {
        return enemy_dodge;
    }

    public int getEnemy_armor_penetration() {
        return enemy_armor_penetration;
    }

    public int getEnemy_energy_max() {
        return enemy_energy_max;
    }

    public int getEnemy_energy() {
        return enemy_energy;
    }

    public int getEnemy_critical() {
        return enemy_critical;
    }

    public int getEnemy_dmg_heat() {
        return enemy_dmg_heat;
    }

    public int getEnemy_dmg_cold() {
        return enemy_dmg_cold;
    }

    public int getEnemy_dmg_poison() {
        return enemy_dmg_poison;
    }

    public int getEnemy_dmg_electric() {
        return enemy_dmg_electric;
    }

    public int getEnemy_persuasion() {
        return enemy_persuasion;
    }
}