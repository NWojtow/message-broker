package broker.datasource.services;

import broker.datasource.entities.MessageDAO;

import broker.datasource.entities.SubjectDAO;
import broker.datasource.resource.HibernateFactory;
import broker.entities.MessageDTO;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.data.repository.CrudRepository;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

public class MessageService implements CrudRepository<MessageDAO, Integer> {
    private HibernateFactory hibernateFactory = new HibernateFactory();
    private SessionFactory sessionFactory = hibernateFactory.getSessionFactory();


    public MessageDAO save(MessageDTO message) {
        SubjectService subjectService = new SubjectService();
        Optional<SubjectDAO> subject = subjectService.getSubjectBySubjectType(message.getSubjectId());

        if(subject.isPresent()) {
            return this.save(new MessageDAO(message.getMessage(), message.getExpirationDate(), subject.get()));
        } else {
            throw new IllegalArgumentException();
        }
    }
    
    @Override
    public <S extends MessageDAO> S save(S s) {
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
    public <S extends MessageDAO> Iterable<S> saveAll(Iterable<S> iterable) {
        return null;
    }

    @Override
    public Optional<MessageDAO> findById(Integer integer) {
        Session session = hibernateFactory.getSessionFactory().openSession();

        try{
            MessageDAO message = session.get(MessageDAO.class, integer);
            return Optional.of(message);

        } catch(Exception e){
            e.printStackTrace();
            throw e;
        }finally{
            session.close();
        }
    }

    @Override
    public boolean existsById(Integer integer) {
        return hibernateFactory.exists(MessageDAO.class,"message_id", integer);

    }

    @Override
    public Iterable<MessageDAO> findAll() {
        return null;
    }

    @Override
    public Iterable<MessageDAO> findAllById(Iterable<Integer> iterable) {
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

            try {
                MessageDAO message = session.get(MessageDAO.class, integer);
                session.delete(message);
                session.getTransaction().commit();
            } catch (Exception e) {
                transaction.rollback();
                e.printStackTrace();
                throw new EntityNotFoundException();
            } finally {
                session.close();
            }
    }

    @Override
    public void delete(MessageDAO MessageDAO) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        try {
            session.delete(MessageDAO);
            session.getTransaction().commit();
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
            throw new EntityNotFoundException();
        } finally {
            session.close();
        }
    }

    @Override
    public void deleteAll(Iterable<? extends MessageDAO> iterable) {
        iterable.forEach(entry -> deleteById(entry.getMessageId()));
    }

    @Override
    public void deleteAll() {}
}
