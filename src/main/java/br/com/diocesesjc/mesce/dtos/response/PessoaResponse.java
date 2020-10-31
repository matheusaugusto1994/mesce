package br.com.diocesesjc.mesce.dtos.response;

import java.time.LocalDate;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class PessoaResponse extends DtoResponse {
    private String address;
    private LocalDate birthDate;
    private String email;
    private String phone;
}
