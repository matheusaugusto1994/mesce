package br.com.diocesesjc.mesce.repository;

import br.com.diocesesjc.mesce.entity.PessoaParoquia;
import java.math.BigInteger;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PessoaParoquiaRepository extends JpaRepository<PessoaParoquia, Long> {

    @Query(nativeQuery = true, value =
        "SELECT p.id " +
            "FROM pessoa_paroquia pr " +
            "INNER JOIN pessoa p ON p.id = pr.pessoa_id " +
            "INNER JOIN paroquia pa ON pa.id = pr.paroquia_id " +
            "WHERE pa.id IN (:paroquiaIds) " +
            "AND LOWER(p.name) LIKE :name " +
        "UNION " +
        "SELECT p.id " +
            "FROM paroquia pa " +
            "INNER JOIN usuario u ON u.id = pa.user_id " +
            "INNER JOIN pessoa p ON p.id = u.pessoa_id " +
            "WHERE pa.id IN (:paroquiaIds) " +
            "AND LOWER(p.name) LIKE :name ")
    Page<BigInteger> findAllPessoaIdsByParoquiaAndName(
        @Param("paroquiaIds") List<Long> paroquiaIds,
        @Param("name") String name,
        Pageable pageable);

    PessoaParoquia findByPessoaId(Long id);

    void deleteByPessoaIdAndParoquiaId(Long pessoaId, Long paroquiaId);

    void deleteAllByPessoaId(Long pessoaId);
}
