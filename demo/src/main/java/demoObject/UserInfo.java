package demoObject;

import java.util.Date;

/**
 * Created by johnny.ly on 2016/1/12.
 */
public class UserInfo {
        private long userId;
        private String userName;
        private int age;
        private String emailAddress;
        private Date birthday;

        public long getUserId() {
            return userId;
        }
        public void setUserId(long userId) {
            this.userId = userId;
        }
        public String getUserName() {
            return userName;
        }
        public void setUserName(String userName) {
            this.userName = userName;
        }
        public int getAge() {
            return age;
        }
        public void setAge(int age) {
            this.age = age;
        }
        public String getEmailAddress() {
            return emailAddress;
        }
        public void setEmailAddress(String emailAddress) {
            this.emailAddress = emailAddress;
        }
        public Date getBirthday() {
            return birthday;
        }
        public void setBirthday(Date birthday) {
            this.birthday = birthday;
        }
}
