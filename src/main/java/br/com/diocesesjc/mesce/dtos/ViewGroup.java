package br.com.diocesesjc.mesce.dtos;

import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ViewGroup {
    private String name;
    private String icon;
    private String url;
    private List<ViewScreen> views;
}
