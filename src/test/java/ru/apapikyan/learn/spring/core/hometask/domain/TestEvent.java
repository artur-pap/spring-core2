package ua.epam.spring.hometask.domain;

import org.junit.Before;
import org.junit.Test;
import ru.apapikyan.learn.spring.core.hometask.domain.EventRating;
import ru.apapikyan.learn.spring.core.hometask.domain.EventV2;

import java.time.LocalDateTime;

import static org.junit.Assert.*;

/**
 * @author Yuriy_Tkach
 */
public class TestEvent {

	private EventV2 event;

	@Before
	public void initEvent() {
		event = new EventV2("aaa", EventRating.HIGH, 1.1);
		LocalDateTime now = LocalDateTime.now();

//TODO UNCOMMENT!!!!! AND FIX
//		event.addAirDateTime(now);
//		event.addAirDateTime(now.plusDays(1));
//		event.addAirDateTime(now.plusDays(2));
	}
	
	@Test
	public void testAddRemoveAirDates() {
		//int size = event.getAirDates().size();
		
		LocalDateTime date = LocalDateTime.now().plusDays(5);

//TODO UNCOMMENT!!!!! AND FIX
//		event.addAirDateTime(date);
		
//		assertEquals(size+1, event.getAirDates().size());
//		assertTrue(event.getAirDates().contains(date));
//TODO UNCOMMENT!!!!! AND FIX
//		event.removeAirDateTime(date);
		
//		assertEquals(size, event.getAirDates().size());
//		assertFalse(event.getAirDates().contains(date));
	}
	
	@Test
	public void testCheckAirDates() {

//TODO UNCOMMENT!!!!! AND FIX
//		assertTrue(event.airsOnDate(LocalDate.now()));
//		assertTrue(event.airsOnDate(LocalDate.now().plusDays(1)));
//		assertFalse(event.airsOnDate(LocalDate.now().minusDays(10)));
//
//		assertTrue(event.airsOnDates(LocalDate.now(), LocalDate.now().plusDays(10)));
//		assertTrue(event.airsOnDates(LocalDate.now().minusDays(10), LocalDate.now().plusDays(10)));
//		assertTrue(event.airsOnDates(LocalDate.now().plusDays(1), LocalDate.now().plusDays(1)));
//		assertFalse(event.airsOnDates(LocalDate.now().minusDays(10), LocalDate.now().minusDays(5)));
//
//		LocalDateTime time = LocalDateTime.now().plusHours(4);
//		event.addAirDateTime(time);
//		assertTrue(event.airsOnDateTime(time));
//		time = time.plusHours(30);
//		assertFalse(event.airsOnDateTime(time));
	}
	
	@Test
	public void testAddRemoveAuditoriums() {
//		LocalDateTime time = event.getAirDates().first();

//TODO UNCOMMENT!!!!! AND FIX
//		assertTrue(event.getAuditoriums().isEmpty());
//
//		event.assignAuditorium(time, new Auditorium());
//
//		assertFalse(event.getAuditoriums().isEmpty());
//
//		event.removeAuditoriumAssignment(time);
		
//		assertTrue(event.getAuditoriums().isEmpty());
	}
	
	@Test
	public void testAddRemoveAuditoriumsWithAirDates() {
		LocalDateTime time = LocalDateTime.now().plusDays(10);
		
//		assertTrue(event.getAuditoriums().isEmpty());

//TODO UNCOMMENT!!!!! AND FIX
//		event.addAirDateTime(time, new Auditorium());
//
//		assertFalse(event.getAuditoriums().isEmpty());
//
//		event.removeAirDateTime(time);
		
//		assertTrue(event.getAuditoriums().isEmpty());
	}
	
	@Test
	public void testNotAddAuditoriumWithoutCorrectDate() {
		LocalDateTime time = LocalDateTime.now().plusDays(10);
//TODO UNCOMMENT!!!!! AND FIX
//		boolean result = event.assignAuditorium(time, new Auditorium());
//
//		assertFalse(result);
//		assertTrue(event.getAuditoriums().isEmpty());
//
//		result = event.removeAirDateTime(time);
//		assertFalse(result);
//
//		assertTrue(event.getAuditoriums().isEmpty());
	}

}
