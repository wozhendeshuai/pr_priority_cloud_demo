package testutils;

import com.jjyu.service.TeamService;
import com.jjyu.service.impl.TeamServiceImpl;

public class Const {
    public static void main(String[] args) {
        TeamService ts = new TeamServiceImpl();
        /*System.out.println(UserRepoRole.ADMIN.equals("Admin"));
        System.out.println(UserRepoRole.CONTRIBUTOR);
        System.out.println(UserTeamRole.hasRole("ADmin"));*/
        ts.addMember("free","devinsba","member");
    }
}
