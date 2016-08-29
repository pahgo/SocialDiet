package socialdiet.imonger.com.socialdiet.architecture.dao;

/**
 * Created by Francisco on 29/08/2016.
 */
public class Example {
    private static Example ourInstance = new Example();

    private Example() {
    }

    public static Example getInstance() {
        return ourInstance;
    }
}
