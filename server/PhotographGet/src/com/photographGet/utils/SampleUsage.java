package com.photographGet.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;
import com.photographGet.entity.PictureDetail;

public class SampleUsage {
	/** 
     * 读取照片里面的信息 
     */ 
    public static PictureDetail printImageTags(File file) throws ImageProcessingException, Exception{  
    	PictureDetail pictureDetail = new PictureDetail();
    	Metadata metadata = ImageMetadataReader.readMetadata(file);
        for (Directory directory : metadata.getDirectories()) {  
            for (Tag tag : directory.getTags()) {  
                String tagName = tag.getTagName();  //标签名
                String desc = tag.getDescription(); //标签信息
                if (tagName.equals("Image Height")) {  
                	System.out.println("图片高度: "+desc);
                } else if (tagName.equals("Image Width")) {  
                	System.out.println("图片宽度: "+desc);
                } else if (tagName.equals("Date/Time Original")) {  
                	System.out.println("拍摄时间: "+desc);
                	//time
                	pictureDetail.setTime(desc);
                }else if (tagName.equals("GPS Latitude")) {  
                	System.err.println("纬度 : "+desc);
                	System.err.println("纬度(度分秒格式) : "+pointToLatlong(desc));
                	//longitude
                	double lon = Double.parseDouble(pointToLatlong(desc));
                	pictureDetail.setLongitude(lon);
                } else if (tagName.equals("GPS Longitude")) {  
                	System.err.println("经度: "+desc);
                	System.err.println("经度(度分秒格式): "+pointToLatlong(desc));
                	//latitude
                	double lat = Double.parseDouble(pointToLatlong(desc));
                	pictureDetail.setLatitude(lat);
                } else if (tagName.equals("Make")) {
					System.out.println("拍摄设备："+desc);
					//brand
					pictureDetail.setBrand(desc);
				}else if (tagName.equals("Model")) {
					System.out.println("型号："+desc);
					//type
					pictureDetail.setType(desc);
				}else if (tagName.equals("ISO Speed Ratings")) {
					System.out.println("ISO："+desc);
					//ISO
					pictureDetail.setIso(desc);
				}else if (tagName.equals("Focal Length")) {
					System.out.println("焦距："+desc);
					//focal_length
					pictureDetail.setFocalLength(desc);
				}else if (tagName.equals("Resolution Unit")) {
					System.out.println("分辨率："+desc);
					//ptype
					pictureDetail.setPtype(desc);
				}else if(tagName.equals("ApertureValue")) {
					System.out.println("镜头："+desc);
					//camera_len
					pictureDetail.setCarmeraLen(desc);
				}
                
            }  
        } 
        return pictureDetail;
    }  
    /** 
     * 经纬度格式  转换为  度分秒格式 ,如果需要的话可以调用该方法进行转换
     * @param point 坐标点 
     * @return 
     */ 
    public static String pointToLatlong (String point ) {  
        Double du = Double.parseDouble(point.substring(0, point.indexOf("°")).trim());  
        Double fen = Double.parseDouble(point.substring(point.indexOf("°")+1, point.indexOf("'")).trim());  
        Double miao = Double.parseDouble(point.substring(point.indexOf("'")+1, point.indexOf("\"")).trim());  
        Double duStr = du + fen / 60 + miao / 60 / 60 ;  
        return duStr.toString();  
    }  
    public static File getFile(String url) throws Exception {
        //对本地文件命名
        String fileName = url.substring(url.lastIndexOf("."),url.length());
        File file = null;

        URL urlfile;
        InputStream inStream = null;
        OutputStream os = null;
        try {
            file = File.createTempFile("net_url", fileName);
            //下载
            urlfile = new URL(url);
            inStream = urlfile.openStream();
            os = new FileOutputStream(file);

            int bytesRead = 0;
            byte[] buffer = new byte[8192];
            while ((bytesRead = inStream.read(buffer, 0, 8192)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != os) {
                    os.close();
                }
                if (null != inStream) {
                    inStream.close();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return file;
    }
}
