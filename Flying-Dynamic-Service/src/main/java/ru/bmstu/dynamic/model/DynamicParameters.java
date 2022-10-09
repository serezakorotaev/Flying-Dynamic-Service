package ru.bmstu.dynamic.model;

import java.util.Objects;

public class DynamicParameters {
//уск.своб падения, геопот высота, температура, давление, плотность, скорость звука
    private String high;


    //Ускорение своб падения
    private String accelerOfGravity;
    //геопот высота
    private String geopotHigh;
    //температура
    private String temperature;
    //давление
    private String pressure;
    //плотность
    private String density;
    //скорость звука
    private String velocityOfSound;

    public String getAccelerOfGravity() {
        return accelerOfGravity;
    }

    public void setAccelerOfGravity(String accelerOfGravity) {
        this.accelerOfGravity = accelerOfGravity;
    }

    public String getGeopotHigh() {
        return geopotHigh;
    }

    public void setGeopotHigh(String geopotHigh) {
        this.geopotHigh = geopotHigh;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getPressure() {
        return pressure;
    }

    public void setPressure(String pressure) {
        this.pressure = pressure;
    }

    public String getDensity() {
        return density;
    }

    public void setDensity(String density) {
        this.density = density;
    }

    public String getVelocityOfSound() {
        return velocityOfSound;
    }

    public void setVelocityOfSound(String velocityOfSound) {
        this.velocityOfSound = velocityOfSound;
    }

    public String getHigh() {
        return high;
    }

    public void setHigh(String high) {
        this.high = high;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        DynamicParameters that = (DynamicParameters) o;
        return Objects.equals(accelerOfGravity , that.accelerOfGravity) && Objects.equals(geopotHigh , that.geopotHigh) && Objects.equals(temperature , that.temperature) && Objects.equals(pressure , that.pressure) && Objects.equals(density , that.density) && Objects.equals(velocityOfSound , that.velocityOfSound);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accelerOfGravity , geopotHigh , temperature , pressure , density , velocityOfSound);
    }

    @Override
    public String toString() {
        return "DynamicParameters{" +
                "accelerOfGravity=" + accelerOfGravity +
                ", geopotHigh=" + geopotHigh +
                ", temperature=" + temperature +
                ", pressure=" + pressure +
                ", density=" + density +
                ", velocityOfSound=" + velocityOfSound +
                '}';
    }
}
