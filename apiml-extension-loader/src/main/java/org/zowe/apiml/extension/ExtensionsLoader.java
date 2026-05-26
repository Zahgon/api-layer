/*
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-v20.html
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Copyright Contributors to the Zowe Project.
 */
package org.zowe.apiml.extension;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.boot.context.event.ApplicationContextInitializedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import java.util.Arrays;

/**
 * Loader of extensions
 */
@Slf4j
@RequiredArgsConstructor
public class ExtensionsLoader implements ApplicationListener<ApplicationContextInitializedEvent> {

    @NonNull
    private final ExtensionConfigReader configReader;

    @Override
    public void onApplicationEvent(ApplicationContextInitializedEvent event) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
