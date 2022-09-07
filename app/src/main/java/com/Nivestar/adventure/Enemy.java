package com.Nivestar.adventure;

import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

public class Enemy {

    private int enemy_hp;
    private int enemy_dmg_phy;
    private String enemy_name;
    private int drop_chance;
    private int spawn_chance;
    private int enemy_class;
    private int xp_value;
    private int enemy_lvl;
    private int enemy_id;
    private int enemy_armor_penetration;
    private int enemy_energy_max;
    private int enemy_energy;
    private int enemy_armor;
    private int enemy_dodge;
    private int enemy_mind;
    private int enemy_critical;
    private int enemy_resis_heat;
    private int enemy_resis_cold;
    private int enemy_resis_poison;
    private int enemy_resis_eletric;
    private int enemy_dmg_heat;
    private int enemy_dmg_cold;
    private int enemy_dmg_poison;
    private int enemy_dmg_electric;

    private int [] enemy_dmg_types_active = new int[5];

    static Random rand = new Random();

    public Enemy(int player_lvl){
        //definindo level de spawn do inimigo

        int r = rand.nextInt(100);
        int lvl = 0;

        if(r<2) { // 2 %
            lvl = player_lvl + rand.nextInt(10 - 4 ) + 4;
        }else if(r<7){ // 5 %
            lvl = player_lvl+3;
        }else if(r<17){ // 10%
            lvl = player_lvl+2;
        }else if(r<32){ // 15%
            lvl = player_lvl+1;
        }else if(r<67){ // 35%
            lvl = player_lvl;
        }else if(r<82){ // 15 %
            lvl = player_lvl-1;
        }else if(r<92){ // 10 %
            lvl = player_lvl-2;
        }else if(r<97){ // 5 %
            lvl = player_lvl-3;
        }else{ //2%
            lvl = player_lvl-rand.nextInt(10);
        }
        if(lvl < 1){
            lvl = 1;
        }
        this.enemy_lvl = lvl;
        enemies_spawn();
        enemy_evolve();
        enemy_energy = enemy_energy_max ;
    }

    public void enemies_spawn(){
        int r = rand.nextInt(100);
        if(this.enemy_lvl < 10) { // weak enemies
            this.enemy_class = 1;
            weak_enemies();
        }else if(this.enemy_lvl < 20 && this.enemy_lvl > 10) { //moderate enemies
            if (r < 50){
                moderate_enemies();
                this.enemy_class = 2;
            }else{
                this.enemy_class = 1;
                weak_enemies();
            }
        }else { // strong enemies
            if(r<30){
                this.enemy_class = 1;
                weak_enemies();
            }
            else if(r<60){
                this.enemy_class = 2;
                moderate_enemies();
            }else{
                this.enemy_class = 3;
                strong_enemies();
            }
        }
    }

