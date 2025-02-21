package com.viju.andaluciaskills.services;

import com.viju.andaluciaskills.entity.EvaluacionItem;
import com.viju.andaluciaskills.repository.EvaluacionItemRepository;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import java.util.Optional;

@Service
public class EvaluacionItemService {

    @Autowired
    private EvaluacionItemRepository evaluacionItemRepository;

    public List<EvaluacionItem> getAllEvaluacionItems() {
        return evaluacionItemRepository.findAll();
    }

    public Optional<EvaluacionItem> getEvaluacionItemById(Integer id) {
        return evaluacionItemRepository.findById(id);
    }

    public EvaluacionItem saveEvaluacionItem(EvaluacionItem evaluacionItem) {
        return evaluacionItemRepository.save(evaluacionItem);
    }

    public void deleteEvaluacionItem(Integer id) {
        evaluacionItemRepository.deleteById(id);
    }
}

