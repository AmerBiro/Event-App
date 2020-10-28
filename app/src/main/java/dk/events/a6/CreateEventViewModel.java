package dk.events.a6;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import dk.events.a6.usecases.entities.ImageDetails;

public class CreateEventViewModel {
    public String title = "";
    public String description = "";
    public String ownerFirstName = "";
    public String ownerLastName = "";
    public List<ImageDetails> createEventImages = new ArrayList<>();
}
