<?php
namespace Sync\Model;

class Product {

	private $db;

	public function __construct(){
		$this->db = \db::getInstance();
	}

	//SYNC PROTOCOL: insert-on-client step 1,2
	public function get_for_client_insert($fk_product, $page_number, $page_size){
		$query = "SELECT \"id\" as \"server_id\", \"last_update\"".
		",\"fk_system\"".
		",\"barcode\"".
		",\"description\"".
		",\"amount\"".
		",\"fk_gender\"".
		",\"purchase_price\"".
		",\"sale_price\"".
		",\"fk_category\"".
		",\"size\"".
		",\"fk_unit\"".
		",\"expiration_date\"".
		",\"fk_brand\"".
		" FROM \"simpledb\".\"product\"".
		" WHERE \"id\" > ". \db::number($fk_product).
		" LIMIT $page_size OFFSET " . ($page_size * $page_number) .";";

		$stmt = $this->db->query($query);
		$res = $stmt->fetchAll(\PDO::FETCH_OBJ);


		return $res;
	}


	//SYNC PROTOCOL: insert-on-server step 2,2
	public function insert($last_update,$fk_system,$barcode,$description,$amount,$fk_gender,$purchase_price,$sale_price,$fk_category,$size,$fk_unit,$expiration_date,$fk_brand){
		$last_update_time = \db::timestamp();

		$query = "INSERT INTO \"simpledb\".\"product\"(".
		"\"last_update\"".
		",\"fk_system\"".
		",\"barcode\"".
		",\"description\"".
		",\"amount\"".
		",\"fk_gender\"".
		",\"purchase_price\"".
		",\"sale_price\"".
		",\"fk_category\"".
		",\"size\"".
		",\"fk_unit\"".
		",\"expiration_date\"".
		",\"fk_brand\"".") VALUES (".
		\db::string($last_update) .
		"," .\db::number($fk_system) .
		"," .\db::string($barcode) .
		"," .\db::string($description) .
		"," .\db::string($amount) .
		"," .\db::number($fk_gender) .
		"," .\db::string($purchase_price) .
		"," .\db::string($sale_price) .
		"," .\db::number($fk_category) .
		"," .\db::string($size) .
		"," .\db::number($fk_unit) .
		"," .\db::timestamp($expiration_date) .
		"," .\db::number($fk_brand) .");";

		$this->db->query($query);
		$query = "SELECT currval(pg_get_serial_sequence('simpledb.product','id'));";
		$stmt = $this->db->query($query);
		$id = $stmt->fetch(\PDO::FETCH_NUM)[0];

		$res = new \stdClass();
		$res->server_id = $id;
		$res->last_update_time = $last_update_time;

		return $res;
	}


	//SYNC PROTOCOL: update-on-server step 4,3
	public function update($id, $last_update,$fk_system,$barcode,$description,$amount,$fk_gender,$purchase_price,$sale_price,$fk_category,$size,$fk_unit,$expiration_date,$fk_brand){

		$last_update_time = \db::timestamp();

		$query = "UPDATE \"simpledb\".\"product\" SET ".
		"\"last_update\" = ".\db::string($last_update) .
		",\"fk_system\" = ".\db::number($fk_system) .
		",\"barcode\" = ".\db::string($barcode) .
		",\"description\" = ".\db::string($description) .
		",\"amount\" = ".\db::string($amount) .
		",\"fk_gender\" = ".\db::number($fk_gender) .
		",\"purchase_price\" = ".\db::string($purchase_price) .
		",\"sale_price\" = ".\db::string($sale_price) .
		",\"fk_category\" = ".\db::number($fk_category) .
		",\"size\" = ".\db::string($size) .
		",\"fk_unit\" = ".\db::number($fk_unit) .
		",\"expiration_date\" = ".\db::timestamp($expiration_date) .
		",\"fk_brand\" = ".\db::number($fk_brand) .
		" WHERE id = ". $id.";";

		$this->db->query($query);

		$res = new \stdClass();

		$res->server_id = $id;

		return $res;
	}


	function get_for_client_update($last_update_time, $page_number, $page_size){

		$query = "SELECT \"id\" as \"server_id\", \"last_update\", \"fk_system\", \"barcode\", \"description\", \"amount\", \"fk_gender\", \"purchase_price\", \"sale_price\", \"fk_category\", \"size\", \"fk_unit\", \"expiration_date\", \"fk_brand\" FROM \"simpledb\".\"product\" WHERE last_update_time > " . $last_update_time;

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
