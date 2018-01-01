DROP FUNCTION "salesv001"."cash_register_insert"(
		"xlast_update" INTEGER, 
		"xfk_user" INTEGER, 
		"xopening_value" DECIMAL(10,2), 
		"xreceived_value" DECIMAL(10,2), 
		"xclosing_value" DECIMAL(10,2), 
		"xfk_currency" INTEGER);

DROP FUNCTION "salesv001"."cash_register_update"(
	xid bigint,
		xlast_update INTEGER, 
		xfk_user INTEGER, 
		xopening_value DECIMAL(10,2), 
		xreceived_value DECIMAL(10,2), 
		xclosing_value DECIMAL(10,2), 
		xfk_currency INTEGER);

DROP "salesv001"."cash_register_select"(ref refcursor, pagesize integer, pagecount integer);

DROP FUNCTION "salesv001"."cash_launch_insert"(
		"xlast_update" INTEGER, 
		"xfk_cash_register" INTEGER, 
		"xjustification" CHAR(30), 
		"xamount_spent" DECIMAL(10,2), 
		"xfk_currency" INTEGER);

DROP FUNCTION "salesv001"."cash_launch_update"(
	xid bigint,
		xlast_update INTEGER, 
		xfk_cash_register INTEGER, 
		xjustification CHAR(30), 
		xamount_spent DECIMAL(10,2), 
		xfk_currency INTEGER);

DROP "salesv001"."cash_launch_select"(ref refcursor, pagesize integer, pagecount integer);

DROP FUNCTION "salesv001"."stock_review_insert"(
		"xlast_update" INTEGER, 
		"xfk_product" INTEGER, 
		"xactual_amount" INTEGER, 
		"xsold_items" INTEGER, 
		"xprevious_amount" INTEGER, 
		"xmissing_amount" INTEGER);

DROP FUNCTION "salesv001"."stock_review_update"(
	xid bigint,
		xlast_update INTEGER, 
		xfk_product INTEGER, 
		xactual_amount INTEGER, 
		xsold_items INTEGER, 
		xprevious_amount INTEGER, 
		xmissing_amount INTEGER);

DROP "salesv001"."stock_review_select"(ref refcursor, pagesize integer, pagecount integer);

DROP FUNCTION "salesv001"."unit_insert"(
		"xlast_update" INTEGER, 
		"xname" CHAR(30));

DROP FUNCTION "salesv001"."unit_update"(
	xid bigint,
		xlast_update INTEGER, 
		xname CHAR(30));

DROP "salesv001"."unit_select"(ref refcursor, pagesize integer, pagecount integer);

DROP FUNCTION "salesv001"."category_insert"(
		"xlast_update" INTEGER, 
		"xfk_category" INTEGER, 
		"xname" CHAR(30));

DROP FUNCTION "salesv001"."category_update"(
	xid bigint,
		xlast_update INTEGER, 
		xfk_category INTEGER, 
		xname CHAR(30));

DROP "salesv001"."category_select"(ref refcursor, pagesize integer, pagecount integer);

DROP FUNCTION "salesv001"."brand_insert"(
		"xlast_update" INTEGER, 
		"xcompany_name" CHAR(30), 
		"xfantasy_name" CHAR(30));

DROP FUNCTION "salesv001"."brand_update"(
	xid bigint,
		xlast_update INTEGER, 
		xcompany_name CHAR(30), 
		xfantasy_name CHAR(30));

DROP "salesv001"."brand_select"(ref refcursor, pagesize integer, pagecount integer);

DROP FUNCTION "salesv001"."gender_insert"(
		"xlast_update" INTEGER, 
		"xname" CHAR(30));

DROP FUNCTION "salesv001"."gender_update"(
	xid bigint,
		xlast_update INTEGER, 
		xname CHAR(30));

DROP "salesv001"."gender_select"(ref refcursor, pagesize integer, pagecount integer);

DROP FUNCTION "salesv001"."product_insert"(
		"xlast_update" INTEGER, 
		"xfk_system" INTEGER, 
		"xbarcode" CHAR, 
		"xdescription" VARCHAR(64), 
		"xamount" INTEGER, 
		"xfk_gender" INTEGER, 
		"xpurchase_price" DECIMAL(10,2), 
		"xsale_price" DECIMAL(10,2), 
		"xfk_category" INTEGER, 
		"xsize" CHAR(30), 
		"xfk_unit" INTEGER, 
		"xfk_currency" INTEGER, 
		"xexpiration_date" INTEGER, 
		"xfk_brand" INTEGER);

