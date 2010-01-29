/**
 *
 * Copyright (C) 2009 Cloud Conscious, LLC. <info@cloudconscious.com>
 *
 * ====================================================================
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * ====================================================================
 */
package org.jclouds.aws.ec2.services;

import static org.jclouds.aws.ec2.options.DescribeSnapshotsOptions.Builder.ownedBy;
import static org.jclouds.aws.ec2.options.DetachVolumeOptions.Builder.fromInstance;
import static org.testng.Assert.assertEquals;

import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.net.URI;
import java.util.Map;

import javax.inject.Singleton;

import org.jclouds.aws.domain.Region;
import org.jclouds.aws.ec2.EC2;
import org.jclouds.aws.ec2.domain.AvailabilityZone;
import org.jclouds.aws.ec2.functions.ReturnVoidOnVolumeAvailable;
import org.jclouds.aws.ec2.options.CreateSnapshotOptions;
import org.jclouds.aws.ec2.options.DescribeSnapshotsOptions;
import org.jclouds.aws.ec2.options.DetachVolumeOptions;
import org.jclouds.aws.ec2.xml.AttachmentHandler;
import org.jclouds.aws.ec2.xml.CreateVolumeResponseHandler;
import org.jclouds.aws.ec2.xml.DescribeSnapshotsResponseHandler;
import org.jclouds.aws.ec2.xml.DescribeVolumesResponseHandler;
import org.jclouds.aws.ec2.xml.PermissionHandler;
import org.jclouds.aws.ec2.xml.SnapshotHandler;
import org.jclouds.aws.filters.FormSigner;
import org.jclouds.aws.reference.AWSConstants;
import org.jclouds.date.TimeStamp;
import org.jclouds.http.functions.ParseSax;
import org.jclouds.http.functions.CloseContentAndReturn;
import org.jclouds.logging.Logger;
import org.jclouds.logging.Logger.LoggerFactory;
import org.jclouds.rest.RestClientTest;
import org.jclouds.rest.internal.GeneratedHttpRequest;
import org.jclouds.rest.internal.RestAnnotationProcessor;
import org.jclouds.util.Jsr330;
import org.testng.annotations.Test;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.inject.AbstractModule;
import com.google.inject.Module;
import com.google.inject.Provides;
import com.google.inject.TypeLiteral;

/**
 * Tests behavior of {@code ElasticBlockStoreAsyncClient}
 * 
 * @author Adrian Cole
 */
@Test(groups = "unit", testName = "ec2.ElasticBlockStoreAsyncClientTest")
public class ElasticBlockStoreAsyncClientTest extends RestClientTest<ElasticBlockStoreAsyncClient> {

   public void testCreateVolume() throws SecurityException, NoSuchMethodException, IOException {
      Method method = ElasticBlockStoreAsyncClient.class.getMethod(
               "createVolumeInAvailabilityZone", AvailabilityZone.class, int.class);
      GeneratedHttpRequest<ElasticBlockStoreAsyncClient> httpMethod = processor.createRequest(
               method, AvailabilityZone.US_EAST_1A, 20);

      assertRequestLineEquals(httpMethod, "POST https://ec2.amazonaws.com/ HTTP/1.1");
      assertHeadersEqual(httpMethod,
               "Content-Length: 74\nContent-Type: application/x-www-form-urlencoded\nHost: ec2.amazonaws.com\n");
      assertPayloadEquals(httpMethod,
               "Version=2009-11-30&Action=CreateVolume&AvailabilityZone=us-east-1a&Size=20");

      assertResponseParserClassEquals(method, httpMethod, ParseSax.class);
      assertSaxResponseParserClassEquals(method, CreateVolumeResponseHandler.class);
      assertExceptionParserClassEquals(method, null);

      checkFilters(httpMethod);
   }

