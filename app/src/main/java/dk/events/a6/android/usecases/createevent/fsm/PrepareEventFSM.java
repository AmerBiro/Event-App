package dk.events.a6.android.usecases.createevent.fsm;

public abstract class PrepareEventFSM {
    private PrepareEventState state;


    public void yesTitle(){
        state.yesTitle(this);
    }
    public void yesDesc(){
        state.yesDesc(this);
    }
    public void yesImg(){
        state.yesDesc(this);
    }

    public void setState(PrepareEventState state){
        this.state = state;
    }

    //| prepare event final state machine transition table      |
    //|=========================================================|
    //| current state | event | new state |      action         |
    //|=========================================================|
    //|    -       | back  |     -     |         -              |
    //|=========================================================|

    public abstract void DoTitleProvided();
    public abstract void DoTitleRemoved();
    public abstract void DoDescProvided();
    public abstract void DoDescRemoved();
    public abstract void DoImgProvided();
    public abstract void DoImgRemoved();

    public abstract void DoSetupPrepareEvent();
    public abstract void DoEnableCreateEvent();
    public abstract void DoDisableCreateEvent();
}
