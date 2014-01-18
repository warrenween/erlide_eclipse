package org.erlide.ui.wizards;

import com.google.common.base.Objects;
import java.io.File;
import java.net.URI;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.ui.dialogs.WizardNewProjectCreationPage;
import org.erlide.ui.wizards.NewProjectData;

@SuppressWarnings("all")
public class ErlangNewProjectCreationPage extends WizardNewProjectCreationPage {
  private final NewProjectData info;
  
  public ErlangNewProjectCreationPage(final String name, final NewProjectData info) {
    super(name);
    this.info = info;
  }
  
  public void setVisible(final boolean visible) {
    super.setVisible(visible);
    if (visible) {
      this.onEntry();
    } else {
      this.onExit();
    }
  }
  
  protected void onEntry() {
  }
  
  protected void onExit() {
    URI _locationURI = this.getLocationURI();
    String _path = _locationURI.getPath();
    Path _path_1 = new Path(_path);
    final Path projectPath = _path_1;
    boolean _or = false;
    String _name = this.info.getName();
    String _projectName = this.getProjectName();
    boolean _notEquals = (!Objects.equal(_name, _projectName));
    if (_notEquals) {
      _or = true;
    } else {
      IPath _location = this.info.getLocation();
      boolean _notEquals_1 = (!Objects.equal(_location, projectPath));
      _or = (_notEquals || _notEquals_1);
    }
    if (_or) {
    }
    String _projectName_1 = this.getProjectName();
    this.info.setName(_projectName_1);
    this.info.setLocation(projectPath);
    boolean _projectExists = this.projectExists();
    this.info.setExistingProject(_projectExists);
  }
  
  private boolean projectExists() {
    final IPath loc = this.info.getLocation();
    boolean _or = false;
    boolean _tripleEquals = (loc == null);
    if (_tripleEquals) {
      _or = true;
    } else {
      String _name = this.info.getName();
      boolean _isEmpty = _name.isEmpty();
      _or = (_tripleEquals || _isEmpty);
    }
    if (_or) {
      return false;
    }
    final File dir = loc.toFile();
    return dir.exists();
  }
}
