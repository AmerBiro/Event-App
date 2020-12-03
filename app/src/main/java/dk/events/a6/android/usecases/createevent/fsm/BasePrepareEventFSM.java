package dk.events.a6.android.usecases.createevent.fsm;

public abstract class BasePrepareEventFSM {
    public interface PrepareEventFSMActions{
        void DoTitleProvided();
        void DoTitleRemoved();
        void DoDescProvided();
        void DoDescRemoved();
        void DoImgProvided();
        void DoImgRemoved();

        void DoSetupPrepareEvent();
        void DoEnableCreateEvent();
        void DoDisableCreateEvent();

        void DoCreateEvent();
    }
}
