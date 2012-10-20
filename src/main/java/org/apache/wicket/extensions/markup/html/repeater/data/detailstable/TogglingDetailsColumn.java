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

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.extensions.markup.html.repeater.data.grid.ICellPopulator;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

/**
 * An abstract class that represents a details column in the {@link DetailsTable}.
 * The populateItem method sets the visibility of the details component to false so it can be toggled.
 * 
 * @author William Speirs (wspeirs)
 * @param <T> The model object type
 * @param <S> The type of the sorting parameter
 */
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
