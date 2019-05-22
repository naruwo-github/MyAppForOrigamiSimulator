package sample;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JFrame;
import javax.swing.JTextPane;
import javax.swing.KeyStroke;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.text.Document;
import javax.swing.text.Keymap;
import javax.swing.undo.UndoManager;

public class SampleUndo1 extends JFrame {
	JTextPane jtext = new JTextPane();

	//--Undo関連--
	UndoManager undoManager = new UndoManager();//UndoやRedoを管理する
	UndoAction undoAction = new UndoAction();//Undo処理用Actionサブクラス
	RedoAction redoAction = new RedoAction();

	class UndoAction extends AbstractAction{
		UndoAction(){
			putValue(Action.NAME, "元に戻す");
			setEnabled(false);
		}
		public void actionPerformed(ActionEvent e) {
			undoManager.undo();
			updateState();
			redoAction.updateState();
		}
		protected void updateState() {
			if(undoManager.canUndo()) {
				setEnabled(true);
			}else {
				setEnabled(false);
			}
		}
	}

	class RedoAction extends AbstractAction{
		RedoAction(){
			putValue(Action.NAME, "やり直し");
			setEnabled(false);
		}
		public void actionPerformed(ActionEvent e) {
			undoManager.redo();
			updateState();
			undoAction.updateState();
		}
		protected void updateState() {
			if(undoManager.canRedo()) {
				setEnabled(true);
			}else {
				setEnabled(false);
			}
		}
	}

	class UndoRedoListener implements UndoableEditListener{
		public void undoableEditHappened(UndoableEditEvent e) {
			undoManager.addEdit(e.getEdit());
			undoAction.updateState();
			redoAction.updateState();
		}
	}

	SampleUndo1(){//Constructor
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.jtext.setPreferredSize(new Dimension(160,120));
		this.getContentPane().add(jtext,  BorderLayout.CENTER);

		Keymap keymap = jtext.getKeymap();
		keymap.addActionForKeyStroke(KeyStroke.getKeyStroke(KeyEvent.VK_Z, InputEvent.CTRL_DOWN_MASK),undoAction);
		keymap.addActionForKeyStroke(KeyStroke.getKeyStroke(KeyEvent.VK_Y, InputEvent.CTRL_DOWN_MASK),redoAction);
		Document doc = this.jtext.getDocument();
		doc.addUndoableEditListener(new UndoRedoListener());

		this.pack();
		this.setVisible(true);
	}
	public static void main(String[] args) {
		new SampleUndo1();
	}
}
