package com.TransportationService.validation;

import  com.TransportationService.dto.request.CabDto;
import com.TransportationService.dto.request.CabUpdateDto;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
public class CabValidation {
    public void validateCab(CabDto cabDto) {
        String regNo = cabDto.getRegisterNo();
        int seat = cabDto.getSeatingCapacity();

        validateSeatingCapacity(seat);

        validateRegisterNo(regNo);
    }


    public void validateCab(CabUpdateDto cabUpdateDto) {
        String regNo = cabUpdateDto.getRegisterNo();
        int seat = cabUpdateDto.getSeatingCapacity();

        validateSeatingCapacity(seat);

        validateRegisterNo(regNo);
    }

    private void validateSeatingCapacity(int seat) {
        if(seat != 5 && seat != 7){
            throw new IllegalArgumentException("Invalid Seating Capacity");
        }
    }

    private void validateRegisterNo(String regNo) {
        Pattern pattern = Pattern.compile("^[A-Z]{2}[0-9]{2}[A-Z]{2}[0-9]{4}");
        if(!pattern.matcher(regNo).matches()){
            throw new IllegalArgumentException("Registration Number is invalid");
        }
    }

}