   public void testCreateVolumeFromSnapShot() throws SecurityException, NoSuchMethodException,
            IOException {
      Method method = ElasticBlockStoreAsyncClient.class.getMethod(
               "createVolumeFromSnapshotInAvailabilityZone", AvailabilityZone.class, String.class);
      GeneratedHttpRequest<ElasticBlockStoreAsyncClient> httpMethod = processor.createRequest(
               method, AvailabilityZone.US_EAST_1A, "snapshotId");

      assertRequestLineEquals(httpMethod, "POST https://ec2.amazonaws.com/ HTTP/1.1");
      assertHeadersEqual(httpMethod,
               "Content-Length: 88\nContent-Type: application/x-www-form-urlencoded\nHost: ec2.amazonaws.com\n");
      assertPayloadEquals(httpMethod,
               "Version=2009-11-30&Action=CreateVolume&AvailabilityZone=us-east-1a&SnapshotId=snapshotId");

      assertResponseParserClassEquals(method, httpMethod, ParseSax.class);
      assertSaxResponseParserClassEquals(method, CreateVolumeResponseHandler.class);
      assertExceptionParserClassEquals(method, null);

      checkFilters(httpMethod);
   }

   public void testDeleteVolume() throws SecurityException, NoSuchMethodException, IOException {
      Method method = ElasticBlockStoreAsyncClient.class.getMethod("deleteVolumeInRegion",
               Region.class, String.class);
      GeneratedHttpRequest<ElasticBlockStoreAsyncClient> httpMethod = processor.createRequest(
               method, Region.DEFAULT, "id");

      assertRequestLineEquals(httpMethod, "POST https://ec2.amazonaws.com/ HTTP/1.1");
      assertHeadersEqual(httpMethod,
               "Content-Length: 50\nContent-Type: application/x-www-form-urlencoded\nHost: ec2.amazonaws.com\n");
      assertPayloadEquals(httpMethod, "Version=2009-11-30&Action=DeleteVolume&VolumeId=id");

      assertResponseParserClassEquals(method, httpMethod, CloseContentAndReturn.class);
      assertSaxResponseParserClassEquals(method, null);
      assertExceptionParserClassEquals(method, null);

      checkFilters(httpMethod);
   }

   public void testDescribeVolumes() throws SecurityException, NoSuchMethodException, IOException {
      Method method = ElasticBlockStoreAsyncClient.class.getMethod("describeVolumesInRegion",
               Region.class, Array.newInstance(String.class, 0).getClass());
      GeneratedHttpRequest<ElasticBlockStoreAsyncClient> httpMethod = processor.createRequest(
               method, Region.DEFAULT);

      assertRequestLineEquals(httpMethod, "POST https://ec2.amazonaws.com/ HTTP/1.1");
      assertHeadersEqual(httpMethod,
               "Content-Length: 41\nContent-Type: application/x-www-form-urlencoded\nHost: ec2.amazonaws.com\n");
      assertPayloadEquals(httpMethod, "Version=2009-11-30&Action=DescribeVolumes");

      assertResponseParserClassEquals(method, httpMethod, ParseSax.class);
      assertSaxResponseParserClassEquals(method, DescribeVolumesResponseHandler.class);
      assertExceptionParserClassEquals(method, null);

      checkFilters(httpMethod);
   }

   public void testDescribeVolumesArgs() throws SecurityException, NoSuchMethodException,
            IOException {
      Method method = ElasticBlockStoreAsyncClient.class.getMethod("describeVolumesInRegion",
               Region.class, Array.newInstance(String.class, 0).getClass());
      GeneratedHttpRequest<ElasticBlockStoreAsyncClient> httpMethod = processor.createRequest(
               method, Region.DEFAULT, "1", "2");

      assertRequestLineEquals(httpMethod, "POST https://ec2.amazonaws.com/ HTTP/1.1");
      assertHeadersEqual(httpMethod,
               "Content-Length: 41\nContent-Type: application/x-www-form-urlencoded\nHost: ec2.amazonaws.com\n");
      assertPayloadEquals(httpMethod,
               "Version=2009-11-30&Action=DescribeVolumes&VolumeId.1=1&VolumeId.2=2");

      assertResponseParserClassEquals(method, httpMethod, ParseSax.class);
      assertSaxResponseParserClassEquals(method, DescribeVolumesResponseHandler.class);
      assertExceptionParserClassEquals(method, null);

      checkFilters(httpMethod);
   }

