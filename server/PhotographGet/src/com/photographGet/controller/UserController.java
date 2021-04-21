 package com.photographGet.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.google.gson.Gson;
import com.photographGet.entity.User;
import com.photographGet.utils.service.UserService;

@Controller
@RequestMapping("/user")
public class UserController {
	@Resource
	private UserService userService;
	
	@RequestMapping("/adduser")
	public void addUser(@RequestParam String user) {
		Gson gson = new Gson();
		User user2=gson.fromJson(user, User.class);
		userService.saveUser(user2);
	}
	
	@RequestMapping("/getuser")
	public String list(@RequestParam String id,Model model,HttpServletRequest req,HttpServletResponse rep) {
		rep.setCharacterEncoding("UTF-8");
		rep.setContentType("text/html;charset=UTF-8");
		Gson gson=new Gson();
		System.out.println(id);
		int userId = Integer.parseInt(id);
		User user=userService.getUser(userId);
		String usering=gson.toJson(user);
		model.addAttribute("user",usering);
		System.out.println(usering);
		PrintWriter writer;
		try {
			writer = rep.getWriter();
			writer.println(usering);
		    writer.flush();
		    writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return usering;
	}
	
	@RequestMapping("/ifuser")
	public String loginif(@RequestParam String client,Model model,HttpServletRequest req,HttpServletResponse rep) {
		rep.setCharacterEncoding("UTF-8");
		rep.setContentType("text/html;charset=UTF-8");
		User user = new User();
		Gson gson=new Gson();
		user = gson.fromJson(client,User.class);
		User ruUer = userService.getUserbyTelephone(user.getTelephone());
		PrintWriter writer;
		if(ruUer.getPassword().equals(user.getPassword())) {
			Gson gson2 = new Gson();
			String ruclient = gson.toJson(ruUer);
			try {
				writer = rep.getWriter();
				writer.println(ruclient);
			    writer.flush();
			    writer.close();
			    System.out.println(ruclient);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return ruclient;
		}
		else {
			return "输入错误";
		}
	}
	/*
	@RequestMapping("/list")
	public String list(Model model) {
		List<User> users = new ArrayList<>();
		users = userService.list();
		int n = pianoServiceIpml.selectPrice("ok");
		System.out.println(n);
		model.addAttribute("users", users);
		return "list";
	}*/

}
