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
        Optional<Cab> optionalCab = cabRepository.findById(id);
        Cab cab = null;
        if (optionalCab.isPresent()) {
            cab = optionalCab.get();
        }else{
            throw new EntityNotFoundException("Cab Not Found");
        }
        return cab;
    }

    @Override
    @Transactional
    public Cab updateCab(Cab cab) {
        boolean isPresent = cabRepository.existsById(cab.getId());
        User user = userRepository.findById(cab.getUser().getId()).get();
        cab.setUser(user);
        Cab updatedCab = null;
        if (isPresent) {
            updatedCab = cabRepository.save(cab);
        }else{
            throw new EntityNotFoundException("Cab Not Found while Updating");
        }
        return updatedCab;
    }

    @Override
    @Transactional
    public void deleteCab(int id) {
        boolean isPresent = cabRepository.existsById(id);
        if (isPresent) {
            cabRepository.deleteById(id);
        }else{
            throw new EntityNotFoundException("Cab Not Found While Deleting");
        }
    }

    @Override
    public User findCabsOwner(int cabId) {
        Cab cab = cabRepository.findById(cabId).get();
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
        User user = userRepository.findById(userId).get();
        cab.setUser(user);
        return cabRepository.save(cab);
    }

}
