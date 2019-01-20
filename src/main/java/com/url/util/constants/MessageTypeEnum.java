package com.url.util.constants;

public enum MessageTypeEnum {

    SUCCESS("success", "green", ""),
    DANGER("danger", "red", "");

    private String name;
    private String colour;
    private String icon;

    MessageTypeEnum(String name, String colour, String icon) {
        this.name = name;
        this.colour = colour;
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public String getColour() {
        return colour;
    }

    public String getIcon() {
        return icon;
    }
}
