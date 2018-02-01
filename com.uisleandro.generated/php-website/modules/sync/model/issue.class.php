<?php
namespace Sync\Model;

class Issue {

	private $db;

	public function __construct(){
		$this->db = \db::getInstance();
	}

	//SYNC PROTOCOL: insert-on-client step 1,2
	public function get_for_client_insert($fk_Issue, $page_number, $page_size){
		$query = "SELECT \"id\" as \"server_id\", \"last_update\"".
		",\"fk_shared_client\"".
		",\"fk_system\"".
		",\"description\"".
		",\"active\"".
		",\"isAnswer\"".
		",\"fk_issue\"".
		" FROM \"simpledb\".\"Issue\"".
		" WHERE \"id\" > ". \db::number($fk_Issue).
		" LIMIT $page_size OFFSET " . ($page_size * $page_number) .";";

		$stmt = $this->db->query($query);
		$res = $stmt->fetchAll(\PDO::FETCH_OBJ);


		return $res;
	}


	//SYNC PROTOCOL: insert-on-server step 2,2
	public function insert($last_update,$fk_shared_client,$fk_system,$description,$active,$isAnswer,$fk_issue){
		$last_update_time = \db::timestamp();

		$query = "INSERT INTO \"simpledb\".\"Issue\"(".
		"\"last_update\"".
		",\"fk_shared_client\"".
		",\"fk_system\"".
		",\"description\"".
		",\"active\"".
		",\"isAnswer\"".
		",\"fk_issue\"".") VALUES (".
		\db::string($last_update) .
		"," .\db::number($fk_shared_client) .
		"," .\db::number($fk_system) .
		"," .\db::string($description) .
		"," .\db::string($active) .
		"," .\db::string($isAnswer) .
		"," .\db::number($fk_issue) .");";

		$this->db->query($query);
		$query = "SELECT currval(pg_get_serial_sequence('simpledb.Issue','id'));";
		$stmt = $this->db->query($query);
		$id = $stmt->fetch(\PDO::FETCH_NUM)[0];

		$res = new \stdClass();
		$res->server_id = $id;
		$res->last_update_time = $last_update_time;

		return $res;
	}


	//SYNC PROTOCOL: update-on-server step 4,3
	public function update($id, $last_update,$fk_shared_client,$fk_system,$description,$active,$isAnswer,$fk_issue){

		$last_update_time = \db::timestamp();

		$query = "UPDATE \"simpledb\".\"Issue\" SET ".
		"\"last_update\" = ".\db::string($last_update) .
		",\"fk_shared_client\" = ".\db::number($fk_shared_client) .
		",\"fk_system\" = ".\db::number($fk_system) .
		",\"description\" = ".\db::string($description) .
		",\"active\" = ".\db::string($active) .
		",\"isAnswer\" = ".\db::string($isAnswer) .
		",\"fk_issue\" = ".\db::number($fk_issue) .
		" WHERE id = ". $id.";";

		$this->db->query($query);

		$res = new \stdClass();

		$res->server_id = $id;

		return $res;
	}


	function get_for_client_update($last_update_time, $page_number, $page_size){

		$query = "SELECT \"id\" as \"server_id\", \"last_update\", \"fk_shared_client\", \"fk_system\", \"description\", \"active\", \"isAnswer\", \"fk_issue\" FROM \"simpledb\".\"Issue\" WHERE last_update_time > " . $last_update_time;

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
