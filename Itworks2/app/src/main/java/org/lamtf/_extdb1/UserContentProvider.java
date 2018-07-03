package org.lamtf._extdb1;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import org.lamtf.db.IConfig;
import org.lamtf.db.IDataSource;
import org.lamtf.db.local.IDbHelper;
import org.lamtf.db.local.IDirectDataAccess;
import org.lamtf.db.remote.IRemoteDataSource;

import java.util.List;

public class UserContentProvider extends ContentProvider
implements IDirectDataAccess,
		IDataSource<Cursor, Uri>
{
	@Override
	public boolean onCreate() {
		return false;
	}

	@Nullable
	@Override
	public Cursor query(@NonNull Uri uri, @Nullable String[] strings, @Nullable String s, @Nullable String[] strings1, @Nullable String s1) {
		return null;
	}

	@Nullable
	@Override
	public String getType(@NonNull Uri uri) {
		return null;
	}

	@Nullable
	@Override
	public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
		return null;
	}

	@Override
	public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
		return 0;
	}

	@Override
	public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
		return 0;
	}

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
	public int delete(Uri that) {
		return 0;
	}

	@Override
	public Cursor findOne(long Id) {
		return null;
	}

	@Override
	public List<Cursor> find(long pn, long ps) {
		return null;
	}

	@Override
	public Uri update(Uri that) {
		return null;
	}
}
