package br.com.diocesesjc.mesce.service;

import static br.com.diocesesjc.mesce.utils.Constants.*;

import br.com.diocesesjc.mesce.converter.report.ParoquiaReportConvert;
import br.com.diocesesjc.mesce.converter.report.PessoaReportConvert;
import br.com.diocesesjc.mesce.converter.report.RegiaoReportConvert;
import br.com.diocesesjc.mesce.dtos.report.BaseReportResponse;
import br.com.diocesesjc.mesce.dtos.response.DtoResponse;
import br.com.diocesesjc.mesce.dtos.response.ParoquiaResponse;
import br.com.diocesesjc.mesce.dtos.response.PessoaResponse;
import br.com.diocesesjc.mesce.dtos.response.RegiaoResponse;
import br.com.diocesesjc.mesce.utils.Constants;
import com.google.common.collect.Lists;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class ReportService {

    private final RegiaoService regiaoService;
    private final ParoquiaService paroquiaService;
    private final SetorService setorService;
    private final PessoaService pessoaService;
    private final PessoaParoquiaService pessoaParoquiaService;

    public ReportService(RegiaoService regiaoService, ParoquiaService paroquiaService,
                         SetorService setorService, PessoaService pessoaService, PessoaParoquiaService pessoaParoquiaService) {
        this.regiaoService = regiaoService;
        this.paroquiaService = paroquiaService;
        this.setorService = setorService;
        this.pessoaService = pessoaService;
        this.pessoaParoquiaService = pessoaParoquiaService;
    }

    public Map<String, Long> getAllCounters() {
        return Map.of(
            TOTAL_REGIOES, regiaoService.count(),
            TOTAL_PAROQUIAS, paroquiaService.count(),
            TOTAL_SETORES, setorService.count(),
            TOTAL_PESSOAS, pessoaService.count()
        );
    }

    public BaseReportResponse getDataByTypeCard(String typeCard, String id) {
        switch (typeCard) {
            case TOTAL_REGIOES: return new RegiaoReportConvert().convert(regiaoService.getAll());
            case TOTAL_PAROQUIAS: return createParoquiaReportByRegiao(id);
//            case TOTAL_SETORES: return setorService.getAll();
            case TOTAL_PESSOAS: return createPessoaReportByParoquia(id);
        }
        return null;
    }

    private BaseReportResponse createParoquiaReportByRegiao(String id) {
        ParoquiaReportConvert converter = new ParoquiaReportConvert();

        return Optional.ofNullable(id)
            .filter(i -> !i.equalsIgnoreCase("null"))
            .map(i -> {
                RegiaoResponse regiao = regiaoService.getById(Long.valueOf(i));
                String newName = converter.getName() + " da região: " + regiao.getName();
                converter.setName(newName);
                return paroquiaService.getAllByRegiao(regiao.getId());
            })
            .map(converter::convert)
            .orElse(converter.convert(paroquiaService.getAll()));
    }

    private BaseReportResponse createPessoaReportByParoquia(String id) {
        PessoaReportConvert converter = new PessoaReportConvert();

        return Optional.ofNullable(id)
            .filter(i -> !i.equalsIgnoreCase("null"))
            .map(i -> {
                ParoquiaResponse paroquia = paroquiaService.getById(Long.valueOf(i));
                String newName = converter.getName() + " da paróquia: " + paroquia.getName();
                converter.setName(newName);
                return pessoaParoquiaService.getAllPessoaByParoquia(paroquia.getId());
            })
            .map(converter::convert)
            .orElse(converter.convert(pessoaService.getAll()));
    }
}