DROP FUNCTION "salesv001"."product_update"(
	xid bigint,
		xlast_update INTEGER, 
		xfk_system INTEGER, 
		xbarcode CHAR, 
		xdescription VARCHAR(64), 
		xamount INTEGER, 
		xfk_gender INTEGER, 
		xpurchase_price DECIMAL(10,2), 
		xsale_price DECIMAL(10,2), 
		xfk_category INTEGER, 
		xsize CHAR(30), 
		xfk_unit INTEGER, 
		xfk_currency INTEGER, 
		xexpiration_date INTEGER, 
		xfk_brand INTEGER);

DROP "salesv001"."product_select"(ref refcursor, pagesize integer, pagecount integer);

DROP FUNCTION "salesv001"."distributor_contact_insert"(
		"xlast_update" INTEGER, 
		"xname" CHAR(30), 
		"xemail1" CHAR(30), 
		"xemail2" CHAR(30), 
		"xphone_number1" CHAR(30), 
		"xphone_number2" CHAR(30), 
		"xphone_number3" CHAR(30), 
		"xphone_number4" CHAR(30), 
		"xfk_brand" INTEGER);

DROP FUNCTION "salesv001"."distributor_contact_update"(
	xid bigint,
		xlast_update INTEGER, 
		xname CHAR(30), 
		xemail1 CHAR(30), 
		xemail2 CHAR(30), 
		xphone_number1 CHAR(30), 
		xphone_number2 CHAR(30), 
		xphone_number3 CHAR(30), 
		xphone_number4 CHAR(30), 
		xfk_brand INTEGER);

DROP "salesv001"."distributor_contact_select"(ref refcursor, pagesize integer, pagecount integer);

DROP FUNCTION "salesv001"."Issue_insert"(
		"xlast_update" INTEGER, 
		"xfk_shared_client" INTEGER, 
		"xfk_system" INTEGER, 
		"xdescription" VARCHAR(64), 
		"xactive" BOOLEAN, 
		"xisAnswer" BOOLEAN, 
		"xfk_issue" INTEGER);

DROP FUNCTION "salesv001"."Issue_update"(
	xid bigint,
		xlast_update INTEGER, 
		xfk_shared_client INTEGER, 
		xfk_system INTEGER, 
		xdescription VARCHAR(64), 
		xactive BOOLEAN, 
		xisAnswer BOOLEAN, 
		xfk_issue INTEGER);

DROP "salesv001"."Issue_select"(ref refcursor, pagesize integer, pagecount integer);

DROP FUNCTION "salesv001"."shared_client_insert"(
		"xlast_update" INTEGER, 
		"xname" CHAR(30), 
		"xbirth_date" INTEGER, 
		"xbirth_city" CHAR(30), 
		"xbirth_state" CHAR(30), 
		"xmothers_name" CHAR(30), 
		"xfathers_name" CHAR(30), 
		"xprofession" CHAR(30), 
		"xzip_code" CHAR(15), 
		"xaddress" CHAR(30), 
		"xneighborhood" CHAR(30), 
		"xcity" CHAR(30), 
		"xstate" CHAR(2), 
		"xcomplement" VARCHAR(64), 
		"xfk_country" INTEGER);

DROP FUNCTION "salesv001"."shared_client_update"(
	xid bigint,
		xlast_update INTEGER, 
		xname CHAR(30), 
		xbirth_date INTEGER, 
		xbirth_city CHAR(30), 
		xbirth_state CHAR(30), 
		xmothers_name CHAR(30), 
		xfathers_name CHAR(30), 
		xprofession CHAR(30), 
		xzip_code CHAR(15), 
		xaddress CHAR(30), 
		xneighborhood CHAR(30), 
		xcity CHAR(30), 
		xstate CHAR(2), 
		xcomplement VARCHAR(64), 
		xfk_country INTEGER);

DROP "salesv001"."shared_client_select"(ref refcursor, pagesize integer, pagecount integer);

DROP FUNCTION "salesv001"."product_on_sale_insert"(
		"xlast_update" INTEGER, 
		"xfk_sale" INTEGER, 
		"xfk_product" INTEGER);

