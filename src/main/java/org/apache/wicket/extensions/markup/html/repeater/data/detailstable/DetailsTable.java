package org.apache.wicket.extensions.markup.html.repeater.data.detailstable;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.wicket.Component;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.extensions.markup.html.repeater.data.grid.ICellPopulator;
import org.apache.wicket.extensions.markup.html.repeater.data.table.DataTable;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.ISortableDataProvider;
import org.apache.wicket.extensions.markup.html.repeater.tree.Node;
import org.apache.wicket.extensions.markup.html.repeater.util.ProviderSubset;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.model.IModel;
import org.apache.wicket.util.visit.IVisit;
import org.apache.wicket.util.visit.IVisitor;

import test.User;

/**
 * A details table that builds on the data table to introduce a details row
 * below each table row.
 * <p>
 * Details table provides its own markup for an html table so the user does not
 * need to provide it. This makes it very simple to add details to a datatable.
 * <p>
 * Example
 * 
 * <pre>
 * &lt;table wicket:id=&quot;detailstable&quot;&gt;&lt;/table&gt;
 * </pre>
 * 
 * And the related Java code: - the first column will be sortable because its
 * sort property is specified - the second column will not be sortable - the
 * third column will trigger the details row.
 * 
 * <pre>
 * List&lt;IColumn&lt;T&gt;&gt; columns = new ArrayList&lt;IColumn&lt;T&gt;&gt;();
 * 
 * columns.add(new PropertyColumn(new Model&lt;String&gt;(&quot;First Name&quot;), &quot;name.first&quot;, &quot;name.first&quot;));
 * columns.add(new PropertyColumn(new Model&lt;String&gt;(&quot;Last Name&quot;), &quot;name.last&quot;));
 * columns.add(new ToggeColumn() { ... });
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
 * @param <T>
 *            The model object type
 * @param <S>
 *            The type of the sorting parameter
 * 
 */
public abstract class DetailsTable<T, S> extends DataTable<T, S> {

    private static final long serialVersionUID = 1L;
    
    private Set<T> detailed = new HashSet<T>();
    
    /**
     * Constructor
     * 
     * @param id
     *            component id
     * @param columns
     *            list of columns
     * @param dataProvider
     *            data provider
     * @param rowsPerPage
     *            number of rows per page
     */
    public DetailsTable(final String id, final List<IColumn<T, S>> columns,
	    final ISortableDataProvider<T, S> dataProvider,
	    final int rowsPerPage) {
	super(id, columns, dataProvider, rowsPerPage);
    }

    @Override
    protected Item<T> newRowItem(String id, int index, final IModel<T> model) {
	Item<T> row = super.newRowItem(id, index, model);

	final Component details = newDetailsComponent("details", model);
	details.setOutputMarkupPlaceholderTag(true);
	details.add(new Behavior() {
	    @Override
	    public void onComponentTag(Component component, ComponentTag tag) {
		super.onComponentTag(component, tag);

		tag.put("colspan", getColumns().size());
	    }
	    
	    @Override
	    public void onConfigure(Component component) {
		details.setVisible(detailed.contains(model.getObject()));
	    }
	});
	row.add(details);

	return row;
    }

    /**
     * A factory method for creating the details component for the model.
     * 
     * @param id
     *            The id of the component used to render the cell.
     * @param model
     *            The model of the row item being rendered. This model usually
     *            contains the model provided by the data provider.
     * @return A component containing details for that model.
     */
    public abstract Component newDetailsComponent(String id, IModel<T> model);

    /**
     * Toggle details for the given cell.
     * 
     * @param target
     * @param cellItem
     */
    public void toggleDetails(final AjaxRequestTarget target,
	    final T t) {
	
	if (detailed.contains(t)) {
		detailed.remove(t);
	} else {
		detailed.add(t);
	}
	
	visitChildren(Item.class, new IVisitor<Item<T>, Void>()
	{
		@Override
		public void component(Item<T> row, IVisit<Void> visit)
		{
			if (t == row.getModelObject())
			{
			    target.add(row.get(0));
			    visit.stop();
			    return;
			}
			visit.dontGoDeeper();
		}
	});
    }
}
