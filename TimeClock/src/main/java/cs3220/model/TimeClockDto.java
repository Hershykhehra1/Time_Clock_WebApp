package cs3220.model;

import java.time.LocalDateTime;

public class TimeClockDto {

    private Integer userId;
    private String userName;
    private LocalDateTime clockInTime;
    private LocalDateTime clockOutTime;
    private String action;

    public TimeClockDto() {
    }

    public TimeClockDto(Integer userId, String userName, LocalDateTime clockInTime, LocalDateTime clockOutTime, String action) {
        this.userId = userId;
        this.userName = userName;
        this.clockInTime = clockInTime;
        this.clockOutTime = clockOutTime;
        this.action = action;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public LocalDateTime getClockInTime() {
        return clockInTime;
    }

    public void setClockInTime(LocalDateTime clockInTime) {
        this.clockInTime = clockInTime;
    }

    public LocalDateTime getClockOutTime() {
        return clockOutTime;
    }

    public void setClockOutTime(LocalDateTime clockOutTime) {
        this.clockOutTime = clockOutTime;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }
}