package com.packsure.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.packsure.entity.BarcodePool;

@Repository
public interface BarcodePoolRepository extends JpaRepository<BarcodePool, Long> {
    Optional<BarcodePool> findFirstByIsUsedFalse();

    Optional<BarcodePool> findByBarcodeNumber(String barcodeNumber);

	boolean existsByBarcodeNumber(String barcodeNumber);
}