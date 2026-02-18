package com.safetynet.alerts.safetynetalertsservice.util;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.appender.AbstractAppender;
import org.apache.logging.log4j.core.layout.PatternLayout;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;


public class InMemoryAppender extends AbstractAppender{
    private final List <LogEvent> events = Collections.synchronizedList(new ArrayList<>());

    public InMemoryAppender(String name){
        super(name, null, PatternLayout.createDefaultLayout(), true, null);
    }

    @Override
    public void append(LogEvent event) {
        events.add(event.toImmutable());
    }

    public List<LogEvent> getEvents(){
        return Collections.unmodifiableList(events);
    }

    public List<String> getMessages(){
        List<String> messages = new ArrayList<>();
        Iterator<LogEvent> iterator = events.iterator();
        while(iterator.hasNext()){
            LogEvent event = iterator.next();
            messages.add(event.getMessage().getFormattedMessage());
        }
        return messages;
    }

    public boolean containsMessage(String message){
        return events.stream().anyMatch(event -> event.getMessage().getFormattedMessage().equals(message));
    }
}
