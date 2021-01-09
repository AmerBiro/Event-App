package dk.events.a6.android.usecases.presentevents;

import java.util.List;

public interface PresentEventsPresenterObserver {
    void starting();
    void pending();
    void onSuccess(List<PresentableEvent> presentableEvents);
    void onFailure(String errorMsg);
}
