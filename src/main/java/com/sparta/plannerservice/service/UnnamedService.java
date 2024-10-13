package com.sparta.plannerservice.service;

import com.sparta.plannerservice.entity.Unnamed;
import com.sparta.plannerservice.repository.UnnamedRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UnnamedService {
    private final UnnamedRepository unnamedRepository;

    @PersistenceContext
    private final EntityManager em;

    @Transactional
    public void unnamedService() {
        Unnamed un = new Unnamed();
        em.persist(un);
        unnamedRepository.save(un);
    }
}
