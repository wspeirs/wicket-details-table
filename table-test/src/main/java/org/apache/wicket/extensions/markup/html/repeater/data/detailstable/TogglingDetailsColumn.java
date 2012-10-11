package org.apache.wicket.extensions.markup.html.repeater.data.detailstable;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.extensions.markup.html.repeater.data.grid.ICellPopulator;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

public abstract class TogglingDetailsColumn<T, S> extends AbstractDetailsColumn<T, S> {

    private static final long serialVersionUID = 1L;

    public TogglingDetailsColumn() {
        super(Model.of("&nbsp;"), null);
    }
    
    /**
     * Populates the cell for this column and the details column.
     * 
     * If this method is overwritten, then it should be called by the extending class.
     */
    @Override
    public final void populateItem(Item<ICellPopulator<T>> cellItem, String componentId, IModel<T> rowModel) {
        Component detailsComponent = getDetailsComponent("details-cell", rowModel);
        Component cellComponent = getCellComponent(componentId, rowModel, detailsComponent);
        
        // make sure we apply this after the call to getCellComponent so it will always be applied
        detailsComponent.setOutputMarkupPlaceholderTag(true);
        detailsComponent.add(new AttributeModifier("colspan", getNumCols()));
        detailsComponent.setVisible(false);
        
        // add in our two components
        cellItem.getParent().getParent().add(detailsComponent);
        cellItem.add(cellComponent);
    }
    
    /**
     * @see org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn#getHeader(java.lang.String)
     */
    @Override
    public Component getHeader(final String componentId) {
        Component header = super.getHeader(componentId);
        return header.setEscapeModelStrings(false);
    }

}