   public void testAttachVolume() throws SecurityException, NoSuchMethodException, IOException {
      Method method = ElasticBlockStoreAsyncClient.class.getMethod("attachVolumeInRegion",
               Region.class, String.class, String.class, String.class);
      GeneratedHttpRequest<ElasticBlockStoreAsyncClient> httpMethod = processor.createRequest(
               method, Region.DEFAULT, "id", "instanceId", "/device");

      assertRequestLineEquals(httpMethod, "POST https://ec2.amazonaws.com/ HTTP/1.1");
      assertHeadersEqual(httpMethod,
               "Content-Length: 89\nContent-Type: application/x-www-form-urlencoded\nHost: ec2.amazonaws.com\n");
      assertPayloadEquals(httpMethod,
               "Version=2009-11-30&Action=AttachVolume&InstanceId=instanceId&VolumeId=id&Device=%2Fdevice");

      assertResponseParserClassEquals(method, httpMethod, ParseSax.class);
      assertSaxResponseParserClassEquals(method, AttachmentHandler.class);
      assertExceptionParserClassEquals(method, null);

      checkFilters(httpMethod);
   }

   public void testDetachVolume() throws SecurityException, NoSuchMethodException, IOException {
      Method method = ElasticBlockStoreAsyncClient.class.getMethod("detachVolumeInRegion",
               Region.class, String.class, boolean.class, Array.newInstance(
                        DetachVolumeOptions.class, 0).getClass());
      GeneratedHttpRequest<ElasticBlockStoreAsyncClient> httpMethod = processor.createRequest(
               method, Region.DEFAULT, "id", false);

      assertRequestLineEquals(httpMethod, "POST https://ec2.amazonaws.com/ HTTP/1.1");
      assertHeadersEqual(httpMethod,
               "Content-Length: 62\nContent-Type: application/x-www-form-urlencoded\nHost: ec2.amazonaws.com\n");
      assertPayloadEquals(httpMethod,
               "Version=2009-11-30&Action=DetachVolume&Force=false&VolumeId=id");

      assertResponseParserClassEquals(method, httpMethod, CloseContentAndReturn.class);
      assertSaxResponseParserClassEquals(method, null);
      assertExceptionParserClassEquals(method, ReturnVoidOnVolumeAvailable.class);

      checkFilters(httpMethod);
   }

   public void testDetachVolumeOptions() throws SecurityException, NoSuchMethodException,
            IOException {
      Method method = ElasticBlockStoreAsyncClient.class.getMethod("detachVolumeInRegion",
               Region.class, String.class, boolean.class, Array.newInstance(
                        DetachVolumeOptions.class, 0).getClass());
      GeneratedHttpRequest<ElasticBlockStoreAsyncClient> httpMethod = processor
               .createRequest(method, Region.DEFAULT, "id", true, fromInstance("instanceId")
                        .fromDevice("/device"));

      assertRequestLineEquals(httpMethod, "POST https://ec2.amazonaws.com/ HTTP/1.1");
      assertHeadersEqual(httpMethod,
               "Content-Length: 100\nContent-Type: application/x-www-form-urlencoded\nHost: ec2.amazonaws.com\n");
      assertPayloadEquals(
               httpMethod,
               "Version=2009-11-30&Action=DetachVolume&Force=true&VolumeId=id&InstanceId=instanceId&Device=%2Fdevice");

      assertResponseParserClassEquals(method, httpMethod, CloseContentAndReturn.class);
      assertSaxResponseParserClassEquals(method, null);
      assertExceptionParserClassEquals(method, ReturnVoidOnVolumeAvailable.class);

      checkFilters(httpMethod);
   }

