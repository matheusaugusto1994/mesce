package br.com.diocesesjc.mesce.enums;

public enum ScreenGroup {
    GROUP_ACOES_RAPIDAS("Associar Ministros", "/associar", "icon-note"),
    GROUP_CADASTROS("Cadastros", null, "icon-folder-alt");

    private final String description;
    private final String url;
    private final String icon;

    ScreenGroup(String description, String url, String icon) {
        this.description = description;
        this.url = url;
        this.icon = icon;
    }

    public String getDescription() {
        return description;
    }

    public String getIcon() {
        return icon;
    }

    public String getUrl() {
        return url;
    }
}
