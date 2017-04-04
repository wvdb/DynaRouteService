package be.ictdynamic.dynarouteservice.common.beanutils;

import be.ictdynamic.common.lang.ClassUtilities;
import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.beanutils.DynaClass;
import org.apache.commons.beanutils.DynaProperty;
import org.apache.commons.beanutils.LazyDynaBean;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

/**
 * BasicDynaBean implementation that tries to convert a value with a mismatching type to the expected one instead of throwing a ConversionException.
 *
 * @author Erik Verkelkeren
 * @version $Revision$
 * @since 12-sep-2008
 */
public class ConvertingBasicDynaBean extends LazyDynaBean implements InvocationHandler {
    private static final long serialVersionUID = 5180569329770683676L;

    /**
     * Constructor.
     *
     * @param dynaClass The DynaClass we are associated with.
     */
    public ConvertingBasicDynaBean(DynaClass dynaClass) {
        super(dynaClass);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @SuppressWarnings({"unchecked", "PMD.AvoidThrowingNullPointerException"})
    public void set(String name, Object value) {
        Object parsedValue = value;
        DynaProperty descriptor = getDynaClass().getDynaProperty(name);
        if (!ClassUtilities.isAssignable(parsedValue, Date.class) || !descriptor.getType().equals(Timestamp.class)) {
            if (parsedValue == null) {
                if (descriptor.getType().isPrimitive()) {
                    throw new NullPointerException("Primitive value for '" + name + "'");
                }
            } else if (!isAssignable(descriptor.getType(), parsedValue.getClass())) {
                String stringValue = ConvertUtilities.toString(parsedValue);
                parsedValue = ConvertUtilities.parse(stringValue, descriptor.getType());
            }
        }
        values.put(name, parsedValue);
    }

    @Override
    /**
     * Create a new Instance of a Property
     */
    protected Object createProperty(String name, Class type) {

        // Create Lists, arrays or DynaBeans
        if (type.isArray() || List.class.isAssignableFrom(type)) {
            return createIndexedProperty(name, type);
        }

        if (Map.class.isAssignableFrom(type)) {
            return createMappedProperty(name, type);
        }

        if (DynaBean.class.isAssignableFrom(type)) {
            return createDynaBeanProperty(name, type);
        }

        if (type.isPrimitive()) {
            return createPrimitiveProperty(name, type);
        }

        if (Number.class.isAssignableFrom(type)) {
            return createNumberProperty(name, type);
        }

        if (type == Object.class ) {
            return null;
        }

        return createOtherProperty(name, type);

    }

    /**
     * {@inheritDoc}
     */
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object result = null;

        String[] tokens = StringUtils.splitByCharacterTypeCamelCase(method.getName());
        String sqlProperty = StringUtils.lowerCase(StringUtils.join(ArrayUtils.subarray(tokens, 1, tokens.length), '_'));
        if (StringUtils.equals(tokens[0], "get")) {
            String tmp = ConvertUtilities.toString(this.get(sqlProperty));
            result = ConvertUtilities.parse(tmp, method.getReturnType());
        } else if (StringUtils.equals(tokens[0], "set")) {
            this.set(sqlProperty, args[0]);
        }

        return result;
    }
}