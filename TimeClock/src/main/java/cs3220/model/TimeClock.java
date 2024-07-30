package cs3220.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class TimeClock {

	@Id
	@GeneratedValue
	private Integer id;
	private Integer userId;
	private String userName;
	private LocalDateTime clockInTime;
	private LocalDateTime clockOutTime;
	
	public TimeClock() {
        this.id = null; 
    }


	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
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
}
