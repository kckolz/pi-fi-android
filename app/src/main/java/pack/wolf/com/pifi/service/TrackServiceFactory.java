package pack.wolf.com.pifi.service;

import pack.wolf.com.pifi.service.api.TrackService;
import pack.wolf.com.pifi.service.impl.TrackServiceImpl;

/**
 * Created by ryanmoore on 8/29/15.
 */
public class TrackServiceFactory {
    private static TrackService trackService;

    public static TrackService getInstance() {

        if (trackService == null) {
            trackService = new TrackServiceImpl();
        }

        return trackService;
    }
}