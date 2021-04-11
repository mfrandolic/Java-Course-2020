package hr.fer.zemris.java.hw11.jnotepadpp;

import java.awt.Component;
import java.awt.FontMetrics;
import java.awt.Image;
import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import javax.swing.ImageIcon;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;

import hr.fer.zemris.java.hw11.jnotepadpp.local.ILocalizationProvider;

/**
 * Default implementation of {@link MultipleDocumentModel} that is represented
 * by {@link JTabbedPane}. Each document is shown in one tab with icon of the tab
 * that shows modification status of the document.
 * 
 * @author Matija Frandolić
 */
public class DefaultMultipleDocumentModel extends JTabbedPane implements MultipleDocumentModel {

	/**
	 * Serial version UID.
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Current document of this model.
	 */
	private SingleDocumentModel currentDocument;
	
	/**
	 * List of documents kept by this model.
	 */
	private List<SingleDocumentModel> documents;
	
	/**
	 * List of listeners.
	 */
	private List<MultipleDocumentListener> listeners;
	
	/**
	 * Localization provider for tab titles.
	 */
	private ILocalizationProvider lp;
	
	/**
	 * Constructs a new {@code DefaultMultipleDocumentModel} that uses the given
	 * localization provider.
	 * 
	 * @param lp localization provider for tab titles
	 */
	public DefaultMultipleDocumentModel(ILocalizationProvider lp) {
		currentDocument = null;
		documents = new ArrayList<SingleDocumentModel>();
		listeners = new ArrayList<MultipleDocumentListener>();
		this.lp = lp;
		
		lp.addLocalizationListener(() -> {
			for (int i = 0, last = documents.size(); i < last; i++) {
				if (documents.get(i).getFilePath() == null) {
					String unnamed = "(" + lp.getString("unnamed") + ")";
					setTitleAt(i, unnamed);
					setToolTipTextAt(i, unnamed);
				}
			}
		});
		
		addChangeListener(e -> {
			SingleDocumentModel previousDocument = currentDocument;
			if (getSelectedIndex() == -1) {
				currentDocument = null;
			} else {
				currentDocument = documents.get(getSelectedIndex());				
			}
			fireCurrentDocumentChanged(previousDocument, currentDocument);
		});
	}

	@Override
	public Iterator<SingleDocumentModel> iterator() {
		return documents.iterator();
	}

	@Override
	public SingleDocumentModel createNewDocument() {
		SingleDocumentModel document = createDocument(null, "");
		return document;
	}

	@Override
	public SingleDocumentModel getCurrentDocument() {
		return currentDocument;
	}

