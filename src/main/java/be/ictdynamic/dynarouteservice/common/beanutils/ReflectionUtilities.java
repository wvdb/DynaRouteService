package be.ictdynamic.dynarouteservice.common.beanutils;

import be.ictdynamic.dynarouteservice.common.collections.CollectionUtilities;
import org.apache.commons.beanutils.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

/**
 * Static class providing utility methods related to reflection.
 * <p/>
 * This class mainly invokes the Jakarta Commons BeanUtils functionality, but handles all possible exceptions.
 *
 * @author Erik Verkelkeren
 * @version $Revision$
 * @since 26-feb-2007
 */
public final class ReflectionUtilities {
    /**
     * Logger responsible for logging and debugging statements.
     */
    private static final Logger LOG = LoggerFactory.getLogger(ReflectionUtilities.class);
    public static final int STRING_BUILDER_INITIAL_CAPACITY = 20;
    public static final int STRING_BUILDER_CAPACITY_MULTIPLIER = 50;

    /**
     * Private constructor to prevent this static class from being instantiated.
     */
    private ReflectionUtilities() {
    }

    /**
     * Constructs a new instance of the specified data type.
     *
     * @param type Class referencing the data type to create a new instance for.
     * @return Object implementation referencing the new instance of the specified data type.
     */
    public static <T> T newInstance(Class<T> type) {
        T instance = null;

        try {
            instance = type.newInstance();
        } catch (IllegalAccessException iae) {
            ReflectionUtilities.debugException(iae);
        } catch (InstantiationException ie) {
            LOG.error(ReflectionUtilities.buildExceptionMessage(ie), ie);
        }
        return instance;
    }

    /**
     * Constructs a new instance of the specified data type.
     *
     * @param type Class referencing the data type to create a new instance for.
     * @param args The arguments.
     * @return Object implementation referencing the new instance of the specified data type.
     */
    public static <T> T newInstance(Class<T> type, Object... args) {
        T instance = null;

        try {
            instance = type.cast(ConstructorUtils.invokeExactConstructor(type, args));
        } catch (NoSuchMethodException nsme) {
            instance = invokeConstructor(type, args);
        } catch (IllegalAccessException iae) {
            ReflectionUtilities.debugException(iae);
        } catch (InvocationTargetException ite) {
            ReflectionUtilities.debugException(ite);
        } catch (InstantiationException ie) {
            LOG.error(ReflectionUtilities.buildExceptionMessage(ie), ie);
        }

        return instance;
    }

    /**
     * Constructs a new instance using the exact constructor for the given arguments.
     *
     * @param type Class referencing the data type to create a new instance for.
     * @param args The arguments.
     * @return Object implementation referencing the new instance of the specified data type.
     */
    public static <T> T invokeConstructor(Class<T> type, Object... args) {
        T instance = null;
        try {
            instance = type.cast(ConstructorUtils.invokeConstructor(type, args));
        } catch (NoSuchMethodException nsme) {
            ReflectionUtilities.debugException(nsme);
        } catch (IllegalAccessException iae) {
            ReflectionUtilities.debugException(iae);
        } catch (InvocationTargetException ite) {
            ReflectionUtilities.debugException(ite);
        } catch (InstantiationException ie) {
            LOG.error(ReflectionUtilities.buildExceptionMessage(ie), ie);
        }
        return instance;
    }

    /**
     * Returns the value of the specified property of the specified bean, no matter which property reference format is used, with no type conversions.
     *
     * @param bean         Object referencing the object to retrieve the specified property from.
     * @param propertyName String referencing the name of the property to be retrieved.
     * @return Object referencing the value of the retrieved property, null when property could not be retrieved.
     */
    public static Object getProperty(Object bean, String propertyName) {
        Object property = null;

        try {
            property = PropertyUtils.getNestedProperty(bean, propertyName);
        } catch (IllegalAccessException iae) {
            ReflectionUtilities.debugException(iae);
        } catch (InvocationTargetException ite) {
            ReflectionUtilities.debugException(ite);
        } catch (NoSuchMethodException nsme) {
            ReflectionUtilities.debugException(nsme);
        } catch (NestedNullException nne) {
            ReflectionUtilities.nestedNullException(nne, propertyName);
        } catch (IllegalArgumentException iae) {
            ReflectionUtilities.nestedNullException(iae, propertyName);
        }

        return property;
    }

