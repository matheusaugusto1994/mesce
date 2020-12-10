package br.com.diocesesjc.mesce.dtos.request;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class SetorRequest extends DtoRequest{
    Long userId;
    Long paroquiaId;
}
