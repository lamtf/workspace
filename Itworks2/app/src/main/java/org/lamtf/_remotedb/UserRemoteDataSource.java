package org.lamtf._remotedb;

import org.lamtf.data.User;
import org.lamtf.db.IConfig;
import org.lamtf.db.remote.IRemoteDataSource;

import java.util.List;

public class UserRemoteDataSource
		implements IRemoteDataSource<User, Long> {

	@Override
	public void setConfig(IConfig iConfig) {

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
