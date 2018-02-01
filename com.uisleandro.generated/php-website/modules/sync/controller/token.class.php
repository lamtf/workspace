<?php

namespace Sync\Controller;

use Sync\Model\Token as TokenModel;

class Token {

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
			$token = new TokenModel();
			$result = $token->get_for_client_insert($obj->server_id, $obj->page_number, $obj->page_size);
			echo json_encode($result, JSON_NUMERIC_CHECK);
		} else {
			echo $error_msg;
			die();
		}
	}

	//SYNC PROTOCOL: insert-on-server step 2,2
	function bash_insert(){
		$error_msg = "{\"status\":\"error\", \"usage\":\"[{\\\"client_id\\\" : (long),\\\"last_update\\\" : (datetime),\\\"fk_user\\\" : (user),\\\"fk_system\\\" : (system),\\\"fk_token_type\\\" : (token_type),\\\"guid\\\" : (guidtype),\\\"last_use_time\\\" : (datetime),\\\"expiration_time\\\" : (datetime)}]\"}";
		if(isset($_REQUEST["json"])){
			$rows = json_decode($_REQUEST["json"]);
			if(NULL == $rows || empty($rows)){
				echo $error_msg;
				die();
			}
			$token = new TokenModel();
			$result = "[";
			for($i = 0; $i < sizeof($rows); $i++){
				$ret = $token->insert($rows[$i]->last_update,$rows[$i]->fk_user,$rows[$i]->fk_system,$rows[$i]->fk_token_type,$rows[$i]->guid,$rows[$i]->last_use_time,$rows[$i]->expiration_time);
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
		$error_msg = "{ \"status\" : \"error\" , \"usage\" : \"[{\\\"client_id\\\" : (long),\\\"server_id\\\" : (long),\\\"last_update\\\" : (datetime),\\\"fk_user\\\" : (user),\\\"fk_system\\\" : (system),\\\"fk_token_type\\\" : (token_type),\\\"guid\\\" : (guidtype),\\\"last_use_time\\\" : (datetime),\\\"expiration_time\\\" : (datetime)}]\"}"; 
		if(isset($_REQUEST["json"])){
			$rows = json_decode($_REQUEST["json"]);
			if(NULL == $rows || empty($rows)){
				echo $error_msg;
				die();
			}
			$token = new TokenModel();
			$result = "[";
			for($i = 0; $i < sizeof($rows); $i++){
				$ret = $token->update($rows[$i]->server_id,$rows[$i]->last_update,$rows[$i]->fk_user,$rows[$i]->fk_system,$rows[$i]->fk_token_type,$rows[$i]->guid,$rows[$i]->last_use_time,$rows[$i]->expiration_time);
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
			$token = new TokenModel();
			$result = $token->get_for_client_update($obj->last_update_time, $obj->page_number, $obj->page_size);
			echo json_encode($ret, JSON_NUMERIC_CHECK);
		} else {
			echo $error_msg;
		}
	}

}
