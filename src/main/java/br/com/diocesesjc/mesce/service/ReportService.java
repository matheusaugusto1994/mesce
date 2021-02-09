package br.com.diocesesjc.mesce.service;

import static br.com.diocesesjc.mesce.utils.Constants.*;

import br.com.diocesesjc.mesce.dtos.response.DtoResponse;
import br.com.diocesesjc.mesce.utils.Constants;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public class ReportService {

    private final RegiaoService regiaoService;
    private final ParoquiaService paroquiaService;
    private final SetorService setorService;
    private final PessoaService pessoaService;

    public ReportService(RegiaoService regiaoService, ParoquiaService paroquiaService,
                         SetorService setorService, PessoaService pessoaService) {
        this.regiaoService = regiaoService;
        this.paroquiaService = paroquiaService;
        this.setorService = setorService;
        this.pessoaService = pessoaService;
    }

    public Map<String, Long> getAllCounters() {
        return Map.of(
            TOTAL_REGIOES, regiaoService.count(),
            TOTAL_PAROQUIAS, paroquiaService.count(),
            TOTAL_SETORES, setorService.count(),
            TOTAL_PESSOAS, pessoaService.count()
        );
    }

    public List<? extends DtoResponse> getDataByTypeCard(String typeCard) {
        switch (typeCard) {
            case TOTAL_REGIOES: return regiaoService.getAll();
            case TOTAL_PAROQUIAS: return paroquiaService.getAll();
            case TOTAL_SETORES: return setorService.getAll();
            case TOTAL_PESSOAS: return pessoaService.getAll();
        }
        return null;
    }
}
