package com.hotel.royalpalace.service.impl;

import com.hotel.royalpalace.auxiliary.Encryption;
import com.hotel.royalpalace.auxiliary.SmtpMailSender;
import com.hotel.royalpalace.model.User;
import com.hotel.royalpalace.model.enums.Roles;
import com.hotel.royalpalace.model.info.UserInfo;
import com.hotel.royalpalace.repository.UserRepository;
import com.hotel.royalpalace.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    UserRepository userRepository;

    @Autowired
    SmtpMailSender smtpMailSender;

    private User usr = null;

    private static final String ALPHA_CAPS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String ALPHA = "abcdefghijklmnopqrstuvwxyz";
    private static final String NUMERIC = "0123456789";

    @Override
    @Transactional(readOnly = true)
    public User getByUserEmail(String email) {
        return userRepository.getByUserEmail(email);
    }

    @Override
    public void createUser(UserInfo userInfo) throws NoSuchAlgorithmException {

        String pass = generatePassword(6, ALPHA_CAPS + ALPHA + NUMERIC);
        userInfo.setPassword(Encryption.computeMD5(pass));
        User user = new User(userInfo);

        String message = "Dear " + user.getFirstName() + "<br/><br/>"
                + "A new account has been created for you on Royal Palace Hotel. <br/><br/>"
                + "Your credentials are: <br/> Email: " + user.getUserEmail() + "<br/>"
                + "Password: " + pass + "<br/><br/> Please change your password at first use!";
        try {
            usr = userRepository.save(user);
            smtpMailSender.sendToSingle(user.getUserEmail(), "New Account", message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void saveUser(User user) {
        userRepository.save(user);
    }

    @Override
    public void editUser(UserInfo userInfo) {

        User user = this.getByUserEmail(userInfo.getUserEmail());

        user.setBirthDate(userInfo.getBirthDate());
        user.setFirstName(userInfo.getFirstName());
        user.setLastName(userInfo.getLastName());
        user.setPhone(userInfo.getPhone());

        try {
            usr = userRepository.save(user);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void changePassword(User user, String newPassword) throws NoSuchAlgorithmException {

        user.setUserPassword(Encryption.computeMD5(newPassword));

        try {
            usr = userRepository.save(user);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public String redirect() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        authentication.getAuthorities();
        String email;
        if (authentication.getPrincipal() instanceof User) {
            email = ((User) authentication.getPrincipal()).getUserEmail();
        } else {
            email = authentication.getName();
        }
        if (!email.equals("anonymousUser")) {
            User user = getByUserEmail(email);

            if (user.getUserRole().getName().equals(Roles.MANAGER.getDescription())) {
                return "redirect:/manager";
            } else if (user.getUserRole().getName().equals(Roles.RECEPTIONIST.getDescription())) {
                return "redirect:/receptionist";
            } else if (user.getUserRole().getName().equals(Roles.MAID.getDescription())) {
                return "redirect:/maid";
            }
        }

        return "index";
    }

    private static String generatePassword(int len, String dic) {
        SecureRandom random = new SecureRandom();
        String result = "";
        for (int i = 0; i < len; i++) {
            int index = random.nextInt(dic.length());
            result += dic.charAt(index);
        }
        return result;
    }
}
