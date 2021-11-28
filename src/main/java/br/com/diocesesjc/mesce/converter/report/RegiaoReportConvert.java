package br.com.diocesesjc.mesce.converter.report;

import br.com.diocesesjc.mesce.dtos.report.RegiaoReportResponse;
import br.com.diocesesjc.mesce.dtos.response.RegiaoResponse;
import br.com.diocesesjc.mesce.utils.Constants;
import java.util.List;
import java.util.stream.Collectors;

public class RegiaoReportConvert extends ReportConverter<RegiaoResponse, RegiaoReportResponse> {

    @Override
    String name() {
        return "Relatório Geral de Regiões";
    }

    @Override
    List<String> headers() {
        return List.of("Região", "Coordenador", "Paróquias");
    }

    @Override
    List<RegiaoReportResponse> content(List<RegiaoResponse> data) {
        return data.stream()
            .map(r -> RegiaoReportResponse.builder()
                .name(r.getName())
                .userName(r.getUser().getName())
                .link(Constants.TOTAL_PAROQUIAS + "/" + r.getId())
                .build())
            .collect(Collectors.toList());
    }
}
