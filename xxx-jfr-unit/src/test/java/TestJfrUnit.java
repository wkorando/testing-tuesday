import static org.moditect.jfrunit.ExpectedEvent.event;
import static org.moditect.jfrunit.JfrEventsAssert.assertThat;

import java.time.Duration;
import java.util.Collection;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.moditect.jfrunit.EnableEvent;
import org.moditect.jfrunit.JfrEventTest;
import org.moditect.jfrunit.JfrEvents;

@JfrEventTest
public class TestJfrUnit {
	public JfrEvents jfrEvents = new JfrEvents();

	@Test
	@EnableEvent("jdk.GarbageCollection")
	@EnableEvent("jdk.ThreadSleep")
    @EnableEvent("jdk.ObjectAllocationInNewTLAB")
    @EnableEvent("jdk.ObjectAllocationOutsideTLAB")
	public void shouldHaveGcAndSleepEvents() throws Exception {
		System.gc();
		Thread.sleep(1000);
		
		record Actor(String fName, String lName) {};
		record Superhero(String fName, String lName, String alias, Collection<Actor> actors) {};
		List<Actor> actors = List.of(new Actor("Robert", "Downey"));
		Superhero superhero = new Superhero("Tony", "Stark", "Ironman", actors);
		
		jfrEvents.awaitEvents();

		assertThat(jfrEvents).contains(event("jdk.GarbageCollection"));
		assertThat(jfrEvents).contains(event("jdk.ThreadSleep").with("time", Duration.ofSeconds(1)));
	}
}
