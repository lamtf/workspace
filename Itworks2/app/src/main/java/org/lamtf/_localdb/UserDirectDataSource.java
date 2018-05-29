package org.lamtf._localdb;

import org.lamtf.contract.IConfig;
import org.lamtf.contract.db.IDbHelper;
import org.lamtf.contract.localdb.IDataSource;
import org.lamtf.contract.localdb.IGotDbHelper;
import org.lamtf.contract.remotedb.IOnlineServiceInvoker;
import org.lamtf.data.User;

// The same operation names which the content provider has
// this class needs to have as well
// but the return of all those functions is the object itself, not a cursor
public class UserDirectDataSource<T>
implements IGotDbHelper, IDataSource<User,Long>
{

	@Override
	public void setDbHelper(IDbHelper dbHelper) {

	}

	@Override
	public void setOnlineServiceInvoker(IOnlineServiceInvoker iOnlineServiceInvoker) {

	}

	@Override
	public void setConfig(IConfig iConfig) {

	}
}
