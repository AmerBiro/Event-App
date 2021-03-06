package dk.events.a6.android.usecases.createevent.fsm;

public abstract class PrepareEventFSM extends BasePrepareEventFSM implements BasePrepareEventFSM.PrepareEventFSMActions{
    private PrepareEventState state;

    public void startPrepareEvent(){
        state.startPrepareEvent(this);
    }
    public void yesTitle(){
        state.yesTitle(this);
    }
    public void noTitle(){
        state.noTitle(this);
    }
    public void yesDesc(){
        state.yesDesc(this);
    }
    public void noDesc(){
        state.noDesc(this);
    }
    public void yesImg(){
        state.yesImg(this);
    }
    public void noImg(){
        state.noImg(this);
    }
    public void createEventPressed(){
        state.createEventPressed(this);
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

/* actions were pulled up in a common interface
        void DoTitleProvided();
        void DoTitleRemoved();
        void DoDescProvided();
        void DoDescRemoved();
        void DoImgProvided();
        void DoImgRemoved();

        void DoSetupPrepareEvent();
        void DoEnableCreateEvent();
        void DoDisableCreateEvent();
* */


}
