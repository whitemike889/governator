/*
 * Copyright 2013 Netflix, Inc.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package com.netflix.governator.lifecycle.mocks;

import com.netflix.governator.annotations.Configuration;
import com.netflix.governator.annotations.PreConfiguration;
import com.netflix.governator.configuration.CompositeConfigurationProvider;
import com.netflix.governator.configuration.PropertiesConfigurationProvider;
import org.testng.Assert;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.Properties;

public class PreConfigurationChange
{
    private final CompositeConfigurationProvider configurationProvider;

    @Configuration("pre-config-test")
    private String value = "default";

    @Inject
    public PreConfigurationChange(CompositeConfigurationProvider configurationProvider)
    {
        this.configurationProvider = configurationProvider;
    }

    @PreConfiguration
    public void preConfiguration()
    {
        Assert.assertEquals(value, "default");

        Properties override = new Properties();
        override.setProperty("pre-config-test", "override");
        configurationProvider.add(new PropertiesConfigurationProvider(override));
    }

    @PostConstruct
    public void postConstruct()
    {
        Assert.assertEquals(value, "override");
    }
}
