package screen;

import java.awt.event.KeyEvent;

import engine.SoundEffect;
import engine.Cooldown;
import engine.Core;

public class ModeScreen extends Screen {

    /** Milliseconds between changes in user selection. */
    private static final int SELECTION_TIME = 200;

    /** Time between changes in user selection. */
    private Cooldown selectionCooldown;
    private int mode;
    /** For selection moving sound */
    private SoundEffect soundEffect;

    /**
     * Constructor, establishes the properties of the screen.
     *
     * @param width
     *            Screen width.
     * @param height
     *            Screen height.
     * @param fps
     *            Frames per second, frame rate at which the game is run.
     */
    public ModeScreen(final int width, final int height, final int fps, final int m){
        super(width, height, fps);

        // Defaults to EASY play.
        mode = m;
        this.selectionCooldown = Core.getCooldown(SELECTION_TIME);
        this.selectionCooldown.reset();

        soundEffect = new SoundEffect();
    }

    /**
     * Starts the action.
     *
     * @return Next screen code.
     */
    public final int run() {
        super.run();

        return this.mode;
    }


    /**
     * Updates the elements on screen and checks for events.
     */
    protected final void update() {
        super.update();

        draw();
        if (this.selectionCooldown.checkFinished()
                && this.inputDelay.checkFinished()) {
            if (inputManager.isKeyDown(KeyEvent.VK_UP)
                    || inputManager.isKeyDown(KeyEvent.VK_W)) {
                soundEffect.playButtonClickSound();
                previousMenuItem();
                this.selectionCooldown.reset();
            }
            if (inputManager.isKeyDown(KeyEvent.VK_DOWN)
                    || inputManager.isKeyDown(KeyEvent.VK_S)) {
                soundEffect.playButtonClickSound();
                nextMenuItem();
                this.selectionCooldown.reset();
            }
            if (inputManager.isKeyDown(KeyEvent.VK_SPACE)) {
                soundEffect.playSpaceButtonSound();
                this.isRunning = false;
            }
        }
    }

    /**
     * Shifts the focus to the next menu item.
     */
    private void nextMenuItem() {
        if (this.mode == 0)
            this.mode = 1;
        else if (this.mode == 1)
            this.mode = 2;
        else if (this.mode == 2)
            this.mode = 3;
        else if (this.mode == 3)
            this.mode = 4;
        else
            this.mode = 0;
    }

    /**
     * Shifts the focus to the previous menu item.
     */
    private void previousMenuItem() {
        if (this.mode == 0)
            this.mode = 4;
        else if (this.mode == 1)
            this.mode = 0;
        else if (this.mode == 2)
            this.mode = 1;
        else if (this.mode == 3)
            this.mode = 2;
        else this.mode = 3;

    }

    /**
     * Draws the elements associated with the screen.
     */
    private void draw() {
        drawManager.initDrawing(this);

        drawManager.modeSelect(this, this.mode);

        drawManager.completeDrawing(this);
    }
}
