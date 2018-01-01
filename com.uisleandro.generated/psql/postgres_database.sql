	DROP SCHEMA IF EXISTS "salesv001" CASCADE;
	CREATE SCHEMA "salesv001";
	CREATE TABLE "salesv001"."cash_register"(
		"id" bigserial PRIMARY KEY,
		"last_update" INTEGER NOT NULL,
		"fk_user" INTEGER NOT NULL,
		"opening_value" DECIMAL(10,2) NOT NULL,
		"received_value" DECIMAL(10,2) NOT NULL,
		"closing_value" DECIMAL(10,2) NOT NULL,
		"fk_currency" INTEGER NOT NULL
	);

	CREATE TABLE "salesv001"."cash_launch"(
		"id" bigserial PRIMARY KEY,
		"last_update" INTEGER NOT NULL,
		"fk_cash_register" INTEGER NOT NULL,
		"justification" CHAR(30) NOT NULL,
		"amount_spent" DECIMAL(10,2) NOT NULL,
		"fk_currency" INTEGER NOT NULL
	);

	CREATE TABLE "salesv001"."stock_review"(
		"id" bigserial PRIMARY KEY,
		"last_update" INTEGER NOT NULL,
		"fk_product" INTEGER NOT NULL,
		"actual_amount" INTEGER NOT NULL,
		"sold_items" INTEGER NOT NULL,
		"previous_amount" INTEGER NOT NULL,
		"missing_amount" INTEGER NOT NULL
	);

	CREATE TABLE "salesv001"."unit"(
		"id" bigserial PRIMARY KEY,
		"last_update" INTEGER NOT NULL,
		"name" CHAR(30) NOT NULL
	);

	CREATE TABLE "salesv001"."category"(
		"id" bigserial PRIMARY KEY,
		"last_update" INTEGER NOT NULL,
		"fk_category" INTEGER NOT NULL,
		"name" CHAR(30) NOT NULL
	);

	CREATE TABLE "salesv001"."brand"(
		"id" bigserial PRIMARY KEY,
		"last_update" INTEGER NOT NULL,
		"company_name" CHAR(30) NOT NULL,
		"fantasy_name" CHAR(30) NOT NULL
	);

	CREATE TABLE "salesv001"."gender"(
		"id" bigserial PRIMARY KEY,
		"last_update" INTEGER NOT NULL,
		"name" CHAR(30) NOT NULL
	);

	CREATE TABLE "salesv001"."product"(
		"id" bigserial PRIMARY KEY,
		"last_update" INTEGER NOT NULL,
		"fk_system" INTEGER NOT NULL,
		"barcode" CHAR NOT NULL,
		"description" VARCHAR(64) NOT NULL,
		"amount" INTEGER NOT NULL,
		"fk_gender" INTEGER NOT NULL,
		"purchase_price" DECIMAL(10,2) NOT NULL,
		"sale_price" DECIMAL(10,2) NOT NULL,
		"fk_category" INTEGER NOT NULL,
		"size" CHAR(30) NOT NULL,
		"fk_unit" INTEGER NOT NULL,
		"fk_currency" INTEGER NOT NULL,
		"expiration_date" INTEGER NOT NULL,
		"fk_brand" INTEGER NOT NULL
	);

	CREATE TABLE "salesv001"."distributor_contact"(
		"id" bigserial PRIMARY KEY,
		"last_update" INTEGER NOT NULL,
		"name" CHAR(30) NOT NULL,
		"email1" CHAR(30) NOT NULL,
		"email2" CHAR(30) NOT NULL,
		"phone_number1" CHAR(30) NOT NULL,
		"phone_number2" CHAR(30) NOT NULL,
		"phone_number3" CHAR(30) NOT NULL,
		"phone_number4" CHAR(30) NOT NULL,
		"fk_brand" INTEGER NOT NULL
	);

	CREATE TABLE "salesv001"."Issue"(
		"id" bigserial PRIMARY KEY,
		"last_update" INTEGER NOT NULL,
		"fk_shared_client" INTEGER NOT NULL,
		"fk_system" INTEGER NOT NULL,
		"description" VARCHAR(64) NOT NULL,
		"active" BOOLEAN NOT NULL,
		"isAnswer" BOOLEAN NOT NULL,
		"fk_issue" INTEGER NOT NULL
	);

	CREATE TABLE "salesv001"."shared_client"(
		"id" bigserial PRIMARY KEY,
		"last_update" INTEGER NOT NULL,
		"name" CHAR(30) NOT NULL,
		"birth_date" INTEGER NOT NULL,
		"birth_city" CHAR(30) NOT NULL,
		"birth_state" CHAR(30) NOT NULL,
		"mothers_name" CHAR(30) NOT NULL,
		"fathers_name" CHAR(30) NOT NULL,
		"profession" CHAR(30) NOT NULL,
		"zip_code" CHAR(15) NOT NULL,
		"address" CHAR(30) NOT NULL,
		"neighborhood" CHAR(30) NOT NULL,
		"city" CHAR(30) NOT NULL,
		"state" CHAR(2) NOT NULL,
		"complement" VARCHAR(64) NOT NULL,
		"fk_country" INTEGER NOT NULL
	);

	CREATE TABLE "salesv001"."product_on_sale"(
		"id" bigserial PRIMARY KEY,
		"last_update" INTEGER NOT NULL,
		"fk_sale" INTEGER NOT NULL,
		"fk_product" INTEGER NOT NULL
	);

	CREATE TABLE "salesv001"."sale_type"(
		"id" bigserial PRIMARY KEY,
		"last_update" INTEGER NOT NULL,
		"name" CHAR(30) NOT NULL
	);

	CREATE TABLE "salesv001"."sale"(
		"id" bigserial PRIMARY KEY,
		"last_update" INTEGER NOT NULL,
		"fk_sale_type" INTEGER NOT NULL,
		"fk_system" INTEGER NOT NULL,
		"total_value" DECIMAL(10,2) NOT NULL,
		"fk_user" INTEGER NOT NULL,
		"fk_client_from_system" INTEGER NOT NULL,
		"fk_currency" INTEGER NOT NULL
	);

	CREATE TABLE "salesv001"."db_log"(
		"id" bigserial PRIMARY KEY,
		"last_update" INTEGER NOT NULL,
		"action_name" CHAR(30) NOT NULL,
		"parameter" VARCHAR(64) NOT NULL,
		"fk_user" INTEGER NOT NULL
	);

	CREATE TABLE "salesv001"."currency"(
		"id" bigserial PRIMARY KEY,
		"last_update" INTEGER NOT NULL,
		"abbreviature" CHAR(30) NOT NULL,
		"description" CHAR(30) NOT NULL
	);

	CREATE TABLE "salesv001"."token_type"(
		"id" bigserial PRIMARY KEY,
		"last_update" INTEGER NOT NULL,
		"name" CHAR(30) NOT NULL
	);

	CREATE TABLE "salesv001"."user"(
		"id" bigserial PRIMARY KEY,
		"last_update" INTEGER NOT NULL,
		"fk_system" INTEGER NOT NULL,
		"fk_role" INTEGER NOT NULL,
		"username" CHAR(30) NOT NULL,
		"password" CHAR(30) NOT NULL,
		"name" CHAR(30) NOT NULL,
		"email" CHAR(30) NOT NULL,
		"last_use_time" INTEGER NOT NULL,
		"last_error_time" INTEGER NOT NULL,
		"error_count" INTEGER NOT NULL,
		"active" BOOLEAN NOT NULL
	);

	CREATE TABLE "salesv001"."token"(
		"id" bigserial PRIMARY KEY,
		"last_update" INTEGER NOT NULL,
		"fk_user" INTEGER NOT NULL,
		"fk_system" INTEGER NOT NULL,
		"fk_token_type" INTEGER NOT NULL,
		"guid" CHAR(36) NOT NULL,
		"last_use_time" INTEGER NOT NULL,
		"expiration_time" INTEGER NOT NULL
	);

	CREATE TABLE "salesv001"."system"(
		"id" bigserial PRIMARY KEY,
		"last_update" INTEGER NOT NULL,
		"name" CHAR(30) NOT NULL,
		"enabled" BOOLEAN NOT NULL,
		"fk_currency" INTEGER NOT NULL
	);

	CREATE TABLE "salesv001"."role"(
		"id" bigserial PRIMARY KEY,
		"last_update" INTEGER NOT NULL,
		"name" CHAR(30) NOT NULL
	);

	CREATE TABLE "salesv001"."discount"(
		"id" bigserial PRIMARY KEY,
		"last_update" INTEGER NOT NULL,
		"value" DECIMAL(10,2) NOT NULL,
		"percentage" DECIMAL(10,2) NOT NULL,
		"fk_product" INTEGER NOT NULL,
		"fk_category" INTEGER NOT NULL,
		"fk_brand" INTEGER NOT NULL,
		"fk_client_from_system" INTEGER NOT NULL,
		"fk_gender" INTEGER NOT NULL
	);

	CREATE TABLE "salesv001"."country"(
		"id" bigserial PRIMARY KEY,
		"last_update" INTEGER NOT NULL,
		"name" CHAR(30) NOT NULL
	);

	CREATE TABLE "salesv001"."brazilian"(
		"id" bigserial PRIMARY KEY,
		"last_update" INTEGER NOT NULL,
		"cpf" CHAR(30) NOT NULL,
		"rg" CHAR(30) NOT NULL,
		"fk_basic_client" INTEGER NOT NULL
	);

	CREATE TABLE "salesv001"."client_from_system"(
		"id" bigserial PRIMARY KEY,
		"last_update" INTEGER NOT NULL,
		"fk_system" INTEGER NOT NULL,
		"fk_basic_client" INTEGER NOT NULL,
		"fk_shared_client" INTEGER NOT NULL,
		"fk_user" INTEGER NOT NULL
	);

	CREATE TABLE "salesv001"."basic_client"(
		"id" bigserial PRIMARY KEY,
		"last_update" INTEGER NOT NULL,
		"name" CHAR(30) NOT NULL,
		"birth_date" INTEGER NOT NULL,
		"birth_city" CHAR(30) NOT NULL,
		"birth_state" CHAR(30) NOT NULL,
		"mothers_name" CHAR(30) NOT NULL,
		"fathers_name" CHAR(30) NOT NULL,
		"profession" CHAR(30) NOT NULL,
		"zip_code" CHAR(15) NOT NULL,
		"address" CHAR(30) NOT NULL,
		"neighborhood" CHAR(30) NOT NULL,
		"city" CHAR(30) NOT NULL,
		"state" CHAR(2) NOT NULL,
		"complement" VARCHAR(64) NOT NULL,
		"fk_country" INTEGER NOT NULL
	);

	CREATE TABLE "salesv001"."bank"(
		"id" bigserial PRIMARY KEY,
		"last_update" INTEGER NOT NULL,
		"code" CHAR(30) NOT NULL,
		"name" VARCHAR(64) NOT NULL
	);

	CREATE TABLE "salesv001"."invoice"(
		"id" bigserial PRIMARY KEY,
		"last_update" INTEGER NOT NULL,
		"fk_system" INTEGER NOT NULL,
		"fk_sale" INTEGER NOT NULL,
		"fk_client_from_system" INTEGER NOT NULL,
		"fk_installment_type" INTEGER NOT NULL,
		"fk_interest_rate_type" INTEGER NOT NULL,
		"fk_bank" INTEGER NOT NULL,
		"fk_currency" INTEGER NOT NULL
	);

	CREATE TABLE "salesv001"."installment_type"(
		"id" bigserial PRIMARY KEY,
		"last_update" INTEGER NOT NULL,
		"name" CHAR(30) NOT NULL
	);

	CREATE TABLE "salesv001"."boleto_sicoob"(
		"id" bigserial PRIMARY KEY,
		"last_update" INTEGER NOT NULL,
		"cpf" CHAR(30) NOT NULL,
		"numero" CHAR(30) NOT NULL,
		"data" INTEGER NOT NULL,
		"vencimento" INTEGER NOT NULL,
		"valor" DECIMAL(10,2) NOT NULL,
		"nosso_numero" CHAR(30) NOT NULL,
		"quantidade" INTEGER NOT NULL,
		"parcela" INTEGER NOT NULL,
		"foi_pago" BOOLEAN NOT NULL,
		"data_de_pagamento" INTEGER NOT NULL,
		"valor_recebido" DECIMAL(10,2) NOT NULL,
		"fk_invoice" INTEGER NOT NULL
	);

	CREATE TABLE "salesv001"."interest_rate_type"(
		"id" bigserial PRIMARY KEY,
		"last_update" INTEGER NOT NULL,
		"name" CHAR(30) NOT NULL
	);


	CREATE INDEX "cash_register_user_idx" ON "salesv001"."cash_register" ("fk_user");
	CREATE INDEX "cash_register_currency_idx" ON "salesv001"."cash_register" ("fk_currency");

	CREATE INDEX "cash_launch_cash_register_idx" ON "salesv001"."cash_launch" ("fk_cash_register");
	CREATE INDEX "cash_launch_currency_idx" ON "salesv001"."cash_launch" ("fk_currency");

	CREATE INDEX "stock_review_product_idx" ON "salesv001"."stock_review" ("fk_product");


	CREATE INDEX "category_category_idx" ON "salesv001"."category" ("fk_category");



	CREATE INDEX "product_system_idx" ON "salesv001"."product" ("fk_system");
	CREATE INDEX "product_gender_idx" ON "salesv001"."product" ("fk_gender");
	CREATE INDEX "product_category_idx" ON "salesv001"."product" ("fk_category");
	CREATE INDEX "product_unit_idx" ON "salesv001"."product" ("fk_unit");
	CREATE INDEX "product_currency_idx" ON "salesv001"."product" ("fk_currency");
	CREATE INDEX "product_brand_idx" ON "salesv001"."product" ("fk_brand");

	CREATE INDEX "distributor_contact_brand_idx" ON "salesv001"."distributor_contact" ("fk_brand");

	CREATE INDEX "Issue_shared_client_idx" ON "salesv001"."Issue" ("fk_shared_client");
	CREATE INDEX "Issue_system_idx" ON "salesv001"."Issue" ("fk_system");
	CREATE INDEX "Issue_issue_idx" ON "salesv001"."Issue" ("fk_issue");

	CREATE INDEX "shared_client_country_idx" ON "salesv001"."shared_client" ("fk_country");

	CREATE INDEX "product_on_sale_sale_idx" ON "salesv001"."product_on_sale" ("fk_sale");
	CREATE INDEX "product_on_sale_product_idx" ON "salesv001"."product_on_sale" ("fk_product");


	CREATE INDEX "sale_sale_type_idx" ON "salesv001"."sale" ("fk_sale_type");
	CREATE INDEX "sale_system_idx" ON "salesv001"."sale" ("fk_system");
	CREATE INDEX "sale_user_idx" ON "salesv001"."sale" ("fk_user");
	CREATE INDEX "sale_client_from_system_idx" ON "salesv001"."sale" ("fk_client_from_system");
	CREATE INDEX "sale_currency_idx" ON "salesv001"."sale" ("fk_currency");

	CREATE INDEX "db_log_user_idx" ON "salesv001"."db_log" ("fk_user");



	CREATE INDEX "user_system_idx" ON "salesv001"."user" ("fk_system");
	CREATE INDEX "user_role_idx" ON "salesv001"."user" ("fk_role");

	CREATE INDEX "token_user_idx" ON "salesv001"."token" ("fk_user");
	CREATE INDEX "token_system_idx" ON "salesv001"."token" ("fk_system");
	CREATE INDEX "token_token_type_idx" ON "salesv001"."token" ("fk_token_type");

	CREATE INDEX "system_currency_idx" ON "salesv001"."system" ("fk_currency");


	CREATE INDEX "discount_product_idx" ON "salesv001"."discount" ("fk_product");
	CREATE INDEX "discount_category_idx" ON "salesv001"."discount" ("fk_category");
	CREATE INDEX "discount_brand_idx" ON "salesv001"."discount" ("fk_brand");
	CREATE INDEX "discount_client_from_system_idx" ON "salesv001"."discount" ("fk_client_from_system");
	CREATE INDEX "discount_gender_idx" ON "salesv001"."discount" ("fk_gender");


	CREATE INDEX "brazilian_cpf_idx" ON "salesv001"."brazilian" ("cpf");
	CREATE INDEX "brazilian_basic_client_idx" ON "salesv001"."brazilian" ("fk_basic_client");

	CREATE INDEX "client_from_system_system_idx" ON "salesv001"."client_from_system" ("fk_system");
	CREATE INDEX "client_from_system_basic_client_idx" ON "salesv001"."client_from_system" ("fk_basic_client");
	CREATE INDEX "client_from_system_shared_client_idx" ON "salesv001"."client_from_system" ("fk_shared_client");
	CREATE INDEX "client_from_system_user_idx" ON "salesv001"."client_from_system" ("fk_user");

	CREATE INDEX "basic_client_country_idx" ON "salesv001"."basic_client" ("fk_country");


	CREATE INDEX "invoice_system_idx" ON "salesv001"."invoice" ("fk_system");
	CREATE INDEX "invoice_sale_idx" ON "salesv001"."invoice" ("fk_sale");
	CREATE INDEX "invoice_client_from_system_idx" ON "salesv001"."invoice" ("fk_client_from_system");
	CREATE INDEX "invoice_installment_type_idx" ON "salesv001"."invoice" ("fk_installment_type");
	CREATE INDEX "invoice_interest_rate_type_idx" ON "salesv001"."invoice" ("fk_interest_rate_type");
	CREATE INDEX "invoice_bank_idx" ON "salesv001"."invoice" ("fk_bank");
	CREATE INDEX "invoice_currency_idx" ON "salesv001"."invoice" ("fk_currency");


	CREATE INDEX "boleto_sicoob_cpf_idx" ON "salesv001"."boleto_sicoob" ("cpf");
	CREATE INDEX "boleto_sicoob_invoice_idx" ON "salesv001"."boleto_sicoob" ("fk_invoice");



	ALTER TABLE "salesv001"."cash_register" 
	ADD CONSTRAINT "fk_salesv001_cash_register_user"
	FOREIGN KEY ("fk_user") 
	REFERENCES "salesv001"."user"("id") 
	ON DELETE CASCADE;
	ALTER TABLE "salesv001"."cash_register" 
	ADD CONSTRAINT "fk_salesv001_cash_register_currency"
	FOREIGN KEY ("fk_currency") 
	REFERENCES "salesv001"."currency"("id") 
	ON DELETE CASCADE;

	ALTER TABLE "salesv001"."cash_launch" 
	ADD CONSTRAINT "fk_salesv001_cash_launch_cash_register"
	FOREIGN KEY ("fk_cash_register") 
	REFERENCES "salesv001"."cash_register"("id") 
	ON DELETE CASCADE;
	ALTER TABLE "salesv001"."cash_launch" 
	ADD CONSTRAINT "fk_salesv001_cash_launch_currency"
	FOREIGN KEY ("fk_currency") 
	REFERENCES "salesv001"."currency"("id") 
	ON DELETE CASCADE;

	ALTER TABLE "salesv001"."stock_review" 
	ADD CONSTRAINT "fk_salesv001_stock_review_product"
	FOREIGN KEY ("fk_product") 
	REFERENCES "salesv001"."product"("id") 
	ON DELETE CASCADE;





	ALTER TABLE "salesv001"."product" 
	ADD CONSTRAINT "fk_salesv001_product_system"
	FOREIGN KEY ("fk_system") 
	REFERENCES "salesv001"."system"("id") 
	ON DELETE CASCADE;
	ALTER TABLE "salesv001"."product" 
	ADD CONSTRAINT "fk_salesv001_product_gender"
	FOREIGN KEY ("fk_gender") 
	REFERENCES "salesv001"."gender"("id") 
	ON DELETE CASCADE;
	ALTER TABLE "salesv001"."product" 
	ADD CONSTRAINT "fk_salesv001_product_category"
	FOREIGN KEY ("fk_category") 
	REFERENCES "salesv001"."category"("id") 
	ON DELETE CASCADE;
	ALTER TABLE "salesv001"."product" 
	ADD CONSTRAINT "fk_salesv001_product_unit"
	FOREIGN KEY ("fk_unit") 
	REFERENCES "salesv001"."unit"("id") 
	ON DELETE CASCADE;
	ALTER TABLE "salesv001"."product" 
	ADD CONSTRAINT "fk_salesv001_product_currency"
	FOREIGN KEY ("fk_currency") 
	REFERENCES "salesv001"."currency"("id") 
	ON DELETE CASCADE;
	ALTER TABLE "salesv001"."product" 
	ADD CONSTRAINT "fk_salesv001_product_brand"
	FOREIGN KEY ("fk_brand") 
	REFERENCES "salesv001"."brand"("id") 
	ON DELETE CASCADE;

	ALTER TABLE "salesv001"."distributor_contact" 
	ADD CONSTRAINT "fk_salesv001_distributor_contact_brand"
	FOREIGN KEY ("fk_brand") 
	REFERENCES "salesv001"."brand"("id") 
	ON DELETE CASCADE;

	ALTER TABLE "salesv001"."Issue" 
	ADD CONSTRAINT "fk_salesv001_Issue_shared_client"
	FOREIGN KEY ("fk_shared_client") 
	REFERENCES "salesv001"."shared_client"("id") 
	ON DELETE CASCADE;
	ALTER TABLE "salesv001"."Issue" 
	ADD CONSTRAINT "fk_salesv001_Issue_system"
	FOREIGN KEY ("fk_system") 
	REFERENCES "salesv001"."system"("id") 
	ON DELETE CASCADE;

	ALTER TABLE "salesv001"."shared_client" 
	ADD CONSTRAINT "fk_salesv001_shared_client_country"
	FOREIGN KEY ("fk_country") 
	REFERENCES "salesv001"."country"("id") 
	ON DELETE CASCADE;

	ALTER TABLE "salesv001"."product_on_sale" 
	ADD CONSTRAINT "fk_salesv001_product_on_sale_sale"
	FOREIGN KEY ("fk_sale") 
	REFERENCES "salesv001"."sale"("id") 
	ON DELETE CASCADE;
	ALTER TABLE "salesv001"."product_on_sale" 
	ADD CONSTRAINT "fk_salesv001_product_on_sale_product"
	FOREIGN KEY ("fk_product") 
	REFERENCES "salesv001"."product"("id") 
	ON DELETE CASCADE;


	ALTER TABLE "salesv001"."sale" 
	ADD CONSTRAINT "fk_salesv001_sale_sale_type"
	FOREIGN KEY ("fk_sale_type") 
	REFERENCES "salesv001"."sale_type"("id") 
	ON DELETE CASCADE;
	ALTER TABLE "salesv001"."sale" 
	ADD CONSTRAINT "fk_salesv001_sale_system"
	FOREIGN KEY ("fk_system") 
	REFERENCES "salesv001"."system"("id") 
	ON DELETE CASCADE;
	ALTER TABLE "salesv001"."sale" 
	ADD CONSTRAINT "fk_salesv001_sale_user"
	FOREIGN KEY ("fk_user") 
	REFERENCES "salesv001"."user"("id") 
	ON DELETE CASCADE;
	ALTER TABLE "salesv001"."sale" 
	ADD CONSTRAINT "fk_salesv001_sale_client_from_system"
	FOREIGN KEY ("fk_client_from_system") 
	REFERENCES "salesv001"."client_from_system"("id") 
	ON DELETE CASCADE;
	ALTER TABLE "salesv001"."sale" 
	ADD CONSTRAINT "fk_salesv001_sale_currency"
	FOREIGN KEY ("fk_currency") 
	REFERENCES "salesv001"."currency"("id") 
	ON DELETE CASCADE;

	ALTER TABLE "salesv001"."db_log" 
	ADD CONSTRAINT "fk_salesv001_db_log_user"
	FOREIGN KEY ("fk_user") 
	REFERENCES "salesv001"."user"("id") 
	ON DELETE CASCADE;



	ALTER TABLE "salesv001"."user" 
	ADD CONSTRAINT "fk_salesv001_user_system"
	FOREIGN KEY ("fk_system") 
	REFERENCES "salesv001"."system"("id") 
	ON DELETE CASCADE;
	ALTER TABLE "salesv001"."user" 
	ADD CONSTRAINT "fk_salesv001_user_role"
	FOREIGN KEY ("fk_role") 
	REFERENCES "salesv001"."role"("id") 
	ON DELETE CASCADE;

	ALTER TABLE "salesv001"."token" 
	ADD CONSTRAINT "fk_salesv001_token_user"
	FOREIGN KEY ("fk_user") 
	REFERENCES "salesv001"."user"("id") 
	ON DELETE CASCADE;
	ALTER TABLE "salesv001"."token" 
	ADD CONSTRAINT "fk_salesv001_token_system"
	FOREIGN KEY ("fk_system") 
	REFERENCES "salesv001"."system"("id") 
	ON DELETE CASCADE;
	ALTER TABLE "salesv001"."token" 
	ADD CONSTRAINT "fk_salesv001_token_token_type"
	FOREIGN KEY ("fk_token_type") 
	REFERENCES "salesv001"."token_type"("id") 
	ON DELETE CASCADE;

	ALTER TABLE "salesv001"."system" 
	ADD CONSTRAINT "fk_salesv001_system_currency"
	FOREIGN KEY ("fk_currency") 
	REFERENCES "salesv001"."currency"("id") 
	ON DELETE CASCADE;


	ALTER TABLE "salesv001"."discount" 
	ADD CONSTRAINT "fk_salesv001_discount_product"
	FOREIGN KEY ("fk_product") 
	REFERENCES "salesv001"."product"("id") 
	ON DELETE CASCADE;
	ALTER TABLE "salesv001"."discount" 
	ADD CONSTRAINT "fk_salesv001_discount_category"
	FOREIGN KEY ("fk_category") 
	REFERENCES "salesv001"."category"("id") 
	ON DELETE CASCADE;
	ALTER TABLE "salesv001"."discount" 
	ADD CONSTRAINT "fk_salesv001_discount_brand"
	FOREIGN KEY ("fk_brand") 
	REFERENCES "salesv001"."brand"("id") 
	ON DELETE CASCADE;
	ALTER TABLE "salesv001"."discount" 
	ADD CONSTRAINT "fk_salesv001_discount_client_from_system"
	FOREIGN KEY ("fk_client_from_system") 
	REFERENCES "salesv001"."client_from_system"("id") 
	ON DELETE CASCADE;
	ALTER TABLE "salesv001"."discount" 
	ADD CONSTRAINT "fk_salesv001_discount_gender"
	FOREIGN KEY ("fk_gender") 
	REFERENCES "salesv001"."gender"("id") 
	ON DELETE CASCADE;


	ALTER TABLE "salesv001"."brazilian" 
	ADD CONSTRAINT "fk_salesv001_brazilian_basic_client"
	FOREIGN KEY ("fk_basic_client") 
	REFERENCES "salesv001"."basic_client"("id") 
	ON DELETE CASCADE;

	ALTER TABLE "salesv001"."client_from_system" 
	ADD CONSTRAINT "fk_salesv001_client_from_system_system"
	FOREIGN KEY ("fk_system") 
	REFERENCES "salesv001"."system"("id") 
	ON DELETE CASCADE;
	ALTER TABLE "salesv001"."client_from_system" 
	ADD CONSTRAINT "fk_salesv001_client_from_system_basic_client"
	FOREIGN KEY ("fk_basic_client") 
	REFERENCES "salesv001"."basic_client"("id") 
	ON DELETE CASCADE;
	ALTER TABLE "salesv001"."client_from_system" 
	ADD CONSTRAINT "fk_salesv001_client_from_system_shared_client"
	FOREIGN KEY ("fk_shared_client") 
	REFERENCES "salesv001"."shared_client"("id") 
	ON DELETE CASCADE;
	ALTER TABLE "salesv001"."client_from_system" 
	ADD CONSTRAINT "fk_salesv001_client_from_system_user"
	FOREIGN KEY ("fk_user") 
	REFERENCES "salesv001"."user"("id") 
	ON DELETE CASCADE;

	ALTER TABLE "salesv001"."basic_client" 
	ADD CONSTRAINT "fk_salesv001_basic_client_country"
	FOREIGN KEY ("fk_country") 
	REFERENCES "salesv001"."country"("id") 
	ON DELETE CASCADE;


	ALTER TABLE "salesv001"."invoice" 
	ADD CONSTRAINT "fk_salesv001_invoice_system"
	FOREIGN KEY ("fk_system") 
	REFERENCES "salesv001"."system"("id") 
	ON DELETE CASCADE;
	ALTER TABLE "salesv001"."invoice" 
	ADD CONSTRAINT "fk_salesv001_invoice_sale"
	FOREIGN KEY ("fk_sale") 
	REFERENCES "salesv001"."sale"("id") 
	ON DELETE CASCADE;
	ALTER TABLE "salesv001"."invoice" 
	ADD CONSTRAINT "fk_salesv001_invoice_client_from_system"
	FOREIGN KEY ("fk_client_from_system") 
	REFERENCES "salesv001"."client_from_system"("id") 
	ON DELETE CASCADE;
	ALTER TABLE "salesv001"."invoice" 
	ADD CONSTRAINT "fk_salesv001_invoice_installment_type"
	FOREIGN KEY ("fk_installment_type") 
	REFERENCES "salesv001"."installment_type"("id") 
	ON DELETE CASCADE;
	ALTER TABLE "salesv001"."invoice" 
	ADD CONSTRAINT "fk_salesv001_invoice_interest_rate_type"
	FOREIGN KEY ("fk_interest_rate_type") 
	REFERENCES "salesv001"."interest_rate_type"("id") 
	ON DELETE CASCADE;
	ALTER TABLE "salesv001"."invoice" 
	ADD CONSTRAINT "fk_salesv001_invoice_bank"
	FOREIGN KEY ("fk_bank") 
	REFERENCES "salesv001"."bank"("id") 
	ON DELETE CASCADE;
	ALTER TABLE "salesv001"."invoice" 
	ADD CONSTRAINT "fk_salesv001_invoice_currency"
	FOREIGN KEY ("fk_currency") 
	REFERENCES "salesv001"."currency"("id") 
	ON DELETE CASCADE;


	ALTER TABLE "salesv001"."boleto_sicoob" 
	ADD CONSTRAINT "fk_salesv001_boleto_sicoob_invoice"
	FOREIGN KEY ("fk_invoice") 
	REFERENCES "salesv001"."invoice"("id") 
	ON DELETE CASCADE;



