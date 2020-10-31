package br.com.diocesesjc.mesce.dtos.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PessoaRequest {
    private Long id;
    private String name;
    private String address;
    private String birthDate;
    private String email;
    private String phone;
}
