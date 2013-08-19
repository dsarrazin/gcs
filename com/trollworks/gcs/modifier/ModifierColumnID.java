/* ***** BEGIN LICENSE BLOCK *****
 * Version: MPL 1.1
 *
 * The contents of this file are subject to the Mozilla Public License Version
 * 1.1 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 * http://www.mozilla.org/MPL/
 *
 * Software distributed under the License is distributed on an "AS IS" basis,
 * WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
 * for the specific language governing rights and limitations under the
 * License.
 *
 * The Original Code is GURPS Character Sheet.
 *
 * The Initial Developer of the Original Code is Richard A. Wilkes.
 * Portions created by the Initial Developer are Copyright (C) 1998-2002,
 * 2005-2011 the Initial Developer. All Rights Reserved.
 *
 * Contributor(s):
 *
 * ***** END LICENSE BLOCK ***** */

package com.trollworks.gcs.modifier;

import com.trollworks.gcs.widgets.outline.ListHeaderCell;
import com.trollworks.gcs.widgets.outline.ListTextCell;
import com.trollworks.gcs.widgets.outline.MultiCell;
import com.trollworks.ttk.utility.LocalizedMessages;
import com.trollworks.ttk.widgets.outline.Cell;
import com.trollworks.ttk.widgets.outline.Column;
import com.trollworks.ttk.widgets.outline.Outline;
import com.trollworks.ttk.widgets.outline.OutlineModel;

import javax.swing.SwingConstants;

/** Modifier Columns */
public enum ModifierColumnID {
	/** The enabled/disabled column. */
	ENABLED {
		@Override
		public String toString() {
			return MSG_ENABLED;
		}

		@Override
		public String getToolTip() {
			return MSG_ENABLED_TOOLTIP;
		}

		@Override
		public Cell getCell() {
			return new ListTextCell(SwingConstants.CENTER, false);
		}

		@Override
		public String getDataAsText(Modifier modifier) {
			return modifier.isEnabled() ? MSG_ENABLED_COLUMN : ""; //$NON-NLS-1$
		}
	},
	/** The advantage name/description. */
	DESCRIPTION {
		@Override
		public String toString() {
			return MSG_DESCRIPTION;
		}

		@Override
		public String getToolTip() {
			return MSG_DESCRIPTION_TOOLTIP;
		}

		@Override
		public Cell getCell() {
			return new MultiCell();
		}

		@Override
		public String getDataAsText(Modifier modifier) {
			StringBuilder builder = new StringBuilder();
			String notes = modifier.getNotes();

			builder.append(modifier.toString());
			if (notes.length() > 0) {
				builder.append(" ("); //$NON-NLS-1$
				builder.append(notes);
				builder.append(')');
			}
			return builder.toString();
		}
	},
	/** The total cost modifier. */
	COST_MODIFIER_TOTAL {
		@Override
		public String toString() {
			return MSG_COST_MODIFIER;
		}

		@Override
		public String getToolTip() {
			return MSG_COST_MODIFIER_TOOLTIP;
		}

		@Override
		public Cell getCell() {
			return new ListTextCell(SwingConstants.LEFT, false);
		}

		@Override
		public String getDataAsText(Modifier modifier) {
			return modifier.getCostDescription();
		}
	},

	/** The page reference. */
	REFERENCE {
		@Override
		public String toString() {
			return MSG_REFERENCE;
		}

		@Override
		public String getToolTip() {
			return MSG_REFERENCE_TOOLTIP;
		}

		@Override
		public Cell getCell() {
			return new ListTextCell(SwingConstants.RIGHT, false);
		}

		@Override
		public String getDataAsText(Modifier modifier) {
			return modifier.getReference();
		}
	};

	static String	MSG_DESCRIPTION;
	static String	MSG_DESCRIPTION_TOOLTIP;
	static String	MSG_COST_MODIFIER;
	static String	MSG_COST_MODIFIER_TOOLTIP;
	static String	MSG_ENABLED;
	static String	MSG_ENABLED_TOOLTIP;
	static String	MSG_ENABLED_COLUMN;
	static String	MSG_REFERENCE;
	static String	MSG_REFERENCE_TOOLTIP;

	static {
		LocalizedMessages.initialize(ModifierColumnID.class);
	}

	/**
	 * @param modifier The {@link Modifier} to get the data from.
	 * @return An object representing the data for this column.
	 */
	public Object getData(Modifier modifier) {
		return getDataAsText(modifier);
	}

	/**
	 * @param modifier The {@link Modifier} to get the data from.
	 * @return Text representing the data for this column.
	 */
	public abstract String getDataAsText(Modifier modifier);

	/** @return The tooltip for the column. */
	public abstract String getToolTip();

	/** @return The {@link Cell} used to display the data. */
	public abstract Cell getCell();

	/** @return Whether this column should be displayed for the specified data file. */
	@SuppressWarnings("static-method")
	public boolean shouldDisplay() {
		return true;
	}

	/**
	 * Adds all relevant {@link Column}s to a {@link Outline}.
	 * 
	 * @param outline The {@link Outline} to use.
	 * @param forEditor Whether this is for an editor or not.
	 */
	public static void addColumns(Outline outline, boolean forEditor) {
		OutlineModel model = outline.getModel();

		for (ModifierColumnID one : values()) {
			if (one.shouldDisplay()) {
				Column column = new Column(one.ordinal(), one.toString(), one.getToolTip(), one.getCell());

				if (!forEditor) {
					column.setHeaderCell(new ListHeaderCell(true));
				}
				model.addColumn(column);
			}
		}
	}

}