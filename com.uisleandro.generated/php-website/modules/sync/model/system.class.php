<?php
namespace Sync\Model;

class System {

	private $db;

	public function __construct(){
		$this->db = \db::getInstance();
	}

	//SYNC PROTOCOL: insert-on-client step 1,2
	public function get_for_client_insert($fk_system, $page_number, $page_size){
		$query = "SELECT \"id\" as \"server_id\", \"last_update\"".
		",\"name\"".
		",\"enabled\"".
		",\"fk_currency\"".
		",\"fantasy_name\"".
		",\"stores_address\"".
		",\"srores_address_number\"".
		",\"stores_city\"".
		",\"stores_neighborhood\"".
		",\"stores_zip_code\"".
		",\"stores_state\"".
		",\"stores_email\"".
		",\"stores_phonenumber\"".
		",\"fk_reseller\"".
		" FROM \"simpledb\".\"system\"".
		" WHERE \"id\" > ". \db::number($fk_system).
		" LIMIT $page_size OFFSET " . ($page_size * $page_number) .";";

		$stmt = $this->db->query($query);
		$res = $stmt->fetchAll(\PDO::FETCH_OBJ);


		return $res;
	}


	//SYNC PROTOCOL: insert-on-server step 2,2
	public function insert($last_update,$name,$enabled,$fk_currency,$fantasy_name,$stores_address,$srores_address_number,$stores_city,$stores_neighborhood,$stores_zip_code,$stores_state,$stores_email,$stores_phonenumber,$fk_reseller){
		$last_update_time = \db::timestamp();

		$query = "INSERT INTO \"simpledb\".\"system\"(".
		"\"last_update\"".
		",\"name\"".
		",\"enabled\"".
		",\"fk_currency\"".
		",\"fantasy_name\"".
		",\"stores_address\"".
		",\"srores_address_number\"".
		",\"stores_city\"".
		",\"stores_neighborhood\"".
		",\"stores_zip_code\"".
		",\"stores_state\"".
		",\"stores_email\"".
		",\"stores_phonenumber\"".
		",\"fk_reseller\"".") VALUES (".
		\db::string($last_update) .
		"," .\db::string($name) .
		"," .\db::string($enabled) .
		"," .\db::number($fk_currency) .
		"," .\db::string($fantasy_name) .
		"," .\db::string($stores_address) .
		"," .\db::string($srores_address_number) .
		"," .\db::string($stores_city) .
		"," .\db::string($stores_neighborhood) .
		"," .\db::string($stores_zip_code) .
		"," .\db::string($stores_state) .
		"," .\db::string($stores_email) .
		"," .\db::string($stores_phonenumber) .
		"," .\db::number($fk_reseller) .");";

		$this->db->query($query);
		$query = "SELECT currval(pg_get_serial_sequence('simpledb.system','id'));";
		$stmt = $this->db->query($query);
		$id = $stmt->fetch(\PDO::FETCH_NUM)[0];

		$res = new \stdClass();
		$res->server_id = $id;
		$res->last_update_time = $last_update_time;

		return $res;
	}


	//SYNC PROTOCOL: update-on-server step 4,3
	public function update($id, $last_update,$name,$enabled,$fk_currency,$fantasy_name,$stores_address,$srores_address_number,$stores_city,$stores_neighborhood,$stores_zip_code,$stores_state,$stores_email,$stores_phonenumber,$fk_reseller){

		$last_update_time = \db::timestamp();

		$query = "UPDATE \"simpledb\".\"system\" SET ".
		"\"last_update\" = ".\db::string($last_update) .
		",\"name\" = ".\db::string($name) .
		",\"enabled\" = ".\db::string($enabled) .
		",\"fk_currency\" = ".\db::number($fk_currency) .
		",\"fantasy_name\" = ".\db::string($fantasy_name) .
		",\"stores_address\" = ".\db::string($stores_address) .
		",\"srores_address_number\" = ".\db::string($srores_address_number) .
		",\"stores_city\" = ".\db::string($stores_city) .
		",\"stores_neighborhood\" = ".\db::string($stores_neighborhood) .
		",\"stores_zip_code\" = ".\db::string($stores_zip_code) .
		",\"stores_state\" = ".\db::string($stores_state) .
		",\"stores_email\" = ".\db::string($stores_email) .
		",\"stores_phonenumber\" = ".\db::string($stores_phonenumber) .
		",\"fk_reseller\" = ".\db::number($fk_reseller) .
		" WHERE id = ". $id.";";

		$this->db->query($query);

		$res = new \stdClass();

		$res->server_id = $id;

		return $res;
	}


	function get_for_client_update($last_update_time, $page_number, $page_size){

		$query = "SELECT \"id\" as \"server_id\", \"last_update\", \"name\", \"enabled\", \"fk_currency\", \"fantasy_name\", \"stores_address\", \"srores_address_number\", \"stores_city\", \"stores_neighborhood\", \"stores_zip_code\", \"stores_state\", \"stores_email\", \"stores_phonenumber\", \"fk_reseller\" FROM \"simpledb\".\"system\" WHERE last_update_time > " . $last_update_time;

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
