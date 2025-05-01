package com.packsure.dto;
public class ItemDTO {
	private String name;
	private Long quantity;
	private Double rate;
	private double amount;
	private String description;
	public ItemDTO(String name, Long quantity, Double rate, double amount, String description) {
		this.name = name;
		this.quantity = quantity;
		this.rate = rate;
		this.amount = amount;
		this.description = description;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Long getQuantity() {
		return quantity;
	}
	public void setQuantity(Long quantity) {
		this.quantity = quantity;
	}
	public Double getRate() {
		return rate;
	}
	public void setRate(Double rate) {
		this.rate = rate;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
}
