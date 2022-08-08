package com.trilogyed.musicstorecatalog.service;

import com.trilogyed.musicstorecatalog.model.Label;
import com.trilogyed.musicstorecatalog.repository.LabelRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
public class LabelServiceTest {

    private LabelRepository labelRepository;

    private LabelService service;

    @Before
    public void setUp() throws Exception {
        setUpLabelRepository();
        service = new LabelService(labelRepository);
    }

    private void setUpLabelRepository() {
        this.labelRepository = mock(LabelRepository.class);
        Label newLabel1 = new Label("AmharicMusic", "www.amharic.music");

        Label savedLabel1 = new Label(1, "AmharicMusic", "www.amharic.music");

        Label newLabel2 = new Label("Oromic Music", "wwww.oromicmusic.com");

        Label savedLabel2 = new Label(2, "Oromic Music", "wwww.oromicmusic.com");

        List<Label> LabelList = Arrays.asList(savedLabel1, savedLabel2);

        Optional<Label> findByIdResult = Optional.of(savedLabel1);

        doReturn(savedLabel1).when(labelRepository).save(newLabel1);
        doReturn(savedLabel2).when(labelRepository).save(newLabel2);
        doReturn(findByIdResult).when(labelRepository).findById(1);
        doReturn(LabelList).when(labelRepository).findAll();

    }


    @Test
    public void getAllLabelShouldReturnAListOfLabels() {
        List<Label> actualResult = service.getLabels();
        assertEquals(2, actualResult.size());
    }

    @Test
    public void AddAndGetLabel() {
        Label label1 = new Label("AmharicMusic", "www.amharic.music");
        label1 = service.saveLabel(label1);
        Label actualResult = service.getLabel(label1.getId());
        assertEquals(label1, actualResult);

    }

    @Test
    public void shouldUpdateLabel() {
        Label label1 = new Label("AmharicMusic", "www.amharic.music");
        label1 = service.saveLabel(label1);
        label1.setWebsite("www.amharicmusic");
        service.updateLabel(label1);
        verify(labelRepository, times(2)).save(any(Label.class));
    }

    @Test(expected = NullPointerException.class)
    public void shouldFailToUpdateWithNullLabel() {
        Label label = null;

        service.updateLabel(label);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldFailToUpdateLabelWithBadId() {
        Label label1 = new Label("AmharicMusic", "www.amharic.music");
        label1 = service.saveLabel(label1);
        label1.setName("Tigrian music");
        label1.setId(label1.getId() + 1);
        service.updateLabel(label1);
    }

    @Test
    public void shouldDeleteLabelById() {
        Label label1 = new Label("AmharicMusic", "www.amharic.music");
        label1 = service.saveLabel(label1);
        service.deleteLabel(label1.getId());
        verify(labelRepository).deleteById(label1.getId());
    }

}