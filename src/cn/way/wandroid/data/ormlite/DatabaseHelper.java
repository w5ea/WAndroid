package cn.way.wandroid.data.ormlite;

import java.sql.SQLException;
import java.util.HashMap;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

/**
 * Database helper class used to manage the creation and upgrading of your database. This class also usually provides
 * the DAOs used by the other classes.
 */
public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

	// name of the database file for your application -- change to something appropriate for your app
	private static final String DATABASE_NAME = "cn.way.wandroid.db";
	// any time you make changes to your database objects, you may have to increase the database version
	private static final int DATABASE_VERSION = 1;

	// the DAO object we use to access the SimpleData table
	protected HashMap<Class<?extends Object>, Dao<?,?>> daos = new HashMap<Class<? extends Object>, Dao<?,?>>();
	protected DatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
	protected static DatabaseHelper instance;
	public static DatabaseHelper instance(Context context){
		if (instance==null) {
			instance = OpenHelperManager.getHelper(context, DatabaseHelper.class);
		}
		return instance;
	}
	public static void releaseHelper(){
		OpenHelperManager.releaseHelper();
		instance = null;
	}
	/**
	 * This is called when the database is first created. Usually you should call createTable statements here to create
	 * the tables that will store your data.
	 */
	@Override
	public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource) {
		
		
	}

	/**
	 * This is called when your application is upgraded and it has a higher version number. This allows you to adjust
	 * the various data to match the new version number.
	 */
	@Override
	public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource, int oldVersion, int newVersion) {
		try {
			Log.i(DatabaseHelper.class.getName(), "onUpgrade");
			for (Dao<?,?> dao : daos.values()) {
				TableUtils.dropTable(connectionSource, dao.getClass(), true);
				onCreate(db, connectionSource);
			}
		} catch (SQLException e) {
			Log.e(DatabaseHelper.class.getName(), "Can't drop databases", e);
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * Close the database connections and clear any cached DAOs.
	 */
	@Override
	public void close() {
		super.close();
		daos.clear();
	}
	
	@SuppressWarnings("unchecked")
	public <T>Dao<?,?> mGetDao(Class<T> clazz) throws SQLException{
		Dao<T, ?> dao = null;
		if (!daos.containsKey(clazz)) {
			dao = super.getDao(clazz);
		}else{
			dao = (Dao<T, ?>) daos.get(clazz);
		}
		return dao;
	}
}
