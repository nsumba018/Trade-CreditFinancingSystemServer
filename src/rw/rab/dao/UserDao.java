
package rw.rab.dao;

import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;
import rw.rab.model.User;

/**
 *
 * @author nsumba
 */
public class UserDao {
    public String createUser(User user){
        Session ss = HibernateUtil.getSessionFactory().openSession();
        Transaction tr = ss.beginTransaction();
        ss.save(user);
        tr.commit();
        ss.close();
        return "User Saved Successfully";
    }
    
    public String updateUser(User user){
        Session ss = HibernateUtil.getSessionFactory().openSession();
        Transaction tr = ss.beginTransaction();
        ss.update(user);
        tr.commit();
        ss.close();
        return "User Updated Successfully";
    }
    
    public String deleteUser(User user){
        Session ss = HibernateUtil.getSessionFactory().openSession();
        Transaction tr = ss.beginTransaction();
        ss.delete(user);
        tr.commit();
        ss.close();
        return "User Deleted Successfully";
    }
    
    public List<User> getAllUsers(){
         Session ss = HibernateUtil.getSessionFactory().openSession();
         List<User> usersList = ss.createQuery("select usr from User usr").list();
         ss.close();
         return usersList;
    }
    
    public User getUserById(User user){
         Session ss = HibernateUtil.getSessionFactory().openSession();
         User usr= (User)ss.get(User.class, user.getUserId());
         ss.close();
         return usr;
    }
    
    public User login(User user) {
        Session ss = HibernateUtil.getSessionFactory().openSession();
        User result = (User) ss.createQuery(
            "select u from User u where u.username = :uname and u.password = :pwd")
            .setParameter("uname", user.getUsername())
            .setParameter("pwd", user.getPassword())
            .uniqueResult();
        ss.close();

        if (result != null) {
            String otp = String.valueOf((int)(Math.random() * 900000) + 100000);

            java.util.Date expiry = new java.util.Date(
                System.currentTimeMillis() + 5 * 60 * 1000
            );

            Session ss2 = HibernateUtil.getSessionFactory().openSession();
            Transaction tr = ss2.beginTransaction();
            result.setOtpCode(otp);
            result.setOtpExpiry(expiry);
            ss2.update(result);
            tr.commit();
            ss2.close();

            sendOtpEmail(result.getEmail(), otp);
        }

        return result;
    }
    
    public String verifyOtp(User user, String enteredOtp) {
    Session ss = HibernateUtil.getSessionFactory().openSession();
    User u = (User) ss.get(User.class, user.getUserId());
    ss.close();

    if (u == null) return "User not found";

    // Check expiry
    if (u.getOtpExpiry() == null || 
        new java.util.Date().after(u.getOtpExpiry())) {
        return "OTP has expired. Please request a new one";
    }

    // Check code
    if (u.getOtpCode().equals(enteredOtp)) {
        // Clear OTP after successful verification
        Session ss2 = HibernateUtil.getSessionFactory().openSession();
        Transaction tr = ss2.beginTransaction();
        u.setOtpCode(null);
        u.setOtpExpiry(null);
        ss2.update(u);
        tr.commit();
        ss2.close();
        return "SUCCESS";
    } else {
        return "Invalid OTP code. Please try again";
    }
}

    public String resendOtp(User user) {
        String newOtp = String.valueOf((int)(Math.random() * 900000) + 100000);

        java.util.Date expiry = new java.util.Date(
            System.currentTimeMillis() + 5 * 60 * 1000
        );

        Session ss = HibernateUtil.getSessionFactory().openSession();
        Transaction tr = ss.beginTransaction();
        User u = (User) ss.get(User.class, user.getUserId());
        u.setOtpCode(newOtp);
        u.setOtpExpiry(expiry);
        ss.update(u);
        tr.commit();
        ss.close();

        sendOtpEmail(u.getEmail(), newOtp);

        return "New OTP sent to " + u.getEmail();
    }

    private void sendOtpEmail(String recipientEmail, String otp) {
        try {
            org.apache.activemq.ActiveMQConnectionFactory factory =
                new org.apache.activemq.ActiveMQConnectionFactory("tcp://localhost:61616");
            javax.jms.Connection connection = factory.createConnection();
            connection.start();
            javax.jms.Session jmsSession = connection.createSession(
                false, javax.jms.Session.AUTO_ACKNOWLEDGE);
            javax.jms.Queue queue = jmsSession.createQueue("OTP_QUEUE");
            javax.jms.MessageProducer producer = jmsSession.createProducer(queue);
            javax.jms.TextMessage message = jmsSession.createTextMessage();
            message.setText("OTP:" + otp + ":EMAIL:" + recipientEmail);
            producer.send(message);
            jmsSession.close();
            connection.close();
            System.out.println("OTP event published to ActiveMQ queue for: " + recipientEmail);

            java.util.Properties props = new java.util.Properties();
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.host", "smtp.gmail.com");
            props.put("mail.smtp.port", "587");

            javax.mail.Session mailSession = javax.mail.Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
                        return new javax.mail.PasswordAuthentication(
                            "nsumbaherve11@gmail.com",
                            "bqmeekohwcbtahsv"
                        );
                    }
                });

            javax.mail.Message email = new javax.mail.internet.MimeMessage(mailSession);
            email.setFrom(new javax.mail.internet.InternetAddress("nsumbaherve11@gmail.com"));
            email.setRecipients(
                javax.mail.Message.RecipientType.TO,
                javax.mail.internet.InternetAddress.parse(recipientEmail)
            );
            email.setSubject("SME Trade Credit — Your OTP Verification Code");
            email.setText(
                "Dear User,\n\n" +
                "Your OTP verification code is: " + otp + "\n\n" +
                "This code expires in 5 minutes.\n\n" +
                "If you did not request this code, please ignore this email.\n\n" +
                "SME Trade Credit Financing System\n" +
                "Adventist University of Central Africa\n" +
                "INSY 7312 — Java Programming"
            );

            javax.mail.Transport.send(email);
            System.out.println("OTP email sent successfully to: " + recipientEmail);

        } catch (Exception e) {
            System.out.println("Error sending OTP email: " + e.getMessage());
            System.out.println("OTP for " + recipientEmail + ": " + otp);
        }
    }


}
