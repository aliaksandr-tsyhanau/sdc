package sample.stage.geoUtils;

import com.byteowls.jopencage.JOpenCageGeocoder;

/**
 * The type Geo coder singleton.
 */
public class GeoCoderSingleton {

    private static JOpenCageGeocoder INSTANCE;

    private GeoCoderSingleton() {
    }

    /**
     * Gets geo coder.
     *
     * @param geoApiKey the geo api key
     * @return the geo coder
     */
    public static JOpenCageGeocoder getGeoCoder(String geoApiKey) {
        if (INSTANCE == null) {
            INSTANCE = createGeoCoder(geoApiKey);
        }
        return INSTANCE;
    }

    private static JOpenCageGeocoder createGeoCoder(String geoApiKey) {
        return new JOpenCageGeocoder(geoApiKey);
    }
}
