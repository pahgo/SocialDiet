package socialdiet.imonger.com.socialdiet.model.dto;


import socialdiet.imonger.com.socialdiet.architecture.annotations.DataTableObject;
import socialdiet.imonger.com.socialdiet.architecture.annotations.PersistentField;

/**
 * Created by Francisco on 28/08/2016.
 */
@DataTableObject(table = "PERSON")
public class Person {

    @PersistentField(id = true, column = "ID", dbType = "INTEGER", autoincrement = true)
    private Long id;

    @PersistentField(column = "USER_NAME", length = 30)
    private String userName;

    @PersistentField(column = "WEIGHT", dbType = "DECIMAL(4,2)")
    private Double weight;


    public Person() {/*Empty on purpose*/}

    public Person(Long id, String userName, Double weight) {
        this.id = id;
        this.userName = userName;
        this.weight = weight;
    }

    public Person(Long id) {
        this.id = id;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder(this.getClass().getName());
        sb.append(":{");
        sb.append("id: ").append(id);
        sb.append(", userName: ").append(userName);
        sb.append(", weight: ").append(weight);
        sb.append("}");
        return sb.toString();
    }
}
