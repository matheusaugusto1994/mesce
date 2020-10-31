package br.com.diocesesjc.mesce.dtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Screen {
    private String name;
    private String url;
}
