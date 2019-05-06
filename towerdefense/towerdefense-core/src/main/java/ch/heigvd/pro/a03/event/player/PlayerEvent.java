package ch.heigvd.pro.a03.event.player;

import java.io.Serializable;

public abstract class PlayerEvent implements Serializable {
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
