package br.com.diocesesjc.mesce.dtos.report;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BaseReportResponse {
    String reportName;
    List<String> headers;
    List content;
}
