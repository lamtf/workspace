<?php
namespace Sync\Model;

class BoletoSicoob {

	private $db;

	public function __construct(){
		$this->db = \db::getInstance();
	}

	//SYNC PROTOCOL: insert-on-client step 1,2
	public function get_for_client_insert($fk_boleto_sicoob, $page_number, $page_size){
		$query = "SELECT \"id\" as \"server_id\", \"last_update\"".
		",\"cpf\"".
		",\"numero\"".
		",\"data\"".
		",\"vencimento\"".
		",\"valor\"".
		",\"nosso_numero\"".
		",\"quantidade\"".
		",\"parcela\"".
		",\"foi_pago\"".
		",\"data_de_pagamento\"".
		",\"valor_recebido\"".
		",\"fk_invoice\"".
		" FROM \"simpledb\".\"boleto_sicoob\"".
		" WHERE \"id\" > ". \db::number($fk_boleto_sicoob).
		" LIMIT $page_size OFFSET " . ($page_size * $page_number) .";";

		$stmt = $this->db->query($query);
		$res = $stmt->fetchAll(\PDO::FETCH_OBJ);


		return $res;
	}


	//SYNC PROTOCOL: insert-on-server step 2,2
	public function insert($last_update,$cpf,$numero,$data,$vencimento,$valor,$nosso_numero,$quantidade,$parcela,$foi_pago,$data_de_pagamento,$valor_recebido,$fk_invoice){
		$last_update_time = \db::timestamp();

		$query = "INSERT INTO \"simpledb\".\"boleto_sicoob\"(".
		"\"last_update\"".
		",\"cpf\"".
		",\"numero\"".
		",\"data\"".
		",\"vencimento\"".
		",\"valor\"".
		",\"nosso_numero\"".
		",\"quantidade\"".
		",\"parcela\"".
		",\"foi_pago\"".
		",\"data_de_pagamento\"".
		",\"valor_recebido\"".
		",\"fk_invoice\"".") VALUES (".
		\db::string($last_update) .
		"," .\db::string($cpf) .
		"," .\db::string($numero) .
		"," .\db::string($data) .
		"," .\db::timestamp($vencimento) .
		"," .\db::string($valor) .
		"," .\db::string($nosso_numero) .
		"," .\db::string($quantidade) .
		"," .\db::string($parcela) .
		"," .\db::string($foi_pago) .
		"," .\db::string($data_de_pagamento) .
		"," .\db::string($valor_recebido) .
		"," .\db::number($fk_invoice) .");";

		$this->db->query($query);
		$query = "SELECT currval(pg_get_serial_sequence('simpledb.boleto_sicoob','id'));";
		$stmt = $this->db->query($query);
		$id = $stmt->fetch(\PDO::FETCH_NUM)[0];

		$res = new \stdClass();
		$res->server_id = $id;
		$res->last_update_time = $last_update_time;

		return $res;
	}


	//SYNC PROTOCOL: update-on-server step 4,3
	public function update($id, $last_update,$cpf,$numero,$data,$vencimento,$valor,$nosso_numero,$quantidade,$parcela,$foi_pago,$data_de_pagamento,$valor_recebido,$fk_invoice){

		$last_update_time = \db::timestamp();

		$query = "UPDATE \"simpledb\".\"boleto_sicoob\" SET ".
		"\"last_update\" = ".\db::string($last_update) .
		",\"cpf\" = ".\db::string($cpf) .
		",\"numero\" = ".\db::string($numero) .
		",\"data\" = ".\db::string($data) .
		",\"vencimento\" = ".\db::timestamp($vencimento) .
		",\"valor\" = ".\db::string($valor) .
		",\"nosso_numero\" = ".\db::string($nosso_numero) .
		",\"quantidade\" = ".\db::string($quantidade) .
		",\"parcela\" = ".\db::string($parcela) .
		",\"foi_pago\" = ".\db::string($foi_pago) .
		",\"data_de_pagamento\" = ".\db::string($data_de_pagamento) .
		",\"valor_recebido\" = ".\db::string($valor_recebido) .
		",\"fk_invoice\" = ".\db::number($fk_invoice) .
		" WHERE id = ". $id.";";

		$this->db->query($query);

		$res = new \stdClass();

		$res->server_id = $id;

		return $res;
	}


	function get_for_client_update($last_update_time, $page_number, $page_size){

		$query = "SELECT \"id\" as \"server_id\", \"last_update\", \"cpf\", \"numero\", \"data\", \"vencimento\", \"valor\", \"nosso_numero\", \"quantidade\", \"parcela\", \"foi_pago\", \"data_de_pagamento\", \"valor_recebido\", \"fk_invoice\" FROM \"simpledb\".\"boleto_sicoob\" WHERE last_update_time > " . $last_update_time;

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
