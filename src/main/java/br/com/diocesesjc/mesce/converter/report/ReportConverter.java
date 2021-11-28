package br.com.diocesesjc.mesce.converter.report;

import br.com.diocesesjc.mesce.dtos.report.BaseReportContentResponse;
import br.com.diocesesjc.mesce.dtos.report.BaseReportResponse;
import br.com.diocesesjc.mesce.dtos.report.RegiaoReportResponse;
import br.com.diocesesjc.mesce.dtos.response.DtoResponse;
import java.util.List;

public abstract class ReportConverter<E extends DtoResponse, BASE extends BaseReportContentResponse> {

    abstract String name();

    abstract List<String> headers();

    abstract List<BASE> content(List<E> data);

    public BaseReportResponse convert(List<E> data) {
        return BaseReportResponse.builder()
            .reportName(name())
            .headers(headers())
            .content(content(data))
            .build();
    }
}
