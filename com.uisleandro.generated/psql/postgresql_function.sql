CREATE OR REPLACE FUNCTION "salesv001"."cash_register_insert"(
		"xlast_update" INTEGER, 
		"xfk_user" INTEGER, 
		"xopening_value" DECIMAL(10,2), 
		"xreceived_value" DECIMAL(10,2), 
		"xclosing_value" DECIMAL(10,2), 
		"xfk_currency" INTEGER) RETURNS void AS $$
BEGIN
	INSERT INTO salesv001.cash_register (
	"last_update", 
	"fk_user", 
	"opening_value", 
	"received_value", 
	"closing_value", 
	"fk_currency") VALUES (
	"xlast_update", 
	"xfk_user", 
	"xopening_value", 
	"xreceived_value", 
	"xclosing_value", 
	"xfk_currency");
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION "salesv001"."cash_register_update"(
	xid bigint,
		xlast_update INTEGER, 
		xfk_user INTEGER, 
		xopening_value DECIMAL(10,2), 
		xreceived_value DECIMAL(10,2), 
		xclosing_value DECIMAL(10,2), 
		xfk_currency INTEGER) RETURNS void AS $$
BEGIN
	UPDATE salesv001.cash_register SET 	"last_update" = xINTEGER, 
	"fk_user" = xINTEGER, 
	"opening_value" = xDECIMAL(10,2), 
	"received_value" = xDECIMAL(10,2), 
	"closing_value" = xDECIMAL(10,2), 
	"fk_currency" = xINTEGER	WHERE id = xid;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION "salesv001"."cash_register_select"(ref refcursor, pagesize integer, pagecount integer) RETURNS refcursor AS $$
BEGIN
  OPEN ref FOR SELECT 
	"last_update", 
	"fk_user", 
	"opening_value", 
	"received_value", 
	"closing_value", 
	"fk_currency"
	FROM salesv001.cash_register LIMIT pagesize OFFSET pagesize * pagecount;
	RETURN ref;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION "salesv001"."cash_launch_insert"(
		"xlast_update" INTEGER, 
		"xfk_cash_register" INTEGER, 
		"xjustification" CHAR(30), 
		"xamount_spent" DECIMAL(10,2), 
		"xfk_currency" INTEGER) RETURNS void AS $$
BEGIN
	INSERT INTO salesv001.cash_launch (
	"last_update", 
	"fk_cash_register", 
	"justification", 
	"amount_spent", 
	"fk_currency") VALUES (
	"xlast_update", 
	"xfk_cash_register", 
	"xjustification", 
	"xamount_spent", 
	"xfk_currency");
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION "salesv001"."cash_launch_update"(
	xid bigint,
		xlast_update INTEGER, 
		xfk_cash_register INTEGER, 
		xjustification CHAR(30), 
		xamount_spent DECIMAL(10,2), 
		xfk_currency INTEGER) RETURNS void AS $$
BEGIN
	UPDATE salesv001.cash_launch SET 	"last_update" = xINTEGER, 
	"fk_cash_register" = xINTEGER, 
	"justification" = xCHAR(30), 
	"amount_spent" = xDECIMAL(10,2), 
	"fk_currency" = xINTEGER	WHERE id = xid;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION "salesv001"."cash_launch_select"(ref refcursor, pagesize integer, pagecount integer) RETURNS refcursor AS $$
BEGIN
  OPEN ref FOR SELECT 
	"last_update", 
	"fk_cash_register", 
	"justification", 
	"amount_spent", 
	"fk_currency"
	FROM salesv001.cash_launch LIMIT pagesize OFFSET pagesize * pagecount;
	RETURN ref;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION "salesv001"."stock_review_insert"(
		"xlast_update" INTEGER, 
		"xfk_product" INTEGER, 
		"xactual_amount" INTEGER, 
		"xsold_items" INTEGER, 
		"xprevious_amount" INTEGER, 
		"xmissing_amount" INTEGER) RETURNS void AS $$
BEGIN
	INSERT INTO salesv001.stock_review (
	"last_update", 
	"fk_product", 
	"actual_amount", 
	"sold_items", 
	"previous_amount", 
	"missing_amount") VALUES (
	"xlast_update", 
	"xfk_product", 
	"xactual_amount", 
	"xsold_items", 
	"xprevious_amount", 
	"xmissing_amount");
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION "salesv001"."stock_review_update"(
	xid bigint,
		xlast_update INTEGER, 
		xfk_product INTEGER, 
		xactual_amount INTEGER, 
		xsold_items INTEGER, 
		xprevious_amount INTEGER, 
		xmissing_amount INTEGER) RETURNS void AS $$
BEGIN
	UPDATE salesv001.stock_review SET 	"last_update" = xINTEGER, 
	"fk_product" = xINTEGER, 
	"actual_amount" = xINTEGER, 
	"sold_items" = xINTEGER, 
	"previous_amount" = xINTEGER, 
	"missing_amount" = xINTEGER	WHERE id = xid;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION "salesv001"."stock_review_select"(ref refcursor, pagesize integer, pagecount integer) RETURNS refcursor AS $$
BEGIN
  OPEN ref FOR SELECT 
	"last_update", 
	"fk_product", 
	"actual_amount", 
	"sold_items", 
	"previous_amount", 
	"missing_amount"
	FROM salesv001.stock_review LIMIT pagesize OFFSET pagesize * pagecount;
	RETURN ref;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION "salesv001"."unit_insert"(
		"xlast_update" INTEGER, 
		"xname" CHAR(30)) RETURNS void AS $$
BEGIN
	INSERT INTO salesv001.unit (
	"last_update", 
	"name") VALUES (
	"xlast_update", 
	"xname");
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION "salesv001"."unit_update"(
	xid bigint,
		xlast_update INTEGER, 
		xname CHAR(30)) RETURNS void AS $$
BEGIN
	UPDATE salesv001.unit SET 	"last_update" = xINTEGER, 
	"name" = xCHAR(30)	WHERE id = xid;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION "salesv001"."unit_select"(ref refcursor, pagesize integer, pagecount integer) RETURNS refcursor AS $$
