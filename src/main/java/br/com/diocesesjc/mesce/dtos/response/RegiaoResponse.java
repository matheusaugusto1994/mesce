package br.com.diocesesjc.mesce.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegiaoResponse {
    private Long id;
    private String name;
    private Long userId;
    private String userName;
}
