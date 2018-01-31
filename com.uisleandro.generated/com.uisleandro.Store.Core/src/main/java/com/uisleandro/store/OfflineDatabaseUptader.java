package com.uisleandro.store;

import android.content.Context;

import com.uisleandro.store.supply.sync.UnitSync;
import com.uisleandro.store.supply.sync.BrandSync;
import com.uisleandro.store.supply.sync.GenderSync;
import com.uisleandro.store.sales.sync.SaleTypeSync;
import com.uisleandro.store.core.sync.CurrencySync;
import com.uisleandro.store.core.sync.TokenTypeSync;
import com.uisleandro.store.core.sync.RoleSync;
import com.uisleandro.store.client.sync.CountrySync;
import com.uisleandro.store.receivement.sync.BankSync;
import com.uisleandro.store.receivement.sync.InstallmentTypeSync;
import com.uisleandro.store.receivement.sync.InterestRateTypeSync;
import com.uisleandro.store.referral.sync.ResellerSync;
import com.uisleandro.store.core.sync.SystemSync;
import com.uisleandro.store.core.sync.UserSync;
import com.uisleandro.store.supply.sync.CategorySync;
import com.uisleandro.store.cash.sync.CashRegisterSync;
import com.uisleandro.store.supply.sync.ProductSync;
import com.uisleandro.store.supply.sync.DistributorContactSync;
import com.uisleandro.store.credit_protection.sync.SharedClientSync;
import com.uisleandro.store.credit_protection.sync.IssueSync;
import com.uisleandro.store.client.sync.BasicClientSync;
import com.uisleandro.store.client.sync.ClientFromSystemSync;
import com.uisleandro.store.core.sync.DbLogSync;
import com.uisleandro.store.cash.sync.CashLaunchSync;
import com.uisleandro.store.core.sync.TokenSync;
import com.uisleandro.store.supply.sync.StockReviewSync;
import com.uisleandro.store.discount.sync.DiscountSync;
import com.uisleandro.store.sales.sync.SaleSync;
import com.uisleandro.store.sales.sync.ProductOnSaleSync;
import com.uisleandro.store.client.sync.BrazilianSync;
import com.uisleandro.store.receivement.sync.InvoiceSync;
import com.uisleandro.store.receivement.sync.BoletoSicoobSync;


import com.uisleandro.store.util.web.TLSUtils;
import com.uisleandro.store.util.web.TLSWebClient2;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/*
Created by Uisleandro Costa dos Santos
uisleandro@gmail.com

This class is Responsible to update a Remote Database based on date In the local database
After comming offline. It's userfull if you're going to collect offline data from a phone
and then send it to a server.
*/
public class OfflineDatabaseUptader {

	TLSUtils utils;

	public OfflineDatabaseUptader(TLSUtils utils) {
		this.utils = utils;
	}

	public void Sync(){

		List<SyncUpdater> syncList = new ArrayList<>();

		Context context = utils.getContext();

		TLSWebClient2 client = new TLSWebClient2(utils);

		syncList.add(new UnitSync(client, context));
		syncList.add(new BrandSync(client, context));
		syncList.add(new GenderSync(client, context));
		syncList.add(new SaleTypeSync(client, context));
		syncList.add(new CurrencySync(client, context));
		syncList.add(new TokenTypeSync(client, context));
		syncList.add(new RoleSync(client, context));
		syncList.add(new CountrySync(client, context));
		syncList.add(new BankSync(client, context));
		syncList.add(new InstallmentTypeSync(client, context));
		syncList.add(new InterestRateTypeSync(client, context));
		syncList.add(new ResellerSync(client, context));
		syncList.add(new SystemSync(client, context));
		// system points to: currency
		// system points to: reseller
		syncList.add(new UserSync(client, context));
		// user points to: system
		// user points to: role
		syncList.add(new CategorySync(client, context));
		// category points to: category
		syncList.add(new CashRegisterSync(client, context));
		// cash_register points to: user
		syncList.add(new ProductSync(client, context));
		// product points to: system
		// product points to: gender
		// product points to: category
		// product points to: unit
		// product points to: brand
		syncList.add(new DistributorContactSync(client, context));
		// distributor_contact points to: brand
		syncList.add(new SharedClientSync(client, context));
		// shared_client points to: country
		syncList.add(new IssueSync(client, context));
		// Issue points to: shared_client
		// Issue points to: system
		syncList.add(new BasicClientSync(client, context));
		// basic_client points to: country
		syncList.add(new ClientFromSystemSync(client, context));
		// client_from_system points to: system
		// client_from_system points to: basic_client
		// client_from_system points to: shared_client
		// client_from_system points to: user
		syncList.add(new DbLogSync(client, context));
		// db_log points to: user
		syncList.add(new CashLaunchSync(client, context));
		// cash_launch points to: cash_register
		syncList.add(new TokenSync(client, context));
		// token points to: user
		// token points to: system
		// token points to: token_type
		syncList.add(new StockReviewSync(client, context));
		// stock_review points to: product
		syncList.add(new DiscountSync(client, context));
		// discount points to: product
		// discount points to: category
		// discount points to: brand
		// discount points to: client_from_system
		// discount points to: gender
		syncList.add(new SaleSync(client, context));
		// sale points to: sale_type
		// sale points to: system
		// sale points to: user
		// sale points to: client_from_system
		syncList.add(new ProductOnSaleSync(client, context));
		// product_on_sale points to: sale
		// product_on_sale points to: product
		syncList.add(new BrazilianSync(client, context));
		// brazilian points to: basic_client
		syncList.add(new InvoiceSync(client, context));
		// invoice points to: system
		// invoice points to: sale
		// invoice points to: client_from_system
		// invoice points to: installment_type
		// invoice points to: interest_rate_type
		// invoice points to: bank
		syncList.add(new BoletoSicoobSync(client, context));
		// boleto_sicoob points to: invoice


		Iterator<SyncUpdater> it;

		it = syncList.iterator();
		while(it.hasNext()){
			SyncUpdater that = it.next();
			that.insert_on_client();
		}

		it = syncList.iterator();
		while(it.hasNext()){
			SyncUpdater that = it.next();
			that.update_on_client();
		}

		it = syncList.iterator();
		while(it.hasNext()){
			SyncUpdater that = it.next();
			that.fix_foreign_keys_on_client();
		}

		it = syncList.iterator();
		while(it.hasNext()){
			SyncUpdater that = it.next();
			that.insert_on_server(); //foreign keys translated
		}
		it = syncList.iterator();
		while(it.hasNext()){
			SyncUpdater that = it.next();
			that.update_on_server(); //foreign keys translated
		}
	}

}