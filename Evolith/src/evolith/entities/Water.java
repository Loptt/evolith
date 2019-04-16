/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package evolith.entities;

import evolith.engine.Assets;
import evolith.game.Game;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

/**
 *
 * @author charles
 */
public class Water extends Resource {

    public Water(int x, int y, int width, int height, Game game) {
        super(x, y, width, height, game);
    }

    @Override
    public void tick() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void render(Graphics g) {
        g.setColor(new Color(173, 255, 250));
        g.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 20));

        g.drawImage(Assets.water, game.getCamera().getRelX(x), game.getCamera().getRelY(y), width, height, null);

        //To display the actual quantity over the maximum
        g.drawString(Integer.toString(quantity) + "/100", game.getCamera().getRelX(x) + 45, game.getCamera().getRelY(y) + 150);
    }
    
}