   public void testCreateSnapshot() throws SecurityException, NoSuchMethodException, IOException {
      Method method = ElasticBlockStoreAsyncClient.class.getMethod("createSnapshotInRegion",
               Region.class, String.class, Array.newInstance(CreateSnapshotOptions.class, 0)
                        .getClass());
      GeneratedHttpRequest<ElasticBlockStoreAsyncClient> httpMethod = processor.createRequest(
               method, Region.DEFAULT, "volumeId");

      assertRequestLineEquals(httpMethod, "POST https://ec2.amazonaws.com/ HTTP/1.1");
      assertHeadersEqual(httpMethod,
               "Content-Length: 58\nContent-Type: application/x-www-form-urlencoded\nHost: ec2.amazonaws.com\n");
      assertPayloadEquals(httpMethod, "Version=2009-11-30&Action=CreateSnapshot&VolumeId=volumeId");

      assertResponseParserClassEquals(method, httpMethod, ParseSax.class);
      assertSaxResponseParserClassEquals(method, SnapshotHandler.class);
      assertExceptionParserClassEquals(method, null);

      checkFilters(httpMethod);
   }

   public void testCreateSnapshotOptions() throws SecurityException, NoSuchMethodException,
            IOException {
      Method method = ElasticBlockStoreAsyncClient.class.getMethod("createSnapshotInRegion",
               Region.class, String.class, Array.newInstance(CreateSnapshotOptions.class, 0)
                        .getClass());
      GeneratedHttpRequest<ElasticBlockStoreAsyncClient> httpMethod = processor.createRequest(
               method, Region.DEFAULT, "volumeId", CreateSnapshotOptions.Builder
                        .withDescription("description"));

      assertRequestLineEquals(httpMethod, "POST https://ec2.amazonaws.com/ HTTP/1.1");
      assertHeadersEqual(httpMethod,
               "Content-Length: 82\nContent-Type: application/x-www-form-urlencoded\nHost: ec2.amazonaws.com\n");
      assertPayloadEquals(httpMethod,
               "Version=2009-11-30&Action=CreateSnapshot&VolumeId=volumeId&Description=description");

      assertResponseParserClassEquals(method, httpMethod, ParseSax.class);
      assertSaxResponseParserClassEquals(method, SnapshotHandler.class);
      assertExceptionParserClassEquals(method, null);

      checkFilters(httpMethod);
   }

   public void testDescribeSnapshots() throws SecurityException, NoSuchMethodException, IOException {
      Method method = ElasticBlockStoreAsyncClient.class.getMethod("describeSnapshotsInRegion",
               Region.class, Array.newInstance(DescribeSnapshotsOptions.class, 0).getClass());
      GeneratedHttpRequest<ElasticBlockStoreAsyncClient> httpMethod = processor.createRequest(
               method, Region.DEFAULT);

      assertRequestLineEquals(httpMethod, "POST https://ec2.amazonaws.com/ HTTP/1.1");
      assertHeadersEqual(httpMethod,
               "Content-Length: 43\nContent-Type: application/x-www-form-urlencoded\nHost: ec2.amazonaws.com\n");
      assertPayloadEquals(httpMethod, "Version=2009-11-30&Action=DescribeSnapshots");

      assertResponseParserClassEquals(method, httpMethod, ParseSax.class);
      assertSaxResponseParserClassEquals(method, DescribeSnapshotsResponseHandler.class);
      assertExceptionParserClassEquals(method, null);

      checkFilters(httpMethod);
   }

