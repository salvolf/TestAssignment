package io.backbase.backbasetestassignment.main;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;

import io.backbase.backbasetestassignment.main.model.BookmarkedPlace;

import static junit.framework.Assert.assertEquals;
import static org.mockito.MockitoAnnotations.initMocks;

public class LocationAdapterTest {
    @Mock private List<BookmarkedPlace> stubPlaceList;
    @Mock private BookmarkedPlace mockPlace;
    @Mock private LocationAdapter.LocationAdapterCallback mockCallback;

    private LocationAdapter subject;

    @Before
    public void setUp() throws Exception {
        initMocks(this);
        stubPlaceList = new ArrayList<BookmarkedPlace>();
        stubPlaceList.add(mockPlace);
        stubPlaceList.add(mockPlace);
        stubPlaceList.add(mockPlace);
        stubPlaceList.add(mockPlace);
        subject = new LocationAdapter(stubPlaceList, mockCallback);
    }

    @Test
    public void itShouldReturnCorrectCountOnGetItemCount() throws Exception {
        assertEquals(4, subject.getItemCount());
    }

}