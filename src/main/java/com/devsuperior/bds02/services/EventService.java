package com.devsuperior.bds02.services;

import com.devsuperior.bds02.dto.EventDTO;
import com.devsuperior.bds02.entities.Event;
import com.devsuperior.bds02.repositories.CityRepository;
import com.devsuperior.bds02.repositories.EventRepository;
import com.devsuperior.bds02.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

@Service
public class EventService {
    @Autowired
    private EventRepository repository;
    @Autowired
    private CityRepository cityRepository;

    @Transactional
    public EventDTO update(EventDTO dto, Long id) {
        try {
            Event aux = auxUpdate(dto, id);
            repository.save(aux);
            return new EventDTO(aux);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Entity not found");
        }
    }


    //Auxiliary methods

    public Event auxUpdate(EventDTO dto, Long id) {
        Event aux = repository.getOne(id);
        aux.setCity(cityRepository.getOne(dto.getCityId()));
        aux.setDate(dto.getDate());
        aux.setName(dto.getName());
        aux.setUrl(dto.getUrl());
        return aux;
    }
}

