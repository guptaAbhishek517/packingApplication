package com.packsure.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.packsure.entity.Barcode;

@Repository
public interface BarcodeRepository extends JpaRepository<Barcode, Long> {
    Optional<Barcode> findByBarcodeNumber(String barcodeNumber);
}

