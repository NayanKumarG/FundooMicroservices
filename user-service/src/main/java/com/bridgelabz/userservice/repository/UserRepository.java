/**
 * @author Nayan Kumar G
 * purpose : Repository provides Crud operations for User
 * 
 */
package com.bridgelabz.userservice.repository;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.bridgelabz.userservice.entity.User;

@Repository
public class UserRepository{

	@Autowired
	private EntityManager entityManager;

	
	/**
	 * 
	 * @param user to save into database
	 */
	@Transactional
	public void save(User user)
	{
		Session session = entityManager.unwrap(Session.class);
		session.save(user);
	}
	/**
	 * 
	 * @param password to update
	 * @param userId to identify user
	 * @return true if updated
	 */
	@Transactional
	public boolean updatePassword(String password ,long userId)
	{
		Session session = entityManager.unwrap(Session.class);
		Query<?> query = session.createQuery("update User set password=:password , update_date_time=:date"+" where user_id =:userId ");
		query.setParameter("password", password);
		query.setParameter("date", LocalDateTime.now());
		query.setParameter("userId", userId);
		return query.executeUpdate()==1;
	}
	
	/**
	 * 
	 * @param email_id to check user
	 * @return true if user exist
	 */
	@Transactional
	public boolean isMailExist(String email_id)
	{
		Session session = entityManager.unwrap(Session.class);
		Query<?> query = session.createQuery("from User where email=:email_id");
		query.setParameter("email_id", email_id);
		return query.uniqueResult()!=null;

	}
	
	/**
	 * 
	 * @param email_id to find user by email
	 * @return optional user if found
	 */
	@Transactional
	public User findByMail(String email_id)
	{
		Session session = entityManager.unwrap(Session.class);
		Query<?> query = session.createQuery("from User where email=:email_id");
		query.setParameter("email_id", email_id);
		return (User) query.uniqueResult();
	}
	/**
	 * 
	 * @param id to identify user
	 * @return user fetched
	 */
	@Transactional
	public User findById(long id)
	{
		Session session = entityManager.unwrap(Session.class);
		Query<?> query = session.createQuery("from User where user_id=:user_id");
		query.setParameter("user_id", id);
		return (User) query.uniqueResult();
		
	}
	
	/**
	 * @param token to find user
	 * @return true if user present
	 */
	@Transactional
	public boolean updateMailVerification(String token , long id) {
			Session session = entityManager.unwrap(Session.class);
			Query<?> query = session.createQuery("update User set is_verified =:verified "+" where user_id =:u_id");
			query.setParameter("verified", true);
			query.setParameter("u_id" , id);
			return query.executeUpdate()==1;
	}
	
	/**
	 * Get the list of users
	 */
	@Transactional
	public List<User> getUsers() {

		Session session = entityManager.unwrap(Session.class);
		Query<User> query = session.createQuery("from User" , User.class);
		return query.getResultList();


	} 
	
	@Transactional
	public List<User> getCollaborators(long noteId)
	{
		Session session = entityManager.unwrap(Session.class);
		String sql= "from User JOIN collaborator_note ON collaborator_note.user_id=User.user_Id where collaborator_note.note_id =:nid ";

		Query<User> query = session.createQuery(sql);
		//hibernateUtil.createUserNativeQuery(sql);
		query.setParameter("nid", noteId);
		return query.list();
	}

}