    public void weak_enemies(){
        int id = rand.nextInt(18);
        this.enemy_id = id;
        switch (id){
            case 0:
                set_drop_spawn_xp(0, 70, 5);
                set_ap_e_d_m_c(0,4,15,1,25);
                set_resis_a_he_co_po_el(0,0,0,0,100);
                set_dmg_phy_he_co_po_el(0,0,0,0,17);
                this.enemy_hp = rand.nextInt(9 - 8 + 1) + 8;
                this.enemy_name = "Lightning Bug";
                break;
            case 1:
                set_drop_spawn_xp(50, 50, 6);
                set_ap_e_d_m_c(1,4,5,0,10);
                set_resis_a_he_co_po_el(8,0,0,-5,5);
                set_dmg_phy_he_co_po_el(9,0,0,0,0);
                this.enemy_hp = rand.nextInt(20 - 15 + 1) + 15;
                this.enemy_name = "Goblin";
                break;
            case 2:
                set_drop_spawn_xp(0, 100,  5);
                set_ap_e_d_m_c(0,2,0,0,0);
                set_resis_a_he_co_po_el(1,-20,15,20, 10);
                set_dmg_phy_he_co_po_el(7,0,0,2,0);
                this.enemy_hp = rand.nextInt(40 - 5 + 1) + 5;
                this.enemy_name = "Zombie";
                break;
            case 3:
                set_drop_spawn_xp(5, 80, 10);
                set_ap_e_d_m_c(2,6,12,1,5);
                set_resis_a_he_co_po_el(0,0,0,0, -10);
                set_dmg_phy_he_co_po_el(9,0,0,5, 0);
                this.enemy_hp = rand.nextInt(22 - 13 + 1) + 13;
                this.enemy_name = "Giant Spider";
                break;
            case 4:
                set_drop_spawn_xp(5, 100, 8);
                set_ap_e_d_m_c(3,4,6,0,15);
                set_resis_a_he_co_po_el(-5,20,50,100, 100);
                set_dmg_phy_he_co_po_el(15,0,3,0, 0);
                this.enemy_hp = rand.nextInt(15 - 10 + 1) + 10;
                this.enemy_name = "Skeleton";
                break;
            case 5:
                set_drop_spawn_xp(40, 50, 25);
                set_ap_e_d_m_c(5,6,5,5,10);
                set_resis_a_he_co_po_el(12,40,-20,0, 5);
                set_dmg_phy_he_co_po_el(12,8,0,0, 0);
                this.enemy_hp = rand.nextInt(30 - 18 + 1) + 18;
                this.enemy_name = "Baby Dragon";
                break;
            case 6:
                set_drop_spawn_xp(10, 100, 8);
                set_ap_e_d_m_c(0,4,15,3,10);
                set_resis_a_he_co_po_el(95,0,0,100, -20);
                set_dmg_phy_he_co_po_el(0,0,0,15, 0);
                this.enemy_hp = rand.nextInt(10 - 5 + 1) + 5;
                this.enemy_name = "Poisonous Slime";
                break;
            case 7:
                set_drop_spawn_xp(5, 100, 17);
                set_ap_e_d_m_c(0,1,5,1,0);
                set_resis_a_he_co_po_el(0,10,20,95, 0);
                set_dmg_phy_he_co_po_el(0,0,0,28, 0);
                this.enemy_hp = rand.nextInt(24 - 11 + 1) + 11;
                this.enemy_name = "Poisonous Zumbi";
                break;
            case 8:
                set_drop_spawn_xp(10, 84, 16);
                set_ap_e_d_m_c(24,7,25,4,25);
                set_resis_a_he_co_po_el(0,0,0,10, 0);
                set_dmg_phy_he_co_po_el(17,0,0,0, 0);
                this.enemy_hp = rand.nextInt(19 - 14 + 1) + 14;
                this.enemy_name = "Anjanath";
                break;
            case 9:
                set_drop_spawn_xp(10, 100, 10);
                set_ap_e_d_m_c(0,2,20,2,0);
                set_resis_a_he_co_po_el(20,-100,100,0, 0);
                set_dmg_phy_he_co_po_el(0,0,30,0, 0);
                this.enemy_hp = rand.nextInt(10 - 5 + 1) + 5;
                this.enemy_name = "Ice Goop";
                break;
            case 10:
                set_drop_spawn_xp(40, 100, 15);
                set_ap_e_d_m_c(5,5,25,2,30);
                set_resis_a_he_co_po_el(9,0,20,30, -10);
                set_dmg_phy_he_co_po_el(13,0,0,5, 0);
                this.enemy_hp = rand.nextInt(10 - 5 + 1) + 5;
                this.enemy_name = "Infected Human";
                break;
            case 11:
                set_drop_spawn_xp(10, 100, 11);
                set_ap_e_d_m_c(0,5,0,1,0);
                set_resis_a_he_co_po_el(0,-10,0,20, 0);
                set_dmg_phy_he_co_po_el(0,0,0,13, 0);
                this.enemy_hp = rand.nextInt(17 - 12 + 1) + 12;
                this.enemy_name = "Poisonous Lotus";
                break;
            case 12:
                set_drop_spawn_xp(35, 65, 14);
                set_ap_e_d_m_c(2,4,0,10,0);
                set_resis_a_he_co_po_el(1,40,40,40, 40);
                set_dmg_phy_he_co_po_el(19,0,0,0, 0);
                this.enemy_hp = rand.nextInt(22 - 16 + 1) + 16;
                this.enemy_name = "makara";
                break;
            case 13:
                set_drop_spawn_xp(35, 100, 10);
                set_ap_e_d_m_c(0,2,20,0,0);
                set_resis_a_he_co_po_el(80,100,-100,30, 30);
                set_dmg_phy_he_co_po_el(0,28,0,0, 0);
                this.enemy_hp = rand.nextInt(10 - 5 + 1) + 5;
                this.enemy_name = "Lava Slime";
                break;
            case 14:
                set_drop_spawn_xp(5, 100, 5);
                set_ap_e_d_m_c(0,6,40,3,25);
                set_resis_a_he_co_po_el(0,0,-0,0, 5);
                set_dmg_phy_he_co_po_el(12,0,0,0, 0);
                this.enemy_hp = rand.nextInt(18 -16 + 1) + 16;
                this.enemy_name = "Rabid Bat";
                break;
            case 15:
                set_drop_spawn_xp(5, 90, 14);
                set_ap_e_d_m_c(20,4,0,6,20);
                set_resis_a_he_co_po_el(0,-80,30,100, 80);
                set_dmg_phy_he_co_po_el(11,0,0,0, 0);
                this.enemy_hp = rand.nextInt(14 -13 + 1) + 14;
                this.enemy_name = "Mummy";
                break;
            case 16:
                set_drop_spawn_xp(5, 90, 18);
                set_ap_e_d_m_c(0,4,20,4,20);
                set_resis_a_he_co_po_el(0,-60,60,30, 0);
                set_dmg_phy_he_co_po_el(12,0,9,0, 0);
                this.enemy_hp = rand.nextInt(20 -17 + 1) + 17;
                this.enemy_name = "Ice Snake";
                break;
            case 17:
                set_drop_spawn_xp(5, 90, 18);
                set_ap_e_d_m_c(45,4,50,4,80);
                set_resis_a_he_co_po_el(6,40,0,-20, 0);
                set_dmg_phy_he_co_po_el(29,0,0,0, 0);
                this.enemy_hp = rand.nextInt(16 -13 + 1) + 13;
                this.enemy_name = "Demonic Canine";
                break;
            case 18:
                set_drop_spawn_xp(50, 50, 30);
                set_ap_e_d_m_c(0,5,30,7,0);
                set_resis_a_he_co_po_el(24,-20,80,20, 10);
                set_dmg_phy_he_co_po_el(0,0,40,0, 0);
                this.enemy_hp = 30;
                this.enemy_name = "Ice Spectrum";
                break;
        }
    }

