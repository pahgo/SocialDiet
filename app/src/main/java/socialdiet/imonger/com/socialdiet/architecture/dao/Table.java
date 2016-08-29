package socialdiet.imonger.com.socialdiet.architecture.dao;

import android.support.annotation.Nullable;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import socialdiet.imonger.com.socialdiet.architecture.annotations.DataTableObject;
import socialdiet.imonger.com.socialdiet.architecture.annotations.PersistentField;

/**
 * Created by Francisco on 29/08/2016.
 */
public class Table {

    private String tableName;
    private List<TableField> fields;
    private TableField pk;

    public Table(Object instance) {
        calculateTableName(instance);
        calculateFields(instance);
    }

    public List<TableField> getFields() {
        return fields;
    }

    public String getTableName() {
        return tableName;
    }

    public TableField getPk() {
        return pk;
    }

    private void calculateFields(Object instance) {
        fields = new ArrayList<>();
        PersistentField pf;
        for (Field f : instance.getClass().getDeclaredFields()) {
            for (Annotation a : f.getDeclaredAnnotations()) {
                if (a instanceof PersistentField) {
                    pf = (PersistentField) a;
                    fields.add(new TableField(f, pf.column(), pf.dbType(), pf.id(), pf.autoincrement()));
                    if (pf.id())
                        pk = new TableField(f, pf.column(), pf.dbType(), pf.id(), pf.autoincrement());
                }
            }
        }
    }

    @Nullable
    private String calculateTableName(Object instance) {
        List<Annotation> annotations = Arrays.asList(instance.getClass().getDeclaredAnnotations());
        DataTableObject dto;
        for (Annotation a : annotations) {
            if (a instanceof DataTableObject) {
                dto = (DataTableObject) a;
                tableName = dto.table();
            }
        }
        return tableName;
    }


}
