package com.packsure.dto;

import java.time.LocalDateTime;
public class BarcodeDTO {
	private Long id;
	private String barcodeNumber;
	private String barcodeImagePath;
	private Double TotalAmount;
	private LocalDateTime packedAt;
	private LocalDateTime dispatchedAt;
	private String status;
	public String getBarcodeNumber() {
		return barcodeNumber;
	}
	public void setBarcodeNumber(String barcodeNumber) {
		this.barcodeNumber = barcodeNumber;
	}
	public String getBarcodeImagePath() {
		return barcodeImagePath;
	}
	public void setBarcodeImagePath(String barcodeImagePath) {
		this.barcodeImagePath = barcodeImagePath;
	}
	public Double getTotalAmount() {
		return TotalAmount;
	}
	public void setTotalAmount(Double totalAmount) {
		TotalAmount = totalAmount;
	}
	public LocalDateTime getPackedAt() {
		return packedAt;
	}
	public void setPackedAt(LocalDateTime packedAt) {
		this.packedAt = packedAt;
	}
	public LocalDateTime getDispatchedAt() {
		return dispatchedAt;
	}
	public void setDispatchedAt(LocalDateTime dispatchedAt) {
		this.dispatchedAt = dispatchedAt;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
}