BEGIN
  OPEN ref FOR SELECT 
	"last_update", 
	"name"
	FROM salesv001.unit LIMIT pagesize OFFSET pagesize * pagecount;
	RETURN ref;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION "salesv001"."category_insert"(
		"xlast_update" INTEGER, 
		"xfk_category" INTEGER, 
		"xname" CHAR(30)) RETURNS void AS $$
BEGIN
	INSERT INTO salesv001.category (
	"last_update", 
	"fk_category", 
	"name") VALUES (
	"xlast_update", 
	"xfk_category", 
	"xname");
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION "salesv001"."category_update"(
	xid bigint,
		xlast_update INTEGER, 
		xfk_category INTEGER, 
		xname CHAR(30)) RETURNS void AS $$
BEGIN
	UPDATE salesv001.category SET 	"last_update" = xINTEGER, 
	"fk_category" = xINTEGER, 
	"name" = xCHAR(30)	WHERE id = xid;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION "salesv001"."category_select"(ref refcursor, pagesize integer, pagecount integer) RETURNS refcursor AS $$
BEGIN
  OPEN ref FOR SELECT 
	"last_update", 
	"fk_category", 
	"name"
	FROM salesv001.category LIMIT pagesize OFFSET pagesize * pagecount;
	RETURN ref;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION "salesv001"."brand_insert"(
		"xlast_update" INTEGER, 
		"xcompany_name" CHAR(30), 
		"xfantasy_name" CHAR(30)) RETURNS void AS $$
BEGIN
	INSERT INTO salesv001.brand (
	"last_update", 
	"company_name", 
	"fantasy_name") VALUES (
	"xlast_update", 
	"xcompany_name", 
	"xfantasy_name");
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION "salesv001"."brand_update"(
	xid bigint,
		xlast_update INTEGER, 
		xcompany_name CHAR(30), 
		xfantasy_name CHAR(30)) RETURNS void AS $$
BEGIN
	UPDATE salesv001.brand SET 	"last_update" = xINTEGER, 
	"company_name" = xCHAR(30), 
	"fantasy_name" = xCHAR(30)	WHERE id = xid;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION "salesv001"."brand_select"(ref refcursor, pagesize integer, pagecount integer) RETURNS refcursor AS $$
BEGIN
  OPEN ref FOR SELECT 
	"last_update", 
	"company_name", 
	"fantasy_name"
	FROM salesv001.brand LIMIT pagesize OFFSET pagesize * pagecount;
	RETURN ref;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION "salesv001"."gender_insert"(
		"xlast_update" INTEGER, 
		"xname" CHAR(30)) RETURNS void AS $$
BEGIN
	INSERT INTO salesv001.gender (
	"last_update", 
	"name") VALUES (
	"xlast_update", 
	"xname");
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION "salesv001"."gender_update"(
	xid bigint,
		xlast_update INTEGER, 
		xname CHAR(30)) RETURNS void AS $$
BEGIN
	UPDATE salesv001.gender SET 	"last_update" = xINTEGER, 
	"name" = xCHAR(30)	WHERE id = xid;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION "salesv001"."gender_select"(ref refcursor, pagesize integer, pagecount integer) RETURNS refcursor AS $$
BEGIN
  OPEN ref FOR SELECT 
	"last_update", 
	"name"
	FROM salesv001.gender LIMIT pagesize OFFSET pagesize * pagecount;
	RETURN ref;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION "salesv001"."product_insert"(
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
		"xfk_brand" INTEGER) RETURNS void AS $$
BEGIN
	INSERT INTO salesv001.product (
	"last_update", 
	"fk_system", 
	"barcode", 
	"description", 
	"amount", 
	"fk_gender", 
	"purchase_price", 
	"sale_price", 
	"fk_category", 
	"size", 
	"fk_unit", 
	"fk_currency", 
	"expiration_date", 
	"fk_brand") VALUES (
	"xlast_update", 
	"xfk_system", 
	"xbarcode", 
	"xdescription", 
	"xamount", 
	"xfk_gender", 
	"xpurchase_price", 
	"xsale_price", 
	"xfk_category", 
	"xsize", 
	"xfk_unit", 
	"xfk_currency", 
	"xexpiration_date", 
	"xfk_brand");
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION "salesv001"."product_update"(
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
		xfk_brand INTEGER) RETURNS void AS $$
BEGIN
	UPDATE salesv001.product SET 	"last_update" = xINTEGER, 
	"fk_system" = xINTEGER, 
	"barcode" = xCHAR, 
	"description" = xVARCHAR(64), 
	"amount" = xINTEGER, 
	"fk_gender" = xINTEGER, 
	"purchase_price" = xDECIMAL(10,2), 
	"sale_price" = xDECIMAL(10,2), 
	"fk_category" = xINTEGER, 
	"size" = xCHAR(30), 
	"fk_unit" = xINTEGER, 
	"fk_currency" = xINTEGER, 
	"expiration_date" = xINTEGER, 
	"fk_brand" = xINTEGER	WHERE id = xid;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION "salesv001"."product_select"(ref refcursor, pagesize integer, pagecount integer) RETURNS refcursor AS $$
BEGIN
  OPEN ref FOR SELECT 
	"last_update", 
	"fk_system", 
	"barcode", 
	"description", 
	"amount", 
	"fk_gender", 
	"purchase_price", 
	"sale_price", 
	"fk_category", 
	"size", 
	"fk_unit", 
	"fk_currency", 
	"expiration_date", 
	"fk_brand"
	FROM salesv001.product LIMIT pagesize OFFSET pagesize * pagecount;
	RETURN ref;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION "salesv001"."distributor_contact_insert"(
		"xlast_update" INTEGER, 
		"xname" CHAR(30), 
		"xemail1" CHAR(30), 
		"xemail2" CHAR(30), 
		"xphone_number1" CHAR(30), 
		"xphone_number2" CHAR(30), 
		"xphone_number3" CHAR(30), 
		"xphone_number4" CHAR(30), 
		"xfk_brand" INTEGER) RETURNS void AS $$
