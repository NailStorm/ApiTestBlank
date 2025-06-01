package jsonHelpers;
import java.util.ArrayList;
import java.util.List;

import static enumCollection.petStatus.*;

public class Pet {
    private int id;
    private Category category = new Category();
    private String name;
    private String status = AVAILABLE.getTitle();
    private List<String> photoUrls = new ArrayList<>();
    private List<Tags> tags = new ArrayList<>();

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public Category getCategory() { return category; }
    public void setCategory(Category category) { this.category = category; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public List<String> getPhotoUrls() { return photoUrls; }
    public void setPhotoUrls(List<String> photoUrls) {
        this.photoUrls = photoUrls != null ? photoUrls : new ArrayList<>();
    }

    public List<Tags> getTags() { return tags; }
    public void setTags(List<Tags> tags) {
        this.tags = tags != null ? tags : new ArrayList<>();
    }

    public static class Category {
        private int id = 0;
        private String name = "Category 0";

        public int getId() { return id; }
        public void setId(int id) { this.id = id; }

        public String getName() { return name; }
        public void setName(String name) { this.name = name != null ? name : "Category 0"; }
    }

    public static class Tags {
        private int id = 0;
        private String name = "Tag 0";

        public int getId() { return id; }
        public void setId(int id) { this.id = id; }

        public String getName() { return name; }
        public void setName(String name) { this.name = name != null ? name : "Untagged"; }
    }
}

