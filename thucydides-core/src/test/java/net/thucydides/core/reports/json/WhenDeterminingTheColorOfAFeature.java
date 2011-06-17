package net.thucydides.core.reports.json;

import net.thucydides.core.annotations.Feature;
import net.thucydides.core.model.FeatureResults;
import net.thucydides.core.model.StoryTestResults;
import net.thucydides.core.model.TestOutcome;
import net.thucydides.core.model.TestResult;
import net.thucydides.core.model.features.ApplicationFeature;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class WhenDeterminingTheColorOfAFeature {


    @Feature
    class WidgetFeature {
         class PurchaseNewWidget{};
         class SearchWidgets{};
         class DisplayWidgets{};
    }

    @Feature
    class GizmoFeature {
         class PurchaseNewGizmo{};
         class SearchGizmos{};
         class DisplayGizmos{};
    }

    @Feature
    class WozitFeature {
         class PurchaseNewWozit{};
         class SearchWozits{};
         class DisplayWozitss{};
    }

    FeatureResults widgetFeature;
    FeatureResults gizmoFeature;
    FeatureResults wozitFeature;


    ColorScheme colorScheme;

    @Before
    public void createColorScheme() {
        colorScheme = new ColorScheme();
    }

    @Test
    public void a_feature_with_no_tests_should_be_black() {
        FeatureResults feature = mockFeatureResults(WidgetFeature.class,100, 10, 0, 0,0,0);
        Color color = colorScheme.colorFor(feature);

        assertThat(color, is(Color.BLACK));
    }

    @Test
    public void a_completely_failing_feature_should_be_red() {
        FeatureResults feature = mockFeatureResults(WidgetFeature.class,100, 10, 20, 0,0,20);
        Color color = colorScheme.colorFor(feature);

        assertThat(color, is(Color.RED));
    }

    @Test
    public void a_completely_pending_feature_should_be_blue() {
        FeatureResults feature = mockFeatureResults(WidgetFeature.class,100, 10, 20, 0,20,0);
        Color color = colorScheme.colorFor(feature);

        assertThat(color, is(Color.BLUE));
    }

    @Test
    public void a_completely_passing_feature_should_be_green() {
        FeatureResults feature = mockFeatureResults(WidgetFeature.class,100, 10, 20, 20,0,0);
        Color color = colorScheme.colorFor(feature);

        assertThat(color, is(Color.GREEN));
    }

    @Test
     public void a_completely_failing_story_should_be_red() {
         StoryTestResults story = mockStory(20, 0,0,20);
         Color color = colorScheme.colorFor(story);

         assertThat(color, is(Color.RED));
     }

     @Test
     public void a_completely_pending_story_should_be_blue() {
         StoryTestResults story = mockStory(20, 0, 20, 0);
         Color color = colorScheme.colorFor(story);

         assertThat(color, is(Color.BLUE));
     }

     @Test
     public void a_completely_passing_story_should_be_green() {
         StoryTestResults story = mockStory(20, 20, 0, 0);
         Color color = colorScheme.colorFor(story);

         assertThat(color, is(Color.GREEN));
     }

    @Test
    public void a_successful_test_outcome_should_be_green() {
        TestOutcome outcome = mockTestOutcome(10, TestResult.SUCCESS);
        Color color = colorScheme.colorFor(outcome);

        assertThat(color, is(Color.GREEN));
    }

    @Test
    public void a_pending_test_outcome_should_be_blue() {
        TestOutcome outcome = mockTestOutcome(10, TestResult.PENDING);
        Color color = colorScheme.colorFor(outcome);

        assertThat(color, is(Color.BLUE));
    }

    @Test
    public void a_skipped_test_outcome_should_be_grey() {
        TestOutcome outcome = mockTestOutcome(10, TestResult.SKIPPED);
        Color color = colorScheme.colorFor(outcome);

        assertThat(color, is(Color.GRAY));
    }

    @Test
    public void an_ignored_test_outcome_should_be_orange() {
        TestOutcome outcome = mockTestOutcome(10, TestResult.IGNORED);
        Color color = colorScheme.colorFor(outcome);

        assertThat(color, is(Color.ORANGE));
    }

    @Test
    public void should_be_able_to_convert_a_color_to_hex() {
        assertThat(ColorScheme.rgbFormatOf(Color.RED), is("#ff0000"));
        assertThat(ColorScheme.rgbFormatOf(Color.GREEN), is("#00ff00"));
        assertThat(ColorScheme.rgbFormatOf(Color.BLUE), is("#0000ff"));
    }

    private FeatureResults mockFeatureResults(Class<?> featureClass,
                                              Integer stepCount,
                                              Integer storyCount,
                                              Integer testCount,
                                              Integer passingCount,
                                              Integer pendingCount,
                                              Integer failingCount) {
        FeatureResults feature = mock(FeatureResults.class);
        when(feature.getFeature()).thenReturn(ApplicationFeature.from(featureClass));
        when(feature.getTotalSteps()).thenReturn(stepCount);
        when(feature.getTotalStories()).thenReturn(storyCount);
        when(feature.getTotalTests()).thenReturn(testCount);
        when(feature.getPassingTests()).thenReturn(passingCount);
        when(feature.getPendingTests()).thenReturn(pendingCount);
        when(feature.getFailingTests()).thenReturn(failingCount);
        return feature;
    }

    private StoryTestResults mockStory(Integer testCount,
                                      Integer passingCount,
                                      Integer pendingCount,
                                      Integer failingCount) {
        StoryTestResults story = mock(StoryTestResults.class);
        when(story.getTotal()).thenReturn(testCount);
        when(story.getSuccessCount()).thenReturn(passingCount);
        when(story.getPendingCount()).thenReturn(pendingCount);
        when(story.getFailureCount()).thenReturn(failingCount);
        return story;
    }

    private TestOutcome mockTestOutcome(int stepCount, TestResult result) {
        TestOutcome outcome = mock(TestOutcome.class);
        when(outcome.getResult()).thenReturn(result);
        when(outcome.getStepCount()).thenReturn(stepCount);
        return outcome;
    }

}