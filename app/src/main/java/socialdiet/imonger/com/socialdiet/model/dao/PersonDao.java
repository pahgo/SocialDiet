package socialdiet.imonger.com.socialdiet.model.dao;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import socialdiet.imonger.com.socialdiet.architecture.dao.BaseDao;
import socialdiet.imonger.com.socialdiet.architecture.dao.DaoException;
import socialdiet.imonger.com.socialdiet.model.dto.Person;

/**
 * Created by Pahgo on 28/08/2016.
 */
public class PersonDao extends BaseDao<Person> {

    public PersonDao(Context context) {
        super(context, new Person());
    }

    public List<Person> getByUserName(String userName) {
        final StringBuilder sb = getSelectAllFromTable();
        sb.append(" WHERE USER_NAME = ?");
        List<Person> result = new ArrayList<>();
        try {
            result = executeSelect(sb.toString(), userName);
        } catch (DaoException e) {
            e.printStackTrace();
        }
        return result;
    }


}
