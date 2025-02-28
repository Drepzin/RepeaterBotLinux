package org.example.entitys;

public class NeoCommandsData {
    private String type;
    private Integer keyTyped;
    private Integer coordinateX;
    private Integer coordinateY;
    private Integer mouseButton;
    private Integer timePressed;

    public NeoCommandsData(){}

    public NeoCommandsData(String type, Integer mouseButton, Integer coordinateY,
                           Integer coordinateX, Integer keyTyped, Integer timePressed) {
        this.type = type;
        this.mouseButton = mouseButton;
        this.coordinateX = coordinateX;
        this.coordinateY = coordinateY;
        this.keyTyped = keyTyped;
        this.timePressed = timePressed;
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

    public Integer getTimePressed() {
        return timePressed;
    }

    public void setTimePressed(Integer timePressed) {
        this.timePressed = timePressed;
    }

    @Override
    public String toString() {
        return "NeoCommandsData{" +
                "type='" + type + '\'' +
                ", keyTyped=" + keyTyped +
                ", coordinateX=" + coordinateX +
                ", coordinateY=" + coordinateY +
                ", mouseButton=" + mouseButton +
                ", timePressed=" + timePressed +
                '}';
    }
}

