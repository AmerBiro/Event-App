package dk.events.a6.android.usecases.createevent.fsm;

public class PrepareEventFSMWrapper extends PrepareEventFSM{
    public PrepareEventFSMActions actionsImpl;

    @Override
    public void DoTitleProvided() {
        actionsImpl.DoTitleProvided();
    }

    @Override
    public void DoTitleRemoved() {
        actionsImpl.DoTitleRemoved();
    }

    @Override
    public void DoDescProvided() {
        actionsImpl.DoDescProvided();
    }

    @Override
    public void DoDescRemoved() {
        actionsImpl.DoDescRemoved();
    }

    @Override
    public void DoImgProvided() {
        actionsImpl.DoImgProvided();
    }

    @Override
    public void DoImgRemoved() {
        actionsImpl.DoImgRemoved();
    }

    @Override
    public void DoSetupPrepareEvent() {
        actionsImpl.DoSetupPrepareEvent();
    }

    @Override
    public void DoEnableCreateEvent() {
        actionsImpl.DoEnableCreateEvent();
    }

    @Override
    public void DoDisableCreateEvent() {
        actionsImpl.DoDisableCreateEvent();
    }
}
