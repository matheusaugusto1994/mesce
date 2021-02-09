package br.com.diocesesjc.mesce.converter.data;

import br.com.diocesesjc.mesce.dtos.request.DtoRequest;
import br.com.diocesesjc.mesce.dtos.response.DtoResponse;
import java.util.List;
import org.springframework.data.domain.Page;

public interface Converter<T, REQ extends DtoRequest, RESP extends DtoResponse> {

    T convert(REQ request);

    RESP convert(T object);

    Page<RESP> convert(Page<T> page);

    List<RESP> convert(List<T> list);
}
