package dk.events.a6.android.usecases.createevent.fsm;

public enum  PrepareEventStateImpl implements PrepareEventState {

    NO_TITLE_IMG_DESC{
        @Override
        public void startPrepareEvent(PrepareEventFSM fsm) {
            fsm.DoSetupPrepareEvent();
        }

        @Override
        public void yesTitle(PrepareEventFSM fsm) {
            fsm.setState(YES_TITLE_NO_IMG_DESC);
            fsm.DoTitleProvided();
        }

        @Override
        public void noTitle(PrepareEventFSM fsm) {
            //stay in the same state
            fsm.DoTitleRemoved();
        }

        @Override
        public void yesDesc(PrepareEventFSM fsm) {
            fsm.setState(YES_DESC_NO_TITLE_IMG);
            fsm.DoDescProvided();
        }

        @Override
        public void noDesc(PrepareEventFSM fsm) {
            //stay in the same state
            fsm.DoDescRemoved();
        }

        @Override
        public void yesImg(PrepareEventFSM fsm) {
            fsm.setState(YES_IMG_NO_TITLE_DESC);
            fsm.DoImgProvided();
        }

        @Override
        public void noImg(PrepareEventFSM fsm) {
            //stay in the same state
            fsm.DoImgRemoved();
        }

        @Override
        public void createEventPressed(PrepareEventFSM fsm) {
            //should not be possible
        }
    },

