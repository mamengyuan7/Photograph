package com.photographGet.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.google.gson.Gson;
import com.photographGet.entity.Comment;
import com.photographGet.utils.service.CommentService;

@Controller
@RequestMapping("/comment")
public class CommentController {

	@Resource
	private CommentService commentService;
	
	@RequestMapping("/addcomment")
	public void addCommment(@RequestParam String comment) {
		Gson gson = new Gson();
		Comment comm = gson.fromJson(comment, Comment.class);
		commentService.saveComment(comm);
	}
	
	@RequestMapping("listcomment")
	public String listComment(@RequestParam String picId,Model model,HttpServletRequest req,HttpServletResponse rep) {
		rep.setCharacterEncoding("UTF-8");
		rep.setContentType("text/html;charset=UTF-8");
		Gson gson=new Gson();
		System.out.println(picId);
		int pId = Integer.parseInt(picId);
		List<Comment> comms = commentService.findBypicID(pId);
		String commsing=gson.toJson(comms);
		model.addAttribute("comms",commsing);
		System.out.println(commsing);
		PrintWriter writer;
		try {
			writer = rep.getWriter();
			writer.println(commsing);
		    writer.flush();
		    writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return commsing;
	}
}
