package org.lamtf._extdb2;

import android.content.ContentProvider;

import org.lamtf.data.User;
import org.lamtf.db.IConfig;
import org.lamtf.db.IContentDataSource;

import java.util.List;

public class UserContentDataSource implements IContentDataSource<User, Integer>
{
	@Override
	public void setContentProvider(ContentProvider provider) {

	}

	@Override
	public void setConfig(IConfig iConfig) {

	}

	@Override
	public int delete(Integer that) {
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
	public Integer update(Integer that) {
		return null;
	}
}
