package Models;

public class UserMoldel {
    private int ID;
    private String userName;
    private String password;

    public UserMoldel(int iD, String i, String j) {
        ID = iD;
        this.userName = i;
        this.password = j;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
