package com.TransportationService.service.impl;

import com.TransportationService.entity.Cab;
import com.TransportationService.repository.CabRepository;
import com.TransportationService.service.CabSerive;
import jakarta.transaction.Transactional;
import org.aspectj.lang.annotation.Around;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CabServiceImpl implements CabSerive {

    @Autowired
    private CabRepository cabRepository;

    @Override
    public Cab findCabById(int id) {
        return cabRepository.findById(id).get();
    }

    @Override
    @Transactional
    public Cab addCab(Cab cab) {
        return cabRepository.save(cab);
    }
}
