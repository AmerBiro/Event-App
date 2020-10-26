package dk.events.a6.models;

public class MyEventViewModel {
    public int imageDrawableId;
    public String title = "default title";
    public String description = "default description";

    public static class Builder{
        private int imageDrawableId;
        private String title = "default title";
        private String description = "default description";

        private Builder(){}

        public Builder withImageDrawableId(int imageDrawableId){
            this.imageDrawableId = imageDrawableId;
            return this;
        }

        public static Builder newInstance(){
            return new Builder();
        }

        public Builder withTitle(String title){
            this.title = title;
            return this;
        }
        public Builder withDescription(String description){
            this.description = description;
            return this;
        }

        public MyEventViewModel build(){
            MyEventViewModel model = new MyEventViewModel();
            model.imageDrawableId = this.imageDrawableId;
            model.title = this.title;
            model.description = this.description;
            return model;
        }

    }

}
