<?php
namespace Sync\Model;

class Token {

	private $db;

	public function __construct(){
		$this->db = \db::getInstance();
	}

	//SYNC PROTOCOL: insert-on-client step 1,2
	public function get_for_client_insert($fk_token, $page_number, $page_size){
		$query = "SELECT \"id\" as \"server_id\", \"last_update\"".
		",\"fk_user\"".
		",\"fk_system\"".
		",\"fk_token_type\"".
		",\"guid\"".
		",\"last_use_time\"".
		",\"expiration_time\"".
		" FROM \"simpledb\".\"token\"".
		" WHERE \"id\" > ". \db::number($fk_token).
		" LIMIT $page_size OFFSET " . ($page_size * $page_number) .";";

		$stmt = $this->db->query($query);
		$res = $stmt->fetchAll(\PDO::FETCH_OBJ);


		return $res;
	}


	//SYNC PROTOCOL: insert-on-server step 2,2
	public function insert($last_update,$fk_user,$fk_system,$fk_token_type,$guid,$last_use_time,$expiration_time){
		$last_update_time = \db::timestamp();

		$query = "INSERT INTO \"simpledb\".\"token\"(".
		"\"last_update\"".
		",\"fk_user\"".
		",\"fk_system\"".
		",\"fk_token_type\"".
		",\"guid\"".
		",\"last_use_time\"".
		",\"expiration_time\"".") VALUES (".
		\db::string($last_update) .
		"," .\db::number($fk_user) .
		"," .\db::number($fk_system) .
		"," .\db::number($fk_token_type) .
		"," .\db::string($guid) .
		"," .\db::string($last_use_time) .
		"," .\db::string($expiration_time) .");";

		$this->db->query($query);
		$query = "SELECT currval(pg_get_serial_sequence('simpledb.token','id'));";
		$stmt = $this->db->query($query);
		$id = $stmt->fetch(\PDO::FETCH_NUM)[0];

		$res = new \stdClass();
		$res->server_id = $id;
		$res->last_update_time = $last_update_time;

		return $res;
	}


	//SYNC PROTOCOL: update-on-server step 4,3
	public function update($id, $last_update,$fk_user,$fk_system,$fk_token_type,$guid,$last_use_time,$expiration_time){

		$last_update_time = \db::timestamp();

		$query = "UPDATE \"simpledb\".\"token\" SET ".
		"\"last_update\" = ".\db::string($last_update) .
		",\"fk_user\" = ".\db::number($fk_user) .
		",\"fk_system\" = ".\db::number($fk_system) .
		",\"fk_token_type\" = ".\db::number($fk_token_type) .
		",\"guid\" = ".\db::string($guid) .
		",\"last_use_time\" = ".\db::string($last_use_time) .
		",\"expiration_time\" = ".\db::string($expiration_time) .
		" WHERE id = ". $id.";";

		$this->db->query($query);

		$res = new \stdClass();

		$res->server_id = $id;

		return $res;
	}


	function get_for_client_update($last_update_time, $page_number, $page_size){

		$query = "SELECT \"id\" as \"server_id\", \"last_update\", \"fk_user\", \"fk_system\", \"fk_token_type\", \"guid\", \"last_use_time\", \"expiration_time\" FROM \"simpledb\".\"token\" WHERE last_update_time > " . $last_update_time;

		if(0 < $page_size){
			$query .= " LIMIT " . $page_size . " OFFSET " . ($page_number * $page_size);
		}

		$query .= ";";

		try{
			$stmt = $this->db->query($query);
		}catch(\Exception $ex){
			echo $query . "\n";
		}

		$res = $stmt->fetchAll(\PDO::FETCH_OBJ);

		return $res;

	}

}
