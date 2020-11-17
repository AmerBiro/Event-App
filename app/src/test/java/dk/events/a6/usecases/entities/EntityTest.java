package dk.events.a6.usecases.entities;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class EntityTest {

    @Test
    public void givenTwoDifferentEntities_returnEntitiesAreNotTheSame(){
        Entity entity = Entity.newEntityBuilder().withId("EntityId").build();
        Entity other = Entity.newEntityBuilder().withId("otherEntityId").build();

        assertEquals(false, entity.isSame(other));
    }

    @Test
    public void givenEntityComparedWithSelf_returnEntityIsTheSame(){
        Entity entity = Entity.newEntityBuilder().withId("EntityId").build();

        assertEquals(true, entity.isSame(entity));
    }

    @Test
    public void givenTwoEntitiesWithTheSameId_returnEntitiesAreTheSame(){
        final String entityID = "EntityID";
        Entity entity = Entity.newEntityBuilder().withId(entityID).build();
        Entity otherEntity = Entity.newEntityBuilder().withId(entityID).build();

        assertEquals(true, entity.isSame(otherEntity));
    }

    @Test
    public void givenTwoEntitiesWithNullId_returnEntitiesAreNotTheSame(){
        Entity entity = Entity.newEntityBuilder().build();
        Entity otherEntity = Entity.newEntityBuilder().build();

        assertEquals(false, entity.isSame(otherEntity));
    }
}
