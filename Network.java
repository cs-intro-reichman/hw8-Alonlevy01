/** Represents a social network. The network has users, who follow other uesrs.
 *  Each user is an instance of the User class. */
public class Network {

    // Fields
    private User[] users;  // the users in this network (an array of User objects)
    private int userCount; // actual number of users in this network

    /** Creates a network with a given maximum number of users. */
    public Network(int maxUserCount) {
        this.users = new User[maxUserCount];
        this.userCount = 0;
    }

    /** Creates a network  with some users. The only purpose of this constructor is 
     *  to allow testing the toString and getUser methods, before implementing other methods. */
    public Network(int maxUserCount, boolean gettingStarted) {
        this(maxUserCount);
        users[0] = new User("Foo");
        users[1] = new User("Bar");
        users[2] = new User("Baz");
        userCount = 3;
    }

    /** Finds in this network, and returns, the user that has the given name.
     *  If there is no such user, returns null.
     *  Notice that the method receives a String, and returns a User object. */
    public User getUser(String name) {
        int i = 0;

        while ((users[i] != null) && (!users[i].getName().equalsIgnoreCase(name))) {
            i++;
        }

        if (users[i] != null ) {
            return users[i];
        }
        return null;
    }

    public int getUserCount() {
        return this.userCount;
    }

    /** Adds a new user with the given name to this network.
    *  If ths network is full, does nothing and returns false;
    *  If the given name is already a user in this network, does nothing and returns false;
    *  Otherwise, creates a new user with the given name, adds the user to this network, and returns true. */
    public boolean addUser(String name) {
        
        if (this.userCount == this.users.length) return false;

        for (int i = 0; i < this.userCount; i++) {
            if (this.users[i].getName().equalsIgnoreCase(name)) {
                return false;
            }
        }

        this.users[this.userCount] = new User(name);
        this.userCount++;
        return true;
    }

    /** Makes the user with name1 follow the user with name2. If successful, returns true.
     *  If any of the two names is not a user in this network,
     *  or if the "follows" addition failed for some reason, returns false. */
    public boolean addFollowee(String name1, String name2) {
        int i = -1;
        int j = -1;
        
        // Finds the users
        for (int k = 0; k < 10; k++) {
            // Finds first user.
            if (this.users[k].getName().equalsIgnoreCase(name1)) {
                i = k;
            }
            // Finds second user.
            if (this.users[k].getName().equalsIgnoreCase(name2)) {
                j = k;
            }
            // Breaks if found both names for efficiency.
            if ((j != -1) && (i != -1)) break;
        }
        
        // False if one or both are not in the network.
        if ((i == -1) || (j == -1)) return false;

        // does the follows
        return this.users[i].addFollowee(name2);

    }
    
    /** For the user with the given name, recommends another user to follow. The recommended user is
     *  the user that has the maximal mutual number of followees as the user with the given name. */
    public String recommendWhoToFollow(String name) {
        int max = -1;
        int i = 0;
        int position = 0;
        User thisUser = getUser(name);

        while (i < this.userCount) {
            // Dont compare user to himself
            if (users[i].getName().equalsIgnoreCase(name)) {
                i++;
            } 

            // find the max
            else {
                    if  (max < thisUser.countMutual(users[i])) {
                        max = thisUser.countMutual(users[i]);
                        position = i;
                    }
                    i++;
            }
        }
        return users[position].getName();
    }

    /** Computes and returns the name of the most popular user in this network: 
     *  The user who appears the most in the follow lists of all the users. */
    public String mostPopularUser() {
        int counter = 0;
        int max = 0;
        int position = 0;

        for (int i = 0; i < this.userCount; i++) {
            counter = 0;
            for (int j = 0; j < this.userCount; j++) {
                if (users[j].follows(users[i].getName())) {
                    counter++;
                }
            }
            if (counter > max) {
                max = counter;
                position = i;
            }
        }
        return users[position].getName();
    }

    /** Returns the number of times that the given name appears in the follows lists of all
     *  the users in this network. Note: A name can appear 0 or 1 times in each list. */
    private int followeeCount(String name) {
        int counter = 0;

        for (int i = 0; i < this.userCount; i++) {
            if (this.users[i].follows(name)) counter++;
        }
        return counter;
    }

    // Returns a textual description of all the users in this network, and who they follow.
    public String toString() {
        String networkString = "";
        for (int i = 0; i < this.userCount; i++) {
            networkString = networkString + " " + users[i].toString() + "\n";
        }
       return networkString;
    }
}
