package ch.heigvd.pro.a03.scenes;

public abstract class Scene {

    /**
     * Run when entering the scene
     */
    public abstract void enter();

    /**
     * Run when leaving the scene
     */
    public abstract void leave();

    /**
     * Update its elements
     * @param deltaTime time passed since last update
     */
    public abstract void update(float deltaTime);

    /**
     * Draws its elements on the screen
     */
    public abstract void draw();

    /**
     * Called when the window is resized
     * @param width new width
     * @param height new height
     */
    public abstract void resize(int width, int height);

    /**
     * Dispose memory
     */
    public abstract void dispose();
}