   public void testDescribeSnapshotsArgs() throws SecurityException, NoSuchMethodException,
            IOException {
      Method method = ElasticBlockStoreAsyncClient.class.getMethod("describeSnapshotsInRegion",
               Region.class, Array.newInstance(DescribeSnapshotsOptions.class, 0).getClass());
      GeneratedHttpRequest<ElasticBlockStoreAsyncClient> httpMethod = processor.createRequest(
               method, Region.DEFAULT, ownedBy("o1", "o2").restorableBy("r1", "r2").snapshotIds(
                        "s1", "s2"));

      assertRequestLineEquals(httpMethod, "POST https://ec2.amazonaws.com/ HTTP/1.1");
      assertHeadersEqual(httpMethod,
               "Content-Length: 133\nContent-Type: application/x-www-form-urlencoded\nHost: ec2.amazonaws.com\n");
      assertPayloadEquals(
               httpMethod,
               "Version=2009-11-30&Action=DescribeSnapshots&Owner.1=o1&Owner.2=o2&RestorableBy.1=r1&RestorableBy.2=r2&SnapshotId.1=s1&SnapshotId.2=s2");

      assertResponseParserClassEquals(method, httpMethod, ParseSax.class);
      assertSaxResponseParserClassEquals(method, DescribeSnapshotsResponseHandler.class);
      assertExceptionParserClassEquals(method, null);

      checkFilters(httpMethod);
   }

   public void testGetCreateVolumePermissionForSnapshot() throws SecurityException,
            NoSuchMethodException, IOException {
      Method method = ElasticBlockStoreAsyncClient.class.getMethod(
               "getCreateVolumePermissionForSnapshotInRegion", Region.class, String.class);
      GeneratedHttpRequest<ElasticBlockStoreAsyncClient> httpMethod = processor.createRequest(
               method, Region.DEFAULT, "snapshotId");

      assertRequestLineEquals(httpMethod, "POST https://ec2.amazonaws.com/ HTTP/1.1");
      assertHeadersEqual(httpMethod,
               "Content-Length: 106\nContent-Type: application/x-www-form-urlencoded\nHost: ec2.amazonaws.com\n");
      assertPayloadEquals(
               httpMethod,
               "Version=2009-11-30&Action=DescribeSnapshotAttribute&Attribute=createVolumePermission&SnapshotId=snapshotId");

      assertResponseParserClassEquals(method, httpMethod, ParseSax.class);
      assertSaxResponseParserClassEquals(method, PermissionHandler.class);
      assertExceptionParserClassEquals(method, null);

      checkFilters(httpMethod);
   }

   public void testAddCreateVolumePermissionsToSnapshot() throws SecurityException,
            NoSuchMethodException, IOException {
      Method method = ElasticBlockStoreAsyncClient.class.getMethod(
               "addCreateVolumePermissionsToSnapshotInRegion", Region.class, Iterable.class,
               Iterable.class, String.class);
      GeneratedHttpRequest<ElasticBlockStoreAsyncClient> httpMethod = processor.createRequest(
               method, Region.DEFAULT, ImmutableList.of("bob", "sue"), ImmutableList.of("all"),
               "snapshotId");

      assertRequestLineEquals(httpMethod, "POST https://ec2.amazonaws.com/ HTTP/1.1");
      assertHeadersEqual(httpMethod,
               "Content-Length: 122\nContent-Type: application/x-www-form-urlencoded\nHost: ec2.amazonaws.com\n");
      assertPayloadEquals(
               httpMethod,
               "Version=2009-11-30&Action=ModifySnapshotAttribute&OperationType=add&Attribute=createVolumePermission&SnapshotId=snapshotId&UserGroup.1=all&UserId.1=bob&UserId.2=sue");

      assertResponseParserClassEquals(method, httpMethod, CloseContentAndReturn.class);
      assertSaxResponseParserClassEquals(method, null);
      assertExceptionParserClassEquals(method, null);

      checkFilters(httpMethod);
   }

