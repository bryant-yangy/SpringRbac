package com.westos.util;

import com.westos.domain.Module;
import com.westos.domain.Role;
import com.westos.service.ModuleService;
import com.westos.service.RoleService;
import com.westos.service.impl.ModuleServiceImpl;
import com.westos.service.impl.RoleServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
@Controller
public class UserUtils {
    @Autowired
    ModuleService moduleService;
    @Autowired
    RoleService roleService;

    public  void setAttributeModuleDao(HttpSession session, int id) {
        //查询所有权限
        List<Module> allModules = moduleService.findAll();
        //存放用户的拥有的所有权限id
        Set<Integer> userModules= new LinkedHashSet<>();
        //存放用户的所有父权限id(一级权限)
        Set<Integer> parentModules= new LinkedHashSet<>();
        //查询用户所有角色
        List<Role> roles = roleService.findByUserId(id);
        for (Role role : roles) {
            //该角色对应的权限
            List<Module> modules = moduleService.findByRoleId(role.getId());
            for (Module module : modules) {
                userModules.add(module.getId());
                parentModules.add(module.getPid());
            }
        }
         session.setAttribute("allModules", allModules);
         session.setAttribute("userModules", userModules);
         session.setAttribute("parentModules", parentModules);
    }

}