    public void moderate_enemies(){
        int id = rand.nextInt(39 - 20 + 1) + 20;
        this.enemy_id = id;
        switch (id){
            case 20:
                set_drop_spawn_xp(20, 100, 60);
                set_ap_e_d_m_c(0,8,30,22,30);
                set_resis_a_he_co_po_el(39,-50,100,0,15);
                set_dmg_phy_he_co_po_el(0,0,33,0,0);
                this.enemy_hp = rand.nextInt(50 - 30 + 1) + 30;
                this.enemy_name = "Ice Warrior";
                break;
            case 21:
                set_drop_spawn_xp(15, 80, 50);
                set_ap_e_d_m_c(5,18,40,20,20);
                set_resis_a_he_co_po_el(15,20,20,25,15);
                set_dmg_phy_he_co_po_el(24,0,0,0,0);
                this.enemy_hp = rand.nextInt(50 - 30 + 1) + 30;
                this.enemy_name = "Ghul";
                break;
            case 22:
                set_drop_spawn_xp(30, 50, 77);
                set_ap_e_d_m_c(0,6,5,40,10);
                set_resis_a_he_co_po_el(5,0,0,-5,5);
                set_dmg_phy_he_co_po_el(0,22,0,0,10);
                this.enemy_hp = rand.nextInt(61 - 44 + 1) + 44;
                this.enemy_name = "Witch";
                break;
            case 23:
                set_drop_spawn_xp(10, 95, 90);
                set_ap_e_d_m_c(30,10,0,10,20);
                set_resis_a_he_co_po_el(20,-15,40,0,-5);
                set_dmg_phy_he_co_po_el(39,0,0,0,0);
                this.enemy_hp = rand.nextInt(61 - 44 + 1) + 44;
                this.enemy_name = "Territorial Bear";
                break;
            case 24:
                set_drop_spawn_xp(40, 100, 82);
                set_ap_e_d_m_c(15,5,10,15,20);
                set_resis_a_he_co_po_el(35,10,20,0,0);
                set_dmg_phy_he_co_po_el(35,0,0,0,0);
                this.enemy_hp = rand.nextInt(55 - 46 + 1) + 46;
                this.enemy_name = "ORC";
                break;
            case 25:
                set_drop_spawn_xp(40, 100, 100);
                set_ap_e_d_m_c(50,6,0,12,15);
                set_resis_a_he_co_po_el(10,10,10,10,10);
                set_dmg_phy_he_co_po_el(60,0,0,0,0);
                this.enemy_hp = rand.nextInt(46 - 45 + 1) + 45;
                this.enemy_name = "Troll";
                break;
            case 26:
                set_drop_spawn_xp(20, 90, 85);
                set_ap_e_d_m_c(15,8,5,20,35);
                set_resis_a_he_co_po_el(30,-5,25,25,20);
                set_dmg_phy_he_co_po_el(51,0,0,0,0);
                this.enemy_hp = rand.nextInt(55 - 40 + 1) + 40;
                this.enemy_name = "Minotaur";
            case 27:
                set_drop_spawn_xp(45, 40, 150);
                set_ap_e_d_m_c(20,16,50,25,35);
                set_resis_a_he_co_po_el(10,10,10,10,10);
                set_dmg_phy_he_co_po_el(22,5,3,7,2);
                this.enemy_hp = rand.nextInt(41 - 30 + 1) + 30;
                this.enemy_name = "Mutant";
            case 28:
                set_drop_spawn_xp(40, 85, 125);
                set_ap_e_d_m_c(25,6,0,20,18);
                set_resis_a_he_co_po_el(35,90,0,10,10);
                set_dmg_phy_he_co_po_el(24,20,0,0,0);
                this.enemy_hp = rand.nextInt(84 - 62 + 1) + 62;
                this.enemy_name = "Medium Dragon";
            case 29:
                set_drop_spawn_xp(90, 75, 165);
                set_ap_e_d_m_c(0,5,10,17,0);
                set_resis_a_he_co_po_el(5,100,-100,50,0);
                set_dmg_phy_he_co_po_el(0,60,0,0,0);
                this.enemy_hp = rand.nextInt(54 - 50 + 1) + 50;
                this.enemy_name = "Fenix";
            case 30:
                set_drop_spawn_xp(30, 89, 125);
                set_ap_e_d_m_c(0,4,10,14,10);
                set_resis_a_he_co_po_el(45,100,-100,20,0);
                set_dmg_phy_he_co_po_el(0,34,0,0,0);
                this.enemy_hp = rand.nextInt(44 - 41 + 1) + 41;
                this.enemy_name = "Lava Golem";
            case 31:
                set_drop_spawn_xp(30, 89, 100);
                set_ap_e_d_m_c(0,4,20,14,0);
                set_resis_a_he_co_po_el(25,25,25,25,25);
                set_dmg_phy_he_co_po_el(20,0,0,0,0);
                this.enemy_hp = rand.nextInt(115 - 88 + 1) + 88;
                this.enemy_name = "Deep Spawn";
            case 32:
                set_drop_spawn_xp(40, 50, 90);
                set_ap_e_d_m_c(10,6,25,10,10);
                set_resis_a_he_co_po_el(5,-20,0,25,-15);
                set_dmg_phy_he_co_po_el(15,0,0,29,0);
                this.enemy_hp = rand.nextInt(35 - 30 + 1) + 30;
                this.enemy_name = "Venomous Spider";
            case 33:
                set_drop_spawn_xp(100, 20, 130);
                set_ap_e_d_m_c(5,6,20,25,10);
                set_resis_a_he_co_po_el(-25,10,10,90,0);
                set_dmg_phy_he_co_po_el(10,0,0,39,0);
                this.enemy_hp = rand.nextInt(51 - 40 + 1) + 40;
                this.enemy_name = "Walking Mushroom";
            case 34:
                set_drop_spawn_xp(80, 90, 170);
                set_ap_e_d_m_c(25,9,40,35,50);
                set_resis_a_he_co_po_el(21,0,-30, 20,0);
                set_dmg_phy_he_co_po_el(28,0,0,0,0);
                this.enemy_hp = rand.nextInt(49 - 41 + 1) + 41;
                this.enemy_name = "Goblin Warrior";
            case 35:
                set_drop_spawn_xp(20, 100, 135);
                set_ap_e_d_m_c(0,4,5,15,15);
                set_resis_a_he_co_po_el(15,-20,70, 0,30);
                set_dmg_phy_he_co_po_el(0,0,32,0,14);
                this.enemy_hp = rand.nextInt(79 - 41 + 1) + 41;
                this.enemy_name = "Tweegon";
            case 36:
                set_drop_spawn_xp(10, 100, 102);
                set_ap_e_d_m_c(0,10,35,15,50);
                set_resis_a_he_co_po_el(11,-30,95, 0,30);
                set_dmg_phy_he_co_po_el(29,0,9,0,0);
                this.enemy_hp = rand.nextInt(43 - 31 + 1) + 31;
                this.enemy_name = "Ice Wolf";
            case 37:
                set_drop_spawn_xp(90, 70, 115);
                set_ap_e_d_m_c(0,19,20,5,90);
                set_resis_a_he_co_po_el(0,-20,20, -30,-25);
                set_dmg_phy_he_co_po_el(33,0,9,0,0);
                this.enemy_hp = rand.nextInt(63 - 61 + 1) + 61;
                this.enemy_name = "Werewolf";
            case 38:
                set_drop_spawn_xp(40, 80, 165);
                set_ap_e_d_m_c(90,6,5,9,0);
                set_resis_a_he_co_po_el(90,25,0, -5,10);
                set_dmg_phy_he_co_po_el(26,0,0,0,0);
                this.enemy_hp = rand.nextInt(93 - 88 + 1) + 88;
                this.enemy_name = "Barroth";
            case 39:
                set_drop_spawn_xp(35, 90, 145);
                set_ap_e_d_m_c(10,8,35,14,20);
                set_resis_a_he_co_po_el(5,0,0, 0,-5);
                set_dmg_phy_he_co_po_el(13,0,0,45,0);
                this.enemy_hp = rand.nextInt(44 - 41 + 1) + 44;
                this.enemy_name = "Acid Reptile";
            case 40:
                set_drop_spawn_xp(45, 100, 127);
                set_ap_e_d_m_c(15,7,35,21,29);
                set_resis_a_he_co_po_el(42,-80,100, 10,0);
                set_dmg_phy_he_co_po_el(24,0,0,0,0);
                this.enemy_hp = rand.nextInt(88 - 81 + 1) + 81;
                this.enemy_name = "Ice Warrior";
            case 41:
                set_drop_spawn_xp(40, 90, 130);
                set_ap_e_d_m_c(25,7,35,14,49);
                set_resis_a_he_co_po_el(18,0,0, 25,60);
                set_dmg_phy_he_co_po_el(39,0,0,0,0);
                this.enemy_hp = rand.nextInt(68 - 65 + 1) + 65;
                this.enemy_name = "Ravishing";

        }
    }

