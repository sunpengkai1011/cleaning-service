package hottopic.mit.co.nz.cleaningservice.model.login;

public class UserLoginModel implements IUserLogin {
    private String username;
    private String password;

    public UserLoginModel(String username, String password){
        this.username = username;
        this.password = password;
    }
    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public boolean checkLoginVisible(String username, String password) {
        if (this.username.equals(username) && this.password.equals(password)){
            return true;
        }
        return false;
    }
}
