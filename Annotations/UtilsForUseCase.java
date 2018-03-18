// password management

import java.util.*;

public class UtilsForUseCase {
    @UseCase(id = 47, description = "password must contain at least one numeric")
    public boolean validatePassword(String pwd) {
        return (pwd.matches("\\w*\\d\\w*"));
    }

    @UseCase(id = 48)
    public String encryptPassword(String pwd) {
        return new StringBuilder(pwd).reverse().toString();
    }

    @UseCase(id = 49, description = "New passwords can't equal to preivously used ones")
    public boolean checkForNewPassword(List<String> prevPwds, String pwd) {
        return !prevPwds.contains(pwd);
    }
}
