package org.lamtf.db;

import org.lamtf.db.query.ICreate;
import org.lamtf.db.query.ICustom;
import org.lamtf.db.query.IDelete;
import org.lamtf.db.query.IQV;
import org.lamtf.db.query.IRead;
import org.lamtf.db.query.IUpdate;

// The same operation names which the content provider has
// this class needs to have as well
// but the return of all those functions is the object itself, not a cursor
public interface IDataSource<T,U> extends ICreate<T>, IRead<T>, IUpdate<U>, IDelete<U>,
		IQV<T>, ICustom<T> {

  void setConfig(IConfig iConfig);

}