BEGIN
	INSERT INTO salesv001.distributor_contact (
	"last_update", 
	"name", 
	"email1", 
	"email2", 
	"phone_number1", 
	"phone_number2", 
	"phone_number3", 
	"phone_number4", 
	"fk_brand") VALUES (
	"xlast_update", 
	"xname", 
	"xemail1", 
	"xemail2", 
	"xphone_number1", 
	"xphone_number2", 
	"xphone_number3", 
	"xphone_number4", 
	"xfk_brand");
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION "salesv001"."distributor_contact_update"(
	xid bigint,
		xlast_update INTEGER, 
		xname CHAR(30), 
		xemail1 CHAR(30), 
		xemail2 CHAR(30), 
		xphone_number1 CHAR(30), 
		xphone_number2 CHAR(30), 
		xphone_number3 CHAR(30), 
		xphone_number4 CHAR(30), 
		xfk_brand INTEGER) RETURNS void AS $$
BEGIN
	UPDATE salesv001.distributor_contact SET 	"last_update" = xINTEGER, 
	"name" = xCHAR(30), 
	"email1" = xCHAR(30), 
	"email2" = xCHAR(30), 
	"phone_number1" = xCHAR(30), 
	"phone_number2" = xCHAR(30), 
	"phone_number3" = xCHAR(30), 
	"phone_number4" = xCHAR(30), 
	"fk_brand" = xINTEGER	WHERE id = xid;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION "salesv001"."distributor_contact_select"(ref refcursor, pagesize integer, pagecount integer) RETURNS refcursor AS $$
BEGIN
  OPEN ref FOR SELECT 
	"last_update", 
	"name", 
	"email1", 
	"email2", 
	"phone_number1", 
	"phone_number2", 
	"phone_number3", 
	"phone_number4", 
	"fk_brand"
	FROM salesv001.distributor_contact LIMIT pagesize OFFSET pagesize * pagecount;
	RETURN ref;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION "salesv001"."Issue_insert"(
		"xlast_update" INTEGER, 
		"xfk_shared_client" INTEGER, 
		"xfk_system" INTEGER, 
		"xdescription" VARCHAR(64), 
		"xactive" BOOLEAN, 
		"xisAnswer" BOOLEAN, 
		"xfk_issue" INTEGER) RETURNS void AS $$
BEGIN
	INSERT INTO salesv001.Issue (
	"last_update", 
	"fk_shared_client", 
	"fk_system", 
	"description", 
	"active", 
	"isAnswer", 
	"fk_issue") VALUES (
	"xlast_update", 
	"xfk_shared_client", 
	"xfk_system", 
	"xdescription", 
	"xactive", 
	"xisAnswer", 
	"xfk_issue");
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION "salesv001"."Issue_update"(
	xid bigint,
		xlast_update INTEGER, 
		xfk_shared_client INTEGER, 
		xfk_system INTEGER, 
		xdescription VARCHAR(64), 
		xactive BOOLEAN, 
		xisAnswer BOOLEAN, 
		xfk_issue INTEGER) RETURNS void AS $$
BEGIN
	UPDATE salesv001.Issue SET 	"last_update" = xINTEGER, 
	"fk_shared_client" = xINTEGER, 
	"fk_system" = xINTEGER, 
	"description" = xVARCHAR(64), 
	"active" = xBOOLEAN, 
	"isAnswer" = xBOOLEAN, 
	"fk_issue" = xINTEGER	WHERE id = xid;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION "salesv001"."Issue_select"(ref refcursor, pagesize integer, pagecount integer) RETURNS refcursor AS $$
BEGIN
  OPEN ref FOR SELECT 
	"last_update", 
	"fk_shared_client", 
	"fk_system", 
	"description", 
	"active", 
	"isAnswer", 
	"fk_issue"
	FROM salesv001.Issue LIMIT pagesize OFFSET pagesize * pagecount;
	RETURN ref;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION "salesv001"."shared_client_insert"(
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
		"xfk_country" INTEGER) RETURNS void AS $$
BEGIN
	INSERT INTO salesv001.shared_client (
	"last_update", 
	"name", 
	"birth_date", 
	"birth_city", 
	"birth_state", 
	"mothers_name", 
	"fathers_name", 
	"profession", 
	"zip_code", 
	"address", 
	"neighborhood", 
	"city", 
	"state", 
	"complement", 
	"fk_country") VALUES (
	"xlast_update", 
	"xname", 
	"xbirth_date", 
	"xbirth_city", 
	"xbirth_state", 
	"xmothers_name", 
	"xfathers_name", 
	"xprofession", 
	"xzip_code", 
	"xaddress", 
	"xneighborhood", 
	"xcity", 
	"xstate", 
	"xcomplement", 
	"xfk_country");
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION "salesv001"."shared_client_update"(
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
		xfk_country INTEGER) RETURNS void AS $$
BEGIN
	UPDATE salesv001.shared_client SET 	"last_update" = xINTEGER, 
	"name" = xCHAR(30), 
	"birth_date" = xINTEGER, 
	"birth_city" = xCHAR(30), 
	"birth_state" = xCHAR(30), 
	"mothers_name" = xCHAR(30), 
	"fathers_name" = xCHAR(30), 
	"profession" = xCHAR(30), 
	"zip_code" = xCHAR(15), 
	"address" = xCHAR(30), 
	"neighborhood" = xCHAR(30), 
	"city" = xCHAR(30), 
	"state" = xCHAR(2), 
	"complement" = xVARCHAR(64), 
	"fk_country" = xINTEGER	WHERE id = xid;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION "salesv001"."shared_client_select"(ref refcursor, pagesize integer, pagecount integer) RETURNS refcursor AS $$
