package name.vaccari.matteo.unitdoctor.core;

import org.jmock.Expectations;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.*;

import static org.junit.Assert.*;

public class UnitDoctorTest {
    @Rule
    public JUnitRuleMockery context = new JUnitRuleMockery();

    @Test
    public void willConvertInchesToMeters() throws Exception {
        final UnitDoctorView view = context.mock(UnitDoctorView.class);

        UnitDoctor unitDoctor = new UnitDoctor(view);

        context.checking(new Expectations() {{
            allowing(view).getFromUnit();
            will(returnValue("in"));

            allowing(view).getToUnit();
            will(returnValue("cm"));

            allowing(view).getInputNumber();
            will(returnValue(10.0));

            oneOf(view).showConversion(25.4);
        }});

        unitDoctor.onChange();
    }
}
