package br.com.diocesesjc.mesce.controller;

import br.com.diocesesjc.mesce.service.ReportService;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/report")
public class ReportController {

    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping("/counts")
    public ResponseEntity<Map<String, Long>> counts() {
        return ResponseEntity.ok(reportService.getAllCounters());
    }

    @GetMapping("/general/{typeCard}")
    public ResponseEntity getReportCard(@PathVariable String typeCard) {
        return ResponseEntity.ok(reportService.getDataByTypeCard(typeCard));
    }
}
