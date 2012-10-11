package org.apache.wicket.extensions.markup.html.repeater.data.detailstable;

import java.util.List;

import org.apache.wicket.extensions.markup.html.repeater.data.table.DefaultDataTable;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.ISortableDataProvider;

public class DetailsTable<T, S> extends DefaultDataTable<T, S> {

    private static final long serialVersionUID = 1L;

    public DetailsTable(String id, List<IColumn<T, S>> columns, ISortableDataProvider<T, S> dataProvider, int rowsPerPage) {
        super(id, columns, dataProvider, rowsPerPage);

        // loop through our columns looking for the AbstractDetailsColumn
        // so we can set the number of columns
        boolean found = false;
        for(IColumn<T,S> col:columns) {
            if(col instanceof AbstractDetailsColumn) {
                if(found) {
                    throw new IllegalArgumentException("You cannot include more than one AbstractDetailsColumn");
                }
                
                found = true;
                ((AbstractDetailsColumn<T, S>) col).setNumCols(columns.size());
            }
        }
        
        if(!found) {
            throw new IllegalArgumentException("One of the columns must be an AbstractDetailsColumn");
        }
    }

}
