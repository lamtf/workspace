package org.lamtf.contract.remotedb;

import org.lamtf.contract.db.IDbHelper;

/*
 It's used to update the database when needed
 It uses the local android Database,

 It updates the remote database only

 And its responsible for updating a single table T
*/
public interface IOffLineHelper<T> {

	void setDbHelper(IDbHelper dbHelper);

	void insert_on_server();

	void update_on_server();

}
