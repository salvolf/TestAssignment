package io.backbase.backbasetestassignment.main;

import android.view.View;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import io.backbase.backbasetestassignment.main.model.BookmarkedPlace;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

public class LocationItemViewModelTest {

    @Mock private BookmarkedPlace mockPlace;
    @Mock private LocationItemViewModel.LocationAdapterCallback mockCallback;

    private LocationItemViewModel subject;
    @Before
    public void setUp() throws Exception {
        initMocks(this);
        subject = new LocationItemViewModel(mockPlace, mockCallback);
    }

    @Test
    public void itShouldNotifyCallbackOnClickWhenOnClick() throws Exception {
        subject.onClick(any(View.class));
        verify(mockCallback).onClick(mockPlace);
    }
    @Test
    public void itShouldNotifyCallbackOnDeleteClickedWhenOnDeleteClicked() throws Exception {
        subject.onDeleteClicked();
        verify(mockCallback).onDeleteClicked(mockPlace);
    }
}