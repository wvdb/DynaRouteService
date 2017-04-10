package be.ictdynamic.dynarouteservice.common.beanutils;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

/**
 * Static class providing convenience and utility methods for DynaBean implementations.
 *
 * @author Erik Verkelkeren
 * @version $Revision$
 * @since 21-feb-2008
 */
public final class DynaBeanUtilities {

    /**
     * Private constructor to prevent this static class from being instantiated.
     */
    private DynaBeanUtilities() {
    }

    /**
     * Nullsafe method to retrieve the value of the specified property from the specified bean and cast it to a String.
     *
     * @param bean     DynaBean referencing the bean to retrieve the property value from, may be null.
     * @param property String referencing the name of the property to be retrieved, may be null.
     * @return String referencing the value of the specified property from the specified bean, null when either bean or property is null.
     */
    public static String castString(DynaBean bean, String property) {
        return DynaBeanUtilities.cast(bean, property);
    }

    /**
     * Nullsafe method to retrieve the value of the specified property from the specified bean and cast it to a Long.
     *
     * @param bean     DynaBean referencing the bean to retrieve the property value from, may be null.
     * @param property Long referencing the name of the property to be retrieved, may be null.
     * @return Long referencing the value of the specified property from the specified bean, null when either bean or property is null.
     */
    public static Long castLong(DynaBean bean, String property) {
        return DynaBeanUtilities.cast(bean, property);
    }

    /**
     * Nullsafe method to retrieve the value of the specified property from the specified bean and cast it to a Integer.
     *
     * @param bean     DynaBean referencing the bean to retrieve the property value from, may be null.
     * @param property Integer referencing the name of the property to be retrieved, may be null.
     * @return Integer referencing the value of the specified property from the specified bean, null when either bean or property is null.
     */
    public static Integer castInteger(DynaBean bean, String property) {
        return DynaBeanUtilities.cast(bean, property);
    }

    /**
     * Nullsafe method to retrieve the value of the specified property from the specified bean and cast it to a Number.
     *
     * @param bean     DynaBean referencing the bean to retrieve the property value from, may be null.
     * @param property Number referencing the name of the property to be retrieved, may be null.
     * @return Number referencing the value of the specified property from the specified bean, null when either bean or property is null.
     */
    public static Number castNumber(DynaBean bean, String property) {
        return DynaBeanUtilities.cast(bean, property);
    }

    /**
     * Nullsafe method to retrieve the value of the specified property from the specified bean and cast it to a Boolean.
     *
     * @param bean     DynaBean referencing the bean to retrieve the property value from, may be null.
     * @param property Boolean referencing the name of the property to be retrieved, may be null.
     * @return Boolean referencing the value of the specified property from the specified bean, null when either bean or property is null.
     */
    public static Boolean castBoolean(DynaBean bean, String property) {
        return DynaBeanUtilities.cast(bean, property);
    }

    /**
     * Nullsafe method to retrieve the value of the specified property from the specified bean and cast it to a Date.
     *
     * @param bean     DynaBean referencing the bean to retrieve the property value from, may be null.
     * @param property Date referencing the name of the property to be retrieved, may be null.
     * @return Date referencing the value of the specified property from the specified bean, null when either bean or property is null.
     */
    public static Date castDate(DynaBean bean, String property) {
        return DynaBeanUtilities.cast(bean, property);
    }

    /**
     * Nullsafe method to retrieve the value of the specified property from the specified bean and parse it to a String.
     *
     * @param bean     DynaBean referencing the bean to retrieve the property value from, may be null.
     * @param property String referencing the name of the property to be retrieved, may be null.
     * @return String referencing the value of the specified property from the specified bean, null when either bean or property is null.
     */
    public static String parseString(DynaBean bean, String property) {
        return DynaBeanUtilities.parse(bean, property, String.class);
    }

    /**
     * Nullsafe method to retrieve the value of the specified property from the specified bean and parse it to a Long.
     *
     * @param bean     DynaBean referencing the bean to retrieve the property value from, may be null.
     * @param property Long referencing the name of the property to be retrieved, may be null.
     * @return Long referencing the value of the specified property from the specified bean, null when either bean or property is null.
     */
    public static Long parseLong(DynaBean bean, String property) {
        return DynaBeanUtilities.parse(bean, property, Long.class);
    }

