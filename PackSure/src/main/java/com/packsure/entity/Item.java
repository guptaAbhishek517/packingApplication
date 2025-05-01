package com.packsure.entity;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    
    private double rate;
    
    private Long quantity;
    
    private double amount;
    
    private String description;

    @ManyToOne
    @JoinColumn(name = "barcode_id")
    @JsonManagedReference
    private Barcode barcode;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getRate() {
		return rate;
	}

	public void setRate(double rate) {
		this.rate = rate;
	}

	public Long getQuantity() {
		return quantity;
	}

	public void setQuantity(Long quantity) {
		this.quantity = quantity;
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

	public Barcode getBarcode() {
		return barcode;
	}

	public void setBarcode(Barcode barcode) {
		this.barcode = barcode;
	}

	public Item() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Item(Long id, String name, double rate, Long quantity, double amount, String description, Barcode barcode) {
		super();
		this.id = id;
		this.name = name;
		this.rate = rate;
		this.quantity = quantity;
		this.amount = amount;
		this.description = description;
		this.barcode = barcode;
	}
    
    
    
}
