package org.lamtf.db.remote.offline;

/*
 It's used to update the database when needed
 It uses the local android Database,
*/
public interface IOfflineUpdater {

	void add(IOffLineHelper offlineHelper);

	void update();
}
