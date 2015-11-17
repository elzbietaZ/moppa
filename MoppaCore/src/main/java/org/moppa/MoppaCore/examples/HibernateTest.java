package org.moppa.MoppaCore.examples;

import java.util.List;
 
import org.hibernate.*;
import org.moppa.MoppaCore.HibernateUtil;
import org.moppa.MoppaCore.model.Task;
 
public class HibernateTest {
 
public static void main(String[] args) {
          
        Session session = HibernateUtil.getSessionFactory().openSession();
  
        session.beginTransaction();
 
        Department department = new Department("java");
        session.save(department);
 
        session.save(new Employee("Ela Z",department));
        session.save(new Employee("Liana G",department));
        session.save(new Task(502, 4));
      
        session.getTransaction().commit();
 
        Query q = session.createQuery("From Employee ");
                 
        List<Employee> resultList = q.list();
        System.out.println("num of employess:" + resultList.size());
        for (Employee next : resultList) {
            System.out.println("next employee: " + next);
        }
 
    }
    
}
