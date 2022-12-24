package com.driver;

import java.awt.*;
import java.util.*;

public class Gmail extends Email {

    int inboxCapacity; //maximum number of mails inbox can store
    TreeMap<Date , String> inboxMails = new TreeMap<>();
    TreeMap<Date , String> trashMails = new TreeMap<>();
    Date oldestDate = new Date();

    public void setInboxCapacity(int inboxCapacity) {
        this.inboxCapacity = inboxCapacity;
    }

    //Inbox: Stores mails. Each mail has date (Date), sender (String), message (String). It is guaranteed that message is distinct for all mails.
    //Trash: Stores mails. Each mail has date (Date), sender (String), message (String)
    public Gmail(String emailId, int inboxCapacity) {
        super(emailId);
        setInboxCapacity(inboxCapacity);
    }

    public void receiveMail(Date date, String sender, String message){
        // If the inbox is full, move the oldest mail in the inbox to trash and add the new mail to inbox.
        // It is guaranteed that:
        // 1. Each mail in the inbox is distinct.
        // 2. The mails are received in non-decreasing order. This means that the date of a new mail is greater than equal to the dates of mails received already.
        if (getInboxCapacity() <= inboxMails.size()){
            findOldestMessage();
            trashMails.put(oldestDate , inboxMails.get(oldestDate));
            inboxMails.remove(oldestDate);
        }
        if (inboxMails.containsKey(date)){
            date = convertTime(date , new Date());
        }
        inboxMails.put(date , message);
    }

    private Date convertTime(Date date , Date time) {
        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        cal1.setTime(date);
        cal2.setTime(time);

        cal1.set(Calendar.HOUR_OF_DAY, cal2.get(Calendar.HOUR_OF_DAY));
        cal1.set(Calendar.MINUTE, cal2.get(Calendar.MINUTE));
        cal1.set(Calendar.SECOND, cal2.get(Calendar.SECOND) + 1);

        return cal1.getTime();
    }

    public void deleteMail(String message){
        for (Map.Entry<Date, String> mapElement : inboxMails.entrySet()) {
            Date key = mapElement.getKey();
            String value = mapElement.getValue();

            if (message.equals(value)){
               putIntoTrash(key , value);
            }
        }
    }

    public void putIntoTrash(Date date , String message){
        trashMails.put(date , message);
        inboxMails.remove(date);
    }

    public String findLatestMessage(){
        if (inboxMails.isEmpty()){
            return null;
        }
        return inboxMails.get(inboxMails.lastKey());
    }

    public String findOldestMessage(){
        if (inboxMails.isEmpty()){
            return null;
        }
        oldestDate = inboxMails.firstKey();
        return inboxMails.get(inboxMails.firstKey());
    }

    public int findMailsBetweenDates(Date start, Date end){
        //find number of mails in the inbox which are received between given dates
        //It is guaranteed that start date <= end date
        int noOfMessages = 0 ;
        for (Map.Entry<Date, String> mapElement : inboxMails.entrySet()) {
            Date key = mapElement.getKey();
            end = convertTime(end , new Date());
           if (isWithinRange(key , start , end)){
               noOfMessages++;
           }
        }
        return noOfMessages;
    }

    public int getInboxSize(){
       return inboxMails.size();
    }

    public int getTrashSize(){
      return trashMails.size();
    }

    public void emptyTrash(){
        // clear all mails in the trash
        trashMails.clear();
    }

    public int getInboxCapacity() {
        return this.inboxCapacity;
    }

    boolean isWithinRange(Date testDate ,Date startDate , Date endDate) {
        return !(testDate.before(startDate) || testDate.after(endDate));
    }
}
