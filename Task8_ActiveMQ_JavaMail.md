# TASK 8 — ActiveMQ + JavaMail OTP Email Sending
# Project: SmeTradeCreditFinancingSystemsServer28028 (SERVER ONLY)
# Package: rw.rab.dao
# Class: UserDao.java
# Gmail: nsumbaherve11@gmail.com
# App Password: bqmeekohwcbtahsv

========================================================
REQUIRED SETUP BEFORE CODING:
========================================================

1. Download and add to SERVER project libraries:
   - activemq-all-5.15.9.jar
   - javax.mail-1.6.2.jar

2. Start ActiveMQ broker before running server:
   cd apache-activemq-5.15.9/bin
   ./activemq start

3. Add same JARs to CLIENT project (for exception deserialization)

========================================================
FILE: UserDao.java (SERVER)
ACTION: Replace the login() method with this complete version
========================================================

public User login(User user) {
    Session ss = HibernateUtil.getSessionFactory().openSession();
    User result = (User) ss.createQuery(
        "select u from User u where u.username = :uname and u.password = :pwd")
        .setParameter("uname", user.getUsername())
        .setParameter("pwd", user.getPassword())
        .uniqueResult();
    ss.close();

    if (result != null) {
        // Generate 6-digit OTP
        String otp = String.valueOf((int)(Math.random() * 900000) + 100000);

        // Set expiry 5 minutes from now
        java.util.Date expiry = new java.util.Date(
            System.currentTimeMillis() + 5 * 60 * 1000
        );

        // Save OTP to database
        Session ss2 = HibernateUtil.getSessionFactory().openSession();
        Transaction tr = ss2.beginTransaction();
        result.setOtpCode(otp);
        result.setOtpExpiry(expiry);
        ss2.update(result);
        tr.commit();
        ss2.close();

        // Send OTP via ActiveMQ + JavaMail
        sendOtpEmail(result.getEmail(), otp);
    }

    return result;
}

========================================================
FILE: UserDao.java (SERVER)
ACTION: Replace the resendOtp() method with this complete version
========================================================

public String resendOtp(User user) {
    // Generate new OTP
    String newOtp = String.valueOf((int)(Math.random() * 900000) + 100000);

    // Set new expiry 5 minutes from now
    java.util.Date expiry = new java.util.Date(
        System.currentTimeMillis() + 5 * 60 * 1000
    );

    // Save new OTP to database
    Session ss = HibernateUtil.getSessionFactory().openSession();
    Transaction tr = ss.beginTransaction();
    User u = (User) ss.get(User.class, user.getUserId());
    u.setOtpCode(newOtp);
    u.setOtpExpiry(expiry);
    ss.update(u);
    tr.commit();
    ss.close();

    // Send new OTP via ActiveMQ + JavaMail
    sendOtpEmail(u.getEmail(), newOtp);

    return "New OTP sent to " + u.getEmail();
}

========================================================
FILE: UserDao.java (SERVER)
ACTION: ADD this new helper method to the class
========================================================

private void sendOtpEmail(String recipientEmail, String otp) {
    try {
        // Step 1: Publish OTP event to ActiveMQ queue
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

        // Step 2: Send actual email via JavaMail + Gmail SMTP
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
        // Print to console as fallback so we can still test
        System.out.println("OTP for " + recipientEmail + ": " + otp);
    }
}

========================================================
NOTE: The sendOtpEmail method has a try/catch that falls back
to printing the OTP to console if email fails.
This means login still works even if email is not configured.
========================================================
