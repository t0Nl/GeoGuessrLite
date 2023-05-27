package com.example.android.geoguessrlite.util;

import com.google.android.gms.maps.StreetViewPanorama.OnStreetViewPanoramaChangeListener;
import com.google.android.gms.maps.model.StreetViewPanoramaLocation;

import java.lang.ref.WeakReference;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class FixedOnStreetViewPanoramaChangeListener implements OnStreetViewPanoramaChangeListener {
    private final WeakReference<Listener> weakRef;

    public FixedOnStreetViewPanoramaChangeListener(Listener listener) {
        weakRef = new WeakReference<>(listener);
    }

    @Override
    public void onStreetViewPanoramaChange(@NonNull StreetViewPanoramaLocation location) {
        Listener listener = weakRef.get();
        if (listener != null) {
            listener.onStreetViewPanoramaChange(location);
        }
    }

    public interface Listener {
        void onStreetViewPanoramaChange(@Nullable StreetViewPanoramaLocation location);
    }
}
