package dk.events.a6.usecases.entities;

import java.util.Objects;

public class Entity {
    protected String id;

    public boolean isSame(Entity entity) {
        return id != null && Objects.equals(id, entity.id);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
