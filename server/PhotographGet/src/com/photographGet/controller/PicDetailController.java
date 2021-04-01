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
import com.photographGet.entity.PictureDetail;
import com.photographGet.utils.service.PicDetailService;

@Controller
@RequestMapping("/pictureDetail")
public class PicDetailController {

	@Resource
	private PicDetailService picDetailService;
	
	@RequestMapping("/add")
	public void addComment(@RequestParam String picDetail) {
		Gson gson = new Gson();
		PictureDetail pDetail = gson.fromJson(picDetail, PictureDetail.class);
		picDetailService.savePicDetail(pDetail);
	}
	
	@RequestMapping("/list")
	public String list(@RequestParam String picId,@RequestParam String flag,Model model,HttpServletRequest req,HttpServletResponse rep) {
		rep.setCharacterEncoding("UTF-8");
		rep.setContentType("text/html;charset=UTF-8");
		Gson gson=new Gson();
		System.out.println(picId);
		System.out.println(flag);
		int uId = Integer.parseInt(picId);
		int f = Integer.parseInt(flag);
		PictureDetail picdetail = picDetailService.findByFlagAndId(uId, f);
		String picdetailing=gson.toJson(picdetail);
		model.addAttribute("picdetail",picdetailing);
		System.out.println(picdetailing);
		PrintWriter writer;
		try {
			writer = rep.getWriter();
			writer.println(picdetailing);
		    writer.flush();
		    writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return picdetailing;
		
	}
}
