package ar.lamansys.sgx.backoffice.permissions.impl;

import java.util.Map;
import java.util.function.Supplier;

import ar.lamansys.sgx.backoffice.permissions.BackofficePermissionValidator;
import ar.lamansys.sgx.backoffice.domain.BOMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ar.lamansys.sgx.backoffice.exceptions.PermissionDeniedException;

public class BackofficePermissionByActionValidator<E, I> implements BackofficePermissionValidator<E, I> {
	private final Logger logger;
	private final Map<BOMethod, Supplier<Boolean>> isAllowedByAction;
	private final Supplier<Boolean> defaultValue;

	public BackofficePermissionByActionValidator(
			Map<BOMethod, Supplier<Boolean>> isAllowedByAction,
			Supplier<Boolean> defaultValue
	) {
		this.logger = LoggerFactory.getLogger(getClass());
		this.isAllowedByAction = isAllowedByAction;
		this.defaultValue = defaultValue;
	}

	@Override
	public void assertGetList(E entity) {
		assertTrue(BOMethod.GET_LIST, String.format("(filtro %s)", entity));
	}

	private void assertTrue(BOMethod method, String message) {
		if (isAllowedByAction.getOrDefault(method, defaultValue).get()) {
			logger.debug("Se permite {} con {}", method,  message);
			return;
		}
		throw new PermissionDeniedException(
				String.format("No se permite %s con %s", method,  message)
		);
	}

	@Override
	public void assertGetOne(I id) {
		assertTrue(BOMethod.GET_ONE, String.format("(id %s)", id));
	}

	@Override
	public void assertCreate(E entity) {
		assertTrue(BOMethod.CREATE, String.format("(body %s)", entity));
	}

	@Override
	public void assertUpdate(I id, E entity) {
		assertTrue(BOMethod.UPDATE, String.format("(id %s, body %s)", id, entity));
	}

	@Override
	public void assertDelete(I id) {
		assertTrue(BOMethod.DELETE, String.format("(id %s)", id));
	}
}
