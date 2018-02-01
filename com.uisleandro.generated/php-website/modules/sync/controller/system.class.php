<?php

namespace Sync\Controller;

use Sync\Model\System as SystemModel;

class System {

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
			$system = new SystemModel();
			$result = $system->get_for_client_insert($obj->server_id, $obj->page_number, $obj->page_size);
			echo json_encode($result, JSON_NUMERIC_CHECK);
		} else {
			echo $error_msg;
			die();
		}
	}

	//SYNC PROTOCOL: insert-on-server step 2,2
	function bash_insert(){
		$error_msg = "{\"status\":\"error\", \"usage\":\"[{\\\"client_id\\\" : (long),\\\"last_update\\\" : (datetime),\\\"name\\\" : (smalltext),\\\"enabled\\\" : (yesnoquestion),\\\"fk_currency\\\" : (currency),\\\"fantasy_name\\\" : (mediumtext),\\\"stores_address\\\" : (mediumtext),\\\"srores_address_number\\\" : (smalltext),\\\"stores_city\\\" : (mediumtext),\\\"stores_neighborhood\\\" : (mediumtext),\\\"stores_zip_code\\\" : (smalltext),\\\"stores_state\\\" : (smalltext),\\\"stores_email\\\" : (mediumtext),\\\"stores_phonenumber\\\" : (smalltext),\\\"fk_reseller\\\" : (reseller)}]\"}";
		if(isset($_REQUEST["json"])){
			$rows = json_decode($_REQUEST["json"]);
			if(NULL == $rows || empty($rows)){
				echo $error_msg;
				die();
			}
			$system = new SystemModel();
			$result = "[";
			for($i = 0; $i < sizeof($rows); $i++){
				$ret = $system->insert($rows[$i]->last_update,$rows[$i]->name,$rows[$i]->enabled,$rows[$i]->fk_currency,$rows[$i]->fantasy_name,$rows[$i]->stores_address,$rows[$i]->srores_address_number,$rows[$i]->stores_city,$rows[$i]->stores_neighborhood,$rows[$i]->stores_zip_code,$rows[$i]->stores_state,$rows[$i]->stores_email,$rows[$i]->stores_phonenumber,$rows[$i]->fk_reseller);
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
		$error_msg = "{ \"status\" : \"error\" , \"usage\" : \"[{\\\"client_id\\\" : (long),\\\"server_id\\\" : (long),\\\"last_update\\\" : (datetime),\\\"name\\\" : (smalltext),\\\"enabled\\\" : (yesnoquestion),\\\"fk_currency\\\" : (currency),\\\"fantasy_name\\\" : (mediumtext),\\\"stores_address\\\" : (mediumtext),\\\"srores_address_number\\\" : (smalltext),\\\"stores_city\\\" : (mediumtext),\\\"stores_neighborhood\\\" : (mediumtext),\\\"stores_zip_code\\\" : (smalltext),\\\"stores_state\\\" : (smalltext),\\\"stores_email\\\" : (mediumtext),\\\"stores_phonenumber\\\" : (smalltext),\\\"fk_reseller\\\" : (reseller)}]\"}"; 
		if(isset($_REQUEST["json"])){
			$rows = json_decode($_REQUEST["json"]);
			if(NULL == $rows || empty($rows)){
				echo $error_msg;
				die();
			}
			$system = new SystemModel();
			$result = "[";
			for($i = 0; $i < sizeof($rows); $i++){
				$ret = $system->update($rows[$i]->server_id,$rows[$i]->last_update,$rows[$i]->name,$rows[$i]->enabled,$rows[$i]->fk_currency,$rows[$i]->fantasy_name,$rows[$i]->stores_address,$rows[$i]->srores_address_number,$rows[$i]->stores_city,$rows[$i]->stores_neighborhood,$rows[$i]->stores_zip_code,$rows[$i]->stores_state,$rows[$i]->stores_email,$rows[$i]->stores_phonenumber,$rows[$i]->fk_reseller);
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
			$system = new SystemModel();
			$result = $system->get_for_client_update($obj->last_update_time, $obj->page_number, $obj->page_size);
			echo json_encode($ret, JSON_NUMERIC_CHECK);
		} else {
			echo $error_msg;
		}
	}

}