    public void strong_enemies(){
        int id = rand.nextInt(58 - 50 + 1) + 50;
        this.enemy_id = id;
        switch (id){
            case 50:
                set_drop_spawn_xp(66, 66, 266);
                set_ap_e_d_m_c(66,6,33,36,66);
                set_resis_a_he_co_po_el(36,66,-6,6,6);
                set_dmg_phy_he_co_po_el(36,26, 0, 0, 0);
                this.enemy_hp = 166;
                this.enemy_name = "Fire Demon";
                break;

            case 51:
                set_drop_spawn_xp(100, 15, 500);
                set_ap_e_d_m_c(5,2,80,50,50);
                set_resis_a_he_co_po_el(25,25,25,25,50);
                set_dmg_phy_he_co_po_el(10,10, 10, 10, 10);
                this.enemy_hp = rand.nextInt(200 - 100 + 1) + 100;
                this.enemy_name = "Crazy Adventure";
                break;

            case 52:
                set_drop_spawn_xp(40, 50, 300);
                set_ap_e_d_m_c(20,8,60,23,30);
                set_resis_a_he_co_po_el(25,0,100,25,20);
                set_dmg_phy_he_co_po_el(35,23, 0, 0, 0);
                this.enemy_hp = rand.nextInt(190 - 160 + 1) + 160;
                this.enemy_name = "Ghost Warrior";
                break;

            case 53:
                set_drop_spawn_xp(100, 25, 350);
                set_ap_e_d_m_c(5,18,40,20,20);
                set_resis_a_he_co_po_el(25,25,25,25,50);
                set_dmg_phy_he_co_po_el(10,10, 10, 10, 10);
                this.enemy_hp = rand.nextInt(235 - 210 + 1) + 210;
                this.enemy_name = "Abomination of Caves";
                break;
            case 54:
                set_drop_spawn_xp(100, 25, 400);
                set_ap_e_d_m_c(10,8,0,18,5);
                set_resis_a_he_co_po_el(36,100,0,0,0);
                set_dmg_phy_he_co_po_el(30,32, 0, 0, 0);
                this.enemy_hp = 300;
                this.enemy_name = "Ancient Dragon";
                break;
            case 55:
                set_drop_spawn_xp(66, 99, 266);
                set_ap_e_d_m_c(66,6,36,30,66);
                set_resis_a_he_co_po_el(20,50,0,-35,0);
                set_dmg_phy_he_co_po_el(36,15, 0, 0, 0);
                this.enemy_hp = rand.nextInt(179 - 130+ 1) + 130;
                this.enemy_name = "Cave Demon";
                break;
            case 56:
                set_drop_spawn_xp(99, 100, 320);
                set_ap_e_d_m_c(30,5,0,20,15);
                set_resis_a_he_co_po_el(-35,30,5,-15,20);
                set_dmg_phy_he_co_po_el(27,0, 0, 0, 0);
                this.enemy_hp = rand.nextInt(510 - 450 + 1) + 450;
                this.enemy_name = "Cave Worm";
                break;
            case 57:
                set_drop_spawn_xp(80, 60, 350);
                set_ap_e_d_m_c(40,8,30,14,25);
                set_resis_a_he_co_po_el(25,40,5,-5,0);
                set_dmg_phy_he_co_po_el(22,28, 0, 14, 0);
                this.enemy_hp = rand.nextInt(210 - 250 + 1) + 250;
                this.enemy_name = "Cerberus";
                break;
            case 58:
                set_drop_spawn_xp(60, 30, 303);
                set_ap_e_d_m_c(35,8,30,14,45);
                set_resis_a_he_co_po_el(30,0,-25,0,-30);
                set_dmg_phy_he_co_po_el(44,12, 0, 12, 0);
                this.enemy_hp = rand.nextInt(199 - 190 + 1) + 199;
                this.enemy_name = "Demoniac Hyena";
                break;
            case 59:
                set_drop_spawn_xp(40, 30, 314);
                set_ap_e_d_m_c(0,7,29,19,50);
                set_resis_a_he_co_po_el(14,-15,5,5,5);
                set_dmg_phy_he_co_po_el(0,0, 0, 52, 0);
                this.enemy_hp = rand.nextInt(179 - 167 + 1) + 167;
                this.enemy_name = "Cholera Spectrum";
                break;
            case 60:
                set_drop_spawn_xp(0, 100, 250);
                set_ap_e_d_m_c(0,5,12,6,10);
                set_resis_a_he_co_po_el(11,-15,-5,60,-5);
                set_dmg_phy_he_co_po_el(0,0, 0, 21, 0);
                this.enemy_hp = rand.nextInt(229 - 190 + 1) + 190;
                this.enemy_name = "Venomous Spawn";
                break;
            case 61:
                set_drop_spawn_xp(90, 50, 385);
                set_ap_e_d_m_c(20,8,21,28,10);
                set_resis_a_he_co_po_el(14,-5,-5,100,-5);
                set_dmg_phy_he_co_po_el(18,0, 0, 39, 0);
                this.enemy_hp = rand.nextInt(259 - 244 + 1) + 244;
                this.enemy_name = "Swamp Queen";
                break;
            case 62:
                set_drop_spawn_xp(90, 50, 340);
                set_ap_e_d_m_c(29,8,40,28,23);
                set_resis_a_he_co_po_el(14,-5,0,0,0);
                set_dmg_phy_he_co_po_el(33,0, 0, 19, 0);
                this.enemy_hp = rand.nextInt(189 - 184 + 1) + 184;
                this.enemy_name = "Queen Spider";
                break;
            case 63:
                set_drop_spawn_xp(90, 50, 380);
                set_ap_e_d_m_c(31,7,1,22,15);
                set_resis_a_he_co_po_el(42,0,50,20,20);
                set_dmg_phy_he_co_po_el(31,0, 40, 0, 0);
                this.enemy_hp = rand.nextInt(269 - 250 + 1) + 250;
                this.enemy_name = "Ice Dragon";
                break;
            case 64:
                set_drop_spawn_xp(100, 25, 290);
                set_ap_e_d_m_c(35,9,90,42,70);
                set_resis_a_he_co_po_el(42,40,80,100,60);
                set_dmg_phy_he_co_po_el(48,0, 40, 0, 0);
                this.enemy_hp = rand.nextInt(100 - 90 + 1) + 90;
                this.enemy_name = "Ghost Warrior";
                break;
            case 65:
                set_drop_spawn_xp(80, 25, 317);
                set_ap_e_d_m_c(80,10,5,15,40);
                set_resis_a_he_co_po_el(36,30,10,30,100);
                set_dmg_phy_he_co_po_el(18,0, 0, 0, 60);
                this.enemy_hp = rand.nextInt(170 - 135 + 1) + 135;
                this.enemy_name = "Thunderbull";
                break;
            case 66:
                set_drop_spawn_xp(100, 5, 800);
                set_ap_e_d_m_c(0,4,0,18,0);
                set_resis_a_he_co_po_el(70,90,90,70,90);
                set_dmg_phy_he_co_po_el(26,5, 17, 0, 12);
                this.enemy_hp = 100;
                this.enemy_name = "Crystal Monster";
                break;
            case 67:
                set_drop_spawn_xp(60, 65, 210);
                set_ap_e_d_m_c(10,7,15,21,20);
                set_resis_a_he_co_po_el(69,90,-30,0,0);
                set_dmg_phy_he_co_po_el(15,55, 0, 0, 0);
                this.enemy_hp = rand.nextInt(131 - 100 + 1) + 100;
                this.enemy_name = "Lava Demon";
                break;
            case 68:
                set_drop_spawn_xp(100, 55, 352);
                set_ap_e_d_m_c(0,10,55,20, 0);
                set_resis_a_he_co_po_el(9,20,20,20,20);
                set_dmg_phy_he_co_po_el(0,26, 13, 0, 18);
                this.enemy_hp = rand.nextInt(171 - 150 + 1) + 150;
                this.enemy_name = "Demon Sorcerer";
                break;
            case 69:
                set_drop_spawn_xp(99, 100, 405);
                set_ap_e_d_m_c(90,6,29,24, 20);
                set_resis_a_he_co_po_el(39,25,25,25,15);
                set_dmg_phy_he_co_po_el(49,0, 0, 0, 0 );
                this.enemy_hp = 250;
                this.enemy_name = "Swamp Predator";
                break;
            case 70:
                set_drop_spawn_xp(100, 55, 280);
                set_ap_e_d_m_c(0,1,50,30, 10);
                set_resis_a_he_co_po_el(9,-500,0,0,0);
                set_dmg_phy_he_co_po_el(0,100, 0, 0, 0);
                this.enemy_hp = rand.nextInt(211 - 210 + 1) + 210;
                this.enemy_name = "Demon of the Flames";
                break;
            case 71:
                set_drop_spawn_xp(80, 100, 295);
                set_ap_e_d_m_c(25,10,80,25, 20);
                set_resis_a_he_co_po_el(26,0,-5,-15,-10);
                set_dmg_phy_he_co_po_el(29,0, 0, 0, 0);
                this.enemy_hp = rand.nextInt(261 - 240 + 1) + 240;
                this.enemy_name = "Deep Stalker";
                break;
            case 72:
                set_drop_spawn_xp(50, 90, 225);
                set_ap_e_d_m_c(25,8,35,22,32);
                set_resis_a_he_co_po_el(20,20,10, 15,20);
                set_dmg_phy_he_co_po_el(44,0,0,0,0);
                this.enemy_hp = rand.nextInt(168 - 165 + 1) + 165;
                this.enemy_name = "Hellhound";
            case 73:
                set_drop_spawn_xp(10, 100, 190);
                set_ap_e_d_m_c(25,8,35,22,32);
                set_resis_a_he_co_po_el(44,0,0, 0,60);
                set_dmg_phy_he_co_po_el(38,0,0,0,5);
                this.enemy_hp = rand.nextInt(168 - 165 + 1) + 165;
                this.enemy_name = "Volt Boar";
        }

        // this.enemy_name = "Dungeon's Guardian(BOSS)"; #not used yet
    }

