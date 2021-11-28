package br.com.diocesesjc.mesce.converter.report;

import br.com.diocesesjc.mesce.dtos.report.PessoaReportResponse;
import br.com.diocesesjc.mesce.dtos.response.PessoaResponse;
import java.util.List;
import java.util.stream.Collectors;

public class PessoaReportConvert extends ReportConverter<PessoaResponse, PessoaReportResponse> {

    String name = "Relatório Geral de Pessoas";

    @Override
    String name() {
        return getName();
    }

    @Override
    List<String> headers() {
        return List.of("Nome", "Telefone", "E-mail");
    }

    @Override
    List<PessoaReportResponse> content(List<PessoaResponse> data) {
        return data.stream()
            .map(p -> PessoaReportResponse.builder()
                .name(p.getName())
                .phone(p.getPhone() != null ? p.getPhone() : "")
                .mail(p.getEmail() != null ? p.getEmail() : "")
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
