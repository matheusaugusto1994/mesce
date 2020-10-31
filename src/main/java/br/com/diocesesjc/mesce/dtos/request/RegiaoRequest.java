package br.com.diocesesjc.mesce.dtos.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegiaoRequest {
    private Long id;
    private String name;
    private Long userId;
}
