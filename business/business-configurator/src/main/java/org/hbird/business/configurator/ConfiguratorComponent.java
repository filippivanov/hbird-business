/**
 * Licensed to the Hummingbird Foundation (HF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The HF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.hbird.business.configurator;

import java.util.List;

import org.hbird.business.core.StartableEntity;
import org.hbird.exchange.configurator.StartComponent;
import org.hbird.exchange.configurator.StopComponent;
import org.hbird.exchange.core.Command;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * 
 * Component for starting other components.
 * 
 * @author Gert Villemos
 * 
 */
public class ConfiguratorComponent extends StartableEntity implements ApplicationContextAware {

    /**
	 * 
	 */
    private static final long serialVersionUID = 2865072999875952015L;

    public static final String DEFAULT_ID = "Configurator";
    public static final String DEFAULT_NAME = "Configurator";
    public static final String DEFAULT_DESCRIPTION = "A component for starting other components.";
    public static final String DEFAULT_DRIVER = ConfiguratorComponentDriver.class.getName();

    protected ApplicationContext applicationContext;

    /**
     * Default constructor.
     */
    public ConfiguratorComponent() {
        super(DEFAULT_ID, DEFAULT_NAME);
        setDescription(DEFAULT_DESCRIPTION);
        setDriverName(DEFAULT_DRIVER);
    }

    /**
     * Default constructor.
     */
    public ConfiguratorComponent(String ID) {
        super(ID, DEFAULT_NAME);
        setDescription(DEFAULT_DESCRIPTION);
        setDriverName(DEFAULT_DRIVER);
    }

    /**
     * @see org.springframework.context.ApplicationContextAware#setApplicationContext(org.springframework.context.
     *      ApplicationContext)
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    /**
     * @see org.hbird.business.core.StartableEntity#createCommandList()
     */
    @Override
    protected List<Command> createCommandList(List<Command> commands) {
        commands.add(new StartComponent(""));
        commands.add(new StopComponent(""));
        return commands;
    }

    /**
     * Initialization method.
     * 
     * Typically called from the Spring XML file, using the init-method attribute.
     * 
     * @throws Exception
     */
    public void start() throws Exception {
        ConfiguratorComponentDriver driver = new ConfiguratorComponentDriver(applicationContext);
        driver.start(this);
    }
}
