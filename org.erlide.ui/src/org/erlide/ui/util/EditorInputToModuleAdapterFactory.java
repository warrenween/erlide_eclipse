package org.erlide.ui.util;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdapterFactory;
import org.eclipse.ui.IEditorInput;
import org.erlide.engine.model.IErlElement;

public class EditorInputToModuleAdapterFactory implements IAdapterFactory {

    @SuppressWarnings("rawtypes")
    private static final Class[] ADAPTER_LIST = new Class[] { IErlElement.class };

    @Override
    public <T> T getAdapter(final Object adaptableObject, final Class<T> adapterType) {
        if (adapterType == IErlElement.class && adaptableObject instanceof IEditorInput) {
            try {
                return adapterType.cast(ErlModelUtils.getModule((IEditorInput) adaptableObject));
            } catch (final CoreException e) {
            }
        }
        return null;
    }

    @Override
    public Class<?>[] getAdapterList() {
        return ADAPTER_LIST;
    }

}
