package br.com.diocesesjc.mesce.enums;

public enum RoleType {
    ROLE_ADMIN("Administrador"),
    ROLE_SUPERVISOR("Supervisor"),
    ROLE_COORDENADOR_REGIAO("Coordenador de região"),
    ROLE_COORDENADOR_PASTORAL("Coordenador de pastoral"),
    ROLE_COORDENADOR_NUCLEO("Coordenador de nucleo"),
    ROLE_AGENTE("Agente");

    private final String description;

    RoleType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
