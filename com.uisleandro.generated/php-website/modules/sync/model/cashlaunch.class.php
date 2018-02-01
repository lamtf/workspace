<?php
namespace Sync\Model;

class CashLaunch {

	private $db;

	public function __construct(){
		$this->db = \db::getInstance();
	}

	//SYNC PROTOCOL: insert-on-client step 1,2
	public function get_for_client_insert($fk_cash_launch, $page_number, $page_size){
		$query = "SELECT \"id\" as \"server_id\", \"last_update\"".
		",\"fk_cash_register\"".
		",\"justification\"".
		",\"amount_spent\"".
		" FROM \"simpledb\".\"cash_launch\"".
		" WHERE \"id\" > ". \db::number($fk_cash_launch).
		" LIMIT $page_size OFFSET " . ($page_size * $page_number) .";";

		$stmt = $this->db->query($query);
		$res = $stmt->fetchAll(\PDO::FETCH_OBJ);


		return $res;
	}


	//SYNC PROTOCOL: insert-on-server step 2,2
	public function insert($last_update,$fk_cash_register,$justification,$amount_spent){
		$last_update_time = \db::timestamp();

		$query = "INSERT INTO \"simpledb\".\"cash_launch\"(".
		"\"last_update\"".
		",\"fk_cash_register\"".
		",\"justification\"".
		",\"amount_spent\"".") VALUES (".
		\db::string($last_update) .
		"," .\db::number($fk_cash_register) .
		"," .\db::string($justification) .
		"," .\db::string($amount_spent) .");";

		$this->db->query($query);
		$query = "SELECT currval(pg_get_serial_sequence('simpledb.cash_launch','id'));";
		$stmt = $this->db->query($query);
		$id = $stmt->fetch(\PDO::FETCH_NUM)[0];

		$res = new \stdClass();
		$res->server_id = $id;
		$res->last_update_time = $last_update_time;

		return $res;
	}


	//SYNC PROTOCOL: update-on-server step 4,3
	public function update($id, $last_update,$fk_cash_register,$justification,$amount_spent){

		$last_update_time = \db::timestamp();

		$query = "UPDATE \"simpledb\".\"cash_launch\" SET ".
		"\"last_update\" = ".\db::string($last_update) .
		",\"fk_cash_register\" = ".\db::number($fk_cash_register) .
		",\"justification\" = ".\db::string($justification) .
		",\"amount_spent\" = ".\db::string($amount_spent) .
		" WHERE id = ". $id.";";

		$this->db->query($query);

		$res = new \stdClass();

		$res->server_id = $id;

		return $res;
	}


	function get_for_client_update($last_update_time, $page_number, $page_size){

		$query = "SELECT \"id\" as \"server_id\", \"last_update\", \"fk_cash_register\", \"justification\", \"amount_spent\" FROM \"simpledb\".\"cash_launch\" WHERE last_update_time > " . $last_update_time;

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
