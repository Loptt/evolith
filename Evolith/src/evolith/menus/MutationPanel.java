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
import static evolith.helpers.Commons.PANEL_WIDTH;
import evolith.helpers.InputReader;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.io.IOException;
import java.io.InputStream;
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
    private Font fontEvolve;
    private InputStream is;

    private InputReader inputReader;

    public MutationPanel(int x, int y, int width, int height, Game game) {
        super(x, y, width, height, game);
        fontPath = "/Fonts/MADE-Evolve-Sans-Regular.ttf";
        this.is = OrganismPanel.class.getResourceAsStream(fontPath);
        try {
            fontEvolve = Font.createFont(Font.TRUETYPE_FONT, is);
            fontEvolve = fontEvolve.deriveFont(20f);
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

        //Exit Button
        buttons.add(new Button(this.x + this.width - 20, this.y - 20, 40, 40));
        //Evolve
        buttons.add(new Button(this.x + PANEL_WIDTH / 2 - 150, this.y + 400, 300, 75, Assets.organismPanel_reproduceButton)); // Reproduce button
        //Not Evolve
        buttons.add(new Button(this.x + PANEL_WIDTH / 2 - 150, this.y + 400, 300, 75, Assets.organismPanel_reproduceButton)); // Reproduce button

    }

    @Override
    public void tick() {
    }

    @Override
    public void render(Graphics g) {
    }

}
