/*
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-v20.html
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Copyright Contributors to the Zowe Project.
 */
package org.zowe.apiml.gateway.conformance;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import org.zowe.apiml.constants.EurekaMetadataDefinition;
import java.util.*;

/**
 * Service class that offers methods for checking onboarding information and also checks availability metadata from
 * a provided serviceId.
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class VerificationOnboardService {

    private final DiscoveryClient discoveryClient;

    @Qualifier("restTemplateWithoutKeystore")
    private final RestTemplate restTemplate;

    /**
     * Accepts serviceId and checks if the service is onboarded to the API Mediation Layer
     *
     * @param serviceId serviceId to check
     * @return true if the service is known by Eureka otherwise false.
     */
    public boolean checkOnboarding(String serviceId) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Accepts metadata and retrieves the Swagger url if it exists
     *
     * @param metadata to grab swagger from
     * @return SwaggerUrl when able, empty string otherwise
     */
    public Optional<String> findSwaggerUrl(Map<String, String> metadata) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Retrieves swagger from the url
     *
     * @param swaggerUrl URL to retrieve from
     * @return Swagger as string
     */
    public String getSwagger(String swaggerUrl) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Checks if endpoints can be called and return documented responses
     *
     * @param endpoints                 endpoints to check
     * @param passedAuthenticationToken Token used to call endpoints that support SSO
     * @return List of problems
     */
    public List<String> testEndpointsByCalling(Set<Endpoint> endpoints, String passedAuthenticationToken) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    private List<String> checkEndpointsWithSSO(Set<Endpoint> endpoints, String passedAuthenticationToken) {
        ArrayList<String> result = new ArrayList<>();
        if (passedAuthenticationToken == null) {
            result.add("Authentication token is not available, serviceId '%s' endpoints SSO check skipped.".formatted(endpoints.stream().findAny().map(Endpoint::getServiceId).orElse("unknown")));
            return result;
        }
        HttpHeaders headersSSO = new HttpHeaders();
        headersSSO.setContentType(MediaType.APPLICATION_JSON);
        headersSSO.add("Cookie", "apimlAuthenticationToken=" + passedAuthenticationToken);
        HttpEntity<String> requestSSO = new HttpEntity<>(headersSSO);
        for (Endpoint endpoint : endpoints) {
            for (HttpMethod method : endpoint.getHttpMethods()) {
                checkEndpoint(endpoint, result, method, requestSSO, true);
            }
        }
        return result;
    }

    private List<String> checkEndpointsNoSSO(Set<Endpoint> endpoints) {
        ArrayList<String> result = new ArrayList<>();
        HttpHeaders headersNoSSO = new HttpHeaders();
        headersNoSSO.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> requestNoSSO = new HttpEntity<>(headersNoSSO);
        for (Endpoint endpoint : endpoints) {
            for (HttpMethod method : endpoint.getHttpMethods()) {
                checkEndpoint(endpoint, result, method, requestNoSSO, false);
            }
        }
        return result;
    }

    private void checkEndpoint(Endpoint endpoint, ArrayList<String> result, HttpMethod method, HttpEntity<String> request, boolean attemptWithSSO) {
        if (method == HttpMethod.DELETE) {
            return;
        }
        String urlFromSwagger = endpoint.getUrl();
        // replaces parameters in {} in query, so it can be called
        // Example: transforms https://localhost:10010/discoverableclient/api/v1/pets/{id} to https://localhost:10010/discoverableclient/api/v1/pets/dummy
        String url = urlFromSwagger.replaceAll("\\{[^{}]*}", "dummy");
        ResponseEntity<String> responseWithSSO = getResponse(request, method, url);
        result.addAll(fromResponseReturnFoundProblems(responseWithSSO, endpoint, method, attemptWithSSO));
    }

    private ResponseEntity<String> getResponse(HttpEntity<String> request, HttpMethod method, String url) {
        ResponseEntity<String> response;
        try {
            response = restTemplate.exchange(url, method, request, String.class);
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            response = ResponseEntity.status(e.getStatusCode()).headers(e.getResponseHeaders()).body(e.getResponseBodyAsString());
        } catch (Exception ex) {
            response = ResponseEntity.status(HttpStatusCode.valueOf(500)).body(ex.getMessage());
        }
        return response;
    }

    private List<String> fromResponseReturnFoundProblems(ResponseEntity<String> response, Endpoint currentEndpoint, HttpMethod method, boolean attemptWithSSO) {
        ArrayList<String> result = new ArrayList<>();
        String responseBody = response.getBody();
        if (responseBody != null && response.getStatusCode() == HttpStatus.NOT_FOUND && responseBody.contains("ZWEAM104E")) {
            result.add("Documented endpoint at " + currentEndpoint.getUrl() + " could not be located, attempting to call it through gateway gives the ZWEAM104E error");
        }
        if (attemptWithSSO && responseBody != null && (response.getStatusCode() == HttpStatus.FORBIDDEN || response.getStatusCode() == HttpStatus.UNAUTHORIZED)) {
            result.add(method + " request to documented endpoint at " + currentEndpoint.getUrl() + " responded with status code " + response.getStatusCode().value() + ", despite being called with the SSO authorization");
        }
        if (!currentEndpoint.isResponseCodeForMethodDocumented(String.valueOf(response.getStatusCode().value()), method)) {
            result.add(method + " request to documented endpoint at " + currentEndpoint.getUrl() + " returns undocumented " + response.getStatusCode().value() + " status code, documented responses are:" + currentEndpoint.getValidResponses().get(method.toString()));
        }
        return result;
    }

    public static List<String> getProblemsWithEndpointUrls(AbstractSwaggerValidator swaggerParser) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static boolean supportsSSO(Map<String, String> metadata) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
