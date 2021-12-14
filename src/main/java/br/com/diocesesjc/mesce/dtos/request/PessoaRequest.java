package br.com.diocesesjc.mesce.dtos.request;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data

public class PessoaRequest extends DtoRequest {
    private String address;
    private String postalCode;
    private String numberAddress;
    private String email;
    private String phone;
    private String photoData;
    private String whatsApp;
}
