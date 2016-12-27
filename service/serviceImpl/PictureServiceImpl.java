package cn.edu.bjtu.weibo.service.impl;

import java.io.IOException;
import java.io.InputStream;

import org.springframework.web.multipart.MultipartFile;

import cn.edu.bjtu.weibo.model.Picture;
import cn.edu.bjtu.weibo.service.PictureService;

public class PictureServiceImpl implements PictureService {

	@Override
	public Picture uploadPicture(MultipartFile multipartFile) {
		Picture ap = new Picture();
		if(!multipartFile.isEmpty()){
			try {
				InputStream ins = multipartFile.getInputStream();
				byte[] b = new byte[ins.available()];
				int len = 0;
				while(len<ins.available()){
					len+=ins.read(b, len, ins.available()-len);
				}
				String pictureContent = new String(b);
				ap.setPicurl(pictureContent);
			} catch (IOException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
				ap.setPicurl("");
			}
		}
		return ap;
	}

}
