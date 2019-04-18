/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package evolith.menus;

import evolith.engine.Assets;
import evolith.entities.Organism;
import evolith.game.Game;
import evolith.helpers.Commons;
import static evolith.helpers.Commons.MAX_MATURITY;
import static evolith.helpers.Commons.MAX_SIZE;
import static evolith.helpers.Commons.MAX_SPEED;
import static evolith.helpers.Commons.MAX_STEALTH;
import static evolith.helpers.Commons.MAX_STRENGTH;
import static evolith.helpers.Commons.MAX_SURVIVABILITY;
import static evolith.helpers.Commons.PANEL_WIDTH;
import evolith.helpers.InputReader;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ErickFrank
 */
public class MutationPanel extends Menu implements Commons {

    private Organism organism;

    private String fontPath;
    private String name;
    private boolean active;
    private Font fontEvolve;
    private InputStream is;
    private HashMap<Integer, String> hmap;

    private InputReader inputReader;

    public MutationPanel(int x, int y, int width, int height, Game game) {
        super(x, y, width, height, game);
        fontPath = "/Fonts/MADE-Evolve-Sans-Regular.ttf";
        this.is = OrganismPanel.class.getResourceAsStream(fontPath);
        try {
            fontEvolve = Font.createFont(Font.TRUETYPE_FONT, is);
            fontEvolve = fontEvolve.deriveFont(18f);
        } catch (FontFormatException ex) {
            Logger.getLogger(MutationPanel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(MutationPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
        inputReader = new InputReader(game);
    }

    public MutationPanel(Organism organism, int x, int y, int width, int height, Game game) {
        super(x, y, width, height, game);
        this.organism = organism;
        //Evolve
        buttons.add(new Button(this.x + PANEL_WIDTH / 2 + 200, this.y + 600, 240, 60, Assets.organismPanel_reproduceButton));
        //Not Evolve
        buttons.add(new Button(this.x + PANEL_WIDTH / 2 - 250, this.y + 600, 240, 60, Assets.organismPanel_reproduceButton));
        active = true;
        hmap = new HashMap<Integer, String>();
        hmap.put(1, "I");
        hmap.put(2, "II");
        hmap.put(3, "III");
        hmap.put(4, "IV");
        
    }

    @Override
    public void tick() {
        
        /*
        if(organism.isDead())
        {
            active = false;
        }
        */
    }

    @Override
    public void render(Graphics g) {
        if(active){
        g.drawImage(Assets.mutation_menu, x, y, width, height, null);
        g.setColor(Color.red);
        
        g.drawRect(x + MUTATION_PANEL_WIDTH / 2 - 250, y + 500, 240, 60);
        g.drawRect(x + MUTATION_PANEL_WIDTH / 2 + 250, y + 500, 240, 60);
     
        for (int i = 0; i < organism.getOrgMutations().getMutations().size(); i++) {
            
            g.setColor(Color.WHITE);
            
            g.fillRect(x + 495, y + 81 + 115 * i, (int) 60 * organism.getStrength() / MAX_STRENGTH, 15);
            g.fillRect(x + 495, y + 106 + 115 * i, (int) 60 * organism.getMaxHealth() / MAX_SURVIVABILITY, 15);
            g.fillRect(x + 613, y + 81 + 115 * i, (int) 60 * organism.getSpeed() / MAX_SPEED, 15);
            g.fillRect(x + 613, y + 106 + 115 * i, (int) 60 * organism.getStealth() / MAX_STEALTH, 15);
            
            int j = 0;
            
             for (int k = 0; k < organism.getOrgMutations().getMutations().get(i).size(); k++) {
                    if (organism.getOrgMutations().getMutations().get(i).get(j).isActive()) {
                        j  = k;
                    }
              }
                    j=0;
                    if (j == organism.getOrgMutations().getMutations().get(i).size() - 1) {
                        g.drawImage(Assets.mutation_max_tier, 380, 110, y + 395, x + 30 + i * 115, null);
                        g.setColor(Color.red);
                        g.drawRect(380, 110, y + 395, x + 30 + i * 115);
                    } else {
                        g.setColor(Color.WHITE);
                        g.setFont(fontEvolve);
                       // g.drawString((String)hmap.get(organism.getOrgMutations().getMutations().get(i).get(j).getTier()) , x + 468, y + 48 + i*118);
                        g.drawString(organism.getOrgMutations().getMutations().get(i).get(j).getName(), x + 501, y + 48 + i*118);

                        if (organism.getOrgMutations().getMutations().get(i).get(j).getStrength() != 0) {

                            if (organism.getOrgMutations().getMutations().get(i).get(j).getStrength() < 0) {
                                g.setColor(Color.RED);
                                g.fillRect(x + 495 + ((int) 60* (organism.getStrength() + organism.getOrgMutations().getMutations().get(i).get(j).getStrength())/MAX_STRENGTH) , y + 81 + 115 * i, (int) 60 *Math.abs(organism.getOrgMutations().getMutations().get(i).get(j).getStrength()) / MAX_STRENGTH, 15);
                            }
                            else {
                                g.setColor(Color.GREEN);
                                g.fillRect(x + 495 + organism.getStrength(), y + 81 + 115 * i, (int) 60 * organism.getOrgMutations().getMutations().get(i).get(j).getStrength()/ MAX_STRENGTH, 15);
                            }
                            
                            if (organism.getOrgMutations().getMutations().get(i).get(j).getMaxHealth() < 0) {
                                g.setColor(Color.RED);
                                g.fillRect(x + 495 + organism.getMaxHealth() + organism.getOrgMutations().getMutations().get(i).get(j).getStrength(), y + 106 + 115 * i,(int) 60 * Math.abs(organism.getOrgMutations().getMutations().get(i).get(j).getMaxHealth()) / MAX_SURVIVABILITY, 15);
                            }
                            else {
                                g.setColor(Color.GREEN);
                                g.fillRect(x + 495 + organism.getMaxHealth(), y + 106 + 115 * i,(int) 60 * organism.getOrgMutations().getMutations().get(i).get(j).getMaxHealth() / MAX_SURVIVABILITY, 15);
                            }
                             
                            if (organism.getOrgMutations().getMutations().get(i).get(j).getStealth() < 0) {
                                g.setColor(Color.RED);
                                g.fillRect(x + 613 + organism.getStealth() + organism.getOrgMutations().getMutations().get(i).get(j).getStealth(), y + 106 + 115 * i, (int) 60 *Math.abs(organism.getOrgMutations().getMutations().get(i).get(j).getStealth()) / MAX_STEALTH, 15);
                            }
                            else {
                                g.setColor(Color.GREEN);
                                g.fillRect(x + 613 + organism.getStealth(), y + 106 + 115 * i, (int) 60 * organism.getOrgMutations().getMutations().get(i).get(j).getStealth() / MAX_STEALTH, 15);
                            }
                            
                            if (organism.getOrgMutations().getMutations().get(i).get(j).getSpeed() < 0) {
                                g.setColor(Color.RED);
                                g.fillRect(x + 613 + organism.getSpeed() + organism.getOrgMutations().getMutations().get(i).get(j).getSpeed(), y + 81 + 115 * i, (int) 60 *Math.abs(organism.getOrgMutations().getMutations().get(i).get(j).getSpeed()) / MAX_SPEED, 15);
                            }
                            else {
                                g.setColor(Color.GREEN);
                                g.fillRect(x + 613 + organism.getSpeed(), y + 81 + 115 * i, (int) 60* organism.getOrgMutations().getMutations().get(i).get(j).getSpeed() / MAX_SPEED, 15);
                            }
                        }
                    }

                }


        for (int i = 0; i < buttons.size(); i++) {
            buttons.get(i).render(g);
        }

    }
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
    

}
