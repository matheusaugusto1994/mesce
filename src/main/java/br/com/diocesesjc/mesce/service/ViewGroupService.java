package br.com.diocesesjc.mesce.service;

import br.com.diocesesjc.mesce.dtos.Screen;
import br.com.diocesesjc.mesce.dtos.ViewGroup;
import com.google.common.collect.Lists;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ViewGroupService {

    public List<ViewGroup> getAll() {
        return Lists.newArrayList(
            ViewGroup.builder().name("Cadastros").icon("icon-folder-alt").views(getAllRegister()).build()
        );
    }

    public List<Screen> getAllRegister() {
        return Lists.newArrayList(
            Screen.builder().name("Paróquias").url("/paroquias").build(),
            Screen.builder().name("Pessoas").url("/pessoas").build(),
            Screen.builder().name("Setores").url("/setores").build(),
            Screen.builder().name("Regiões").url("/regioes").build(),
            Screen.builder().name("Usuarios").url("/usuarios").build()
        );
    }
}