BEGIN
  OPEN ref FOR SELECT 
	"last_update", 
	"name", 
	"birth_date", 
	"birth_city", 
	"birth_state", 
	"mothers_name", 
	"fathers_name", 
	"profession", 
	"zip_code", 
	"address", 
	"neighborhood", 
	"city", 
	"state", 
	"complement", 
	"fk_country"
	FROM salesv001.shared_client LIMIT pagesize OFFSET pagesize * pagecount;
	RETURN ref;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION "salesv001"."product_on_sale_insert"(
		"xlast_update" INTEGER, 
		"xfk_sale" INTEGER, 
		"xfk_product" INTEGER) RETURNS void AS $$
BEGIN
	INSERT INTO salesv001.product_on_sale (
	"last_update", 
	"fk_sale", 
	"fk_product") VALUES (
	"xlast_update", 
	"xfk_sale", 
	"xfk_product");
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION "salesv001"."product_on_sale_update"(
	xid bigint,
		xlast_update INTEGER, 
		xfk_sale INTEGER, 
		xfk_product INTEGER) RETURNS void AS $$
BEGIN
	UPDATE salesv001.product_on_sale SET 	"last_update" = xINTEGER, 
	"fk_sale" = xINTEGER, 
	"fk_product" = xINTEGER	WHERE id = xid;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION "salesv001"."product_on_sale_select"(ref refcursor, pagesize integer, pagecount integer) RETURNS refcursor AS $$
BEGIN
  OPEN ref FOR SELECT 
	"last_update", 
	"fk_sale", 
	"fk_product"
	FROM salesv001.product_on_sale LIMIT pagesize OFFSET pagesize * pagecount;
	RETURN ref;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION "salesv001"."sale_type_insert"(
		"xlast_update" INTEGER, 
		"xname" CHAR(30)) RETURNS void AS $$
BEGIN
	INSERT INTO salesv001.sale_type (
	"last_update", 
	"name") VALUES (
	"xlast_update", 
	"xname");
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION "salesv001"."sale_type_update"(
	xid bigint,
		xlast_update INTEGER, 
		xname CHAR(30)) RETURNS void AS $$
BEGIN
	UPDATE salesv001.sale_type SET 	"last_update" = xINTEGER, 
	"name" = xCHAR(30)	WHERE id = xid;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION "salesv001"."sale_type_select"(ref refcursor, pagesize integer, pagecount integer) RETURNS refcursor AS $$
BEGIN
  OPEN ref FOR SELECT 
	"last_update", 
	"name"
	FROM salesv001.sale_type LIMIT pagesize OFFSET pagesize * pagecount;
	RETURN ref;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION "salesv001"."sale_insert"(
		"xlast_update" INTEGER, 
		"xfk_sale_type" INTEGER, 
		"xfk_system" INTEGER, 
		"xtotal_value" DECIMAL(10,2), 
		"xfk_user" INTEGER, 
		"xfk_client_from_system" INTEGER, 
		"xfk_currency" INTEGER) RETURNS void AS $$
BEGIN
	INSERT INTO salesv001.sale (
	"last_update", 
	"fk_sale_type", 
	"fk_system", 
	"total_value", 
	"fk_user", 
	"fk_client_from_system", 
	"fk_currency") VALUES (
	"xlast_update", 
	"xfk_sale_type", 
	"xfk_system", 
	"xtotal_value", 
	"xfk_user", 
	"xfk_client_from_system", 
	"xfk_currency");
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION "salesv001"."sale_update"(
	xid bigint,
		xlast_update INTEGER, 
		xfk_sale_type INTEGER, 
		xfk_system INTEGER, 
		xtotal_value DECIMAL(10,2), 
		xfk_user INTEGER, 
		xfk_client_from_system INTEGER, 
		xfk_currency INTEGER) RETURNS void AS $$
BEGIN
	UPDATE salesv001.sale SET 	"last_update" = xINTEGER, 
	"fk_sale_type" = xINTEGER, 
	"fk_system" = xINTEGER, 
	"total_value" = xDECIMAL(10,2), 
	"fk_user" = xINTEGER, 
	"fk_client_from_system" = xINTEGER, 
	"fk_currency" = xINTEGER	WHERE id = xid;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION "salesv001"."sale_select"(ref refcursor, pagesize integer, pagecount integer) RETURNS refcursor AS $$
BEGIN
  OPEN ref FOR SELECT 
	"last_update", 
	"fk_sale_type", 
	"fk_system", 
	"total_value", 
	"fk_user", 
	"fk_client_from_system", 
	"fk_currency"
	FROM salesv001.sale LIMIT pagesize OFFSET pagesize * pagecount;
	RETURN ref;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION "salesv001"."db_log_insert"(
		"xlast_update" INTEGER, 
		"xaction_name" CHAR(30), 
		"xparameter" VARCHAR(64), 
		"xfk_user" INTEGER) RETURNS void AS $$
BEGIN
	INSERT INTO salesv001.db_log (
	"last_update", 
	"action_name", 
	"parameter", 
	"fk_user") VALUES (
	"xlast_update", 
	"xaction_name", 
	"xparameter", 
	"xfk_user");
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION "salesv001"."db_log_update"(
	xid bigint,
		xlast_update INTEGER, 
		xaction_name CHAR(30), 
		xparameter VARCHAR(64), 
		xfk_user INTEGER) RETURNS void AS $$
BEGIN
	UPDATE salesv001.db_log SET 	"last_update" = xINTEGER, 
	"action_name" = xCHAR(30), 
	"parameter" = xVARCHAR(64), 
	"fk_user" = xINTEGER	WHERE id = xid;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION "salesv001"."db_log_select"(ref refcursor, pagesize integer, pagecount integer) RETURNS refcursor AS $$
BEGIN
  OPEN ref FOR SELECT 
	"last_update", 
	"action_name", 
	"parameter", 
	"fk_user"
	FROM salesv001.db_log LIMIT pagesize OFFSET pagesize * pagecount;
	RETURN ref;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION "salesv001"."currency_insert"(
		"xlast_update" INTEGER, 
		"xabbreviature" CHAR(30), 
		"xdescription" CHAR(30)) RETURNS void AS $$
