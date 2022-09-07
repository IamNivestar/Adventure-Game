package com.Nivestar.adventure;

import org.json.JSONArray;

import java.util.Random;

public class Player {

    //atributos

    private int player_constitution;
    private int player_strength;
    private int player_dexterity;
    private int player_vitality;
    private int player_cunning;
    private int player_magic;
    private int player_lucky;

    //status
    private int player_dmg_phy;
    private int player_hp_max;
    private int player_hp;
    private int player_armor_penetration;
    private int player_energy_max;
    private int player_energy;
    private int player_dodge;
    private int player_mind;
    private int player_critical;
    private int player_armor;
    private int player_resis_heat;
    private int player_resis_cold;
    private int player_resis_poison;
    private int player_resis_eletric;
    private int player_dmg_heat;
    private int player_dmg_cold;
    private int player_dmg_poison;
    private int player_dmg_electric;

    private String player_name;
    private String player_class;
    private int player_potion;
    private int player_xp;
    private int player_lvl;
    private int necessary_xp;

    private int [] player_dmg_types_active = new int[5];

    static Random rand = new Random();

    public Player(String name, int player_id_class){

        switch (player_id_class){
            case 1:
                this.player_class = "Warrior";
                this.player_constitution = 13;
                this.player_strength = 16;
                this.player_dexterity = 13;
                this.player_cunning = 10;
                this.player_vitality = 12;
                this.player_magic = 10;
                this.player_lucky = 10;
                break;
            case 2:
                this.player_class = "Wizard";

                this.player_constitution = 10;
                this.player_strength = 10;
                this.player_dexterity = 10;
                this.player_cunning = 11;
                this.player_vitality = 15;
                this.player_magic = 17;
                this.player_lucky = 11;
                break;
            case 3:
                this.player_class = "Rogue";

                this.player_constitution = 10;
                this.player_strength = 10;
                this.player_dexterity = 15;
                this.player_cunning = 14;
                this.player_vitality = 12;
                this.player_magic = 10;
                this.player_lucky = 13;
                break;
            case 4:
                this.player_class = "Tank";

                this.player_constitution = 17;
                this.player_strength = 14;
                this.player_dexterity = 12;
                this.player_cunning = 10;
                this.player_vitality = 10;
                this.player_magic = 10;
                this.player_lucky = 11;
                break;
            case 5:
                this.player_class = "None";

                this.player_constitution = 12;
                this.player_strength = 12;
                this.player_dexterity = 12;
                this.player_cunning = 12;
                this.player_vitality = 12;
                this.player_magic = 12;
                this.player_lucky = 12;
                break;
            default:
                System.err.println("Error Class ID");
                System.exit(0);
        }

        generate_status( );
        this.player_critical = 0;
        this.player_potion = 2;
        this.necessary_xp = 5;
        this.player_name = name;
        this.player_lvl = 1;
        this.player_xp = 0;
        this.player_energy = this.player_energy_max;
        this.player_hp= this.player_hp_max;
    }

    public int get_id_by_class(){
        switch (this.player_class){
            case "Warrior":
                return 1;
            case "Wizard":
                return 2;
            case "Rogue":
                return 3;
            case "Tank":
                return 4;
            case "None":
                return 5;
            default:
                System.err.println("Error Class ID");
                System.exit(0);
                return -1;
        }
    }

