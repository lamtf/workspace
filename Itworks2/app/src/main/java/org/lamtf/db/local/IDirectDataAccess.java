package org.lamtf.db.local;

import org.lamtf.db.remote.IRemoteDataSource;

public interface IDirectDataAccess {

	public void setDbHelper(IDbHelper dbHelper);

	public void setRemoteDataSource(IRemoteDataSource onlineDataSource);

}
