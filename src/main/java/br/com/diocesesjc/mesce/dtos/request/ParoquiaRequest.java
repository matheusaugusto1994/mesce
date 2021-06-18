package br.com.diocesesjc.mesce.dtos.request;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ParoquiaRequest extends DtoRequest{
    private Long cep;
    private String phone;
    private String address;
    private Long regiaoId;
    private Long userId;
    private String numberAddress;
}