BEGIN
	INSERT INTO salesv001.currency (
	"last_update", 
	"abbreviature", 
	"description") VALUES (
	"xlast_update", 
	"xabbreviature", 
	"xdescription");
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION "salesv001"."currency_update"(
	xid bigint,
		xlast_update INTEGER, 
		xabbreviature CHAR(30), 
		xdescription CHAR(30)) RETURNS void AS $$
BEGIN
	UPDATE salesv001.currency SET 	"last_update" = xINTEGER, 
	"abbreviature" = xCHAR(30), 
	"description" = xCHAR(30)	WHERE id = xid;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION "salesv001"."currency_select"(ref refcursor, pagesize integer, pagecount integer) RETURNS refcursor AS $$
BEGIN
  OPEN ref FOR SELECT 
	"last_update", 
	"abbreviature", 
	"description"
	FROM salesv001.currency LIMIT pagesize OFFSET pagesize * pagecount;
	RETURN ref;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION "salesv001"."token_type_insert"(
		"xlast_update" INTEGER, 
		"xname" CHAR(30)) RETURNS void AS $$
BEGIN
	INSERT INTO salesv001.token_type (
	"last_update", 
	"name") VALUES (
	"xlast_update", 
	"xname");
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION "salesv001"."token_type_update"(
	xid bigint,
		xlast_update INTEGER, 
		xname CHAR(30)) RETURNS void AS $$
BEGIN
	UPDATE salesv001.token_type SET 	"last_update" = xINTEGER, 
	"name" = xCHAR(30)	WHERE id = xid;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION "salesv001"."token_type_select"(ref refcursor, pagesize integer, pagecount integer) RETURNS refcursor AS $$
BEGIN
  OPEN ref FOR SELECT 
	"last_update", 
	"name"
	FROM salesv001.token_type LIMIT pagesize OFFSET pagesize * pagecount;
	RETURN ref;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION "salesv001"."user_insert"(
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
		"xactive" BOOLEAN) RETURNS void AS $$
BEGIN
	INSERT INTO salesv001.user (
	"last_update", 
	"fk_system", 
	"fk_role", 
	"username", 
	"password", 
	"name", 
	"email", 
	"last_use_time", 
	"last_error_time", 
	"error_count", 
	"active") VALUES (
	"xlast_update", 
	"xfk_system", 
	"xfk_role", 
	"xusername", 
	"xpassword", 
	"xname", 
	"xemail", 
	"xlast_use_time", 
	"xlast_error_time", 
	"xerror_count", 
	"xactive");
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION "salesv001"."user_update"(
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
		xactive BOOLEAN) RETURNS void AS $$
BEGIN
	UPDATE salesv001.user SET 	"last_update" = xINTEGER, 
	"fk_system" = xINTEGER, 
	"fk_role" = xINTEGER, 
	"username" = xCHAR(30), 
	"password" = xCHAR(30), 
	"name" = xCHAR(30), 
	"email" = xCHAR(30), 
	"last_use_time" = xINTEGER, 
	"last_error_time" = xINTEGER, 
	"error_count" = xINTEGER, 
	"active" = xBOOLEAN	WHERE id = xid;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION "salesv001"."user_select"(ref refcursor, pagesize integer, pagecount integer) RETURNS refcursor AS $$
BEGIN
  OPEN ref FOR SELECT 
	"last_update", 
	"fk_system", 
	"fk_role", 
	"username", 
	"password", 
	"name", 
	"email", 
	"last_use_time", 
	"last_error_time", 
	"error_count", 
	"active"
	FROM salesv001.user LIMIT pagesize OFFSET pagesize * pagecount;
	RETURN ref;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION "salesv001"."token_insert"(
		"xlast_update" INTEGER, 
		"xfk_user" INTEGER, 
		"xfk_system" INTEGER, 
		"xfk_token_type" INTEGER, 
		"xguid" CHAR(36), 
		"xlast_use_time" INTEGER, 
		"xexpiration_time" INTEGER) RETURNS void AS $$
BEGIN
	INSERT INTO salesv001.token (
	"last_update", 
	"fk_user", 
	"fk_system", 
	"fk_token_type", 
	"guid", 
	"last_use_time", 
	"expiration_time") VALUES (
	"xlast_update", 
	"xfk_user", 
	"xfk_system", 
	"xfk_token_type", 
	"xguid", 
	"xlast_use_time", 
	"xexpiration_time");
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION "salesv001"."token_update"(
	xid bigint,
		xlast_update INTEGER, 
		xfk_user INTEGER, 
		xfk_system INTEGER, 
		xfk_token_type INTEGER, 
		xguid CHAR(36), 
		xlast_use_time INTEGER, 
		xexpiration_time INTEGER) RETURNS void AS $$
BEGIN
	UPDATE salesv001.token SET 	"last_update" = xINTEGER, 
	"fk_user" = xINTEGER, 
	"fk_system" = xINTEGER, 
	"fk_token_type" = xINTEGER, 
	"guid" = xCHAR(36), 
	"last_use_time" = xINTEGER, 
	"expiration_time" = xINTEGER	WHERE id = xid;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION "salesv001"."token_select"(ref refcursor, pagesize integer, pagecount integer) RETURNS refcursor AS $$
BEGIN
  OPEN ref FOR SELECT 
	"last_update", 
	"fk_user", 
	"fk_system", 
	"fk_token_type", 
	"guid", 
	"last_use_time", 
	"expiration_time"
	FROM salesv001.token LIMIT pagesize OFFSET pagesize * pagecount;
	RETURN ref;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION "salesv001"."system_insert"(
		"xlast_update" INTEGER, 
		"xname" CHAR(30), 
		"xenabled" BOOLEAN, 
		"xfk_currency" INTEGER) RETURNS void AS $$
