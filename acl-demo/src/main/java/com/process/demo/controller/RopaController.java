package com.process.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.acls.domain.BasePermission;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import com.process.demo.model.Ropa;
import com.process.demo.model.User;
import com.process.demo.repository.RopaRepository;
import com.process.demo.repository.UserRepository;
import com.process.demo.service.CustomService;
import com.process.demo.service.CustomServiceImpl;
import com.process.demo.service.LocalPermissionService;

import java.util.List;

import javax.validation.Valid;

@Controller
@RequestMapping(value = "/ropa")
public class RopaController {

    @Autowired
    private RopaRepository ropaRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LocalPermissionService permissionService;
    
    @Autowired
    private CustomServiceImpl CustomServiceImpl;
    // API
    
    @RequestMapping
    public ModelAndView list() {
        Iterable<Ropa> ropas = this.ropaRepository.findAll();
        return new ModelAndView("tl/ropas", "ropas", ropas);
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    @PostAuthorize("hasPermission(returnObject, 'READ') or hasPermission(returnObject, 'ADMINISTRATION')")
    public Ropa findOne(@PathVariable("id") final Long id) {
        return ropaRepository.findOne(id);
    }
    
    @PreAuthorize("hasPermission(#ropa, 'WRITE') or hasPermission(#ropa, 'ADMINISTRATION')")
    @RequestMapping(value = "modify/{id}", method = RequestMethod.GET)
    public ModelAndView modifyForm(@PathVariable("id") final Ropa ropa) {
        return new ModelAndView("tl/edit", "ropa", ropa);
    }
    
    @RequestMapping(value = "/delete/{id}")
    public ModelAndView delete(@PathVariable("id") final Long id) {
    	Ropa deletedRopa = ropaRepository.findOne(id);
    	if(CustomServiceImpl.delete(deletedRopa)) {
    		ropaRepository.delete(deletedRopa);
    	}
        return new ModelAndView("redirect:/ropa");
    }
    
//    @PreAuthorize("hasPermission(#ropa, 'ADMINISTRATION')")
//    @RequestMapping(value="/deleteall", method=RequestMethod.DELETE)
//    public void deleteAll() {
//    		ropaRepository.deleteAll();
//    }
	
    @RequestMapping(method = RequestMethod.POST)
    public ModelAndView create(@Valid Ropa ropa, Authentication authentication) {
    	ropa.setOwner(userRepository.findByEmail(authentication.getName()));
    	ropa = ropaRepository.save(ropa);
        System.out.println(ropa);
        // permissionService.addPermissionForAuthority(ropa, BasePermission.ADMINISTRATION, "ADMIN");
        permissionService.addPermissionForUser(ropa, BasePermission.ADMINISTRATION, authentication.getName());
        return new ModelAndView("redirect:/ropa?message=Ropa with id " + ropa.getId());
    }

    //

    @RequestMapping(params = "form", method = RequestMethod.GET)
    public String createForm(@ModelAttribute final Ropa ropa) {
        return "tl/ropa";
    }

}