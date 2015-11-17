package given.a.spec.with.passing.and.failing.tests;

import static com.greghaskins.spectrum.Spectrum.afterEach;
import static com.greghaskins.spectrum.Spectrum.beforeEach;
import static com.greghaskins.spectrum.Spectrum.describe;
import static com.greghaskins.spectrum.Spectrum.it;
import static org.mockito.Mockito.mock;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.Description;
import org.junit.runner.RunWith;
import org.junit.runner.Runner;
import org.junit.runner.notification.RunNotifier;
import org.mockito.InOrder;
import org.mockito.Mockito;

import com.greghaskins.spectrum.Spectrum;

public class WhenRunningTheTests {
    private static final String CONTEXT_NAME = "a spec with three passing and two failing tests";

    private final RunNotifier runNotifier = mock(RunNotifier.class);

    {
        describe("Start and finished notifications", () -> {
            beforeEach(() -> {
                final Runner runner = new Spectrum(Fixture.getSpecWithPassingAndFailingTests());
                runner.run(runNotifier);
            });

            afterEach(() -> {
                Mockito.validateMockitoUsage();
                Mockito.reset(runNotifier);
            });

            it("Should fire the start failure and finished notifications for failing tests", () -> {
                final Description descriptionOfFailingTest = Description.createTestDescription(CONTEXT_NAME, "fails test 1");

                final InOrder inOrder = Mockito.inOrder(runNotifier);
                inOrder.verify(runNotifier).fireTestStarted(descriptionOfFailingTest);
                inOrder.verify(runNotifier).fireTestFailure(Mockito.any());
                inOrder.verify(runNotifier).fireTestFinished(descriptionOfFailingTest);
            });

            it("Should fire the start and finished notifications for passing tests", () -> {
                final Description descriptionOfPassingTest = Description.createTestDescription(CONTEXT_NAME, "passes test 3");

                final InOrder inOrder = Mockito.inOrder(runNotifier);
                inOrder.verify(runNotifier).fireTestStarted(descriptionOfPassingTest);
                inOrder.verify(runNotifier).fireTestFinished(descriptionOfPassingTest);
            });
        });
    }
}
