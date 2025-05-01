package com.packsure.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

@Entity
public class Barcode {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, unique = true)
	private String barcodeNumber;

	private String packedBy = "unknown";
	private String branch = "xyz";
	private Long companyId = 1L;
	private LocalDateTime packedAt;
	private LocalDateTime dispatchedAt;
	private String status; // PACKED, DISPATCHED

	@OneToOne
	@JoinColumn(name = "order_id", unique = true)
	private Order order;

	@OneToMany(mappedBy = "barcode", cascade = CascadeType.ALL)
	@JsonIgnore
	private List<Item> items = new ArrayList<>();

	private Double TotalAmount;

	private String barcodeImagePath;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getBarcodeNumber() {
		return barcodeNumber;
	}

	public void setBarcodeNumber(String barcodeNumber) {
		this.barcodeNumber = barcodeNumber;
	}

	public String getPackedBy() {
		return packedBy;
	}

	public void setPackedBy(String packedBy) {
		this.packedBy = packedBy;
	}

	public String getBranch() {
		return branch;
	}

	public void setBranch(String branch) {
		this.branch = branch;
	}

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
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

	public List<Item> getItems() {
		return items;
	}

	public void setItems(List<Item> items) {
		this.items = items;
	}

	public Double getTotalAmount() {
		return TotalAmount;
	}

	public void setTotalAmount(Double totalAmount) {
		TotalAmount = totalAmount;
	}

	public String getBarcodeImagePath() {
		return barcodeImagePath;
	}

	public void setBarcodeImagePath(String barcodeImagePath) {
		this.barcodeImagePath = barcodeImagePath;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public Barcode() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Barcode(Long id, String barcodeNumber, String packedBy, String branch, Long companyId,
			LocalDateTime packedAt, LocalDateTime dispatchedAt, String status, List<Item> items, Double TotalAmount,
			String barcodeImagePath, boolean isUsed, Order order) {
		super();
		this.id = id;
		this.barcodeNumber = barcodeNumber;
		this.packedBy = packedBy;
		this.branch = branch;
		this.companyId = companyId;
		this.packedAt = packedAt;
		this.dispatchedAt = dispatchedAt;
		this.status = status;
		this.items = items;
		this.TotalAmount = TotalAmount;
		this.barcodeImagePath = barcodeImagePath;
		this.order = order;

	}

}

