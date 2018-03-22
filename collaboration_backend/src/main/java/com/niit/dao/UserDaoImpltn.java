package com.niit.dao;

import javax.transaction.Transactional;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.niit.model.User;
@Repository
@Transactional
public class UserDaoImpltn implements UserDao {
	@Autowired
	private SessionFactory sessionFactory;
		public void registration(User user) {
			Session session=sessionFactory.getCurrentSession();
				session.save(user);//insert into user values (...)
		}
		public boolean isEmailValid(String email) {
			Session session=sessionFactory.getCurrentSession();
			Query query=session.createQuery("from User where email=?");
			query.setString(0, email);
			User user=(User)query.uniqueResult();
			if(user==null)//email is unique ,valid
				return true;
			else//email is duplicate,invalid
			return false;
		}
		public User login(User user) {
			Session session=sessionFactory.getCurrentSession();
			Query query=session.createQuery("from User where email=? and password=?");
			query.setString(0, user.getEmail());
			query.setString(1,user.getPassword());
			user=(User)query.uniqueResult();//1 user object or null value
			return user;// 1 user object[valid]  / null value [invalid credentials]
		}
		public void update(User user) {
			Session session=sessionFactory.getCurrentSession();
			session.update(user);//update User set email,password,firstname...lastname,online where email=?
			
		}
		public User getUser(String email) {
			Session session=sessionFactory.getCurrentSession();
			User user=(User)session.get(User.class, email);
			return user;
		}
		
	}

