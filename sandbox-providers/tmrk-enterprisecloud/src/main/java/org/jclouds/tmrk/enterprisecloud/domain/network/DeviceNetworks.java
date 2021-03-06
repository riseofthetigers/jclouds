/**
 * Licensed to jclouds, Inc. (jclouds) under one or more
 * contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  jclouds licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.jclouds.tmrk.enterprisecloud.domain.network;

import com.google.common.collect.Sets;

import javax.xml.bind.annotation.XmlElement;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Container for DeviceNetwork items
 * <xs:complexType name="DeviceNetworks">
 * @author Jason King
 */
public class DeviceNetworks {

    private LinkedHashSet<DeviceNetwork> deviceNetworks = Sets.newLinkedHashSet();

    protected DeviceNetworks() {
        //For JAXB
    }

    @XmlElement(name = "Network")
    void setDeviceNetwork(DeviceNetwork deviceNetwork) {
        checkNotNull(deviceNetwork,"deviceNetwork");
        this.deviceNetworks.add(deviceNetwork);
    }

    public Set<DeviceNetwork> getDeviceNetworks() {
        return Collections.unmodifiableSet(deviceNetworks);
    }
    
    @Override
    public String toString() {
        return "[deviceNetworks="+deviceNetworks+"]";
    }
}