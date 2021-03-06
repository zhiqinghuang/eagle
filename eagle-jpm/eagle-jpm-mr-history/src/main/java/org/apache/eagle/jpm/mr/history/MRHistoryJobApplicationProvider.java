/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.eagle.jpm.mr.history;

import com.codahale.metrics.health.HealthCheck;
import com.google.common.util.concurrent.Service;
import com.typesafe.config.Config;
import io.dropwizard.lifecycle.Managed;
import org.apache.eagle.app.service.ApplicationListener;
import org.apache.eagle.app.spi.AbstractApplicationProvider;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class MRHistoryJobApplicationProvider extends AbstractApplicationProvider<MRHistoryJobApplication> {
    @Override
    public MRHistoryJobApplication getApplication() {
        return new MRHistoryJobApplication();
    }

    @Override
    public Optional<ApplicationListener> getApplicationListener() {
        return Optional.of(new MRHistoryJobApplicationListener());
    }

    @Override
    public Optional<HealthCheck> getManagedHealthCheck(Config config) {
        return Optional.of(new MRHistoryJobApplicationHealthCheck(config));
    }

    @Override
    public Optional<List<Service>> getSharedServices(Config envConfig) {
        if (envConfig.hasPath(MRHistoryJobDailyReporter.SERVICE_PATH)) {
            return Optional.of(Collections.singletonList(new MRHistoryJobDailyReporter(envConfig)));
        } else {
            return Optional.empty();
        }
    }
}