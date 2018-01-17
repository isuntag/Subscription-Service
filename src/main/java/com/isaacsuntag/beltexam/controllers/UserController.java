package com.isaacsuntag.beltexam.controllers;

import java.security.Principal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.isaacsuntag.beltexam.auth.validator.UserValidator;
import com.isaacsuntag.beltexam.models.APackage;
import com.isaacsuntag.beltexam.models.User;
import com.isaacsuntag.beltexam.services.APackageService;
import com.isaacsuntag.beltexam.services.RoleService;
import com.isaacsuntag.beltexam.services.UserService;

@Controller
public class UserController {
	private UserService userService;
	private RoleService roleService;
	private APackageService apackageService;
	private UserValidator userValidator;
    
    public UserController(UserService userService, UserValidator userValidator, RoleService roleService, APackageService apackageService) {
    		this.userService = userService;
    		this.userValidator = userValidator;
    		this.roleService = roleService;
    		this.apackageService = apackageService;
    }
	
	@RequestMapping("/login")
    public String login(@Valid @ModelAttribute("user") User user, @RequestParam(value="error", required=false) String error, @RequestParam(value="logout", required=false) String logout, Model model) {
        if(error != null) {
            model.addAttribute("errorMessage", "Invalid Credentials, Please try again.");
        }
        if(logout != null) {
            model.addAttribute("logoutMessage", "Logout Successfull!");
        }
        return "loginReg.jsp";
    }
	
	@PostMapping("/registration")
    public String registration(@Valid @ModelAttribute("user") User user, BindingResult result, Model model, HttpSession session) {
		userValidator.validate(user, result);
		if (result.hasErrors()) {
			return "loginReg.jsp";
		}
		 else if(roleService.findByName("ROLE_ADMIN").getUsers().size() < 1) {
			userService.saveUserWithAdminRole(user);
		}
		else {
			userService.saveWithUserRole(user);
		}
		session.setAttribute("created", "Account created. Please log in.");
		return "redirect:/login";
    }
	
	@RequestMapping("/loggedin")
	public String updatedLoggedIn(Principal principal) {
		String email = principal.getName();
		User user = userService.findByEmail(email);
        if(user.checkIfAdmin()) {
        		return "redirect:/admin";
        }
        else {
        		if(user.getApackage() != null) {
        			return "redirect:/dashboard";
        		}
        		return "redirect:/dashboard";
        }
	}
	
	@RequestMapping("/selection")
	public String select(Principal principal, Model model) {
		String email = principal.getName();
        model.addAttribute("currentUser", userService.findByEmail(email));
        List<APackage> packages = apackageService.getAllPackages();
        model.addAttribute("packages", packages);
        boolean activeflag = false;
        for(int i=0; i<packages.size(); i++) {
        		if(packages.get(i).isActive()) {
        			activeflag = true;
			}
        }
        if(!activeflag) {
        		model.addAttribute("active", false);
        } else {
        		model.addAttribute("active", true);
        }
        return "select.jsp";
	}

	@PostMapping("/users/{id}")
	public String addProduct(@PathVariable("id") Long id, @RequestParam("apackage") Long apackageId, @RequestParam("due") int dueDate, Principal principal, Model model) {
		if(dueDate < 1 || dueDate > 31) {
			model.addAttribute("errors", "Due date must be between 1 and 31.");
			String email = principal.getName();
	        model.addAttribute("currentUser", userService.findByEmail(email));
	        model.addAttribute("packages", apackageService.getAllPackages());
	        return "select.jsp"; 
		}
		if(apackageId == null) {
			model.addAttribute("errors", "You must select a package.");
			String email = principal.getName();
	        model.addAttribute("currentUser", userService.findByEmail(email));
	        model.addAttribute("packages", apackageService.getAllPackages());
	        return "select.jsp"; 
		}
		// To get last day of the month
        Calendar lastDayOfMonth = Calendar.getInstance();
        lastDayOfMonth.set(Calendar.HOUR_OF_DAY, 0);
        lastDayOfMonth.add(Calendar.MONTH, 1);
        lastDayOfMonth.set(Calendar.DAY_OF_MONTH, 1);
        lastDayOfMonth.add(Calendar.DATE, -1);
        // To get date for today
        Calendar todayDate = Calendar.getInstance();
        todayDate.set(Calendar.HOUR_OF_DAY, 0);
        // To get date for next month
        Calendar nextMonth = Calendar.getInstance(); 
        nextMonth.add(Calendar.MONTH, 1);
        nextMonth.set(Calendar.DAY_OF_MONTH, dueDate);
        nextMonth.set(Calendar.HOUR_OF_DAY, 0);
        // To get date for this month
        Calendar thisMonth = Calendar.getInstance(); 
        thisMonth.set(Calendar.DAY_OF_MONTH, dueDate);
        thisMonth.set(Calendar.HOUR_OF_DAY, 0);
		String email = principal.getName();
		User user = userService.findByEmail(email);
		APackage apackage = apackageService.getOne(apackageId);
		user.setApackage(apackage);
		if(dueDate > lastDayOfMonth.get(Calendar.DAY_OF_MONTH)) {
			user.setDue_date(new Date(lastDayOfMonth.getTimeInMillis()));
		} else if(dueDate < todayDate.get(Calendar.DAY_OF_MONTH)) {
			user.setDue_date(new Date(nextMonth.getTimeInMillis()));
		} else {
			user.setDue_date(new Date(thisMonth.getTimeInMillis()));
		}
		userService.update(user);
		return "redirect:/dashboard";
	}
	
	@RequestMapping("/dashboard")
    public String home(Principal principal, Model model) {
        String email = principal.getName();
        model.addAttribute("currentUser", userService.findByEmail(email));
        return "dashboard.jsp";
    }
	@RequestMapping("/admin")
    public String adminPage(Principal principal, Model model, @ModelAttribute("apackage") APackage apackage) {
        String email = principal.getName();
        model.addAttribute("currentUser", userService.findByEmail(email));
        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("packages", apackageService.getAllPackages());
        return "adminPage.jsp";
    }
}

