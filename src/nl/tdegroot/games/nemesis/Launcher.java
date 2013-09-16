package nl.tdegroot.games.nemesis;

import org.eclipse.wb.swing.FocusTraversalOnArray;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.SlickException;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class Launcher extends JFrame {

	private static final long serialVersionUID = 4138047282156541171L;

	private JPanel contentPane;
	private final String location = System.getenv("APPDATA") + "\\.nemesis";

	private int width, height;
	private boolean vs, fs;

	final JComboBox cbResolution = new JComboBox();
	final JCheckBox ckbxVsync = new JCheckBox("VSync");
	final JCheckBox ckbxFullscreen = new JCheckBox("Fullscreen");

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Launcher frame = new Launcher();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public Launcher() {
		setResizable(false);
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		setTitle("Nemesis");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 318, 235);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setLocationRelativeTo(null);

		cbResolution.setModel(new DefaultComboBoxModel(new String[] {"1024 x 768", "1024 x 720", "1280 x 720", "1280 x 768", "1600 x 900", "1920 x 1080"}));
		cbResolution.setSelectedIndex(2);
		cbResolution.setBounds(186, 49, 106, 20);
		contentPane.add(cbResolution);

		JLabel lblResolution = new JLabel("Screen Resolution:");
		lblResolution.setBounds(83, 52, 209, 14);
		contentPane.add(lblResolution);

		ckbxVsync.setSelected(true);
		ckbxVsync.setBounds(110, 90, 97, 23);
		contentPane.add(ckbxVsync);

		ckbxFullscreen.setBounds(213, 90, 97, 23);
		contentPane.add(ckbxFullscreen);

		JButton btnStart = new JButton("Play!");
		btnStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String[] arr = ((String) cbResolution.getSelectedItem()).split(" x ");
				width = Integer.parseInt(arr[0]);
				height = Integer.parseInt(arr[1]);
				vs = ckbxVsync.isSelected();
				fs = ckbxFullscreen.isSelected();
				try {
					launch();
				} catch (SlickException ex) {
					ex.printStackTrace();
				}
				Log.log("Width: " + width + ", height: " + height + ", vs: " + vs + ", fullscreen: " + fs);
			}
		});
		btnStart.setBounds(114, 163, 89, 23);
		contentPane.add(btnStart);

		JButton btnQuit = new JButton("Quit");
		btnQuit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		btnQuit.setBounds(207, 163, 89, 23);
		contentPane.add(btnQuit);
		setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[] {contentPane, cbResolution, ckbxVsync, ckbxFullscreen, btnStart, btnQuit}));
		loadConfig();
	}

	private void launch() throws SlickException {
		saveConfig();
		AppGameContainer game = new AppGameContainer(new Nemesis());
		game.setDisplayMode(width, height, fs);
		game.setShowFPS(false);
		game.setVSync(vs);
		if (vs) game.setTargetFrameRate(60);
		game.setMaximumLogicUpdateInterval(1000 / 60);
		game.setMinimumLogicUpdateInterval(1000 / 60);
		game.start();
		dispose();
	}

	private void saveConfig() {
		File file = new File(location);
		if (! file.exists()) {
			file.mkdir();
		}

		Properties prop = new Properties();

		try {
			prop.setProperty("width", "" + width);
			prop.setProperty("height", "" + height);
			prop.setProperty("vs", "" + vs);
			prop.setProperty("fs", "" + fs);

			prop.store(new FileOutputStream(location + "\\" + "config.cfg"), null);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	private void loadConfig() {
		Properties prop = new Properties();

		try {
			prop.load(new FileInputStream(location + "\\" + "config.cfg"));
			width = Integer.parseInt(prop.getProperty("width"));
			height = Integer.parseInt(prop.getProperty("height"));
			vs = Boolean.parseBoolean(prop.getProperty("vs"));
			fs = Boolean.parseBoolean(prop.getProperty("fs"));
		} catch (IOException ex) {
			ex.printStackTrace();
		}

		String res = width + " x " + height;
		cbResolution.setSelectedItem(res);
		ckbxVsync.setSelected(vs);
		ckbxFullscreen.setSelected(fs);

	}

}
