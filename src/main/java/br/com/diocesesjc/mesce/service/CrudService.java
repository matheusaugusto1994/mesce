package br.com.diocesesjc.mesce.service;

import br.com.diocesesjc.mesce.converter.data.Converter;
import br.com.diocesesjc.mesce.dtos.request.DtoRequest;
import br.com.diocesesjc.mesce.dtos.response.DtoResponse;
import br.com.diocesesjc.mesce.repository.DataQueryFilteredRepository;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public class CrudService<
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

    public void save(REQ request) {
        T object = converter.convert(request);
        repository.saveAndFlush(object);
    }

    public Page<RESP> get(String query, Integer page) {
        Page<T> objects = repository.findByNameIgnoreCaseContainingOrderByName(query, PageRequest.of(page, 10));
        return converter.convert(objects);
    }

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

}