    YES_TITLE_NO_IMG_DESC{
        @Override
        public void startPrepareEvent(PrepareEventFSM fsm) {
            //should not be called in this state
        }

        @Override
        public void yesTitle(PrepareEventFSM fsm) {
            //stay in the same state
            fsm.DoTitleRemoved();
        }

        @Override
        public void noTitle(PrepareEventFSM fsm) {
            fsm.setState(NO_TITLE_IMG_DESC);
            fsm.DoTitleRemoved();
        }

        @Override
        public void yesDesc(PrepareEventFSM fsm) {
            fsm.setState(YES_DESC_TITLE_NO_IMG);
            fsm.DoDescProvided();
        }

        @Override
        public void noDesc(PrepareEventFSM fsm) {
            //do nothing
            fsm.DoDescRemoved();
        }

        @Override
        public void yesImg(PrepareEventFSM fsm) {
            fsm.setState(YES_TITLE_IMG_NO_DESC);
            fsm.DoImgProvided();
        }

        @Override
        public void noImg(PrepareEventFSM fsm) {
            //stay in current state
            fsm.DoImgRemoved();
        }

        @Override
        public void createEventPressed(PrepareEventFSM fsm) {
            //should not be possible
        }
    },
    YES_TITLE_IMG_NO_DESC{
        @Override
        public void startPrepareEvent(PrepareEventFSM fsm) {
            //should not be called in this state
        }

        @Override
        public void yesTitle(PrepareEventFSM fsm) {
            //stay in the same state
            fsm.DoTitleProvided();
        }

        @Override
        public void noTitle(PrepareEventFSM fsm) {
            fsm.setState(YES_IMG_NO_TITLE_DESC);
            fsm.DoTitleRemoved();
        }

        @Override
        public void yesDesc(PrepareEventFSM fsm) {
            fsm.setState(YES_TITLE_IMG_DESC);
            fsm.DoDescProvided();
            fsm.DoEnableCreateEvent();
        }

        @Override
        public void noDesc(PrepareEventFSM fsm) {
            //stay in the same state
            fsm.DoDescRemoved();
        }

        @Override
        public void yesImg(PrepareEventFSM fsm) {
            //stay in the same state
            fsm.DoImgProvided();
        }

        @Override
        public void noImg(PrepareEventFSM fsm) {
            fsm.setState(YES_TITLE_NO_IMG_DESC);
            fsm.DoImgRemoved();
        }

        @Override
        public void createEventPressed(PrepareEventFSM fsm) {
            //should not be possible
        }
    },
    YES_TITLE_IMG_DESC{
        @Override
        public void startPrepareEvent(PrepareEventFSM fsm) {
            //this is entry event, probably the activity was recreated, what to do here and when
        }

        @Override
        public void yesTitle(PrepareEventFSM fsm) {
            //stay in the same state
            fsm.DoTitleProvided();
        }

        @Override
        public void noTitle(PrepareEventFSM fsm) {
            fsm.setState(YES_IMG_DESC_NO_TITLE);
            fsm.DoTitleRemoved();
            fsm.DoDisableCreateEvent();
        }

        @Override
        public void yesDesc(PrepareEventFSM fsm) {
            //stay in the same state
            fsm.DoDescProvided();
        }

        @Override
        public void noDesc(PrepareEventFSM fsm) {
            fsm.setState(YES_TITLE_IMG_NO_DESC);
            fsm.DoDescRemoved();
            fsm.DoDisableCreateEvent();
        }

        @Override
        public void yesImg(PrepareEventFSM fsm) {
            //stay in the same state
            fsm.DoImgProvided();
        }

        @Override
        public void noImg(PrepareEventFSM fsm) {
            fsm.setState(YES_DESC_TITLE_NO_IMG);
            fsm.DoImgRemoved();
            fsm.DoDisableCreateEvent();
        }

        @Override
        public void createEventPressed(PrepareEventFSM fsm) {
            //here the state changes to another state something like IN_EVENT_CREATION_PROCESS in a parent fsm
            //this here is the lowest level fsm, the IN_EVENT_CREATION_PROCESS will be in a higher level fsm
            fsm.setState(IN_EVENT_CREATION_PROCESS);
            fsm.DoCreateEvent();

        }
    },
    IN_EVENT_CREATION_PROCESS{
        @Override
        public void startPrepareEvent(PrepareEventFSM fsm) {

        }

        @Override
        public void yesTitle(PrepareEventFSM fsm) {

        }

        @Override
        public void noTitle(PrepareEventFSM fsm) {

        }

        @Override
        public void yesDesc(PrepareEventFSM fsm) {

        }

        @Override
        public void noDesc(PrepareEventFSM fsm) {

        }

        @Override
        public void yesImg(PrepareEventFSM fsm) {

        }

        @Override
        public void noImg(PrepareEventFSM fsm) {

        }

        @Override
        public void createEventPressed(PrepareEventFSM fsm) {

        }
    },
    YES_DESC_NO_TITLE_IMG{
        @Override
        public void startPrepareEvent(PrepareEventFSM fsm) {
            //should not be called in this state
        }

        @Override
        public void yesTitle(PrepareEventFSM fsm) {
            fsm.setState(YES_DESC_TITLE_NO_IMG);
            fsm.DoTitleProvided();
        }

        @Override
        public void noTitle(PrepareEventFSM fsm) {
            //stay in the same state
            fsm.DoTitleRemoved();
        }

        @Override
        public void yesDesc(PrepareEventFSM fsm) {
            //do nothing
        }

        @Override
        public void noDesc(PrepareEventFSM fsm) {
            fsm.setState(NO_TITLE_IMG_DESC);
            fsm.DoDescRemoved();
        }

        @Override
        public void yesImg(PrepareEventFSM fsm) {
            fsm.setState(YES_IMG_DESC_NO_TITLE);
            fsm.DoImgProvided();
        }

        @Override
        public void noImg(PrepareEventFSM fsm) {
            //stay in the same state
            fsm.DoImgRemoved();
        }

        @Override
        public void createEventPressed(PrepareEventFSM fsm) {
            //should not be possible
        }
    },
    YES_DESC_TITLE_NO_IMG{
        @Override
        public void startPrepareEvent(PrepareEventFSM fsm) {
            //should not be called in this state
        }

        @Override
        public void yesTitle(PrepareEventFSM fsm) {
            //do nothing
        }

        @Override
        public void noTitle(PrepareEventFSM fsm) {
            fsm.setState(YES_DESC_NO_TITLE_IMG);
            fsm.DoTitleRemoved();
        }

        @Override
        public void yesDesc(PrepareEventFSM fsm) {
            //stay in the same state
            fsm.DoDescProvided();
        }

        @Override
        public void noDesc(PrepareEventFSM fsm) {
            fsm.setState(YES_TITLE_NO_IMG_DESC);
            fsm.DoDescRemoved();
        }

        @Override
        public void yesImg(PrepareEventFSM fsm) {
            fsm.setState(YES_TITLE_IMG_DESC);
            fsm.DoImgProvided();
            fsm.DoEnableCreateEvent();
        }

        @Override
        public void noImg(PrepareEventFSM fsm) {
            //do nothing
        }

        @Override
        public void createEventPressed(PrepareEventFSM fsm) {
            //should not be possible
        }
    },
    YES_IMG_NO_TITLE_DESC{
        @Override
        public void startPrepareEvent(PrepareEventFSM fsm) {
            //should not be called in this state
        }

        @Override
        public void yesTitle(PrepareEventFSM fsm) {
            fsm.setState(YES_TITLE_IMG_NO_DESC);
            fsm.DoTitleProvided();
        }

        @Override
        public void noTitle(PrepareEventFSM fsm) {
            //do nothing
        }

        @Override
        public void yesDesc(PrepareEventFSM fsm) {
            fsm.setState(YES_IMG_DESC_NO_TITLE);
            fsm.DoDescProvided();
        }

        @Override
        public void noDesc(PrepareEventFSM fsm) {
            //do nothing
        }

        @Override
        public void yesImg(PrepareEventFSM fsm) {
            //stay in the same state
            //in case new image is provided, hm
            //I might need to do the same for the Title and Desc when in state with Title or Desc
            fsm.DoImgProvided();
        }

        @Override
        public void noImg(PrepareEventFSM fsm) {
            //well this is not implemented yet and
            //might not be implemented at all but lets change the state and call the action for now
            fsm.setState(NO_TITLE_IMG_DESC);
            fsm.DoImgRemoved();
        }

        @Override
        public void createEventPressed(PrepareEventFSM fsm) {
            //this should not be possible
        }
    },
    YES_IMG_DESC_NO_TITLE{
        @Override
        public void startPrepareEvent(PrepareEventFSM fsm) {
            //should not be called in this state
        }

        @Override
        public void yesTitle(PrepareEventFSM fsm) {
            fsm.setState(YES_TITLE_IMG_DESC);
            fsm.DoTitleProvided();
            fsm.DoEnableCreateEvent();
        }

        @Override
        public void noTitle(PrepareEventFSM fsm) {
            //stay in the same state
            fsm.DoTitleRemoved();
        }

        @Override
        public void yesDesc(PrepareEventFSM fsm) {
            //stay in the same state
            fsm.DoDescProvided();
        }

        @Override
        public void noDesc(PrepareEventFSM fsm) {
            fsm.setState(YES_IMG_NO_TITLE_DESC);
            fsm.DoDescRemoved();
        }

        @Override
        public void yesImg(PrepareEventFSM fsm) {
            //stay in the same state
            fsm.DoImgProvided();
        }

        @Override
        public void noImg(PrepareEventFSM fsm) {
            fsm.setState(YES_DESC_NO_TITLE_IMG);
            fsm.DoImgRemoved();
        }

        @Override
        public void createEventPressed(PrepareEventFSM fsm) {
            //should not be possible
        }
    }
}
