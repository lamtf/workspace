package org.lamtf.contract.localdb;

import org.lamtf.contract.IConfig;
import org.lamtf.contract.db.queries.ICreate;
import org.lamtf.contract.db.queries.ICustom;
import org.lamtf.contract.db.queries.IDelete;
import org.lamtf.contract.db.queries.IQuery;
import org.lamtf.contract.db.queries.IRead;
import org.lamtf.contract.db.queries.IUpdate;
import org.lamtf.contract.remotedb.IOnlineServiceInvoker;

// The same operation names which the content provider has
// this class needs to have as well
// but the return of all those functions is the object itself, not a cursor
public interface IDataSource<T,U> extends ICreate<T>, IRead<T>, IUpdate<U>, IDelete<U>,
		IQuery<T>, ICustom<T> {

	void setOnlineServiceInvoker(IOnlineServiceInvoker<T> iOnlineServiceInvoker);

	void setConfig(IConfig iConfig);

}
