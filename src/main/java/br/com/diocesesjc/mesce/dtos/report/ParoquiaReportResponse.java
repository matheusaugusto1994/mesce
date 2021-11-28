package br.com.diocesesjc.mesce.dtos.report;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class ParoquiaReportResponse extends BaseReportContentResponse {
    String phone;
    String regiaoName;
    String userName;
}
