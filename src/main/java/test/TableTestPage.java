package test;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.extensions.markup.html.repeater.data.detailstable.DetailsTable;
import org.apache.wicket.extensions.markup.html.repeater.data.grid.ICellPopulator;
import org.apache.wicket.extensions.markup.html.repeater.data.table.AbstractColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.HeadersToolbar;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.NavigationToolbar;
import org.apache.wicket.extensions.markup.html.repeater.data.table.PropertyColumn;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.Fragment;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

public class TableTestPage extends WebPage {

	private static final long serialVersionUID = 1L;
	private DetailsTable<User, String> table;

	public TableTestPage() {
		final Form<User> form = new Form<User>("form");

		add(form);

		final TestDataProvider tdp = new TestDataProvider();

		final List<IColumn<User, String>> columns = new ArrayList<IColumn<User, String>>();
		columns.add(new ToggleColumn());
		columns.add(new PropertyColumn<User, String>(Model.of("First Name"),
				"firstName", "firstName"));
		columns.add(new PropertyColumn<User, String>(Model.of("Last Name"),
				"lastName", "lastName"));

		table = new DetailsTable<User, String>("table", columns, tdp, 2) {
			@Override
			public Component newDetailsComponent(String id, IModel<User> model) {
				Fragment detailsFragment = new Fragment(id, "details-fragment",
						TableTestPage.this);

				User user = model.getObject();

				Label label = new Label("details", user.getFirstName() + " "
						+ user.getLastName());

				detailsFragment.add(label);
				return detailsFragment;
			}
		};
		table.addTopToolbar(new NavigationToolbar(table));
		table.addTopToolbar(new HeadersToolbar<String>(table, tdp));
		form.add(table);
	}

	private class ToggleColumn extends AbstractColumn<User, String> {

		private static final long serialVersionUID = 1L;

		public ToggleColumn() {
			super(Model.of("&nbsp;"), null);
		}

		@Override
		public void populateItem(final Item<ICellPopulator<User>> cellItem,
				String componentId, final IModel<User> rowModel) {

			Fragment buttonFragment = new Fragment(componentId,
					"button-fragment", TableTestPage.this);

			AjaxButton button = new AjaxButton("button") {

				private static final long serialVersionUID = 1L;

				@Override
				protected void onError(AjaxRequestTarget target, Form<?> form) {
					System.out.println("ERROR");
				}

				@Override
				protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
					table.toggleDetails(target, rowModel.getObject());
				}

			};

			button.setDefaultFormProcessing(false);
			buttonFragment.add(button);

			cellItem.add(buttonFragment);
		}
	}
}
