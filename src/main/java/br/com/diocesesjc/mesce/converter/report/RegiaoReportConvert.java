package br.com.diocesesjc.mesce.converter.report;

import br.com.diocesesjc.mesce.dtos.report.RegiaoReportResponse;
import br.com.diocesesjc.mesce.dtos.response.RegiaoResponse;
import java.util.List;
import java.util.stream.Collectors;

public class RegiaoReportConvert implements ReportConverter<RegiaoReportResponse, RegiaoResponse> {

    @Override
    public String name() {
        return "Relatório Geral de Regiões";
    }

    @Override
    public List<String> headers() {
        return List.of("Região", "Coordenador");
    }

    @Override
    public List<RegiaoReportResponse> convert(List<RegiaoResponse> list) {
        return list.stream()
            .map(regiao -> RegiaoReportResponse.builder()
                .name(regiao.getName())
                .userName(regiao.getUser().getName())
                .build())
            .collect(Collectors.toList());
    }
}
