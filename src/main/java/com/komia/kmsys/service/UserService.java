package com.komia.kmsys.service;

import java.util.Date;
import java.util.List;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.komia.kmsys.dao.IUserDao;
import com.komia.kmsys.po.Resource;
import com.komia.kmsys.po.Role;
import com.komia.kmsys.po.User;
import com.komia.util.KomiaConstant;
import com.komia.util.KomiaUtil;

@Service
public class UserService {
	@Autowired
	private IUserDao userDao;

	public User login(String username, String password) {
		User u = userDao.getUserByUserName(username);
		if(u==null) {
			throw new UnknownAccountException("用户名或者密码错误");
		}
		
		if(u.getStatus()==0) {
			Date unlockDate = u.getUnlockDate();
			if(unlockDate!=null&&unlockDate.compareTo(new Date())>0) {
				throw new LockedAccountException("用户已经被锁定");
			}
			this.userDao.unlock(u.getId());
		}
		
		if(!u.getPassword().equals(KomiaUtil.md5(password, username))) {
			int failcnt = u.getFailcnt() + 1;
			this.userDao.addFailcnt(u.getId());
			
			if(failcnt < KomiaConstant.REMIND_LOGIN_FAIL_CNT) {
				throw new IncorrectCredentialsException("密码错误");
			}else if(failcnt >= KomiaConstant.REMIND_LOGIN_FAIL_CNT && failcnt < KomiaConstant.MAX_LOGIN_FAIL_CNT) {
				throw new IncorrectCredentialsException("密码错误,剩余" + (KomiaConstant.MAX_LOGIN_FAIL_CNT - failcnt) + "次");
			}else {
				u.setUnlockDate(KomiaUtil.add10Minite());
				this.userDao.lock(u);
				throw new LockedAccountException("用户已经被锁定");
			}
		}
		
		return u;
	}

	public User add(User user) {
		if(KomiaUtil.isEmpty(user.getUsername())||KomiaUtil.isEmpty(user.getPassword())) {
			throw new RuntimeException("用户名或者密码不能为空");
		}
		user.setPassword(KomiaUtil.md5(user.getPassword(), user.getUsername()));
		userDao.addUser(user);
		return user;
	}

	public List<User> getAllUsers() {
		return userDao.getAllUsers();
	}

	public List<User> getUsersByRoleId(int roleId) {
		return userDao.getUsersByRoleId(roleId);
	}

	public List<Resource> getResourceByUserId(int uid) {
		return userDao.getResourceByUserId(uid);
	}
	
	public List<Role> getRoleListByUserId(int uid){
		return userDao.getRoleListByUserId(uid);
	}

}
