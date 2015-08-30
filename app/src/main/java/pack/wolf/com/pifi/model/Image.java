package pack.wolf.com.pifi.model;

import java.io.Serializable;

/**
 * Created by ryanmoore on 8/29/15.
 */
public class Image implements Serializable {
    Integer height;
    Integer width;
    String url;

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
