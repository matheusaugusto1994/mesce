package br.com.diocesesjc.mesce.repository;

import br.com.diocesesjc.mesce.entity.Paroquia;
import br.com.diocesesjc.mesce.entity.Pessoa;
import br.com.diocesesjc.mesce.entity.PessoaParoquia;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PessoaParoquiaRepository extends JpaRepository<PessoaParoquia, Long> {

    Page<Pessoa> findAllByParoquiaInAndPessoaNameIgnoreCaseContainingOrderByPessoaName(List<Paroquia> paroquia, String name, Pageable pageable);
}
