package com.uisleandro.store.sales.view;
import android.database.Cursor;

public class ListProductsOnSalesChartOut {

	public static ListProductsOnSalesChartOut FromCursor(Cursor cursor){
		ListProductsOnSalesChartOut instance = new ListProductsOnSalesChartOut();
		instance.setProductOnSaleLastUpdate(cursor.getLong(1));
		instance.setProductBarcode(cursor.getString(2));
		instance.setProductDescription(cursor.getString(3));
		instance.setProductSalePrice(cursor.getFloat(4));
		instance.setProductSize(cursor.getString(5));
		instance.setProductExpirationDate(cursor.getLong(6));
		instance.setBrandFantasyName(cursor.getString(7));
		instance.setCurrencyAbbreviature(cursor.getString(8));
		instance.setUnitName(cursor.getString(9));
		instance.setCategoryName(cursor.getString(10));
		instance.setCategoryFkCategory(cursor.getLong(11));
		instance.setGenderName(cursor.getString(12));
		return instance;
	}

	private Long product_on_sale_last_update;
	private String product_barcode;
	private String product_description;
	private Float product_sale_price;
	private String product_size;
	private Long product_expiration_date;
	private String brand_fantasy_name;
	private String currency_abbreviature;
	private String unit_name;
	private String category_name;
	private Long category_fk_category;
	private String gender_name;

	public Long getProductOnSaleLastUpdate () {
		return product_on_sale_last_update;
	}
	
	public void setProductOnSaleLastUpdate (Long product_on_sale_last_update) {
		this.product_on_sale_last_update = product_on_sale_last_update;
	}

	public String getProductBarcode () {
		return product_barcode;
	}
	
	public void setProductBarcode (String product_barcode) {
		this.product_barcode = product_barcode;
	}

	public String getProductDescription () {
		return product_description;
	}
	
	public void setProductDescription (String product_description) {
		this.product_description = product_description;
	}

	public Float getProductSalePrice () {
		return product_sale_price;
	}
	
	public void setProductSalePrice (Float product_sale_price) {
		this.product_sale_price = product_sale_price;
	}

	public String getProductSize () {
		return product_size;
	}
	
	public void setProductSize (String product_size) {
		this.product_size = product_size;
	}

	public Long getProductExpirationDate () {
		return product_expiration_date;
	}
	
	public void setProductExpirationDate (Long product_expiration_date) {
		this.product_expiration_date = product_expiration_date;
	}

	public String getBrandFantasyName () {
		return brand_fantasy_name;
	}
	
	public void setBrandFantasyName (String brand_fantasy_name) {
		this.brand_fantasy_name = brand_fantasy_name;
	}

	public String getCurrencyAbbreviature () {
		return currency_abbreviature;
	}
	
	public void setCurrencyAbbreviature (String currency_abbreviature) {
		this.currency_abbreviature = currency_abbreviature;
	}

	public String getUnitName () {
		return unit_name;
	}
	
	public void setUnitName (String unit_name) {
		this.unit_name = unit_name;
	}

	public String getCategoryName () {
		return category_name;
	}
	
	public void setCategoryName (String category_name) {
		this.category_name = category_name;
	}

	public Long getCategoryFkCategory () {
		return category_fk_category;
	}
	
	public void setCategoryFkCategory (Long category_fk_category) {
		this.category_fk_category = category_fk_category;
	}

	public String getGenderName () {
		return gender_name;
	}
	
	public void setGenderName (String gender_name) {
		this.gender_name = gender_name;
	}

}
