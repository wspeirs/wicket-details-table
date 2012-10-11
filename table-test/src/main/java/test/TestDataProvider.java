package test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.wicket.extensions.markup.html.repeater.util.SortableDataProvider;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

public class TestDataProvider extends SortableDataProvider<User, String> {

    private static final long serialVersionUID = 1L;
    private static List<User> userList = new ArrayList<User>();
    
    static {
        userList.add(new User("Joe", "Tester"));
        userList.add(new User("Sally", "Tester"));
    }
    
    @Override
    public Iterator<? extends User> iterator(long first, long count) {
        return userList.iterator();
    }

    @Override
    public long size() {
        return userList.size() * 2;
    }

    @Override
    public IModel<User> model(User user) {
        return Model.of(user);
    }

}
