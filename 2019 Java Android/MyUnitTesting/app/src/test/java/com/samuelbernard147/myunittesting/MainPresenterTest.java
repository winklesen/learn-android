package com.samuelbernard147.myunittesting;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class MainPresenterTest {
    @Mock
    private MainPresenter presenter;
    private MainView view;

    @Before
    public void setUp() {
        view = mock(MainView.class);
        presenter = new MainPresenter(view);
    }

    @Test
    public void testVolumeWithIntegerInput(){
        double volume = presenter.volume(2,8,1);
        assertEquals(16,volume ,0.0001);
    }

    @Test
    public void testVolumeWithDoubleInput() {
        double volume = presenter.volume(2.3, 8.1, 2.9);
        assertEquals(54.026999999999994, volume, 0.0001);
    }
    @Test
    public void testVolumeWithZeroInput() {
        double volume = presenter.volume(0, 0, 0);
        assertEquals(0.0, volume, 0.0001);
    }


    @Test
    public void testCalculateVolume() {
        presenter.calculateVolume(11.1, 2.2, 1);
        verify(view).showVolume(any(MainModel.class));
    }

    @Test
    public void volume() {

    }

    @Test
    public void calculateVolume() {
    }
}