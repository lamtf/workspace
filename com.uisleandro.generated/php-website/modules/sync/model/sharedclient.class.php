<?php
namespace Sync\Model;

class SharedClient {

	private $db;

	public function __construct(){
		$this->db = \db::getInstance();
	}

	//SYNC PROTOCOL: insert-on-client step 1,2
	public function get_for_client_insert($fk_shared_client, $page_number, $page_size){
		$query = "SELECT \"id\" as \"server_id\", \"last_update\"".
		",\"name\"".
		",\"birth_date\"".
		",\"birth_city\"".
		",\"birth_state\"".
		",\"mothers_name\"".
		",\"fathers_name\"".
		",\"profession\"".
		",\"zip_code\"".
		",\"address\"".
		",\"neighborhood\"".
		",\"city\"".
		",\"state\"".
		",\"complement\"".
		",\"fk_country\"".
		" FROM \"simpledb\".\"shared_client\"".
		" WHERE \"id\" > ". \db::number($fk_shared_client).
		" LIMIT $page_size OFFSET " . ($page_size * $page_number) .";";

		$stmt = $this->db->query($query);
		$res = $stmt->fetchAll(\PDO::FETCH_OBJ);


		return $res;
	}


	//SYNC PROTOCOL: insert-on-server step 2,2
	public function insert($last_update,$name,$birth_date,$birth_city,$birth_state,$mothers_name,$fathers_name,$profession,$zip_code,$address,$neighborhood,$city,$state,$complement,$fk_country){
		$last_update_time = \db::timestamp();

		$query = "INSERT INTO \"simpledb\".\"shared_client\"(".
		"\"last_update\"".
		",\"name\"".
		",\"birth_date\"".
		",\"birth_city\"".
		",\"birth_state\"".
		",\"mothers_name\"".
		",\"fathers_name\"".
		",\"profession\"".
		",\"zip_code\"".
		",\"address\"".
		",\"neighborhood\"".
		",\"city\"".
		",\"state\"".
		",\"complement\"".
		",\"fk_country\"".") VALUES (".
		\db::string($last_update) .
		"," .\db::string($name) .
		"," .\db::timestamp($birth_date) .
		"," .\db::string($birth_city) .
		"," .\db::string($birth_state) .
		"," .\db::string($mothers_name) .
		"," .\db::string($fathers_name) .
		"," .\db::string($profession) .
		"," .\db::string($zip_code) .
		"," .\db::string($address) .
		"," .\db::string($neighborhood) .
		"," .\db::string($city) .
		"," .\db::string($state) .
		"," .\db::string($complement) .
		"," .\db::number($fk_country) .");";

		$this->db->query($query);
		$query = "SELECT currval(pg_get_serial_sequence('simpledb.shared_client','id'));";
		$stmt = $this->db->query($query);
		$id = $stmt->fetch(\PDO::FETCH_NUM)[0];

		$res = new \stdClass();
		$res->server_id = $id;
		$res->last_update_time = $last_update_time;

		return $res;
	}


	//SYNC PROTOCOL: update-on-server step 4,3
	public function update($id, $last_update,$name,$birth_date,$birth_city,$birth_state,$mothers_name,$fathers_name,$profession,$zip_code,$address,$neighborhood,$city,$state,$complement,$fk_country){

		$last_update_time = \db::timestamp();

		$query = "UPDATE \"simpledb\".\"shared_client\" SET ".
		"\"last_update\" = ".\db::string($last_update) .
		",\"name\" = ".\db::string($name) .
		",\"birth_date\" = ".\db::timestamp($birth_date) .
		",\"birth_city\" = ".\db::string($birth_city) .
		",\"birth_state\" = ".\db::string($birth_state) .
		",\"mothers_name\" = ".\db::string($mothers_name) .
		",\"fathers_name\" = ".\db::string($fathers_name) .
		",\"profession\" = ".\db::string($profession) .
		",\"zip_code\" = ".\db::string($zip_code) .
		",\"address\" = ".\db::string($address) .
		",\"neighborhood\" = ".\db::string($neighborhood) .
		",\"city\" = ".\db::string($city) .
		",\"state\" = ".\db::string($state) .
		",\"complement\" = ".\db::string($complement) .
		",\"fk_country\" = ".\db::number($fk_country) .
		" WHERE id = ". $id.";";

		$this->db->query($query);

		$res = new \stdClass();

		$res->server_id = $id;

		return $res;
	}


	function get_for_client_update($last_update_time, $page_number, $page_size){

		$query = "SELECT \"id\" as \"server_id\", \"last_update\", \"name\", \"birth_date\", \"birth_city\", \"birth_state\", \"mothers_name\", \"fathers_name\", \"profession\", \"zip_code\", \"address\", \"neighborhood\", \"city\", \"state\", \"complement\", \"fk_country\" FROM \"simpledb\".\"shared_client\" WHERE last_update_time > " . $last_update_time;

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
