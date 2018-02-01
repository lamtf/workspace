<?php

namespace Sync\Controller;

use Sync\Model\Product as ProductModel;

class Product {

	public function __construct(){
		if(empty($GLOBALS["ENABLE_SYNC_MODULE"])){
			die();
		}
	}

	//SYNC PROTOCOL: insert-on-client step 1,2
	public function get_for_client_insert(){
		$error_msg = "{\"status\":\"error\", \"usage\":\"{ \\\"server_id\\\" : (number), \\\"page_number\\\" : (number), \\\"page_size\\\" : (number)}\" }";
		if(isset($_REQUEST["json"])){
			$obj = json_decode($_REQUEST["json"]);
			if(NULL == $obj){
				echo $error_msg;
				die();
			}
			$product = new ProductModel();
			$result = $product->get_for_client_insert($obj->server_id, $obj->page_number, $obj->page_size);
			echo json_encode($result, JSON_NUMERIC_CHECK);
		} else {
			echo $error_msg;
			die();
		}
	}

	//SYNC PROTOCOL: insert-on-server step 2,2
	function bash_insert(){
		$error_msg = "{\"status\":\"error\", \"usage\":\"[{\\\"client_id\\\" : (long),\\\"last_update\\\" : (datetime),\\\"fk_system\\\" : (system),\\\"barcode\\\" : (barcodetype),\\\"description\\\" : (mediumtext),\\\"amount\\\" : (number),\\\"fk_gender\\\" : (gender),\\\"purchase_price\\\" : (money),\\\"sale_price\\\" : (money),\\\"fk_category\\\" : (category),\\\"size\\\" : (smalltext),\\\"fk_unit\\\" : (unit),\\\"expiration_date\\\" : (date),\\\"fk_brand\\\" : (brand)}]\"}";
		if(isset($_REQUEST["json"])){
			$rows = json_decode($_REQUEST["json"]);
			if(NULL == $rows || empty($rows)){
				echo $error_msg;
				die();
			}
			$product = new ProductModel();
			$result = "[";
			for($i = 0; $i < sizeof($rows); $i++){
				$ret = $product->insert($rows[$i]->last_update,$rows[$i]->fk_system,$rows[$i]->barcode,$rows[$i]->description,$rows[$i]->amount,$rows[$i]->fk_gender,$rows[$i]->purchase_price,$rows[$i]->sale_price,$rows[$i]->fk_category,$rows[$i]->size,$rows[$i]->fk_unit,$rows[$i]->expiration_date,$rows[$i]->fk_brand);
				$ret->client_id = $rows[$i]->client_id;
				if($i > 0){
					$result .= ",";
				}
				$result .= json_encode($ret, JSON_NUMERIC_CHECK);
			}
			$result .= "]";
			echo $result;
		} else {
			echo $error_msg;
		}
	}



	//SYNC PROTOCOL: update-on-server step 4,3
	function bash_update(){
		$error_msg = "{ \"status\" : \"error\" , \"usage\" : \"[{\\\"client_id\\\" : (long),\\\"server_id\\\" : (long),\\\"last_update\\\" : (datetime),\\\"fk_system\\\" : (system),\\\"barcode\\\" : (barcodetype),\\\"description\\\" : (mediumtext),\\\"amount\\\" : (number),\\\"fk_gender\\\" : (gender),\\\"purchase_price\\\" : (money),\\\"sale_price\\\" : (money),\\\"fk_category\\\" : (category),\\\"size\\\" : (smalltext),\\\"fk_unit\\\" : (unit),\\\"expiration_date\\\" : (date),\\\"fk_brand\\\" : (brand)}]\"}"; 
		if(isset($_REQUEST["json"])){
			$rows = json_decode($_REQUEST["json"]);
			if(NULL == $rows || empty($rows)){
				echo $error_msg;
				die();
			}
			$product = new ProductModel();
			$result = "[";
			for($i = 0; $i < sizeof($rows); $i++){
				$ret = $product->update($rows[$i]->server_id,$rows[$i]->last_update,$rows[$i]->fk_system,$rows[$i]->barcode,$rows[$i]->description,$rows[$i]->amount,$rows[$i]->fk_gender,$rows[$i]->purchase_price,$rows[$i]->sale_price,$rows[$i]->fk_category,$rows[$i]->size,$rows[$i]->fk_unit,$rows[$i]->expiration_date,$rows[$i]->fk_brand);
				$ret->client_id = $rows[$i]->client_id;
				if($i > 0){
					$result .= ",";
				}
				$result .= json_encode($ret, JSON_NUMERIC_CHECK);
			}
			$result .= "]";
			echo $result;
		} else {
			echo $error_msg;
		}
	}


	function get_for_client_update(){
		$error_msg = "{ \"status\" : \"error\" , \"usage\" : \"{ \"last_update_time\": (number), \"page_number\":  (number), \"page_size\": (number) }\"}"; 
		if(isset($_REQUEST["json"])){
			$obj = json_decode($_REQUEST["json"]);
			if(NULL == $obj){
				echo $error_msg;
				return;
			}
			if(0 == $obj->last_update_time){
					echo "[]";
					return;
			}
			$product = new ProductModel();
			$result = $product->get_for_client_update($obj->last_update_time, $obj->page_number, $obj->page_size);
			echo json_encode($ret, JSON_NUMERIC_CHECK);
		} else {
			echo $error_msg;
		}
	}

}
