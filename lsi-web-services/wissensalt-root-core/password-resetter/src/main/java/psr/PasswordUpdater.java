package psr;

import com.wissensalt.scaffolding.exception.ServiceException;
import com.wissensalt.scaffolding.service.core.IUserService;
import com.wissensalt.shared.entity.security.SecurityUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created on 12/1/18.
 *
 * @author <a href="mailto:fauzi.knightmaster.achmad@gmail.com">Achmad Fauzi</a>
 */
@Service
public class PasswordUpdater {

    @Autowired
    private IUserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    public void updatePassword() {
        try {
            List<SecurityUser> securityUsers = userService.findAllDATAVolunteer();
            System.out.println("Found "+securityUsers.size()+" users");
            for (SecurityUser user : securityUsers) {
                user.setPassword(passwordEncoder.encode(user.getUsername()));
                userService.update(user);
                System.out.println("Updated "+user.getUsername());
            }
        } catch (ServiceException e) {
            e.printStackTrace();
        }
    }

    public void matcher() {
        try {
            List<SecurityUser> securityUsers = userService.findAllDATAVolunteer();
            System.out.println("Found "+securityUsers.size()+" users");
            for (SecurityUser user : securityUsers) {
                if (passwordEncoder.matches(user.getUsername(), user.getPassword())) {
                    System.out.println("MATCH");
                }else {
                    System.out.println("NOT MATCH");
                }
            }
        }catch (ServiceException e) {
            e.printStackTrace();
        }
    }

    @Transactional
    public void updateSingle() {
        try {
            SecurityUser user = userService.findByCode("adminbanten007");
            user.setCode(user.getCode().toLowerCase());
            user.setPassword(passwordEncoder.encode(user.getUsername()));
            userService.update(user);
        } catch (ServiceException e) {
            e.printStackTrace();
        }
    }
}