DROP FUNCTION "salesv001"."product_on_sale_update"(
	xid bigint,
		xlast_update INTEGER, 
		xfk_sale INTEGER, 
		xfk_product INTEGER);

DROP "salesv001"."product_on_sale_select"(ref refcursor, pagesize integer, pagecount integer);

DROP FUNCTION "salesv001"."sale_type_insert"(
		"xlast_update" INTEGER, 
		"xname" CHAR(30));

DROP FUNCTION "salesv001"."sale_type_update"(
	xid bigint,
		xlast_update INTEGER, 
		xname CHAR(30));

DROP "salesv001"."sale_type_select"(ref refcursor, pagesize integer, pagecount integer);

DROP FUNCTION "salesv001"."sale_insert"(
		"xlast_update" INTEGER, 
		"xfk_sale_type" INTEGER, 
		"xfk_system" INTEGER, 
		"xtotal_value" DECIMAL(10,2), 
		"xfk_user" INTEGER, 
		"xfk_client_from_system" INTEGER, 
		"xfk_currency" INTEGER);

DROP FUNCTION "salesv001"."sale_update"(
	xid bigint,
		xlast_update INTEGER, 
		xfk_sale_type INTEGER, 
		xfk_system INTEGER, 
		xtotal_value DECIMAL(10,2), 
		xfk_user INTEGER, 
		xfk_client_from_system INTEGER, 
		xfk_currency INTEGER);

DROP "salesv001"."sale_select"(ref refcursor, pagesize integer, pagecount integer);

DROP FUNCTION "salesv001"."db_log_insert"(
		"xlast_update" INTEGER, 
		"xaction_name" CHAR(30), 
		"xparameter" VARCHAR(64), 
		"xfk_user" INTEGER);

DROP FUNCTION "salesv001"."db_log_update"(
	xid bigint,
		xlast_update INTEGER, 
		xaction_name CHAR(30), 
		xparameter VARCHAR(64), 
		xfk_user INTEGER);

DROP "salesv001"."db_log_select"(ref refcursor, pagesize integer, pagecount integer);

DROP FUNCTION "salesv001"."currency_insert"(
		"xlast_update" INTEGER, 
		"xabbreviature" CHAR(30), 
		"xdescription" CHAR(30));

DROP FUNCTION "salesv001"."currency_update"(
	xid bigint,
		xlast_update INTEGER, 
		xabbreviature CHAR(30), 
		xdescription CHAR(30));

DROP "salesv001"."currency_select"(ref refcursor, pagesize integer, pagecount integer);

DROP FUNCTION "salesv001"."token_type_insert"(
		"xlast_update" INTEGER, 
		"xname" CHAR(30));

DROP FUNCTION "salesv001"."token_type_update"(
	xid bigint,
		xlast_update INTEGER, 
		xname CHAR(30));

DROP "salesv001"."token_type_select"(ref refcursor, pagesize integer, pagecount integer);

DROP FUNCTION "salesv001"."user_insert"(
		"xlast_update" INTEGER, 
		"xfk_system" INTEGER, 
		"xfk_role" INTEGER, 
		"xusername" CHAR(30), 
		"xpassword" CHAR(30), 
		"xname" CHAR(30), 
		"xemail" CHAR(30), 
		"xlast_use_time" INTEGER, 
		"xlast_error_time" INTEGER, 
		"xerror_count" INTEGER, 
		"xactive" BOOLEAN);

DROP FUNCTION "salesv001"."user_update"(
	xid bigint,
		xlast_update INTEGER, 
		xfk_system INTEGER, 
		xfk_role INTEGER, 
		xusername CHAR(30), 
		xpassword CHAR(30), 
		xname CHAR(30), 
		xemail CHAR(30), 
		xlast_use_time INTEGER, 
		xlast_error_time INTEGER, 
		xerror_count INTEGER, 
		xactive BOOLEAN);

DROP "salesv001"."user_select"(ref refcursor, pagesize integer, pagecount integer);

DROP FUNCTION "salesv001"."token_insert"(
		"xlast_update" INTEGER, 
		"xfk_user" INTEGER, 
		"xfk_system" INTEGER, 
		"xfk_token_type" INTEGER, 
		"xguid" CHAR(36), 
		"xlast_use_time" INTEGER, 
		"xexpiration_time" INTEGER);

