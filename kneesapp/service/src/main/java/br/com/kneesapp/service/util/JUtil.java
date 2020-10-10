package br.com.kneesapp.service.util;

import static br.com.kneesapp.criteria.AdvertiserCriteria.ADVERTISER;
import static br.com.kneesapp.criteria.AdvertiserCriteria.ADVERTISER_ID;
import static br.com.kneesapp.criteria.BaseCriteria.DATE;
import static br.com.kneesapp.criteria.BaseCriteria.DESCRIPTION;
import static br.com.kneesapp.criteria.BaseCriteria.NAME;
import static br.com.kneesapp.criteria.CategoryCriteria.CATEGORY;
import static br.com.kneesapp.criteria.EventCriteria.ADDRESS;
import static br.com.kneesapp.criteria.EventCriteria.ADVERTISER_IL;
import static br.com.kneesapp.criteria.EventCriteria.CATEGORY_IL;
import static br.com.kneesapp.criteria.EventCriteria.DATE_IL;
import static br.com.kneesapp.criteria.EventCriteria.DESCRIPTION_IL;
import static br.com.kneesapp.criteria.EventCriteria.LIMIT_IL;
import static br.com.kneesapp.criteria.EventCriteria.NAME_IL;
import static br.com.kneesapp.criteria.EventCriteria.OFFSET_IL;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;
import static br.com.kneesapp.criteria.EventCriteria.ADDRESS_IL;
import static br.com.kneesapp.criteria.EventCriteria.ADVERTISER_ID_EQ;
import br.com.kneesapp.dao.JEventDAO;
import br.com.kneesapp.dao.PathImage;
import br.com.kneesapp.sql.JConnectionDB;
import java.sql.Connection;

/**
 *
 * @author andre
 */
public class JUtil {

    public static BufferedOutputStream getFile(String fileName) throws FileNotFoundException, Exception {
        String path = getPath();
        File file = new File(path + fileName);
        return new BufferedOutputStream(new FileOutputStream(file));
    }

    public static String removeIfNull(String field) {
        return field != null ? field : "";
    }

    public static String setImageProfileIfIsNull(String photo) {
        return photo != null ? photo : "profile.jpg";
    }

    public static String setImageEventIfIsNull(String logo) {
        return logo != null ? logo : "event_default.jpg";
    }

    public static String removeNullNumber(String number) {
        return number != null ? number : "0";
    }
    
    private static String getPath() throws Exception{
        String path = null;
        PathImage dao = new PathImage();
        Connection conn = JConnectionDB.getInstance().getConnection();
        try {
            path = dao.readPath(conn);
            conn.commit();
            conn.close();
        } catch (Exception ex) {
            conn.rollback();
            conn.close();
            throw ex;
        }
        return path;
    }

    public static Map<Long, Object> getCriteria(Integer page, Integer pagesize, String fields) {
        Map<Long, Object> criteria = new HashMap<>();
        if (page == null) {
            page = 10;
        }
        if (pagesize == null) {
            pagesize = 0;
        }
        criteria.put(LIMIT_IL, Long.valueOf(page));
        criteria.put(OFFSET_IL, Long.valueOf(pagesize));

        if (fields != null && !fields.isEmpty()) {

            String searchFields[] = fields.split(",");
            for (String searchField : searchFields) {
                String result = searchField.split(":")[0];

                switch (result) {
                    case NAME:
                        criteria.put(NAME_IL, searchField.split(":")[1]);
                        break;
                    case CATEGORY:
                        criteria.put(CATEGORY_IL, searchField.split(":")[1]);
                        break;
                    case DESCRIPTION:
                        criteria.put(DESCRIPTION_IL, searchField.split(":")[1]);
                        break;
                    case ADVERTISER:
                        criteria.put(ADVERTISER_IL, searchField.split(":")[1]);
                        break;
                    case ADVERTISER_ID:
                        criteria.put(ADVERTISER_ID_EQ, searchField.split(":")[1]);
                        break;
                    case DATE:
                        criteria.put(DATE_IL, searchField.split(":")[1]);
                        break;
                    case ADDRESS:
                        criteria.put(ADDRESS_IL, searchField.split(":")[1]);
                        break;
                }
            }
        }

        return criteria;
    }

}
