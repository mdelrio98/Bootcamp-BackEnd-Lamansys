package ar.lamansys.sgx.backoffice.rest;

import java.util.List;

import javax.validation.Valid;

import ar.lamansys.sgx.backoffice.permissions.BackofficePermissionValidator;
import ar.lamansys.sgx.backoffice.repository.BackofficeRepository;
import ar.lamansys.sgx.backoffice.repository.BackofficeStore;
import ar.lamansys.sgx.backoffice.rest.dto.BackofficeDeleteResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import ar.lamansys.sgx.backoffice.validation.BackofficeEntityValidator;

public abstract class AbstractBackofficeController<E, I> {
	protected final Logger logger;
	protected final BackofficeStore<E, I> store;
	protected final BackofficePermissionValidator<E, I> permissionValidator;
	protected final BackofficeEntityValidator<E, I> entityValidator;

	protected AbstractBackofficeController(
			BackofficeStore<E, I> repository,
			BackofficePermissionValidator<E, I> permissionValidator,
			BackofficeEntityValidator<E, I> entityValidator
	) {
		this.logger = LoggerFactory.getLogger(this.getClass());
		this.store = repository;
		this.permissionValidator = permissionValidator;
		this.entityValidator = entityValidator;
	}

	protected AbstractBackofficeController(
			BackofficeStore<E, I> repository
	) {
		this(
				repository,
				new BackofficePermissionValidatorAdapter<>(),
				new BackofficeEntityValidatorAdapter<>()
		);
	}

	protected AbstractBackofficeController(
			JpaRepository<E, I> repository
	) {
		this(
				new BackofficeRepository<>(repository)
		);
	}

	protected AbstractBackofficeController(
			BackofficeStore<E, I> repository, BackofficePermissionValidatorAdapter<E,I> backofficePermissionValidatorAdapter) {
		this(
				repository,
				backofficePermissionValidatorAdapter,
				new BackofficeEntityValidatorAdapter<>()
		);
	}

	@GetMapping(params = "!ids")
	public @ResponseBody
	Page<E> getList(Pageable pageable, E entity) {
		logger.debug("GET_LIST {}", entity);
		permissionValidator.assertGetList(entity);
		return store.findAll(entity, pageable);
	}

	@GetMapping(params = "ids")
	public @ResponseBody
	Iterable<E> getMany(@RequestParam List<I> ids) {
		return store.findAllById(ids);
	}

	@GetMapping("/{id}")
	public @ResponseBody
	E getOne(@PathVariable("id") I id) {
		logger.debug("GET_ONE[id={}]", id);
		permissionValidator.assertGetOne(id);
		return store.findById(id)
				.orElseThrow(() -> new RuntimeException("Not found " + id));
	}

	@PostMapping
	public @ResponseBody
	E create(@Valid @RequestBody E entity) {
		logger.debug("CREATE {}", entity);
		permissionValidator.assertCreate(entity);
		entityValidator.assertCreate(entity);
		return store.save(entity);
	}


	@PutMapping("/{id}") @Modifying
	public @ResponseBody
	E update(@PathVariable("id") I id, @RequestBody E body) {
		logger.debug("UPDATE[id={}] {}", id, body);
		permissionValidator.assertUpdate(id, body);
		entityValidator.assertUpdate(id, body);
		return store.save(body);
	}

	@DeleteMapping("/{id}")
	public @ResponseBody
    BackofficeDeleteResponse<I> delete(@PathVariable("id") I id) {
		logger.debug("DELETE[id={}]", id);
		permissionValidator.assertDelete(id);
		entityValidator.assertDelete(id);
		store.deleteById(id);

		return new BackofficeDeleteResponse<>(id);
	}

	@DeleteMapping(params = "ids")
	public @ResponseBody
	BackofficeDeleteResponse<List<I>> deleteMany(@RequestParam List<I> ids) {
		logger.debug("DELETE_MANY[ids={}]", ids);
		ids.forEach(id ->  {
			permissionValidator.assertDelete(id);
			entityValidator.assertDelete(id);
			store.deleteById(id);
		});

		return new BackofficeDeleteResponse<>(ids);
	}

}
