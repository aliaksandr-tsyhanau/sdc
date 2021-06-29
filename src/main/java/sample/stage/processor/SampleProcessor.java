/**
 * Copyright 2015 StreamSets Inc.
 * <p>
 * Licensed under the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package sample.stage.processor;

import com.streamsets.pipeline.api.Field;
import com.streamsets.pipeline.api.Record;
import com.streamsets.pipeline.api.StageException;
import com.streamsets.pipeline.api.base.SingleLaneRecordProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sample.stage.geoUtils.GeoHashHandler;

import java.util.List;

/**
 * The type Sample processor.
 */
public abstract class SampleProcessor extends SingleLaneRecordProcessor {

    private static final Logger LOG = LoggerFactory.getLogger(SampleProcessor.class);
    /**
     * The Geo hash handler.
     */
    public GeoHashHandler geoHashHandler;

    /**
     * Gets geohash length.
     *
     * @return the geohash length
     */
    public abstract int getGeohashLength();

    @Override
    protected List<ConfigIssue> init() {
        // Validate configuration values and open any required resources.
        List<ConfigIssue> issues = super.init();

        geoHashHandler = new GeoHashHandler();

        // If issues is not empty, the UI will inform the user of each configuration issue in the list.
        return issues;
    }

    @Override
    public void destroy() {
        // Clean up any open resources.
        super.destroy();
    }

    protected void process(Record record, SingleLaneBatchMaker batchMaker) throws StageException {
        LOG.info("Input record: {}", record);

        try {
            String latitude = record.get("/Latitude").getValueAsString();
            String longitude = record.get("/Longitude").getValueAsString();
            if (latitude.isEmpty()) {
                latitude = geoHashHandler.getLatitude(getAddress(record)).toString();
                record.set("/Latitude", Field.create(latitude));
            }
            if (longitude.isEmpty()) {
                longitude = geoHashHandler.getLongitude(getAddress(record)).toString();
                record.set("/Longitude", Field.create(longitude));
            }
            String geoHash = geoHashHandler.getGeohash(latitude, longitude, getGeohashLength());
            record.set("/GeoHash", Field.create(geoHash));


        } catch (Exception e) {
            String id = record.get("/Id").getValueAsString();
            LOG.info("Exception getting data from record with Id {}", id, e);
        }

        LOG.info("Output record: {}", record);

        batchMaker.addRecord(record);
    }

    /**
     * Gets address.
     *
     * @param record the record
     * @return the address
     */
    protected String getAddress(Record record) {
        String address = record.get("/Address").getValueAsString();
        String city = record.get("/City").getValueAsString();
        return address + ", " + city;
    }
}
