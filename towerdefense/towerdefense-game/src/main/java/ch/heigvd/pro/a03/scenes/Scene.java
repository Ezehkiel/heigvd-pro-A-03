package ch.heigvd.pro.a03.scenes;

public abstract class Scene {

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
     * Dispose memory
     */
    public abstract void dispose();
}
