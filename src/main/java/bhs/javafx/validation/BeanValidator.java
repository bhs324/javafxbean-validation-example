package bhs.javafx.validation;

import org.controlsfx.validation.Severity;
import org.controlsfx.validation.ValidationResult;
import org.controlsfx.validation.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.ValidatorFactory;

/**
 * Created by bhs on 2016-09-19.
 */
public interface BeanValidator<T> extends org.controlsfx.validation.Validator<T> {
	Logger LOG = LoggerFactory.getLogger(BeanValidator.class);

	static <T> Validator<T> createPropertyValidator(final T obj, final String propertyName) {
		return createPropertyValidator(obj, propertyName, Severity.ERROR);
	}

	static <T> Validator<T> createPropertyValidator(final T obj, final String propertyName, final Severity severity) {
		return (c, value) -> {
			ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
			javax.validation.Validator validator = validatorFactory.getValidator();
			StringBuilder sbViolationMessage = new StringBuilder();

			for (ConstraintViolation<T> constraintViolations : validator.validateProperty(obj, propertyName)) {
				LOG.warn("VALIDATION FAIL: {}", constraintViolations.getMessage());
				sbViolationMessage.append(String.format("%s\n", constraintViolations.getMessage()));
			}

			return ValidationResult.fromMessageIf(c, sbViolationMessage.toString(), severity, sbViolationMessage.length() > 0);
		};
	}
}