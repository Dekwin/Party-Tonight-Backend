import com.partymaker.mvc.admin.confirmation.mail.AdminService;
import com.partymaker.mvc.configuration.ApplicationContext;
import com.partymaker.mvc.model.admin.Admin;
import com.partymaker.mvc.model.whole.BillingEntity;
import com.partymaker.mvc.model.whole.RoleEntity;
import com.partymaker.mvc.model.whole.UserEntity;
import com.partymaker.mvc.service.event.EventService;
import com.partymaker.mvc.service.user.UserService;
import org.hibernate.SessionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.transaction.Transactional;

import static org.junit.Assert.assertNotNull;

/**
 * Created by anton on 05.03.17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ApplicationContext.class})
@WebAppConfiguration
public class TestAdmin {

    @Autowired
    UserService userService;

    @Autowired
    EventService eventService;

    @Autowired
    AdminService adminService;

    @Test
    public void testGetAllUsers() {
        assertNotNull(userService.findAllUsers());
    }

    @Test
    public void testGetAllEvents() {
        assertNotNull(eventService.findAll());
    }

    @Test
    public void testHoldUserById() {
        userService.userLock(6);
    }

    public void testUnHoldUserById() {
        userService.userUnLock(6);
    }

    @Test
    public void testDeleteUser() {

        UserEntity u = new UserEntity();
        u.setEmail("testDelete");
        u.setRole(new RoleEntity(1, "PARTY_MAKER"));

        /* a little  hard code it will be uncommented */
        BillingEntity billing = new BillingEntity("dancer" + System.currentTimeMillis());
        u.setBilling(billing);
        userService.createUser(u);


        userService.deleteUser(((UserEntity) userService.findUserByEmail(u.getEmail())).getId_user());
    }


    @Autowired
    SessionFactory sessionFactory;


    @Test
    @Transactional
    public void testCreateAdmin() {
        Admin admin = new Admin();

        admin.setAccountEmail("gmail.comc");
        admin.setEmailUserName("usecr");
        admin.setEmailUserPassword("passwcord");

        adminService.saveAdminEntity(admin);

        sessionFactory.getCurrentSession().delete(admin);
    }

    @Test
    @Transactional
    public void testGetAdmin(){
        Admin admin = new Admin();

        admin.setAccountEmail("gmail.comc");
        admin.setEmailUserName("usecr");
        admin.setEmailUserPassword("passwcord");

        adminService.saveAdminEntity(admin);
        System.out.println(adminService.getAdminEntity(1));
        sessionFactory.getCurrentSession().delete(admin);

    }
}
