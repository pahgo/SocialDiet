package socialdiet.imonger.com.socialdiet.architecture.dao;

import java.lang.reflect.Field;

/**
 * Created by Francisco on 29/08/2016.
 */
public class TableField {

    private Field field;
    private String column;
    private String dbType;
    private boolean isPK;
    private boolean autoincrement;

    public TableField(Field field, String column, String dbType, boolean isPK, boolean autoincrement) {
        field.setAccessible(true);
        this.field = field;
        this.column = column;
        this.dbType = dbType;
        this.isPK = isPK;
        this.autoincrement = autoincrement;
    }

    public TableField(Field field, String column, String dbType) {
        field.setAccessible(true);
        this.field = field;
        this.column = column;
        this.dbType = dbType;
        this.isPK = false;
    }

    public TableField() {
    }

    public Field getField() {
        return field;
    }

    public String getColumn() {
        return column;
    }

    public String getDbType() {
        return dbType;
    }

    public boolean isPK() {
        return isPK;
    }

    public boolean isAutoincrement() {
        return autoincrement;
    }
}