DROP FUNCTION "salesv001"."token_update"(
	xid bigint,
		xlast_update INTEGER, 
		xfk_user INTEGER, 
		xfk_system INTEGER, 
		xfk_token_type INTEGER, 
		xguid CHAR(36), 
		xlast_use_time INTEGER, 
		xexpiration_time INTEGER);

DROP "salesv001"."token_select"(ref refcursor, pagesize integer, pagecount integer);

DROP FUNCTION "salesv001"."system_insert"(
		"xlast_update" INTEGER, 
		"xname" CHAR(30), 
		"xenabled" BOOLEAN, 
		"xfk_currency" INTEGER);

DROP FUNCTION "salesv001"."system_update"(
	xid bigint,
		xlast_update INTEGER, 
		xname CHAR(30), 
		xenabled BOOLEAN, 
		xfk_currency INTEGER);

DROP "salesv001"."system_select"(ref refcursor, pagesize integer, pagecount integer);

DROP FUNCTION "salesv001"."role_insert"(
		"xlast_update" INTEGER, 
		"xname" CHAR(30));

DROP FUNCTION "salesv001"."role_update"(
	xid bigint,
		xlast_update INTEGER, 
		xname CHAR(30));

DROP "salesv001"."role_select"(ref refcursor, pagesize integer, pagecount integer);

DROP FUNCTION "salesv001"."discount_insert"(
		"xlast_update" INTEGER, 
		"xvalue" DECIMAL(10,2), 
		"xpercentage" DECIMAL(10,2), 
		"xfk_product" INTEGER, 
		"xfk_category" INTEGER, 
		"xfk_brand" INTEGER, 
		"xfk_client_from_system" INTEGER, 
		"xfk_gender" INTEGER);

DROP FUNCTION "salesv001"."discount_update"(
	xid bigint,
		xlast_update INTEGER, 
		xvalue DECIMAL(10,2), 
		xpercentage DECIMAL(10,2), 
		xfk_product INTEGER, 
		xfk_category INTEGER, 
		xfk_brand INTEGER, 
		xfk_client_from_system INTEGER, 
		xfk_gender INTEGER);

DROP "salesv001"."discount_select"(ref refcursor, pagesize integer, pagecount integer);

DROP FUNCTION "salesv001"."country_insert"(
		"xlast_update" INTEGER, 
		"xname" CHAR(30));

DROP FUNCTION "salesv001"."country_update"(
	xid bigint,
		xlast_update INTEGER, 
		xname CHAR(30));

DROP "salesv001"."country_select"(ref refcursor, pagesize integer, pagecount integer);

DROP FUNCTION "salesv001"."brazilian_insert"(
		"xlast_update" INTEGER, 
		"xcpf" CHAR(30), 
		"xrg" CHAR(30), 
		"xfk_basic_client" INTEGER);

DROP FUNCTION "salesv001"."brazilian_update"(
	xid bigint,
		xlast_update INTEGER, 
		xcpf CHAR(30), 
		xrg CHAR(30), 
		xfk_basic_client INTEGER);

DROP "salesv001"."brazilian_select"(ref refcursor, pagesize integer, pagecount integer);

DROP FUNCTION "salesv001"."client_from_system_insert"(
		"xlast_update" INTEGER, 
		"xfk_system" INTEGER, 
		"xfk_basic_client" INTEGER, 
		"xfk_shared_client" INTEGER, 
		"xfk_user" INTEGER);

DROP FUNCTION "salesv001"."client_from_system_update"(
	xid bigint,
		xlast_update INTEGER, 
		xfk_system INTEGER, 
		xfk_basic_client INTEGER, 
		xfk_shared_client INTEGER, 
		xfk_user INTEGER);

DROP "salesv001"."client_from_system_select"(ref refcursor, pagesize integer, pagecount integer);

DROP FUNCTION "salesv001"."basic_client_insert"(
		"xlast_update" INTEGER, 
		"xname" CHAR(30), 
		"xbirth_date" INTEGER, 
		"xbirth_city" CHAR(30), 
		"xbirth_state" CHAR(30), 
		"xmothers_name" CHAR(30), 
		"xfathers_name" CHAR(30), 
		"xprofession" CHAR(30), 
		"xzip_code" CHAR(15), 
		"xaddress" CHAR(30), 
		"xneighborhood" CHAR(30), 
		"xcity" CHAR(30), 
		"xstate" CHAR(2), 
		"xcomplement" VARCHAR(64), 
		"xfk_country" INTEGER);

