package br.com.diocesesjc.mesce.dtos.response;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class SetorResponse extends DtoResponse {
    private DtoResponse user;
    private DtoResponse paroquia;
}
