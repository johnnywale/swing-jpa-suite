package com.jovx.app;

import java.awt.BorderLayout;
import java.util.UUID;
import java.util.logging.Logger;

import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.SoftBevelBorder;

import com.jovx.app.entity.ModuleConfig;
import com.jovx.app.mbean.BaseModuleMBean;

public abstract class BaseComponentModule extends JPanel {

	static Border LOWER_BORDER = new CompoundBorder(new SoftBevelBorder(
			SoftBevelBorder.LOWERED), new EmptyBorder(5, 5, 5, 5));
	private Application application = null;
	private JPanel panel = null;
	private ModuleConfig moduleConfig;
	private BaseModuleMBean baseModuleMBean;

	public abstract String getDomain();

	public Application getApplication() {
		return application;
	}

	public void ModuleConfig() {

	}

	protected abstract void initPanel();

	public void initModule(Application application, ModuleConfig moduleConfig) {
		panel = new JPanel();
		panel.setLayout(new BorderLayout());
		this.application = application;
		this.moduleConfig = moduleConfig;
		initPanel();
		baseModuleMBean = new BaseModuleMBean(this);
		baseModuleMBean.initInternal();
	}

	public String getObjectNameKeyProperties() {

		return "type=Manager,host=localhost,uuid="
				+ UUID.randomUUID().toString();
	}

	public JPanel getModulePanel() {
		return panel;
	}

	public String getString(String key) {
		return key;
	}

	protected final Logger logger = Logger.getLogger(getClass()
			.getCanonicalName());

	public char getMnemonic(String key) {
		return (getString(key)).charAt(0);
	}

	public ImageIcon createImageIcon(String filename, String description) {
		String path = filename;
		return new ImageIcon(getClass().getResource(path), description);
	}

	public String getName() {
		return moduleConfig.getTitle();
	};

	public Icon getIcon() {
		return createImageIcon(moduleConfig.getImage(),
				moduleConfig.getDescription());
	};

	public String getToolTip() {
		return getString(moduleConfig.getResourceName());
	};

	public JPanel createHorizontalPanel(boolean threeD) {
		JPanel p = new JPanel();
		p.setLayout(new BoxLayout(p, BoxLayout.X_AXIS));
		p.setAlignmentY(TOP_ALIGNMENT);
		p.setAlignmentX(LEFT_ALIGNMENT);
		if (threeD) {
			p.setBorder(LOWER_BORDER);
		}
		return p;
	}

	public JPanel createVerticalPanel(boolean threeD) {
		JPanel p = new JPanel();
		p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
		p.setAlignmentY(TOP_ALIGNMENT);
		p.setAlignmentX(LEFT_ALIGNMENT);
		if (threeD) {
			p.setBorder(LOWER_BORDER);
		}
		return p;
	}

	public void init() {
		setLayout(new BorderLayout());
		add(getModulePanel(), BorderLayout.CENTER);
	}

}
