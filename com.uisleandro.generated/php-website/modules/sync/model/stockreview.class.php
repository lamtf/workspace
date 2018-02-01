<?php
namespace Sync\Model;

class StockReview {

	private $db;

	public function __construct(){
		$this->db = \db::getInstance();
	}

	//SYNC PROTOCOL: insert-on-client step 1,2
	public function get_for_client_insert($fk_stock_review, $page_number, $page_size){
		$query = "SELECT \"id\" as \"server_id\", \"last_update\"".
		",\"fk_product\"".
		",\"actual_amount\"".
		",\"sold_items\"".
		",\"previous_amount\"".
		",\"missing_amount\"".
		" FROM \"simpledb\".\"stock_review\"".
		" WHERE \"id\" > ". \db::number($fk_stock_review).
		" LIMIT $page_size OFFSET " . ($page_size * $page_number) .";";

		$stmt = $this->db->query($query);
		$res = $stmt->fetchAll(\PDO::FETCH_OBJ);


		return $res;
	}


	//SYNC PROTOCOL: insert-on-server step 2,2
	public function insert($last_update,$fk_product,$actual_amount,$sold_items,$previous_amount,$missing_amount){
		$last_update_time = \db::timestamp();

		$query = "INSERT INTO \"simpledb\".\"stock_review\"(".
		"\"last_update\"".
		",\"fk_product\"".
		",\"actual_amount\"".
		",\"sold_items\"".
		",\"previous_amount\"".
		",\"missing_amount\"".") VALUES (".
		\db::string($last_update) .
		"," .\db::number($fk_product) .
		"," .\db::string($actual_amount) .
		"," .\db::string($sold_items) .
		"," .\db::string($previous_amount) .
		"," .\db::string($missing_amount) .");";

		$this->db->query($query);
		$query = "SELECT currval(pg_get_serial_sequence('simpledb.stock_review','id'));";
		$stmt = $this->db->query($query);
		$id = $stmt->fetch(\PDO::FETCH_NUM)[0];

		$res = new \stdClass();
		$res->server_id = $id;
		$res->last_update_time = $last_update_time;

		return $res;
	}


	//SYNC PROTOCOL: update-on-server step 4,3
	public function update($id, $last_update,$fk_product,$actual_amount,$sold_items,$previous_amount,$missing_amount){

		$last_update_time = \db::timestamp();

		$query = "UPDATE \"simpledb\".\"stock_review\" SET ".
		"\"last_update\" = ".\db::string($last_update) .
		",\"fk_product\" = ".\db::number($fk_product) .
		",\"actual_amount\" = ".\db::string($actual_amount) .
		",\"sold_items\" = ".\db::string($sold_items) .
		",\"previous_amount\" = ".\db::string($previous_amount) .
		",\"missing_amount\" = ".\db::string($missing_amount) .
		" WHERE id = ". $id.";";

		$this->db->query($query);

		$res = new \stdClass();

		$res->server_id = $id;

		return $res;
	}


	function get_for_client_update($last_update_time, $page_number, $page_size){

		$query = "SELECT \"id\" as \"server_id\", \"last_update\", \"fk_product\", \"actual_amount\", \"sold_items\", \"previous_amount\", \"missing_amount\" FROM \"simpledb\".\"stock_review\" WHERE last_update_time > " . $last_update_time;

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
