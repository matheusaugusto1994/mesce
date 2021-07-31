package br.com.diocesesjc.mesce.enums;

public enum ScreenType {
    SCREEN_PESSOAS("Pessoas", "/pessoas"),
    SCREEN_USUARIOS("Usuários", "/usuarios"),
    SCREEN_REGIAO("Regiões", "/regioes"),
    SCREEN_PAROQUIAS("Paróquias", "/paroquias"),
    SCREEN_SETORES("Setores", "/setores"),
    SCREEN_ACOES_RAPIDAS("Associar ministros", "/associar");

    private final String description;
    private final String pathUrl;

    ScreenType(String description, String pathUrl) {
        this.description = description;
        this.pathUrl = pathUrl;
    }

    public String getDescription() {
        return description;
    }

    public String getPathUrl() {
        return pathUrl;
    }
}
