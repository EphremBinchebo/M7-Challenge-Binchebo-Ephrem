package com.trilogyed.musicstorerecommendations.service;

import com.trilogyed.musicstorerecommendations.model.LabelRecommendation;
import com.trilogyed.musicstorerecommendations.repository.LabelRecommendationRepository;
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
public class LabelRecommendationServiceTest {
    LabelRecommendationRepository labelRecommendationRepository;

    LabelRecommendationService service;

    @Before
    public void setUp() throws Exception {
        setUpLabelRecommendationRepositoryMock();
        service = new LabelRecommendationService(labelRecommendationRepository);
    }

    private void setUpLabelRecommendationRepositoryMock() {
        this.labelRecommendationRepository = mock(LabelRecommendationRepository.class);


        LabelRecommendation newLabelRec1 = new LabelRecommendation(1, 2, true);

        LabelRecommendation savedLabelRec1 = new LabelRecommendation(1, 1, 2, true);


        LabelRecommendation newLabelRec2 = new LabelRecommendation(11, 22, true);

        LabelRecommendation savedLabelRec2 = new LabelRecommendation(5, 11, 22, true);

        List<LabelRecommendation> LabelRecList = Arrays.asList(savedLabelRec1, savedLabelRec2);
        Optional<LabelRecommendation> findByIdResult = Optional.of(savedLabelRec1);


        doReturn(savedLabelRec1).when(labelRecommendationRepository).save(newLabelRec1);
        doReturn(savedLabelRec2).when(labelRecommendationRepository).save(newLabelRec2);
        // doReturn(Optional.of(savedLabelRec2)).when(LabelRecommendationRepository).findById(5);
        doReturn(findByIdResult).when(labelRecommendationRepository).findById(1);
        doReturn(LabelRecList).when(labelRecommendationRepository).findAll();
    }

    @Test
    public void shouldCreateLabelRecommendation() {
        LabelRecommendation labelIn = new LabelRecommendation(1, 2, true);

        LabelRecommendation labelOut = new LabelRecommendation(1, 1, 2, true);
        LabelRecommendation actualResult = service.saveLabelRecommendation(labelIn);

        assertEquals(labelOut, actualResult);
    }

    @Test
    public void shouldReturnNullForInvalidLabelId() {
        LabelRecommendation labelRecWithoutLabelId = new LabelRecommendation(0, 2, true);
        LabelRecommendation expectedResult = null;
        LabelRecommendation actualResult = service.saveLabelRecommendation(labelRecWithoutLabelId);

        assertEquals(null, actualResult);

    }

    @Test
    public void shouldReturnNullForInvalidUserId() {
        LabelRecommendation labelRecWithoutUserId = new LabelRecommendation(2, 0, true);
        LabelRecommendation expectedResult = null;
        LabelRecommendation actualResult = service.saveLabelRecommendation(labelRecWithoutUserId);

        assertEquals(null, actualResult);

    }

    @Test
    public void shouldReturnAllLabelRecommendationList() {
        List<LabelRecommendation> labelRecList = service.getAllLabelRecommendations();
        assertEquals(2, labelRecList.size());
    }

    @Test
    public void shouldReturnLabelRecommendationById() {
        LabelRecommendation labelIn = new LabelRecommendation(1, 2, true);
        labelIn = service.saveLabelRecommendation(labelIn);

        LabelRecommendation actualResult = service.getLabelRecommendation(labelIn.getId());

        assertEquals(labelIn, actualResult);
    }

    @Test
    public void shouldUpdateLabelRecommendation() {
        LabelRecommendation Label1 = new LabelRecommendation(1, 2, true);
        Label1 = service.saveLabelRecommendation(Label1);

        Label1.setLabelId(11);
        Label1.setLiked(false);
        service.updateLabelRecommendation(Label1);
        verify(labelRecommendationRepository, times(2)).save(any(LabelRecommendation.class));
    }

    @Test
    public void shouldDeleteLabelRecommendation() {
        LabelRecommendation label1 = new LabelRecommendation(1, 2, true);
        label1 = service.saveLabelRecommendation(label1);
        service.deleteLabelRecommendation(label1.getId());
        verify(labelRecommendationRepository).deleteById(label1.getId());
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldFailToUpdateLabelRecommendationWithBadId() {
        LabelRecommendation label1 = new LabelRecommendation(1, 2, true);
        label1 = service.saveLabelRecommendation(label1);

        label1.setLabelId(1);
        label1.setLiked(false);
        label1.setId(label1.getId() + 1);
        service.updateLabelRecommendation(label1);
    }

    @Test(expected = NullPointerException.class)
    public void shouldFailToUpdateWithNullLabelRecommendation() {
        LabelRecommendation labelRec1 = null;
        service.updateLabelRecommendation(labelRec1);
    }
}