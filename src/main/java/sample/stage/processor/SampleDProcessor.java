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

import com.streamsets.pipeline.api.ConfigDef;
import com.streamsets.pipeline.api.GenerateResourceBundle;
import com.streamsets.pipeline.api.StageDef;

/**
 * The type Sample d processor.
 */
@StageDef(
        version = 1,
        label = "Geo Processor",
        description = "",
        icon = "default.png",
        onlineHelpRefUrl = ""
)

@GenerateResourceBundle
public class SampleDProcessor extends SampleProcessor {

    /**
     * The Geohash length.
     */
    @ConfigDef(
            required = true,
            type = ConfigDef.Type.NUMBER,
            defaultValue = "4",
            label = "Geohash number length"
    )
    public int geohashLength;

    @Override
    public int getGeohashLength() {
        return geohashLength;
    }
}
