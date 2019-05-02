/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package evolith.entities;

import evolith.game.Game;
import evolith.helpers.Commons;
import evolith.menus.Menu;
import java.awt.Graphics;

/**
 *
 * @author ErickFrank
 */
public class StatisticsPanel extends Menu implements Commons{

    public StatisticsPanel(int x, int y, int width, int height, Game game) {
        super(x, y, width, height, game);
    }

    @Override
    public void tick() {
 }

    @Override
    public void render(Graphics g) {
}
    
}
