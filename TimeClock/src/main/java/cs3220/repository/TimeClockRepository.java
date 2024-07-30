package cs3220.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import cs3220.model.TimeClock;

public interface TimeClockRepository extends CrudRepository<TimeClock, Integer>{
	Optional<TimeClock> findById(Integer id);
	List<TimeClock> findByUserIdAndClockOutTimeIsNull(Integer userId);
	List<TimeClock> findByUserIdOrderByClockInTimeDesc(Integer userId);

}
