package hibernateQueriesSample;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import java.util.List;

public class MainPersonTest {

    private       Session    session = null;
    private final String[][] persons = {{"12", "Вильям"}, {"14", "Шекспир"}};
    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    /**
     * Процедура создания сессии
     * @return org.hibernate.Session
     */
    private Session createHibernateSession()
    {
        SessionFactory sessionFactory  = null;
        ServiceRegistry serviceRegistry = null;
        try {
            try {
                Configuration cfg = new Configuration().
                        addResource("person.hbm.xml").configure();
                serviceRegistry = new StandardServiceRegistryBuilder().
                        applySettings(cfg.getProperties()).build();
                sessionFactory = cfg.buildSessionFactory(serviceRegistry);
            } catch (Throwable e) {
                System.err.println("Failed to create sessionFactory object." + e);
                throw new ExceptionInInitializerError(e);
            }
            session = sessionFactory.openSession();
            System.out.println("Создание сессии");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return session;
    }
    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    /**
     * Процедура добавления записей в таблицу
     */
    private void recordsAdd()
    {
        try {
            System.out.println("Добавление записи в таблицу БД");
            Transaction tx = session.beginTransaction();
            for (int i = 0; i < persons.length; i++) {
                Person person = new Person();
                person.setId(Integer.valueOf(persons[i][0]));
                person.setName(persons[i][1]);
                session.save(person);
            }
            tx.commit();
            System.out.println("\tЗаписи добавлены");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    /**
     * Процедура чтения записей
     */
    private void recordsRead()
    {
        System.out.println("\nЧтение записей таблицы");
        String query = "select p from " + Person.class.getSimpleName() + " p";

        @SuppressWarnings("unchecked")
        List<Person> list = (List<Person>)session.createQuery(query).list();
        System.out.println(list);
    }

    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    /**
     * Процедура поиска записи
     */
    private void recordFind(final int id)
    {
        System.out.println("\nЧтение записи таблицы по ID");
        Person person = (Person) session.load(Person.class, id);
        System.out.println(person);
    }
    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    /**
     * Конструктор класса
     */
    public MainPersonTest()
    {
        // Создание сессии
        session = createHibernateSession();
        if (session != null) {
            // Добавление записей в таблицу
            recordsAdd();
            // Чтение записей таблицы
            recordsRead();
            // Поиск записи по идентификатору
            recordFind(Integer.valueOf(persons[1][0]));
            // Закрытие сессии
            if (session.isOpen())
                session.close();
        }
    }
    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    public static void main(String[] args)
    {
        new MainPersonTest();
        System.exit(0);
    }
}
