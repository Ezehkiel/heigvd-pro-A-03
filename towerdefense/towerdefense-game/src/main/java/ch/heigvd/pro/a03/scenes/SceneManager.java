package ch.heigvd.pro.a03.scenes;

import java.util.Stack;

/**
 * Manage scenes. You can add or remove scenes or get the current scene to display
 */
public class SceneManager {

    private Stack<Scene> scenes;

    /**
     * Create an empty scene manager. Manages scenes with a stack.
     */
    public SceneManager() {
        scenes = new Stack<>();
    }

    /**
     * Add a new scene on top. The ne scene will be the one to display.
     * Calls leave() on the current scene and enter() on the new scene.
     * @param scene Scene to display
     */
    public void add(Scene scene) {

        // Call leave on current scene
        if (hasScene()) {
            scenes.peek().leave();
        }

        scenes.add(scene);

        // Call enter on new scene
        scenes.peek().enter();
    }

    /**
     * Remove the top scene. The scene under the top scene will become the top one.
     * Calls leave() and dispose() on the current scene and enter() on the new top one.
     * Nothing happens if there are no mor scenes.
     */
    public void pop() {

        if (hasScene()) {

            // call leave on current scene
            scenes.peek().leave();

            scenes.peek().dispose();
            scenes.pop();

            if (hasScene()) {
                // Call enter on new scene
                scenes.peek().enter();
            }
        }
    }

    /**
     * Check is there is still at least one scene.
     * @return Boolean
     */
    public boolean hasScene() {
        return !scenes.empty();
    }

    /**
     * Get the top scene.
     * @return The top scene or null.
     */
    public Scene peek() {
        return hasScene() ? scenes.peek() : null;
    }
}
