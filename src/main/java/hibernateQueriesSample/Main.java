package hibernateQueriesSample;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import javax.transaction.Transactional;
import java.util.List;

public class Main {

    private static SessionFactory sessionFactory=null;

//    @Transactional
    public static void main(String[] args) {
        try {
            // Build a SessionFactory object from session-factory config
            // defined in the hibernate.cfg.xml file. In this file we
            // register the JDBC connection information, connection pool,
            // hibernate dialect that we used and the mapping to our
            // hbm.xml file for each pojo (plain old java object).
            Configuration config = new Configuration();
            config.configure("hibernate.cfg.xml");
            sessionFactory = config.configure().buildSessionFactory();


        } catch (Throwable e) {
            System.err.println("Error in creating SessionFactory object."
                    + e.getMessage());
            throw new ExceptionInInitializerError(e);
        }

        Session session = sessionFactory.openSession();

        // INSERT object
        Transaction tx1 = session.beginTransaction();
        User user = new User();
        user.setName("1");
        user.setEmail("asdfasd@qsdfasdf.com");
        session.save(user);
        tx1.commit();

        // INSERT
        String hql2 = "insert into User (name, email) " +
                "select 'UserName', 'UserName@gmail.com' from User";
        Transaction tx2 = session.beginTransaction();
        int rows = session.createQuery(hql2).executeUpdate();
        System.out.println("rows : " + rows);
        tx2.commit();

        // Update
        Transaction tx3 = session.beginTransaction();
        String hql3 = "UPDATE User set name = :name "  +
                "WHERE id = :user_id";
        Query query3 = session.createQuery(hql3);
        query3.setParameter("name", "NewUserName1");
        query3.setParameter("user_id", 10);
        int result3 = query3.executeUpdate();
        System.out.println("Rows affected: " + result3);
        tx2.commit();

        // DELETE
        String hql4 = "DELETE FROM User "  +
                "WHERE id = :user_id";
        Transaction tx4 = session.beginTransaction();
        Query query4 = session.createQuery(hql4);
        query4.setParameter("user_id", 1);
        int result4 = query4.executeUpdate();
        System.out.println("Rows affected: " + result4);
        tx4.commit();

        // Select
        String hql5 = "FROM User";
        Query query5 = session.createQuery(hql5);
        List<User> users5 = query5.list();

        String hql6 = "FROM User where name = :paramName";
        Query query6 = session.createQuery(hql6);
        query6.setParameter("paramName", "Alex");
        List<User> users6 = query6.list();

        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            // do some work
            tx.commit();
        }

        catch (Exception e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }
}