    public void generate_status(){
        // depende do inventory
        boolean var_str_dmg = true; //por enquanto ele so ataca usando mao crua**
        //(int)(Math.round(0.5*this.player_strength))
        this.player_armor = 0;
        if(var_str_dmg){
            this.player_dmg_phy= 2*this.player_dexterity + 2*this.player_strength - 30;
        }else{
            this.player_dmg_phy= 2*this.player_dexterity - 10;
        }
        if(this.player_dmg_phy < 0) this.player_dmg_phy = 0;

        this.player_hp_max = 10 * this.player_constitution + 5 * this.player_vitality - 30;
        this.player_armor_penetration = this.player_cunning -10;
        this.player_energy_max = this.player_vitality - 6;
        this.player_dodge = this.player_lucky + 2*this.player_cunning -20;
        this.player_mind = this.player_lucky + 2*this.player_cunning -20;
        this.player_critical = this.player_lucky + 2*this.player_cunning -20;
        this.player_resis_heat = this.player_constitution -10;
        this.player_resis_cold = this.player_constitution -10;
        this.player_resis_poison = this.player_constitution -10;
        this.player_resis_eletric = this.player_constitution -10;

        if(this.player_magic > 10){
            this.player_dmg_heat = this.player_magic - 10; //adicionar verificador de valor nulo ao ler esse numero
            this.player_dmg_cold = this.player_magic - 10;
            this.player_dmg_poison = this.player_magic - 10;
            this.player_dmg_electric = this.player_magic - 10;
        }else{
            this.player_dmg_heat = 0; //adicionar verificador de valor nulo ao ler esse numero***
            this.player_dmg_cold = 0;
            this.player_dmg_poison = 0;
            this.player_dmg_electric = 0;
        }
    }

    public void load_player(int[] attributes){

        this.player_hp= attributes[0];
        this.player_xp = attributes[1];
        this.necessary_xp =attributes[2];
        this.player_potion = attributes[3];
        this.player_lvl = attributes[4];
        this.player_constitution = attributes[5];
        this.player_cunning = attributes[6];
        this.player_strength =attributes[7];
        this.player_vitality = attributes[8];
        this.player_dexterity =attributes[9];
        this.player_magic = attributes[10];
        this.player_lucky = attributes[11];
        this.player_energy = attributes[12];
        generate_status();
    }

    public JSONArray get_load_attributes(){
        JSONArray attributes = new JSONArray();
        attributes.put(this.player_hp);
        attributes.put(this.player_xp);
        attributes.put(this.necessary_xp);
        attributes.put(this.player_potion);
        attributes.put(this.player_lvl);
        attributes.put(this.player_constitution);
        attributes.put(this.player_cunning);
        attributes.put(this.player_strength);
        attributes.put(this.player_vitality);
        attributes.put(this.player_dexterity);
        attributes.put(this.player_magic);
        attributes.put(this.player_lucky);
        attributes.put(this.player_energy);
        return attributes;
    }

    //just visual
    public void set_player_dmg_types(int [] player_dmg_types){
        for (int i=0; i<5; i++){
            this.player_dmg_types_active[i] = player_dmg_types[i];
        }
    }

    public int [] get_player_dmg_types(){
        return this.player_dmg_types_active;
    }

    public boolean add_xp(int count){
        this.player_xp += count;
        if(this.player_xp >= this.necessary_xp) {
            this.player_xp = 0;
            this.player_lvl++;
            this.necessary_xp = (int)(Math.round(this.necessary_xp * 1.2)) + 10; //20% + const de aumento de xp
            return true;
        }
        else{
            return false;
        }
    }

    public void player_levelup(int atribute) {
        switch (atribute){
            case 1:
                this.player_constitution +=1;
                break;
            case 2:
                this.player_strength +=1;
                break;
            case 3:
                this.player_dexterity +=1;
                break;
            case 4:
                this.player_vitality +=1;
                break;
            case 5:
                this.player_cunning +=1;
                break;
            case 6:
                this.player_magic +=1;
                break;
            case 7:
                this.player_lucky +=1;
                break;
        }
        generate_status();
        this.player_hp = this.player_hp_max;
    }

    public int player_exhaustion(int dmg){
        if( (this.player_energy) == 0){
            dmg = (int)(Math.round(dmg/2));
            return dmg;
        }else{
            return dmg;
        }
    }

    public boolean player_att_dodge(int ene_dodge){
        int lucky = rand.nextInt(100);
        if(lucky < ene_dodge)
            return true;
        else {
            return false;
        }
    }

