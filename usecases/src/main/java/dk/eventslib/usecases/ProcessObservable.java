package dk.eventslib.usecases;

public interface ProcessObservable{
    void addProcessObserver(ProcessObserver observer);
    void removeProcessObserver(ProcessObserver observer);
}
