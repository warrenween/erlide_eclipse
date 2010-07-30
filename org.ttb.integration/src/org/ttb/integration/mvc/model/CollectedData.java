package org.ttb.integration.mvc.model;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.graphics.Image;

public class CollectedData implements ITreeNode {

    private ITreeNode parent;
    private final String label;

    // private static final ImageRegistry imageRegistry = new ImageRegistry();
    // private static String IMAGE_NAME = "treeNode";
    //
    // static {
    // imageRegistry.put(IMAGE_NAME,
    // ImageDescriptor.createFromFile(TracePatternLabelProvider.class,
    // IMAGE_NAME + ".gif"));
    // }

    public CollectedData(String label) {
        this.label = label;
    }

    @Override
    public ITreeNode getParent() {
        return parent;
    }

    public void setParent(ITreeNode parent) {
        this.parent = parent;
    }

    @Override
    public String getLabel() {
        return label;
    }

    @Override
    public boolean hasChildren() {
        return false;
    }

    @Override
    public List<ITreeNode> getChildren() {
        return new ArrayList<ITreeNode>();
    }

    @Override
    public void addChild(ITreeNode child) {
    }

    @Override
    public Image getImage() {
        // return imageRegistry.get(IMAGE_NAME);
        return null;
    }
}
