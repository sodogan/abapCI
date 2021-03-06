package abapci.views.actions.ui;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.window.Window;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;

import abapci.ci.presenter.ContinuousIntegrationPresenter;
import abapci.ci.views.AddOrUpdateContinuousIntegrationConfigPage;

public class AddAction extends Action {

	private final ContinuousIntegrationPresenter presenter;

	public AddAction(ContinuousIntegrationPresenter presenter, String label) {
		this.setText(label);
		this.setImageDescriptor(
				PlatformUI.getWorkbench().getSharedImages().getImageDescriptor(ISharedImages.IMG_OBJ_ADD));
		this.presenter = presenter;

	}

	@Override
	public void run() {

		AddOrUpdateContinuousIntegrationConfigPage ciConfigPage = new AddOrUpdateContinuousIntegrationConfigPage(
				PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), presenter, null, false);
		if (ciConfigPage.open() == Window.OK) {

			// TODO
		}

	}
}
