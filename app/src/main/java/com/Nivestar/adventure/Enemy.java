package com.Nivestar.adventure;

import java.util.Random;

public class Enemy {

    private int enemy_hp;
    private int enemy_at_max;
    private int enemy_at_min;
    private String enemy_name;
    private int drop_chance;
    private int xp_value;
    private int enemy_lvl;
    static Random rand = new Random();

    public Enemy(int player_rank){
        int max = -1;
        int min = -1;
        int r = rand.nextInt(100);
        int enemy_id = 0;

        switch(player_rank){  //random enemy rank dificulty with based in player nivel

            case 1:
                if(r<1){
                    enemy_id = 3;
                }else if(r<20){
                    enemy_id = 2;
                }else{
                    enemy_id = 1;
                }
                break;
            case 2:
                if(r<5){
                    enemy_id = 4;
                }else if(r<20){
                    enemy_id = 3;
                }else if(r>=70){
                    enemy_id = 1;
                }else{
                    enemy_id = 2;
                }
                break;
            case 3:
                if(r<5){
                    enemy_id = 5;
                }else if(r<20){
                    enemy_id = 4;
                }else if(r>=90){
                    enemy_id = 1;
                }else if(r>=70){
                    enemy_id = 2;
                }
                else{
                    enemy_id = 3;
                }
                break;
            case 4:
                if(r<15){
                    enemy_id = 5;
                }else if(r>=95){
                    enemy_id = 1;
                }else if(r>75){
                    enemy_id = 2;
                }
                else if(r>50){
                    enemy_id = 3;
                }
                else{
                    enemy_id = 4;
                }
                break;
            case 5:
                if(r>=99){
                    enemy_id = 1;
                }
                else if(r>=90){
                    enemy_id = 2;
                }else if(r>=65){
                    enemy_id = 3;
                }
                else if(r>=30){
                    enemy_id = 4;
                }
                else{
                    enemy_id = 5;
                }
                break;

            default:
                System.out.println("erro!!");
        }
        r = rand.nextInt(100);
        switch(enemy_id){
            case 1:
                this.enemy_lvl = 1;
                if(r<25){
                    this.enemy_at_max = 8;
                    this.enemy_at_min = 8;
                    max = 19;
                    min = 11;
                    this.drop_chance = 50;
                    this.xp_value = 5;
                    this.enemy_name = "Goblin";
                }else if( r<45){
                    this.enemy_at_max = 10;
                    this.enemy_at_min = 5;
                    max = 25;
                    min = 9;
                    this.drop_chance = 10;
                    this.xp_value = 10;
                    this.enemy_name = "Zombie";
                }else if(r < 65){
                    this.enemy_at_max = 15;
                    this.enemy_at_min = 7;
                    max = 20;
                    min = 10;
                    this.drop_chance = 10;
                    this.xp_value = 9;
                    this.enemy_name = "Giant Spider";
                }else if(r<85){
                    this.enemy_at_max = 18;
                    this.enemy_at_min = 10;
                    max = 9;
                    min = 5;
                    this.drop_chance = 0;
                    this.xp_value = 8;
                    this.enemy_name = "Skeleton";
                }else{
                    this.enemy_at_max = 13;
                    this.enemy_at_min = 0;
                    max = 39;
                    min = 30;
                    this.drop_chance = 0;
                    this.xp_value = 15;
                    this.enemy_name = "Baby Dragon";
                }
                break;
            case 2:
                this.enemy_lvl = 2;
                if(r<30){
                    this.enemy_at_max = 26;
                    this.enemy_at_min = 11;
                    max = 25;
                    min = 20;
                    this.drop_chance = 30;
                    this.xp_value = 20;
                    this.enemy_name = "Ghul";
                }else if(r<50){
                    this.enemy_at_max = 42;
                    this.enemy_at_min = 10;
                    max = 19;
                    min = 10;
                    this.drop_chance = 70;
                    this.xp_value = 25;
                    this.enemy_name = "Witch";
                }else if (r<75){
                    this.enemy_at_max = 22;
                    this.enemy_at_min = 18;
                    max = 45;
                    min = 37;
                    this.drop_chance = 10;
                    this.xp_value = 28;
                    this.enemy_name = "Rabid Bear";
                }else{
                    this.enemy_at_max = 28;
                    this.enemy_at_min = 15;
                    max = 42;
                    min = 35;
                    this.drop_chance = 40;
                    this.xp_value = 30;
                    this.enemy_name = "ORC";
                }
                break;
            case 3:
                this.enemy_lvl = 3;
                if(r<25){
                    this.enemy_at_max = 31;
                    this.enemy_at_min = 26;
                    max = 49;
                    min = 39;
                    this.drop_chance = 30;
                    this.xp_value = 40;
                    this.enemy_name = "Minotaur";
                }else if(r<45){
                    this.enemy_at_max = 49;
                    this.enemy_at_min = 19;
                    max = 30;
                    min = 30;
                    this.drop_chance = 45;
                    this.xp_value = 40;
                    this.enemy_name = "Deep Demon";
                }else if(r < 60){
                    this.enemy_at_max = 45;
                    this.enemy_at_min = -10;
                    max = 75;
                    min = 60;
                    this.drop_chance = 99;
                    this.xp_value = 50;
                    this.enemy_name = "Crazy Adventure";
                }else if(r<80){
                    this.enemy_at_max = 41;
                    this.enemy_at_min = 20;
                    max = 70;
                    min = 60;
                    this.drop_chance = 30;
                    this.xp_value = 60;
                    this.enemy_name = "Medium Dragon";
                }else{
                    this.enemy_at_max = 18;
                    this.enemy_at_min = 18;
                    max = 120;
                    min = 100;
                    this.drop_chance = 60;
                    this.xp_value = 45;
                    this.enemy_name = "Troll";
                }
                break;
            case 4:
                this.enemy_lvl =4;
                if(r<28){
                    this.enemy_at_max = 100;
                    this.enemy_at_min = 1;
                    max = 70;
                    min = 70;
                    this.drop_chance = 90;
                    this.xp_value = 80;
                    this.enemy_name = "Ghost Warrior";
                }else if(r<65){
                    this.enemy_at_max = 41;
                    this.enemy_at_min = 33;
                    max = 99;
                    min = 90;
                    this.drop_chance = 50;
                    this.xp_value = 100;
                    this.enemy_name = "Abomination of Caves";
                }else{
                    this.enemy_at_max = 54;
                    this.enemy_at_min = 32;
                    max = 130;
                    min = 100;
                    this.drop_chance = 95;
                    this.xp_value = 120;
                    this.enemy_name = "Ancient Dragon";
                    break;
                }
                break;
            case 5:
                this.enemy_lvl = 5;
                this.enemy_at_max = 69;
                this.enemy_at_min = 44;
                max = 300;
                min = 300;
                this.drop_chance = 99;
                this.xp_value = 99;
                this.enemy_name = "Dungeon's Guardian(BOSS)";

            default:
                System.err.println("ERRo! invalid enemy");
        }
        this.enemy_hp = rand.nextInt(max - min + 1) + min;
    }

    public int enemy_attacking(){
        int luck = rand.nextInt(100);
        if (luck>=90){
            return 0;
        }else{
            return rand.nextInt(this.enemy_at_max - this.enemy_at_min +1 ) + this.enemy_at_min;
        }
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

    //g and s

    public String getEnemy_name() {
        return this.enemy_name;
    }

    public int getEnemy_hp() {
        return this.enemy_hp;
    }

    public int getEnemy_at_max() {
        return this.enemy_at_max;
    }

    public int getXp_value() {
        return this.xp_value;
    }

    public int getEnemy_lvl() {
        return this.enemy_lvl;
    }
}