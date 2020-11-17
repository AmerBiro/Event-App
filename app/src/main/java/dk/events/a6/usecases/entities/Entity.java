package dk.events.a6.usecases.entities;

import java.util.Objects;

public class Entity {
    protected String id;

    public static EntityBuilder newEntityBuilder(){
        return new EntityBuilder();
    }

    public static class EntityBuilder{
        private String id;
        private EntityBuilder(){}
        public Entity build(){
            Entity e = new Entity();
            e.setId(this.id);
            return e;
        }
        public EntityBuilder withId(String id){
            this.id = id;
            return this;
        }
    }

    @Override
    public String toString() {
        return "Entity{" +
                "id='" + id + '\'' +
                '}';
    }

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