	@Override
	public SingleDocumentModel loadDocument(Path path) {
		Objects.requireNonNull(path, "Path to document cannot be null.");
		path = path.toAbsolutePath().normalize();
		
		for (SingleDocumentModel document : documents) {
			if (document.getFilePath() == null) {
				continue;
			}
			Path documentPath = document.getFilePath().toAbsolutePath().normalize();
			if (path.equals(documentPath)) {
				setSelectedIndex(documents.indexOf(document));
				return document;
			}
		}
		
		byte[] bytes;
		try {
			bytes = Files.readAllBytes(path);
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
		
		String text = new String(bytes, StandardCharsets.UTF_8);
		SingleDocumentModel document = createDocument(path, text);
		
		return document;
	}

	@Override
	public void saveDocument(SingleDocumentModel model, Path newPath) {		
		Path savePath;
		if (newPath != null) {
			savePath = newPath.toAbsolutePath().normalize();
			for (SingleDocumentModel document : documents) {
				if (document.getFilePath() == null) {
					continue;
				}
				Path documentPath = document.getFilePath().toAbsolutePath().normalize();
				if (savePath.equals(documentPath)) {
					throw new IllegalArgumentException("Specified file is opened in another tab.");
				}
			}
		} else {
			savePath = model.getFilePath();
		}
		
		JTextArea textArea = currentDocument.getTextComponent();
		byte[] bytes = textArea.getText().getBytes(StandardCharsets.UTF_8);
		
		try {
			Files.write(savePath, bytes);
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
		
		model.setModified(false);
		model.setFilePath(savePath);
		fireStateChanged();
	}

	@Override
	public void closeDocument(SingleDocumentModel model) {
		int index = documents.indexOf(model);
		if (index == -1) {
			return;
		}
		documents.remove(model);
		removeTabAt(index);
		fireDocumentRemoved(model);
	}

	@Override
	public void addMultipleDocumentListener(MultipleDocumentListener l) {
		listeners = new ArrayList<>(listeners);
		listeners.add(l);
	}

	@Override
	public void removeMultipleDocumentListener(MultipleDocumentListener l) {
		listeners = new ArrayList<>(listeners);
		listeners.remove(l);
	}

	@Override
	public int getNumberOfDocuments() {
		return documents.size();
	}

	@Override
	public SingleDocumentModel getDocument(int index) {
		if (index < 0 || index > getNumberOfDocuments() - 1) {
			throw new IllegalArgumentException("Invalid index.");
		}
		return documents.get(index);
	}
	
	/**
	 * Creates a new document, sets its path and content to the given path 
	 * and text string, and returns it.
	 * 
	 * @param path path to be used as path of the newly created document
	 * @param text text to be used as content of the newly created document
	 * @return     created document
	 */
	private SingleDocumentModel createDocument(Path path, String text) {
		SingleDocumentModel document = new DefaultSingleDocumentModel(path, text);
		
		ImageIcon unmodifiedDocumentIcon = loadIcon("icons/greenDisk.png");
		ImageIcon modifiedDocumentIcon = loadIcon("icons/redDisk.png");
		
		String title;
		String toolTip;
		if (path != null) {
			title = path.getFileName().toString();
			toolTip = path.toString();
		} else {
			String unnamed = "(" + lp.getString("unnamed") + ")";
			title = unnamed;
			toolTip = unnamed;
		}
		
		documents.add(document);
		Component tabComponent = new JScrollPane(document.getTextComponent());
		addTab(title, unmodifiedDocumentIcon, tabComponent, toolTip);
		setSelectedIndex(documents.size() - 1);
		
		document.addSingleDocumentListener(new SingleDocumentListener() {
			@Override
			public void documentModifyStatusUpdated(SingleDocumentModel model) {
				if (model.isModified()) {
					setIconAt(documents.indexOf(model), modifiedDocumentIcon);
				} else {
					setIconAt(documents.indexOf(model), unmodifiedDocumentIcon);
				}
			}
			@Override
			public void documentFilePathUpdated(SingleDocumentModel model) {
				int index = documents.indexOf(model);
				Path documentPath = model.getFilePath();
				
				setTitleAt(index, documentPath.getFileName().toString());
				setToolTipTextAt(index, documentPath.toString());
			}
		});
		
		fireDocumentAdded(document);
		return document;
	}
	
	/**
	 * Loads the icon from the given path relative to this package and returns
	 * it with its height scaled to the height of font used by this model.
	 * 
	 * @param  path path from which to load the icon
	 * @return      icon that was loaded
	 * @throws NullPointerException if the given path is null
	 * @throws IllegalArgumentException if icon doesn't exist at the given path
	 */
	private ImageIcon loadIcon(String path) {
		Objects.requireNonNull(path, "Path to icon cannot be null");
		
		try (InputStream is = this.getClass().getResourceAsStream(path)) {
			if (is == null) {
				throw new IllegalArgumentException("Icon doesn't exist at given path.");
			}
			
			ImageIcon imageIcon = new ImageIcon(is.readAllBytes());
			FontMetrics fm = getGraphics().getFontMetrics();
			Image image = imageIcon.getImage();
			Image scaledImage = image.getScaledInstance(-1, fm.getHeight(), Image.SCALE_SMOOTH);
			
			return new ImageIcon(scaledImage); 
		} catch (IOException e) {
			return null;
		}
	}
	
	/**
	 * Notifies all registered listeners that the current document has changed.
	 * 
	 * @param previousModel document that was previously the current document
	 * @param currentModel  the current document
	 */
	private void fireCurrentDocumentChanged(SingleDocumentModel previousModel, 
	                                        SingleDocumentModel currentModel) {
		listeners.forEach(l -> l.currentDocumentChanged(previousModel, currentModel));
	}
	
	/**
	 * Notifies all registered listeners that a document was added.
	 * 
	 * @param model document that was added
	 */
	private void fireDocumentAdded(SingleDocumentModel model) {
		listeners.forEach(l -> l.documentAdded(model));
	}
	
	/**
	 * Notifies all registered listeners that a document was removed.
	 * 
	 * @param model document that was removed
	 */
	private void fireDocumentRemoved(SingleDocumentModel model) {
		listeners.forEach(l -> l.documentRemoved(model));
	}

}