BEGIN
	INSERT INTO salesv001.system (
	"last_update", 
	"name", 
	"enabled", 
	"fk_currency") VALUES (
	"xlast_update", 
	"xname", 
	"xenabled", 
	"xfk_currency");
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION "salesv001"."system_update"(
	xid bigint,
		xlast_update INTEGER, 
		xname CHAR(30), 
		xenabled BOOLEAN, 
		xfk_currency INTEGER) RETURNS void AS $$
BEGIN
	UPDATE salesv001.system SET 	"last_update" = xINTEGER, 
	"name" = xCHAR(30), 
	"enabled" = xBOOLEAN, 
	"fk_currency" = xINTEGER	WHERE id = xid;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION "salesv001"."system_select"(ref refcursor, pagesize integer, pagecount integer) RETURNS refcursor AS $$
BEGIN
  OPEN ref FOR SELECT 
	"last_update", 
	"name", 
	"enabled", 
	"fk_currency"
	FROM salesv001.system LIMIT pagesize OFFSET pagesize * pagecount;
	RETURN ref;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION "salesv001"."role_insert"(
		"xlast_update" INTEGER, 
		"xname" CHAR(30)) RETURNS void AS $$
BEGIN
	INSERT INTO salesv001.role (
	"last_update", 
	"name") VALUES (
	"xlast_update", 
	"xname");
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION "salesv001"."role_update"(
	xid bigint,
		xlast_update INTEGER, 
		xname CHAR(30)) RETURNS void AS $$
BEGIN
	UPDATE salesv001.role SET 	"last_update" = xINTEGER, 
	"name" = xCHAR(30)	WHERE id = xid;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION "salesv001"."role_select"(ref refcursor, pagesize integer, pagecount integer) RETURNS refcursor AS $$
BEGIN
  OPEN ref FOR SELECT 
	"last_update", 
	"name"
	FROM salesv001.role LIMIT pagesize OFFSET pagesize * pagecount;
	RETURN ref;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION "salesv001"."discount_insert"(
		"xlast_update" INTEGER, 
		"xvalue" DECIMAL(10,2), 
		"xpercentage" DECIMAL(10,2), 
		"xfk_product" INTEGER, 
		"xfk_category" INTEGER, 
		"xfk_brand" INTEGER, 
		"xfk_client_from_system" INTEGER, 
		"xfk_gender" INTEGER) RETURNS void AS $$
BEGIN
	INSERT INTO salesv001.discount (
	"last_update", 
	"value", 
	"percentage", 
	"fk_product", 
	"fk_category", 
	"fk_brand", 
	"fk_client_from_system", 
	"fk_gender") VALUES (
	"xlast_update", 
	"xvalue", 
	"xpercentage", 
	"xfk_product", 
	"xfk_category", 
	"xfk_brand", 
	"xfk_client_from_system", 
	"xfk_gender");
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION "salesv001"."discount_update"(
	xid bigint,
		xlast_update INTEGER, 
		xvalue DECIMAL(10,2), 
		xpercentage DECIMAL(10,2), 
		xfk_product INTEGER, 
		xfk_category INTEGER, 
		xfk_brand INTEGER, 
		xfk_client_from_system INTEGER, 
		xfk_gender INTEGER) RETURNS void AS $$
BEGIN
	UPDATE salesv001.discount SET 	"last_update" = xINTEGER, 
	"value" = xDECIMAL(10,2), 
	"percentage" = xDECIMAL(10,2), 
	"fk_product" = xINTEGER, 
	"fk_category" = xINTEGER, 
	"fk_brand" = xINTEGER, 
	"fk_client_from_system" = xINTEGER, 
	"fk_gender" = xINTEGER	WHERE id = xid;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION "salesv001"."discount_select"(ref refcursor, pagesize integer, pagecount integer) RETURNS refcursor AS $$
BEGIN
  OPEN ref FOR SELECT 
	"last_update", 
	"value", 
	"percentage", 
	"fk_product", 
	"fk_category", 
	"fk_brand", 
	"fk_client_from_system", 
	"fk_gender"
	FROM salesv001.discount LIMIT pagesize OFFSET pagesize * pagecount;
	RETURN ref;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION "salesv001"."country_insert"(
		"xlast_update" INTEGER, 
		"xname" CHAR(30)) RETURNS void AS $$
BEGIN
	INSERT INTO salesv001.country (
	"last_update", 
	"name") VALUES (
	"xlast_update", 
	"xname");
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION "salesv001"."country_update"(
	xid bigint,
		xlast_update INTEGER, 
		xname CHAR(30)) RETURNS void AS $$
BEGIN
	UPDATE salesv001.country SET 	"last_update" = xINTEGER, 
	"name" = xCHAR(30)	WHERE id = xid;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION "salesv001"."country_select"(ref refcursor, pagesize integer, pagecount integer) RETURNS refcursor AS $$
BEGIN
  OPEN ref FOR SELECT 
	"last_update", 
	"name"
	FROM salesv001.country LIMIT pagesize OFFSET pagesize * pagecount;
	RETURN ref;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION "salesv001"."brazilian_insert"(
		"xlast_update" INTEGER, 
		"xcpf" CHAR(30), 
		"xrg" CHAR(30), 
		"xfk_basic_client" INTEGER) RETURNS void AS $$
BEGIN
	INSERT INTO salesv001.brazilian (
	"last_update", 
	"cpf", 
	"rg", 
	"fk_basic_client") VALUES (
	"xlast_update", 
	"xcpf", 
	"xrg", 
	"xfk_basic_client");
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION "salesv001"."brazilian_update"(
	xid bigint,
		xlast_update INTEGER, 
		xcpf CHAR(30), 
		xrg CHAR(30), 
		xfk_basic_client INTEGER) RETURNS void AS $$
BEGIN
	UPDATE salesv001.brazilian SET 	"last_update" = xINTEGER, 
	"cpf" = xCHAR(30), 
	"rg" = xCHAR(30), 
	"fk_basic_client" = xINTEGER	WHERE id = xid;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION "salesv001"."brazilian_select"(ref refcursor, pagesize integer, pagecount integer) RETURNS refcursor AS $$