    /**
     * Sets the specified value to the specified property of the specified bean, no matter which property reference format is used, with no type conversions.
     *
     * @param bean          Object referencing the object to set the specified property to.
     * @param propertyName  String referencing the name of the property to be set.
     * @param propertyValue Object referencing the new property value to be set.
     */
    public static void setProperty(Object bean, String propertyName, Object propertyValue) {
        try {
            PropertyUtils.setNestedProperty(bean, propertyName, propertyValue);
        } catch (IllegalAccessException iae) {
            ReflectionUtilities.debugException(iae);
        } catch (InvocationTargetException ite) {
            ReflectionUtilities.debugException(ite);
        } catch (NoSuchMethodException nsme) {
            ReflectionUtilities.debugException(nsme);
        } catch (NestedNullException nne) {
            ReflectionUtilities.nestedNullException(nne, propertyName);
        } catch (IllegalArgumentException iae) {
            ReflectionUtilities.nestedNullException(iae, propertyName);
            if (bean != null && StringUtils.isNotEmpty(propertyName) && propertyName.contains(".")) {
                ReflectionUtilities.logPropertyPath(bean, propertyName);
            }
        }
    }

    private static void logPropertyPath(Object bean, String propertyName) {
        Assert.notNull(bean, "No bean specified");
        Assert.notNull(propertyName, "No name specified");

        String[] path = StringUtils.split(propertyName, ".");
        Object nested = bean;
        StringBuilder message = new StringBuilder(STRING_BUILDER_INITIAL_CAPACITY + STRING_BUILDER_CAPACITY_MULTIPLIER * path.length);
        message.append("\n\tProperty path:");
        for (int i = 0; nested != null && i < path.length; i++) {
            String property = path[i];
            Object value = ReflectionUtilities.getProperty(nested, property);
            message.append("\n\t\t").append(property).append(" -> ").append(value);
            nested = value;
        }
        LOG.debug((LOG.isDebugEnabled() ? message.toString() : null));
    }

    /**
     * Invoke a named method whose parameter type matches the object type.
     *
     * @param object     Object referencing the object to invoke the specified method on.
     * @param methodName String referencing the name of the method to be invoked.
     * @param args       Array of Objects referencing the arguments to be passed during method invocation.
     * @return Object referencing the return value after method invocation.
     * @throws ReflectionException When an exception occurs during call of method.
     */
    public static Object invokeMethod(Object object, String methodName, Object... args) {
        Object returned;

        try {
            returned = MethodUtils.invokeMethod(object, methodName, args);
        } catch (NoSuchMethodException nsme) {
            throw new ReflectionException(nsme);
        } catch (IllegalAccessException iae) {
            throw new ReflectionException(iae);
        } catch (InvocationTargetException ite) {
            throw new ReflectionException(ite.getCause() != null ? ite.getCause() : ite);
        }

        return returned;
    }

    /**
     * Populates the JavaBeans properties of the specified bean, based on the specified name/value pairs.
     *
     * @param bean       Object referencing the JavaBean whose properties are being populated.
     * @param properties Map keyed by property name, with the corresponding (String or String[]) value(s) to be set.
     */
    public static void populate(Object bean, Map<String, Object> properties) {
        try {
            BeanUtils.populate(bean, properties);
        } catch (IllegalAccessException iae) {
            ReflectionUtilities.debugException(iae);
        } catch (InvocationTargetException ite) {
            ReflectionUtilities.debugException(ite);
        } catch (IllegalArgumentException iae) {
            for (Map.Entry<String, Object> entry : properties.entrySet()) {
                ReflectionUtilities.setProperty(bean, entry.getKey(), entry.getValue());
            }
        }
    }

