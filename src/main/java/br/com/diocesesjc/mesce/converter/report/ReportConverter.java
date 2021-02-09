package br.com.diocesesjc.mesce.converter.report;

import br.com.diocesesjc.mesce.dtos.report.ReportResponse;
import br.com.diocesesjc.mesce.dtos.response.DtoResponse;
import java.util.List;

public interface ReportConverter<RESP extends ReportResponse, E extends DtoResponse> {

    String name();

    List<String> headers();

    List<RESP> convert(List<E> list);
}
