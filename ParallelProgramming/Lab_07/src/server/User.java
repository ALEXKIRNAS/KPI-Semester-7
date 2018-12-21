package server;

public class User {
    private String name;
    private boolean isOnline;

    User(String name) {
        this.name = name;
        isOnline = true;
    }

    public void setIsOnline() {
        isOnline = true;
    }

    public boolean getIsOnline() {
        return isOnline;
    }

    public String getName() {
        return name;
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof User)) {
            return false;
        }

        User user = (User) o;

        return user.name.equals(name);
    }
}