    public void enemy_evolve(){
        int evolve_lvl = this.enemy_lvl;
        if (this.enemy_id >= 50){
            evolve_lvl -= 20; //the base damage is lvl 20 (strong enemies)
        }else if(this.enemy_id >= 20){
            evolve_lvl -= 10; //tha base damage is lvl 10
        }
        this.xp_value = (int) (this.xp_value + Math.round(this.xp_value*0.15*evolve_lvl) + 5); // 15 % + 5
        this.enemy_hp = (int) (this.enemy_hp + Math.round(this.enemy_hp*0.1*evolve_lvl));
        int random_mutation_evolve = rand.nextInt(14);
        switch (random_mutation_evolve){
            case 0:
                this.enemy_armor_penetration += evolve_lvl;
                break;
            case 1:
                this.enemy_armor += evolve_lvl;
                break;
            case 2:
                this.enemy_resis_cold += 2*evolve_lvl;
                break;
            case 3:
                this.enemy_resis_eletric += 2*evolve_lvl;
                break;
            case 4:
                this.enemy_resis_heat += 2*evolve_lvl;
                break;
            case 5:
                this.enemy_resis_poison += 2*evolve_lvl;
                break;
            case 6:
                this.enemy_dmg_cold += 2*evolve_lvl;
                break;
            case 7:
                this.enemy_dmg_electric += 2*evolve_lvl;
                break;
            case 8:
                this.enemy_dmg_poison += 2*evolve_lvl;
                break;
            case 9:
                this.enemy_dmg_heat += 2*evolve_lvl;
                break;
            case 10:
                this.enemy_dodge += 2*evolve_lvl;
                break;
            case 11:
                this.enemy_critical += 2*evolve_lvl;
                break;
            case 12:
                this.enemy_mind += evolve_lvl;
                break;
            case 13:
                this.enemy_energy += Math.round(evolve_lvl/2);
                break;
        }

    }

