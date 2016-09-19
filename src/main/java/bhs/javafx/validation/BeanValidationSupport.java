package bhs.javafx.validation;

import javafx.scene.control.Control;
import org.controlsfx.validation.ValidationSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.constraints.NotNull;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by bhs on 2016-09-19.
 */
public class BeanValidationSupport extends ValidationSupport {

	private static final Logger logger = LoggerFactory.getLogger(BeanValidationSupport.class);

	public <T> boolean registerValidator(Control c, T value, String propertyName) {
		Method m;
		try {
			m = new PropertyDescriptor("field", value.getClass()).getReadMethod();
            logger.debug("method: {}", m);
		} catch (IntrospectionException e) {
            m = null;
		}
		Field f;
		try {
			f = value.getClass().getDeclaredField(propertyName);
			logger.debug("field: {}", f);
		} catch (NoSuchFieldException e) {
            f = null;
		}

		boolean isRequired = (m != null && m.isAnnotationPresent(NotNull.class))
				|| (f != null && f.isAnnotationPresent(NotNull.class));

		if (m == null && f == null) {
			logger.error(value + " has neither field nor method with given property name(" + propertyName + ")");
		}

		return (m != null || f != null)
				&& this.registerValidator(c, isRequired, BeanValidator.createPropertyValidator(value, propertyName));
	}
}
