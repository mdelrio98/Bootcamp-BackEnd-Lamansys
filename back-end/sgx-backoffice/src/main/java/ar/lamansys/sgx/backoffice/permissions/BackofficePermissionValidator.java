package ar.lamansys.sgx.backoffice.permissions;

public interface BackofficePermissionValidator<E, I> {

	void assertGetList(E entity);

	void assertGetOne(I id);

	void assertCreate(E entity);

	void assertUpdate(I id, E entity);

	void assertDelete(I id);
}
