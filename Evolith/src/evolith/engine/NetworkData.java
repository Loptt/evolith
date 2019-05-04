/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package evolith.engine;

import evolith.entities.*;
import evolith.helpers.Commons;
import java.awt.Point;

/**
 *
 * @author charles
 */
public class NetworkData implements Commons {
    private static int constructedByteAmount;
    
    public static byte[] constructData(OrganismManager orgs) {
        byte[] data;
        constructedByteAmount = orgs.getAmount() * ORG_DATA_SIZE + 1;
        data = new byte[constructedByteAmount];
        
        for (int i = 0; i < constructedByteAmount; i++) {
            data[i] = 0;
        }
        
        data[0] = (byte) orgs.getAmount();
        
        int index = 1;
        
        for (int i = 0; i < orgs.getAmount(); i++) {
            Organism org = orgs.getOrganism(i);
            
            //Get x coordinate
            data[index++] = (byte) (org.getX() / 256);
            data[index++] = (byte) (org.getX());
            
            //Get y coordinate
            data[index++] = (byte) (org.getY() / 256);
            data[index++] = (byte) (org.getY());
            
            //Get vitals
            data[index++] = (byte) ((int) (org.getLife()));
            data[index++] = (byte) (org.getHunger());
            data[index++] = (byte) (org.getThirst());
            
            //Get mutations
            data[index++] = convertMutations(org.getOrgMutations());
            data[index++] = addExtraInfo(org, org.getOrgMutations());
            
            //Extra info
        }
        
        return data;
    }
    
    public static void parseBytes(OrganismManager orgs, byte[] data) {
        int index = 1;
        int x;
        int y;
        
        double life;
        int hunger;
        int thirst;
        
        int addAmount = (int) (data[0] & 0xff) - orgs.getAmount();
        
        for (int i = 0; i < orgs.getAmount(); i++) {
            Organism org = orgs.getOrganism(i);
            
            x = data[index++] * 256 + unsignByte(data[index++]);
            y = data[index++] * 256 + unsignByte(data[index++]);

            org.setPoint(new Point(x, y));
            
            life = (double) ((int) (data[index++]));
            hunger = data[index++];
            thirst = data[index++];
            
            parseMutations(org.getOrgMutations(), data, index++);
            getExtraInfo(org, orgs, data, index++);
        }
        
        for (int i = 0; i < addAmount; i++) {
            
            x = data[index++] * 256 + unsignByte(data[index++]);
            y = data[index++] * 256 + unsignByte(data[index++]);
            Organism org = new Organism(x, y, ORGANISM_SIZE_STAT, ORGANISM_SIZE_STAT, orgs.getGame(), orgs.getSkin(), orgs.getIdCounter(), true);
            org.setPoint(new Point(x, y));
            
            life = (double) ((int) (data[index++]));
            hunger = data[index++];
            thirst = data[index++];
            
            parseMutations(org.getOrgMutations(), data, index++);
            getExtraInfo(org, orgs, data, index++);
            org.setEgg(true);
            org.setBorn(false);
            
            orgs.addOrganism(org);
        }
    }
    
    private static byte convertMutations(MutationManager muts) {
        byte result = 0;
        int tier;
        
        // 00 00 00 00
        
        //Strength
        tier = muts.getStrengthTier() << 6;
        result = (byte) (result | tier);
        
        System.out.println("RES TO SEND: " + result);
        
        //Speed
        tier = muts.getSpeedTier() << 4;
        result = (byte) (result | tier);
        
        //Speed
        tier = muts.getHealthTier() << 2;
        result = (byte) (result | tier);
        
        //Stealth
        tier = muts.getStealthTier();
        result = (byte) (result | tier);
        
        return result;
    }
    
    private static void parseMutations(MutationManager muts, byte[] data, int index) {
        int tier;
        
        // 01 01 01 01
        
        //Strength
        tier = ((data[index] >> 6) & 0x3);
        System.out.println("RES RECEIVED:  " + unsignByte(data[index]));
        System.out.println("TIER TO APPLY:  " + tier);
        muts.setStrengthTier(tier);
        
        //Speed
        tier = (data[index] >> 4) & 0x3;
        muts.setSpeedTier(tier);
        
        //Health
        tier = (data[index] >> 2) & 0x3;
        muts.setHealthTier(tier);
        
        //Stealth
        tier = (data[index]) & 0x3;
        muts.setStealthTier(tier);
    }
    
    private static byte addExtraInfo(Organism org, MutationManager muts) {
        byte result = 0; 
        
        if (org.isEgg()) {
            result = (byte) (result | 128);
        }
        
        if (org.isDead()) {
            result = (byte) (result | 64);
        }
        
        if (muts.getStrengthTier() > 3) {
            result = (byte) (result | 8);
        }
        
        if (muts.getSpeedTier() > 3) {
            result = (byte) (result | 4);
        }
        
        if (muts.getHealthTier() > 2) {
            result = (byte) (result | 2);
        }
        
        if (muts.getStealthTier() > 1) {
            result = (byte) (result | 1);
        }
        
        return result;
    }
    
    private static void getExtraInfo(Organism org, OrganismManager orgs, byte[] data, int index) {
        if ((unsignByte(data[index]) & 128) == 128) {
            org.setEgg(true);
        } else {
            org.setEgg(false);
        }
        
        if ((data[index] & 64) == 64) {
            org.setDead(true);
        } else {
            org.setDead(false);
        }
        
        if ((data[index] & 8) == 8) {
            org.getOrgMutations().setStrengthTier(4);
        }
        
        if ((data[index] & 4) == 4) {
            org.getOrgMutations().setSpeedTier(4);
        }
        
        if ((data[index] & 2) == 2) {
            org.getOrgMutations().setHealthTier(3);
        }
        
        if ((data[index] & 1) == 1) {
            org.getOrgMutations().setStealthTier(2);
        }
        
        org.updateMutations();
    }
    
    public static int getConstructedByteAmount() {
        return constructedByteAmount;
    }
    
    public static int unsignByte(byte b) {
        return (int) (b & 0xff);
    }
}
