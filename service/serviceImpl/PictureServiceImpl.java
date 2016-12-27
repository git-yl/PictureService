package cn.edu.bjtu.weibo.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import cn.edu.bjtu.weibo.dao.ImgDAO;
import cn.edu.bjtu.weibo.dao.impl.ImgDAOImpl;
import cn.edu.bjtu.weibo.model.Picture;
import cn.edu.bjtu.weibo.service.PictureService;

public class PictureServiceImpl implements PictureService {

	@Override
	public List<String> uploadPicture(MultipartFile multipartFile) {
		// TODO 自动生成的方法存根
		List<String> pictures = new ArrayList<String>();
		pictures.add("23456");
		return pictures;
	}

	@Override
	public Picture getPicture(String picId) {
		// TODO 自动生成的方法存根
		ImgDAO aimg = new ImgDAOImpl();
		Picture apic = new Picture();
		apic.setPicurl(aimg.getimgThUrl(picId));
		apic.setPicurlor(aimg.getimgOrUrl(picId));
		return apic;
	}
}
