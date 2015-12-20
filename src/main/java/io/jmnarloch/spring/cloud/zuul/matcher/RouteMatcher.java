/**
 * Copyright (c) 2015 the original author or authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.jmnarloch.spring.cloud.zuul.matcher;

import org.springframework.cloud.netflix.zuul.filters.ProxyRouteLocator;

import java.util.Map;

/**
 * A Zuul route matcher. Allows to find the specific route information for given route.
 *
 * @author Jakub Narloch
 */
public interface RouteMatcher {

    /**
     * Sets the map of routes.
     *
     * @param routes the routes map
     */
    void setRoutes(Map<String, ProxyRouteLocator.ProxyRouteSpec> routes);

    /**
     * Retrieves the route spec for given path.
     * @param path the route path
     * @return the matching route spec
     */
    ProxyRouteLocator.ProxyRouteSpec getMatchingRoute(String path);
}
