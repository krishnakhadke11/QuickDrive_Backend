package com.TransportationService.service.impl;

import com.TransportationService.dto.request.CabDto;
import com.TransportationService.dto.request.CabUpdateDto;
import com.TransportationService.entity.Cab;
import com.TransportationService.entity.User;
import com.TransportationService.repository.CabRepository;
import com.TransportationService.repository.UserRepository;
import com.TransportationService.service.CabService;
import com.TransportationService.validation.CabValidation;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CabServiceImpl implements CabService {

    @Autowired
    private CabRepository cabRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CabValidation cabValidation;

    @Override
    public Cab findCabById(int id) {
        Cab cab = cabRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Cab Not Found"));

        return cab;
    }

    @Override
    @Transactional
    public Cab updateCab(CabUpdateDto cabUpdateDto) {
        cabValidation.validateCab(cabUpdateDto);

        if(!cabRepository.existsById(cabUpdateDto.getId())){
            throw new EntityNotFoundException("Cab Not Found while Updating");
        }
        User user = userRepository.findById(cabUpdateDto.getUser().getId())
                .orElseThrow(() -> new EntityNotFoundException("User Entity Not Found"));

        Cab cab = new Cab();
        cab.setRegisterNo(cabUpdateDto.getRegisterNo());
        cab.setSeatingCapacity(cabUpdateDto.getSeatingCapacity());
        cab.setColor(cabUpdateDto.getColor());
        cab.setModel(cabUpdateDto.getModel());
        cab.setUser(user);
        return cabRepository.save(cab);
    }

    @Override
    @Transactional
    public void deleteCab(int id) {
        if (!cabRepository.existsById(id)) {
            throw new EntityNotFoundException("Cab Not Found While Deleting");
        }

        cabRepository.deleteById(id);
    }

    @Override
    public User findCabsOwner(int cabId) {
        Cab cab = cabRepository.findById(cabId)
                .orElseThrow(() -> new EntityNotFoundException("Cab Not Found"));
        User user = cab.getUser();
        return user;
    }

    @Override
    public List<Cab> findAllCabs() {
        return cabRepository.findAll();
    }

    @Override
    @Transactional
    public Cab addCab(CabDto cabDto) {
        //Validation
        cabValidation.validateCab(cabDto);

        User user = userRepository.findById(cabDto.getUser().getId())
                .orElseThrow(() -> new EntityNotFoundException("User or Owner Not found"));
        System.out.println("Seating capacity : " + cabDto.getSeatingCapacity());

        Cab cab = new Cab();
        cab.setRegisterNo(cabDto.getRegisterNo());
        cab.setSeatingCapacity(cabDto.getSeatingCapacity());
        cab.setColor(cabDto.getColor());
        cab.setModel(cabDto.getModel());
        cab.setUser(user);
        return cabRepository.save(cab);
    }

}