BEGIN
  OPEN ref FOR SELECT 
	"last_update", 
	"cpf", 
	"rg", 
	"fk_basic_client"
	FROM salesv001.brazilian LIMIT pagesize OFFSET pagesize * pagecount;
	RETURN ref;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION "salesv001"."client_from_system_insert"(
		"xlast_update" INTEGER, 
		"xfk_system" INTEGER, 
		"xfk_basic_client" INTEGER, 
		"xfk_shared_client" INTEGER, 
		"xfk_user" INTEGER) RETURNS void AS $$
BEGIN
	INSERT INTO salesv001.client_from_system (
	"last_update", 
	"fk_system", 
	"fk_basic_client", 
	"fk_shared_client", 
	"fk_user") VALUES (
	"xlast_update", 
	"xfk_system", 
	"xfk_basic_client", 
	"xfk_shared_client", 
	"xfk_user");
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION "salesv001"."client_from_system_update"(
	xid bigint,
		xlast_update INTEGER, 
		xfk_system INTEGER, 
		xfk_basic_client INTEGER, 
		xfk_shared_client INTEGER, 
		xfk_user INTEGER) RETURNS void AS $$
BEGIN
	UPDATE salesv001.client_from_system SET 	"last_update" = xINTEGER, 
	"fk_system" = xINTEGER, 
	"fk_basic_client" = xINTEGER, 
	"fk_shared_client" = xINTEGER, 
	"fk_user" = xINTEGER	WHERE id = xid;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION "salesv001"."client_from_system_select"(ref refcursor, pagesize integer, pagecount integer) RETURNS refcursor AS $$
BEGIN
  OPEN ref FOR SELECT 
	"last_update", 
	"fk_system", 
	"fk_basic_client", 
	"fk_shared_client", 
	"fk_user"
	FROM salesv001.client_from_system LIMIT pagesize OFFSET pagesize * pagecount;
	RETURN ref;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION "salesv001"."basic_client_insert"(
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
		"xfk_country" INTEGER) RETURNS void AS $$
BEGIN
	INSERT INTO salesv001.basic_client (
	"last_update", 
	"name", 
	"birth_date", 
	"birth_city", 
	"birth_state", 
	"mothers_name", 
	"fathers_name", 
	"profession", 
	"zip_code", 
	"address", 
	"neighborhood", 
	"city", 
	"state", 
	"complement", 
	"fk_country") VALUES (
	"xlast_update", 
	"xname", 
	"xbirth_date", 
	"xbirth_city", 
	"xbirth_state", 
	"xmothers_name", 
	"xfathers_name", 
	"xprofession", 
	"xzip_code", 
	"xaddress", 
	"xneighborhood", 
	"xcity", 
	"xstate", 
	"xcomplement", 
	"xfk_country");
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION "salesv001"."basic_client_update"(
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
		xfk_country INTEGER) RETURNS void AS $$
BEGIN
	UPDATE salesv001.basic_client SET 	"last_update" = xINTEGER, 
	"name" = xCHAR(30), 
	"birth_date" = xINTEGER, 
	"birth_city" = xCHAR(30), 
	"birth_state" = xCHAR(30), 
	"mothers_name" = xCHAR(30), 
	"fathers_name" = xCHAR(30), 
	"profession" = xCHAR(30), 
	"zip_code" = xCHAR(15), 
	"address" = xCHAR(30), 
	"neighborhood" = xCHAR(30), 
	"city" = xCHAR(30), 
	"state" = xCHAR(2), 
	"complement" = xVARCHAR(64), 
	"fk_country" = xINTEGER	WHERE id = xid;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION "salesv001"."basic_client_select"(ref refcursor, pagesize integer, pagecount integer) RETURNS refcursor AS $$
BEGIN
  OPEN ref FOR SELECT 
	"last_update", 
	"name", 
	"birth_date", 
	"birth_city", 
	"birth_state", 
	"mothers_name", 
	"fathers_name", 
	"profession", 
	"zip_code", 
	"address", 
	"neighborhood", 
	"city", 
	"state", 
	"complement", 
	"fk_country"
	FROM salesv001.basic_client LIMIT pagesize OFFSET pagesize * pagecount;
	RETURN ref;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION "salesv001"."bank_insert"(
		"xlast_update" INTEGER, 
		"xcode" CHAR(30), 
		"xname" VARCHAR(64)) RETURNS void AS $$
BEGIN
	INSERT INTO salesv001.bank (
	"last_update", 
	"code", 
	"name") VALUES (
	"xlast_update", 
	"xcode", 
	"xname");
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION "salesv001"."bank_update"(
	xid bigint,
		xlast_update INTEGER, 
		xcode CHAR(30), 
		xname VARCHAR(64)) RETURNS void AS $$
BEGIN
	UPDATE salesv001.bank SET 	"last_update" = xINTEGER, 
	"code" = xCHAR(30), 
	"name" = xVARCHAR(64)	WHERE id = xid;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION "salesv001"."bank_select"(ref refcursor, pagesize integer, pagecount integer) RETURNS refcursor AS $$
BEGIN
  OPEN ref FOR SELECT 
	"last_update", 
	"code", 
	"name"
	FROM salesv001.bank LIMIT pagesize OFFSET pagesize * pagecount;
	RETURN ref;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION "salesv001"."invoice_insert"(
		"xlast_update" INTEGER, 
		"xfk_system" INTEGER, 
		"xfk_sale" INTEGER, 
		"xfk_client_from_system" INTEGER, 
		"xfk_installment_type" INTEGER, 
		"xfk_interest_rate_type" INTEGER, 
		"xfk_bank" INTEGER, 
		"xfk_currency" INTEGER) RETURNS void AS $$
