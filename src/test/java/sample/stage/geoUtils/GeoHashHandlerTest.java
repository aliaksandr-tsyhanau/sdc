package sample.stage.geoUtils;

import com.byteowls.jopencage.model.JOpenCageLatLng;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
public class GeoHashHandlerTest {

    @Spy
    private GeoHashHandler geoHashHandler;
    private JOpenCageLatLng latLng;
    private static final Double LATITUDE = 00.00;
    private static final Double LONGITUDE = 99.99;
    private static final String GEOHASH = "w0p0";
    private static final int HASH_LENGTH = 4;

    @BeforeEach
    public void init() {
        latLng = new JOpenCageLatLng();
        latLng.setLat(LATITUDE);
        latLng.setLng(LONGITUDE);
    }

    @Test
    public void testGetGeohash() {
        assertEquals(GEOHASH, geoHashHandler.getGeohash(LATITUDE.toString(), LONGITUDE.toString(), HASH_LENGTH));
    }

    @Test
    public void testGetLatitude() {
        doReturn(latLng).when(geoHashHandler).getGeometryByAddress(anyString());
        assertEquals(LATITUDE, geoHashHandler.getLatitude(anyString()));
    }

    @Test
    public void testGetLongitude() {
        doReturn(latLng).when(geoHashHandler).getGeometryByAddress(anyString());
        assertEquals(LONGITUDE, geoHashHandler.getLongitude(anyString()));
    }
}
