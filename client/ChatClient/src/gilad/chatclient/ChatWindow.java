package gilad.chatclient;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.ScrollPaneConstants;

import common.ChatMessage;

@SuppressWarnings("serial")
public class ChatWindow extends JFrame 
{
	private final JTextPane chatPane = new JTextPane();
	private final JTextField textField = new JTextField("Enter messages here");
	
	private static final SimpleDateFormat sdf = new SimpleDateFormat("EEEEEEEEE dd/MM/yyyy HH:mm:ss");
	
	public ChatWindow(final ChatController controller)
	{
		initUI();
		initKeyboardListener(controller);
	}

	private void initKeyboardListener(final ChatController controller) 
	{
		KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(new KeyEventDispatcher() {
			
			@Override
			public boolean dispatchKeyEvent(KeyEvent e) 
			{
				String text = textField.getText();
				if (sendMessage(e, text))
				{
					controller.sendMessage(text);
					textField.setText("");
				}
				return false;
			}

			
			private boolean sendMessage(KeyEvent e, String text) 
			{
				return e.getID() == KeyEvent.KEY_PRESSED && e.getKeyCode() == KeyEvent.VK_ENTER && text != null && textField.hasFocus();
			}
		});
	}

	private void initUI() 
	{
		JPanel contentPane = new JPanel();
		this.setContentPane(contentPane);
		this.setPreferredSize(new Dimension(800, 600));
		
		contentPane.setLayout(new BorderLayout());
		JLabel titleLabel = new JLabel("Gilad's Cool Chat Application");
		
		contentPane.add(titleLabel, BorderLayout.NORTH);
		JScrollPane scrollPane = new JScrollPane(chatPane);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		contentPane.add(scrollPane, BorderLayout.CENTER);
		contentPane.add(textField, BorderLayout.SOUTH);
		chatPane.setEditable(false);
		
		textField.addFocusListener(new FocusListener() 
		{
			private boolean done = false;
			
			@Override
			public void focusLost(FocusEvent e) 
			{
				// TODO Auto-generated method stub
			}
			
			@Override
			public void focusGained(FocusEvent e)
			{
				if (!done)
				{
					textField.setText("");
					done = true;
				}
			}
		});
		
		this.pack();
		this.revalidate();
		this.repaint();
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public void newMessage(ChatMessage msg)
	{
		String newMessage = msg.author + " [ "+sdf.format(new Date()) + " ] : " + msg.msg;
		chatPane.setText(chatPane.getText() + "\n" + newMessage);
		scrollDown();
	}

	private void scrollDown() 
	{
		chatPane.setCaretPosition(chatPane.getDocument().getLength());
	}

}
