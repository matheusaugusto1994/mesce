package br.com.diocesesjc.mesce.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PessoaParoquiaResponse {
    private PessoaResponse pessoa;
    private ParoquiaResponse paroquia;
}
