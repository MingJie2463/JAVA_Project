package com.jayfan.store.domain;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
@Table(name = "goods")
public class Goods {

	@Id
	@Column(name = "id")
	private Long id;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "price")
	private Double price;
	
	@Column(name = "description")
	private String description;
	
	@Column(name = "brand")
	private String brand;
	
	@Column(name = "cpu_brand")
	private String cpuBrand;
	
	@Column(name = "cpu_type")
	private String cpuType;
	
	@Column(name = "memory_capacity")
	private String memoryCapacity;
	
	@Column(name = "hd_capacity")
	private String hdCapacity;
	
	@Column(name = "card_model")
	private String cardModel;
	
	@Column(name = "displaysize")
	private String displaysize;
	
	@Column(name = "image")
	private String image;
	
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
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getBrand() {
		return brand;
	}
	public void setBrand(String brand) {
		this.brand = brand;
	}
	public String getCpuBrand() {
		return cpuBrand;
	}
	public void setCpuBrand(String cpuBrand) {
		this.cpuBrand = cpuBrand;
	}
	public String getCpuType() {
		return cpuType;
	}
	public void setCpuType(String cpuType) {
		this.cpuType = cpuType;
	}
	public String getMemoryCapacity() {
		return memoryCapacity;
	}
	public void setMemoryCapacity(String memoryCapacity) {
		this.memoryCapacity = memoryCapacity;
	}
	public String getHdCapacity() {
		return hdCapacity;
	}
	public void setHdCapacity(String hdCapacity) {
		this.hdCapacity = hdCapacity;
	}
	public String getCardModel() {
		return cardModel;
	}
	public void setCardModel(String cardModel) {
		this.cardModel = cardModel;
	}
	public String getDisplaysize() {
		return displaysize;
	}
	public void setDisplaysize(String displaysize) {
		this.displaysize = displaysize;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	
}
