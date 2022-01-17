package testutils;

import com.jjyu.utils.UserRepoRole;
import com.jjyu.utils.UserTeamRole;

public class Const {
    public static void main(String[] args) {
        System.out.println(UserRepoRole.ADMIN.equals("Admin"));
        System.out.println(UserRepoRole.CONTRIBUTOR);
        System.out.println(UserTeamRole.hasRole("ADmin"));
    }
}