    /**
     * Nullsafe method to retrieve the value of the specified property from the specified bean and parse it to a Integer.
     *
     * @param bean     DynaBean referencing the bean to retrieve the property value from, may be null.
     * @param property Integer referencing the name of the property to be retrieved, may be null.
     * @return Integer referencing the value of the specified property from the specified bean, null when either bean or property is null.
     */
    public static Integer parseInteger(DynaBean bean, String property) {
        return DynaBeanUtilities.parse(bean, property, Integer.class);
    }

    /**
     * Nullsafe method to retrieve the value of the specified property from the specified bean and parse it to a Number.
     *
     * @param bean     DynaBean referencing the bean to retrieve the property value from, may be null.
     * @param property Number referencing the name of the property to be retrieved, may be null.
     * @return Number referencing the value of the specified property from the specified bean, null when either bean or property is null.
     */
    public static Number parseNumber(DynaBean bean, String property) {
        return DynaBeanUtilities.parse(bean, property, Number.class);
    }

    /**
     * Nullsafe method to retrieve the value of the specified property from the specified bean and parse it to a Boolean.
     *
     * @param bean     DynaBean referencing the bean to retrieve the property value from, may be null.
     * @param property Boolean referencing the name of the property to be retrieved, may be null.
     * @return Boolean referencing the value of the specified property from the specified bean, null when either bean or property is null.
     */
    public static Boolean parseBoolean(DynaBean bean, String property) {
        return DynaBeanUtilities.parse(bean, property, Boolean.class);
    }

    /**
     * Nullsafe method to retrieve the value of the specified property from the specified bean and parse it to a Date.
     *
     * @param bean     DynaBean referencing the bean to retrieve the property value from, may be null.
     * @param property Date referencing the name of the property to be retrieved, may be null.
     * @return Date referencing the value of the specified property from the specified bean, null when either bean or property is null.
     */
    public static Date parseDate(DynaBean bean, String property) {
        return DynaBeanUtilities.parse(bean, property, Date.class);
    }

    /**
     * Nullsafe method to retrieve the value of the specified property from the specified bean and parse it to a Timestamp.
     *
     * @param bean     DynaBean referencing the bean to retrieve the property value from, may be null.
     * @param property Timestamp referencing the name of the property to be retrieved, may be null.
     * @return Timestamp referencing the value of the specified property from the specified bean, null when either bean or property is null.
     */
    public static Timestamp parseTimestamp(DynaBean bean, String property) {
        return DynaBeanUtilities.parse(bean, property, Timestamp.class);
    }

    /**
     * Nullsafe method to retrieve the value of the specified property from the specified bean.
     *
     * @param bean     DynaBean referencing the bean to retrieve the property value from, may be null.
     * @param property String referencing the name of the property to be retrieved, may be null.
     * @return Object referencing the value of the specified property from the specified bean, null when either bean or property is null.
     */
    public static Object getProperty(DynaBean bean, String property) {
        Object value = null;

        if (bean != null && StringUtils.isNotEmpty(property)) {
            value = bean.get(property);
        }

        return value;
    }

    /**
     * Nullsafe method to retrieve the value of the specified property from the specified bean and CASTS the value to the specified type.
     *
     * @param bean     DynaBean referencing the bean to retrieve the property value from, may be null.
     * @param property String referencing the name of the property to be retrieved, may be null.
     * @return Serializable implementation referencing the value of the specified property from the specified bean, null when either bean or property is null.
     */
    @SuppressWarnings({"unchecked"})
    private static <S extends Serializable> S cast(DynaBean bean, String property) {
        return (S) DynaBeanUtilities.getProperty(bean, property);
    }

    /**
     * Nullsafe method to retrieve the value of the specified property from the specified bean and PARSES the value to the specified type.
     *
     * @param bean     DynaBean referencing the bean to retrieve the property value from, may be null.
     * @param property String referencing the name of the property to be retrieved, may be null.
     * @param type     Class referencing the data type to parse the found value to, may not be null.
     * @return Serializable implementation referencing the value of the specified property from the specified bean, null when either bean or property is null.
     */
    private static <S extends Serializable> S parse(DynaBean bean, String property, final Class<S> type) {
        return ConvertUtilities.parse(ConvertUtilities.toString(DynaBeanUtilities.getProperty(bean, property)), type);
    }
}