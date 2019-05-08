package ch.heigvd.pro.a03.event.player;

import ch.heigvd.pro.a03.event.Event;

import java.io.Serializable;

public abstract class PlayerEvent extends Event implements Serializable {
    int entityId;

    public PlayerEvent(int entityId) {
        this.entityId = entityId;
    }

    @Override
    public String toString() {
        return "PlayerEvent{" +
                "entityId=" + entityId +
                '}';
    }
}
