package sample.stage.geoUtils;

import com.byteowls.jopencage.JOpenCageGeocoder;
import com.byteowls.jopencage.model.JOpenCageForwardRequest;
import com.byteowls.jopencage.model.JOpenCageLatLng;
import com.byteowls.jopencage.model.JOpenCageResponse;
import com.github.davidmoten.geo.GeoHash;

/**
 * The type Geo hash handler.
 */
public class GeoHashHandler {

    private String GEO_API_KEY = "d0cdad7025b142e1b2d538fae9edb12c";
    private JOpenCageGeocoder jOpenCageGeocoder;

    /**
     * Gets geohash.
     *
     * @param latitude  the latitude
     * @param longitude the longitude
     * @param length    the length
     * @return the geohash
     */
    public String getGeohash(String latitude, String longitude, int length) {
        return GeoHash.encodeHash(Double.valueOf(latitude), Double.valueOf(longitude), length);
    }

    /**
     * Gets longitude.
     *
     * @param address the address
     * @return the longitude
     */
    public Double getLongitude(String address) {
        return getGeometryByAddress(address).getLng();
    }

    /**
     * Gets latitude.
     *
     * @param address the address
     * @return the latitude
     */
    public Double getLatitude(String address) {
        System.out.println("........................................");
        System.out.println(address);
        System.out.println(getGeometryByAddress(address));
        System.out.println("........................................");
        return getGeometryByAddress(address).getLat();
    }

    /**
     * Gets geometry by address.
     *
     * @param address the address
     * @return the geometry by address
     */
    public JOpenCageLatLng getGeometryByAddress(String address) {
        jOpenCageGeocoder = GeoCoderSingleton.getGeoCoder(GEO_API_KEY);
        JOpenCageForwardRequest request = new JOpenCageForwardRequest(address);
        request.setMinConfidence(1);
        request.setNoAnnotations(false);
        request.setNoDedupe(true);
        JOpenCageResponse response = jOpenCageGeocoder.forward(request);
        return response.getResults().get(0).getGeometry();
    }
}
