package facesnap.emazdoor.com.facesnap;

/**
 * Created by Ahmed on 10/5/2017.
 */

import javax.inject.Inject;

public class User {

    //using ButterKnife instead!
    private String firstName;
    private String lastName;

    public User(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    @Override
    public String toString() {
        return "User [firstName=" + firstName + ", lastName=" + lastName + "]";
    }
}