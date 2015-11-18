package io.grpc.proxy.server;

import io.grpc.examples.experimental.proxy.GreeterService;
import io.grpc.examples.experimental.proxy.GreeterServiceImpl;
import io.grpc.proxy.annotation.GrpcService;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

public class ReflectionHelper {
	
	
    public static Collection<Method> findAnnotatedMethods(Class<?> type, Class<? extends Annotation> annotation)
    {
        List<Method> result = new ArrayList<Method>();
        for (Method method : type.getMethods()) {
            if (method.isSynthetic() || method.isBridge() || Modifier.isStatic(method.getModifiers())) {
                continue;
            }
            Method managedMethod = findAnnotatedMethod(
                    type,
                    annotation,
                    method.getName(),
                    method.getParameterTypes());
            if (managedMethod != null) {
                result.add(managedMethod);
            }
        }
        return result;
    }
    
    public static Method findAnnotatedMethod(Class<?> configClass, Class<? extends Annotation> annotation, String methodName, Class<?>... paramTypes)
    {
        try {
            Method method = configClass.getDeclaredMethod(methodName, paramTypes);
            if (method != null && method.isAnnotationPresent(annotation)) {
                return method;
            }
        }
        catch (NoSuchMethodException e) {
            // ignore
        }

        if (configClass.getSuperclass() != null) {
            Method managedMethod = findAnnotatedMethod(
                    configClass.getSuperclass(),
                    annotation,
                    methodName,
                    paramTypes);
            if (managedMethod != null) {
                return managedMethod;
            }
        }

        for (Class<?> iface : configClass.getInterfaces()) {
            Method managedMethod = findAnnotatedMethod(iface, annotation, methodName, paramTypes);
            if (managedMethod != null) {
                return managedMethod;
            }
        }

        return null;
    }
    
    public static List<Class> getEffectiveClassAnnotations(Class type, Class annotation) {
    	
        // if the class is directly annotated, it is considered the only annotation
        if (type.isAnnotationPresent(annotation)) {
            return ImmutableList.of(type);
        }

        // otherwise find all annotations from all super classes and interfaces
        ImmutableList.Builder<Class> builder = ImmutableList.builder();
        addEffectiveClassAnnotations(type, annotation, builder);
        return builder.build();
    }

    private static void addEffectiveClassAnnotations(Class<?> type, Class annotation, ImmutableList.Builder builder) {
        if (type.isAnnotationPresent(annotation)) {
            builder.add(type);
            return;
        }
        if (type.getSuperclass() != null) {
            addEffectiveClassAnnotations(type.getSuperclass(), annotation, builder);
        }
        for (Class<?> anInterface : type.getInterfaces()) {
            addEffectiveClassAnnotations(anInterface, annotation, builder);
        }
    }
    
    
    

}
