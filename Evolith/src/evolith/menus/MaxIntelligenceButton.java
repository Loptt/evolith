/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package evolith.menus;

import evolith.entities.Organism;
import static evolith.helpers.Commons.MAX_INTELLIGENCE;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

/**
 *
 * @author carlo
 */
public class MaxIntelligenceButton extends Button {
    
    private Organism org;
    
    public MaxIntelligenceButton(int x, int y, int width, int height, BufferedImage off, BufferedImage on, Organism org) {
        super(x, y, width, height, off, on);
        this.org = org;
    }
    
    public MaxIntelligenceButton(int x, int y, int width, int height, BufferedImage off) {
        super(x, y, width, height, off);
    }

    public void applyMouse() {
        pressed = true;
    }

    @Override
    public void tick() {
        super.tick();
    }

    @Override
    public void render(Graphics g) {
        super.render(g);
        
        g.setColor(new Color(255,215,0));
        g.fillRect(x + 10, y + 15, (int) 151 * org.getIntelligence() / MAX_INTELLIGENCE, 19);
    }

    public void setOrg(Organism org) {
        this.org = org;
    }

    public Organism getOrg() {
        return org;
    }
}
