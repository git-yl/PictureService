package cn.edu.bjtu.weibo.service.impl;

import java.awt.Color;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import cn.edu.bjtu.weibo.dao.ImgDAO;
import cn.edu.bjtu.weibo.model.Picture;
import cn.edu.bjtu.weibo.service.PictureService;

@Service
public class PictureServiceImpl implements PictureService {

	private final String savePath = "/Weibo/src/main/java/cn/edu/bjtu/weibo/upload";
	
	@Autowired
	ImgDAO imgDAO;
	@Autowired
	MarkServiceImpl markServiceImpl;
	
	@Override
	public String uploadPicture(MultipartFile multipartFile) {
		String pictureid="";
		
		//If the file path isn't exist, create it
		File forSavePath = new File(savePath);
		if(!forSavePath.exists()){
			forSavePath.mkdirs();
		}
		
		//If file existed
		if ((!multipartFile.isEmpty()) && multipartFile!=null) {
            try {
            	//use to make file name and file save path
            	String prefix = String.valueOf(System.currentTimeMillis());
            	String fileOrName = prefix + "_or_" + multipartFile.getOriginalFilename(); 
            	String fileThName = prefix + "_th_" + multipartFile.getOriginalFilename();
                String filePath = savePath + "/" + fileOrName;
                String filePath2 = savePath + "/" + fileThName;
                
                //Save the file 
                File fileOr = new File(filePath);
                if(!fileOr.exists()){
                	fileOr.createNewFile();
                }
                multipartFile.transferTo(fileOr);
                
                //Make the thumbnail 
                File fileTh = new File(filePath2);
                AffineTransform transform = new AffineTransform();
                BufferedImage bis = ImageIO.read(fileOr);
                int w = bis.getWidth();		int h = bis.getHeight();
                int nw = 100;				int nh = (nw*h)/w ;
                double sx = (double)nw/w;	double sy = (double)nh/h;
                transform.setToScale(sx,sy);
                AffineTransformOp ato = new AffineTransformOp(transform,null);
                BufferedImage bid = new BufferedImage(nw,nh,BufferedImage.TYPE_INT_ARGB);
                ato.filter(bis,bid);
                String type = "";
                if(fileOrName.indexOf(".")>-1){
                	String[] types = fileOrName.split("\\.");
                	type = types[types.length-1];
                	if(type.equals("bmp") || type.equals("dib") || type.equals("gif") || 
                			type.equals("jfif") || type.equals("jpe") || type.equals("jpeg") || 
                			type.equals("jpg") || type.equals("png") || type.equals("tif") || 
                			type.equals("tiff") || type.equals("ico")){
                		ImageIO.write(bid,type,fileTh);
                		
                		markServiceImpl.mark(filePath, filePath, Color.WHITE, "@爪哇微博通用水印OVO");
                		markServiceImpl.mark(filePath2, filePath2, Color.WHITE, "@爪哇微博通用水印OVO");
                		
                        //Save now time
                        SimpleDateFormat df = new SimpleDateFormat("yyyy/mm/dd hh:mm:ss");
                        String nowTime = df.format(new Date());
                        
                        //Save to database
                        pictureid = imgDAO.insert(nowTime, fileOrName, fileThName);
                	}
                }
            } catch (Exception e) {
                e.printStackTrace();  
            }  
        }
		if(pictureid.equals("")){
			return null;
		}
		return pictureid;
	}

	@Override
	public Picture getPicture(String picId) {
		// TODO 自动生成的方法存根
		Picture apic = new Picture();
		apic.setPicurl(savePath+"/"+imgDAO.getimgThUrl(picId));
		apic.setPicurlor(savePath+"/"+imgDAO.getimgOrUrl(picId));
		return apic;
	}
}
