package br.com.diocesesjc.mesce.dtos.request;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class PessoaRequest extends DtoRequest {
    private String address;
    private String birthDate;
    private String email;
    private String phone;
}