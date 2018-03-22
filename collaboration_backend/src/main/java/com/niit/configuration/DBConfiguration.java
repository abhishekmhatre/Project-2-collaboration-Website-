package com.niit.configuration;

import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBuilder;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.niit.model.BlogComment;
import com.niit.model.BlogPost;
import com.niit.model.BlogPostLikes;
import com.niit.model.Chat;
import com.niit.model.Friend;
import com.niit.model.Job;
import com.niit.model.Notification;
import com.niit.model.ProfilePicture;
import com.niit.model.User;



@Configuration
@EnableTransactionManagement
public class DBConfiguration
{
	public DBConfiguration(){
		System.out.println("DBCOnfiguration class instantiated");
	}
	@Bean
	public SessionFactory sessionFactory() {
		LocalSessionFactoryBuilder lsf=
				new LocalSessionFactoryBuilder(getDataSource());
		Properties hibernateProperties=new Properties();
		hibernateProperties.setProperty(
				"hibernate.dialect", "org.hibernate.dialect.Oracle10gDialect");
		hibernateProperties.setProperty("hibernate.hbm2ddl.auto", "update");
		hibernateProperties.setProperty("hibernate.show_sql", "true");
		lsf.addProperties(hibernateProperties);
		//Class classes[]=new Class[]{Student.class};
		Class classes[]=new Class[] {User.class,Job.class,BlogPost.class,BlogPostLikes.class,Notification.class,
										BlogComment.class,ProfilePicture.class,Friend.class,Chat.class};
		System.out.println("User table created");
		System.out.println("Job table created");
		System.out.println("BlogPost table created");
		System.out.println("BlogPostLikes table created");
		System.out.println("Notification table created");
		System.out.println("BlogComment table created");
		System.out.println("ProfilePicture table created");
		System.out.println("Friend table created");
		System.out.println("Chat table created");
	    return lsf.addAnnotatedClasses(classes).buildSessionFactory();
	}
	@Bean
	public DataSource getDataSource() {
	    BasicDataSource dataSource = new BasicDataSource();
	    dataSource.setDriverClassName("oracle.jdbc.OracleDriver");
	    dataSource.setUrl("jdbc:oracle:thin:@localhost:1521:XE");
	    dataSource.setUsername("abhi");
	    dataSource.setPassword("abhi");
	    return dataSource;
	    
	}
	@Bean
	public HibernateTransactionManager hibTransManagement(){
		return new HibernateTransactionManager(sessionFactory());
	}


}
