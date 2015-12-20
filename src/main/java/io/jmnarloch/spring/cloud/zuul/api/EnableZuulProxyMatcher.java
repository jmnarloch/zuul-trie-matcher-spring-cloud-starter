/**
 * Copyright (c) 2015 the original author or authors
 *
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
 */
package io.jmnarloch.spring.cloud.zuul.api;

import io.jmnarloch.spring.cloud.zuul.matcher.RouteMatcher;
import io.jmnarloch.spring.cloud.zuul.support.ZuulProxyMatcherConfiguration;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Enables the Zuul proxy with configurable matching strategy besides that this pretty much resembles the standard
 * {@link org.springframework.cloud.netflix.zuul.EnableZuulProxy}. Requires a instance of
 * {@link RouteMatcher} to be registered within the application context.
 *
 * @author Jakub Narloch
 * @see RouteMatcher
 */
@EnableCircuitBreaker
@EnableDiscoveryClient
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Import(ZuulProxyMatcherConfiguration.class)
public @interface EnableZuulProxyMatcher {
}