    public int player_att_phy(int ene_armor){
        int min, dmg;
        min = (int) (Math.round(this.player_dmg_phy * 0.8));
        dmg = rand.nextInt(this.player_dmg_phy - min + 1) + min;
        ene_armor = ene_armor - this.player_armor_penetration;
        if(ene_armor < 0)
            ene_armor = 0;
        dmg = Math.round(dmg*(100-ene_armor)/100);
        if(dmg < 0){
            dmg = 0;
        }
        return dmg;
    }
    public int player_att_heat(int ene_resis){
        int min, dmg;
        min = (int) (Math.round(this.player_dmg_heat * 0.8));
        dmg = rand.nextInt(this.player_dmg_heat - min + 1) + min;
        dmg = Math.round(dmg*(100-ene_resis)/100);
        if(dmg < 0){
            dmg = 0;
        }
        return dmg;
    }
    public int player_att_cold(int ene_resis){
        int min, dmg;
        min = (int) (Math.round(this.player_dmg_cold * 0.8));
        dmg = rand.nextInt(this.player_dmg_cold - min + 1) + min;
        dmg = Math.round(dmg*(100-ene_resis)/100);
        if(dmg < 0){
            dmg = 0;
        }
        return dmg;
    }
    public int player_att_eletric(int ene_resis){
        int min, dmg;
        min = (int) (Math.round(this.player_dmg_electric * 0.8));
        dmg = rand.nextInt(this.player_dmg_electric - min + 1) + min;
        dmg = Math.round(dmg*(100-ene_resis)/100);
        if(dmg < 0){
            dmg = 0;
        }
        return dmg;
    }
    public int player_att_poison(int ene_resis){
        int min, dmg;
        min = (int) (Math.round(this.player_dmg_poison * 0.8));
        dmg = rand.nextInt(this.player_dmg_poison - min + 1) + min;
        dmg = Math.round(dmg*(100-ene_resis)/100);
        if(dmg < 0){
            dmg = 0;
        }
        return dmg;
    }

    public boolean critic(){
        int luck = rand.nextInt(100);
        if( luck > (100-this.player_critical)) {
            return true;
        }else{
            return false;
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

    public void player_recover_energy(){
        this.player_energy = this.player_energy_max;
    }

    //g and s
    public int getPlayer_dodge() {
        if(this.player_energy < 2){
            return 0;
        }else{
            return player_dodge;
        }
    }

    public int getPlayer_armor() {
        return player_armor;
    }

    public int getPlayer_resis_heat() {
        return player_resis_heat;
    }

    public int getPlayer_resis_cold() {
        return player_resis_cold;
    }

    public int getPlayer_resis_poison() {
        return player_resis_poison;
    }

    public int getPlayer_resis_eletric() {
        return player_resis_eletric;
    }

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

    public void setPlayer_dmg_phy(int x) {
        this.player_dmg_phy = x;
    }

    public int getPlayer_dmg_phy() {
        return player_dmg_phy;
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

    public int getPlayer_critical() {
        return player_critical;
    }

    public int getPlayer_constitution() {
        return player_constitution;
    }

    public int getPlayer_strength() {
        return player_strength;
    }

    public int getPlayer_dexterity() {
        return player_dexterity;
    }

    public int getPlayer_cunning() {
        return player_cunning;
    }

    public int getPlayer_vitality() {
        return player_vitality;
    }

    public int getPlayer_magic() {
        return player_magic;
    }

    public int getPlayer_lucky() {
        return player_lucky;
    }

    public int getPlayer_energy() {
        return player_energy;
    }

    public int getPlayer_armor_penetration() {
        return player_armor_penetration;
    }

    public int getPlayer_energy_max() {
        return player_energy_max;
    }

    public int getPlayer_mind() {
        return player_mind;
    }

    public int getPlayer_dmg_heat() {
        return player_dmg_heat;
    }

    public int getPlayer_dmg_cold() {
        return player_dmg_cold;
    }

    public int getPlayer_dmg_poison() {
        return player_dmg_poison;
    }

    public int getPlayer_dmg_electric() {
        return player_dmg_electric;
    }

    public void setPlayer_energy(int x) {
        this.player_energy = x;
    }

    public void setPlayer_hp_max(int player_hp_max) {
        this.player_hp_max = player_hp_max;
    }

    public void setPlayer_critical(int player_critical) {
        this.player_critical = player_critical;
    }

}