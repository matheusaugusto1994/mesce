package br.com.diocesesjc.mesce.enums;

public enum RoleType {
    ROLE_ADMIN("Administrador"),
    ROLE_SUPERVISOR("Supervisor/Padre"),
    ROLE_COORDENADOR_REGIAO("Coordenador de Região Pastoral"),
    ROLE_COORDENADOR_PAROQUIAL("Coordenador Paroquial/Padre Paroquial"),
//    ROLE_COORDENADOR_MESCE("Coordenador MESCE"),
    ROLE_MINISTRO("Ministro");

    private final String description;

    RoleType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
