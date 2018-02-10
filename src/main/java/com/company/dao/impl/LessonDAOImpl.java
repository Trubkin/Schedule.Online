package com.company.dao.impl;

import com.company.dao.api.LessonDAO;
import com.company.model.Group;
import com.company.model.Lesson;
import com.company.util.GenericReflector;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;

import java.util.List;

public class LessonDAOImpl extends DAO<Lesson> implements LessonDAO {
    private static final Logger LOGGER = Logger.getLogger(LessonDAOImpl.class);

    public LessonDAOImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Lesson> readAllByGroup(int groupId) {
        Session session = getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            List<Lesson> lessons = session.createCriteria(GenericReflector.getClassParameterType(this.getClass()))
                    .add(Restrictions.eq("group.id", groupId))
                    .list();
            transaction.commit();
            return lessons;
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            LOGGER.error(HIBERNATE_EXC_MSG, e);
            throw e;
        } finally {
            session.close();
        }
    }
}
