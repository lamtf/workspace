package com.uisleandro.store.client.view;
import android.database.Cursor;

public class FindByCpfOut {

	public static FindByCpfOut FromCursor(Cursor cursor){
		FindByCpfOut instance = new FindByCpfOut();
		instance.setBrazilianLastUpdate(cursor.getLong(1));
		instance.setBrazilianCpf(cursor.getString(2));
		instance.setBrazilianRg(cursor.getString(3));
		instance.setBasicClientLastUpdate(cursor.getLong(4));
		instance.setBasicClientName(cursor.getString(5));
		instance.setBasicClientBirthDate(cursor.getLong(6));
		instance.setBasicClientBirthCity(cursor.getString(7));
		instance.setBasicClientBirthState(cursor.getString(8));
		instance.setBasicClientMothersName(cursor.getString(9));
		instance.setBasicClientFathersName(cursor.getString(10));
		instance.setBasicClientProfession(cursor.getString(11));
		instance.setBasicClientZipCode(cursor.getString(12));
		instance.setBasicClientAddress(cursor.getString(13));
		instance.setBasicClientNeighborhood(cursor.getString(14));
		instance.setBasicClientCity(cursor.getString(15));
		instance.setBasicClientState(cursor.getString(16));
		instance.setBasicClientComplement(cursor.getString(17));
		instance.setCountryLastUpdate(cursor.getLong(18));
		instance.setCountryName(cursor.getString(19));
		return instance;
	}

	private Long brazilian_last_update;
	private String brazilian_cpf;
	private String brazilian_rg;
	private Long basic_client_last_update;
	private String basic_client_name;
	private Long basic_client_birth_date;
	private String basic_client_birth_city;
	private String basic_client_birth_state;
	private String basic_client_mothers_name;
	private String basic_client_fathers_name;
	private String basic_client_profession;
	private String basic_client_zip_code;
	private String basic_client_address;
	private String basic_client_neighborhood;
	private String basic_client_city;
	private String basic_client_state;
	private String basic_client_complement;
	private Long country_last_update;
	private String country_name;

	public Long getBrazilianLastUpdate () {
		return brazilian_last_update;
	}
	
	public void setBrazilianLastUpdate (Long brazilian_last_update) {
		this.brazilian_last_update = brazilian_last_update;
	}

	public String getBrazilianCpf () {
		return brazilian_cpf;
	}
	
	public void setBrazilianCpf (String brazilian_cpf) {
		this.brazilian_cpf = brazilian_cpf;
	}

	public String getBrazilianRg () {
		return brazilian_rg;
	}
	
	public void setBrazilianRg (String brazilian_rg) {
		this.brazilian_rg = brazilian_rg;
	}

	public Long getBasicClientLastUpdate () {
		return basic_client_last_update;
	}
	
	public void setBasicClientLastUpdate (Long basic_client_last_update) {
		this.basic_client_last_update = basic_client_last_update;
	}

	public String getBasicClientName () {
		return basic_client_name;
	}
	
	public void setBasicClientName (String basic_client_name) {
		this.basic_client_name = basic_client_name;
	}

	public Long getBasicClientBirthDate () {
		return basic_client_birth_date;
	}
	
	public void setBasicClientBirthDate (Long basic_client_birth_date) {
		this.basic_client_birth_date = basic_client_birth_date;
	}

	public String getBasicClientBirthCity () {
		return basic_client_birth_city;
	}
	
	public void setBasicClientBirthCity (String basic_client_birth_city) {
		this.basic_client_birth_city = basic_client_birth_city;
	}

	public String getBasicClientBirthState () {
		return basic_client_birth_state;
	}
	
	public void setBasicClientBirthState (String basic_client_birth_state) {
		this.basic_client_birth_state = basic_client_birth_state;
	}

	public String getBasicClientMothersName () {
		return basic_client_mothers_name;
	}
	
	public void setBasicClientMothersName (String basic_client_mothers_name) {
		this.basic_client_mothers_name = basic_client_mothers_name;
	}

	public String getBasicClientFathersName () {
		return basic_client_fathers_name;
	}
	
	public void setBasicClientFathersName (String basic_client_fathers_name) {
		this.basic_client_fathers_name = basic_client_fathers_name;
	}

	public String getBasicClientProfession () {
		return basic_client_profession;
	}
	
	public void setBasicClientProfession (String basic_client_profession) {
		this.basic_client_profession = basic_client_profession;
	}

	public String getBasicClientZipCode () {
		return basic_client_zip_code;
	}
	
	public void setBasicClientZipCode (String basic_client_zip_code) {
		this.basic_client_zip_code = basic_client_zip_code;
	}

	public String getBasicClientAddress () {
		return basic_client_address;
	}
	
	public void setBasicClientAddress (String basic_client_address) {
		this.basic_client_address = basic_client_address;
	}

	public String getBasicClientNeighborhood () {
		return basic_client_neighborhood;
	}
	
	public void setBasicClientNeighborhood (String basic_client_neighborhood) {
		this.basic_client_neighborhood = basic_client_neighborhood;
	}

	public String getBasicClientCity () {
		return basic_client_city;
	}
	
	public void setBasicClientCity (String basic_client_city) {
		this.basic_client_city = basic_client_city;
	}

	public String getBasicClientState () {
		return basic_client_state;
	}
	
	public void setBasicClientState (String basic_client_state) {
		this.basic_client_state = basic_client_state;
	}

	public String getBasicClientComplement () {
		return basic_client_complement;
	}
	
	public void setBasicClientComplement (String basic_client_complement) {
		this.basic_client_complement = basic_client_complement;
	}

	public Long getCountryLastUpdate () {
		return country_last_update;
	}
	
	public void setCountryLastUpdate (Long country_last_update) {
		this.country_last_update = country_last_update;
	}

	public String getCountryName () {
		return country_name;
	}
	
	public void setCountryName (String country_name) {
		this.country_name = country_name;
	}

}
