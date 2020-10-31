package br.com.diocesesjc.mesce.dtos;

import com.google.common.collect.Lists;
import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ViewGroup {
    private String name;
    private String icon;
    private List<Screen> views = Lists.newArrayList();
}
