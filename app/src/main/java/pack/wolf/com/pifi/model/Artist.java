package pack.wolf.com.pifi.model;

import java.io.Serializable;

/**
 * Created by ryanmoore on 8/29/15.
 */
public class Artist implements Serializable {

    String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
