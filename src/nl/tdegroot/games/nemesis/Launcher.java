package nl.tdegroot.games.nemesis;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.SlickException;
import javax.swing.JFormattedTextField;
import org.eclipse.wb.swing.FocusTraversalOnArray;
import java.awt.Component;

public class Launcher extends JFrame {

	private static final long serialVersionUID = 4138047282156541171L;

	private JPanel contentPane;

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

		final JComboBox cbResolution = new JComboBox();
		cbResolution.setModel(new DefaultComboBoxModel(new String[] { "1024 x 768", "1024 x 720", "1280 x 720", "1280 x 768", "1600 x 900", "1920 x 1080" }));
		cbResolution.setSelectedIndex(2);
		cbResolution.setBounds(186, 49, 106, 20);
		contentPane.add(cbResolution);

		JLabel lblResolution = new JLabel("Screen Resolution:");
		lblResolution.setBounds(83, 52, 209, 14);
		contentPane.add(lblResolution);

		final JCheckBox ckbxVsync = new JCheckBox("VSync");
		ckbxVsync.setBounds(110, 90, 97, 23);
		contentPane.add(ckbxVsync);

		final JCheckBox ckbxFullscreen = new JCheckBox("Fullscreen");
		ckbxFullscreen.setBounds(213, 90, 97, 23);
		contentPane.add(ckbxFullscreen);

		JButton btnStart = new JButton("Play!");
		btnStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String[] arr = ((String) cbResolution.getSelectedItem()).split(" x ");
				int width = Integer.parseInt(arr[0]);
				int height = Integer.parseInt(arr[1]);
				boolean vs = ckbxVsync.isSelected();
				boolean fs = ckbxFullscreen.isSelected();
				try {
					launch(width, height, vs, fs);
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
		setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[]{contentPane, cbResolution, ckbxVsync, ckbxFullscreen, btnStart, btnQuit}));
	}

	private void launch(int width, int height, boolean vs, boolean fs) throws SlickException {

		AppGameContainer game = new AppGameContainer(new Nemesis());
		game.setDisplayMode(width, height, fs);
		game.setShowFPS(false);
		game.setVSync(vs);
		if (vs) game.setTargetFrameRate(60);
		game.setMaximumLogicUpdateInterval(1000 / 60);
		game.setMinimumLogicUpdateInterval(1000 / 60);
		game.start();

	}
}
