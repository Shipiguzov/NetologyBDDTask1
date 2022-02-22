package data;

public enum Users {
    USER1("vasya", "qwerty123");

    private final String login;
    private final String password;

    Users(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }
}
