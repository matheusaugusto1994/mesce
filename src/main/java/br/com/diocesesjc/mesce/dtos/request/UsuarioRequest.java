package br.com.diocesesjc.mesce.dtos.request;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class UsuarioRequest extends DtoRequest {
    private boolean blocked;
    private Long pessoaId;
    private Long roleId;
    private String password;
}
