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
        LocalTime startTime = driverDto.getStartTime();
        LocalTime endTime  = driverDto.getEndTime();

        validateTimeInterval(startTime,endTime);

        validateDriverLicence(driverDto.getDriversLicense());

        userValidation.validateUser(driverDto.getUser());

    }
    public void validateDriver(DriverUpdateDto driverUpdateDto) {
        LocalTime startTime = driverUpdateDto.getStartTime();
        LocalTime endTime  = driverUpdateDto.getEndTime();

        validateTimeInterval(startTime,endTime);

        validateDriverLicence(driverUpdateDto.getDriversLicense());

        userValidation.validateUser(driverUpdateDto.getUser());
    }
    public void validateDriverLicence(String license) {
        Pattern pattern = Pattern.compile("^DL\\d{5}$");
        if(!pattern.matcher(license).matches()) {
            throw new IllegalArgumentException("Driver licence is Invalid");
        }
    }
    public void validateTimeInterval(LocalTime startTime, LocalTime endTime) {
        Duration duration = Duration.between(startTime, endTime);
        if(startTime.compareTo(endTime) == 0 ){
            throw new IllegalArgumentException("Start and End time should not be same");
        }else if(duration.toHours() < 7){
            throw new IllegalArgumentException("Duration must be greater than or equal to 7 hours");
        }
    }

}
