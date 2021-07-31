package br.com.diocesesjc.mesce.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PessoaParoquiaRequest {

    private Long pessoa;
    private Long paroquia;
}
