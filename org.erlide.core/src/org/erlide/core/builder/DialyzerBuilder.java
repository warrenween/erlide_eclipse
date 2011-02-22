package org.erlide.core.builder;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.osgi.util.NLS;
import org.erlide.backend.BackendException;
import org.erlide.core.ErlangPlugin;
import org.erlide.core.builder.internal.BuilderMessages;
import org.erlide.core.erlang.ErlangCore;
import org.erlide.core.erlang.IErlModel;
import org.erlide.core.erlang.IErlModule;
import org.erlide.core.erlang.IErlProject;
import org.erlide.jinterface.util.ErlLogger;

public class DialyzerBuilder extends IncrementalProjectBuilder {

    public static final String BUILDER_ID = "org.erlide.core.builder.DialyzerBuilder";

    // private static final BuilderHelper helper = new BuilderHelper();

    @Override
    protected IProject[] build(final int kind, @SuppressWarnings("rawtypes")
    final Map args, final IProgressMonitor monitor) throws CoreException {
        final IProject project = getProject();
        DialyzerPreferences prefs;
        try {
            prefs = DialyzerPreferences.get(project);
        } catch (final BackendException e1) {
            throw new CoreException(new Status(IStatus.ERROR,
                    ErlangPlugin.PLUGIN_ID, e1.toString()));
        }
        if (!prefs.getDialyzeOnCompile()) {
            return null;
        }
        final IErlModel model = ErlangCore.getModel();
        final Map<IErlProject, Set<IErlModule>> modules = new HashMap<IErlProject, Set<IErlModule>>();
        DialyzerUtils.addModulesFromResource(model, project, modules);
        if (modules.size() != 0) {
            try {
                DialyzerUtils.doDialyze(monitor, modules);
            } catch (final InvocationTargetException e) {
                ErlLogger.error(e);
                final String msg = NLS.bind(
                        BuilderMessages.build_dialyzerProblem, e
                                .getTargetException().getLocalizedMessage());
                MarkerUtils.addProblemMarker(project, null, null, msg, 0,
                        IMarker.SEVERITY_ERROR);
            }
        }
        return null;
    }

    @Override
    protected void clean(final IProgressMonitor monitor) throws CoreException {
        final IProject project = getProject();
        if (project == null || !project.isAccessible()) {
            return;
        }
        MarkerUtils.removeDialyzerMarkers(project);
    }

}
