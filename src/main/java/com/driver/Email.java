package com.driver;

public class Email {

    private String emailId;
    private String password;

    public void setPassword(String password) {
        this.password = password;
    }

    public Email(String emailId){
        this.emailId = emailId;
        this.password = "Accio@123";
    }

    public String getEmailId() {
        return emailId;
    }

    public String getPassword() {
        return password;
    }

    public void changePassword(String oldPassword, String newPassword){
        boolean isValid = checkCase(newPassword , oldPassword);
        if (isValid){
            setPassword(newPassword);
        }
    }

    private boolean checkCase(String newPassword , String oldPassword) {
        boolean isSameAsOldPass = oldPassword.equals(newPassword);
        boolean containsEightDig = newPassword.length() >= 8 ;
        boolean isUpperCase = false;
        boolean isSpecialCase = false;
        boolean isLowerCase = false;
        boolean isNumber = false;

        for (char ch : newPassword.toCharArray()){
            if(ch >= 65 && ch <= 90)
               isUpperCase = true ;
            else if(ch >= 97 && ch <= 122)
                isLowerCase = true;
            else if(ch >= 48 && ch <= 57)
                isNumber = true;
            else
                isSpecialCase = true;
        }

        return !isSameAsOldPass && isLowerCase && isUpperCase &&
                isSpecialCase && isNumber && containsEightDig;
    }
}
