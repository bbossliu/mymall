package com.atguigu.gmall.config.service;

import com.atguigu.gmall.entity.TbPermission;
import com.atguigu.gmall.entity.TbUser;
import com.atguigu.gmall.user.service.TbPermissionService;
import com.atguigu.gmall.user.service.TbUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author dalaoban
 * @create 2020-02-13-15:37
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    TbUserService tbUserService;

    @Autowired
    TbPermissionService tbPermissionService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        TbUser tbUser = new TbUser();
        tbUser.setUsername(username);
        List<TbUser> tbUsers = tbUserService.queryAll(tbUser);
        List<GrantedAuthority> grantedAuthorities= new ArrayList<>();
        if(! tbUsers.isEmpty() && tbUser!=null ){
            TbUser user=tbUsers.get(0);

            // 获取用户授权
            List<TbPermission> tbPermissions = tbPermissionService.permissionByUserId(user.getId());

            if(!tbPermissions.isEmpty() && tbPermissions!=null){
                tbPermissions.forEach(tbPermission -> {
                    //声明授权
                    GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(tbPermission.getEnname());
                    grantedAuthorities.add(grantedAuthority);
                });
            }
            return new User(user.getUsername(), user.getPassword(), grantedAuthorities);
        }
        return null;
    }
}
