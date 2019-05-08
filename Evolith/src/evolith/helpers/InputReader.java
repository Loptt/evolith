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
    private boolean onlyDelete;

    /**
     * To initialize the input reader when the organism has no name
     * 
     * @param game
     */
    public InputReader(Game game) {
        this.game = game;
        this.speciesName = "";
        this.onlyDelete = false;
    }

    /**
     * To initialize the input reader when the organism has a name
     * 
     * @param speciesName
     * @param game
     */
    public InputReader(String speciesName, Game game) {
        this.speciesName = speciesName;
        this.game = game;
        this.onlyDelete = false;
    }

    /**
     * To set the status if the user can only delete
     * 
     * @return onlyDelete
     */
    public boolean isOnlyDelete() {
        return onlyDelete;
    }

    /**
     * To get the status if the user can only delete
     * 
     * @param onlyDelete
     */
    public void setOnlyDelete(boolean onlyDelete) {
        this.onlyDelete = onlyDelete;
    }

    /**
     * To get the species name
     * 
     * @return speciesName
     */
    public String getSpeciesName() {
        return speciesName;
    }

    /**
     * To set the species name
     * 
     * @param speciesName
     */
    public void setSpeciesName(String speciesName) {
        this.speciesName = speciesName;
    }

    /**
     * To read the input of the user's keyboard when typing the species name
     */
    public void readInput() {
        if (!onlyDelete) {
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
            
            if (game.getInputKeyboard().num1) {
                speciesName += '1';
            }
            
            if (game.getInputKeyboard().num2) {
                speciesName += '2';
            }
            
            if (game.getInputKeyboard().num3) {
                speciesName += '3';
            }
            
            if (game.getInputKeyboard().num4) {
                speciesName += '4';
            }
            
            if (game.getInputKeyboard().num5) {
                speciesName += '5';
            }
            if (game.getInputKeyboard().num6) {
                speciesName += '6';
            }
            if (game.getInputKeyboard().num7) {
                speciesName += '7';
            }
            if (game.getInputKeyboard().num8) {
                speciesName += '8';
            }
            
            if (game.getInputKeyboard().num9) {
                speciesName += '9';
            }
            if (game.getInputKeyboard().num0) {
                speciesName += '0';
            }
            if (game.getInputKeyboard().period) {
                speciesName += '.';
            }
            
        }

        if (game.getInputKeyboard().delete && speciesName.length() > 0 && speciesName != null) {
            if (speciesName.length() == 1) {
                speciesName = "";
            } else {
                speciesName = speciesName.substring(0, speciesName.length() - 1);
            }
            System.out.println("Input is deleted");
        }
    }
}
