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
package org.jclouds.compute.functions;

import javax.inject.Singleton;

import org.jclouds.compute.domain.Template;
import org.jclouds.compute.options.TemplateOptions;
import org.jclouds.domain.LoginCredentials;
import org.jclouds.domain.LoginCredentials.Builder;

import com.google.common.base.Function;

/**
 * 
 * @author Adrian Cole
 */
@Singleton
public class DefaultCredentialsFromImageOrOverridingCredentials implements Function<Template, LoginCredentials> {

   @Override
   public LoginCredentials apply(Template template) {
      TemplateOptions options = template.getOptions();
      Builder builder = LoginCredentials.builder(template.getImage().getDefaultCredentials());
      if (options.getLoginUser() != null)
         builder.user(options.getLoginUser());
      if (options.getLoginPassword() != null)
         builder.password(options.getLoginPassword());
      if (options.getLoginPrivateKey() != null)
         builder.privateKey(options.getLoginPrivateKey());
      if (options.shouldAuthenticateSudo() != null)
         builder.authenticateSudo(true);
      return builder.build();
   }

}