package com.packsure.service;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.packsure.entity.BarcodePool;
import com.packsure.exception.BarcodeNotFoundException;
import com.packsure.exception.NoAvailableBarcodeException;
import com.packsure.repository.BarcodePoolRepository;
import com.packsure.utility.BarcodeGenerator;

@Service
public class BarcodePoolService {

	@Autowired
	private BarcodePoolRepository barcodePoolRepository;

	public List<String> getAvailableBarcodes() {
		return barcodePoolRepository.findAll().stream().filter(bp -> !bp.isUsed()).map(BarcodePool::getBarcodeNumber)
				.collect(Collectors.toList());
	}

	public void markBarcodeAsUsed(String barcodeNumber) {
		BarcodePool barcodePool = barcodePoolRepository.findByBarcodeNumber(barcodeNumber)
				.orElseThrow(() -> new BarcodeNotFoundException("Barcode not found!"));
		if (barcodePool.isUsed()) {
			throw new IllegalStateException("Barcode already used!");
		}
		barcodePool.setUsed(true);
		barcodePoolRepository.save(barcodePool);
	}

	private Random random = new Random();

	public void generateBarcodeNumbers(int numberOfBarcodes) {

		String uploadDir = "uploads/barcodes";

		for (int i = 0; i < numberOfBarcodes; i++) {
			String barcodeNumber;
			do {
				barcodeNumber = "BC" + (100000 + random.nextInt(900000));
			} while (barcodePoolRepository.existsByBarcodeNumber(barcodeNumber));

			BarcodePool barcodePool = new BarcodePool();
			barcodePool.setBarcodeNumber(barcodeNumber);
			barcodePool.setUsed(false);

			try {

				String uploadBarcodeImage = "uploads/barcodes";

				String filePath = BarcodeGenerator.generateBarcodeImage(barcodeNumber, uploadBarcodeImage);

				barcodePool.setBarcodeImagePath(filePath);

				barcodePoolRepository.save(barcodePool);

			} catch (Exception e) {
				e.printStackTrace();

			}

		}
	}

	public List<String> getUsedBarcodes() {
		return barcodePoolRepository.findAll().stream().filter(bp -> bp.isUsed()).map(BarcodePool::getBarcodeNumber)
				.collect(Collectors.toList());
	}
}