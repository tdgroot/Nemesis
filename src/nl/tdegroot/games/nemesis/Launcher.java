package nl.tdegroot.games.nemesis;

import org.eclipse.wb.swing.FocusTraversalOnArray;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.SlickException;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
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

	private int width = 1280, height = 720;
	private boolean vs = true, fs = false;
	private float soundVolume = 100, musicVolume = 100;

	final JComboBox cbResolution = new JComboBox();
	final JCheckBox ckbxVsync = new JCheckBox("VSync");
	final JCheckBox ckbxFullscreen = new JCheckBox("Fullscreen");

	final JSlider slSoundVolume = new JSlider();
	final JSlider slMusicVolume = new JSlider();

	final JLabel lblSoundVolume = new JLabel("100 %");
	final JLabel lblMusicVolume = new JLabel("100 %");

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
		setBounds(100, 100, 319, 358);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setLocationRelativeTo(null);

		cbResolution.setModel(new DefaultComboBoxModel(new String[]{"1024 x 768", "1024 x 720", "1280 x 720", "1280 x 768", "1600 x 900", "1920 x 1080"}));
		cbResolution.setSelectedIndex(2);
		cbResolution.setBounds(172, 11, 106, 20);
		contentPane.add(cbResolution);

		JLabel lblResolution = new JLabel("Screen Resolution:");
		lblResolution.setBounds(69, 14, 209, 14);
		contentPane.add(lblResolution);

		ckbxVsync.setSelected(true);
		ckbxVsync.setBounds(96, 52, 97, 23);
		contentPane.add(ckbxVsync);

		ckbxFullscreen.setBounds(199, 52, 97, 23);
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
		btnStart.setBounds(121, 296, 89, 23);
		contentPane.add(btnStart);

		JButton btnQuit = new JButton("Quit");
		btnQuit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				saveConfig();
				System.exit(0);
			}
		});
		btnQuit.setBounds(214, 296, 89, 23);
		contentPane.add(btnQuit);

		slSoundVolume.setValue(100);
		slSoundVolume.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				soundVolume = (float) slSoundVolume.getValue() / 100;
				lblSoundVolume.setText("" + (int) (soundVolume * 100) + " %");
				Log.log("Sound Volume: " + soundVolume);
			}
		});
		slSoundVolume.setBounds(91, 82, 155, 30);
		contentPane.add(slSoundVolume);

		JLabel lbl = new JLabel("Sound Volume:");
		lbl.setBounds(10, 90, 71, 14);
		contentPane.add(lbl);

		lblSoundVolume.setBounds(256, 90, 46, 14);
		contentPane.add(lblSoundVolume);

		JLabel lblNewLabel = new JLabel("Music Volume:");
		lblNewLabel.setBounds(10, 126, 71, 14);
		contentPane.add(lblNewLabel);

		slMusicVolume.setValue(100);
		slMusicVolume.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				musicVolume = (float) slMusicVolume.getValue() / 100;
				lblMusicVolume.setText("" + (int) (musicVolume * 100) + " %");
				Log.log("Music Volume: " + musicVolume);
			}
		});
		slMusicVolume.setBounds(91, 115, 155, 37);
		contentPane.add(slMusicVolume);

		lblMusicVolume.setBounds(256, 126, 40, 14);
		contentPane.add(lblMusicVolume);
		setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[]{contentPane, cbResolution, ckbxVsync, ckbxFullscreen, btnStart, btnQuit}));
		loadConfig();
	}

	private void launch() throws SlickException {
		saveConfig();
		Nemesis nemesis = new Nemesis();
		Log.log("Sound Volume: " + soundVolume + ", Music Volume: " + musicVolume);
		nemesis.setVolume(soundVolume, musicVolume);
		AppGameContainer game = new AppGameContainer(new Nemesis());
		game.setDisplayMode(width, height, fs);
		game.setShowFPS(false);
		game.setSoundVolume(soundVolume);
		game.setMusicVolume(musicVolume);
		game.setVSync(vs);
		if (vs) game.setTargetFrameRate(60);
		game.setMaximumLogicUpdateInterval(1000 / 60);
		game.setMinimumLogicUpdateInterval(1000 / 60);
		game.start();
		dispose();
	}

	private void saveConfig() {
		File file = new File(location);
		if (!file.exists()) {
			file.mkdir();
		}

		Properties prop = new Properties();

		Log.log("Sound Volume: " + soundVolume);
		Log.log("Music Volume: " + musicVolume);
		try {
			prop.setProperty("width", "" + width);
			prop.setProperty("height", "" + height);
			prop.setProperty("vs", "" + vs);
			prop.setProperty("fs", "" + fs);
			prop.setProperty("sv", "" + soundVolume);
			prop.setProperty("mv", "" + musicVolume);
			prop.store(new FileOutputStream(location + "\\" + "config.cfg"), null);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	private void loadConfig() {
		Properties prop = new Properties();

		File file = new File(location);

		if (!file.exists()) {
			saveConfig();
		}

		try {
			prop.load(new FileInputStream(location + "\\" + "config.cfg"));
			if (prop.containsKey("width")) width = Integer.parseInt(prop.getProperty("width"));
			if (prop.containsKey("height")) height = Integer.parseInt(prop.getProperty("height"));
			if (prop.containsKey("vs")) vs = Boolean.parseBoolean(prop.getProperty("vs"));
			if (prop.containsKey("fs")) fs = Boolean.parseBoolean(prop.getProperty("fs"));
			if (prop.containsKey("sv")) soundVolume = Float.parseFloat(prop.getProperty("sv"));
			if (prop.containsKey("mv")) musicVolume = Float.parseFloat(prop.getProperty("mv"));
		} catch (IOException ex) {
			ex.printStackTrace();
		}

		String res = width + " x " + height;
		cbResolution.setSelectedItem(res);

		ckbxVsync.setSelected(vs);
		ckbxFullscreen.setSelected(fs);

		slSoundVolume.setValue((int) (soundVolume * 100));
		lblSoundVolume.setText("" + (int) (soundVolume * 100) + " %");

		slMusicVolume.setValue((int) (musicVolume * 100));
		lblMusicVolume.setText("" + (int) (musicVolume * 100) + " %");

	}
}
