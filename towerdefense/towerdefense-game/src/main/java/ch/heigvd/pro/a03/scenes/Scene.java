package ch.heigvd.pro.a03.scenes;

import ch.heigvd.pro.a03.GameLauncher;
import ch.heigvd.pro.a03.utils.UI;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Represents a scene.
 */
public abstract class Scene {

    private Viewport viewport;
    private Skin skin;
    private Stage stage;

    /**
     * Creates a new scene
     */
    public Scene() {
        viewport = new FitViewport(GameLauncher.WIDTH, GameLauncher.HEIGHT);
        viewport.update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        skin = UI.createSkin();
        stage = new Stage(viewport);
    }

    /**
     * Run when entering the scene. Set input processor to the stage
     */
    public void enter() {
        Gdx.input.setInputProcessor(stage);
    }

    /**
     * Run when leaving the scene. Remove input processor.
     */
    public void leave() {
        Gdx.input.setInputProcessor(null);
    }

    /**
     * Update its elements. Updates the stage
     * @param deltaTime time passed since last update
     */
    public void update(float deltaTime) {
        stage.act(deltaTime);
    }

    /**
     * Draws its elements on the screen. Draws the stage.
     */
    public void draw(){
        viewport.apply();
        stage.draw();
    }

    /**
     * Called when the window is resized. Resize the viewport and center it.
     * @param width new width
     * @param height new height
     */
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    /**
     * Dispose memory
     */
    public void dispose() {
        stage.dispose();
        skin.dispose();
    }

    /**
     * Changes the viewport.
     * @param viewport new viewport
     */
    public void setViewport(Viewport viewport) {
        this.viewport = viewport;
    }

    /**
     * Gets the view port.
     * @return the viewport
     */
    public Viewport getViewport() {
        return viewport;
    }

    /**
     * Gets the skin.
     * @return the skin
     */
    public Skin getSkin() {
        return skin;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    /**
     * Gets the stage.
     * @return the stage
     */
    public Stage getStage() {
        return stage;
    }
}