DROP FUNCTION "salesv001"."basic_client_update"(
	xid bigint,
		xlast_update INTEGER, 
		xname CHAR(30), 
		xbirth_date INTEGER, 
		xbirth_city CHAR(30), 
		xbirth_state CHAR(30), 
		xmothers_name CHAR(30), 
		xfathers_name CHAR(30), 
		xprofession CHAR(30), 
		xzip_code CHAR(15), 
		xaddress CHAR(30), 
		xneighborhood CHAR(30), 
		xcity CHAR(30), 
		xstate CHAR(2), 
		xcomplement VARCHAR(64), 
		xfk_country INTEGER);

DROP "salesv001"."basic_client_select"(ref refcursor, pagesize integer, pagecount integer);

DROP FUNCTION "salesv001"."bank_insert"(
		"xlast_update" INTEGER, 
		"xcode" CHAR(30), 
		"xname" VARCHAR(64));

DROP FUNCTION "salesv001"."bank_update"(
	xid bigint,
		xlast_update INTEGER, 
		xcode CHAR(30), 
		xname VARCHAR(64));

DROP "salesv001"."bank_select"(ref refcursor, pagesize integer, pagecount integer);

DROP FUNCTION "salesv001"."invoice_insert"(
		"xlast_update" INTEGER, 
		"xfk_system" INTEGER, 
		"xfk_sale" INTEGER, 
		"xfk_client_from_system" INTEGER, 
		"xfk_installment_type" INTEGER, 
		"xfk_interest_rate_type" INTEGER, 
		"xfk_bank" INTEGER, 
		"xfk_currency" INTEGER);

DROP FUNCTION "salesv001"."invoice_update"(
	xid bigint,
		xlast_update INTEGER, 
		xfk_system INTEGER, 
		xfk_sale INTEGER, 
		xfk_client_from_system INTEGER, 
		xfk_installment_type INTEGER, 
		xfk_interest_rate_type INTEGER, 
		xfk_bank INTEGER, 
		xfk_currency INTEGER);

DROP "salesv001"."invoice_select"(ref refcursor, pagesize integer, pagecount integer);

DROP FUNCTION "salesv001"."installment_type_insert"(
		"xlast_update" INTEGER, 
		"xname" CHAR(30));

DROP FUNCTION "salesv001"."installment_type_update"(
	xid bigint,
		xlast_update INTEGER, 
		xname CHAR(30));

DROP "salesv001"."installment_type_select"(ref refcursor, pagesize integer, pagecount integer);

DROP FUNCTION "salesv001"."boleto_sicoob_insert"(
		"xlast_update" INTEGER, 
		"xcpf" CHAR(30), 
		"xnumero" CHAR(30), 
		"xdata" INTEGER, 
		"xvencimento" INTEGER, 
		"xvalor" DECIMAL(10,2), 
		"xnosso_numero" CHAR(30), 
		"xquantidade" INTEGER, 
		"xparcela" INTEGER, 
		"xfoi_pago" BOOLEAN, 
		"xdata_de_pagamento" INTEGER, 
		"xvalor_recebido" DECIMAL(10,2), 
		"xfk_invoice" INTEGER);

DROP FUNCTION "salesv001"."boleto_sicoob_update"(
	xid bigint,
		xlast_update INTEGER, 
		xcpf CHAR(30), 
		xnumero CHAR(30), 
		xdata INTEGER, 
		xvencimento INTEGER, 
		xvalor DECIMAL(10,2), 
		xnosso_numero CHAR(30), 
		xquantidade INTEGER, 
		xparcela INTEGER, 
		xfoi_pago BOOLEAN, 
		xdata_de_pagamento INTEGER, 
		xvalor_recebido DECIMAL(10,2), 
		xfk_invoice INTEGER);

DROP "salesv001"."boleto_sicoob_select"(ref refcursor, pagesize integer, pagecount integer);

DROP FUNCTION "salesv001"."interest_rate_type_insert"(
		"xlast_update" INTEGER, 
		"xname" CHAR(30));

DROP FUNCTION "salesv001"."interest_rate_type_update"(
	xid bigint,
		xlast_update INTEGER, 
		xname CHAR(30));

DROP "salesv001"."interest_rate_type_select"(ref refcursor, pagesize integer, pagecount integer);

