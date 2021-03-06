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

package org.jclouds.virtualbox.functions;

import static com.google.common.base.Preconditions.checkNotNull;
import static org.jclouds.compute.util.ComputeServiceUtils.parseOsFamilyOrUnrecognized;
import static org.jclouds.compute.util.ComputeServiceUtils.parseVersionOrReturnEmptyString;

import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.jclouds.compute.domain.Image;
import org.jclouds.compute.domain.ImageBuilder;
import org.jclouds.compute.domain.OperatingSystem;
import org.jclouds.compute.domain.OsFamily;
import org.jclouds.javax.annotation.Nullable;
import org.virtualbox_4_1.IGuestOSType;
import org.virtualbox_4_1.IMachine;
import org.virtualbox_4_1.VirtualBoxManager;

import com.google.common.base.Function;

@Singleton
public class IMachineToImage implements Function<IMachine, Image> {

   private final VirtualBoxManager virtualboxManager;
   private final Map<OsFamily, Map<String, String>> osVersionMap;

   @Inject
   public IMachineToImage(VirtualBoxManager virtualboxManager, Map<OsFamily, Map<String, String>> osVersionMap) {
      this.virtualboxManager = checkNotNull(virtualboxManager, "virtualboxManager");
      this.osVersionMap = checkNotNull(osVersionMap, "osVersionMap");
   }

   @Override
   public Image apply(@Nullable IMachine from) {
      if (from == null)
         return null;

      IGuestOSType guestOSType = virtualboxManager.getVBox().getGuestOSType(from.getOSTypeId());
      OsFamily family = parseOsFamilyOrUnrecognized(guestOSType.getDescription());
      String version = parseVersionOrReturnEmptyString(family, guestOSType.getDescription(), osVersionMap);
      OperatingSystem os = OperatingSystem.builder().description(guestOSType.getDescription()).family(family)
            .version(version).is64Bit(guestOSType.getIs64Bit()).build();

      return new ImageBuilder().id("" + from.getId()).description(from.getDescription()).operatingSystem(os).build();
   }

}
