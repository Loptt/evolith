/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package evolith.helpers;

import evolith.game.Game;
import java.awt.Graphics;

/**
 *
 * @author Erick González
 * @author Carlos Estrada
 * @author Víctor Villarreal
 * @author Moisés Fernández
 */
public class InputReader {
    private String speciesName;
    private Game game;
    
    public InputReader(Game game) {
        this.game = game;
        this.speciesName = "";
    }

    public String getSpeciesName() {
        return speciesName;
    }

    public void setSpeciesName(String speciesName) {
        this.speciesName = speciesName;
    }
    
    public void readInput() {
            if (game.getInputKeyboard().a) {
                speciesName += 'a';
            }
            
            if (game.getInputKeyboard().b) {
                speciesName += 'b';
            }
            
            if (game.getInputKeyboard().c) {
                speciesName += 'c';
            }
            
            if (game.getInputKeyboard().d) {
                speciesName += 'd';
            }
            
            if (game.getInputKeyboard().e) {
                speciesName += 'e';
            }
            
            if (game.getInputKeyboard().f) {
                speciesName += 'f';
            }
            
            if (game.getInputKeyboard().g) {
                speciesName += 'g';
            }
            
            
            if (game.getInputKeyboard().h) {
                speciesName += 'h';
            }
            
            if (game.getInputKeyboard().i) {
                speciesName += 'i';
            }
            
            
            if (game.getInputKeyboard().j) {
                speciesName += 'j';
            }
            
            
            if (game.getInputKeyboard().k) {
                speciesName += 'k';
            }
            
            
            if (game.getInputKeyboard().l) {
                speciesName += 'l';
            }
            
            
            if (game.getInputKeyboard().m) {
                speciesName += 'm';
            }
            
            
            if (game.getInputKeyboard().n) {
                speciesName += 'n';
            }
            
            
            if (game.getInputKeyboard().o) {
                speciesName += 'o';
            }
            
            if (game.getInputKeyboard().p) {
                speciesName += 'p';
            }
            
            
            if (game.getInputKeyboard().q) {
                speciesName += 'q';
            }
            
            
            if (game.getInputKeyboard().r) {
                speciesName += 'r';
            }
            
            
            if (game.getInputKeyboard().s) {
                speciesName += 's';
            }
            
            
            if (game.getInputKeyboard().t) {
                speciesName += 't';
            }
            
            
            if (game.getInputKeyboard().u) {
                speciesName += 'u';
            }
            
            
            if (game.getInputKeyboard().v) {
                speciesName += 'v';
            }
            
            if (game.getInputKeyboard().w) {
                speciesName += 'w';
            }
            
            
            if (game.getInputKeyboard().x) {
                speciesName += 'x';
            }
            
            
            if (game.getInputKeyboard().y) {
                speciesName += 'y';
            }
            
            
            if (game.getInputKeyboard().z) {
                speciesName += 'z';
            }
            
            if (game.getInputKeyboard().delete) {
                if (speciesName.length() > 0) {
                    speciesName = speciesName.substring(0, speciesName.length() - 1);
                    System.out.println("HEEEHEEE");
                }
            }
    }
}
