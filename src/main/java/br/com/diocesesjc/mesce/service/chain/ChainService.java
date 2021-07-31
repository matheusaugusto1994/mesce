package br.com.diocesesjc.mesce.service.chain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ChainService {

    Page get(String query, Pageable page);

    Page nextLevelChain(String query, Pageable page);

}
