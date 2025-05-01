package com.packsure.dto;
import java.util.List;
public class BarcodeResponseDTO {
	private String barcodeNumber;
	private List<ItemDTO> items;
	private String status;
	
	public String getBarcodeNumber() {
		return barcodeNumber;
	}
	public void setBarcodeNumber(String barcodeNumber) {
		this.barcodeNumber = barcodeNumber;
	}
	public List<ItemDTO> getItems() {
		return items;
	}
	public void setItems(List<ItemDTO> items) {
		this.items = items;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public BarcodeResponseDTO(String barcodeNumber, List<ItemDTO> items, String status) {
		super();
		this.barcodeNumber = barcodeNumber;
		this.items = items;
		this.status = status;
	}
}


