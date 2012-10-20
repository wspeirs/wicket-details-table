package org.apache.wicket.extensions.markup.html.repeater.data.detailstable;

import java.util.List;

import org.apache.wicket.extensions.markup.html.repeater.data.table.DataTable;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.ISortableDataProvider;

/**
 * A details table that builds on the data table to introduce a details row below each table row.
 * <p>
 * Details table provides its own markup for an html table so the user does not need to provide it.
 * This makes it very simple to add details to a datatable.
 * <p>
 * Example
 * 
 * <pre>
 * &lt;table wicket:id=&quot;detailstable&quot;&gt;&lt;/table&gt;
 * </pre>
 * 
 * And the related Java code:
 * - the first column will be sortable because its sort property is specified
 * - the second column will not be sortable
 * - the third column will trigger the details row (this column type is required)
 * 
 * <pre>
 * List&lt;IColumn&lt;T&gt;&gt; columns = new ArrayList&lt;IColumn&lt;T&gt;&gt;();
 * 
 * columns.add(new PropertyColumn(new Model&lt;String&gt;(&quot;First Name&quot;), &quot;name.first&quot;, &quot;name.first&quot;));
 * columns.add(new PropertyColumn(new Model&lt;String&gt;(&quot;Last Name&quot;), &quot;name.last&quot;));
 * columns.add(new TogglineDetailsColumn() { ... });
 * 
 * DetailsTable table = new DetailsTable(&quot;detailstable&quot;, columns, new UserProvider(), 10);
 * table.addBottomToolbar(new NavigationToolbar(table));
 * table.addTopToolbar(new HeadersToolbar(table, null));
 * add(table);
 * </pre>
 * 
 * @see DefaultDetailsTable
 * 
 * @author William Speirs (wspeirs)
 * 
 * @param <T> The model object type
 * @param <S> The type of the sorting parameter
 * 
 */
public class DetailsTable<T, S> extends DataTable<T, S> {

    private static final long serialVersionUID = 1L;

    /**
     * Constructor
     * 
     * @param id component id
     * @param columns list of columns
     * @param dataProvider data provider
     * @param rowsPerPage number of rows per page
     */
    public DetailsTable(final String id,
                        final List<IColumn<T, S>> columns,
                        final ISortableDataProvider<T, S> dataProvider,
                        final int rowsPerPage) {
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
