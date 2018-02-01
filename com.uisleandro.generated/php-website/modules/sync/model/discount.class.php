<?php
namespace Sync\Model;

class Discount {

	private $db;

	public function __construct(){
		$this->db = \db::getInstance();
	}

	//SYNC PROTOCOL: insert-on-client step 1,2
	public function get_for_client_insert($fk_discount, $page_number, $page_size){
		$query = "SELECT \"id\" as \"server_id\", \"last_update\"".
		",\"value\"".
		",\"percentage\"".
		",\"fk_product\"".
		",\"fk_category\"".
		",\"fk_brand\"".
		",\"fk_client_from_system\"".
		",\"fk_gender\"".
		" FROM \"simpledb\".\"discount\"".
		" WHERE \"id\" > ". \db::number($fk_discount).
		" LIMIT $page_size OFFSET " . ($page_size * $page_number) .";";

		$stmt = $this->db->query($query);
		$res = $stmt->fetchAll(\PDO::FETCH_OBJ);


		return $res;
	}


	//SYNC PROTOCOL: insert-on-server step 2,2
	public function insert($last_update,$value,$percentage,$fk_product,$fk_category,$fk_brand,$fk_client_from_system,$fk_gender){
		$last_update_time = \db::timestamp();

		$query = "INSERT INTO \"simpledb\".\"discount\"(".
		"\"last_update\"".
		",\"value\"".
		",\"percentage\"".
		",\"fk_product\"".
		",\"fk_category\"".
		",\"fk_brand\"".
		",\"fk_client_from_system\"".
		",\"fk_gender\"".") VALUES (".
		\db::string($last_update) .
		"," .\db::string($value) .
		"," .\db::string($percentage) .
		"," .\db::number($fk_product) .
		"," .\db::number($fk_category) .
		"," .\db::number($fk_brand) .
		"," .\db::number($fk_client_from_system) .
		"," .\db::number($fk_gender) .");";

		$this->db->query($query);
		$query = "SELECT currval(pg_get_serial_sequence('simpledb.discount','id'));";
		$stmt = $this->db->query($query);
		$id = $stmt->fetch(\PDO::FETCH_NUM)[0];

		$res = new \stdClass();
		$res->server_id = $id;
		$res->last_update_time = $last_update_time;

		return $res;
	}


	//SYNC PROTOCOL: update-on-server step 4,3
	public function update($id, $last_update,$value,$percentage,$fk_product,$fk_category,$fk_brand,$fk_client_from_system,$fk_gender){

		$last_update_time = \db::timestamp();

		$query = "UPDATE \"simpledb\".\"discount\" SET ".
		"\"last_update\" = ".\db::string($last_update) .
		",\"value\" = ".\db::string($value) .
		",\"percentage\" = ".\db::string($percentage) .
		",\"fk_product\" = ".\db::number($fk_product) .
		",\"fk_category\" = ".\db::number($fk_category) .
		",\"fk_brand\" = ".\db::number($fk_brand) .
		",\"fk_client_from_system\" = ".\db::number($fk_client_from_system) .
		",\"fk_gender\" = ".\db::number($fk_gender) .
		" WHERE id = ". $id.";";

		$this->db->query($query);

		$res = new \stdClass();

		$res->server_id = $id;

		return $res;
	}


	function get_for_client_update($last_update_time, $page_number, $page_size){

		$query = "SELECT \"id\" as \"server_id\", \"last_update\", \"value\", \"percentage\", \"fk_product\", \"fk_category\", \"fk_brand\", \"fk_client_from_system\", \"fk_gender\" FROM \"simpledb\".\"discount\" WHERE last_update_time > " . $last_update_time;

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
