package ru.bmstu.dynamic.service;

import org.springframework.stereotype.Service;
import ru.bmstu.dynamic.model.DynamicParameters;

import java.math.BigDecimal;
import java.math.MathContext;

@Service
public class DynamicService {

    private static final double ACCELLER_ON_GRAVITY_ON_ECVATOR = 9.80665d;
    private static final double RADIUS_OF_EARTH = 6356767;
    private static final double R = 287.05287;

    public DynamicParameters getParameterByHigh(Double high) {
        DynamicParameters dynamicParameters = new DynamicParameters();

        dynamicParameters.setHigh(BigDecimal.valueOf(high).round(new MathContext(6)).toString().replace(".", ","));

        dynamicParameters.setAccelerOfGravity(
                BigDecimal.valueOf(getAccelerationOfGravityOnHigh(high)).round(new MathContext(6)).toString().replace(".", ",")
        );
        dynamicParameters.setGeopotHigh(
                BigDecimal.valueOf(getGeopotencialHigh(high)).round(new MathContext(6)).toString().replace(".", ",")
        );
        dynamicParameters.setTemperature(
                BigDecimal.valueOf(getTemperature(getGeopotencialHigh(high))).round(new MathContext(6)).toString().replace(".", ",")
        );
        dynamicParameters.setPressure(
                BigDecimal.valueOf(getPressure(getGeopotencialHigh(high) , getTemperature(getGeopotencialHigh(high)))).round(new MathContext(7)).toString().replace(".", ",")
        );
        dynamicParameters.setDensity(
                BigDecimal.valueOf(getDensity(high)).round(new MathContext(6)).toString().replace(".", ",")
        );
        dynamicParameters.setVelocityOfSound(
                BigDecimal.valueOf(20.0468 * Math.sqrt(getTemperature(getGeopotencialHigh(high)))).round(new MathContext(6)).toString().replace(".", ",")
        );
        return dynamicParameters;
    }

    public Double getAccelerationOfGravityOnHigh(Double high) {
        return ACCELLER_ON_GRAVITY_ON_ECVATOR *
                (RADIUS_OF_EARTH / (RADIUS_OF_EARTH + high)) *
                (RADIUS_OF_EARTH / (RADIUS_OF_EARTH + high));
    }

    public Double getGeopotencialHigh(Double high) {
        return RADIUS_OF_EARTH * high / (RADIUS_OF_EARTH + high);
    }

    public Double getTemperature(Double geopotentialHigh) {
        if (geopotentialHigh >= -2001 && geopotentialHigh < 0) {
            return 301.15 + (-0.0065) * (geopotentialHigh - (-2000));
        } else if (geopotentialHigh >= 0 && geopotentialHigh < 11000) {
            return 288.15 + (-0.0065) * (geopotentialHigh - 0);
        } else if (geopotentialHigh >= 11000 && geopotentialHigh < 20000) { // b =0
            return 216.65;
        } else if (geopotentialHigh >= 20000 && geopotentialHigh < 32000) {
            return 216.65 + (0.0010) * (geopotentialHigh - 20000);
        } else if (geopotentialHigh >= 32000 && geopotentialHigh < 47000) {
            return 228.65 + (0.0028) * (geopotentialHigh - 32000);
        } else if (geopotentialHigh >= 47000 && geopotentialHigh < 51000) { // b =0
            return 270.65;
        } else if (geopotentialHigh >= 51000 && geopotentialHigh < 71000) {
            return 270.65 + (-0.0028) * (geopotentialHigh - 51000);
        } else if (geopotentialHigh >= 71000 && geopotentialHigh < 85000) {
            return 214.65 + (-0.0020) * (geopotentialHigh - 71000);
        } else if (geopotentialHigh >= 85000 && geopotentialHigh < 94000) {
            return 186.65;
        } else {
            return 273.15;
        }
    }

    public Double getPressure(Double geopotentialHigh , Double temperature) {
        double pressure = 0.0;
        if (geopotentialHigh >= -2001 && geopotentialHigh < 0) {
            pressure = Math.log10(127773.0) - ACCELLER_ON_GRAVITY_ON_ECVATOR / ((-0.0065) * R) * Math.log10(temperature / 301.15);
        } else if (geopotentialHigh >= 0 && geopotentialHigh < 11000) {
            pressure = Math.log10(101325) - ACCELLER_ON_GRAVITY_ON_ECVATOR / ((-0.0065) * R) * Math.log10(temperature / 288.15);
        } else if (geopotentialHigh >= 11000 && geopotentialHigh < 20000) { // b =0
            pressure = Math.log10(22632.0) - (0.434294 * ACCELLER_ON_GRAVITY_ON_ECVATOR) * (geopotentialHigh - 11000) / (R * temperature);
        } else if (geopotentialHigh >= 20000 && geopotentialHigh < 32000) {
            pressure = Math.log10(5474.87) - ACCELLER_ON_GRAVITY_ON_ECVATOR / ((0.0010) * R) * Math.log10(temperature / 216.65);
        } else if (geopotentialHigh >= 32000 && geopotentialHigh < 47000) {
            pressure = Math.log10(868.014) - ACCELLER_ON_GRAVITY_ON_ECVATOR / ((0.0028) * R) * Math.log10(temperature / 228.65);
        } else if (geopotentialHigh >= 47000 && geopotentialHigh < 51000) { // b =0
            pressure = Math.log10(110.906) - (0.434294 * ACCELLER_ON_GRAVITY_ON_ECVATOR) * (geopotentialHigh - 47000) / (R * temperature);
        } else if (geopotentialHigh >= 51000 && geopotentialHigh < 71000) {
            pressure = Math.log10(66.9384) - ACCELLER_ON_GRAVITY_ON_ECVATOR / ((-0.0028) * R) * Math.log10(temperature / 270.65);
        } else if (geopotentialHigh >= 71000 && geopotentialHigh < 85000) {
            pressure = Math.log10(3.95639) - ACCELLER_ON_GRAVITY_ON_ECVATOR / ((-0.0020) * R) * Math.log10(temperature / 214.65);
        } else if (geopotentialHigh >= 85000 && geopotentialHigh < 94000) {
            pressure = Math.log10(8.86272) - (0.434294 * ACCELLER_ON_GRAVITY_ON_ECVATOR) * (geopotentialHigh - 85000) / (R * temperature);
        }
        return Math.pow(10 , pressure);
    }

    public Double getDensity(Double high) {
        return getPressure(getGeopotencialHigh(high) , getTemperature(getGeopotencialHigh(high))) /
                (R*getTemperature(getGeopotencialHigh(high)));
    }
}
