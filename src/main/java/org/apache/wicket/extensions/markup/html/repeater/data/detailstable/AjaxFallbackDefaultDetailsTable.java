/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.wicket.extensions.markup.html.repeater.data.detailstable;

import java.util.List;

import org.apache.wicket.extensions.ajax.markup.html.repeater.data.table.AjaxFallbackHeadersToolbar;
import org.apache.wicket.extensions.ajax.markup.html.repeater.data.table.AjaxNavigationToolbar;
import org.apache.wicket.extensions.markup.html.repeater.data.table.DataTable;
import org.apache.wicket.extensions.markup.html.repeater.data.table.HeadersToolbar;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.ISortableDataProvider;
import org.apache.wicket.extensions.markup.html.repeater.data.table.NavigationToolbar;
import org.apache.wicket.extensions.markup.html.repeater.data.table.NoRecordsToolbar;

/**
 * An implementation of the DetailsTable that aims to solve the 90% usecase by adding navigation,
 * headers, an no-records-found toolbars to a standard {@link DetailsTable}.
 * <p>
 * The {@link NavigationToolbar} and the {@link HeadersToolbar} are added as top toolbars, while the
 * {@link NoRecordsToolbar} toolbar is added as a bottom toolbar.
 * 
 * @see DataTable
 * @see HeadersToolbar
 * @see NavigationToolbar
 * @see NoRecordsToolbar
 * 
 * @author William Speirs (wspeirs)
 * 
 * @param <T> The model object type
 * @param <S> The type of the sorting parameter
 * 
 */
public class AjaxFallbackDefaultDetailsTable<T, S> extends DetailsTable<T, S> {
    private static final long serialVersionUID = 1L;

    /**
     * Constructor
     * 
     * @param id component id
     * @param columns list of columns
     * @param dataProvider data provider
     * @param rowsPerPage number of rows per page
     */
    public AjaxFallbackDefaultDetailsTable(final String id,
                                           final List<IColumn<T, S>> columns,
                                           final ISortableDataProvider<T, S> dataProvider,
                                           final int rowsPerPage)
    {
            super(id, columns, dataProvider, rowsPerPage);
            setOutputMarkupId(true);
            setVersioned(false);
            addTopToolbar(new AjaxNavigationToolbar(this));
            addTopToolbar(new AjaxFallbackHeadersToolbar<S>(this, dataProvider));
            addBottomToolbar(new NoRecordsToolbar(this));
    }

}
