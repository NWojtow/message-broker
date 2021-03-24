package broker.datasource.services;

import broker.datasource.entities.SubjectDAO;
import broker.datasource.entities.UserDAO;
import broker.datasource.resource.HibernateFactory;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.util.*;

@Repository
public class UserService implements CrudRepository<UserDAO, Integer> {

    private HibernateFactory hibernateFactory = new HibernateFactory();
    private SessionFactory sessionFactory = hibernateFactory.getSessionFactory();

    public Optional<UserDAO> findByUsername(String username) {
        HibernateFactory hibernateFactory = new HibernateFactory();
        Session session = hibernateFactory.getSessionFactory().openSession();

        checkIfUserNotExistsByUsername(username);
        try{
            Criteria criteria = session.createCriteria(UserDAO.class);
            UserDAO daoUser = (UserDAO) criteria.add(Restrictions.eq("username",username)).uniqueResult();
            return Optional.of(daoUser);
        } catch(Exception e){
            e.printStackTrace();
            throw e;
        }finally{
            session.close();
        }
    }

    public void updateSubjects(String subjectType, String username) {
        HibernateFactory hibernateFactory = new HibernateFactory();
        Session session = hibernateFactory.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        SubjectService subjectService = new SubjectService();
        try {
            UserDAO user = this.findByUsername(username).orElseThrow(EntityNotFoundException::new);
            Set<SubjectDAO> subjects;

            session.evict(user);
            SubjectDAO subject = subjectService.getSubjectBySubjectType(subjectType).orElseThrow(EntityNotFoundException::new);
            if(user.getSubjects() != null) {
                subjects = user.getSubjects();
            } else {
               subjects = new HashSet<>();
            }
            subjects.add(subject);
            user.setSubjects(subjects);
            session.update(user);
            session.getTransaction().commit();
        } catch(Exception e){
            transaction.rollback();
            e.printStackTrace();
            throw e;
        }finally{
            session.close();
        }
    }

    @Override
    public <S extends UserDAO> S save(S s) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        checkIfUserExistsByUsername(s.getUsername());
        try {
            session.save(s);
            session.getTransaction().commit();
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
            throw e;
        } finally {
            session.close();
        }
        return s;
    }

    @Override
    public <S extends UserDAO> Iterable<S> saveAll(Iterable<S> iterable) {
        return null;
    }

    @Override
    public Optional<UserDAO> findById(Integer integer) {
        Session session = hibernateFactory.getSessionFactory().openSession();
        checkIfUserNotExistsById(integer);
        try{
            UserDAO daoUser = session.get(UserDAO.class, integer);
            return Optional.of(daoUser);

        } catch(Exception e){
            e.printStackTrace();
            throw e;
        }finally{
            session.close();
        }
    }

    @Override
    public boolean existsById(Integer integer) {
        return hibernateFactory.exists(UserDAO.class,"id", integer);
    }

    @Override
    public Iterable<UserDAO> findAll() {
        return null;
    }

    @Override
    public Iterable<UserDAO> findAllById(Iterable<Integer> iterable) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(Integer integer) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        if(existsById(integer)) {
            try {
                UserDAO daoUser = session.get(UserDAO.class, integer);
                session.delete(daoUser);
                session.getTransaction().commit();
            } catch (Exception e) {
                transaction.rollback();
                e.printStackTrace();
                throw new IllegalArgumentException();
            } finally {
                session.close();
            }
        }
    }

    @Override
    public void delete(UserDAO userDAO) {

    }

    @Override
    public void deleteAll(Iterable<? extends UserDAO> iterable) {

    }

    @Override
    public void deleteAll() {

    }

    private void checkIfUserExistsByUsername(String username){
        if(hibernateFactory.exists(UserDAO.class,"username", username)){
            throw new EntityExistsException();
        }
    }
    private void checkIfUserNotExistsByUsername(String username){
        if(!hibernateFactory.exists(UserDAO.class,"username", username)){
            throw new EntityNotFoundException();
        }
    }

    private void checkIfUserNotExistsById(Integer id){
        if(!hibernateFactory.exists(UserDAO.class,"id", id)){
            throw new EntityNotFoundException();
        }
    }
}
