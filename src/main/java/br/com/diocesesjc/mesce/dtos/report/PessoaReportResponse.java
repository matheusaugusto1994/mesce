package br.com.diocesesjc.mesce.dtos.report;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class PessoaReportResponse extends BaseReportContentResponse {
    String phone;
    String mail;
}