   public void testRemoveCreateVolumePermissionsFromSnapshot() throws SecurityException,
            NoSuchMethodException, IOException {
      Method method = ElasticBlockStoreAsyncClient.class.getMethod(
               "removeCreateVolumePermissionsFromSnapshotInRegion", Region.class, Iterable.class,
               Iterable.class, String.class);
      GeneratedHttpRequest<ElasticBlockStoreAsyncClient> httpMethod = processor.createRequest(
               method, Region.DEFAULT, ImmutableList.of("bob", "sue"), ImmutableList.of("all"),
               "snapshotId");

      assertRequestLineEquals(httpMethod, "POST https://ec2.amazonaws.com/ HTTP/1.1");
      assertHeadersEqual(httpMethod,
               "Content-Length: 125\nContent-Type: application/x-www-form-urlencoded\nHost: ec2.amazonaws.com\n");
      assertPayloadEquals(
               httpMethod,
               "Version=2009-11-30&Action=ModifySnapshotAttribute&OperationType=remove&Attribute=createVolumePermission&SnapshotId=snapshotId&UserGroup.1=all&UserId.1=bob&UserId.2=sue");
      assertResponseParserClassEquals(method, httpMethod, CloseContentAndReturn.class);
      assertSaxResponseParserClassEquals(method, null);
      assertExceptionParserClassEquals(method, null);

      checkFilters(httpMethod);
   }

   public void testResetCreateVolumePermissionsOnSnapshot() throws SecurityException,
            NoSuchMethodException, IOException {
      Method method = ElasticBlockStoreAsyncClient.class.getMethod(
               "resetCreateVolumePermissionsOnSnapshotInRegion", Region.class, String.class);
      GeneratedHttpRequest<ElasticBlockStoreAsyncClient> httpMethod = processor.createRequest(
               method, Region.DEFAULT, "snapshotId");

      assertRequestLineEquals(httpMethod, "POST https://ec2.amazonaws.com/ HTTP/1.1");
      assertHeadersEqual(httpMethod,
               "Content-Length: 103\nContent-Type: application/x-www-form-urlencoded\nHost: ec2.amazonaws.com\n");
      assertPayloadEquals(
               httpMethod,
               "Version=2009-11-30&Action=ResetSnapshotAttribute&Attribute=createVolumePermission&SnapshotId=snapshotId");
      assertResponseParserClassEquals(method, httpMethod, CloseContentAndReturn.class);
      assertSaxResponseParserClassEquals(method, null);
      assertExceptionParserClassEquals(method, null);

      checkFilters(httpMethod);
   }

   @Override
   protected void checkFilters(GeneratedHttpRequest<ElasticBlockStoreAsyncClient> httpMethod) {
      assertEquals(httpMethod.getFilters().size(), 1);
      assertEquals(httpMethod.getFilters().get(0).getClass(), FormSigner.class);
   }

   @Override
   protected TypeLiteral<RestAnnotationProcessor<ElasticBlockStoreAsyncClient>> createTypeLiteral() {
      return new TypeLiteral<RestAnnotationProcessor<ElasticBlockStoreAsyncClient>>() {
      };
   }

   @Override
   protected Module createModule() {
      return new AbstractModule() {
         @Override
         protected void configure() {
            bind(URI.class).annotatedWith(EC2.class).toInstance(
                     URI.create("https://ec2.amazonaws.com"));
            bindConstant().annotatedWith(Jsr330.named(AWSConstants.PROPERTY_AWS_ACCESSKEYID)).to(
                     "user");
            bindConstant().annotatedWith(Jsr330.named(AWSConstants.PROPERTY_AWS_SECRETACCESSKEY))
                     .to("key");
            bind(Logger.LoggerFactory.class).toInstance(new LoggerFactory() {
               public Logger getLogger(String category) {
                  return Logger.NULL;
               }
            });
         }

         @SuppressWarnings("unused")
         @Provides
         @TimeStamp
         String provide() {
            return "2009-11-08T15:54:08.897Z";
         }

         @SuppressWarnings("unused")
         @Singleton
         @Provides
         Map<Region, URI> provideMap() {
            return ImmutableMap.<Region, URI> of(Region.DEFAULT, URI
                     .create("https://ec2.amazonaws.com"));
         }

         @SuppressWarnings("unused")
         @Singleton
         @Provides
         Map<AvailabilityZone, Region> provideAvailabilityZoneRegionMap() {
            return ImmutableMap.<AvailabilityZone, Region> of(AvailabilityZone.US_EAST_1A,
                     Region.DEFAULT);
         }
      };
   }
}