    public void set_dmg_phy_he_co_po_el(int phy, int he, int co, int po, int el){
        this.enemy_dmg_phy = phy;
        this.enemy_dmg_heat = he;
        this.enemy_dmg_cold = co;
        this.enemy_dmg_poison = po;
        this.enemy_dmg_electric = el;

        phy = (phy > 0)? 0:-1;  // if phy>0 phy = 0 else -1
        he = (he>0)? 1:-1;
        co = (co>0)? 2:-1;
        po = (po>0)? 3:-1;
        el = (el>0)? 4:-1;
        Integer[]arr = {phy, he, co, po, el};
        Arrays.sort(arr, Collections.reverseOrder());
        set_enemy_dmg_types(arr);
    }

    public void set_resis_a_he_co_po_el(int a, int he, int co, int po, int el) {
        this.enemy_armor = a;
        this.enemy_resis_heat = he;
        this.enemy_resis_cold = co;
        this.enemy_resis_poison = po;
        this.enemy_resis_eletric = el;
    }

    public void set_drop_spawn_xp(int dc, int spawn, int xp){
        this.drop_chance = dc;
        this.xp_value = xp;
        this.spawn_chance = spawn;
    }

    public void set_ap_e_d_m_c(int ap, int e, int d, int m, int c){
        this.enemy_armor_penetration = ap;
        this.enemy_energy_max = e;
        this.enemy_dodge = d;
        this.enemy_mind = m;
        this.enemy_critical = c;
    }