BEGIN
	INSERT INTO salesv001.invoice (
	"last_update", 
	"fk_system", 
	"fk_sale", 
	"fk_client_from_system", 
	"fk_installment_type", 
	"fk_interest_rate_type", 
	"fk_bank", 
	"fk_currency") VALUES (
	"xlast_update", 
	"xfk_system", 
	"xfk_sale", 
	"xfk_client_from_system", 
	"xfk_installment_type", 
	"xfk_interest_rate_type", 
	"xfk_bank", 
	"xfk_currency");
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION "salesv001"."invoice_update"(
	xid bigint,
		xlast_update INTEGER, 
		xfk_system INTEGER, 
		xfk_sale INTEGER, 
		xfk_client_from_system INTEGER, 
		xfk_installment_type INTEGER, 
		xfk_interest_rate_type INTEGER, 
		xfk_bank INTEGER, 
		xfk_currency INTEGER) RETURNS void AS $$
BEGIN
	UPDATE salesv001.invoice SET 	"last_update" = xINTEGER, 
	"fk_system" = xINTEGER, 
	"fk_sale" = xINTEGER, 
	"fk_client_from_system" = xINTEGER, 
	"fk_installment_type" = xINTEGER, 
	"fk_interest_rate_type" = xINTEGER, 
	"fk_bank" = xINTEGER, 
	"fk_currency" = xINTEGER	WHERE id = xid;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION "salesv001"."invoice_select"(ref refcursor, pagesize integer, pagecount integer) RETURNS refcursor AS $$
BEGIN
  OPEN ref FOR SELECT 
	"last_update", 
	"fk_system", 
	"fk_sale", 
	"fk_client_from_system", 
	"fk_installment_type", 
	"fk_interest_rate_type", 
	"fk_bank", 
	"fk_currency"
	FROM salesv001.invoice LIMIT pagesize OFFSET pagesize * pagecount;
	RETURN ref;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION "salesv001"."installment_type_insert"(
		"xlast_update" INTEGER, 
		"xname" CHAR(30)) RETURNS void AS $$
BEGIN
	INSERT INTO salesv001.installment_type (
	"last_update", 
	"name") VALUES (
	"xlast_update", 
	"xname");
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION "salesv001"."installment_type_update"(
	xid bigint,
		xlast_update INTEGER, 
		xname CHAR(30)) RETURNS void AS $$
BEGIN
	UPDATE salesv001.installment_type SET 	"last_update" = xINTEGER, 
	"name" = xCHAR(30)	WHERE id = xid;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION "salesv001"."installment_type_select"(ref refcursor, pagesize integer, pagecount integer) RETURNS refcursor AS $$
BEGIN
  OPEN ref FOR SELECT 
	"last_update", 
	"name"
	FROM salesv001.installment_type LIMIT pagesize OFFSET pagesize * pagecount;
	RETURN ref;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION "salesv001"."boleto_sicoob_insert"(
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
		"xfk_invoice" INTEGER) RETURNS void AS $$
BEGIN
	INSERT INTO salesv001.boleto_sicoob (
	"last_update", 
	"cpf", 
	"numero", 
	"data", 
	"vencimento", 
	"valor", 
	"nosso_numero", 
	"quantidade", 
	"parcela", 
	"foi_pago", 
	"data_de_pagamento", 
	"valor_recebido", 
	"fk_invoice") VALUES (
	"xlast_update", 
	"xcpf", 
	"xnumero", 
	"xdata", 
	"xvencimento", 
	"xvalor", 
	"xnosso_numero", 
	"xquantidade", 
	"xparcela", 
	"xfoi_pago", 
	"xdata_de_pagamento", 
	"xvalor_recebido", 
	"xfk_invoice");
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION "salesv001"."boleto_sicoob_update"(
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
		xfk_invoice INTEGER) RETURNS void AS $$
BEGIN
	UPDATE salesv001.boleto_sicoob SET 	"last_update" = xINTEGER, 
	"cpf" = xCHAR(30), 
	"numero" = xCHAR(30), 
	"data" = xINTEGER, 
	"vencimento" = xINTEGER, 
	"valor" = xDECIMAL(10,2), 
	"nosso_numero" = xCHAR(30), 
	"quantidade" = xINTEGER, 
	"parcela" = xINTEGER, 
	"foi_pago" = xBOOLEAN, 
	"data_de_pagamento" = xINTEGER, 
	"valor_recebido" = xDECIMAL(10,2), 
	"fk_invoice" = xINTEGER	WHERE id = xid;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION "salesv001"."boleto_sicoob_select"(ref refcursor, pagesize integer, pagecount integer) RETURNS refcursor AS $$
BEGIN
  OPEN ref FOR SELECT 
	"last_update", 
	"cpf", 
	"numero", 
	"data", 
	"vencimento", 
	"valor", 
	"nosso_numero", 
	"quantidade", 
	"parcela", 
	"foi_pago", 
	"data_de_pagamento", 
	"valor_recebido", 
	"fk_invoice"
	FROM salesv001.boleto_sicoob LIMIT pagesize OFFSET pagesize * pagecount;
	RETURN ref;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION "salesv001"."interest_rate_type_insert"(
		"xlast_update" INTEGER, 
		"xname" CHAR(30)) RETURNS void AS $$
BEGIN
	INSERT INTO salesv001.interest_rate_type (
	"last_update", 
	"name") VALUES (
	"xlast_update", 
	"xname");
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION "salesv001"."interest_rate_type_update"(
	xid bigint,
		xlast_update INTEGER, 
		xname CHAR(30)) RETURNS void AS $$
BEGIN
	UPDATE salesv001.interest_rate_type SET 	"last_update" = xINTEGER, 
	"name" = xCHAR(30)	WHERE id = xid;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION "salesv001"."interest_rate_type_select"(ref refcursor, pagesize integer, pagecount integer) RETURNS refcursor AS $$
BEGIN
  OPEN ref FOR SELECT 
	"last_update", 
	"name"
	FROM salesv001.interest_rate_type LIMIT pagesize OFFSET pagesize * pagecount;
	RETURN ref;
END;
$$ LANGUAGE plpgsql;

