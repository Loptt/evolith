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
import static evolith.helpers.Commons.MAX_SPEED;
import static evolith.helpers.Commons.MAX_STEALTH;
import static evolith.helpers.Commons.MAX_STRENGTH;
import static evolith.helpers.Commons.MAX_SURVIVABILITY;
import evolith.helpers.InputReader;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
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

    private int selection;

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
    }

    public MutationPanel(Organism organism, int x, int y, int width, int height, Game game) {
        super(x, y, width, height, game);
        this.organism = organism;
        //Evolve
        buttons.add(new Button(this.x + MUTATION_PANEL_WIDTH / 2 - 250, this.y + MUTATION_PANEL_HEIGHT+10, 240, 60, Assets.mutationPanel_evolveButton_ON,Assets.mutationPanel_evolveButton_OFF));
        //Not Evolve
        buttons.add(new Button(this.x + MUTATION_PANEL_WIDTH / 2 + 250, this.y + MUTATION_PANEL_HEIGHT+10, 240, 60, Assets.mutationPanel_not_evolveButton_ON,Assets.mutationPanel_not_evolveButton_OFF));
        //Strength
        buttons.add(new Button(x + 300, y + 30, 390, 110));
        //Speed
        buttons.add(new Button(x + 300, y + 145, 390, 110));
        //Max_Health
        buttons.add(new Button(x + 300, y + 260, 390, 110));
        //Strealth
        buttons.add(new Button(x + 300, y + 375, 390, 110));
        selection = 0;
        active = false;
        hmap = new HashMap<Integer, String>();
        hmap.put(1, "I");
        hmap.put(2, "II");
        hmap.put(3, "III");
        hmap.put(4, "IV");

    }

    @Override
    public void tick() {
        if (active) {
            for (int i = 0; i < buttons.size(); i++) {
                if (buttons.get(i).hasMouse(game.getMouseManager().getX(), game.getMouseManager().getY())) {
                    //if the mouse is over the button 
                    buttons.get(i).setActive(true);
                    //if left click change mouse status
                    if (game.getMouseManager().isLeft()) {
                        buttons.get(i).setPressed(true);
                        for (int j = 0; j < buttons.size(); j++) {
                            if (i != j) {
                                buttons.get(j).setPressed(false);
                            }
                        }
                        game.getMouseManager().setLeft(false);
                    }
                } else {
                    buttons.get(i).setActive(false);
                }
            }
            if (buttons.get(1).isPressed()) {
                active = false;
            }
            if (buttons.get(2).isPressed() && !organism.getOrgMutations().getMutations().get(0).get(organism.getOrgMutations().getMutations().get(0).size() -1).isActive()) {
                selection = 1;

            }
            if (buttons.get(3).isPressed() && !organism.getOrgMutations().getMutations().get(1).get(organism.getOrgMutations().getMutations().get(1).size() -1).isActive()) {

                selection = 2;

            }
            if (buttons.get(4).isPressed()  && !organism.getOrgMutations().getMutations().get(2).get(organism.getOrgMutations().getMutations().get(2).size() -1).isActive()) {

                selection = 3;

            }
            if (buttons.get(5).isPressed()  && !organism.getOrgMutations().getMutations().get(3).get(organism.getOrgMutations().getMutations().get(3).size() -1).isActive()) {

                selection = 4;

            }
            
            //Evolve organism
            if (selection != 0 && buttons.get(0).isPressed()) {
                int j = 0;
                
                //Iterate over the specified type of mutation
                for (int k = 0; k < organism.getOrgMutations().getMutations().get(selection - 1).size(); k++) {
                    if (organism.getOrgMutations().getMutations().get(selection - 1).get(k).isActive()) {
                        //J has the current active tier
                        j = k;
                    }
                }
                //Check if mutation is not already in the max tier
                if (j != organism.getOrgMutations().getMutations().get(selection - 1).size() - 1) {
                    //Check if no tier is active
                    if (j == 0 && !organism.getOrgMutations().getMutations().get(selection - 1).get(0).isActive()) {
                        organism.getOrgMutations().getMutations().get(selection - 1).get(j).setActive(true); //Working fine
                    } else {
                        //Deactivate current tier
                        organism.getOrgMutations().getMutations().get(selection - 1).get(j).setActive(false);
                        //Activate next tier
                        organism.getOrgMutations().getMutations().get(selection - 1).get(j + 1).setActive(true);
                    }
                    //organism.updateMutation(selection-1, j+1);

                }
                buttons.get(0).setPressed(false);
                selection = 0;
                active = false;

            }

        }
        /*
        if(organism.isDead())
        {
            active = false;
        }
         */

    }
    
    @Override
    public void render(Graphics g) {
        if (active) {
            
            g.drawImage(Assets.mutation_menu, x, y, width, height, null);
            
            g.drawImage(Assets.orgColors.get(organism.getSkin()), x + 71, y + 188, 140, 140, null);

            for (int i = 0; i < organism.getOrgMutations().getMutations().size(); i++) {
                for (int j = 0; j < organism.getOrgMutations().getMutations().get(i).size(); j++) {
                    if (organism.getOrgMutations().getMutations().get(i).get(j).isActive()) {
                        g.drawImage(organism.getOrgMutations().getMutations().get(i).get(j).getSprite(),
                                x + 71, y + 188,(int) 140*organism.getOrgMutations().getMutations().get(i).get(j).getWidth()/organism.getCurrentSize(),
                                (int)140*organism.getOrgMutations().getMutations().get(i).get(j).getHeight()/organism.getCurrentSize(), null);
                    }
                }
            }
            

            for (int i = 0; i < organism.getOrgMutations().getMutations().size(); i++) {

                g.setColor(Color.WHITE);

                g.fillRect(x + 495, y + 81 + 115 * i, (int) 60 * organism.getStrength() / MAX_STRENGTH, 15);
                g.fillRect(x + 495, y + 106 + 115 * i, (int) 60 * organism.getMaxHealth() / MAX_SURVIVABILITY, 15);
                g.fillRect(x + 613, y + 81 + 115 * i, (int) 60 * organism.getSpeed() / MAX_SPEED, 15);
                g.fillRect(x + 613, y + 106 + 115 * i, (int) 60 * organism.getStealth() / MAX_STEALTH, 15);

                int j = 0;
                
                //Get current active tier
                for (int k = 1; k <= organism.getOrgMutations().getMutations().get(i).size(); k++) {
                    if (organism.getOrgMutations().getMutations().get(i).get(k-1).isActive()) {
                        j = k;
                    }
                }
                //Draws selection rectangle
                if (selection != 0) {
                    g.drawImage(Assets.mutation_select, x + 297, y + 25 + (selection - 1) * 115, 397, 120, null);
                }
                //Check if max tier
                if (j == organism.getOrgMutations().getMutations().get(i).size()) {
                    g.drawImage(Assets.mutation_max_tier, x + 300, y + 30 + i * 115, 390, 110, null);
                } else {
                    g.setColor(Color.WHITE);
                    g.setFont(fontEvolve);
                    g.drawImage(organism.getOrgMutations().getMutations().get(i).get(j).getSprite(), x + 331, y + 55 + i*115, 60, 60, null);
                    g.drawString((String) hmap.get(organism.getOrgMutations().getMutations().get(i).get(j).getTier()), x + 468, y + 60 + i * 115);
                    g.drawString(organism.getOrgMutations().getMutations().get(i).get(j).getName(), x + 500, y + 60 + i * 115);

                    if (organism.getOrgMutations().getMutations().get(i).get(j).getStrength() != 0) {

                        if (organism.getOrgMutations().getMutations().get(i).get(j).getStrength() < 0) {
                            g.setColor(Color.RED);
                            g.fillRect(x + 495 + (int) 60 * ((organism.getStrength() + organism.getOrgMutations().getMutations().get(i).get(j).getStrength()) / MAX_STRENGTH), y + 81 + 115 * i, (int) 60 * -1 * organism.getOrgMutations().getMutations().get(i).get(j).getStrength() / MAX_STRENGTH, 15);
                        } else {
                            g.setColor(Color.GREEN);
                            g.fillRect(x + 495 + (int) 60 * organism.getStrength() / MAX_STRENGTH, y + 81 + 115 * i, (int) 60 * organism.getOrgMutations().getMutations().get(i).get(j).getStrength() / MAX_STRENGTH, 15);
                        }
                    }
                    if (organism.getOrgMutations().getMutations().get(i).get(j).getMaxHealth() != 0) {
                        if (organism.getOrgMutations().getMutations().get(i).get(j).getMaxHealth() < 0) {
                            g.setColor(Color.RED);
                            g.fillRect(x + 495 + (int) 60 * ((organism.getMaxHealth() + organism.getOrgMutations().getMutations().get(i).get(j).getStrength()) / MAX_SURVIVABILITY), y + 106 + 115 * i, (int) 60 * -1 * organism.getOrgMutations().getMutations().get(i).get(j).getMaxHealth() / MAX_SURVIVABILITY, 15);
                        } else {
                            g.setColor(Color.GREEN);
                            g.fillRect(x + 495 + (int) 60 * organism.getMaxHealth() / MAX_SURVIVABILITY, y + 106 + 115 * i, (int) 60 * organism.getOrgMutations().getMutations().get(i).get(j).getMaxHealth() / MAX_SURVIVABILITY, 15);
                        }
                    }
                    if (organism.getOrgMutations().getMutations().get(i).get(j).getStealth() != 0) {
                        if (organism.getOrgMutations().getMutations().get(i).get(j).getStealth() < 0) {
                            g.setColor(Color.RED);
                            g.fillRect(x + 613 + (int) 60 * ((organism.getStealth() + organism.getOrgMutations().getMutations().get(i).get(j).getStealth()) / MAX_STEALTH), y + 106 + 115 * i, (int) 60 * -1 * organism.getOrgMutations().getMutations().get(i).get(j).getStealth() / MAX_STEALTH, 15);
                        } else {
                            g.setColor(Color.GREEN);
                            g.fillRect(x + 613 + (int) 60 * organism.getStealth() / MAX_STEALTH, y + 106 + 115 * i, (int) 60 * organism.getOrgMutations().getMutations().get(i).get(j).getStealth() / MAX_STEALTH, 15);
                        }
                    }
                    if (organism.getOrgMutations().getMutations().get(i).get(j).getSpeed() != 0) {
                        if (organism.getOrgMutations().getMutations().get(i).get(j).getSpeed() < 0) {
                            g.setColor(Color.RED);
                            g.fillRect(x + 613 + (int) 60 * ((organism.getSpeed() + organism.getOrgMutations().getMutations().get(i).get(j).getSpeed()) / MAX_SPEED), y + 81 + 115 * i, (int) 60 * -1 * organism.getOrgMutations().getMutations().get(i).get(j).getSpeed() / MAX_SPEED, 15);
                        } else {
                            g.setColor(Color.GREEN);
                            g.fillRect(x + 613 + (int) 60 * organism.getSpeed() / MAX_SPEED, y + 81 + 115 * i, (int) 60 * organism.getOrgMutations().getMutations().get(i).get(j).getSpeed() / MAX_SPEED, 15);
                        }
                    }
                }
            }

            for (int i = 0; i < buttons.size(); i++) {

                if (selection != 0 || i != 0) {
                    buttons.get(i).render(g);
                }
            }
        }
    }

    public ArrayList<Button> getButtons() {
        return buttons;
    }
    
    public Organism getOrganism() {
        return organism;
    }

    public void setOrganism(Organism organism) {
        this.organism = organism;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public HashMap<Integer, String> getHmap() {
        return hmap;
    }

    public void setHmap(HashMap<Integer, String> hmap) {
        this.hmap = hmap;
    }

    public int getSelection() {
        return selection;
    }

    public void setSelection(int selection) {
        this.selection = selection;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

}
