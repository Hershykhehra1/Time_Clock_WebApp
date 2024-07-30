package cs3220.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import cs3220.model.TimeClock;
import cs3220.model.TimeClockDto;
import cs3220.model.UserDto;
import cs3220.model.UserEntry;
import cs3220.repository.TimeClockRepository;
import cs3220.repository.UserRepository;

@RestController
@RequestMapping("/api")
public class ApiController {

    private final UserRepository userRepository;
    private final TimeClockRepository timeClockRepository;

    public ApiController(UserRepository userRepository, TimeClockRepository timeClockRepository) {
        this.userRepository = userRepository;
        this.timeClockRepository = timeClockRepository;
    }

    @PostMapping("/Login")
    public Integer postLogin(@RequestBody UserDto userdto) {
        Integer userId = 0;
        List<UserEntry> users = userRepository.findByEmail(userdto.getEmail());

        if (!users.isEmpty()) {
            UserEntry user1 = users.get(0);
            if (user1.getPassword().equals(userdto.getPassword())) {
                userId = user1.getId();
            } else {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Email or Password is Incorrect");
            }
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User Doesn't Exist");
        }
        return userId;
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public Integer postRegister(@RequestBody UserDto userdto) {
        List<UserEntry> users = userRepository.findByEmail(userdto.getEmail());
        if (userdto.getEmail().isEmpty() || userdto.getPassword().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email or Password is Empty");
        }
        if (!users.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User Already Exists");
        }
        UserEntry user = userRepository.save(userdto.newUser());
        return user.getId();
    }

    @PostMapping("/clockin")
    @ResponseStatus(HttpStatus.OK)
    public void clockIn(@RequestBody TimeClockDto timeClockDto) {
        TimeClock timeClock = new TimeClock();
        timeClock.setUserId(timeClockDto.getUserId());
        timeClock.setUserName(userRepository.findById(timeClockDto.getUserId()).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found")).getName());
        timeClock.setClockInTime(LocalDateTime.now());
        timeClockRepository.save(timeClock);
    }

    @PostMapping("/clockout")
    @ResponseStatus(HttpStatus.OK)
    public void clockOut(@RequestBody TimeClockDto timeClockDto) {
        List<TimeClock> entries = timeClockRepository.findByUserIdAndClockOutTimeIsNull(timeClockDto.getUserId());
        if (!entries.isEmpty()) {
            TimeClock entry = entries.get(entries.size() - 1); // Get the most recent open entry
            entry.setClockOutTime(LocalDateTime.now());
            timeClockRepository.save(entry);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No open time clock entry found for the user");
        }
    }

    @GetMapping("/timeclock/{userId}")
    public List<TimeClockDto> getTimeClockEntries(@PathVariable Integer userId) {
        List<TimeClock> entries = (List<TimeClock>) timeClockRepository.findAll();
        return entries.stream().map(entry -> {
            List<TimeClockDto> timeClockDtos = new ArrayList<>();
            timeClockDtos.add(new TimeClockDto(entry.getUserId(), entry.getUserName(), entry.getClockInTime(), null, "Clocked In"));
            if (entry.getClockOutTime() != null) {
                timeClockDtos.add(new TimeClockDto(entry.getUserId(), entry.getUserName(), null, entry.getClockOutTime(), "Clocked Out"));
            }
            return timeClockDtos;
        }).flatMap(List::stream).collect(Collectors.toList());
    }




}