package com.uisleandro.store.receivement.view; 

import org.json.JSONException;
import org.json.JSONObject;
import android.database.Cursor;

public class BoletoSicoobDataView {

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

	public BoletoSicoobView () {
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

	public long getId () {
		return id;
	}

	public void setId (long id) {
		this.id = id;
	}

	public long getServerId () {
		return server_id;
	}

	public void setServerId (long server_id) {
		this.server_id = server_id;
	}

	public boolean isDirty () {
		return dirty;
	}

	public void setDirty (boolean dirty) {
		this.dirty = dirty;
	}

	public long getLastUpdate () {
		return last_update;
	}

	public void setLastUpdate (long last_update) {
		this.last_update = last_update;
	}

	public String getCpf () {
		return cpf;
	}

	public void setCpf (String cpf) {
		this.cpf = cpf;
	}

	public String getNumero () {
		return numero;
	}

	public void setNumero (String numero) {
		this.numero = numero;
	}

	public long getData () {
		return data;
	}

	public void setData (long data) {
		this.data = data;
	}

	public long getVencimento () {
		return vencimento;
	}

	public void setVencimento (long vencimento) {
		this.vencimento = vencimento;
	}

	public float getValor () {
		return valor;
	}

	public void setValor (float valor) {
		this.valor = valor;
	}

	public String getNossoNumero () {
		return nosso_numero;
	}

	public void setNossoNumero (String nosso_numero) {
		this.nosso_numero = nosso_numero;
	}

	public int getQuantidade () {
		return quantidade;
	}

	public void setQuantidade (int quantidade) {
		this.quantidade = quantidade;
	}

	public int getParcela () {
		return parcela;
	}

	public void setParcela (int parcela) {
		this.parcela = parcela;
	}

	public boolean getFoiPago () {
		return foi_pago;
	}

	public void setFoiPago (boolean foi_pago) {
		this.foi_pago = foi_pago;
	}

	public long getDataDePagamento () {
		return data_de_pagamento;
	}

	public void setDataDePagamento (long data_de_pagamento) {
		this.data_de_pagamento = data_de_pagamento;
	}

	public float getValorRecebido () {
		return valor_recebido;
	}

	public void setValorRecebido (float valor_recebido) {
		this.valor_recebido = valor_recebido;
	}

	public long getFkInvoice () {
		return fk_invoice;
	}

	public void setFkInvoice (long fk_invoice) {
		this.fk_invoice = fk_invoice;
	}

	public String toJsonString () {
		String result = "{" +
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
		return result;
	}

	public String toString () {
		return this.cpf;

	}

	public static BoletoSicoobView FromJson(String json){
		if(json != null) {
			try {
				JSONObject obj = new JSONObject(json);
				return BoletoSicoobView.FromJsonObj(obj);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	public static BoletoSicoobView FromJsonObj (JSONObject obj) {
		if(null != obj) {
			try {
				BoletoSicoobView result = new BoletoSicoobView();
				if(obj.has("client_id") && !obj.isNull("client_id")){
					result.setId(obj.getLong("client_id"));
				}
				if(obj.has("server_id") && !obj.isNull("server_id")){
					result.setServerId(obj.getLong("server_id"));
				}
				/* if(obj.has("dirty") && !obj.isNull("dirty")){
					result.setDirty(obj.getInt("dirty") > 0);
				} */
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

	public static BoletoSicoobView FromCursor (Cursor cursor) {
		if(null != cursor){
			BoletoSicoobView result = new BoletoSicoobView();
			result.setId(cursor.getLong(0));
			result.setServerId(cursor.getLong(1));
			result.setDirty(cursor.getInt(2) > 0);
			result.setLastUpdate(cursor.getLong(3));
			result.setCpf(cursor.getString(4));
			result.setNumero(cursor.getString(5));
			result.setData(cursor.getLong(6));
			result.setVencimento(cursor.getLong(7));
			result.setValor(cursor.getFloat(8));
			result.setNossoNumero(cursor.getString(9));
			result.setQuantidade(cursor.getInt(10));
			result.setParcela(cursor.getInt(11));
			result.setFoiPago((cursor.getInt(12) > 0));
			result.setDataDePagamento(cursor.getLong(13));
			result.setValorRecebido(cursor.getFloat(14));
			result.setFkInvoice(cursor.getLong(15));
			return result;		
		}
		return null;
	}

	public String[] toInsertArray () {
		return new String[]{String.valueOf(last_update), cpf, numero, String.valueOf(data), String.valueOf(vencimento), String.valueOf(valor), nosso_numero, String.valueOf(quantidade), String.valueOf(parcela), String.valueOf(foi_pago), String.valueOf(data_de_pagamento), String.valueOf(valor_recebido), String.valueOf(invoice)};
	}

	public String[] toUpdateArray () {
		return new String[]{String.valueOf(id), String.valueOf(server_id), String.valueOf(dirty), String.valueOf(last_update), cpf, numero, String.valueOf(data), String.valueOf(vencimento), String.valueOf(valor), nosso_numero, String.valueOf(quantidade), String.valueOf(parcela), String.valueOf(foi_pago), String.valueOf(data_de_pagamento), String.valueOf(valor_recebido), String.valueOf(invoice)};
	}

}
