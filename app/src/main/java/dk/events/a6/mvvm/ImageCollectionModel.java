package dk.events.a6.mvvm;


public class ImageCollectionModel {

    private String image_url;
    private int number;

    public ImageCollectionModel(String image_url, int number) {
        this.image_url = image_url;
        this.number = number;
    }

    public ImageCollectionModel() {
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
