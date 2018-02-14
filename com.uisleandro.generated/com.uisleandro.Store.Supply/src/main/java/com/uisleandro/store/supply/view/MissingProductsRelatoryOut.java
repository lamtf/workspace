package com.uisleandro.store.supply.view;
import android.database.Cursor;

public class MissingProductsRelatoryOut {

	public static MissingProductsRelatoryOut FromCursor(Cursor cursor){
		MissingProductsRelatoryOut instance = new MissingProductsRelatoryOut();
		instance.setStockReviewLastUpdate(cursor.getLong(1));
		instance.setStockReviewActualAmount(cursor.getInt(2));
		instance.setStockReviewSoldItems(cursor.getInt(3));
		instance.setStockReviewPreviousAmount(cursor.getInt(4));
		instance.setStockReviewMissingAmount(cursor.getInt(5));
		instance.setProductLastUpdate(cursor.getLong(6));
		instance.setProductBarcode(cursor.getString(7));
		instance.setProductDescription(cursor.getString(8));
		instance.setProductAmount(cursor.getInt(9));
		instance.setProductPurchasePrice(cursor.getFloat(10));
		instance.setProductSalePrice(cursor.getFloat(11));
		instance.setProductSize(cursor.getString(12));
		instance.setProductExpirationDate(cursor.getLong(13));
		instance.setBrandLastUpdate(cursor.getLong(14));
		instance.setBrandCompanyName(cursor.getString(15));
		instance.setBrandFantasyName(cursor.getString(16));
		instance.setUnitLastUpdate(cursor.getLong(17));
		instance.setUnitName(cursor.getString(18));
		instance.setCategoryLastUpdate(cursor.getLong(19));
		instance.setCategoryName(cursor.getString(20));
		instance.setCategoryFkCategory(cursor.getLong(21));
		instance.setGenderLastUpdate(cursor.getLong(22));
		instance.setGenderName(cursor.getString(23));
		instance.setSystemLastUpdate(cursor.getLong(24));
		instance.setSystemName(cursor.getString(25));
		instance.setSystemEnabled((cursor.getInt(26) > 0));
		return instance;
	}

	private Long stock_review_last_update;
	private Integer stock_review_actual_amount;
	private Integer stock_review_sold_items;
	private Integer stock_review_previous_amount;
	private Integer stock_review_missing_amount;
	private Long product_last_update;
	private String product_barcode;
	private String product_description;
	private Integer product_amount;
	private Float product_purchase_price;
	private Float product_sale_price;
	private String product_size;
	private Long product_expiration_date;
	private Long brand_last_update;
	private String brand_company_name;
	private String brand_fantasy_name;
	private Long unit_last_update;
	private String unit_name;
	private Long category_last_update;
	private String category_name;
	private Long category_fk_category;
	private Long gender_last_update;
	private String gender_name;
	private Long system_last_update;
	private String system_name;
	private Boolean system_enabled;

	public Long getStockReviewLastUpdate () {
		return stock_review_last_update;
	}
	
	public void setStockReviewLastUpdate (Long stock_review_last_update) {
		this.stock_review_last_update = stock_review_last_update;
	}

	public Integer getStockReviewActualAmount () {
		return stock_review_actual_amount;
	}
	
	public void setStockReviewActualAmount (Integer stock_review_actual_amount) {
		this.stock_review_actual_amount = stock_review_actual_amount;
	}

	public Integer getStockReviewSoldItems () {
		return stock_review_sold_items;
	}
	
	public void setStockReviewSoldItems (Integer stock_review_sold_items) {
		this.stock_review_sold_items = stock_review_sold_items;
	}

	public Integer getStockReviewPreviousAmount () {
		return stock_review_previous_amount;
	}
	
	public void setStockReviewPreviousAmount (Integer stock_review_previous_amount) {
		this.stock_review_previous_amount = stock_review_previous_amount;
	}

	public Integer getStockReviewMissingAmount () {
		return stock_review_missing_amount;
	}
	
	public void setStockReviewMissingAmount (Integer stock_review_missing_amount) {
		this.stock_review_missing_amount = stock_review_missing_amount;
	}

	public Long getProductLastUpdate () {
		return product_last_update;
	}
	
	public void setProductLastUpdate (Long product_last_update) {
		this.product_last_update = product_last_update;
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

	public Integer getProductAmount () {
		return product_amount;
	}
	
	public void setProductAmount (Integer product_amount) {
		this.product_amount = product_amount;
	}

	public Float getProductPurchasePrice () {
		return product_purchase_price;
	}
	
	public void setProductPurchasePrice (Float product_purchase_price) {
		this.product_purchase_price = product_purchase_price;
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

	public Long getBrandLastUpdate () {
		return brand_last_update;
	}
	
	public void setBrandLastUpdate (Long brand_last_update) {
		this.brand_last_update = brand_last_update;
	}

	public String getBrandCompanyName () {
		return brand_company_name;
	}
	
	public void setBrandCompanyName (String brand_company_name) {
		this.brand_company_name = brand_company_name;
	}

	public String getBrandFantasyName () {
		return brand_fantasy_name;
	}
	
	public void setBrandFantasyName (String brand_fantasy_name) {
		this.brand_fantasy_name = brand_fantasy_name;
	}

	public Long getUnitLastUpdate () {
		return unit_last_update;
	}
	
	public void setUnitLastUpdate (Long unit_last_update) {
		this.unit_last_update = unit_last_update;
	}

	public String getUnitName () {
		return unit_name;
	}
	
	public void setUnitName (String unit_name) {
		this.unit_name = unit_name;
	}

	public Long getCategoryLastUpdate () {
		return category_last_update;
	}
	
	public void setCategoryLastUpdate (Long category_last_update) {
		this.category_last_update = category_last_update;
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

	public Long getGenderLastUpdate () {
		return gender_last_update;
	}
	
	public void setGenderLastUpdate (Long gender_last_update) {
		this.gender_last_update = gender_last_update;
	}

	public String getGenderName () {
		return gender_name;
	}
	
	public void setGenderName (String gender_name) {
		this.gender_name = gender_name;
	}

	public Long getSystemLastUpdate () {
		return system_last_update;
	}
	
	public void setSystemLastUpdate (Long system_last_update) {
		this.system_last_update = system_last_update;
	}

	public String getSystemName () {
		return system_name;
	}
	
	public void setSystemName (String system_name) {
		this.system_name = system_name;
	}

	public Boolean getSystemEnabled () {
		return system_enabled;
	}
	
	public void setSystemEnabled (Boolean system_enabled) {
		this.system_enabled = system_enabled;
	}

}
