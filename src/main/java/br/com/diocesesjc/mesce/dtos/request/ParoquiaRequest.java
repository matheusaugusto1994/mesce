package br.com.diocesesjc.mesce.dtos.request;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ParoquiaRequest extends DtoRequest{
    private String phone;
    private String address;
    private Long regiaoId;
    private Long userId;
}
