package com.packsure.dto;
import java.util.*;
public class BarcodeRequestDTO {
	private String barcodeNumber;
	private String barcodeImagePath;
	private List<ItemDTO> items;
	private Long orderId;
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
	public String getBarcodeImagePath() {
		return barcodeImagePath;
	}
	public void setBarcodeImagePath(String barcodeImagePath) {
		this.barcodeImagePath = barcodeImagePath;
	}
	public Long getOrderId() {
		return orderId;
	}
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}
}
