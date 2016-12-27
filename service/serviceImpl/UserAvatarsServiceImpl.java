package cn.edu.bjtu.weibo.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import cn.edu.bjtu.weibo.dao.UserDAO;
import cn.edu.bjtu.weibo.dao.impl.UserDAOImpl;
import cn.edu.bjtu.weibo.model.Picture;
import cn.edu.bjtu.weibo.service.UserAvatarsService;

public class UserAvatarsServiceImpl implements UserAvatarsService {

	@Override
	public List<Picture> getUserAvatarList(String userId, int pageIndex, int numberPerPage) {
		List<Picture> pics = new ArrayList<Picture>();
		UserDAO auser = new UserDAOImpl();
		List<String> pictures = auser.getUserAvatars(userId, pageIndex, numberPerPage);
		for(int i = 0; i < pictures.size();i++){
			Picture apic = new Picture();
			apic.setPicurl(pictures.get(i));
			pics.add(apic);
		}
		return pics;
	}

	@Override
	public boolean uploadUserAvatar(String userId, MultipartFile multipartFile) {
		// TODO 自动生成的方法存根
		return false;
	}

}
