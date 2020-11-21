package dk.events.a6.android;

import java.util.ArrayList;
import java.util.List;

import dk.events.entities.ImageDetails;

public class CreateEventViewModel {
    public String title = "";
    public String description = "";
    public String ownerFirstName = "";
    public String ownerLastName = "";
    public List<ImageDetails> createEventImages = new ArrayList<dk.events.entities.ImageDetails>();
}
