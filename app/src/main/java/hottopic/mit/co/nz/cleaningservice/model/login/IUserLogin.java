package hottopic.mit.co.nz.cleaningservice.model.login;

public interface IUserLogin {
    String getUsername();
    String getPassword();
    boolean checkLoginVisible(String username,String password);
}
