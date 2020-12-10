package br.com.diocesesjc.mesce.converter;

import br.com.diocesesjc.mesce.dtos.request.DtoRequest;
import br.com.diocesesjc.mesce.dtos.response.DtoResponse;
import java.util.List;
import org.springframework.data.domain.Page;

public abstract class Converter<T, REQ extends DtoRequest, RESP extends DtoResponse> {

    public abstract T convert(REQ request);
    public abstract RESP convert(T object);
    public abstract Page<RESP> convert(Page<T> page);
    public abstract List<RESP> convert(List<T> list);
}
