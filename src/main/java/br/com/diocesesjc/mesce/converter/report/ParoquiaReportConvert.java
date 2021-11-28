package br.com.diocesesjc.mesce.converter.report;

import br.com.diocesesjc.mesce.dtos.report.ParoquiaReportResponse;
import br.com.diocesesjc.mesce.dtos.response.ParoquiaResponse;
import br.com.diocesesjc.mesce.utils.Constants;
import java.util.List;
import java.util.stream.Collectors;

public class ParoquiaReportConvert extends ReportConverter<ParoquiaResponse, ParoquiaReportResponse> {

    private String name = "Relatório Geral de Paróquias";

    @Override
    String name() {
        return name;
    }

    @Override
    List<String> headers() {
        return List.of("Paróquia", "Telefone", "Região", "Coordenador", "Pessoas");
    }

    @Override
    List<ParoquiaReportResponse> content(List<ParoquiaResponse> data) {
        return data.stream()
            .map(p -> ParoquiaReportResponse.builder()
                .name(p.getName())
                .phone(p.getPhone())
                .regiaoName(p.getRegiao().getName())
                .userName(p.getUser() != null ? p.getUser().getName() : "")
                .link(Constants.TOTAL_PESSOAS + "/" + p.getId())
                .build())
            .collect(Collectors.toList());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
