/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package evolith.helpers;

import evolith.menus.OrganismPanel;
import java.awt.Font;
import java.awt.FontFormatException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author ErickFrank
 */
public class FontLoader  {
    private String fontPath;                //Path to where the font is located
    private Font fontEvolve;                //Font used in the organism panel
    private InputStream is;                 //Manages the input of the name in the panel

    public FontLoader() {
        fontPath = "/Fonts/MADE-Evolve-Sans-Regular.ttf";
        this.is = OrganismPanel.class.getResourceAsStream(fontPath);
        try {
            fontEvolve = Font.createFont(Font.TRUETYPE_FONT, is);
            fontEvolve = fontEvolve.deriveFont(20f);
        } catch (FontFormatException ex) {
            Logger.getLogger(OrganismPanel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(OrganismPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Font getFontEvolve() {
        return fontEvolve;
    }
    
}
