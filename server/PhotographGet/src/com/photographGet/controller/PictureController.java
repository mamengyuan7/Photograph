package com.photographGet.controller;

import java.io.File;
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
import com.photographGet.entity.Picture;
import com.photographGet.entity.PictureDetail;
import com.photographGet.utils.SampleUsage;
import com.photographGet.utils.service.PicDetailService;
import com.photographGet.utils.service.PictureService;

@Controller
@RequestMapping("/picture")
public class PictureController {
	@Resource
	private PictureService pictureService;
	
	@Resource
	private PicDetailService picDetailService;
	
	@RequestMapping("/addPic")
	public String addPic(@RequestParam String picture,HttpServletResponse rep) {
		rep.setCharacterEncoding("UTF-8");
		rep.setContentType("text/html;charset=UTF-8");
		Gson gson = new Gson();
		Picture pic = gson.fromJson(picture, Picture.class);
		pictureService.savePicture(pic);
		List<Picture> ps = pictureService.findAllPicture();
		int num = ps.size();
		System.out.println("num是："+num);
		PictureDetail pictureDetail = new PictureDetail();
		String path = pic.getImgAddress();
		String[] p = path.split("--");
		
		for(int i = 0;i<p.length;i++) {
			try {
				File file = SampleUsage.getFile(p[i]);
				pictureDetail = SampleUsage.printImageTags(file);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(pictureDetail.getBrand()==null) {
				pictureDetail.setBrand("--");
			}
			if(pictureDetail.getType()==null) {
				pictureDetail.setType("--");
			}
			if(pictureDetail.getPtype()==null) {
				pictureDetail.setPtype("--");
			}
			if(pictureDetail.getFocalLength()==null) {
				pictureDetail.setFocalLength("--");
			}
			if(pictureDetail.getCarmeraLen()==null) {
				pictureDetail.setCarmeraLen("--");
			}
			if(pictureDetail.getIso()==null) {
				pictureDetail.setIso("--");
			}
			if(pictureDetail.getTime()==null) {
				pictureDetail.setTime("--");
			}
			pictureDetail.setPicId(num);
			pictureDetail.setFlag(i);
			pictureDetail.setAddress(p[i]);
			picDetailService.savePicDetail(pictureDetail);
		}
		PrintWriter writer;
		try {
			writer = rep.getWriter();
			writer.println("添加成功！");
		    writer.flush();
		    writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	@RequestMapping("/listall")
	public String listall(Model model,HttpServletRequest req,HttpServletResponse rep) {
		rep.setCharacterEncoding("UTF-8");
		rep.setContentType("text/html;charset=UTF-8");
		Gson gson=new Gson();
		List<Picture> pics = pictureService.findAllPicture();
		String picstring = gson.toJson(pics);
		model.addAttribute("pics", picstring);
		System.out.println(picstring);
		PrintWriter writer;
		try {
			writer = rep.getWriter();
			writer.println(picstring);
			writer.flush();
			writer.close();
		}catch (IOException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return null;
	}

	@RequestMapping("listpart")
	public String listpart(@RequestParam String userId,Model model,HttpServletRequest req,HttpServletResponse rep) {
		rep.setCharacterEncoding("UTF-8");
		rep.setContentType("text/html;charset=UTF-8");
		Gson gson=new Gson();
		System.out.println(userId);
		int uId = Integer.parseInt(userId);
		List<Picture> pics = pictureService.findByUserId(uId);
		String picsing=gson.toJson(pics);
		model.addAttribute("pics",picsing);
		System.out.println(picsing);
		PrintWriter writer;
		try {
			writer = rep.getWriter();
			writer.println(picsing);
		    writer.flush();
		    writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	@RequestMapping("lista")
	public String lista(@RequestParam String id,Model model,HttpServletRequest req,HttpServletResponse rep) {
		rep.setCharacterEncoding("UTF-8");
		rep.setContentType("text/html;charset=UTF-8");
		Gson gson=new Gson();
		System.out.println(id);
		int uId = Integer.parseInt(id);
		Picture pic = pictureService.findById(uId);
		String picsing=gson.toJson(pic);
		model.addAttribute("pic",picsing);
		System.out.println(picsing);
		PrintWriter writer;
		try {
			writer = rep.getWriter();
			writer.println(picsing);
		    writer.flush();
		    writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
