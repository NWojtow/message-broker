package broker.datasource.resource;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

public class HibernateFactory {
    public SessionFactory getSessionFactory(){
        return new Configuration().configure().buildSessionFactory();
    }

    public boolean exists (Class claz, String idKey, Object idValue) {
        Session session = getSessionFactory().openSession();
        boolean exists = session.createCriteria(claz)
                .add(Restrictions.eq(idKey, idValue))
                .setProjection(Projections
                        .property(idKey))
                .uniqueResult() != null;
        session.close();
        return exists;
    }
}
