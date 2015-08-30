package pack.wolf.com.pifi.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ryanmoore on 8/29/15.
 */
public class Album implements Serializable {

    List<Image> images;
    String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Image> getImages() {
        if (images == null) {
            images = new ArrayList<>();
        }
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }
}