    /**
     * Copy property values from the origin bean to the destination bean for all cases where the property names are the same.
     *
     * @param src  Object referencing the JavaBean whose properties are being copied.
     * @param dest Object referencing the JavaBean whose properties are being populated.
     */
    public static void copyProperties(Object src, Object dest) {
        try {
            BeanUtils.copyProperties(dest, src);
        } catch (IllegalAccessException iae) {
            ReflectionUtilities.debugException(iae);
        } catch (InvocationTargetException ite) {
            ReflectionUtilities.debugException(ite);
        }
    }

    /**
     * Invoke setter.
     *
     * @param target    the target
     * @param parameter the parameter
     */
    public static void invokeSetter(final Object target, final Object parameter) {
        ReflectionUtils.doWithMethods(target.getClass(), new ReflectionUtils.MethodCallback() {
            public void doWith(Method method) throws IllegalAccessException {
                try {
                    method.invoke(target, parameter);
                } catch (InvocationTargetException ite) {
                    ReflectionUtilities.debugException(ite);
                }
            }
        }, new ReflectionUtils.MethodFilter() {
            public boolean matches(Method method) {
                Class<?>[] parameterTypes = method.getParameterTypes();
                return method.getName().startsWith("set") && parameterTypes.length == 1 && parameterTypes[0].equals(parameter.getClass());
            }
        });
    }

    /**
     * Invoke getter.
     *
     * @param target        the target
     * @param parameterType the parameter type
     * @return the t
     */
    public static <T> T invokeGetter(final Object target, final Class<T> parameterType) {
        final Collection<T> objects = new ArrayList<T>();
        ReflectionUtils.doWithMethods(target.getClass(), new ReflectionUtils.MethodCallback() {
            public void doWith(Method method) throws IllegalAccessException {
                try {
                    objects.add(parameterType.cast(method.invoke(target)));
                } catch (InvocationTargetException ite) {
                    ReflectionUtilities.debugException(ite);
                }
            }
        }, new ReflectionUtils.MethodFilter() {
            public boolean matches(Method method) {
                Class<?>[] parameterTypes = method.getParameterTypes();
                return method.getName().startsWith("get") && parameterTypes.length == 0 && method.getReturnType().equals(parameterType);
            }
        });
        return CollectionUtilities.firstElement(objects);
    }

    /**
     * Gets the field value of the given source Object identified
     *
     * @param source    The object from which to get the field
     * @param fieldname The name of the field
     * @param fieldType The type of the field
     * @return T implementation referencing the field value of the given source Object identified
     */
    public static <T> T getFieldValue(Object source, String fieldname, Class<T> fieldType) {
        T value = null;
        if (source != null && fieldname != null && fieldType != null) {
            Field field = ReflectionUtils.findField(source.getClass(), fieldname);
            field.setAccessible(true);
            try {
                value = fieldType.cast(field.get(source));
            } catch (IllegalAccessException iae) {
                ReflectionUtilities.debugException(iae);
            }
        }
        return value;
    }

    private static void debugException(Exception e) {
        LOG.debug((LOG.isDebugEnabled() ? ReflectionUtilities.buildExceptionMessage(e) : null));
    }

    private static void nestedNullException(Exception e, String propertyName) {
        LOG.debug((LOG.isDebugEnabled() ? MessageFormat.format("The nested property ''{0}'' has a null value in its path: {1}", propertyName, e.getMessage()) : null));
    }

    private static String buildExceptionMessage(Exception e) {
        return MessageFormat.format("{0} caught in {1}: {2}", e.getClass().getName(), e.getStackTrace()[0].getMethodName(), e.getMessage());
    }
}