    public void set_enemy_dmg_types(Integer [] enemy_dmg_types){
        for (int i=0; i<5; i++){
            this.enemy_dmg_types_active[i] = enemy_dmg_types[i];
        }
    }

    public int [] get_enemy_dmg_types(){
        return this.enemy_dmg_types_active;
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
        pla_armor = pla_armor - Math.round((pla_armor*this.enemy_armor_penetration)/100);
        dmg = Math.round(dmg*(100-pla_armor)/100);
        if(dmg < 0){
            dmg = 0;
        }
        return dmg;
    }

    public int enemy_att_heat(int pla_heat){
        int min, dmg;
        min = (int) (Math.round(this.enemy_dmg_heat * 0.8));
        dmg = rand.nextInt(this.enemy_dmg_heat - min + 1) + min;
        dmg = Math.round(dmg*(100-pla_heat)/100);
        if(dmg < 0){
            dmg = 0;
        }
        return dmg;
    }
    public int enemy_att_cold(int play_resist){
        int min, dmg;
        min = (int) (Math.round(this.enemy_dmg_cold * 0.8));
        dmg = rand.nextInt(this.enemy_dmg_cold - min + 1) + min;
        dmg = Math.round(dmg*(100-play_resist)/100);
        if(dmg < 0){
            dmg = 0;
        }
        return dmg;
    }
    public int enemy_att_eletric(int play_resist){
        int min, dmg;
        min = (int) (Math.round(this.enemy_dmg_electric * 0.8));
        dmg = rand.nextInt(this.enemy_dmg_electric - min + 1) + min;
        dmg = Math.round(dmg*(100-play_resist)/100);
        if(dmg < 0){
            dmg = 0;
        }
        return dmg;
    }
    public int enemy_att_poison(int play_resist){
        int min, dmg;
        min = (int) (Math.round(this.enemy_dmg_poison * 0.8));
        dmg = rand.nextInt(this.enemy_dmg_poison - min + 1) + min;
        dmg = Math.round(dmg*(100-play_resist)/100);
        if(dmg < 0){
            dmg = 0;
        }
        return dmg;
    }

    public boolean enemy_att_dodge(int play_dodge){
        int lucky = rand.nextInt(100);
        if (lucky < play_dodge){
            return true;
        }else{
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
        if( (this.enemy_energy) == 0){
            dmg = (int)(Math.round(dmg/2));
            return dmg;
        }else{
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
        if(this.enemy_energy < 2){ //estado de exaustão
            return 0;
        }else{
            return enemy_dodge;
        }
    }

    public int getEnemy_class() {
        return enemy_class;
    }

    public void setEnemy_class(int enemy_class) {
        this.enemy_class = enemy_class;
    }

    public int getEnemy_armor_penetration() {
        return enemy_armor_penetration;
    }

    public int getEnemy_energy_max() {
        return enemy_energy_max;
    }

    public void setEnemy_energy(int enemy_energy) {
        this.enemy_energy = enemy_energy;
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

    public int getEnemy_mind() {
        return enemy_mind;
    }

    public int getSpawn_chance() {
        return spawn_chance;
    }
}