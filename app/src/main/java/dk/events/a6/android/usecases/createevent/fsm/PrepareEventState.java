package dk.events.a6.android.usecases.createevent.fsm;

public interface PrepareEventState {
    void startPrepareEvent(PrepareEventFSM fsm);

    void yesTitle(PrepareEventFSM fsm);
    void noTitle(PrepareEventFSM fsm);
    void yesDesc(PrepareEventFSM fsm);
    void noDesc(PrepareEventFSM fsm);
    void yesImg(PrepareEventFSM fsm);
    void noImg(PrepareEventFSM fsm);

    void createEventPressed(PrepareEventFSM fsm);
}
