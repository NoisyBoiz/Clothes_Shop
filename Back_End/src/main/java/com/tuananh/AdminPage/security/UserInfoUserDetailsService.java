package com.tuananh.AdminPage.security;

import com.tuananh.AdminPage.entities.TblAdminEntity;
import com.tuananh.AdminPage.entities.TblCustomerEntity;
import com.tuananh.AdminPage.repositories.AdminRepository;
import com.tuananh.AdminPage.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Component
@Service
public class UserInfoUserDetailsService implements UserDetailsService {
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private AdminRepository adminRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<TblCustomerEntity> customer = customerRepository.findByUsername(username);
        if(customer.isEmpty()) {
            Optional<TblAdminEntity> admin = adminRepository.findByUsername(username);
            return admin.map(UserInfoUserDetails::new)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found " + username));
        }
        return customer.map(UserInfoUserDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException("User not found " + username));

    }
}
