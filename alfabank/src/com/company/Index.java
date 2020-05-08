package com.company;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.hibernate.query.NativeQuery;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class Index {


    @PostMapping(path = "/index", produces = "application/json")
    public Integer[] printHello(@RequestParam(value = "box", required = false) String box_id, @RequestParam(value = "color", required = false) String color) {

        SessionFactory sessionFactory = new org.hibernate.cfg.Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Integer box_id_int = null;
        try {
            box_id_int = Integer.valueOf(box_id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Integer[] ids = get_items_id(box_id_int, color, session);

        session.getTransaction().commit();
        session.close();


        return ids;
    }

    public Integer[] get_items_id(Integer box_id, String color, Session session) {
        String sql = null;
        Integer[] result = null;
        List<Item> r = new ArrayList<>();
        if (box_id != null) {
            sql = "WITH RECURSIVE r AS (\n" +
                    "    SELECT id, contained_in\n" +
                    "    FROM box\n" +
                    "    WHERE contained_in =:box_id or id=:box_id\n" +
                    "\n" +
                    "    UNION\n" +
                    "\n" +
                    "    SELECT box.id, box.contained_in\n" +
                    "    FROM box\n" +
                    "             JOIN r\n" +
                    "                  ON box.contained_in = r.id\n" +
                    ")\n" +
                    "\n" +
                    "SELECT b.* from r inner join item b on b.contained_in =r.id";
            if (color == null) {
                sql = sql + " where  b.color is null ";
            } else {
                sql = sql + " where  b.color=:color ";
            }
            NativeQuery<Item> query = session.createNativeQuery(sql, Item.class);
            if (color != null) {
                query.setParameter("color", color);

            }

            query.setParameter("box_id", Integer.valueOf(box_id));

            try {
                r = query.list();
            } catch (Exception e) {
                e.printStackTrace();
            }


        } else {
            Criteria criteria = session.createCriteria(Item.class);
            if (color != null) {
                criteria.add(Restrictions.eq("color", color));
            } else {
                criteria.add(Restrictions.isNull("color"));
            }
            criteria.add(Restrictions.isNull("box_id"));
            r = (List<Item>) criteria.list();


        }
        if (r != null) {

            result = new Integer[r.size()];
            for (int i = 0; i < r.size(); i++) {
                result[i] = r.get(i).getId();
            }
        }
        return result;
    }
}
