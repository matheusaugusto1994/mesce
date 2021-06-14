package br.com.diocesesjc.mesce.dtos.response;

import java.time.LocalDate;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class PessoaResponse extends DtoResponse {
    private String address;
    private String email;
    private String phone;
    private String whatsApp;
}
