package com.isaacsuntag.beltexam.controllers;

import java.security.Principal;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.isaacsuntag.beltexam.auth.validator.APackageValidator;
import com.isaacsuntag.beltexam.models.APackage;
import com.isaacsuntag.beltexam.services.APackageService;
import com.isaacsuntag.beltexam.services.UserService;

@Controller
@RequestMapping("/packages")
public class APackageController {
	private UserService userService;
	private APackageService apackageService;
	private APackageValidator apackageValidator;
	
	public APackageController(APackageService apackageService, APackageValidator apackageValidator, UserService userService) {
		this.apackageService = apackageService;
		this.userService = userService;
		this.apackageValidator = apackageValidator;
	}
	
	@PostMapping("/new")
	public String add(@Valid @ModelAttribute("apackage") APackage apackage, BindingResult result, RedirectAttributes errors, Model model, Principal principal) {
		apackageValidator.validate(apackage, result);
		if(result.hasErrors()) {
			String email = principal.getName();
	        model.addAttribute("currentUser", userService.findByEmail(email));
			errors.addFlashAttribute("errors", result.getAllErrors());
	        model.addAttribute("users", userService.getAllUsers());
	        model.addAttribute("packages", apackageService.getAllPackages());
			return "adminPage.jsp";
		} else {
			apackageService.addPackage(apackage);
		}
		return "redirect:/admin";
	}
	@RequestMapping("/changeactive/{id}")
	public String activate(@PathVariable("id") Long id) {
		apackageService.changeActive(id);
		return "redirect:/admin";
	}
	@RequestMapping("/delete/{id}")
	public String delete(@PathVariable("id") Long id) {
		APackage thispackage = apackageService.getOne(id);
		if(thispackage.getUsers().size() < 1) {
			apackageService.delete(id);
		}
		return "redirect:/admin";
	}
}
