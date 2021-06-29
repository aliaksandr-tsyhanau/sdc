package sample.stage.processor;

import com.byteowls.jopencage.model.JOpenCageLatLng;
import com.streamsets.pipeline.api.Field;
import com.streamsets.pipeline.api.Record;
import com.streamsets.pipeline.api.StageException;
import com.streamsets.pipeline.sdk.ProcessorRunner;
import com.streamsets.pipeline.sdk.RecordCreator;
import com.streamsets.pipeline.sdk.StageRunner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class SampleProcessorTest {

    private JOpenCageLatLng latLng;
    private ProcessorRunner runner;
    private SampleDProcessor sp;
    private Record record;
    private static final Double LATITUDE = 00.00;
    private static final Double LONGITUDE = 99.99;
    private static final String GEOHASH = "w0p0";

    @BeforeEach
    void init() {
        sp = new SampleDProcessor();
        runner = new ProcessorRunner.Builder(SampleDProcessor.class, sp)
                .addConfiguration("geohashLength", 4)
                .addOutputLane("a")
                .build();

        runner.runInit();

        record = RecordCreator.create();
        record.set(Field.create(new HashMap<>()));
    }

    @Test
    void normalProcess() throws StageException {

        record.set("/Id", Field.create("123"));
        record.set("/Longitude", Field.create(LONGITUDE.toString()));
        record.set("/Latitude", Field.create(LATITUDE.toString()));
        StageRunner.Output output = runner.runProcess(Arrays.asList(record));
        assertEquals(1, output.getRecords().size());
        assertEquals("123", output.getRecords().get("a").get(0).get("/Id").getValueAsString());
        assertEquals(LONGITUDE, output.getRecords().get("a").get(0).get("/Longitude").getValueAsDouble());
        assertEquals(LATITUDE, output.getRecords().get("a").get(0).get("/Latitude").getValueAsDouble());
        assertEquals(GEOHASH, output.getRecords().get("a").get(0).get("/GeoHash").getValueAsString());

        runner.runDestroy();
    }

    @Test
    void processRecordWithEmptyValues() throws StageException {

        record.set("/Id", Field.create("123"));
        record.set("/Longitude", Field.create(""));
        record.set("/Latitude", Field.create(""));
        record.set("/Address", Field.create("Opolska 10"));
        record.set("/City", Field.create("Krakow"));
        StageRunner.Output output = runner.runProcess(Arrays.asList(record));
        assertEquals(1, output.getRecords().size());
        assertEquals("123", output.getRecords().get("a").get(0).get("/Id").getValueAsString());
        assertNotNull(output.getRecords().get("a").get(0).get("/Longitude").getValueAsDouble());
        assertNotNull(output.getRecords().get("a").get(0).get("/Latitude").getValueAsDouble());
        assertNotNull(output.getRecords().get("a").get(0).get("/GeoHash").getValueAsString());

        runner.runDestroy();
    }

    @Test
    void getAddress() {
        record.set("/Address", Field.create("Opolska 10"));
        record.set("/City", Field.create("Krakow"));
        assertEquals("Opolska 10, Krakow", sp.getAddress(record));
    }
}
