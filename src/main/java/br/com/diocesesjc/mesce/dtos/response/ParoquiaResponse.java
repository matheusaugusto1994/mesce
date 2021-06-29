package br.com.diocesesjc.mesce.dtos.response;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class ParoquiaResponse extends DtoResponse {
    private String cep;
    private String address;
    private String numberAddress;
    private String phone;
    private DtoResponse regiao;
    private DtoResponse user;
}
