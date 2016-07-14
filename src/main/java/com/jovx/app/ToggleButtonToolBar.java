package com.jovx.app;

import java.awt.Insets;

import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;

public class ToggleButtonToolBar extends JToolBar {
	public ToggleButtonToolBar() {
		super();
	}

	static Insets zeroInsets = new Insets(1, 1, 1, 1);

	public JToggleButton addToggleButton(Action a) {
		JToggleButton tb = new JToggleButton((String) a.getValue(Action.NAME),
				(Icon) a.getValue(Action.SMALL_ICON));
		tb.setMargin(zeroInsets);
		tb.setEnabled(a.isEnabled());
		tb.setToolTipText((String) a.getValue(Action.SHORT_DESCRIPTION));
		tb.setAction(a);
		add(tb);
		return tb;
	}

}