package abapci.manager;

import java.util.List;

import org.eclipse.core.resources.IProject;

import abapci.ci.presenter.ContinuousIntegrationPresenter;
import abapci.domain.AbapPackageTestState;
import abapci.domain.TestState;

abstract class AbstractTestManager {

	IProject project;
	protected List<String> packageNames;
	protected ContinuousIntegrationPresenter continuousIntegrationPresenter;

	public AbstractTestManager(ContinuousIntegrationPresenter continuousIntegrationPresenter, IProject project,
			List<String> packageNames) {
		this.continuousIntegrationPresenter = continuousIntegrationPresenter;
		this.project = project;
		this.packageNames = packageNames;
		overallTestState = TestState.UNDEF;
	}

	public void setPackages(List<String> packageNames) {
		this.packageNames = packageNames;
	}

	protected TestState overallTestState;

	protected void mergePackageTestStateIntoGlobalTestState(TestState packageTestState) {
		if (overallTestState == null && packageTestState == TestState.OK) {
			overallTestState = packageTestState;
		}

		switch (packageTestState) {
		case UNDEF:
		case NOK:
		case OFFL:
		case DEACT:
		case NO_CONFIG: 
			overallTestState = packageTestState;
			break;
		case OK:
			overallTestState = (overallTestState == TestState.UNDEF) ? packageTestState : overallTestState;
			break;
		}

	}

	@Deprecated
	protected void calculateOverallTestState(List<AbapPackageTestState> packageTestStates,
			TestStateType teststateType) {

		if (teststateType == TestStateType.UNIT) {
			if (packageTestStates.stream().anyMatch(item -> item.getUnitTestState().equals(TestState.UNDEF))) {
				overallTestState = TestState.UNDEF;
			} else if (packageTestStates.stream().anyMatch(item -> item.getUnitTestState().equals(TestState.NOK))) {
				overallTestState = TestState.NOK;
			} else {
				overallTestState = TestState.OK;
			}
		} else {
			if (packageTestStates.stream().anyMatch(item -> item.getAtcTestState().equals(TestState.UNDEF))) {
				overallTestState = TestState.UNDEF;
			} else if (packageTestStates.stream().anyMatch(item -> item.getAtcTestState().equals(TestState.NOK))) {
				overallTestState = TestState.NOK;
			} else {
				overallTestState = TestState.OK;
			}
		}
	}

	public enum TestStateType {
		UNIT, ATC
	}

}
