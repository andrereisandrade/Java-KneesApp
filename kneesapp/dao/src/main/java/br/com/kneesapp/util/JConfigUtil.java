package br.com.kneesapp.util;

/**
 *
 * @author andre
 */
public class JConfigUtil {

    private static final String EVENT_IMAGE_URL = "http://localhost:8080/kneesapp/resources/img/";
    //private static final String USER_IMAGE_URL = "http://localhost:8080/kneesapp/resources/img/users/";
    //public static String EVENT_IMAGE_URL = "http://ec2-18-220-159-137.us-east-2.compute.amazonaws.com:8084/controller-1.0-SNAPSHOT/resources/img/events/";
    //public static String USER_IMAGE_URL = "http://ec2-18-220-159-137.us-east-2.compute.amazonaws.com:8084/controller-1.0-SNAPSHOT/resources/img/events/";
    //public static String ADVERTISER_IMAGE_URL = "http://ec2-18-220-159-137.us-east-2.compute.amazonaws.com:8084/controller-1.0-SNAPSHOT/resources/img/events/";

    
    public static String getEventImage(String image) {
        return EVENT_IMAGE_URL + image;
    }
    
    public static String getUserImage(String image) {
//        return USER_IMAGE_URL + image;
        return EVENT_IMAGE_URL + image;
    }
}
