<?php
namespace Sync\Model;

class Sale {

	private $db;

	public function __construct(){
		$this->db = \db::getInstance();
	}

	//SYNC PROTOCOL: insert-on-client step 1,2
	public function get_for_client_insert($fk_sale, $page_number, $page_size){
		$query = "SELECT \"id\" as \"server_id\", \"last_update\"".
		",\"fk_sale_type\"".
		",\"fk_system\"".
		",\"total_value\"".
		",\"fk_user\"".
		",\"fk_client_from_system\"".
		" FROM \"simpledb\".\"sale\"".
		" WHERE \"id\" > ". \db::number($fk_sale).
		" LIMIT $page_size OFFSET " . ($page_size * $page_number) .";";

		$stmt = $this->db->query($query);
		$res = $stmt->fetchAll(\PDO::FETCH_OBJ);


		return $res;
	}


	//SYNC PROTOCOL: insert-on-server step 2,2
	public function insert($last_update,$fk_sale_type,$fk_system,$total_value,$fk_user,$fk_client_from_system){
		$last_update_time = \db::timestamp();

		$query = "INSERT INTO \"simpledb\".\"sale\"(".
		"\"last_update\"".
		",\"fk_sale_type\"".
		",\"fk_system\"".
		",\"total_value\"".
		",\"fk_user\"".
		",\"fk_client_from_system\"".") VALUES (".
		\db::string($last_update) .
		"," .\db::number($fk_sale_type) .
		"," .\db::number($fk_system) .
		"," .\db::string($total_value) .
		"," .\db::number($fk_user) .
		"," .\db::number($fk_client_from_system) .");";

		$this->db->query($query);
		$query = "SELECT currval(pg_get_serial_sequence('simpledb.sale','id'));";
		$stmt = $this->db->query($query);
		$id = $stmt->fetch(\PDO::FETCH_NUM)[0];

		$res = new \stdClass();
		$res->server_id = $id;
		$res->last_update_time = $last_update_time;

		return $res;
	}


	//SYNC PROTOCOL: update-on-server step 4,3
	public function update($id, $last_update,$fk_sale_type,$fk_system,$total_value,$fk_user,$fk_client_from_system){

		$last_update_time = \db::timestamp();

		$query = "UPDATE \"simpledb\".\"sale\" SET ".
		"\"last_update\" = ".\db::string($last_update) .
		",\"fk_sale_type\" = ".\db::number($fk_sale_type) .
		",\"fk_system\" = ".\db::number($fk_system) .
		",\"total_value\" = ".\db::string($total_value) .
		",\"fk_user\" = ".\db::number($fk_user) .
		",\"fk_client_from_system\" = ".\db::number($fk_client_from_system) .
		" WHERE id = ". $id.";";

		$this->db->query($query);

		$res = new \stdClass();

		$res->server_id = $id;

		return $res;
	}


	function get_for_client_update($last_update_time, $page_number, $page_size){

		$query = "SELECT \"id\" as \"server_id\", \"last_update\", \"fk_sale_type\", \"fk_system\", \"total_value\", \"fk_user\", \"fk_client_from_system\" FROM \"simpledb\".\"sale\" WHERE last_update_time > " . $last_update_time;

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
