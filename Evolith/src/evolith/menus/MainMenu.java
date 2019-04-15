package evolith.menus;

import evolith.game.Game;
import evolith.engine.Assets;
import java.awt.Graphics;

/**
 *
 * @author Erick González
 * @author Carlos Estrada
 * @author Víctor Villarreal
 * @author Moisés Fernández
 */
public class MainMenu extends Menu {

    private boolean active;
    private boolean clickPlay;

    /**
     * Constructor of the main menu
     *
     * @param x
     * @param y
     * @param width
     * @param height
     * @param game
     */
    public MainMenu(int x, int y, int width, int height, Game game) {
        super(x, y, width, height, game);
        active = true;
        clickPlay = false;

        buttons.add(new Button(340, 390, 350, 74)); // Play button
        buttons.add(new Button(340, 500, 350, 74)); // Instructions button

    }

    /**
     * To
     *
     * @return active
     */
    public boolean isActive() {
        return active;
    }

    /**
     * To change the status of the active menu
     *
     * @param active
     */
    public void setActive(boolean active) {
        this.active = active;
    }

    /**
     * To get the status of the play button
     *
     * @return clickPlay
     */
    public boolean isClickPlay() {
        return clickPlay;
    }

    /**
     * To set the status of the play button
     *
     * @param clickPlay
     */
    public void setClickPlay(boolean clickPlay) {
        this.clickPlay = clickPlay;
    }

    /**
     * To tick the buttons on the main menu
     */
    @Override
    public void tick() {

        //active status of the main menu
        if (active) {
            for (int i = 0; i < buttons.size(); i++) {
                if (buttons.get(i).hasMouse(game.getMouseManager().getX(), game.getMouseManager().getY())) {
                    //if the mouse is over the button
                    buttons.get(i).setActive(true);
                    //if left click change mouse status
                    if (game.getMouseManager().isLeft()) {
                        buttons.get(i).setPressed(true);
                        game.getMouseManager().setLeft(false);
                    }
                } else {
                    buttons.get(i).setActive(false);
                }
                if (buttons.get(0).isPressed()) {
                    setClickPlay(true);
                    setActive(false);
                }
            }
        }
    }

    /**
     * To render the main menu
     *
     * @param g
     */

    @Override
    public void render(Graphics g) {
        // if the main menu is active
        if (active && !buttons.get(0).isActive() && !buttons.get(1).isActive()) {
            g.drawImage(Assets.start, 0, 0, 1000, 700, null);
        } else if (active && buttons.get(0).isActive()) {
            g.drawImage(Assets.startPlay, 0, 0, 1000, 700, null);
        } else if (active && buttons.get(1).isActive()) {
            g.drawImage(Assets.startInstructions, 0, 0, 1000, 700, null);
        }
    }

}
