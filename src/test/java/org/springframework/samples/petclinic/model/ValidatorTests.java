package org.springframework.samples.petclinic.model;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.Locale;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.junit.jupiter.api.Test;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

/**
 * @author Michael Isvy Simple test to make sure that Bean Validation is working (useful
 * when upgrading to a new version of Hibernate Validator/ Bean Validation)
 */
class ValidatorTests {

	private Validator createValidator() {
		LocalValidatorFactoryBean localValidatorFactoryBean = new LocalValidatorFactoryBean();
		localValidatorFactoryBean.afterPropertiesSet();
		return localValidatorFactoryBean;
	}

	@Test
	void shouldNotValidateWhenFirstNameEmpty() {

		LocaleContextHolder.setLocale(Locale.ENGLISH);
		Person person = new Person();
		person.setFirstName("");
		person.setLastName("smith");

		Validator validator = createValidator();
		Set<ConstraintViolation<Person>> constraintViolations = validator.validate(person);

		assertThat(constraintViolations.size()).isEqualTo(1);
		ConstraintViolation<Person> violation = constraintViolations.iterator().next();
		assertThat(violation.getPropertyPath().toString()).isEqualTo("firstName");
		assertThat(violation.getMessage()).isEqualTo("must not be empty");
	}
	
	@Test
	void shouldNotValidateSancionWhenDescripcionEmpty() {

		Sancion sancion = new Sancion();
		sancion.setFechaFin(LocalDate.of(2022, 1, 23));
		sancion.setDescripcion("");

		Validator validator = createValidator();
		Set<ConstraintViolation<Sancion>> constraintViolations = validator.validate(sancion);

		assertThat(constraintViolations.size()).isEqualTo(1);
		ConstraintViolation<Sancion> violation = constraintViolations.iterator().next();
		assertThat(violation.getPropertyPath().toString()).isEqualTo("descripcion");
		assertThat(violation.getMessage()).isEqualTo("must not be blank");
	}
	
}
