package br.com.diocesesjc.mesce.service;

import br.com.diocesesjc.mesce.converter.data.Converter;
import br.com.diocesesjc.mesce.dtos.request.DtoRequest;
import br.com.diocesesjc.mesce.dtos.response.DtoResponse;
import br.com.diocesesjc.mesce.entity.Usuario;
import br.com.diocesesjc.mesce.enums.RoleType;
import br.com.diocesesjc.mesce.repository.DataQueryFilteredRepository;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;

public abstract class CrudService<
    T,
    REPO extends DataQueryFilteredRepository<T, Long>,
    CONV extends Converter<T, REQ, RESP>,
    REQ extends DtoRequest,
    RESP extends DtoResponse> {

    private final REPO repository;
    private final CONV converter;

    public CrudService(REPO repository, CONV converter) {
        this.repository = repository;
        this.converter = converter;
    }

    public abstract Page<RESP> getDataByResource(String query, Pageable page);

    public Page<RESP> get(String query, Integer page) {
        Usuario usuario = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (usuario.getRole().getName() == RoleType.ROLE_ADMIN || usuario.getRole().getName() == RoleType.ROLE_SUPERVISOR) {
            Page<T> objects = repository.findByNameIgnoreCaseContainingOrderByName(query, PageRequest.of(page, 10));
            return converter.convert(objects);
        }

        return getDataByResource(query, PageRequest.of(page, 10));
    }

    @Transactional
    public T save(REQ request) {
        T object = converter.convert(request);
        return repository.save(object);
    }

    @Transactional
    public void delete(Long id) {
        repository.deleteById(id);
    }

    public List<RESP> getAll() {
        List<T> objects = (List<T>) repository.findAll();
        return converter.convert(objects);
    }

    public long count() {
        return repository.count();
    }

    public RESP getById(Long id) {
        T object = repository.getOne(id);
        return converter.convert(object);
    }
}
