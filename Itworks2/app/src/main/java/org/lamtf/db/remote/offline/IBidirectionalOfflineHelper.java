package org.lamtf.db.remote.offline;

/*
 It's used to update the database when needed
 It uses the local android Database,

 It updates the local and the remote databases

 And its responsible for updating a single table T
*/
public interface IBidirectionalOfflineHelper<T> extends IOffLineHelper<T> {

	void insert_on_client();

	void update_on_client();

	void fix_foreign_keys_on_client();

}
