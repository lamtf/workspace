package org.lamtf._localdb;

import org.lamtf.data.User;
import org.lamtf.db.IConfig;
import org.lamtf.db.IDataSource;
import org.lamtf.db.local.IDbHelper;
import org.lamtf.db.local.IDirectDataAccess;
import org.lamtf.db.remote.IRemoteDataSource;

import java.util.List;

// The same operation names which the content provider has
// this class needs to have as well
// but the return of all those functions is the object itself, not a cursor
public class UserDirectDataSource<T>
implements IDirectDataAccess, IDataSource<User,Long>
{
	@Override
	public void setConfig(IConfig iConfig) {

	}

	@Override
	public void setDbHelper(IDbHelper dbHelper) {

	}

	@Override
	public void setRemoteDataSource(IRemoteDataSource onlineDataSource) {

	}

	@Override
	public int delete(Long that) {
		return 0;
	}

	@Override
	public User findOne(long Id) {
		return null;
	}

	@Override
	public List<User> find(long pn, long ps) {
		return null;
	}

	@Override
	public Long update(Long that) {
		return null;
	}
}
