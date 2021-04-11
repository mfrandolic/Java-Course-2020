package hr.fer.zemris.java.hw11.jnotepadpp;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.Collator;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.function.UnaryOperator;

import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.event.CaretListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Caret;
import javax.swing.text.DefaultEditorKit;
import javax.swing.text.Document;
import javax.swing.text.Element;
import javax.swing.text.JTextComponent;

import hr.fer.zemris.java.hw11.jnotepadpp.local.FormLocalizationProvider;
import hr.fer.zemris.java.hw11.jnotepadpp.local.LocalizableAction;
import hr.fer.zemris.java.hw11.jnotepadpp.local.LocalizableJToolBar;
import hr.fer.zemris.java.hw11.jnotepadpp.local.LocalizationProvider;

/**
 * Implementation of text editor called "JNotepad++". This editor allows the user
 * to work on multiple documents at the same time by showing each document in a
 * separate tab. It also provides basic functionality expected from a text editor,
 * such as creating a new blank document, opening existing document, saving document,
 * cut/copy/paste actions, statistical information about the currently opened document,
 * tools for text manipulation and status bar with information about currently opened
 * document. All actions are available from menus, dockable toolbar and keyboard shortcuts.
 * Localization of GUI is provided in English, Croatian and German.
 * 
 * @author Matija Frandolić
 */
public class JNotepadPP extends JFrame {

	/**
	 * Serial version UID.
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Localization provider which is connected to this frame.
	 */
	private FormLocalizationProvider flp;
	
	/**
	 * Multiple document model used to keep documents.
	 */
	private MultipleDocumentModel model;
	
	/**
	 * Toolbar with actions.
	 */
	private JToolBar toolBar;
	
	/**
	 * File menu action (no "real" action, just name, description and mnemonic are set).
	 */
	private Action fileMenuAction;
	
	/**
	 * Edit menu action (no "real" action, just name, description and mnemonic are set).
	 */
	private Action editMenuAction;
	
	/**
	 * Tools menu action (no "real" action, just name, description and mnemonic are set).
	 */
	private Action toolsMenuAction;
	
	/**
	 * Change case submenu action (no "real" action, just name, description and mnemonic are set).
	 */
	private Action changeCaseSubMenuAction;
	
	/**
	 * Sort submenu (no "real" action, just name, description and mnemonic are set).
	 */
	private Action sortSubMenuAction;
	
	/**
	 * Languages menu action (no "real" action, just name, description and mnemonic are set).
	 */
	private Action languagesMenuAction;
	
	/**
	 * Create new document action.
	 */
	private Action newDocumentAction;
	
	/**
	 * Open existing document action.
	 */
	private Action openDocumentAction;
	
	/**
	 * Save document action.
	 */
	private Action saveDocumentAction;
	
	/**
	 * Save document under new path action.
	 */
	private Action saveAsDocumentAction;
	
	/**
	 * Close currently opened document action.
	 */
	private Action closeDocumentAction;
	
	/**
	 * Exit the program action.
	 */
	private Action exitAction;
	
	/**
	 * Cut currently selected text action.
	 */
	private Action cutAction;
	
	/**
	 * Copy currently selected text action.
	 */
	private Action copyAction;
	
	/**
	 * Paste text action.
	 */
	private Action pasteAction;
	
	/**
	 * Show statistical information about currently opened document action.
	 */
	private Action statisticsAction;
	
	/**
	 * Change currently selected text to upper case.
	 */
	private Action toUpperCaseAction;
	
	/**
	 * Change currently selected text to lower case.
	 */
	private Action toLowerCaseAction;
	
	/**
	 * Invert case of currently selected text.
	 */
	private Action invertCaseAction;
	
	/**
	 * Sort currently selected lines in ascending order.
	 */
	private Action ascendingAction;
	
	/**
	 * Sort currently selected lines in descending order.
	 */
	private Action descendingAction;
	
	/**
	 * Keep only unique lines from the currently selected lines.
	 */
	private Action uniqueAction;
	
	/**
	 * Change current language to English action.
	 */
	private Action enLanguageAction;
	
	/**
	 * Change current language to Croatian action.
	 */
	private Action hrLanguageAction;
	
	/**
	 * Change current language to German action.
	 */
	private Action deLanguageAction;
	
