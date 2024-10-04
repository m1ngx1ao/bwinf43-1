package test.excursion;
import static org.junit.Assert.*;

import java.util.LinkedList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import excursion.ExcursionResult;
import excursion.ExcursionOrganizer;

public class ExcursionOrganizerTest {
	ExcursionOrganizer cut;
	List<Integer> memberLowerBounds;
	List<Integer> memberUpperBounds;
	ExcursionResult result;

	@Before
	public void setup() {
		cut = new ExcursionOrganizer();
		memberLowerBounds = new LinkedList<Integer>();
		memberUpperBounds = new LinkedList<Integer>();
	}

	private void givenMember(int lowerBound, int upperBound) {
		memberLowerBounds.add(lowerBound);
		memberUpperBounds.add(upperBound);
	}

	private void whenExecute() {
		result = cut.execute(memberLowerBounds, memberUpperBounds);
	}

	@Test
	public void noMembersEmptyExcursion() {
		whenExecute();

		assertEquals(0, result.participantsByDistance.size());
		assertEquals(0, result.overallParticipants.size());
	}

	@Test
	public void twoMembersWithoutOverlapAllParticipate() {
		givenMember(1, 1);
		givenMember(3, 3);

		whenExecute();

		assertEquals(2, result.overallParticipants.size());
	}

	@Test
	public void threeMembersWithoutOverlapAllParticipate() {
		givenMember(1, 1);
		givenMember(3, 3);
		givenMember(5, 5);

		whenExecute();

		assertEquals(3, result.overallParticipants.size());
		assertEquals(0, result.participantsByDistance.get(1).iterator().next().intValue());
		assertEquals(1, result.participantsByDistance.get(3).iterator().next().intValue());
		assertEquals(2, result.participantsByDistance.get(5).iterator().next().intValue());
	}

	@Test
	public void fourMembersWithoutOverlapThreeParticipate() {
		givenMember(1, 2);
		givenMember(3, 4);
		givenMember(5, 6);
		givenMember(7, 8);

		whenExecute();

		assertEquals(3, result.overallParticipants.size());
	}

	@Test
	public void fourMembersWithOverlapAllParticipate() {
		givenMember(1, 2);
		givenMember(3, 4);
		givenMember(5, 6);
		givenMember(6, 7);

		whenExecute();

		assertEquals(4, result.overallParticipants.size());
		assertTrue(result.participantsByDistance.containsKey(6));
	}

	@Test
	public void threeMemberPairsOneOddSixParticipate() {
		givenMember(1, 2);
		givenMember(1, 2);
		givenMember(3, 4);
		givenMember(5, 6);
		givenMember(5, 6);
		givenMember(7, 8);
		givenMember(7, 8);

		whenExecute();

		assertEquals(6, result.overallParticipants.size());
		assertFalse(result.overallParticipants.contains(2));
	}
	
	@Test
	public void eigthMembersSevenParticipants() {
		//	 123456789
		// M0:  ***	 
		// M1:	  *** 
		// M2:	***   
		// M3:	 *	
		// M4:		**	 
		// M5: **	   
		// M6:   **	  
		// M7:	*	 
		//	 123456789

		// one optimal solution here (leaving out M3)
		// E0:  *	   
		// E1:	*	 
		// E2:		* 

		givenMember(2, 4);
		givenMember(6, 8);
		givenMember(4, 6);
		givenMember(5, 5);
		givenMember(8, 9);
		givenMember(1, 2);
		givenMember(3, 4);
		givenMember(4, 4);

		whenExecute();

		assertEquals(3, result.participantsByDistance.size());
		assertEquals(7, result.overallParticipants.size());
	}
	
	@Test
	public void exampleFile1OneMemberNotParticipating() {
		givenMember(12, 35);
		givenMember(22, 45);
		givenMember(46, 46);
		givenMember(49, 62);
		givenMember(51, 57);
		givenMember(64, 64);
		givenMember(64, 71);

		whenExecute();

		assertEquals(3, result.participantsByDistance.size());
		assertEquals(6, result.overallParticipants.size());
	}
	
	@Test
	public void exampleFile2AllParticipate() {
		givenMember(60, 80);
		givenMember(90, 90);
		givenMember(40, 80);
		givenMember(40, 60);
		givenMember(10, 30);
		givenMember(10, 50);

		whenExecute();

		assertEquals(3, result.participantsByDistance.size());
		assertTrue(result.participantsByDistance.containsKey(60));
		assertTrue(result.participantsByDistance.containsKey(90));
		assertEquals(6, result.overallParticipants.size());
	}
}
