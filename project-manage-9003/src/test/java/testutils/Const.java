package testutils;

import com.jjyu.dao.UserBaseDao;
import com.jjyu.service.TeamService;
import com.jjyu.service.impl.TeamServiceImpl;
import com.jjyu.utils.UserRepoRole;
import com.jjyu.utils.UserTeamRole;
import org.springframework.beans.factory.annotation.Autowired;

public class Const {
    public static void main(String[] args) {
        TeamService ts = new TeamServiceImpl();
        /*System.out.println(UserRepoRole.ADMIN.equals("Admin"));
        System.out.println(UserRepoRole.CONTRIBUTOR);
        System.out.println(UserTeamRole.hasRole("ADmin"));*/
        ts.addMember("free","devinsba","member");
    }
}
