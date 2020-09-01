package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import controller.PhotoComponent;


public class PhotoView extends JFrame {

	PhotoComponent photoComponent = null;
	JScrollPane scrollPane = null;

	JPanel mainPanel = new JPanel();

	JToolBar toolBar = new JToolBar(JToolBar.TOP);
	JToolBar toolBarStatus = new JToolBar();
	JToolBar toolBarMenu = new JToolBar();

	public JMenuBar menuBar;

	private JMenu fileMenu, viewMenu;
	private JRadioButtonMenuItem photoRadioItem, browserRadioItem, splitRadioItem;

	private JLabel statusBar = new JLabel();

	public JToggleButton familyButton, vacationButton, schoolButton;

	private void groupButton() {

		ButtonGroup grup = new ButtonGroup();
		grup.add(photoRadioItem);
		grup.add(browserRadioItem);
		grup.add(splitRadioItem);
	}

	private void toggleButton() {

		ButtonGroup grup = new ButtonGroup();
		grup.add(familyButton);
		grup.add(vacationButton);
		grup.add(schoolButton);
	}

	private JMenuItem importFile, deleteItem, quitItem;

	public PhotoView() {


		// frame.setSize(1280 , 960);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int height = screenSize.height * 1 / 3;
		int width = screenSize.width * 1 / 3;

		this.setPreferredSize(new Dimension(screenSize.width, screenSize.height));
		this.setMinimumSize(new Dimension(1050, 700));
		this.setMinimumSize(new Dimension(width, height));
		this.setResizable(true);
		this.setTitle("Foto");

		this.setVisible(true);

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// add text to label
		statusBar.setText("CURRENT STATUS");
		statusBar.isFontSet();


		this.add(mainPanel);
		mainPanel.setLayout(null);

		  mainPanel.setBackground(new Color(220, 220, 220));
	        mainPanel.setMaximumSize(null);
	        mainPanel.setMinimumSize(new Dimension(50, 50));

		mainPanel.add(statusBar, BorderLayout.SOUTH);

		addMenu();

	}

	public void addMenu()

	{

		menuBar = new JMenuBar();
		this.setJMenuBar(menuBar);

		fileMenu = new JMenu("File");
		fileMenu.setMnemonic('D');

		menuBar.add(fileMenu);

		viewMenu = new JMenu("View");

		viewMenu.setMnemonic('z');

		menuBar.add(viewMenu);

		importFile = new JMenuItem("Import");

		fileMenu.add(importFile);;

		importFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				createPhotoComponent();
				statusBar.setText("Import");

			}

			@SuppressWarnings("unused")
			private void createPhotoComponent() {
				JFileChooser chooser = new JFileChooser();

				FileNameExtensionFilter filter = new FileNameExtensionFilter("Image files",
						ImageIO.getReaderFileSuffixes());
				chooser.setFileFilter(filter);
				chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
				int selectPhoto = chooser.showOpenDialog(null);

				if (selectPhoto == JFileChooser.APPROVE_OPTION) {
					if (photoComponent != null) {
						scrollPane.remove(photoComponent);
						mainPanel.remove(scrollPane);
						scrollPane = null;
						photoComponent = null;
						mainPanel.repaint();
					}

				}

				photoComponent = new PhotoComponent(chooser.getSelectedFile().getAbsolutePath(), statusBar);

				scrollPane = new JScrollPane(photoComponent, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
						JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
				scrollPane.setSize(mainPanel.getWidth(), mainPanel.getHeight());
				mainPanel.add(scrollPane, BorderLayout.CENTER);
				photoComponent.requestFocus();
				mainPanel.revalidate();
				mainPanel.repaint();

			}


		});


	    addComponentListener(new java.awt.event.ComponentAdapter() {
	    public void componentResized( ComponentEvent e) {
	    	resizeComponent(e);
        }

		private void resizeComponent(ComponentEvent e) {
			// TODO Auto-generated method stub
			if(scrollPane != null){
	            scrollPane.setSize(mainPanel.getWidth(), mainPanel.getHeight());
	        }

		}
    });


		// Delete Menu Item
		deleteItem = new JMenuItem("Delete");
		fileMenu.add(deleteItem);
		deleteItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				statusBar.setText("Delete");

				createDeleteComponent();

			}

			private void createDeleteComponent() {
				if (photoComponent != null) {
					scrollPane.remove(photoComponent);
					mainPanel.remove(scrollPane);
					scrollPane = null;
					photoComponent = null;
					mainPanel.repaint();
				}

			}

		});

		fileMenu.addSeparator();

		quitItem = new JMenuItem("exit");

		quitItem.setMnemonic('q');

		fileMenu.add(quitItem);

		quitItem.addActionListener(new ActionListener()

		{

			public void actionPerformed(ActionEvent e)

			{
				statusBar.setText("Quit");

				int secim = JOptionPane.showConfirmDialog(null,

						"Do you wanna exit ?",

						"exit",

						JOptionPane.YES_NO_OPTION);

				if (secim == JOptionPane.YES_OPTION)

					System.exit(0);

				else

					return;

			}

		});

		photoRadioItem = new JRadioButtonMenuItem("Photo Viewer");
		photoRadioItem.setSelected(true);
		viewMenu.add(photoRadioItem);

		photoRadioItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				statusBar.setText("Photo Viewer");

			}
		});

		browserRadioItem = new JRadioButtonMenuItem("Browser");
		viewMenu.add(browserRadioItem);

		browserRadioItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				statusBar.setText("Browser");

			}
		});

		splitRadioItem = new JRadioButtonMenuItem("Split mode");
		viewMenu.add(splitRadioItem);

		splitRadioItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				statusBar.setText("Split mode");

			}
		});

		familyButton = new JToggleButton(" Family ");
		familyButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				statusBar.setText("Family category selected");

			}
		});

		vacationButton = new JToggleButton("Vacation");
		vacationButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				statusBar.setText("Vacation category selected");
			}
		});
		vacationButton.setBounds(10, 27, 21, 23);
		schoolButton = new JToggleButton("School");

		schoolButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				statusBar.setText("School category selected");

			}
		});
		schoolButton.setBounds(10, 27, 21, 23);

		groupButton();
		toggleButton();
		toolBar.add(familyButton);
		toolBar.addSeparator();

		toolBar.add(vacationButton);
		toolBar.addSeparator();
		toolBar.add(schoolButton);


		Container contentPane = this.getContentPane();
		contentPane.add(toolBar, BorderLayout.WEST);
		contentPane.add(statusBar, BorderLayout.SOUTH);


		  final JButton button = new JButton("Change Background");

		   ActionListener actionListener = new ActionListener() {
		     public void actionPerformed(ActionEvent actionEvent) {
		       Color initialBackground = button.getBackground();
		       Color background = JColorChooser.showDialog(null,
		           "Background Color Changer", initialBackground);
		       if (background != null) {
		         mainPanel.setBackground(background);

		       }
		     }
		   };
		   button.addActionListener(actionListener);
		   toolBar.addSeparator();
		   toolBar.addSeparator();
		   toolBar.add(button, BorderLayout.CENTER);

	}

	public static void main(String[] args) {
		new PhotoView();
	}
}