	/**
	 * Constructs the GUI.
	 */
	public JNotepadPP() {
		flp = new FormLocalizationProvider(LocalizationProvider.getInstance(), this);
		
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		setTitle("JNotepad++");
		initGUI();
		
		setSize(1800, 900);
		setLocationRelativeTo(null);
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				closeAllDocuments();
			}
		});
	}
	
	/**
	 * Main method of the program that is responsible for starting the GUI.
	 * 
	 * @param args command line arguments (not used)
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			new JNotepadPP().setVisible(true);
		});
	}
	
	/**
	 * Initializes the GUI components and layout.
	 */
	private void initGUI() {
		model = new DefaultMultipleDocumentModel(flp);
		
		((JTabbedPane) model).addChangeListener(e -> changeTitle());
		flp.addLocalizationListener(() -> changeTitle());
		
		createActions();
		createMenus();
		createToolBar();
		
		JPanel panel = new JPanel(new BorderLayout());
		panel.add((JTabbedPane) model, BorderLayout.CENTER);
		panel.add(toolBar, BorderLayout.NORTH);
		
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(panel, BorderLayout.CENTER);
		getContentPane().add(new StatusBar(this, model, flp), BorderLayout.SOUTH);
	}
	
	/**
	 * Action to be performed in order to set the correct title based on the currently
	 * opened file.
	 */
	private void changeTitle() {
		String title = "";
		
		if (model.getNumberOfDocuments() > 0) {
			int selectedIndex = ((JTabbedPane) model).getSelectedIndex();
			Path path = model.getDocument(selectedIndex).getFilePath();
			if (path == null) {
				title += "(" + flp.getString("unnamed") + ")";
			} else {
				title += path;
			}
			title += " - ";
		}
		
		setTitle(title + "JNotepad++");
	}
	
	/**
	 * Closes the given document and returns closing status. If the document has 
	 * unsaved changes, dialog is shown to either save the changes, discard them 
	 * or cancel the operation. Returned status is {@code true} if document was
	 * successfully closed (either by saving the changes or discarding them) or 
	 * {@code false} if the operation was canceled.
	 * 
	 * @param document document to be closed
	 * @return         {@code true} if document was successfully closed,
	 *                 {@code false} otherwise
	 */
	private boolean closeDocument(SingleDocumentModel document) {
		if (document == null) {
			return true;
		}
		
		if (!document.isModified()) {
			model.closeDocument(document);
			return true;
		}
		
		Object[] options = {
			flp.getString("saveButton"), 
			flp.getString("discardButton"), 
			flp.getString("cancelButton")
		};
		
		String documentPath;
		if (document.getFilePath() != null) {
			documentPath = document.getFilePath().toString();
		} else {
			documentPath = "(" + flp.getString("unnamed") + ")";
		}
		
		int status = JOptionPane.showOptionDialog(
			this,
			String.format(flp.getString("closingDialogMessage"), documentPath),
			flp.getString("warning"),
			JOptionPane.YES_NO_CANCEL_OPTION,
			JOptionPane.WARNING_MESSAGE,
			null,
			options,
			options[2]
		);
		
		switch (status) {
		case JOptionPane.YES_OPTION:
			Path newPath = null;
			if (model.getCurrentDocument().getFilePath() == null) {
				newPath = chooseSavePath();
				if (newPath == null) {
					return false;
				}
			}
			tryToSaveDocument(model.getCurrentDocument(), newPath);
			model.closeDocument(document);
			return true;
		case JOptionPane.NO_OPTION:
			model.closeDocument(document);
			return true;
		default:
			return false;
		}
	}
	
	/**
	 * Closes all opened documents and disposes this frame if all documents were
	 * closed. For each document it is checked if the document has unsaved changes. 
	 * If so, dialog is shown to either save the changes, discard them or cancel 
	 * the operation. If the operation is canceled for some document, the whole
	 * operation of closing all documents is also canceled. 
	 */
	private void closeAllDocuments() {
		while (model.getNumberOfDocuments() > 0) {
			if (!closeDocument(model.getCurrentDocument())) {
				return;
			}
		}
		dispose();
	}
	
	/**
	 * Shows the dialog to choose save path. If save path is not chosen (for example
	 * by canceling the operation), {@code null} is returned. 
	 * 
	 * @return
	 */
	private Path chooseSavePath() {
		JFileChooser jfc = new JFileChooser();
		jfc.setDialogTitle(flp.getString("saveChooserTitle"));
		if (jfc.showSaveDialog(this) != JFileChooser.APPROVE_OPTION) {
			return null;
		}
		return jfc.getSelectedFile().toPath();
	}
	
	/**
	 * Tries to save the given document at the given path. If at the given
	 * path already exists document that is opened in some other tab, an error
	 * dialog is shown. Error dialog is also shown if the saving operation fails
	 * due to I/O error.
	 * 
	 * @param document document to be saved
	 * @param newPath  path at which the given document should be saved
	 */
	private void tryToSaveDocument(SingleDocumentModel document, Path newPath) {
		if (newPath != null) {
			newPath = newPath.toAbsolutePath().normalize();
			for (SingleDocumentModel doc : model) {
				if (doc.getFilePath() == null) {
					continue;
				}
				Path docPath = doc.getFilePath().toAbsolutePath().normalize();
				if (newPath.equals(docPath)) {
					Object[] options = { flp.getString("okButton") };
					JOptionPane.showOptionDialog(
						this,
						String.format(flp.getString("fileAlreadyOpenedMessage"), 
								      newPath.toAbsolutePath()),
						flp.getString("error"),
						JOptionPane.OK_OPTION,
						JOptionPane.ERROR_MESSAGE,
						null,
						options,
						options[0]
					);
					return;
				}
			}
		}
		try {
			model.saveDocument(document, newPath);
		} catch (UncheckedIOException ex) {
			Object[] options = { flp.getString("okButton") };
			JOptionPane.showOptionDialog(
				this,
				String.format(flp.getString("saveFailedMessage"), 
						      newPath.toAbsolutePath()),
				flp.getString("error"),
				JOptionPane.OK_OPTION,
				JOptionPane.ERROR_MESSAGE,
				null,
				options,
				options[0]
			);
		}
	}
	
	/**
	 * Creates menus and menu items.
	 */
	private void createMenus() {
		JMenuBar menuBar = new JMenuBar();
		
		JMenu fileMenu = new JMenu(fileMenuAction);
		menuBar.add(fileMenu);
		
		fileMenu.add(new JMenuItem(newDocumentAction));
		fileMenu.add(new JMenuItem(openDocumentAction));
		fileMenu.add(new JMenuItem(saveDocumentAction));
		fileMenu.add(new JMenuItem(saveAsDocumentAction));
		fileMenu.addSeparator();
		fileMenu.add(new JMenuItem(closeDocumentAction));
		fileMenu.add(new JMenuItem(exitAction));
		
		JMenu editMenu = new JMenu(editMenuAction);
		menuBar.add(editMenu);
		
		editMenu.add(new JMenuItem(cutAction));
		editMenu.add(new JMenuItem(copyAction));
		editMenu.add(new JMenuItem(pasteAction));
		editMenu.addSeparator();
		editMenu.add(new JMenuItem(statisticsAction));
		
		JMenu toolsMenu = new JMenu(toolsMenuAction);
		menuBar.add(toolsMenu);
		
		JMenu changeCaseSubMenu = new JMenu(changeCaseSubMenuAction);
		toolsMenu.add(changeCaseSubMenu);
		
		changeCaseSubMenu.add(new JMenuItem(toUpperCaseAction));
		changeCaseSubMenu.add(new JMenuItem(toLowerCaseAction));
		changeCaseSubMenu.add(new JMenuItem(invertCaseAction));
		
		JMenu sortSubMenu = new JMenu(sortSubMenuAction);
		toolsMenu.add(sortSubMenu);
		
		sortSubMenu.add(new JMenuItem(ascendingAction));
		sortSubMenu.add(new JMenuItem(descendingAction));
		
		toolsMenu.add(new JMenuItem(uniqueAction));
		
		JMenu languagesMenu = new JMenu(languagesMenuAction);
		menuBar.add(languagesMenu);
		
		languagesMenu.add(new JMenuItem(enLanguageAction));
		languagesMenu.add(new JMenuItem(hrLanguageAction));
		languagesMenu.add(new JMenuItem(deLanguageAction));
		
		setJMenuBar(menuBar);
	}

	/**
	 * Creates toolbar.
	 */
	private void createToolBar() {
		toolBar = new LocalizableJToolBar("toolBarName", flp);
		toolBar.setFloatable(true);
		
		toolBar.add(new JButton(newDocumentAction));
		toolBar.add(new JButton(openDocumentAction));
		toolBar.add(new JButton(saveDocumentAction));
		toolBar.add(new JButton(saveAsDocumentAction));
		toolBar.addSeparator();
		toolBar.add(new JButton(closeDocumentAction));
		toolBar.add(new JButton(exitAction));
		toolBar.addSeparator();
		toolBar.add(new JButton(cutAction));
		toolBar.add(new JButton(copyAction));
		toolBar.add(new JButton(pasteAction));
		toolBar.addSeparator();
		toolBar.add(new JButton(statisticsAction));
		toolBar.addSeparator();
		toolBar.add(new JButton(toUpperCaseAction));
		toolBar.add(new JButton(toLowerCaseAction));
		toolBar.add(new JButton(invertCaseAction));
		toolBar.addSeparator();
		toolBar.add(new JButton(ascendingAction));
		toolBar.add(new JButton(descendingAction));
		toolBar.addSeparator();
		toolBar.add(new JButton(uniqueAction));
		toolBar.addSeparator();
	}
	
	/**
	 * Creates actions.
	 */
	private void createActions() {
		fileMenuAction = new LocalizableAction(
				"fileName", "fileDescription", null, "fileMnemonic", flp) {
			private static final long serialVersionUID = 1L;
			@Override
			public void actionPerformed(ActionEvent e) {
				// do nothing
			}
		};
		editMenuAction = new LocalizableAction(
				"editName", "editDescription", null, "editMnemonic", flp) {
			private static final long serialVersionUID = 1L;
			@Override
			public void actionPerformed(ActionEvent e) {
				// do nothing
			}
		};
		toolsMenuAction = new LocalizableAction(
				"toolsName", "toolsDescription", null, "toolsMnemonic", flp) {
			private static final long serialVersionUID = 1L;
			@Override
			public void actionPerformed(ActionEvent e) {
				// do nothing
			}
		};
		changeCaseSubMenuAction = new LocalizableAction(
				"changeCaseName", "changeCaseDescription", null, "changeCaseMnemonic", flp) {
			private static final long serialVersionUID = 1L;
			@Override
			public void actionPerformed(ActionEvent e) {
				// do nothing
			}
		};
		sortSubMenuAction = new LocalizableAction(
				"sortName", "sortDescription", null, "sortMnemonic", flp) {
			private static final long serialVersionUID = 1L;
			@Override
			public void actionPerformed(ActionEvent e) {
				// do nothing
			}
		};
		languagesMenuAction = new LocalizableAction(
				"languagesName", "languagesDescription", null, "languagesMnemonic", flp) {
			private static final long serialVersionUID = 1L;
			@Override
			public void actionPerformed(ActionEvent e) {
				// do nothing
			}
		};
		newDocumentAction = new LocalizableAction(
				"newName", "newDescription", "newAccelerator", "newMnemonic", flp) {
			private static final long serialVersionUID = 1L;
			@Override
			public void actionPerformed(ActionEvent e) {
				model.createNewDocument();
			}
		};
		openDocumentAction = new LocalizableAction(
				"openName", "openDescription", "openAccelerator", "openMnemonic", flp) {
			private static final long serialVersionUID = 1L;
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser jfc = new JFileChooser();
				jfc.setDialogTitle(flp.getString("openChooserTitle"));
				if (jfc.showOpenDialog(JNotepadPP.this) != JFileChooser.APPROVE_OPTION) {
					return;
				}
				Path filePath = jfc.getSelectedFile().toPath();
				if (!Files.isReadable(filePath)) {
					Object[] options = { flp.getString("okButton") };
					JOptionPane.showOptionDialog(
						JNotepadPP.this,
						String.format(flp.getString("noFileMessage"), 
								      filePath.toAbsolutePath()),
						flp.getString("error"),
						JOptionPane.OK_OPTION,
						JOptionPane.ERROR_MESSAGE,
						null,
						options,
						options[0]
					);
					return;
				}
				try {
					model.loadDocument(filePath);					
				} catch (UncheckedIOException ex) {
					Object[] options = { flp.getString("okButton") };
					JOptionPane.showOptionDialog(
						JNotepadPP.this,
						String.format(flp.getString("openFailedMessage"), 
								      filePath.toAbsolutePath()),
						flp.getString("error"),
						JOptionPane.OK_OPTION,
						JOptionPane.ERROR_MESSAGE,
						null,
						options,
						options[0]
					);
				}
			}
		};
		saveDocumentAction = new LocalizableAction(
				"saveName", "saveDescription", "saveAccelerator", "saveMnemonic", flp) {
			private static final long serialVersionUID = 1L;
			@Override
			public void actionPerformed(ActionEvent e) {
				if (model.getCurrentDocument() == null) {
					return;
				}
				Path newPath = null;
				if (model.getCurrentDocument().getFilePath() == null) {
					newPath = chooseSavePath();
					if (newPath == null) {
						return;
					}
				}
				tryToSaveDocument(model.getCurrentDocument(), newPath);
			}
		};
		saveAsDocumentAction = new LocalizableAction(
				"saveAsName", "saveAsDescription", "saveAsAccelerator", "saveAsMnemonic", flp) {
			private static final long serialVersionUID = 1L;
			@Override
			public void actionPerformed(ActionEvent e) {
				if (model.getCurrentDocument() == null) {
					return;
				}
				Path newPath = chooseSavePath();
				if (newPath == null) {
					return;
				}
				tryToSaveDocument(model.getCurrentDocument(), newPath);
			}
		};
		closeDocumentAction = new LocalizableAction(
				"closeName", "closeDescription", "closeAccelerator", "closeMnemonic", flp) {
			private static final long serialVersionUID = 1L;
			@Override
			public void actionPerformed(ActionEvent e) {
				closeDocument(model.getCurrentDocument());
			}
		};
		exitAction = new LocalizableAction(
				"exitName", "exitDescription", "exitAccelerator", "exitMnemonic", flp) {
			private static final long serialVersionUID = 1L;
			@Override
			public void actionPerformed(ActionEvent e) {
				closeAllDocuments();
			}
		};
		cutAction = new LocalizableAction(
				"cutName", "cutDescription", "cutAccelerator", "cutMnemonic", flp) {
			private static final long serialVersionUID = 1L;
			@Override
			public void actionPerformed(ActionEvent e) {
				DefaultEditorKit.CutAction cut = new DefaultEditorKit.CutAction();
				cut.actionPerformed(e);
			}
		};
		copyAction = new LocalizableAction(
				"copyName", "copyDescription", "copyAccelerator", "copyMnemonic", flp) {
			private static final long serialVersionUID = 1L;
			@Override
			public void actionPerformed(ActionEvent e) {
				DefaultEditorKit.CopyAction copy = new DefaultEditorKit.CopyAction();
				copy.actionPerformed(e);
			}
		};
		pasteAction = new LocalizableAction(
				"pasteName", "pasteDescription", "pasteAccelerator", "pasteMnemonic", flp) {
			private static final long serialVersionUID = 1L;
			@Override
			public void actionPerformed(ActionEvent e) {
				DefaultEditorKit.PasteAction paste = new DefaultEditorKit.PasteAction();
				paste.actionPerformed(e);
			}
		};
		statisticsAction = new LocalizableAction(
				"statisticsName", "statisticsDescription", "statisticsAccelerator", 
				"statisticsMnemonic", flp) {
			private static final long serialVersionUID = 1L;
			@Override
			public void actionPerformed(ActionEvent e) {
				if (model.getCurrentDocument() == null) {
					return;
				}
				JTextArea textArea = model.getCurrentDocument().getTextComponent();
				String text = textArea.getText();
				
				int characterCount = text.length();
				int nonBlankCharacterCount = text.replaceAll("\\s+", "").length();
				int lineCount = textArea.getLineCount();
				
				Object[] options = { flp.getString("okButton") };
				JOptionPane.showOptionDialog(
					JNotepadPP.this,
					String.format(flp.getString("statisticsMessage"), characterCount, 
				                  nonBlankCharacterCount, lineCount),
					flp.getString("statisticsName"),
					JOptionPane.OK_OPTION,
					JOptionPane.INFORMATION_MESSAGE,
					null,
					options,
					options[0]
				);
			}
		};
		toUpperCaseAction = new LocalizableAction(
				"toUpperCaseName", "toUpperCaseDescription", "toUpperCaseAccelerator", 
				"toUpperCaseMnemonic", flp) {
			private static final long serialVersionUID = 1L;
			@Override
			public void actionPerformed(ActionEvent e) {
				changeSelectedText(String::toUpperCase);
			}
		};
		toLowerCaseAction = new LocalizableAction(
				"toLowerCaseName", "toLowerCaseDescription", "toLowerCaseAccelerator", 
				"toLowerCaseMnemonic", flp) {
			private static final long serialVersionUID = 1L;
			@Override
			public void actionPerformed(ActionEvent e) {
				changeSelectedText(String::toLowerCase);
			}
		};
		invertCaseAction = new LocalizableAction(
				"invertCaseName", "invertCaseDescription", "invertCaseAccelerator", 
				"invertCaseMnemonic", flp) {
			private static final long serialVersionUID = 1L;
			@Override
			public void actionPerformed(ActionEvent e) {
				UnaryOperator<String> invertCase = text -> {
					char[] charArray = text.toCharArray();
					for (int i = 0; i < charArray.length; i++) {
						char c = charArray[i];
						if (Character.isLowerCase(c)) {
							charArray[i] = Character.toUpperCase(c);
						} else if (Character.isUpperCase(c)) {
							charArray[i] = Character.toLowerCase(c);
						}
					}
					return new String(charArray);
				};
				
				changeSelectedText(invertCase);
			}
		};
		ascendingAction = new LocalizableAction(
				"ascendingName", "ascendingDescription", "ascendingAccelerator", 
				"ascendingMnemonic", flp) {
			private static final long serialVersionUID = 1L;
			@Override
			public void actionPerformed(ActionEvent e) {
				UnaryOperator<List<String>> sortAscending = lines -> {
					Locale currentLocale = new Locale(flp.getCurrentLanguage());
					Collator collator = Collator.getInstance(currentLocale);
					lines.sort(collator);
					return lines;
				};
				
				changeSelectedLines(sortAscending);
			}
		};
		descendingAction = new LocalizableAction(
				"descendingName", "descendingDescription", "descendingAccelerator", 
				"descendingMnemonic", flp) {
			private static final long serialVersionUID = 1L;
			@Override
			public void actionPerformed(ActionEvent e) {
				UnaryOperator<List<String>> sortDescending = lines -> {
					Locale currentLocale = new Locale(flp.getCurrentLanguage());
					Collator collator = Collator.getInstance(currentLocale);
					lines.sort(collator.reversed());
					return lines;
				};
				
				changeSelectedLines(sortDescending);
			}
		};
		uniqueAction = new LocalizableAction(
				"uniqueName", "uniqueDescription", "uniqueAccelerator", "uniqueMnemonic", flp) {
			private static final long serialVersionUID = 1L;
			@Override
			public void actionPerformed(ActionEvent e) {
				UnaryOperator<List<String>> retainUnique = lines -> {
					Set<String> uniqueLines = new LinkedHashSet<>(lines);
					return new ArrayList<String>(uniqueLines);
				};
				
				changeSelectedLines(retainUnique);
			}
		};
		enLanguageAction = new LocalizableAction(
				"enLanguageName", "enLanguageDescription", null, "enLanguageMnemonic", flp) {
			private static final long serialVersionUID = 1L;
			@Override
			public void actionPerformed(ActionEvent e) {
				LocalizationProvider.getInstance().setLanguage("en");
			}
		};
		hrLanguageAction = new LocalizableAction(
				"hrLanguageName", "hrLanguageDescription", null, "hrLanguageMnemonic", flp) {
			private static final long serialVersionUID = 1L;
			@Override
			public void actionPerformed(ActionEvent e) {
				LocalizationProvider.getInstance().setLanguage("hr");
			}
		};
		deLanguageAction = new LocalizableAction(
				"deLanguageName", "deLanguageDescription", null, "deLanguageMnemonic", flp) {
			private static final long serialVersionUID = 1L;
			@Override
			public void actionPerformed(ActionEvent e) {
				LocalizationProvider.getInstance().setLanguage("de");
			}
		};
		
		setToolsMenuItemsEnabled(false);
		
		model.addMultipleDocumentListener(new MultipleDocumentListener() {
			CaretListener listener;
			
			@Override
			public void currentDocumentChanged(SingleDocumentModel previousModel, 
			                                   SingleDocumentModel currentModel) {
				if (previousModel != null) {
					JTextComponent previousTextComponent = previousModel.getTextComponent();
					previousTextComponent.removeCaretListener(listener);	
				}
				if (currentModel != null) {
					JTextComponent currentTextComponent = currentModel.getTextComponent();
					updateToolsMenuItems(currentTextComponent);
					listener = e -> updateToolsMenuItems(currentTextComponent);
					currentTextComponent.addCaretListener(listener);
				} else {
					setToolsMenuItemsEnabled(false);
				}
			}
			
			@Override
			public void documentRemoved(SingleDocumentModel model) {
				// do nothing
			}
			
			@Override
			public void documentAdded(SingleDocumentModel model) {
				// do nothing
			}
		});
	}
	
	/**
	 * Sets "Tools" menu items to either enabled or disabled.
	 * 
	 * @param enabled if set to {@code true} are the menu items enabled
	 */
	private void setToolsMenuItemsEnabled(boolean enabled) {
		toUpperCaseAction.setEnabled(enabled);
		toLowerCaseAction.setEnabled(enabled);
		invertCaseAction.setEnabled(enabled);
		ascendingAction.setEnabled(enabled);
		descendingAction.setEnabled(enabled);
		uniqueAction.setEnabled(enabled);
	}
	
	/**
	 * Action to be performed to update "Tools" menu items to enabled or disabled status
	 * if the selection exists in the given text component.
	 * 
	 * @param textComponent text component depending on which are the "Tools" menu 
	 *                      items updated to either enabled or disabled
	 */
	private void updateToolsMenuItems(JTextComponent textComponent) {
		Caret caret = textComponent.getCaret();
		int selection = Math.abs(caret.getDot() - caret.getMark());
		
		if (selection == 0) {
			setToolsMenuItemsEnabled(false);
		} else {
			setToolsMenuItemsEnabled(true);
		}
	}
	
	/**
	 * Transformation to be performed on selected text of current document.
	 * 
	 * @param transformation transformation to be performed on selected text of 
	 *                       current document
	 */
	private void changeSelectedText(UnaryOperator<String> transformation) {
		JTextComponent textComponent = model.getCurrentDocument().getTextComponent();
		Document doc = textComponent.getDocument();
		Caret caret = textComponent.getCaret();
		
		int length = Math.abs(caret.getDot() - caret.getMark());
		int offset = Math.min(caret.getDot(), caret.getMark());
		
		try {
			String text = doc.getText(offset, length);
			text = transformation.apply(text);
			doc.remove(offset, length);
			doc.insertString(offset, text, null);
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Transformation to be performed on selected lines of current document.
	 * 
	 * @param transformation transformation to be performed on selected lines of 
	 *                       current document
	 */
	private void changeSelectedLines(UnaryOperator<List<String>> transformation) {
		JTextComponent textComponent = model.getCurrentDocument().getTextComponent();
		Document doc = textComponent.getDocument();
		Element root = doc.getDefaultRootElement();
		Caret caret = textComponent.getCaret();
		
		int fromLine = root.getElementIndex(Math.min(caret.getDot(), caret.getMark()));
		int toLine = root.getElementIndex(Math.max(caret.getDot(), caret.getMark()));
		int fromLineOffset = root.getElement(fromLine).getStartOffset();
		int toLineOffset = root.getElement(toLine).getEndOffset();
		
		List<String> lines = new ArrayList<>();
		for (int i = fromLine; i <= toLine; i++) {
			Element line = root.getElement(i);
			int lineStart = line.getStartOffset();
			int lineEnd = line.getEndOffset();
			try {
				if (lineEnd == doc.getLength() + 1) {
					lines.add(doc.getText(lineStart, doc.getLength() - lineStart));					
				} else {
					lines.add(doc.getText(lineStart, lineEnd - lineStart));										
				}
			} catch (BadLocationException e) {
				e.printStackTrace();
			}
		}
		
		try {
			if (toLineOffset == doc.getLength() + 1) {
				doc.remove(fromLineOffset, doc.getLength() - fromLineOffset);								
			} else {
				doc.remove(fromLineOffset, toLineOffset - fromLineOffset);				
			}
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
		
		lines = transformation.apply(lines);
		
		int i = fromLineOffset;
		for (String line : lines) {
			try {
				doc.insertString(i, line, null);										
			} catch (BadLocationException e) {
				e.printStackTrace();
			}
			i += line.length();
		}
		
		if (i != doc.getLength()) {
			caret.setDot(i - 1);			
		}
	}
	
}
