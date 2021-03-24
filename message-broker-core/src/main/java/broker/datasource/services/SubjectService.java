package broker.datasource.services;

import broker.datasource.entities.SubjectDAO;
import broker.datasource.resource.HibernateFactory;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Repository
public class SubjectService implements CrudRepository<SubjectDAO, Integer> {

    private HibernateFactory hibernateFactory = new HibernateFactory();
    private SessionFactory sessionFactory = hibernateFactory.getSessionFactory();

    public Optional<SubjectDAO> getSubjectBySubjectType(String subjectType) {
        HibernateFactory hibernateFactory = new HibernateFactory();
        Session session = hibernateFactory.getSessionFactory().openSession();

        if (checkIfSubjectExistsByType(subjectType)) {
            try {
                Criteria criteria = session.createCriteria(SubjectDAO.class);
                SubjectDAO subject = (SubjectDAO) criteria.add(Restrictions.eq("subject_type", subjectType)).uniqueResult();
                return Optional.of(subject);
            } catch (Exception e) {
                e.printStackTrace();
                throw e;
            } finally {
                session.close();
            }
        } else {
            throw new EntityNotFoundException();
        }
    }

    public SubjectDAO save(String subjecType) {
        SubjectDAO s = new SubjectDAO(subjecType);

        return this.save(s);
    }

    @Override
    public <S extends SubjectDAO> S save(S s) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
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
    public <S extends SubjectDAO> Iterable<S> saveAll(Iterable<S> iterable) {
        return null;
    }

    @Override
    public Optional<SubjectDAO> findById(Integer integer) {
        Session session = hibernateFactory.getSessionFactory().openSession();

        try{
            SubjectDAO subject = session.get(SubjectDAO.class, integer);
            return Optional.of(subject);

        } catch(Exception e){
            e.printStackTrace();
            throw e;
        }finally{
            session.close();
        }
    }

    @Override
    public boolean existsById(Integer integer) {
        return hibernateFactory.exists(SubjectDAO.class,"subject_id", integer);

    }

    @Override
    public Iterable<SubjectDAO> findAll() {
        return null;
    }

    @Override
    public Iterable<SubjectDAO> findAllById(Iterable<Integer> iterable) {
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
                SubjectDAO subject = session.get(SubjectDAO.class, integer);
                session.delete(subject);
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
    public void delete(SubjectDAO subjectDAO) {

    }

    @Override
    public void deleteAll(Iterable<? extends SubjectDAO> iterable) {

    }

    @Override
    public void deleteAll() {

    }

    private boolean checkIfSubjectExistsByType(String type){
       return hibernateFactory.exists(SubjectDAO.class,"subject_type", type);
    }
}
