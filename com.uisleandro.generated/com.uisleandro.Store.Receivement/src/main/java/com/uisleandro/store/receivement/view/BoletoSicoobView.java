
package com.uisleandro.store.receivement.view; 

import org.json.JSONException;
import org.json.JSONObject;

public class BoletoSicoobView{

	private long id;
	private long server_id;
	private boolean dirty;
	private long last_update;
	private String cpf;
	private String numero;
	private long data;
	private long vencimento;
	private float valor;
	private String nosso_numero;
	private int quantidade;
	private int parcela;
	private boolean foi_pago;
	private long data_de_pagamento;
	private float valor_recebido;
	private long fk_invoice;

	public BoletoSicoobView(){
		this.id = 0L;
		this.server_id = 0L;
		this.dirty = false;
		this.last_update = 0L;
		this.cpf = "";
		this.numero = "";
		this.data = 0L;
		this.vencimento = 0L;
		this.valor = 0F;
		this.nosso_numero = "";
		this.quantidade = 0;
		this.parcela = 0;
		this.foi_pago = false;
		this.data_de_pagamento = 0L;
		this.valor_recebido = 0F;
		this.fk_invoice = 0L;

	}

	public long getId(){
		return id;
	}

	public void setId(long id){
		this.id = id;
	}

	public long getServerId(){
		return server_id;
	}

	public void setServerId(long server_id){
		this.server_id = server_id;
	}

	public boolean isDirty(){
		return dirty;
	}

	public void setDirty(boolean dirty){
		this.dirty = dirty;
	}

	public long getLastUpdate(){
		return last_update;
	}

	public void setLastUpdate(long last_update){
		this.last_update = last_update;
	}

	public String getCpf(){
		return cpf;
	}

	public void setCpf(String cpf){
		this.cpf = cpf;
	}

	public String getNumero(){
		return numero;
	}

	public void setNumero(String numero){
		this.numero = numero;
	}

	public long getData(){
		return data;
	}

	public void setData(long data){
		this.data = data;
	}

	public long getVencimento(){
		return vencimento;
	}

	public void setVencimento(long vencimento){
		this.vencimento = vencimento;
	}

	public float getValor(){
		return valor;
	}

	public void setValor(float valor){
		this.valor = valor;
	}

	public String getNossoNumero(){
		return nosso_numero;
	}

	public void setNossoNumero(String nosso_numero){
		this.nosso_numero = nosso_numero;
	}

	public int getQuantidade(){
		return quantidade;
	}

	public void setQuantidade(int quantidade){
		this.quantidade = quantidade;
	}

	public int getParcela(){
		return parcela;
	}

	public void setParcela(int parcela){
		this.parcela = parcela;
	}

	public boolean getFoiPago(){
		return foi_pago;
	}

	public void setFoiPago(boolean foi_pago){
		this.foi_pago = foi_pago;
	}

	public long getDataDePagamento(){
		return data_de_pagamento;
	}

	public void setDataDePagamento(long data_de_pagamento){
		this.data_de_pagamento = data_de_pagamento;
	}

	public float getValorRecebido(){
		return valor_recebido;
	}

	public void setValorRecebido(float valor_recebido){
		this.valor_recebido = valor_recebido;
	}

	public long getFkInvoice(){
		return fk_invoice;
	}

	public void setFkInvoice(long fk_invoice){
		this.fk_invoice = fk_invoice;
	}


	public String toJsonString(){

		String that = "{" +
			"\"client_id\":\"" + this.id + "\"," +
			"\"server_id\":\"" + this.server_id + "\"," +
			"\"last_update\":\"" + this.last_update+ "\"," + 
			"\"cpf\":\"" + this.cpf+ "\"," + 
			"\"numero\":\"" + this.numero+ "\"," + 
			"\"data\":\"" + this.data+ "\"," + 
			"\"vencimento\":\"" + this.vencimento+ "\"," + 
			"\"valor\":\"" + this.valor+ "\"," + 
			"\"nosso_numero\":\"" + this.nosso_numero+ "\"," + 
			"\"quantidade\":\"" + this.quantidade+ "\"," + 
			"\"parcela\":\"" + this.parcela+ "\"," + 
			"\"foi_pago\":\"" + this.foi_pago+ "\"," + 
			"\"data_de_pagamento\":\"" + this.data_de_pagamento+ "\"," + 
			"\"valor_recebido\":\"" + this.valor_recebido+ "\"," + 
			"\"fk_invoice\":\"" + this.fk_invoice+ "\"" + 
		"}";

		return that;

	}

	public String toString(){

		return this.cpf;

	}

	public static BoletoSicoobView FromJson(String json){

		if(json != null) {
		try {

		JSONObject obj = new JSONObject(json);
				BoletoSicoobView result = new BoletoSicoobView();

				if(obj.has("client_id") && !obj.isNull("client_id")){
					result.setId(obj.getLong("client_id"));
				}
				if(obj.has("server_id") && !obj.isNull("server_id")){
					result.setServerId(obj.getLong("server_id"));
				}
				result.setLastUpdate(obj.getLong("last_update"));
				result.setCpf(obj.getString("cpf"));
				result.setNumero(obj.getString("numero"));
				result.setData(obj.getLong("data"));
				result.setVencimento(obj.getLong("vencimento"));
				result.setValor(Float.valueOf(obj.getString("valor")));
				result.setNossoNumero(obj.getString("nosso_numero"));
				result.setQuantidade(obj.getInt("quantidade"));
				result.setParcela(obj.getInt("parcela"));
				result.setFoiPago(obj.getInt("foi_pago") > 0);
				result.setDataDePagamento(obj.getLong("data_de_pagamento"));
				result.setValorRecebido(Float.valueOf(obj.getString("valor_recebido")));
				if(obj.has("server_id") && !obj.isNull("fk_invoice")){
					result.setFkInvoice(obj.getLong("fk_invoice"));
				}

				return result;

			} catch (JSONException e) {
				e.printStackTrace();
			}
		}

		return null;

	}


	public static BoletoSicoobView FromJsonObj(JSONObject obj){

		if(null != obj) {
			try {
				BoletoSicoobView result = new BoletoSicoobView();

				if(obj.has("client_id") && !obj.isNull("client_id")){
					result.setId(obj.getLong("client_id"));
				}
				if(obj.has("server_id") && !obj.isNull("server_id")){
					result.setServerId(obj.getLong("server_id"));
				}
				result.setLastUpdate(obj.getLong("last_update"));
				result.setCpf(obj.getString("cpf"));
				result.setNumero(obj.getString("numero"));
				result.setData(obj.getLong("data"));
				result.setVencimento(obj.getLong("vencimento"));
				result.setValor(Float.valueOf(obj.getString("valor")));
				result.setNossoNumero(obj.getString("nosso_numero"));
				result.setQuantidade(obj.getInt("quantidade"));
				result.setParcela(obj.getInt("parcela"));
				result.setFoiPago(obj.getInt("foi_pago") > 0);
				result.setDataDePagamento(obj.getLong("data_de_pagamento"));
				result.setValorRecebido(Float.valueOf(obj.getString("valor_recebido")));
				if(obj.has("fk_invoice") && !obj.isNull("fk_invoice")){
					result.setFkInvoice(obj.getLong("fk_invoice"));
				}

				return result;
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}

		return null;

	}


}
