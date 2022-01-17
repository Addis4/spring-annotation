package com.knoldus.helper;

import com.knoldus.aspect.ClientFilter;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SmokeTestClientFilter {
    private static final String SMOKE_TEST_CLIENT = "__naas_smoke_test_client";
    private static final String CLIENT_ID = "clientId";
    private static final String UNKNOWN = "unknown";

    private static String readClientId(Object classObj, Field clientIdField) throws IllegalAccessException {
        clientIdField.setAccessible(true);
        Object clientId = clientIdField.get(classObj);
        return (clientId != null ? clientId.toString() : UNKNOWN);
    }

    private static List<Field> getAllFieldsList(final Class<?> eventClass) {
        final List<Field> allFields = new ArrayList<>();
        Class<?> currentClass = eventClass;
        while (currentClass != null) {
            final Field[] declaredFields = currentClass.getDeclaredFields();
            Collections.addAll(allFields, declaredFields);
            currentClass = currentClass.getSuperclass();
        }
        return allFields;
    }

    public boolean isValid(JoinPoint joinPoint) throws IllegalAccessException {
        String serviceName = getServiceName(joinPoint);
        Object value = joinPoint.getArgs()[0];
        List<Field> allFields = getAllFieldsList(value.getClass());
        Field clientIdField = allFields.stream()
                .filter(field -> field.getName().equals(CLIENT_ID))
                .findFirst()
                .orElse(null);

        if (null != clientIdField) {
            String clientId = readClientId(value, clientIdField);
            return !UNKNOWN.equals(clientId)
                    && !(((clientId.startsWith(SMOKE_TEST_CLIENT)) && !clientId.endsWith(serviceName)));
        }
        return false;
    }

    public String getServiceName(JoinPoint joinPoint) {
        final MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        final ClientFilter clientFilter = methodSignature.getMethod().getAnnotation(ClientFilter.class);
        return clientFilter.serviceName();
    }

}
