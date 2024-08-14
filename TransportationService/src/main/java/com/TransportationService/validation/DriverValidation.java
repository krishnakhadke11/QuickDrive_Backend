package com.TransportationService.validation;

import com.TransportationService.dto.request.DriverDto;
import com.TransportationService.dto.request.DriverUpdateDto;
import com.TransportationService.entity.Driver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalTime;
import java.util.Optional;
import java.util.regex.Pattern;

@Component
public class DriverValidation {
    @Autowired
    private UserValidation userValidation;

    public void validateDriver(DriverDto driverDto) {
        validateDriverLicence(driverDto.getDriversLicense());

        userValidation.validateUser(driverDto.getUser());

    }
    public void validateDriver(DriverUpdateDto driverUpdateDto) {

        validateDriverLicence(driverUpdateDto.getDriversLicense());

        userValidation.validateUser(driverUpdateDto.getUser());
    }
    public void validateDriverLicence(String license) {
        Pattern pattern = Pattern.compile("^([A-Z]{2}[0-9]{2} )((19|20)[0-9]{2})[0-9]{7}$");
//        System.out.println(license);
        if(!pattern.matcher(license).matches()) {
            throw new IllegalArgumentException("Driver licence is Invalid");
        }
    }
    public void validateTimeInterval(LocalTime startTime, LocalTime endTime) {
        Duration duration = Duration.between(startTime, endTime);
        if(startTime.equals(endTime)){
            throw new IllegalArgumentException("Start and End time should not be same");
        }else if(duration.toHours() < 7){
            throw new IllegalArgumentException("Duration must be greater than or equal to 7 hours");
        }
    }

}
