package org.lamtf._extdb1;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import org.lamtf.contract.db.IDbHelper;
import org.lamtf.contract.db.queries.ICreate;
import org.lamtf.contract.db.queries.ICustom;
import org.lamtf.contract.db.queries.IDelete;
import org.lamtf.contract.db.queries.IQuery;
import org.lamtf.contract.db.queries.IRead;
import org.lamtf.contract.db.queries.IUpdate;
import org.lamtf.contract.localdb.IGotDbHelper;

public class UserContentProvider extends ContentProvider
implements IGotDbHelper,
		ICreate<Uri>, IRead<Cursor>, IUpdate<Integer>, IDelete<Integer>,
		IQuery<Cursor>, ICustom<Cursor>
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
	public void setDbHelper(IDbHelper dbHelper) {

	}
}
