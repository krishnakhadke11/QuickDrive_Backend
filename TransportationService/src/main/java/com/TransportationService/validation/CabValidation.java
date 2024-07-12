package com.TransportationService.validation;

import com.TransportationService.dto.request.CabDto;
import com.TransportationService.dto.request.CabUpdateDto;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
public class CabValidation {
    public void validateCab(CabDto cabDto) {
        String regNo = cabDto.getRegisterNo();

        validateRegisterNo(regNo);
    }
    public void validateCab(CabUpdateDto cabUpdateDto) {
        String regNo = cabUpdateDto.getRegisterNo();

        validateRegisterNo(regNo);
    }

    private void validateRegisterNo(String regNo) {
        Pattern pattern = Pattern.compile("userUpdateDetailsDto");
        if(!pattern.matcher(regNo).matches()){
            throw new IllegalArgumentException("Registration Number is invalid");
        }
    }

}
