package models;

public class Pet {

//    1. Create and return a new Pet with: a. Id b. Category_name c. Pet_name d. Status e. tagName f. photoUrl
//    even though status was not in the above list like outlined in the doc I kept it as to line with json response


    private int id;
    private Category category;
    private String name;
    private String[] photoUrls;
    private Tag[] tags;
    private String status;


    public Pet(int id, Category category, String name, String[] photoUrls, Tag[] tags, String status) {
        this.id = id;
        this.category = category;
        this.name = name;
        this.photoUrls = photoUrls;
        this.tags = tags;
        this.status = status;
    }

    public int getID() {
        return id;
    }

    public Category getCategory() {
        return category;
    }

    public String getName() {
        return name;
    }

    public String[] getPhotoUrls() {
        return photoUrls;
    }

    public Tag[] getTags() {
        return tags;
    }

    public  String getStatus() {
        return status;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Pet createPreDefinedPet(){
        Category category = new Category(1,"albinoDogs");
        String [] urls = new String[]{"https://www.dogs.com"};
        Tag[] tags = new Tag[] {new Tag(1,"#Dog")};
        return new Pet(1, category, "Ted", urls, tags, "available");

    }

}
