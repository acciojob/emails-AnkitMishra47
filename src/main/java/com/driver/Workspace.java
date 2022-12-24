package com.driver;

import org.apache.commons.lang3.tuple.Pair;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;

public class Workspace extends Gmail{

    private ArrayList<Meeting> calendar; // Stores all the meetings

    public Workspace(String emailId) {
        // The inboxCapacity is equal to the maximum value an integer can store.
        super(emailId , Integer.MAX_VALUE);
        calendar = new ArrayList<>();
    }

    public void addMeeting(Meeting meeting){
        //add the meeting to calendar
        calendar.add(meeting);
    }

    public int findMaxMeetings(){
        // find the maximum number of meetings you can attend
        // 1. At a particular time, you can be present in at most one meeting
        // 2. If you want to attend a meeting, you must join it at its start time and leave at end time.
        // Example: If a meeting ends at 10:00 am, you cannot attend another meeting starting at 10:00 am
        int noOfMeetingsAttended = 1 ;
        for (Meeting meeting : calendar){
                int canAttend = 1 ;
                Date meetingStartTime   = getDateFromLocalTime(meeting.getStartTime());
                Date meetingEndTime     = getDateFromLocalTime(meeting.getEndTime());

                for (Meeting meeting1 : calendar){
                    if (meeting1 != meeting){
                        Date startTime   = getDateFromLocalTime(meeting1.getStartTime());
                        Date endTime     = getDateFromLocalTime(meeting1.getEndTime());

                        boolean doOverLap = dateOverlaps( startTime , endTime , meetingStartTime , meetingEndTime );

                        if (!doOverLap && !meetingEndTime.after(startTime)){
                            canAttend++;
                        }
                    }
                }
            noOfMeetingsAttended = Math.max(noOfMeetingsAttended , canAttend);
        }
        return calendar.isEmpty() ? 0 : noOfMeetingsAttended;
    }

    private Date getDateFromLocalTime(LocalTime time) {
        Calendar cal1 = Calendar.getInstance();

        cal1.set(Calendar.HOUR_OF_DAY , time.getHour());
        cal1.set(Calendar.MINUTE , time.getMinute());
        cal1.set(Calendar.SECOND , time.getSecond());

        return cal1.getTime();
    }


    public boolean dateOverlaps(Date start1 , Date end1 , Date start2 , Date end2){
        return isWithinRange(start1 , start2, end2) || isWithinRange(end1 , start2 , end2);
    }
}
