package com.money_transfer_api.app.repository;

import com.money_transfer_api.app.service.AccountService;

public abstract class RepoFactory {

	public static final int H2 = 1;

	public abstract AccountService getAccountService();

	public static RepoFactory getRepoFactory(int factoryCode) {

		switch (factoryCode) {
		case H2:
			return new H2DataFactory();
		default:
			// by default using H2 in memory database
			return new H2DataFactory();
		}
	}
}
