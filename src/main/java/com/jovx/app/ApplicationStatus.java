package com.jovx.app;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.jovx.app.entity.ApplicationConfig;
import com.jovx.app.event.AppStatusChange;
import com.jovx.app.event.ApplicationEventHelper;

public class ApplicationStatus extends JPanel {

	private JComboBox jComboBox;

	public String getCurrentValue() {
		return (String) jComboBox.getSelectedItem();
	}

	public JComboBox getjComboBox() {
		return jComboBox;
	}

	public ApplicationStatus(ApplicationConfig applicationConfig) {
		setLayout(new FlowLayout(FlowLayout.LEFT));
		JLabel jLabel = new JLabel("APP Status");
		this.add(jLabel);
		applicationConfig.getAppStatus();

		DefaultComboBoxModel defaultComboBoxModel = new DefaultComboBoxModel(
				applicationConfig.getAppStatus().toArray(
						new String[applicationConfig.getAppStatus().size()]));
		jComboBox = new JComboBox(defaultComboBoxModel);
		jComboBox.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				AppStatusChange appStatusChange = new AppStatusChange();
				appStatusChange.setData((String) jComboBox.getSelectedItem());
				ApplicationEventHelper.fireAppEvent(appStatusChange);
			}
		});
		this.add(jComboBox);
	}
}
