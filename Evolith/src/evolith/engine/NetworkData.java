/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package evolith.engine;

import evolith.entities.*;
import evolith.game.Game;
import evolith.game.Weather;
import evolith.helpers.Commons;
import java.awt.Point;
import java.util.ArrayList;

/**
 *
 * @author Erick González
 * @author Carlos Estrada
 * @author Víctor Villarreal
 * @author Moisés Fernández
 */
public class NetworkData implements Commons {
    private static int constructedByteAmount;
    
    public static byte[] constructData(OrganismManager orgs) {
        byte[] data;
        constructedByteAmount = orgs.getAmount() * ORG_DATA_SIZE + 3;
        data = new byte[constructedByteAmount];
        
        for (int i = 0; i < constructedByteAmount; i++) {
            data[i] = 0;
        }
        
        //Type organisms
        data[0] = (byte) 1;
        data[1] = (byte) orgs.getAmount();
        
        //Game state
        data[2] = constructGameState(orgs.getGame());
        
        int index = 3;
        
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
            data[index++] = addExtraInfo(org, org.getOrgMutations());
            
        }
        
        return data;
    }
    
    /**
     * To construct the plants with their respective data
     * 
     * @param res
     * @return data
     */
    public static byte[] constructDataPlants(ResourceManager res) {
        byte[] data;
        int amountPlant = 0;
        
        for (int i = 0; i < res.getPlantAmount(); i++) {
            if (res.getPlant(i).isUpdate() || res.getPlant(i).isAdd()) {
                amountPlant++;
            }
        }

        constructedByteAmount = (amountPlant) * RES_DATA_SIZE + 3;
        data = new byte[constructedByteAmount];
        
        //Type: plant info
        data[0] = (byte) 2;
        
        data[1] = (byte) amountPlant;
        
        int index = 2;
        
        for (int i = 0; i < res.getPlantAmount(); i++) {
            if (res.getPlant(i).isUpdate() || res.getPlant(i).isAdd()) {
                Resource reso = res.getPlant(i);
                data[index++] = (byte) i;
                
                data[index++] = addFlags(reso);
                
                data[index++] = (byte) (reso.getX() / 256);
                data[index++] = (byte) (reso.getX());
                
                data[index++] = (byte) (reso.getY() / 256);
                data[index++] = (byte) (reso.getY());
                
                data[index++] = (byte) reso.getQuantity();
                
                res.getPlant(i).setAdd(false);
            }
        }
        
        return data;
    }
    
    /**
     * To construct the water with its respective data
     * 
     * @param res
     * @return data
     */
    public static byte[] constructDataWaters(ResourceManager res) {
        byte[] data;
        int amountWater = 0;
        
        for (int i = 0; i < res.getWaterAmount(); i++) {
            if (res.getWater(i).isUpdate() || res.getWater(i).isAdd()) {
                amountWater++;
            }
        }

        constructedByteAmount = (amountWater) * RES_DATA_SIZE + 3;
        data = new byte[constructedByteAmount];
        
        //Type: water info
        data[0] = (byte) 3;
        
        data[1] = (byte) amountWater;
        
        int index = 2;
        
        for (int i = 0; i < res.getWaterAmount(); i++) {
            if (res.getWater(i).isUpdate() || res.getWater(i).isAdd()) {
                Resource reso = res.getWater(i);
                data[index++] = (byte) i;
                
                data[index++] = addFlags(reso);
                
                data[index++] = (byte) (reso.getX() / 256);
                data[index++] = (byte) (reso.getX());
                
                data[index++] = (byte) (reso.getY() / 256);
                data[index++] = (byte) (reso.getY());
                
                data[index++] = (byte) reso.getQuantity();
                
                reso.setAdd(false);
            }
        }
        
        return data;
    }
    
    /**
     * To construct the predators with their respective data
     * 
     * @param preds
     * @return data
     */
    public static byte[] constructDataPreds(PredatorManager preds) {
        byte[] data;
        constructedByteAmount = preds.getPredatorAmount() * PRE_DATA_SIZE + 2;
        data = new byte[constructedByteAmount];
        
        //Type: pred info
        data[0] = (byte) 4;
        data[1] = (byte) preds.getPredatorAmount();
        
        int index = 2;
        
        for (int i = 0; i < preds.getPredatorAmount(); i++) {
            Predator pred = preds.getPredator(i);
            data[index++] = addFlags(pred);
            
            data[index++] = (byte) (pred.getX()/ 256);
            data[index++] = (byte) (pred.getX());

            data[index++] = (byte) (pred.getY() / 256);
            data[index++] = (byte) (pred.getY());
            
            data[index++] = (byte) ((int) pred.getLife());
        }
        
        return data;
    }
    
    /**
     * To construct the data when a player extincts
     * 
     * @return data
     */
    public static byte[] constructDataExtinct() {
        byte[] data;
        constructedByteAmount = 1;
        data = new byte[constructedByteAmount];
        
        data[0] = (byte) 5;
        
        return data;
    }
    
    /**
     * To construct the data when a player wins
     * 
     * @return data
     */
    public static byte[] constructDataWin() {
        byte[] data;
        constructedByteAmount = 1;
        data = new byte[constructedByteAmount];
        
        data[0] = (byte) 6;
        
        return data;
    }
    
    /**
     * To construct the state of the game
     * 
     * @param game
     * @return result
     */
    private static byte constructGameState(Game game) {
        byte result = 0;
        
        if (game.isNight()) {
            result = (byte) (result | 128);
        }
        
        if (null != game.getWeather().getState()) switch (game.getWeather().getState()) {
            case Clear:
                result = (byte) (result | 64);
                break;
            case Dry:
                result = (byte) (result | 32);
                break;
            case Rain:
                result = (byte) (result | 16);
                break;
            case Snow:
                result = (byte) (result | 8);
                break;
            case Storm:
                result = (byte) (result | 4);
                break;
            case Hail:
                result = (byte) (result | 2);
                break;
            default:
                break;
        }
        
        return result;
    }
    
    /**
     * To construct the data when the client is ready
     * 
     * @param client
     * @return data
     */
    public static byte[] constructReady(boolean client) {
        byte[] data;
        constructedByteAmount = 2;
        data = new byte[constructedByteAmount];
        
        //Type: Ready
        data[0] = (byte) 7;
        
        if (client) {
            data[1] = (byte) 128;
        } else {
            data[1] = (byte) 64;
        }
        
        return data;
    }
    
    /**
     * To parse bytes
     * 
     * @param orgs
     * @param data 
     */
    public static void parseBytes(OrganismManager orgs, byte[] data) {
        int index = 3;
        int x;
        int y;
        
        double life;
        int hunger;
        int thirst;
        
        if (orgs == null) {
            return;
        }
        
        int addAmount = (int) unsignByte(data[1]) - orgs.getAmount();
        
        if (!orgs.getGame().isServer()) {
            if ((unsignByte(data[2]) & 128) == 128) {
                orgs.getGame().setNight(true);
            } else {
                orgs.getGame().setNight(false);
            }
            
            if ((unsignByte(data[2]) & 64) == 64) {
                orgs.getGame().getWeather().setWeather(Weather.State.Clear);
            } else if ((unsignByte(data[2]) & 32) == 32) {
                orgs.getGame().getWeather().setWeather(Weather.State.Dry);
            }  else if ((unsignByte(data[2]) & 16) == 16) {
                orgs.getGame().getWeather().setWeather(Weather.State.Rain);
            } else if ((unsignByte(data[2]) & 8) == 8) {
                orgs.getGame().getWeather().setWeather(Weather.State.Snow);
            } else if ((unsignByte(data[2]) & 4) == 4) {
                orgs.getGame().getWeather().setWeather(Weather.State.Storm);
            }  else if ((unsignByte(data[2]) & 2) == 2) {
                orgs.getGame().getWeather().setWeather(Weather.State.Hail);
            }
        }
        
        for (int i = 0; i < orgs.getAmount(); i++) {
            Organism org = orgs.getOrganism(i);
            
            x = data[index++] * 256 + unsignByte(data[index++]);
            y = data[index++] * 256 + unsignByte(data[index++]);

            org.setPoint(new Point(x, y));
            
            life = (double) ((int) unsignByte((data[index++])));
            hunger = data[index++];
            thirst = data[index++];
            org.setLife(life);
            
            parseMutations(org.getOrgMutations(), data, index++);
            getExtraInfo(org, orgs, data, index++, true);
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
            getExtraInfo(org, orgs, data, index++, true);
            org.setEgg(true);
            org.setBorn(false);
            
            orgs.addOrganism(org);
        }
    }
    
    /**
     * To parse the bytes of the plants
     * 
     * @param res
     * @param data
     * @param server 
     */
    public static void parseBytesPlants(ResourceManager res, byte[] data, boolean server) {
        int index = 2;
        int x;
        int y;

        int quantity;
        
        ArrayList<Integer> removeIndices = new ArrayList<>();
        
        int plantAmount = unsignByte(data[1]);
        int plantsUpdated = 0;
        
        if (plantAmount == 0) {
            return;
        }
        
        //Update current plants
        for (int i = 0; i < res.getPlantAmount(); i++) {
            Resource plant;
            int ind = unsignByte(data[index++]);
            
            if (ind == i) {
                if (getFlags(data, index++)) {
                    removeIndices.add(i);
                }
                
                plant = res.getPlant(i);
                x = data[index++] * 256 + unsignByte(data[index++]);
                y = data[index++] * 256 + unsignByte(data[index++]);

                //plant.setX(x);
                //plant.setY(y);

                quantity = unsignByte(data[index++]);
                plant.setQuantity(quantity);

                plantsUpdated++;
            } else {
                index--;
            } 
        }
        
        int plantRemaining = plantAmount - plantsUpdated;
        
        if (!server) {
            //Add remaining plants
            for (int i = 0; i < plantRemaining; i++) {
                Resource plant = new Resource(0, 0, PLANT_SIZE, PLANT_SIZE, res.getGame(), Resource.ResourceType.Plant);
                int ind = unsignByte(data[index++]);
                boolean remove = getFlags(data, index++);

                x = data[index++] * 256 + unsignByte(data[index++]);
                y = data[index++] * 256 + unsignByte(data[index++]);

                plant.setX(x);
                plant.setY(y);

                quantity = unsignByte(data[index++]);
                plant.setQuantity(quantity);

                plant.updatePositions();
                plant.setAdd(false);
                res.addPlant(plant);
            }
        }
        
        //Remove flagged plants
        for (int i = 0; i < removeIndices.size(); i++) {
            res.removePlant(res.getPlant(removeIndices.get(i)));
        }
    }
    
    /**
     * To parse the bytes of the water
     * 
     * @param res
     * @param data
     * @param server 
     */
    public static void parseBytesWaters(ResourceManager res, byte[] data, boolean server) {
        int index = 2;
        int x;
        int y;

        int quantity;
        
        ArrayList<Integer> removeIndices = new ArrayList<>();
        
        int waterAmount = unsignByte(data[1]);
        int watersUpdated = 0;
        
        if (waterAmount == 0)
            return;
        
        //Update current plants
        for (int i = 0; i < res.getWaterAmount(); i++) {
            Resource water;
            int ind = unsignByte(data[index++]);
            
            if (ind == i) {
                if (getFlags(data, index++)) {
                    removeIndices.add(i);
                }
                
                water = res.getWater(i);
                x = data[index++] * 256 + unsignByte(data[index++]);
                y = data[index++] * 256 + unsignByte(data[index++]);
                
                /*
                if (!server) {
                    water.setX(x);
                    water.setY(y);
                }*/

                quantity = unsignByte(data[index++]);
                water.setQuantity(quantity);

                watersUpdated++;
            } else {
                index--;
            } 
        }
        
        int waterRemaining = waterAmount - watersUpdated;
        
        if (!server) {
            //Add remaining plants
            for (int i = 0; i < waterRemaining; i++) {
                Resource water = new Resource(0, 0, WATER_SIZE, WATER_SIZE, res.getGame(), Resource.ResourceType.Water);
                int ind = unsignByte(data[index++]);
                boolean remove = getFlags(data, index++);

                x = data[index++] * 256 + unsignByte(data[index++]);
                y = data[index++] * 256 + unsignByte(data[index++]);

                water.setX(x);
                water.setY(y);

                quantity = unsignByte(data[index++]);
                water.setQuantity(quantity);

                water.updatePositions();
                water.setAdd(false);
                res.addWater(water);
            }
            
            //Remove flagged plants
            for (int i = 0; i < removeIndices.size(); i++) {
                res.removeWater(res.getWater(removeIndices.get(i)));
            }
        } 
    }
    
    /**
     * To parse the bytes of the predators
     * 
     * @param preds
     * @param data 
     */
    public static void parseBytesPreds(PredatorManager preds, byte[] data) {
        int index = 2;
        int x;
        int y;

        int life;
        int amount = unsignByte(data[1]);
        int addAmount = amount - preds.getPredatorAmount();
        
        for (int i = 0; i < preds.getPredatorAmount(); i++) {
            Predator pred = preds.getPredator(i);
            getFlags(data, index++, pred);
            
            x = data[index++] * 256 + unsignByte(data[index++]);
            y = data[index++] * 256 + unsignByte(data[index++]);
            
            pred.setPoint(new Point(x, y));
            
            life = unsignByte(data[index++]);
            
            pred.setLife(life);
        }
        
        for (int i = 0; i < addAmount; i++) {
            Predator pred = new Predator(0, 0, PREDATOR_SIZE,PREDATOR_SIZE, preds.getGame(),0); 
            
            getFlags(data, index++, pred);
            
            x = data[index++] * 256 + unsignByte(data[index++]);
            y = data[index++] * 256 + unsignByte(data[index++]);
            
            pred.setX(x);
            pred.setY(y);
            pred.setPoint(new Point(x, y));
            
            life = unsignByte(data[index++]);
            
            pred.setLife(life);
        }
    }
    
    /**
     * To convert the mutations
     * 
     * @param muts
     * @return result
     */
    private static byte convertMutations(MutationManager muts) {
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
    
    /**
     * To parse the mutations
     * 
     * @param muts
     * @param data
     * @param index 
     */
    private static void parseMutations(MutationManager muts, byte[] data, int index) {
        int tier;
        
        // 01 01 01 01
        
        //Strength
        tier = ((data[index] >> 6) & 0x3);
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
    
    /**
     * To add extra information
     * 
     * @param org
     * @param muts
     * @return result
     */
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
    
    /**
     * To add flags for the resources
     * 
     * @param res
     * @return result
     */
    private static byte addFlags(Resource res) {
        byte result = 0;
        
        if (res.isOver()) {
            result = (byte) (result | 128);
        }
        
        if (res.isPlant()) {
            result = (byte) (result | 64);
        }
        
        return result;
    }
    
    /**
     * To add flags for the predators
     * 
     * @param pred
     * @return result
     */
    private static byte addFlags(Predator pred) {
        byte result = 0;
        
        if (pred.isDead()) {
            result = (byte) (result | 128);
        }
        
        if (pred.getMode() == Predator.Mode.Attacking) {
            result = (byte) (result | 64);
        }
        
        //Small
        if (pred.getDamage() < 0.08) {
            result = (byte) (result | 0);
        } else if (pred.getDamage() < 0.25) {
            result = (byte) (result | 1);
        } else {
            result = (byte) (result | 2);
        }
        
        return result;
    }
    
    /**
     * To get the flags of the resources
     * 
     * @param data
     * @param index
     * @return boolean
     */
    private static boolean getFlags(byte[] data, int index) {
        if ((unsignByte(data[index]) & 128) == 128) {
            return true;
        }
        
        return false;
    }
    
    /**
     * To get the flags of the predators
     * 
     * @param data
     * @param index
     * @param pred 
     */
    private static void getFlags(byte[] data, int index, Predator pred) {
        if ((unsignByte(data[index]) & 128) == 128) {
            pred.setDead(true);
        }
        
        if ((unsignByte(data[index]) & 64) == 64) {
            pred.setAbsMaxVel(pred.getChasingSpeed());
        } else {
            pred.setAbsMaxVel(1);
        }
        
        if ((data[index] & 0x3) == 0) {
            //Small
            pred.setWidth(PREDATOR_SIZE - 20);
            pred.setHeight(PREDATOR_SIZE - 20);
            
            pred.setDamage(0.07);
            pred.setChasingSpeed(3);
            
            pred.setMaxHealth(100);
            pred.setLife(100);
        } else if ((data[index] & 0x3) == 1) {
            //Med
            pred.setWidth(PREDATOR_SIZE);
            pred.setHeight(PREDATOR_SIZE );
            
            pred.setDamage(0.2);
            pred.setChasingSpeed(2);
            
            pred.setMaxHealth(100);
            pred.setLife(100);
        } else {
            //BIG SCARY
            pred.setWidth(PREDATOR_SIZE + 20);
            pred.setHeight(PREDATOR_SIZE + 20);
            
            pred.setDamage(0.3);
            pred.setChasingSpeed(2);
            
            pred.setMaxHealth(150);
            pred.setLife(150);
        }
    }
    
    /**
     * To get extra information
     * 
     * @param org
     * @param orgs
     * @param data
     * @param index
     * @param first 
     */
    private static void getExtraInfo(Organism org, OrganismManager orgs, byte[] data, int index, boolean first) {
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
        
        if (first) {
            org.updateMutations(true);
        }
    }
    
    /**
     * To get the constructed byte amount
     * 
     * @return constructedByteAmount
     */
    public static int getConstructedByteAmount() {
        return constructedByteAmount;
    }
    
    /**
     * To unsign byte
     * 
     * @param b
     * @return unsigned byte
     */
    public static int unsignByte(byte b) {
        return (int) (b & 0xff);
    }
}
