package socialdiet.imonger.com.socialdiet.architecture.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import socialdiet.imonger.com.socialdiet.R;

public abstract class BaseDao<T> extends SQLiteOpenHelper {

    private static Table table;
    private T instance;

    public BaseDao(Context context, T instance) {
        super(context, context.getResources().getString(R.string.DATABASE), null, context.getResources().getInteger(R.integer.dbVersion));
        this.instance = instance;
        table = new Table(instance);
    }

    @Override
    public final void onCreate(SQLiteDatabase sqLiteDatabase) {
        checkTableSettings();
        final StringBuilder sb = new StringBuilder("CREATE TABLE ");
        sb.append(table.getTableName()).append("(");
        boolean first = true;
        for (TableField f : table.getFields()) {
            if (!first) sb.append(", ");
            else first = false;
            sb.append(" ")
                    .append(f.getColumn())
                    .append(" ")
                    .append(f.getDbType())
                    .append(" ");
            if (f.isPK()) {
                sb.append(" PRIMARY KEY ");
                if (f.isAutoincrement()) {
                    sb.append(" AUTOINCREMENT ");
                }
            }
        }
        sb.append(")");
        Log.d(this.getClass().getName(), "Creating table:" + sb.toString());
        sqLiteDatabase.execSQL(sb.toString());

    }

    @Override
    public final void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        throw new UnsupportedOperationException(this.getClass().getName() + " must implement onUpgrade method.");
    }

    public final void insert(T object) throws DaoException {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values;
        try {
            values = getContentValues(object);
        } catch (IllegalAccessException e) {
            throw new DaoException(e, "Error getting values", object);
        }

        db.insert(table.getTableName(), null, values);
        db.close();
    }

    public final List<T> getAllValues() throws DaoException {
        final StringBuilder sb = getSelectAllFromTable();
        return executeSelect(sb.toString(), null);
    }

    public final T getById(T object) throws DaoException {
        Cursor c = null;
        final StringBuilder sb = getSelectAllFromTable();
        try {
            final SQLiteDatabase db = this.getReadableDatabase();
            sb.append(" WHERE ");
            sb.append(table.getPk().getColumn()).append(" = ?");
            c = db.rawQuery(sb.toString(), new String[]{table.getPk().getField().get(object).toString()});
            if (c != null) {
                c.moveToFirst();
                object = mapT(c);
            }
        } catch (IllegalAccessException e) {
            throw new DaoException(e, "Error mapping the result", sb, object);
        } catch (InstantiationException e) {
            throw new DaoException(e, "Error mapping the result", sb, object);
        } finally {
            if (c != null) c.close();
        }
        return object;
    }

    public final List<T> executeSelect(final String sql, final String... args) throws DaoException {
        final List<T> result = new ArrayList<>();
        final SQLiteDatabase db = this.getReadableDatabase();
        final Cursor cursor = db.rawQuery(sql, args);
        try {
            if (cursor.moveToFirst()) {
                do {
                    result.add(mapT(cursor));
                } while (cursor.moveToNext());
            }
        } catch (InstantiationException e) {
            throw new DaoException(e, "Error mapping the result", sql);
        } catch (IllegalAccessException e) {
            throw new DaoException(e, "Error mapping the result", sql);
        } finally {
            cursor.close();
        }
        return result;
    }

    private void checkTableSettings() {
        if (table.getTableName() == null)
            throw new UnsupportedOperationException(instance.getClass().getName() + " is not a table.");
        if (table.getFields() == null || table.getFields().size() == 0)
            throw new UnsupportedOperationException(instance.getClass().getName() + " does not have persistent fields.");
    }

    @NonNull
    private ContentValues getContentValues(T object) throws IllegalAccessException {
        Object value;
        ContentValues values = new ContentValues();
        for (TableField f : table.getFields()) {
            value = f.getField().get(object);
            if (value == null)
                values.putNull(f.getColumn());
            else if (value instanceof String) {
                values.put(f.getColumn(), (String) value);
            } else if (value instanceof Double) {
                values.put(f.getColumn(), (Double) value);
            } else if (value instanceof Long) {
                values.put(f.getColumn(), (Long) value);
            } else if (value instanceof Integer) {
                values.put(f.getColumn(), (Integer) value);
            }
        }
        return values;
    }

    private T mapT(Cursor cursor) throws InstantiationException, IllegalAccessException {
        T a = (T) instance.getClass().newInstance();
        int i = 0;
        for (TableField tf : table.getFields()) {
            Class<?> clazz = tf.getField().getType();
            if (clazz.isInstance("")) {
                tf.getField().set(a, cursor.getString(i++));
            } else if (clazz.isInstance(Long.MIN_VALUE)) {
                tf.getField().set(a, cursor.getLong(i++));
            } else if (clazz.isInstance(Double.MIN_NORMAL)) {
                tf.getField().set(a, cursor.getDouble(i++));
            }
        }
        return a;
    }

    @NonNull
    public final StringBuilder getSelectAllFromTable() {
        StringBuilder sb = new StringBuilder("SELECT ");
        boolean first = true;
        for (TableField f : table.getFields()) {
            if (!first) sb.append(", ");
            else first = false;
            sb.append(f.getColumn());
        }
        sb.append(" FROM ").append(table.getTableName());
        return sb;
    }
}