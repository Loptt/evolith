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
        inputReader = new InputReader(game);
    }

    public MutationPanel(Organism organism, int x, int y, int width, int height, Game game) {
        super(x, y, width, height, game);
        this.organism = organism;
        //Evolve
        buttons.add(new Button(this.x + PANEL_WIDTH / 2 - 250, this.y + 450, 240, 60, Assets.organismPanel_reproduceButton));
        //Not Evolve
        buttons.add(new Button(this.x + PANEL_WIDTH / 2 + 250, this.y + 450, 240, 60, Assets.organismPanel_reproduceButton));

        buttons.add(new Button(x + 300, y + 30, 390, 110));
        buttons.add(new Button(x + 300, y + 145, 390, 110));
        buttons.add(new Button(x + 300, y + 260, 390, 110));
        buttons.add(new Button(x + 300, y + 375, 390, 110));   
        selection = 0;
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

        for (int i = 0; i < buttons.size(); i++) {
            if (buttons.get(i).hasMouse(game.getMouseManager().getX(), game.getMouseManager().getY())) {
                //if the mouse is over the button 
                buttons.get(i).setActive(true);
                //if left click change mouse status
                if (game.getMouseManager().isLeft()) {
                    buttons.get(i).setPressed(true);
                    for(int j=0; j<buttons.size(); j++){
                        if(i!=j){
                            buttons.get(j).setPressed(false);
                        }
                    }
                    game.getMouseManager().setLeft(false);
                }
            } else {
                buttons.get(i).setActive(false);
            }
            if(selection != 0 && buttons.get(0).isPressed())
            {
                int j = 0;

                for (int k = 0; k < organism.getOrgMutations().getMutations().get(selection-1).size(); k++) {
                    if (organism.getOrgMutations().getMutations().get(selection-1).get(j).isActive()) {
                        j = k;
                    }
                }
                if(j!=organism.getOrgMutations().getMutations().get(selection-1).size()-1){
                    active = false;
                    organism.getOrgMutations().getMutations().get(selection-1).get(j).setActive(false);
                    organism.getOrgMutations().getMutations().get(selection-1).get(j+1).setActive(true);
                    
            
                }
            }
            if (buttons.get(2).isPressed()) {
                selection = 1;
                
            }
            if (buttons.get(3).isPressed()) {
                
                selection = 2;
                
            }
            if (buttons.get(4).isPressed()) {
                
                selection = 3;
                
            }
            if (buttons.get(5).isPressed()) {
                
                selection = 4;
                
            }
        }
        
    }

    @Override
    public void render(Graphics g) {
        if (active) {
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
                        j = k;
                    }
                }
                if(selection != 0)
                {
                    g.drawImage(Assets.mutation_select, x + 297, y + 25 + (selection-1) * 115, 397, 120, null);  
                }
                if (j == organism.getOrgMutations().getMutations().get(i).size() - 1) {
                    g.drawImage(Assets.mutation_max_tier, x + 300, y + 30 + i * 115, 390, 110, null);
                    g.setColor(Color.red);
                    g.drawRect(x + 300, y + 30 + i * 115, 390, 110);
                } else {
                    g.setColor(Color.WHITE);
                    g.setFont(fontEvolve);
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
