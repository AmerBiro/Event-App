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

    YES_TITLE_NO_IMG_DESC{
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
    YES_TITLE_IMG_NO_DESC{
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
    YES_TITLE_IMG_DESC{
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
    YES_DESC_TITLE_NO_IMG{
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
    YES_IMG_NO_TITLE_DESC{
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
    YES_IMG_DESC_NO_TITLE{
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
    }
}
