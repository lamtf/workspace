<?php
namespace Sync\Model;

class DistributorContact {

	private $db;

	public function __construct(){
		$this->db = \db::getInstance();
	}

	//SYNC PROTOCOL: insert-on-client step 1,2
	public function get_for_client_insert($fk_distributor_contact, $page_number, $page_size){
		$query = "SELECT \"id\" as \"server_id\", \"last_update\"".
		",\"name\"".
		",\"email1\"".
		",\"email2\"".
		",\"phone_number1\"".
		",\"phone_number2\"".
		",\"phone_number3\"".
		",\"phone_number4\"".
		",\"fk_brand\"".
		" FROM \"simpledb\".\"distributor_contact\"".
		" WHERE \"id\" > ". \db::number($fk_distributor_contact).
		" LIMIT $page_size OFFSET " . ($page_size * $page_number) .";";

		$stmt = $this->db->query($query);
		$res = $stmt->fetchAll(\PDO::FETCH_OBJ);


		return $res;
	}


	//SYNC PROTOCOL: insert-on-server step 2,2
	public function insert($last_update,$name,$email1,$email2,$phone_number1,$phone_number2,$phone_number3,$phone_number4,$fk_brand){
		$last_update_time = \db::timestamp();

		$query = "INSERT INTO \"simpledb\".\"distributor_contact\"(".
		"\"last_update\"".
		",\"name\"".
		",\"email1\"".
		",\"email2\"".
		",\"phone_number1\"".
		",\"phone_number2\"".
		",\"phone_number3\"".
		",\"phone_number4\"".
		",\"fk_brand\"".") VALUES (".
		\db::string($last_update) .
		"," .\db::string($name) .
		"," .\db::string($email1) .
		"," .\db::string($email2) .
		"," .\db::string($phone_number1) .
		"," .\db::string($phone_number2) .
		"," .\db::string($phone_number3) .
		"," .\db::string($phone_number4) .
		"," .\db::number($fk_brand) .");";

		$this->db->query($query);
		$query = "SELECT currval(pg_get_serial_sequence('simpledb.distributor_contact','id'));";
		$stmt = $this->db->query($query);
		$id = $stmt->fetch(\PDO::FETCH_NUM)[0];

		$res = new \stdClass();
		$res->server_id = $id;
		$res->last_update_time = $last_update_time;

		return $res;
	}


	//SYNC PROTOCOL: update-on-server step 4,3
	public function update($id, $last_update,$name,$email1,$email2,$phone_number1,$phone_number2,$phone_number3,$phone_number4,$fk_brand){

		$last_update_time = \db::timestamp();

		$query = "UPDATE \"simpledb\".\"distributor_contact\" SET ".
		"\"last_update\" = ".\db::string($last_update) .
		",\"name\" = ".\db::string($name) .
		",\"email1\" = ".\db::string($email1) .
		",\"email2\" = ".\db::string($email2) .
		",\"phone_number1\" = ".\db::string($phone_number1) .
		",\"phone_number2\" = ".\db::string($phone_number2) .
		",\"phone_number3\" = ".\db::string($phone_number3) .
		",\"phone_number4\" = ".\db::string($phone_number4) .
		",\"fk_brand\" = ".\db::number($fk_brand) .
		" WHERE id = ". $id.";";

		$this->db->query($query);

		$res = new \stdClass();

		$res->server_id = $id;

		return $res;
	}


	function get_for_client_update($last_update_time, $page_number, $page_size){

		$query = "SELECT \"id\" as \"server_id\", \"last_update\", \"name\", \"email1\", \"email2\", \"phone_number1\", \"phone_number2\", \"phone_number3\", \"phone_number4\", \"fk_brand\" FROM \"simpledb\".\"distributor_contact\" WHERE last_update_time > " . $last_update_time;

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
