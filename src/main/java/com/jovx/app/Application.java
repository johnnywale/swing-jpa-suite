package com.jovx.app;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ButtonGroup;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JToggleButton;
import javax.swing.SingleSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.border.EtchedBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.jovx.app.entity.ApplicationConfig;
import com.jovx.app.entity.ModuleConfig;

public class Application extends JPanel {

	private ArrayList<BaseComponentModule> moduleList = new ArrayList<BaseComponentModule>();
	private BaseComponentModule currentModule = null;
	private JPanel modulePanel = null;
	private ToggleButtonToolBar toolbar = null;
	private ButtonGroup toolbarGroup = new ButtonGroup();
	private JMenuBar menuBar = null;
	private JFrame frame = null;
	private JTabbedPane tabbedPane = null;
	private Container contentPane = null;
	private Logger logger = Logger.getLogger("com.johnny.app");
	public static final String CHANNEL_APP = "APP";

	private void loadModules() {
		List<ModuleConfig> modules = applicationConfig.getModuleConfigs();
		for (int i = 0; i < modules.size();) {
			loadModuleAndAdd(modules.get(i));
			i++;
		}
	}

	private ApplicationConfig applicationConfig;

	public void init(ApplicationConfig applicationConfig) {
		this.applicationConfig = applicationConfig;
		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		setPreferredSize(new Dimension(applicationConfig.getWidth(),
				applicationConfig.getHeight()));
		initApp();
		BaseComponentModule xx = loadModule(applicationConfig
				.getDefaultModule());
		BaseComponentModule defaultModule = addModule(xx);
		setModule(defaultModule);
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				startApp();
			}
		});

		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				loadModules();
			}
		});
		thread.start();
	}

	protected void initApp() {
		JPanel top = new JPanel();
		top.setLayout(new BorderLayout());
		add(top, BorderLayout.NORTH);
		menuBar = createMenus();
		if (menuBar != null) {
			frame.setJMenuBar(menuBar);
		}
		ToolBarPanel toolbarPanel = new ToolBarPanel();
		toolbarPanel.setLayout(new BorderLayout());
		toolbar = new ToggleButtonToolBar();
		toolbarPanel.add(toolbar, BorderLayout.CENTER);
		top.add(toolbarPanel, BorderLayout.SOUTH);

		tabbedPane = new JTabbedPane();
		add(tabbedPane, BorderLayout.CENTER);
		tabbedPane.getModel().addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				SingleSelectionModel model = (SingleSelectionModel) e
						.getSource();
				boolean srcSelected = model.getSelectedIndex() == 1;
				if (currentTab != currentModule && srcSelected) {

					repaint();
				}
			}
		});
		if (applicationConfig.getAppStatus() != null) {
			if (applicationConfig.getAppStatus().size() > 0) {
				add(getApplicationStatus(), BorderLayout.SOUTH);
			}

		}

		modulePanel = new JPanel();
		modulePanel.setLayout(new BorderLayout());
		modulePanel.setBorder(new EtchedBorder());
		tabbedPane.addTab("Module Panel", modulePanel);

	}

	protected ApplicationStatus applicationStatus;

	public ApplicationStatus getApplicationStatus() {
		if (applicationStatus == null) {
			applicationStatus = new ApplicationStatus(this.applicationConfig);
		}
		return applicationStatus;

	}

	private BaseComponentModule currentTab = null;

	public BaseComponentModule getCurrentModule() {
		return currentModule;
	}

	public BaseComponentModule getCurrentTab() {
		return currentTab;
	}

	/**
	 * Create menus
	 */
	protected JMenuBar createMenus() {
		return null;
	}

	/**
	 * Creates a generic menu item
	 */
	protected JMenuItem createMenuItem(JMenu menu, String label,
			String mnemonic, String accessibleDescription, Action action) {
		JMenuItem mi = (JMenuItem) menu.add(new JMenuItem(label));
		mi.setMnemonic(getMnemonic(mnemonic));
		mi.getAccessibleContext().setAccessibleDescription(
				accessibleDescription);
		mi.addActionListener(action);
		if (action == null) {
			mi.setEnabled(false);
		}
		return mi;
	}

	public BaseComponentModule addModule(final BaseComponentModule module) {
		moduleList.add(module);
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				SwitchToModuleAction action = new SwitchToModuleAction(module);
				JToggleButton tb = getToolBar().addToggleButton(action);
				getToolBarGroup().add(tb);
				tb.setText(null);
				if (getToolBarGroup().getSelection() == null) {
					tb.setSelected(true);
				}
				tb.setToolTipText(module.getToolTip());
			}
		});
		return module;
	}

	public BaseComponentModule getCurrentTabDemo() {
		return currentTab;
	}

	public void setModule(BaseComponentModule module) {
		currentModule = module;
		JComponent currentDemoPanel = module.getModulePanel();
		SwingUtilities.updateComponentTreeUI(currentDemoPanel);
		modulePanel.removeAll();
		modulePanel.add(currentDemoPanel, BorderLayout.CENTER);
		tabbedPane.setSelectedIndex(0);
		tabbedPane.setTitleAt(0, module.getName());
		tabbedPane.setToolTipTextAt(0, module.getToolTip());
	}

	protected void startApp() {
		JFrame f = getFrame();
		f.setTitle(applicationConfig.getTitle());
		f.getContentPane().add(this, BorderLayout.CENTER);
		f.pack();
		Rectangle screenRect = f.getGraphicsConfiguration().getBounds();
		Insets screenInsets = Toolkit.getDefaultToolkit().getScreenInsets(
				f.getGraphicsConfiguration());
		int centerWidth = screenRect.width < f.getSize().width ? screenRect.x
				: screenRect.x + screenRect.width / 2 - f.getSize().width / 2;
		int centerHeight = screenRect.height < f.getSize().height ? screenRect.y
				: screenRect.y + screenRect.height / 2 - f.getSize().height / 2;

		centerHeight = centerHeight < screenInsets.top ? screenInsets.top
				: centerHeight;

		f.setLocation(centerWidth, centerHeight);
		f.setVisible(true);

	}

	private BaseComponentModule loadModule(ModuleConfig moduleConfig) {
		try {
			Class demoClass = Class.forName(moduleConfig.getModuleClazz());

			BaseComponentModule baseComponentModule = (BaseComponentModule) demoClass
					.newInstance();
			baseComponentModule.initModule(this, moduleConfig);

			return baseComponentModule;
		} catch (Throwable throwable) {
			throw new RuntimeException(throwable);
		}
	}

	private void loadModuleAndAdd(ModuleConfig moduleConfig) {

		try {
			BaseComponentModule baseComponentModule = loadModule(moduleConfig);
			addModule(baseComponentModule);
		} catch (Exception e) {
			e.printStackTrace();
			logger.warning("Error occurred loading demo: "
					+ moduleConfig.getModuleClazz());
		}
	}

	public JFrame getFrame() {
		return frame;
	}

	public JMenuBar getMenuBar() {
		return menuBar;
	}

	/**
	 * Returns the toolbar
	 */
	public ToggleButtonToolBar getToolBar() {
		return toolbar;
	}

	/**
	 * Returns the toolbar button group
	 */
	public ButtonGroup getToolBarGroup() {
		return toolbarGroup;
	}

	/**
	 * Returns the content pane wether we're in an applet or application
	 */
	public Container getContentPane() {
		if (contentPane == null) {
			if (getFrame() != null) {
				contentPane = getFrame().getContentPane();
			}
		}
		return contentPane;
	}

	public char getMnemonic(String key) {
		return key.charAt(0);
	}

	public class SwitchToModuleAction extends AbstractAction {
		BaseComponentModule baseComponentModule;

		public SwitchToModuleAction(BaseComponentModule baseComponentModule) {
			super(baseComponentModule.getName(), baseComponentModule.getIcon());
			this.baseComponentModule = baseComponentModule;
		}

		public void actionPerformed(ActionEvent e) {
			Application.this.setModule(baseComponentModule);
		}
	}

}
