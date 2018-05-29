package org.lamtf._extdb2;

import org.lamtf.contract.IConfig;
import org.lamtf.contract.localdb.IDataSource;
import org.lamtf.contract.remotedb.IOnlineServiceInvoker;
import org.lamtf.data.User;

public class UserContentDataSource implements IDataSource<User, Integer>
{
	@Override
	public void setOnlineServiceInvoker(IOnlineServiceInvoker<User> iOnlineServiceInvoker) {

	}

	@Override
	public void setConfig(IConfig iConfig) {

	}
}
