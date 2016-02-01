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
package io.jmnarloch.spring.cloud.zuul.route;

import io.jmnarloch.spring.cloud.zuul.matcher.RouteMatcher;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.netflix.zuul.filters.ProxyRouteLocator;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties;
import org.springframework.util.StringUtils;

/**
 * A simple implementation of {@link ProxyRouteLocator} that delegates to the {@link RouteMatcher} for retrieving the
 * best matching route for specified request path.
 *
 * @author Jakub Narloch
 */
public class MatcherProxyRouteLocator extends ProxyRouteLocator {

    /**
     * The route matcher instance.
     */
    private final RouteMatcher routeMatcher;

    /**
     * The servlet path.
     */
    private final String servletPath;

    /**
     * The Zuul properties.
     */
    private final ZuulProperties properties;

    /**
     * Creates new instance of {@link MatcherProxyRouteLocator}.
     *
     * @param servletPath  the servlet path
     * @param discovery    the service discovery
     * @param properties   the properties
     * @param routeMatcher the route matcher
     */
    public MatcherProxyRouteLocator(String servletPath, DiscoveryClient discovery, ZuulProperties properties,
                                    RouteMatcher routeMatcher) {
        super(servletPath, discovery, properties);
        this.servletPath = servletPath;
        this.properties = properties;
        this.routeMatcher = routeMatcher;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ProxyRouteSpec getMatchingRoute(String path) {

        if (StringUtils.hasText(this.servletPath) && !this.servletPath.equals("/")
                && path.startsWith(this.servletPath)) {
            path = path.substring(this.servletPath.length());
        }

        return toProxyRouteSpec(path, routeMatcher.getMatchingRoute(path));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void resetRoutes() {
        routeMatcher.setRoutes(locateRoutes());
    }

    /**
     * Creates a {@link org.springframework.cloud.netflix.zuul.filters.ProxyRouteLocator.ProxyRouteSpec} out of
     * {@link org.springframework.cloud.netflix.zuul.filters.ZuulProperties.ZuulRoute}.
     *
     * @param route the zuul route
     * @return the route spec
     */
    private ProxyRouteSpec toProxyRouteSpec(final String path, final ZuulProperties.ZuulRoute route) {
        if(route == null) {
            return null;
        }

        String targetPath = getRequestPath(path);
        String prefix = properties.getPrefix();
        final Boolean retryable = isRetryable(route);

        if (properties.isStripPrefix() && targetPath.startsWith(prefix)) {
            targetPath = path.substring(prefix.length());
        }

        if (route.isStripPrefix()) {
            int index = route.getPath().indexOf("*") - 1;
            if (index > 0) {
                String routePrefix = route.getPath().substring(0, index);
                targetPath = targetPath.replaceFirst(routePrefix, "");
                prefix = prefix + routePrefix;
            }
        }

        return new ProxyRouteSpec(
                route.getId(),
                targetPath,
                route.getLocation(),
                prefix,
                retryable
        );
    }

    /**
     * Returns whether connection to specific route is retryable.
     *
     * @param route the route
     * @return true if is retryable, false otherwise
     */
    private Boolean isRetryable(ZuulProperties.ZuulRoute route) {
        if (route.getRetryable() != null) {
            return route.getRetryable();
        }
        return properties.getRetryable();
    }

    /**
     * Retrieves the route path, stripped from the servlet path.
     *
     * @param path the route path
     * @return the route request path
     */
    private String getRequestPath(String path) {
        if (StringUtils.hasText(this.servletPath) && !this.servletPath.equals("/")
                && path.startsWith(this.servletPath)) {
            return path.substring(this.servletPath.length());
        }
        return path;
    }
}
