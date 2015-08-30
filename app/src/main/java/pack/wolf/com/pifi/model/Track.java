package pack.wolf.com.pifi.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Whitney Champion on 8/29/15.
 */
public class Track implements Serializable {

    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPopularity() {
        return popularity;
    }

    public void setPopularity(String popularity) {
        this.popularity = popularity;
    }

    private String name;

    private String popularity;

    private Album album;

    private List<Artist> artists;

    public Album getAlbum() {
        return album;
    }

    public void setAlbum(Album album) {
        this.album = album;
    }

    public List<Artist> getArtists() {
        if (artists == null) {
            artists = new ArrayList<>();
        }
        return artists;
    }

    public void setArtists(List<Artist> artists) {
        this.artists = artists;
    }



}
