package com.opticalshop.domain.repository;

import com.opticalshop.domain.model.prescription.Prescription;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PrescriptionRepository {
    Prescription save(Prescription prescription);
    Optional<Prescription> findById(UUID id);
    List<Prescription> findByCustomerId(UUID customerId);
}
