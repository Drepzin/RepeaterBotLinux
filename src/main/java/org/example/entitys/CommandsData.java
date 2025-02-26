package org.example.entitys;

public class CommandsData {

    private String type;
    private Integer keyTyped;
    private Integer coordinateX;
    private Integer coordinateY;
    private Integer mouseButton;

    public CommandsData(){}

    public CommandsData(String type, Integer mouseButton, Integer coordinateY, Integer coordinateX, Integer keyTyped) {
        this.type = type;
        this.mouseButton = mouseButton;
        this.coordinateX = coordinateX;
        this.coordinateY = coordinateY;
        this.keyTyped = keyTyped;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getMouseButton() {
        return mouseButton;
    }

    public void setMouseButton(Integer mouseButton) {
        this.mouseButton = mouseButton;
    }

    public Integer getCoordinateY() {
        return coordinateY;
    }

    public void setCoordinateY(Integer coordinateY) {
        this.coordinateY = coordinateY;
    }

    public Integer getCoordinateX() {
        return coordinateX;
    }

    public void setCoordinateX(Integer coordinateX) {
        this.coordinateX = coordinateX;
    }

    public Integer getKeyTyped() {
        return keyTyped;
    }

    public void setKeyTyped(Integer keyTyped) {
        this.keyTyped = keyTyped;
    }

    @Override
    public String toString() {
        return "CommandsData{" +
                "type='" + type + '\'' +
                ", keyTyped=" + keyTyped +
                ", coordinateX=" + coordinateX +
                ", coordinateY=" + coordinateY +
                ", mouseButton=" + mouseButton +
                '}';
    }
}
