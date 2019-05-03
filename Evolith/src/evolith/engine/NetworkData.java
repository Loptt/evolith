/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package evolith.engine;

import evolith.entities.*;
import evolith.helpers.Commons;

/**
 *
 * @author charles
 */
public class NetworkData implements Commons {
    private byte[] data;
    private int byteAmount;
    private OrganismManager orgs;
    
    public NetworkData(OrganismManager orgs) {
        this.orgs = orgs;
        byteAmount = orgs.getAmount() * ORG_DATA_SIZE + 1;
        
        constructData();
    }
    
    private void constructData() {
        data = new byte[byteAmount];
        
        for (int i = 0; i < byteAmount; i++) {
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
            
            //Extra info
            data[index++] = addExtraInfo(org);
        }
    }
    
    private byte convertMutations(MutationManager muts) {
        byte result = 0;
        int tier;
        
        // 00 00 00 00
        
        //Strength
        tier = muts.getStrengthTier() << 6;
        result = (byte) (result | tier);
        
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
    
    private byte addExtraInfo(Organism org) {
        byte result = 0; 
        
        if (org.isEgg()) {
            result = (byte) (result | 128);
        }
        
        if (org.isDead()) {
            result = (byte) (result | 64);
        }
        
        return result;
    }
    
    private byte[] getData() {
        return data;
    }

}
