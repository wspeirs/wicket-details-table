package org.apache.wicket.extensions.markup.html.repeater.data.detailstable;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.extensions.markup.html.repeater.data.grid.ICellPopulator;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.model.IModel;

public abstract class AbstractDetailsColumn<T, S> implements IColumn<T, S> {

    private static final long serialVersionUID = 1L;

    private final IModel<String> displayModel;
    private final S sortProperty;
    private int numCols;

    /**
     * @param displayModel model used to generate header text
     * @param sortProperty sort property this column represents
     */
    public AbstractDetailsColumn(final IModel<String> displayModel, final S sortProperty) {
        this.displayModel = displayModel;
        this.sortProperty = sortProperty;
    }

    /**
     * @param displayModel model used to generate header text
     */
    public AbstractDetailsColumn(final IModel<String> displayModel) {
        this(displayModel, null);
    }

    /**
     * A factory method for creating the details component for the model.
     * @param id  The id of the component used to render the cell (only one component should be added to the cell).
     * @param model The model of the row item being rendered. This model usually contains the model provided by the data provider.
     * @return A component containing details for that model.
     */
    public abstract Component getDetailsComponent(String id, IModel<T> model);
    
    /**
     * A factory method for creating the the component for the cell.
     * @param id  The id of the component used to render the cell (only one component should be added to the cell).
     * @param model The model of the row item being rendered. This model usually contains the model provided by the data provider.
     * @param detailsComponent The component generated for this row by the call to {@link AbstractDetailsColumn#getDetailsComponent(String, IModel)}
     * @return A component to populate the cell with.
     */
    public abstract Component getCellComponent(String id, IModel<T> model, final Component detailsComponent);
    
    /**
     * This method is automatically called by the DetailsTable and does not need to be called.
     * @param numCols the number of columns being passed to the table.
     */
    void setNumCols(int numCols) {
        this.numCols = numCols;
    }
    
    int getNumCols() {
        return this.numCols;
    }

    /**
     * Populates the cell for this column and the details column.
     * 
     * <b>99% of the time this method should not be overwritten.</b>
     */
    @Override
    public void populateItem(Item<ICellPopulator<T>> cellItem, String componentId, IModel<T> rowModel) {
        Component detailsComponent = getDetailsComponent("details-cell", rowModel);
        Component cellComponent = getCellComponent(componentId, rowModel, detailsComponent);
        
        // make sure we apply this after the call to getCellComponent so it will always be applied
        detailsComponent.add(new AttributeModifier("colspan", numCols));
        
        // add in our two components
        cellItem.getParent().getParent().add(detailsComponent);
        cellItem.add(cellComponent);
    }

    /**
     * @return returns display model to be used for the header component
     */
    public IModel<String> getDisplayModel() {
        return displayModel;
    }

    /**
     * @see org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn#getSortProperty()
     */
    @Override
    public S getSortProperty() {
        return sortProperty;
    }

    /**
     * @see org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn#isSortable()
     */
    @Override
    public boolean isSortable() {
        return getSortProperty() != null;
    }

    /**
     * @see org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn#getHeader(java.lang.String)
     */
    @Override
    public Component getHeader(final String componentId) {
        return new Label(componentId, getDisplayModel());
    }

    /**
     * @see org.apache.wicket.model.IDetachable#detach()
     */
    @Override
    public void detach() {
        if (displayModel != null) {
            displayModel.detach();
        }
    }

}
