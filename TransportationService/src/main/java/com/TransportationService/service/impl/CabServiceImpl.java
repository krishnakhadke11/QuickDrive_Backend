package com.TransportationService.service.impl;

import com.TransportationService.entity.Cab;
import com.TransportationService.entity.User;
import com.TransportationService.repository.CabRepository;
import com.TransportationService.repository.UserRepository;
import com.TransportationService.service.CabService;
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

    @Override
    public Cab findCabById(int id) {
        Cab cab = cabRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Cab Not Found"));

        return cab;
    }

    @Override
    @Transactional
    public Cab updateCab(Cab cab) {
        if(!cabRepository.existsById(cab.getId())){
            throw new EntityNotFoundException("Cab Not Found while Updating");
        }
        User user = userRepository.findById(cab.getUser().getId())
                .orElseThrow(() -> new EntityNotFoundException("User Entity Not Found"));
        cab.setUser(user);
        Cab updatedCab = cabRepository.save(cab);
        return updatedCab;
    }

    @Override
    @Transactional
    public void deleteCab(int id) {
        if (!cabRepository.existsById(id)) {
        }else{
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
    public Cab addCab(Cab cab) {
        int userId = cab.getUser().getId();
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User or Owner Not found"));
        cab.setUser(user);
        return cabRepository.save(cab);
    }

}
