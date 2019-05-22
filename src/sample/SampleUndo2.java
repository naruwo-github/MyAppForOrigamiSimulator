package sample;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JTextPane;
import javax.swing.KeyStroke;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.text.Document;
import javax.swing.undo.UndoManager;

public class SampleUndo2 extends JFrame {
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

	Action getActionByName(String act_name) {
		Action[] array = jtext.getActions();
		for(int i = 0; i < array.length; i++) {
			String name = (String)array[i].getValue(Action.NAME);
			if(name.equals(act_name)) return array[i];
		}
		return null;
	}

	SampleUndo2(){//Constructor
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.jtext.setPreferredSize(new Dimension(160,120));
		this.getContentPane().add(jtext, BorderLayout.CENTER);

		//メニュー作成
		JMenuBar menuBar = new javax.swing.JMenuBar();
		this.getRootPane().setJMenuBar(menuBar);//メニューバーのセット
		JMenu menuEdit = new JMenu("編集(E)");
		menuEdit.setMnemonic('E');
		menuBar.add(menuEdit);

		JMenuItem itemUndo = new JMenuItem(undoAction);
		menuEdit.add(itemUndo);//メニュー項目追加
		itemUndo.setAccelerator(KeyStroke.getKeyStroke("ctrl Z"));
		JMenuItem itemRedo = new JMenuItem(redoAction);
		menuEdit.add(itemRedo);//メニュー項目追加
		itemRedo.setAccelerator(KeyStroke.getKeyStroke("ctrl Y"));
		menuEdit.addSeparator();
		Action actionCut=getActionByName("cut-to-clipboard");
		actionCut.putValue(Action.NAME, "切り取り");
		JMenuItem itemCut = new JMenuItem(actionCut);
		itemCut.setAccelerator(KeyStroke.getKeyStroke("ctrl X"));
		menuEdit.add(itemCut);//メニュー項目追加
		Action actionCopy=getActionByName("copy-to-clipboard");
		actionCopy.putValue(Action.NAME, "コピー");
		JMenuItem itemCopy = new JMenuItem(actionCopy);
		itemCopy.setAccelerator(KeyStroke.getKeyStroke("ctrl C"));
		menuEdit.add(itemCopy);//メニュー項目追加
		Action actionPaste=getActionByName("paste-from-clipboard");
		actionPaste.putValue(Action.NAME, "貼り付け");
		JMenuItem itemPaste = new JMenuItem(actionPaste);
		itemPaste.setAccelerator(KeyStroke.getKeyStroke("ctrl V"));
		menuEdit.add(itemPaste);//メニュー項目追加

		Document doc = this.jtext.getDocument();
		doc.addUndoableEditListener(new UndoRedoListener());

		this.pack();
		this.setVisible(true);
	}

	public static void main(String[] args) {
		new SampleUndo2();
	}
}
